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

import java.util.List;
import java.util.Map;

import javax.faces.application.NavigationCase;
import javax.faces.application.NavigationCaseWrapper;
import javax.faces.context.FacesContext;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.util.URLUtil;


/**
 * @author  Neil Griffin
 */
public class BridgeNavigationCaseImpl extends NavigationCaseWrapper implements BridgeNavigationCase {

	// Private Data Members
	private Map<String, List<String>> parameters;
	private NavigationCase wrappedNavigationCase;

	public BridgeNavigationCaseImpl(NavigationCase navigationCase) {
		this.wrappedNavigationCase = navigationCase;
	}

	protected String getParameter(String parameterName) {

		String parameter = null;

		Map<String, List<String>> parameterMap = getParameters();

		if (parameterMap != null) {
			List<String> values = parameterMap.get(parameterName);

			if ((values != null) && (values.size() > 0)) {
				parameter = values.get(0);
			}
		}

		return parameter;
	}

	@Override
	public Map<String, List<String>> getParameters() {

		if (parameters == null) {
			parameters = super.getParameters();

			if (parameters == null) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				parameters = URLUtil.parseParameterMapValuesList(wrappedNavigationCase.getToViewId(facesContext));
			}
		}

		return parameters;
	}

	public String getPortletMode() {
		return getParameter(Bridge.PORTLET_MODE_PARAMETER);
	}

	public String getWindowState() {
		return getParameter(Bridge.PORTLET_WINDOWSTATE_PARAMETER);
	}

	@Override
	public NavigationCase getWrapped() {
		return wrappedNavigationCase;
	}

}
