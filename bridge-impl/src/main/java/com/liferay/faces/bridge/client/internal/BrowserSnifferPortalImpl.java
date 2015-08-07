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
package com.liferay.faces.bridge.client.internal;

import com.liferay.faces.util.client.BrowserSniffer;


/**
 * @author  Kyle Stiemann
 */
public class BrowserSnifferPortalImpl implements BrowserSniffer {

	@Override
	public boolean acceptsGzip() {
		return false;
	}

	@Override
	public boolean isIeOnWin32() {
		return false;
	}

	@Override
	public boolean isIeOnWin64() {
		return false;
	}

	@Override
	public boolean isMozilla() {
		return false;
	}

	@Override
	public boolean isOpera() {
		return false;
	}

	@Override
	public String getBrowserId() {
		return "";
	}

	@Override
	public boolean isMac() {
		return false;
	}

	@Override
	public boolean isAndroid() {
		return false;
	}

	@Override
	public boolean isIpad() {
		return false;
	}

	@Override
	public boolean isChrome() {
		return false;
	}

	@Override
	public boolean isIe() {
		return false;
	}

	@Override
	public boolean isIphone() {
		return false;
	}

	@Override
	public boolean isMobile() {
		return false;
	}

	@Override
	public boolean isRtf() {
		return false;
	}

	@Override
	public boolean isSafari() {
		return false;
	}

	@Override
	public boolean isWapXhtml() {
		return false;
	}

	@Override
	public boolean isWml() {
		return false;
	}

	@Override
	public float getMajorVersion() {
		return 0;
	}

	@Override
	public boolean isSun() {
		return false;
	}

	@Override
	public boolean isGecko() {
		return false;
	}

	@Override
	public boolean isWap() {
		return false;
	}

	@Override
	public boolean isAir() {
		return false;
	}

	@Override
	public String getRevision() {
		return "";
	}

	@Override
	public boolean isWindows() {
		return false;
	}

	@Override
	public boolean isWebKit() {
		return false;
	}

	@Override
	public String getVersion() {
		return "";
	}

	@Override
	public boolean isFirefox() {
		return false;
	}

	@Override
	public boolean isLinux() {
		return false;
	}
}
