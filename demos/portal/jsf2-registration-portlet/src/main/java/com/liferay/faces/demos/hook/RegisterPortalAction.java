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
package com.liferay.faces.demos.hook;

import java.lang.reflect.Proxy;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;


/**
 * NOTE -- THIS APPROACH IS NOT RECOMMENDED IN PRODUCTION!
 *
 * <p>This is a portal startup action that will wrap the out-of-the-box Liferay PortalImpl instance with a wrapped
 * (decorated) class. This approach is not recommended in a production environment, because it has an unnecessary
 * performance impact. The reason why this approach is taken here is because it is a convenient way to perform an
 * override with a self-contained portlet WAR demo. Rather than wrapping/decorating, a more performant approach would be
 * to create a Liferay EXT Plugin that simply extends the PortalImpl class and overrides the {@link
 * Portal#getCreateAccountURL(javax.servlet.http.HttpServletRequest, com.liferay.portal.theme.ThemeDisplay)} method. The
 * override would need to take place during portal startup by overriding the bean with the ext-spring.xml file. For more
 * information, see {@link
 * http://www.liferay.com/documentation/liferay-portal/6.1/development/-/ai/developing-an-ext-plug-4}.</p>
 *
 * @author  Neil Griffin
 */
public class RegisterPortalAction extends SimpleAction {

	private static final Logger logger = LoggerFactory.getLogger(RegisterPortalAction.class);

	@Override
	public void run(String[] companyIds) throws ActionException {
		Portal portal = PortalUtil.getPortal();

		try {
			ClassLoader classLoader = Portal.class.getClassLoader();
			portal = (Portal) Proxy.newProxyInstance(classLoader, new Class[] { Portal.class },
					new PortalHookImpl(portal));
		}
		catch (Exception e) {
			logger.error(e);
		}

		PortalUtil portalUtil = (PortalUtil) PortalBeanLocatorUtil.getBeanLocator().locate(PortalUtil.class.getName());
		portalUtil.setPortal(portal);
	}

}
