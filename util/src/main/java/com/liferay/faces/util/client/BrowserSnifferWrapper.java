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

	// Java 6: @Override
	public boolean acceptsGzip() {
		return getWrapped().acceptsGzip();
	}

	// Java 6: @Override
	public boolean isIeOnWin32() {
		return getWrapped().isIeOnWin32();
	}

	// Java 6: @Override
	public boolean isIeOnWin64() {
		return getWrapped().isIeOnWin64();
	}

	// Java 6: @Override
	public boolean isMozilla() {
		return getWrapped().isMozilla();
	}

	// Java 6: @Override
	public boolean isOpera() {
		return getWrapped().isOpera();
	}

	// Java 6: @Override
	public String getBrowserId() {
		return getWrapped().getBrowserId();
	}

	// Java 6: @Override
	public boolean isMac() {
		return getWrapped().isMac();
	}

	// Java 6: @Override
	public boolean isAndroid() {
		return getWrapped().isAndroid();
	}

	// Java 6: @Override
	public boolean isChrome() {
		return getWrapped().isChrome();
	}

	// Java 6: @Override
	public boolean isIe() {
		return getWrapped().isIe();
	}

	// Java 6: @Override
	public boolean isIphone() {
		return getWrapped().isIphone();
	}

	// Java 6: @Override
	public boolean isMobile() {
		return getWrapped().isMobile();
	}

	// Java 6: @Override
	public boolean isRtf() {
		return getWrapped().isRtf();
	}

	// Java 6: @Override
	public boolean isSafari() {
		return getWrapped().isSafari();
	}

	// Java 6: @Override
	public boolean isWapXhtml() {
		return getWrapped().isWapXhtml();
	}

	// Java 6: @Override
	public boolean isWml() {
		return getWrapped().isWml();
	}

	// Java 6: @Override
	public float getMajorVersion() {
		return getWrapped().getMajorVersion();
	}

	// Java 6: @Override
	public boolean isSun() {
		return getWrapped().isSun();
	}

	// Java 6: @Override
	public boolean isGecko() {
		return getWrapped().isGecko();
	}

	// Java 6: @Override
	public boolean isWap() {
		return getWrapped().isWap();
	}

	// Java 6: @Override
	public boolean isAir() {
		return getWrapped().isAir();
	}

	// Java 6: @Override
	public String getRevision() {
		return getWrapped().getRevision();
	}

	// Java 6: @Override
	public boolean isWindows() {
		return getWrapped().isWindows();
	}

	// Java 6: @Override
	public boolean isWebKit() {
		return getWrapped().isWebKit();
	}

	// Java 6: @Override
	public String getVersion() {
		return getWrapped().getVersion();
	}

	// Java 6: @Override
	public abstract BrowserSniffer getWrapped();

	// Java 6: @Override
	public boolean isFirefox() {
		return getWrapped().isFirefox();
	}

	// Java 6: @Override
	public boolean isLinux() {
		return getWrapped().isLinux();
	}

}
