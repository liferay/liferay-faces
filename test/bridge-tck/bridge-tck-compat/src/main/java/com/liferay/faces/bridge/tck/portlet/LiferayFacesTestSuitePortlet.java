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

import java.io.IOException;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.myfaces.portlet.faces.testsuite.common.portlet.GenericFacesTestSuitePortlet;



/**
 * @author  Neil Griffin
 */
public class LiferayFacesTestSuitePortlet extends GenericFacesTestSuitePortlet {

	@Override
	public void doDispatch(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {
		super.doDispatch(renderRequest, new RenderResponseTCKImpl(renderResponse));
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(new PortletConfigTCKImpl(portletConfig));
	}
}
