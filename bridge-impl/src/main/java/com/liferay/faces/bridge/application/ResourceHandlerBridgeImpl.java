/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.application.ResourceHandlerWrapperBase;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ResourceHandlerBridgeImpl extends ResourceHandlerWrapperBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceHandlerBridgeImpl.class);

	// Private Data Members
	private Integer bufferSize;

	public ResourceHandlerBridgeImpl(ResourceHandler resourceHandler) {
		super(resourceHandler);
	}

	@Override
	public Resource createResource(String resourceName) {
		Resource wrappableResource = getWrapped().createResource(resourceName);

		if (wrappableResource == null) {
			return new MissingResourceImpl(getWrapped(), resourceName);
		}
		else {
			return new ResourceImpl(wrappableResource);
		}
	}

	@Override
	public Resource createResource(String resourceName, String libraryName) {
		Resource wrappableResource = getWrapped().createResource(resourceName, libraryName);

		if (wrappableResource == null) {
			return new MissingResourceImpl(getWrapped(), resourceName, libraryName);
		}
		else {
			return new ResourceImpl(wrappableResource);
		}
	}

	@Override
	public Resource createResource(String resourceName, String libraryName, String contentType) {
		Resource wrappableResource = getWrapped().createResource(resourceName, libraryName, contentType);

		if (wrappableResource == null) {
			return new MissingResourceImpl(getWrapped(), resourceName, libraryName, contentType);
		}
		else {
			return new ResourceImpl(wrappableResource);
		}
	}

	/**
	 * Gets the size of the buffer (in bytes) that is to be used when loading contents of resources that are to be sent
	 * back via {@link ExternalContext#getResponseOutputStream()}. The default value is 1024 (1 kilobyte).
	 */
	@Override
	public int getBufferSize(FacesContext facesContext) {

		if (bufferSize == null) {

			bufferSize = DEFAULT_BUFFER_SIZE;

			String constantName = BridgeConfigConstants.PARAM_RESOURCE_BUFFER_SIZE1;
			ExternalContext externalContext = facesContext.getExternalContext();
			String sizeAsString = externalContext.getInitParameter(constantName);

			if (sizeAsString == null) {

				// Backward compatibility
				constantName = BridgeConfigConstants.PARAM_RESOURCE_BUFFER_SIZE2;
				sizeAsString = externalContext.getInitParameter(constantName);
			}

			if (sizeAsString != null) {

				try {
					bufferSize = Integer.parseInt(sizeAsString);
					logger.debug("Found portlet.xml init-param name=[{0}] value=[{1}]", constantName, bufferSize);
				}
				catch (NumberFormatException e) {
					logger.error("Invalid value=[{0}] for portlet.xml init-param {1}", sizeAsString, constantName);
				}
			}
			else {
				logger.debug("Returning default portletbufferSize=[{0}]", bufferSize);
			}
		}

		return bufferSize;
	}

	@Override
	public boolean isAbleToSetHttpStatusCode(FacesContext facesContext) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		PortletContainer portletContainer = bridgeContext.getPortletContainer();

		return portletContainer.isAbleToSetHttpStatusCode();
	}
}
