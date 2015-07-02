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

import java.util.List;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import com.liferay.faces.util.product.ProductConstants;


/**
 * @author  Neil Griffin
 */
public class ProductWildFlyImpl extends ProductBaseImpl {

	public ProductWildFlyImpl() {

		try {
			this.title = ProductConstants.WILDFLY;

			List<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);

			if (mBeanServers != null) {

				for (MBeanServer mBeanServer : mBeanServers) {

					ObjectName objectName = new ObjectName("jboss.as:management-root=server");

					if (objectName != null) {
						String releaseVersion = (String) mBeanServer.getAttribute(objectName, "releaseVersion");

						if (releaseVersion != null) {
							detected = true;
							initVersionInfo(releaseVersion);

							break;
						}
					}
				}
			}
		}
		catch (Exception e) {
			// Ignore -- WildFly is likely not present.
		}
	}
}
