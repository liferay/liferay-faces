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
package com.liferay.faces.alloy.component.outputtooltip;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = OutputTooltip.COMPONENT_TYPE)
public class OutputTooltip extends OutputTooltipBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.outputtooltip.OutputTooltip";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.outputtooltip.OutputTooltipRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-output-tooltip";

	public OutputTooltip() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public Integer getzIndex() {
		return (Integer) getStateHelper().eval(Z_INDEX, Integer.MIN_VALUE);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(CLIENT_KEY, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(CLIENT_KEY, clientKey);
	}

	@Override
	public String getCssClass() {
		String cssClass = super.getCssClass();

		if (cssClass == null) {
			cssClass = getStyleClass();
		}

		return cssClass;
	}

	@Override
	public boolean isEscape() {
		Boolean escape = super.isEscape();

		if (escape == null) {
			escape = true;
		}

		return escape;
	}

	// Returns the clientId of the component that the "for" attribute points to.
	protected String getForClientId(FacesContext facesContext) {

		String forClientId = null;

		String forComponent = getFor();

		if (forComponent != null) {
			UIComponent uiComponent = findComponent(forComponent);

			if (uiComponent == null) {
				forClientId = forComponent;
			}
			else {
				forClientId = uiComponent.getClientId(facesContext);
			}
		}

		return forClientId;
	}

	public String getForClientIdEscaped(FacesContext facesContext) {
		return StringPool.POUND + ComponentUtil.escapeClientId(getForClientId(facesContext));
	}

	@Override
	public String getPosition() {
		String position = super.getPosition();

		if (position == null) {
			position = "right";
		}

		return position;
	}

	@Override
	public String getStyle() {
		String style = super.getStyle();

		// do not blink
		if (style == null) {
			style = AlloyRendererUtil.DISPLAY_NONE;
		}
		else {
			style = style + StringPool.SEMICOLON + AlloyRendererUtil.DISPLAY_NONE;
		}

		return style;
	}

	@Override
	public String getStyleClass() {

		String styleClass = (String) getStateHelper().eval(STYLE_CLASS, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}

	@Override
	public Boolean isAutoShow() {
		Boolean autoShow = super.isAutoShow();

		if (autoShow == null) {
			autoShow = false;
		}

		return autoShow;
	}

}
