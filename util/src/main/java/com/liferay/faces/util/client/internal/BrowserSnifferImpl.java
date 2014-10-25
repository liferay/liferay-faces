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
package com.liferay.faces.util.client.internal;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.portal.PortalUtil;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class BrowserSnifferImpl extends LiferayPortalBrowserSnifferImpl implements BrowserSniffer {

	// Private Constants
	private static final boolean LIFERAY_FACES_BRIDGE_DETECTED = ProductMap.getInstance().get(
			ProductConstants.LIFERAY_FACES_BRIDGE).isDetected();
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	// Private Data Members
	private HttpServletRequest httpServletRequest;

	public BrowserSnifferImpl(ExternalContext externalContext) {

		if (LIFERAY_PORTAL_DETECTED) {

			this.httpServletRequest = PortalUtil.getHttpServeletRequest(externalContext);
		}
		else if (LIFERAY_FACES_BRIDGE_DETECTED) {
			// no-op because there is no way to obtain the underlying HttpServletRequest.
		}
		else {
			this.httpServletRequest = (HttpServletRequest) externalContext.getRequest();
		}
	}

	// Java 1.6+: @Override
	public boolean acceptsGzip() {
		return acceptsGzip(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isIeOnWin32() {
		return isIeOnWin32(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isIeOnWin64() {
		return isIeOnWin64(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isMozilla() {
		return isMozilla(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isOpera() {
		return isOpera(httpServletRequest);
	}

	// Java 1.6+: @Override
	public String getBrowserId() {
		return getBrowserId(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isMac() {
		return isMac(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isAndroid() {
		return isAndroid(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isChrome() {
		return isChrome(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isIe() {
		return isIe(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isIphone() {
		return isIphone(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isMobile() {
		return isMobile(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isRtf() {
		return isRtf(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isSafari() {
		return isSafari(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isWapXhtml() {
		return isWapXhtml(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isWml() {
		return isWml(httpServletRequest);
	}

	// Java 1.6+: @Override
	public float getMajorVersion() {
		return getMajorVersion(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isSun() {
		return isSun(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isGecko() {
		return isGecko(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isWap() {
		return isWap(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isAir() {
		return isAir(httpServletRequest);
	}

	// Java 1.6+: @Override
	public String getRevision() {
		return getRevision(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isWindows() {
		return isWindows(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isWebKit() {
		return isWebKit(httpServletRequest);
	}

	// Java 1.6+: @Override
	public String getVersion() {
		return getVersion(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isFirefox() {
		return isFirefox(httpServletRequest);
	}

	// Java 1.6+: @Override
	public boolean isLinux() {
		return isLinux(httpServletRequest);
	}
}
