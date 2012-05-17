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
package com.liferay.faces.bridge.container;

import com.liferay.faces.bridge.FactoryWrapper;
import com.liferay.faces.bridge.context.BridgeContext;


/**
 * This abstract class provides a contract for defining a factory that knows how to create instances of type {@link
 * PortletContainer}. It is inspired by the factory pattern found in the JSF API like {@link
 * javax.faces.context.FacesContextFactory} and {@link javax.faces.context.ExternalContextFactory}. By implementing the
 * {@link javax.faces.FacesWrapper} interface, the class provides implementations with the opportunity to wrap another
 * factory (participate in a chain-of-responsibility pattern). If an implementation wraps a factory, then it should
 * provide a one-arg constructor so that the wrappable factory can be passed at initialization time.
 *
 * @author  Neil Griffin
 */
public abstract class PortletContainerFactory implements FactoryWrapper<PortletContainerFactory> {

	/**
	 * Gets an instance of PortletContainer according to the type of portlet container (Liferay, etc.) that created in
	 * the specified portletRequest.
	 */
	public abstract PortletContainer getPortletContainer(BridgeContext bridgeContext);
}
