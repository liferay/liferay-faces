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
package com.liferay.faces.bridge.container.liferay;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;


/**
 * @author  Neil Griffin
 */
public abstract class LiferayPortletURLImpl extends LiferayBaseURLImpl implements LiferayPortletURL {

	// Private Data Members
	private PortletMode portletMode;
	private String toStringValue;
	private WindowState windowState;

	public LiferayPortletURLImpl(LiferayURLGenerator liferayURLGenerator) {
		super(liferayURLGenerator);
	}

	public void removePublicRenderParameter(String name) {
		// no-op
	}

	@Override
	public String toString() {

		if (toStringValue == null) {
			toStringValue = getLiferayURLGenerator().generateURL(getParameterMap(), portletMode, windowState);
		}

		return toStringValue;
	}

	protected void resetToString() {
		this.toStringValue = null;
	}

	public PortletMode getPortletMode() {
		return portletMode;
	}

	public void setPortletMode(PortletMode portletMode) throws PortletModeException {
		this.portletMode = portletMode;
		resetToString();
	}

	public WindowState getWindowState() {
		return windowState;
	}

	public void setWindowState(WindowState windowState) throws WindowStateException {
		this.windowState = windowState;
		resetToString();
	}

}
