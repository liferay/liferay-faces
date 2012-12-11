/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.bridge.renderkit.richfaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.faces.render.Renderer;
import javax.faces.render.RendererWrapper;

import com.liferay.faces.bridge.context.map.RequestParameterMap;
import com.liferay.faces.bridge.model.UploadedFile;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class is a runtime wrapper around the RichFaces FileUploadRenderer class that makes the rich:fileUpload
 * component compatible with a portlet environment.
 *
 * @author  Neil Griffin
 */
public class FileUploadRendererRichFacesImpl extends RendererWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FileUploadRendererRichFacesImpl.class);

	// Private Constants
	private static final String RICHFACES_UPLOADED_FILE_FQCN = "org.richfaces.model.UploadedFile";
	private static final String RICHFACES_FILE_UPLOAD_EVENT_FQCN = "org.richfaces.event.FileUploadEvent";

	// Private Data Members
	private Renderer wrappedRenderer;

	public FileUploadRendererRichFacesImpl(Renderer renderer) {
		this.wrappedRenderer = renderer;
	}

	/**
	 * This method overrides the {@link #decode(FacesContext, UIComponent)} method so that it can avoid a Servlet-API
	 * dependency in the RichFaces FileUploadRenderer. Note that rich:fileUpload will do an Ajax postback and invoke the
	 * JSF lifecycle for each individual file.
	 */
	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		try {

			String clientId = uiComponent.getClientId(facesContext);
			ExternalContext externalContext = facesContext.getExternalContext();

			// Get the UploadedFile from the request attribute map.
			Map<String, Object> requestAttributeMap = externalContext.getRequestMap();
			@SuppressWarnings("unchecked")
			Map<String, List<UploadedFile>> uploadedFilesMap = (Map<String, List<UploadedFile>>)
				requestAttributeMap.get(RequestParameterMap.PARAM_UPLOADED_FILES);

			if (uploadedFilesMap != null) {

				// Use reflection to create a dynamic proxy class that implements the RichFaces UploadedFile interface.
				Class<?> uploadedFileInterface = Class.forName(RICHFACES_UPLOADED_FILE_FQCN);
				Class<?> fileUploadEventClass = Class.forName(RICHFACES_FILE_UPLOAD_EVENT_FQCN);
				ClassLoader classLoader = uploadedFileInterface.getClassLoader();

				List<UploadedFile> uploadedFiles = uploadedFilesMap.get(clientId);

				if (uploadedFiles != null) {

					for (UploadedFile uploadedFile : uploadedFiles) {
						RichFacesUploadedFileHandler richFacesUploadedFileHandler = new RichFacesUploadedFileHandler(
								uploadedFile);
						Object richFacesUploadedFile = Proxy.newProxyInstance(classLoader,
								new Class[] { uploadedFileInterface }, richFacesUploadedFileHandler);
						FacesEvent fileUploadEvent = (FacesEvent) fileUploadEventClass.getConstructor(UIComponent.class,
								uploadedFileInterface).newInstance(uiComponent, richFacesUploadedFile);

						// Queue the RichFaces FileUploadEvent instance so that it can be handled with an
						// ActionListener.
						uiComponent.queueEvent(fileUploadEvent);
					}
				}
			}
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public Renderer getWrapped() {
		return wrappedRenderer;
	}

	public class RichFacesUploadedFileHandler implements InvocationHandler {

		// Private Constants
		private static final String METHOD_DELETE = "delete";
		private static final String METHOD_GET_CONTENT_TYPE = "getContentType";
		private static final String METHOD_GET_DATA = "getData";
		private static final String METHOD_GET_INPUT_STREAM = "getInputStream";
		private static final String METHOD_GET_NAME = "getName";
		private static final String METHOD_GET_SIZE = "getSize";
		private static final String METHOD_WRITE = "write";

		// Private Data Members
		private UploadedFile uploadedFile;

		public RichFacesUploadedFileHandler(UploadedFile uploadedFile) {
			this.uploadedFile = uploadedFile;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			String methodName = method.getName();

			if (METHOD_DELETE.equals(methodName)) {
				File file = new File(uploadedFile.getAbsolutePath());
				file.delete();

				return null;
			}
			else if (METHOD_GET_CONTENT_TYPE.equals(methodName)) {
				return uploadedFile.getContentType();
			}
			else if (METHOD_GET_DATA.equals(methodName)) {
				return getBytes();
			}
			else if (METHOD_GET_INPUT_STREAM.equals(methodName)) {
				return new FileInputStream(uploadedFile.getAbsolutePath());
			}
			else if (METHOD_GET_NAME.equals(methodName)) {
				return uploadedFile.getName();
			}
			else if (METHOD_GET_SIZE.equals(methodName)) {
				return uploadedFile.getSize();
			}
			else if (METHOD_WRITE.equals(methodName)) {
				String fileName = (String) args[0];
				OutputStream outputStream = new FileOutputStream(fileName);
				outputStream.write(getBytes());
				outputStream.close();

				return null;
			}
			else {

				// Unsupported method.
				return null;
			}
		}

		protected byte[] getBytes() {
			byte[] bytes = null;

			try {
				File file = new File(uploadedFile.getAbsolutePath());

				if (file.exists()) {
					RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
					bytes = new byte[(int) randomAccessFile.length()];
					randomAccessFile.readFully(bytes);
					randomAccessFile.close();
				}
			}
			catch (Exception e) {
				logger.error(e);
			}

			return bytes;
		}

	}

}
