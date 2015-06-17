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
package com.liferay.faces.portal.component.header;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlPanelGroup;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class HeaderBase extends HtmlPanelGroup implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.header.Header";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.header.HeaderRenderer";

	// Protected Enumerations
	protected enum HeaderPropertyKeys {
		backLabel,
		backURL,
		escapeXml,
		showBackURL,
		style,
		styleClass,
		title
	}

	public HeaderBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getBackLabel() {
		return (String) getStateHelper().eval(HeaderPropertyKeys.backLabel, null);
	}

	public void setBackLabel(String backLabel) {
		getStateHelper().put(HeaderPropertyKeys.backLabel, backLabel);
	}

	public String getBackURL() {
		return (String) getStateHelper().eval(HeaderPropertyKeys.backURL, null);
	}

	public void setBackURL(String backURL) {
		getStateHelper().put(HeaderPropertyKeys.backURL, backURL);
	}

	public boolean isEscapeXml() {
		return (Boolean) getStateHelper().eval(HeaderPropertyKeys.escapeXml, true);
	}

	public void setEscapeXml(boolean escapeXml) {
		getStateHelper().put(HeaderPropertyKeys.escapeXml, escapeXml);
	}

	public boolean isShowBackURL() {
		return (Boolean) getStateHelper().eval(HeaderPropertyKeys.showBackURL, true);
	}

	public void setShowBackURL(boolean showBackURL) {
		getStateHelper().put(HeaderPropertyKeys.showBackURL, showBackURL);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(HeaderPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(HeaderPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(HeaderPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(HeaderPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-header");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(HeaderPropertyKeys.styleClass, styleClass);
	}

	public String getTitle() {
		return (String) getStateHelper().eval(HeaderPropertyKeys.title, null);
	}

	public void setTitle(String title) {
		getStateHelper().put(HeaderPropertyKeys.title, title);
	}
}
//J+
