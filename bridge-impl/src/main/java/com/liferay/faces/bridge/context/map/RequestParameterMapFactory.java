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

import com.liferay.faces.bridge.context.BridgeContext;


/**
 * @author  Neil Griffin
 */
public class RequestParameterMapFactory {

	// Private Constants
	private static final String MULTIPART_CONTENT_TYPE_PREFIX = "multipart/";
	private static final String ICEFACES_FILE_ENTRY_FQCN = "org.icefaces.ace.component.fileentry.FileEntry";

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
			if ((contentType != null) && contentType.toLowerCase().startsWith(MULTIPART_CONTENT_TYPE_PREFIX) &&
					!isICEfacesPresent()) {
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

	protected boolean isICEfacesPresent() {

		boolean iceFacesPresent = Boolean.TRUE;

		try {
			Class.forName(ICEFACES_FILE_ENTRY_FQCN);
		}
		catch (ClassNotFoundException e) {
			iceFacesPresent = Boolean.FALSE;
		}

		return iceFacesPresent;
	}
}
