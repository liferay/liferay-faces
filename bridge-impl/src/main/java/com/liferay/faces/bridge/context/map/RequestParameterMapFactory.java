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
package com.liferay.faces.bridge.context.map;

import javax.portlet.ClientDataRequest;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.config.Product;
import com.liferay.faces.bridge.context.BridgeContext;


/**
 * @author  Neil Griffin
 */
public class RequestParameterMapFactory {

	// Private Data Members
	private RequestParameterMap requestParameterMap;
	private RequestParameterValuesMap requestParameterValuesMap;

	public RequestParameterMapFactory(BridgeContext bridgeContext) {

		PortletRequest portletRequest = bridgeContext.getPortletRequest();

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;
			String contentType = clientDataRequest.getContentType();

			// Note that ICEfaces ace:fileEntry cannot rely on RequestParameterValuesMapImpl because it relies on its
			// own mechanism for handling file upload.
			Product iceFaces = bridgeContext.getBridgeConfig().getProducts().get(BridgeConstants.ICEFACES);
			if ((contentType != null) && contentType.toLowerCase().startsWith(BridgeConstants.MULTIPART_CONTENT_TYPE_PREFIX) &&
					!iceFaces.isDetected()) {
				RequestParameterMapMultiPartImpl requestParameterMapMultiPartImpl =
					new RequestParameterMapMultiPartImpl(bridgeContext, clientDataRequest);
				requestParameterMap = requestParameterMapMultiPartImpl;
				requestParameterValuesMap = new RequestParameterValuesMapMultiPartImpl(
						requestParameterMapMultiPartImpl);
			}
			else {
				requestParameterMap = new RequestParameterMapImpl(bridgeContext);
				requestParameterValuesMap = new RequestParameterValuesMapImpl(bridgeContext);
			}
		}
		else {
			requestParameterMap = new RequestParameterMapImpl(bridgeContext);
			requestParameterValuesMap = new RequestParameterValuesMapImpl(bridgeContext);
		}
	}

	public RequestParameterMap getRequestParameterMap() {
		return requestParameterMap;
	}

	public RequestParameterValuesMap getRequestParameterValuesMap() {
		return requestParameterValuesMap;
	}
}
