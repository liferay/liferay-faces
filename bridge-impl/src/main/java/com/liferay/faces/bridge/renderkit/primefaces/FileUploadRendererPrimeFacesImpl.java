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
package com.liferay.faces.bridge.renderkit.primefaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.faces.render.Renderer;
import javax.faces.render.RendererWrapper;

import org.apache.commons.fileupload.FileItem;

import com.liferay.faces.bridge.component.primefaces.PrimeFacesFileUpload;
import com.liferay.faces.bridge.context.map.RequestParameterMap;
import com.liferay.faces.bridge.model.UploadedFile;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class is a runtime wrapper around the PrimeFaces FileUploadRenderer class that makes the p:fileUpload component
 * compatible with a portlet environment.
 *
 * @author  Neil Griffin
 */
public class FileUploadRendererPrimeFacesImpl extends RendererWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FileUploadRendererPrimeFacesImpl.class);

	// Private Constants
	private static final String FQCN_DEFAULT_UPLOADED_FILE = "org.primefaces.model.DefaultUploadedFile";
	private static final String FQCN_FILE_UPLOAD_EVENT = "org.primefaces.event.FileUploadEvent";
	private static final String FQCN_UPLOADED_FILE = "org.primefaces.model.UploadedFile";

	// Private Data Members
	private Renderer wrappedRenderer;

	public FileUploadRendererPrimeFacesImpl(Renderer renderer) {
		this.wrappedRenderer = renderer;
	}

	/**
	 * This method overrides the {@link #decode(FacesContext, UIComponent)} method so that it can avoid a Servlet-API
	 * dependency in the PrimeFaces FileUploadRenderer. Note that p:fileUpload will do an Ajax postback and invoke the
	 * JSF lifecycle for each individual file.
	 */
	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		try {
			String clientId = uiComponent.getClientId(facesContext);
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
			String submittedValue = requestParameterMap.get(clientId);

			if (submittedValue != null) {

				// Get the UploadedFile from the request attribute map.
				Map<String, Object> requestAttributeMap = externalContext.getRequestMap();
				@SuppressWarnings("unchecked")
				Map<String, List<UploadedFile>> facesFileMap = (Map<String, List<UploadedFile>>)
					requestAttributeMap.get(RequestParameterMap.PARAM_UPLOADED_FILES);
				List<UploadedFile> uploadedFiles = facesFileMap.get(clientId);

				if (uploadedFiles != null) {

					for (UploadedFile uploadedFile : uploadedFiles) {

						// Convert the UploadedFile to a Commons-FileUpload FileItem.
						FileItem fileItem = new PrimeFacesFileItem(clientId, uploadedFile);

						// Reflectively create an instance of the PrimeFaces DefaultUploadedFile class.
						Class<?> defaultUploadedFileClass = Class.forName(FQCN_DEFAULT_UPLOADED_FILE);
						Constructor<?> constructor = defaultUploadedFileClass.getDeclaredConstructor(FileItem.class);
						Object defaultUploadedFile = constructor.newInstance(fileItem);

						// If the PrimeFaces FileUpload component is in "simple" mode, then simply set the submitted
						// value of the component to the DefaultUploadedFile instance.
						PrimeFacesFileUpload primeFacesFileUpload = new PrimeFacesFileUpload((UIInput) uiComponent);

						if (primeFacesFileUpload.getMode().equals(PrimeFacesFileUpload.MODE_SIMPLE)) {
							logger.debug("Setting submittedValue=[{0}]", submittedValue);
							primeFacesFileUpload.setSubmittedValue(defaultUploadedFile);
						}

						// Otherwise,
						else {
							logger.debug("Queuing FileUploadEvent for submittedValue=[{0}]", submittedValue);

							// Reflectively create an instance of the PrimeFaces FileUploadEvent class.
							Class<?> uploadedFileClass = Class.forName(FQCN_UPLOADED_FILE);
							Class<?> fileUploadEventClass = Class.forName(FQCN_FILE_UPLOAD_EVENT);
							constructor = fileUploadEventClass.getConstructor(UIComponent.class, uploadedFileClass);

							FacesEvent fileUploadEvent = (FacesEvent) constructor.newInstance(uiComponent,
									defaultUploadedFile);

							// Queue the event.
							primeFacesFileUpload.queueEvent(fileUploadEvent);
						}
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

	protected class PrimeFacesFileItem implements FileItem {

		// serialVersionUID
		private static final long serialVersionUID = 4243775660521293895L;

		// Private Data Members
		private String clientId;
		private UploadedFile uploadedFile;

		public PrimeFacesFileItem(String clientId, UploadedFile uploadedFile) {
			this.clientId = clientId;
			this.uploadedFile = uploadedFile;
		}

		public void delete() {

			// Will never be called by the PrimeFaces UploadedFile interface.
			throw new UnsupportedOperationException();
		}

		public byte[] get() {

			byte[] bytes = null;

			try {
				File file = new File(uploadedFile.getAbsolutePath());

				if (file.exists()) {
					RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
					bytes = new byte[(int) randomAccessFile.length()];
					randomAccessFile.readFully(bytes);
					randomAccessFile.close();
					file.delete();
				}
			}
			catch (Exception e) {
				logger.error(e);
			}

			return bytes;
		}

		public void write(File file) throws Exception {

			// Will never be called by the PrimeFaces UploadedFile interface.
			throw new UnsupportedOperationException();
		}

		public String getContentType() {
			return uploadedFile.getContentType();
		}

		public boolean isFormField() {
			return false;
		}

		public String getFieldName() {
			return clientId;
		}

		public void setFieldName(String name) {
			clientId = name;
		}

		public void setFormField(boolean state) {

			// Will never be called by the PrimeFaces UploadedFile interface.
			throw new UnsupportedOperationException();
		}

		public InputStream getInputStream() throws IOException {
			return new UploadedFileInputStream(uploadedFile.getAbsolutePath());
		}

		public String getName() {
			return uploadedFile.getName();
		}

		public OutputStream getOutputStream() throws IOException {

			// Will never be called by the PrimeFaces UploadedFile interface.
			throw new UnsupportedOperationException();
		}

		public long getSize() {
			return uploadedFile.getSize();
		}

		public String getString() {
			return getString(StringPool.UTF8);
		}

		public String getString(String encoding) {
			String stringValue = null;
			byte[] bytes = get();

			if (bytes != null) {

				try {
					stringValue = new String(bytes, encoding);
				}
				catch (UnsupportedEncodingException e) {
					logger.error(e);
				}
			}

			return stringValue;
		}

		public boolean isInMemory() {
			return false;
		}

	}

	protected class UploadedFileInputStream extends FileInputStream {

		// Private Data Members
		private String absolutePath;

		public UploadedFileInputStream(String absolutePath) throws FileNotFoundException {
			super(absolutePath);
			this.absolutePath = absolutePath;
		}

		@Override
		public void close() throws IOException {
			super.close();

			try {
				File file = new File(absolutePath);
				file.delete();
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

	}
}
