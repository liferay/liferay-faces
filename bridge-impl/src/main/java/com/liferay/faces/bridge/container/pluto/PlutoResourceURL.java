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
package com.liferay.faces.bridge.container.pluto;

import java.util.Map;

import javax.portlet.ResourceURL;

import com.liferay.faces.bridge.container.ResourceURLWrapper;


/**
 * @author  Neil Griffin
 */
public class PlutoResourceURL extends ResourceURLWrapper {

	// Private Constants
	private static final String PLUTO_CACHE_LEVEL_PAGE_TOKEN = "/__clcacheLevelPage";

	// Protected Data Members
	protected String toStringValue;

	public PlutoResourceURL(ResourceURL resourceURL) {
		super(resourceURL);
	}

	/**
	 * Pluto has the habit of adding cache tokens to URLs during the RESOURCE_PHASE of the portlet lifecycle that are
	 * not present during the RENDER_PHASE. Although it would be nice to take advantage of cache-ability of resources,
	 * these differing URLs cause full-portlet DOM-diffs to take place when using ICEfaces. This method ensures that the
	 * cache tokens are removed, so that the return value of this method is the same during the RENDER_PHASE and
	 * RESOURCE_PHASE.
	 */
	@Override
	public String toString() {

		if (toStringValue == null) {

			// Invoke Pluto's toString() method.
			toStringValue = getWrapped().toString();

			// If cache tokens are found in the URL, then remove them.
			if (toStringValue != null) {
				int pos = toStringValue.indexOf(PLUTO_CACHE_LEVEL_PAGE_TOKEN);

				if (pos > 0) {
					toStringValue = toStringValue.substring(0, pos) +
						toStringValue.substring(pos + PLUTO_CACHE_LEVEL_PAGE_TOKEN.length());
				}
			}
		}

		// Return the normalized URL.
		return toStringValue;
	}

	@Override
	public void setParameter(String name, String[] values) {
		super.setParameter(name, values);
	}

	@Override
	public void setParameter(String name, String value) {
		super.setParameter(name, value);
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		super.setParameters(parameters);
	}

}
