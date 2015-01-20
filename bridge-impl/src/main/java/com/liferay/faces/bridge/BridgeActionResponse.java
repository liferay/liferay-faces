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

package com.liferay.faces.bridge;

import java.io.IOException;

import javax.portlet.ActionResponse;
import javax.portlet.filter.ActionResponseWrapper;

import com.liferay.faces.bridge.scope.BridgeRequestScope;

/**
 * Encapsulates the portlet action response to handle bridge specific calls.
 * 
 * @author Tobias Liefke
 */
public class BridgeActionResponse extends ActionResponseWrapper {

	private BridgeRequestScope requestScope;

	public BridgeActionResponse(ActionResponse response) {

		super(response);
	}

	public BridgeRequestScope getRequestScope() {

		return requestScope;
	}

	public void setRequestScope(BridgeRequestScope requestScope) {

		this.requestScope = requestScope;
	}

	@Override
	public void sendRedirect(String location) throws IOException {

		super.sendRedirect(location);
		requestScope.setRedirectOccurred(true);
	}

	@Override
	public void sendRedirect(String location, String renderUrlParamName) throws IOException {

		super.sendRedirect(location, renderUrlParamName);
		requestScope.setRedirectOccurred(true);
	}

}
