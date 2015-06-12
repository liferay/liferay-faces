/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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

import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.client.BrowserSniffer;


/**
 * @author  Neil Griffin
 */
public class BrowserSnifferImpl extends LiferayPortalBrowserSnifferImpl implements BrowserSniffer {

	// Private Data Members
	private HttpServletRequest httpServletRequest;

	public BrowserSnifferImpl(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	@Override
	public boolean acceptsGzip() {
		return acceptsGzip(httpServletRequest);
	}

	@Override
	public boolean isIeOnWin32() {
		return isIeOnWin32(httpServletRequest);
	}

	@Override
	public boolean isIeOnWin64() {
		return isIeOnWin64(httpServletRequest);
	}

	@Override
	public boolean isMozilla() {
		return isMozilla(httpServletRequest);
	}

	@Override
	public boolean isOpera() {
		return isOpera(httpServletRequest);
	}

	@Override
	public String getBrowserId() {
		return getBrowserId(httpServletRequest);
	}

	@Override
	public boolean isMac() {
		return isMac(httpServletRequest);
	}

	@Override
	public boolean isAndroid() {
		return isAndroid(httpServletRequest);
	}

	@Override
	public boolean isIpad() {
		String userAgent = getUserAgent(httpServletRequest);

		if (userAgent.contains("ipad")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isChrome() {
		return isChrome(httpServletRequest);
	}

	@Override
	public boolean isIe() {
		return isIe(httpServletRequest);
	}

	@Override
	public boolean isIphone() {
		return isIphone(httpServletRequest);
	}

	@Override
	public boolean isMobile() {
		return isMobile(httpServletRequest);
	}

	@Override
	public boolean isRtf() {
		return isRtf(httpServletRequest);
	}

	@Override
	public boolean isSafari() {
		return isSafari(httpServletRequest);
	}

	@Override
	public boolean isWapXhtml() {
		return isWapXhtml(httpServletRequest);
	}

	@Override
	public boolean isWml() {
		return isWml(httpServletRequest);
	}

	@Override
	public float getMajorVersion() {
		return getMajorVersion(httpServletRequest);
	}

	@Override
	public boolean isSun() {
		return isSun(httpServletRequest);
	}

	@Override
	public boolean isGecko() {
		return isGecko(httpServletRequest);
	}

	@Override
	public boolean isWap() {
		return isWap(httpServletRequest);
	}

	@Override
	public boolean isAir() {
		return isAir(httpServletRequest);
	}

	@Override
	public String getRevision() {
		return getRevision(httpServletRequest);
	}

	@Override
	public boolean isWindows() {
		return isWindows(httpServletRequest);
	}

	@Override
	public boolean isWebKit() {
		return isWebKit(httpServletRequest);
	}

	@Override
	public String getVersion() {
		return getVersion(httpServletRequest);
	}

	@Override
	public boolean isFirefox() {
		return isFirefox(httpServletRequest);
	}

	@Override
	public boolean isLinux() {
		return isLinux(httpServletRequest);
	}
}
