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


/**
 * @author  Neil Griffin
 */
public class LiferayActionURLFriendlyImpl extends LiferayPortletURLFriendlyImpl implements LiferayActionURL {

	// Private Data Members
	private String responseNamespace;
	private PortletURL wrappedLiferayPortletURL;

	public LiferayActionURLFriendlyImpl(PortletURL liferayPortletURL, String responseNamespace) {
		this.wrappedLiferayPortletURL = liferayPortletURL;
		this.responseNamespace = responseNamespace;
	}

	@Override
	protected LiferayURLGenerator getLiferayURLGenerator() {

		PortletURL actionURL = getWrapped();

		return new LiferayURLGeneratorActionImpl(actionURL.toString(), actionURL.getPortletMode(), responseNamespace,
				actionURL.getWindowState());
	}

	@Override
	public PortletURL getWrapped() {
		return wrappedLiferayPortletURL;
	}

}
