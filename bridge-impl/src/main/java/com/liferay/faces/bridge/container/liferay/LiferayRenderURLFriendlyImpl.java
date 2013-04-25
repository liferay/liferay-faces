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
package com.liferay.faces.bridge.container.liferay;

import javax.portlet.PortletURL;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public class LiferayRenderURLFriendlyImpl extends LiferayPortletURLFriendlyImpl implements LiferayRenderURL,
	Wrapper<PortletURL> {

	// Private Data Members
	private String responseNamespace;
	private PortletURL wrappedLiferayPortletURL;

	public LiferayRenderURLFriendlyImpl(PortletURL liferayPortletURL, String responseNamespace) {

		this.wrappedLiferayPortletURL = liferayPortletURL;
		this.responseNamespace = responseNamespace;
	}

	@Override
	protected LiferayURLGenerator getLiferayURLGenerator() {

		PortletURL renderURL = getWrapped();

		return new LiferayURLGeneratorRenderImpl(renderURL.toString(), renderURL.getPortletMode(), responseNamespace,
				renderURL.getWindowState());
	}

	@Override
	public PortletURL getWrapped() {
		return wrappedLiferayPortletURL;
	}

}
