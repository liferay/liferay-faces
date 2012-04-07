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
package com.liferay.faces.bridge.context.url;

import java.net.URISyntaxException;

import javax.portlet.PortletRequest;

import org.junit.Test;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigMockImpl;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.BridgeContextMockImpl;
import com.liferay.faces.portlet.PortletRequestMockImpl;

import junit.framework.Assert;


/**
 * @author  Neil Griffin
 */
public class BridgeURLTest {

	// Private Constants
	private static final String CONTEXT_PATH = "/my-portlet";
	private static final String CURRENT_FACES_VIEW_ID = "/views/foo.faces";

	// Private Data Members
	private BridgeContext bridgeContext;

	public BridgeURLTest() {
		BridgeConfig bridgeConfig = new BridgeConfigMockImpl();
		PortletRequest portletRequest = new PortletRequestMockImpl(CONTEXT_PATH);
		this.bridgeContext = new BridgeContextMockImpl(bridgeConfig, portletRequest, CURRENT_FACES_VIEW_ID);
	}

	@Test
	public void testEscaped() {

		try {
			Assert.assertFalse(newBridgeURL("http://www.liferay.com/foo").isEscaped());
			Assert.assertFalse(newBridgeURL("http://www.liferay.com/foo?a=1&b=2").isEscaped());
			Assert.assertFalse(newBridgeURL("http://www.liferay.com/foo?a=1&b=2&amp;c=3").isEscaped());
			Assert.assertTrue(newBridgeURL("http://www.liferay.com/foo?a=1&amp;b=2&amp;c=3").isEscaped());
		}
		catch (URISyntaxException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testExternal() {

		try {
			Assert.assertTrue(newBridgeURL("http://www.liferay.com").isExternal());
			Assert.assertFalse(newBridgeURL(CONTEXT_PATH).isExternal());
			Assert.assertFalse(newBridgeURL("/relativeToContextPath?someurl=" + CONTEXT_PATH).isExternal());
		}
		catch (URISyntaxException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testHierarchical() {

		try {
			Assert.assertTrue(newBridgeURL("http://www.liferay.com").isHierarchical());
			Assert.assertTrue(newBridgeURL("/foo/bar.gif").isHierarchical());
			Assert.assertTrue(newBridgeURL("foo/bar.gif").isHierarchical());
			Assert.assertFalse(newBridgeURL("mailto:foo@liferay.com").isHierarchical());
			Assert.assertFalse(newBridgeURL("portlet:render").isHierarchical());
		}
		catch (URISyntaxException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testOpaque() {

		try {
			Assert.assertFalse(newBridgeURL("http://www.liferay.com").isOpaque());
			Assert.assertTrue(newBridgeURL("mailto:foo@liferay.com").isOpaque());
		}
		catch (URISyntaxException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testPathRelative() {

		try {
			Assert.assertFalse(newBridgeURL("http://www.liferay.com").isPathRelative());
			Assert.assertFalse(newBridgeURL("/foo/bar.gif").isPathRelative());
			Assert.assertTrue(newBridgeURL("foo/bar.gif").isPathRelative());
		}
		catch (URISyntaxException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testViewPath() {

		try {
			Assert.assertTrue(newBridgeURL("http://www.liferay.com").getContextRelativePath().equals(
					BridgeConstants.EMPTY));
			Assert.assertTrue(newBridgeURL("/views/foo.xhtml").getContextRelativePath().equals(
					"/views/foo.xhtml"));
			Assert.assertTrue(newBridgeURL(CONTEXT_PATH + "/views/foo.xhtml")
				.getContextRelativePath().equals("/views/foo.xhtml"));
		}
		catch (URISyntaxException e) {
			Assert.fail(e.getMessage());
		}
	}

	protected BridgeURL newBridgeURL(String url) throws URISyntaxException {
		return new BridgeURLMockImpl(url, CURRENT_FACES_VIEW_ID, bridgeContext);
	}
}
