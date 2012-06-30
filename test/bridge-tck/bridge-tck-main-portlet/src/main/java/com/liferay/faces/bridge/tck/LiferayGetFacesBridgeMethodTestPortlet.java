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
package com.liferay.faces.bridge.tck;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

import org.apache.myfaces.portlet.faces.testsuite.tests.chapter_4.section_4_2_15.GetFacesBridgeMethodTestPortlet;

import com.liferay.faces.bridge.tck.portlet.PortletConfigTCKImpl;


/**
 * @author  Neil Griffin
 */
public class LiferayGetFacesBridgeMethodTestPortlet extends GetFacesBridgeMethodTestPortlet {

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(new PortletConfigTCKImpl(portletConfig));
	}
}
