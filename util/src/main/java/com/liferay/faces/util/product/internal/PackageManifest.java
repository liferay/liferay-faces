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
package com.liferay.faces.util.product.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class is designed to work-around a problem with JBoss AS such that the {@link
 * Class#getPackage()#getImplementationVersion()} method returns null during WAR application deployment. For more
 * information, see: http://issues.liferay.com/browse/FACES-1296
 *
 * @author  Neil Griffin
 */
public class PackageManifest {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PackageManifest.class);

	// Private Constants
	private static final String MANIFEST_MF_PATH = "META-INF/MANIFEST.MF";
	private static final String IMPLEMENTATION_VERSION_UNKNOWN = "0.0.0";

	// Private Data Members
	private String implementationTitle;
	private String implementationVersion;

	public PackageManifest(Class<?> clazz, String expectedImplementationTitle) {

		try {
			implementationTitle = expectedImplementationTitle;
			implementationVersion = IMPLEMENTATION_VERSION_UNKNOWN;

			// For each of the "META-INF/MANIFEST.MF" resources found by the ClassLoader:
			Enumeration<URL> manifestURLs = clazz.getClassLoader().getResources(MANIFEST_MF_PATH);
			boolean found = false;

			while (manifestURLs.hasMoreElements() && !found) {
				URL manifestURL = manifestURLs.nextElement();
				InputStream inputStream = manifestURL.openStream();
				Manifest manifest = new Manifest(inputStream);
				Attributes mainAttributes = manifest.getMainAttributes();

				// If the current resource matches the specified title, then retrieve the Implementation-Version.
				if (expectedImplementationTitle.equals(mainAttributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE))) {
					implementationVersion = mainAttributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
					found = true;
				}

				inputStream.close();
			}
		}
		catch (IOException e) {
			logger.error(e);
		}
	}

	public String getImplementationTitle() {
		return implementationTitle;
	}

	public String getImplementationVersion() {
		return implementationVersion;
	}
}
