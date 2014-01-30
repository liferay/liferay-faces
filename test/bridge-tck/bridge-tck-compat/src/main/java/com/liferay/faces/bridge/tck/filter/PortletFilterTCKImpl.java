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
package com.liferay.faces.bridge.tck.filter;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;

import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class PortletFilterTCKImpl implements RenderFilter {

	// Private Constants
	private static final boolean RESIN_DETECTED = ProductMap.getInstance().get(ProductConstants.RESIN).isDetected();

	public void destroy() {
	}

	public void doFilter(RenderRequest renderRequest, RenderResponse renderResponse, FilterChain filterChain)
		throws IOException, PortletException {

		if (RESIN_DETECTED) {

			// Workaround for FACES-1629
			renderRequest = new RenderRequestResinImpl(renderRequest);
		}

		filterChain.doFilter(renderRequest, renderResponse);
	}

	public void init(FilterConfig filterConfig) throws PortletException {
	}

}
