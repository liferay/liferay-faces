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
import javax.portlet.PortletConfig;

import com.liferay.faces.bridge.config.PortletConfigParam;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.application.ResourceHandlerWrapperBase;


/**
 * @author  Neil Griffin
 */
public class ResourceHandlerBridgeImpl extends ResourceHandlerWrapperBase {

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

			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			PortletConfig portletConfig = bridgeContext.getPortletConfig();
			bufferSize = PortletConfigParam.ResourceBufferSize.getIntegerValue(portletConfig);
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
