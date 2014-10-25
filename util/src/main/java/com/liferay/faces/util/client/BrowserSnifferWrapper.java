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
package com.liferay.faces.util.client;

import javax.faces.FacesWrapper;


/**
 * @author  Neil Griffin
 */
public abstract class BrowserSnifferWrapper implements BrowserSniffer, FacesWrapper<BrowserSniffer> {

	// Java 1.6+: @Override
	public boolean acceptsGzip() {
		return getWrapped().acceptsGzip();
	}

	// Java 1.6+: @Override
	public boolean isIeOnWin32() {
		return getWrapped().isIeOnWin32();
	}

	// Java 1.6+: @Override
	public boolean isIeOnWin64() {
		return getWrapped().isIeOnWin64();
	}

	// Java 1.6+: @Override
	public boolean isMozilla() {
		return getWrapped().isMozilla();
	}

	// Java 1.6+: @Override
	public boolean isOpera() {
		return getWrapped().isOpera();
	}

	// Java 1.6+: @Override
	public String getBrowserId() {
		return getWrapped().getBrowserId();
	}

	// Java 1.6+: @Override
	public boolean isMac() {
		return getWrapped().isMac();
	}

	// Java 1.6+: @Override
	public boolean isAndroid() {
		return getWrapped().isAndroid();
	}

	// Java 1.6+: @Override
	public boolean isChrome() {
		return getWrapped().isChrome();
	}

	// Java 1.6+: @Override
	public boolean isIe() {
		return getWrapped().isIe();
	}

	// Java 1.6+: @Override
	public boolean isIphone() {
		return getWrapped().isIphone();
	}

	// Java 1.6+: @Override
	public boolean isMobile() {
		return getWrapped().isMobile();
	}

	// Java 1.6+: @Override
	public boolean isRtf() {
		return getWrapped().isRtf();
	}

	// Java 1.6+: @Override
	public boolean isSafari() {
		return getWrapped().isSafari();
	}

	// Java 1.6+: @Override
	public boolean isWapXhtml() {
		return getWrapped().isWapXhtml();
	}

	// Java 1.6+: @Override
	public boolean isWml() {
		return getWrapped().isWml();
	}

	// Java 1.6+: @Override
	public float getMajorVersion() {
		return getWrapped().getMajorVersion();
	}

	// Java 1.6+: @Override
	public boolean isSun() {
		return getWrapped().isSun();
	}

	// Java 1.6+: @Override
	public boolean isGecko() {
		return getWrapped().isGecko();
	}

	// Java 1.6+: @Override
	public boolean isWap() {
		return getWrapped().isWap();
	}

	// Java 1.6+: @Override
	public boolean isAir() {
		return getWrapped().isAir();
	}

	// Java 1.6+: @Override
	public String getRevision() {
		return getWrapped().getRevision();
	}

	// Java 1.6+: @Override
	public boolean isWindows() {
		return getWrapped().isWindows();
	}

	// Java 1.6+: @Override
	public boolean isWebKit() {
		return getWrapped().isWebKit();
	}

	// Java 1.6+: @Override
	public String getVersion() {
		return getWrapped().getVersion();
	}

	// Java 1.6+: @Override
	public abstract BrowserSniffer getWrapped();

	// Java 1.6+: @Override
	public boolean isFirefox() {
		return getWrapped().isFirefox();
	}

	// Java 1.6+: @Override
	public boolean isLinux() {
		return getWrapped().isLinux();
	}

}
