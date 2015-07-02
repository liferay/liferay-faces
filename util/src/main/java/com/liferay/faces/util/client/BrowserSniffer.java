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

/**
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author  Brian Wing Shun Chan
 */
public interface BrowserSniffer {

	public boolean acceptsGzip();

	public boolean isIeOnWin32();

	public boolean isIeOnWin64();

	public boolean isMozilla();

	public boolean isOpera();

	public String getBrowserId();

	public boolean isMac();

	public boolean isAndroid();

	public boolean isIpad();

	public boolean isChrome();

	public boolean isIe();

	public boolean isIphone();

	public boolean isMobile();

	public boolean isRtf();

	public boolean isSafari();

	public boolean isWapXhtml();

	public boolean isWml();

	public float getMajorVersion();

	public boolean isSun();

	public boolean isGecko();

	public boolean isWap();

	public boolean isAir();

	public String getRevision();

	public boolean isWindows();

	public boolean isWebKit();

	public String getVersion();

	public boolean isFirefox();

	public boolean isLinux();

}
