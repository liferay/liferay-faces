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
package com.liferay.faces.portlet.component.renderurl;
//J-

import javax.annotation.Generated;
import com.liferay.faces.portlet.component.baseurl.BaseURL;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class RenderURLBase extends BaseURL {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.renderurl.RenderURL";
	public static final String RENDERER_TYPE = "com.liferay.faces.portlet.component.renderurl.RenderURLRenderer";

	// Protected Enumerations
	protected enum RenderURLPropertyKeys {
		copyCurrentRenderParameters,
		portletMode,
		windowState
	}

	public RenderURLBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public boolean isCopyCurrentRenderParameters() {
		return (Boolean) getStateHelper().eval(RenderURLPropertyKeys.copyCurrentRenderParameters, false);
	}

	public void setCopyCurrentRenderParameters(boolean copyCurrentRenderParameters) {
		getStateHelper().put(RenderURLPropertyKeys.copyCurrentRenderParameters, copyCurrentRenderParameters);
	}

	public String getPortletMode() {
		return (String) getStateHelper().eval(RenderURLPropertyKeys.portletMode, null);
	}

	public void setPortletMode(String portletMode) {
		getStateHelper().put(RenderURLPropertyKeys.portletMode, portletMode);
	}

	public String getWindowState() {
		return (String) getStateHelper().eval(RenderURLPropertyKeys.windowState, null);
	}

	public void setWindowState(String windowState) {
		getStateHelper().put(RenderURLPropertyKeys.windowState, windowState);
	}
}
//J+
