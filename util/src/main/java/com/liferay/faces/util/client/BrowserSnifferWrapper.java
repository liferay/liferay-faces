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
package com.liferay.faces.util.client;

import javax.faces.FacesWrapper;


/**
 * @author  Neil Griffin
 */
public abstract class BrowserSnifferWrapper implements BrowserSniffer, FacesWrapper<BrowserSniffer> {

	@Override
	public boolean acceptsGzip() {
		return getWrapped().acceptsGzip();
	}

	@Override
	public boolean isIeOnWin32() {
		return getWrapped().isIeOnWin32();
	}

	@Override
	public boolean isIeOnWin64() {
		return getWrapped().isIeOnWin64();
	}

	@Override
	public boolean isMozilla() {
		return getWrapped().isMozilla();
	}

	@Override
	public boolean isOpera() {
		return getWrapped().isOpera();
	}

	@Override
	public String getBrowserId() {
		return getWrapped().getBrowserId();
	}

	@Override
	public boolean isMac() {
		return getWrapped().isMac();
	}

	@Override
	public boolean isAndroid() {
		return getWrapped().isAndroid();
	}

	@Override
	public boolean isChrome() {
		return getWrapped().isChrome();
	}

	@Override
	public boolean isIe() {
		return getWrapped().isIe();
	}

	@Override
	public boolean isIphone() {
		return getWrapped().isIphone();
	}

	@Override
	public boolean isMobile() {
		return getWrapped().isMobile();
	}

	@Override
	public boolean isRtf() {
		return getWrapped().isRtf();
	}

	@Override
	public boolean isSafari() {
		return getWrapped().isSafari();
	}

	@Override
	public boolean isWapXhtml() {
		return getWrapped().isWapXhtml();
	}

	@Override
	public boolean isWml() {
		return getWrapped().isWml();
	}

	@Override
	public float getMajorVersion() {
		return getWrapped().getMajorVersion();
	}

	@Override
	public boolean isSun() {
		return getWrapped().isSun();
	}

	@Override
	public boolean isGecko() {
		return getWrapped().isGecko();
	}

	@Override
	public boolean isWap() {
		return getWrapped().isWap();
	}

	@Override
	public boolean isAir() {
		return getWrapped().isAir();
	}

	@Override
	public String getRevision() {
		return getWrapped().getRevision();
	}

	@Override
	public boolean isWindows() {
		return getWrapped().isWindows();
	}

	@Override
	public boolean isWebKit() {
		return getWrapped().isWebKit();
	}

	@Override
	public String getVersion() {
		return getWrapped().getVersion();
	}

	@Override
	public abstract BrowserSniffer getWrapped();

	@Override
	public boolean isFirefox() {
		return getWrapped().isFirefox();
	}

	@Override
	public boolean isLinux() {
		return getWrapped().isLinux();
	}

}
