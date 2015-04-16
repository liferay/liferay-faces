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
package com.liferay.faces.bridge.filter.liferay.internal;

import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.filter.liferay.LiferayActionURL;
import com.liferay.faces.bridge.filter.liferay.LiferayRenderURL;
import com.liferay.faces.bridge.filter.liferay.LiferayResourceURL;
import com.liferay.faces.bridge.filter.liferay.LiferayURLFactory;
import com.liferay.faces.bridge.filter.liferay.LiferayURLGenerator;

import com.liferay.portal.kernel.portlet.FriendlyURLMapper;


/**
 * This class implements the {@link com.liferay.faces.bridge.filter.liferay.LiferayURLFactory} contract for creating
 * Liferay-compatible URLs. The design provides a performance optimization that was first introduced in FACES-220 and
 * FACES-245. The optimization prevents repetitive calls to Liferay Portal's {@link PortletURL#toString()} method by
 * ensuring that the toString() method of {@link MimeResponse#createActionURL()}, {@link
 * MimeResponse#createRenderURL()}, and {@link MimeResponse#createResourceURL()} are called only once during the JSF
 * lifecycle, and that the pertinent parts of the String are cached. However, the optimization is only usable for
 * portlets that do not have an associated Liferay {@link FriendlyURLMapper}. For more info, see FACES-257.
 *
 * @author  Neil Griffin
 */
public class LiferayURLFactoryImpl extends LiferayURLFactory {

	private static final String ACTION_URL_GENERATOR = LiferayURLFactoryImpl.class.getName() +
		"com.liferay.faces.bridge.container.liferay.ACTION_URL_GENERATOR";
	private static final String RENDER_URL_GENERATOR =
		"com.liferay.faces.bridge.container.liferay.RENDER_URL_GENERATOR";
	private static final String RESOURCE_URL_GENERATOR =
		"com.liferay.faces.bridge.container.liferay.RESOURCE_URL_GENERATOR";

	@Override
	public LiferayActionURL getLiferayActionURL(BridgeContext bridgeContext, MimeResponse mimeResponse,
		String responseNamespace) {

		LiferayURLGenerator liferayURLGenerator = (LiferayURLGenerator) bridgeContext.getAttributes().get(
				ACTION_URL_GENERATOR);

		if (liferayURLGenerator == null) {

			PortletURL actionURL = mimeResponse.createActionURL();
			liferayURLGenerator = new LiferayURLGeneratorActionImpl(actionURL.toString(), actionURL.getPortletMode(),
					responseNamespace, actionURL.getWindowState());
			bridgeContext.getAttributes().put(ACTION_URL_GENERATOR, liferayURLGenerator);
		}

		return new LiferayActionURLImpl(liferayURLGenerator);
	}

	@Override
	public LiferayRenderURL getLiferayRenderURL(BridgeContext bridgeContext, MimeResponse mimeResponse,
		String responseNamespace, boolean friendlyURLMapperEnabled) {

		LiferayRenderURL liferayRenderURL;

		if (friendlyURLMapperEnabled) {

			PortletURL renderURL = mimeResponse.createRenderURL();
			liferayRenderURL = new LiferayRenderURLFriendlyImpl(renderURL, responseNamespace);
		}
		else {
			LiferayURLGenerator liferayURLGenerator = (LiferayURLGenerator) bridgeContext.getAttributes().get(
					RENDER_URL_GENERATOR);

			if (liferayURLGenerator == null) {

				PortletURL renderURL = mimeResponse.createRenderURL();
				liferayURLGenerator = new LiferayURLGeneratorRenderImpl(renderURL.toString(),
						renderURL.getPortletMode(), responseNamespace, renderURL.getWindowState());
				bridgeContext.getAttributes().put(RENDER_URL_GENERATOR, liferayURLGenerator);
			}

			liferayRenderURL = new LiferayRenderURLImpl(liferayURLGenerator);
		}

		return liferayRenderURL;
	}

	@Override
	public LiferayResourceURL getLiferayResourceURL(BridgeContext bridgeContext, MimeResponse mimeResponse,
		String responseNamespace) {

		LiferayURLGenerator liferayURLGenerator = (LiferayURLGenerator) bridgeContext.getAttributes().get(
				RESOURCE_URL_GENERATOR);

		if (liferayURLGenerator == null) {

			ResourceURL resourceURL = mimeResponse.createResourceURL();
			liferayURLGenerator = new LiferayURLGeneratorResourceImpl(resourceURL.toString(), responseNamespace);
			bridgeContext.getAttributes().put(RESOURCE_URL_GENERATOR, liferayURLGenerator);
		}

		return new LiferayResourceURLImpl(liferayURLGenerator);
	}

	public LiferayURLFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
