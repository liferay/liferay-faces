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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.faces.render.Renderer;

import org.apache.commons.fileupload.FileItem;

import com.liferay.faces.bridge.component.UploadedFile;
import com.liferay.faces.bridge.component.primefaces.PrimeFacesFileUpload;
import com.liferay.faces.bridge.context.map.RequestParameterMap;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.bridge.renderkit.html_basic.RendererWrapper;


/**
 * @author  Neil Griffin
 */
public class PrimeFacesFileUploadRenderer extends RendererWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PrimeFacesFileUploadRenderer.class);

	// Private Constants
	private static final String FQCN_DEFAULT_UPLOADED_FILE = "org.primefaces.model.DefaultUploadedFile";
	private static final String FQCN_FILE_UPLOAD_EVENT = "org.primefaces.event.FileUploadEvent";
	private static final String FQCN_UPLOADED_FILE = "org.primefaces.model.UploadedFile";

	// Private Data Members
	private Renderer wrappedRenderer;

	public PrimeFacesFileUploadRenderer(Renderer renderer) {
		this.wrappedRenderer = renderer;
	}

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
				Map<String, UploadedFile> facesFileMap = (Map<String, UploadedFile>) requestAttributeMap.get(
						RequestParameterMap.PARAM_UPLOADED_FILES);
				UploadedFile uploadedFile = facesFileMap.get(clientId);

				// Convert the UploadedFile to a Commons-FileUpload FileItem.
				FileItem fileItem = new PrimeFacesFileItem(clientId, uploadedFile);

				// Reflectively create an instance of the PrimeFaces DefaultUploadedFile class.
				Class<?> defaultUploadedFileClass = Class.forName(FQCN_DEFAULT_UPLOADED_FILE);
				Constructor<?> constructor = defaultUploadedFileClass.getDeclaredConstructor(FileItem.class);
				Object defaultUploadedFile = constructor.newInstance(fileItem);

				// If the PrimeFaces FileUpload component is in "simple" mode, then simply set the submitted value of
				// the component to the DefaultUploadedFile instance.
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

					FacesEvent fileUploadEvent = (FacesEvent) constructor.newInstance(uiComponent, defaultUploadedFile);

					// Queue the event.
					primeFacesFileUpload.queueEvent(fileUploadEvent);
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
			throw new UnsupportedOperationException();
		}

		public byte[] get() {
			throw new UnsupportedOperationException();
		}

		public void write(File file) throws Exception {
			throw new UnsupportedOperationException();
		}

		public String getContentType() {
			return uploadedFile.getContentType();
		}

		public boolean isFormField() {
			return true;
		}

		public String getFieldName() {
			return clientId;
		}

		public void setFieldName(String name) {
			clientId = name;
		}

		public void setFormField(boolean state) {
			throw new UnsupportedOperationException();
		}

		public InputStream getInputStream() throws IOException {
			throw new UnsupportedOperationException();
		}

		public String getName() {
			return uploadedFile.getName();
		}

		public OutputStream getOutputStream() throws IOException {
			throw new UnsupportedOperationException();
		}

		public long getSize() {
			return uploadedFile.getSize();
		}

		public String getString() {
			throw new UnsupportedOperationException();
		}

		public String getString(String encoding) {
			throw new UnsupportedOperationException();
		}

		public boolean isInMemory() {
			return false;
		}

	}
}
