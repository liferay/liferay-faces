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

import javax.portlet.ActionRequest;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.context.BridgeContext;


/**
 * @author  Neil Griffin
 */
public class RequestParameterMapFactory {

	// Private Constants
	private static final String MULTIPART_CONTENT_TYPE_PREFIX = "multipart/";

	// Private Data Members
	private RequestParameterMap requestParameterMap;
	private RequestParameterValuesMap requestParameterValuesMap;

	public RequestParameterMapFactory(BridgeContext bridgeContext) {

		if (bridgeContext.getPortletRequestPhase() == Bridge.PortletPhase.ACTION_PHASE) {
			ActionRequest actionRequest = (ActionRequest) bridgeContext.getPortletRequest();
			String contentType = actionRequest.getContentType();

			if ((contentType != null) && contentType.toLowerCase().startsWith(MULTIPART_CONTENT_TYPE_PREFIX)) {
				RequestParameterMapMultiPartImpl requestParameterMapMultiPartImpl =
					new RequestParameterMapMultiPartImpl(bridgeContext, actionRequest);
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
