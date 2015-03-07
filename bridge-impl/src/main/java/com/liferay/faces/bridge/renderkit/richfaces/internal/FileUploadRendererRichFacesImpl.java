/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.renderkit.richfaces.internal;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.faces.render.Renderer;
import javax.faces.render.RendererWrapper;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.map.internal.ContextMapFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.UploadedFile;


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
	 * This method overrides the {@link RendererWrapper#decode(FacesContext, UIComponent)} method so that it can avoid a
	 * Servlet-API dependency in the RichFaces FileUploadRenderer. Note that rich:fileUpload will do an Ajax postback
	 * and invoke the JSF lifecycle for each individual file.
	 */
	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		try {

			// Get the UploadedFile from the request attribute map.
			ContextMapFactory contextMapFactory = (ContextMapFactory) BridgeFactoryFinder.getFactory(
					ContextMapFactory.class);
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			Map<String, List<UploadedFile>> uploadedFileMap = contextMapFactory.getUploadedFileMap(bridgeContext);

			if (uploadedFileMap != null) {

				// Use reflection to create a dynamic proxy class that implements the RichFaces UploadedFile interface.
				Class<?> uploadedFileInterface = Class.forName(RICHFACES_UPLOADED_FILE_FQCN);
				Class<?> fileUploadEventClass = Class.forName(RICHFACES_FILE_UPLOAD_EVENT_FQCN);
				ClassLoader classLoader = uploadedFileInterface.getClassLoader();

				String clientId = uiComponent.getClientId(facesContext);
				List<UploadedFile> uploadedFiles = uploadedFileMap.get(clientId);

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
}
