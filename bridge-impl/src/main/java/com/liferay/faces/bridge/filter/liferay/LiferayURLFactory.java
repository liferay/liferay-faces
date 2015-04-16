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
package com.liferay.faces.bridge.filter.liferay;

import javax.faces.FacesWrapper;
import javax.portlet.MimeResponse;

import com.liferay.faces.bridge.context.BridgeContext;


/**
 * @author  Neil Griffin
 */
public abstract class LiferayURLFactory implements FacesWrapper<LiferayURLFactory> {

	public abstract LiferayActionURL getLiferayActionURL(BridgeContext bridgeContext, MimeResponse mimeResponse,
		String responseNamespace);

	public abstract LiferayRenderURL getLiferayRenderURL(BridgeContext bridgeContext, MimeResponse mimeResponse,
		String responseNamespace, boolean friendlyURLMapperEnabled);

	public abstract LiferayResourceURL getLiferayResourceURL(BridgeContext bridgeContext, MimeResponse mimeResponse,
		String responseNamespace);
}
