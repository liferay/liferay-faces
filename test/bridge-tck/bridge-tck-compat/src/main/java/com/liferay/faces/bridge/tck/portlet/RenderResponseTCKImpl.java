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
package com.liferay.faces.bridge.tck.portlet;

import javax.portlet.RenderResponse;
import javax.portlet.filter.RenderResponseWrapper;


/**
 * This class is a wrapper around the {@link RenderResponse} implementation provided by the portlet container. Its
 * purpose is to provide an override of the {@link RenderResponse#setContentType(String)} method that performs a no-op.
 * This is necessary when running the TCK under Liferay Portal, since the underlying Liferay {@link
 * PortletServletResponse#setContentType(String)} method respects the JSP &lt;%@ page contentType="" ... %&gt;
 * directive.
 *
 * @author  Neil Griffin
 */
public class RenderResponseTCKImpl extends RenderResponseWrapper {

	public RenderResponseTCKImpl(RenderResponse response) {
		super(response);
	}

	@Override
	public void setContentType(String contentType) {
		// TCK TestPage017 (requestProcessingNonFacesTest) does not anticipate this being called. Therefore the
		// specified contentType value must be ignored in order for the test to pass.
	}

}
