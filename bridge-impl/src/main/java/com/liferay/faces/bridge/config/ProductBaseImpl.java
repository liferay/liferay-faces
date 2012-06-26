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
package com.liferay.faces.bridge.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.liferay.faces.bridge.BridgeConstants;


/**
 * @author  Neil Griffin
 */
public class ProductBaseImpl implements Product {

	// Private Constants
	private static final String DOT_DELIMITER_REGEX = "[.]";

	// Protected Data Members
	protected int buildId;
	protected boolean detected;
	protected int majorVersion;
	protected int minorVersion;
	protected int revisionVersion;
	protected String stringValue;
	protected String title;
	protected String version;

	@Override
	public String toString() {

		if (stringValue == null) {
			StringBuilder buf = new StringBuilder();

			if (title != null) {
				buf.append(title);
				buf.append(BridgeConstants.CHAR_SPACE);
			}

			if (version != null) {
				buf.append(version);
			}

			stringValue = buf.toString();
		}

		return stringValue;
	}

	protected void init(String title, String resourceId) {
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceId);
			Properties props = new Properties();
			props.load(is);
			this.title = title;
			initVersionInfo(props.getProperty("version"));
		} catch (Exception e) {
			// Module not implemented
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// Ignore
				}
			}
		}
		if (this.majorVersion > 0) {
			detected = true;
		}
	}

	protected void initVersionInfo(String version) {

		this.version = version;

		String[] versionParts = version.split(DOT_DELIMITER_REGEX);

		if (versionParts != null) {

			if (versionParts.length > 0) {
				majorVersion = parseInt(versionParts[0]);
			}

			if (versionParts.length > 1) {
				minorVersion = parseInt(versionParts[1]);
			}

			if (versionParts.length > 2) {
				revisionVersion = parseInt(versionParts[2]);
			}
		}
	}

	protected int parseInt(String value) {
		int intValue = 0;

		try {
			intValue = Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
			// ignore
		}

		return intValue;
	}

	public int getBuildId() {
		return buildId;
	}

	public boolean isDetected() {
		return detected;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public int getRevisionVersion() {
		return revisionVersion;
	}

	public String getTitle() {
		return title;
	}

	public String getVersion() {
		return version;
	}
}
