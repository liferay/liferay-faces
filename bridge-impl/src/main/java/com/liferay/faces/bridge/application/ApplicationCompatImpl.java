/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.application;

import javax.faces.application.Application;
import javax.faces.application.ApplicationWrapper;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import com.liferay.faces.bridge.component.UIViewRootBridgeImpl;
import com.liferay.faces.bridge.component.icefaces.DataPaginator;
import com.liferay.faces.bridge.component.icefaces.DataPaginatorBridgeImpl;
import com.liferay.faces.bridge.config.ConfiguredSystemEventListener;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class ApplicationCompatImpl extends ApplicationWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ApplicationCompatImpl.class);

	// Private Data Members
	private Application wrappedApplication;

	public ApplicationCompatImpl(Application application) {
		this.wrappedApplication = application;
	}

	@Override
	public UIComponent createComponent(FacesContext facesContext, String componentType, String rendererType) {

		UIComponent wrappedUIComponent = wrappedApplication.createComponent(facesContext, componentType, rendererType);

		if (componentType.equals(DataPaginator.COMPONENT_TYPE)) {

			// Workaround for: http://jira.icesoft.org/browse/ICE-6398
			DataPaginator dataPaginator = new DataPaginatorBridgeImpl(wrappedUIComponent);

			try {
				dataPaginator.setUIData(dataPaginator.findUIData(facesContext));
				wrappedUIComponent = dataPaginator;
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return wrappedUIComponent;
	}

	protected void subscribeToJSF2SystemEvent(ConfiguredSystemEventListener configuredSystemEventListener) {

		try {
			@SuppressWarnings("unchecked")
			Class<? extends SystemEvent> systemEventClass = (Class<? extends SystemEvent>) Class.forName(
					configuredSystemEventListener.getSystemEventClass());
			@SuppressWarnings("unchecked")
			Class<? extends SystemEventListener> systemEventListenerClass = (Class<? extends SystemEventListener>) Class
				.forName(configuredSystemEventListener.getSystemEventListenerClass());
			SystemEventListener systemEventListener = (SystemEventListener) systemEventListenerClass.newInstance();

			logger.debug("Subscribing UIViewRootBridgeImpl for systemEventClass=[{0}] systemEventListener=[{1}]",
				systemEventClass, systemEventListener);
			subscribeToEvent(systemEventClass, UIViewRootBridgeImpl.class, systemEventListener);
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * @see    {@link Application#getResourceHandler()}
	 * @since  JSF 2.0
	 */
	@Override
	public ResourceHandler getResourceHandler() {

		ResourceHandler resourceHandler = super.getResourceHandler();

		if (resourceHandler != null) {
			resourceHandler = new ResourceHandlerOuterImpl(resourceHandler);
		}

		return resourceHandler;
	}

	/**
	 * @see  ApplicationWrapper#getWrapped()
	 */
	@Override
	public Application getWrapped() {
		return wrappedApplication;
	}
}
