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

import javax.faces.FacesException;

import com.liferay.faces.bridge.FactoryWrapper;


/**
 * This abstract class provides a contract for defining a factory that knows how to create instances of type {@link
 * BridgeConfig}. It is inspired by the factory pattern found in the JSF API like {@link
 * javax.faces.context.FacesContextFactory} and {@link javax.faces.context.ExternalContextFactory}. However, unlike
 * these classes, it does NOT implement the {@link javax.faces.FacesWrapper} interface. The reason why is because bridge
 * configuration is part of a chicken-and-the-egg type of scenario. Without the bridge configuration, the bridge can't
 * initialize properly. Once it is initialized, it's too late to delegate responsibility to a different factory in the
 * chain.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeConfigFactory implements FactoryWrapper<BridgeConfigFactory> {

	public abstract BridgeConfig getBridgeConfig() throws FacesException;
}
