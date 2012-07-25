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
package com.liferay.faces.bridge.application.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.portlet.ResourceRequest;


/**
 * @author  Neil Griffin
 */
public class BridgeAfterViewContentRequestResourceImpl extends BridgeAfterViewContentRequest
	implements ResourceRequest {

	// Private Data Members
	private ResourceRequest wrappedResourceRequest;

	public BridgeAfterViewContentRequestResourceImpl(ResourceRequest resourceRequest) {
		super(resourceRequest);
		this.wrappedResourceRequest = resourceRequest;
	}

	public String getCacheability() {
		return wrappedResourceRequest.getCacheability();
	}

	public String getETag() {
		return wrappedResourceRequest.getETag();
	}

	public InputStream getPortletInputStream() throws IOException {
		return wrappedResourceRequest.getPortletInputStream();
	}

	public Map<String, String[]> getPrivateRenderParameterMap() {
		return wrappedResourceRequest.getPrivateParameterMap();
	}

	public String getResourceID() {
		return wrappedResourceRequest.getResourceID();
	}
}
