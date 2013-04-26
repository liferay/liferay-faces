/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.container.liferay;

import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

import com.liferay.faces.bridge.context.BridgeContext;

import com.liferay.portal.kernel.portlet.FriendlyURLMapper;


/**
 * This class implements the {@link LiferayURLFactory} contract for creating Liferay-compatible URLs. The design
 * provides a performance optimization that was first introduced in FACES-220 and FACES-245. The optimization prevents
 * repetitive calls to Liferay Portal's {@link PortletURL#toString()} method by ensuring that the toString() method of
 * {@link MimeResponse#createActionURL()}, {@link MimeResponse#createRenderURL()}, and {@link
 * MimeResponse#createResourceURL()} are called only once during the JSF lifecycle, and that the pertinent parts of the
 * String are cached. However, the optimization is only usable for portlets that do not have an associated Liferay
 * {@link FriendlyURLMapper}. For more info, see FACES-257.
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
		String responseNamespace, boolean friendlyURLMapperEnabled) {

		LiferayActionURL liferayActionURL = null;

		if (friendlyURLMapperEnabled) {

			PortletURL actionURL = mimeResponse.createActionURL();
			liferayActionURL = new LiferayActionURLFriendlyImpl(actionURL, responseNamespace);
		}
		else {
			LiferayURLGenerator liferayURLGenerator = (LiferayURLGenerator) bridgeContext.getAttributes().get(
					ACTION_URL_GENERATOR);

			if (liferayURLGenerator == null) {

				PortletURL actionURL = mimeResponse.createActionURL();
				liferayURLGenerator = new LiferayURLGeneratorActionImpl(actionURL.toString(),
						actionURL.getPortletMode(), responseNamespace, actionURL.getWindowState());
				bridgeContext.getAttributes().put(ACTION_URL_GENERATOR, liferayURLGenerator);
			}

			liferayActionURL = new LiferayActionURLImpl(liferayURLGenerator);
		}

		return liferayActionURL;
	}

	@Override
	public LiferayRenderURL getLiferayRenderURL(BridgeContext bridgeContext, MimeResponse mimeResponse,
		String responseNamespace, boolean friendlyURLMapperEnabled) {

		LiferayRenderURL liferayRenderURL = null;

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
		String responseNamespace, boolean friendlyURLMapperEnabled) {

		LiferayResourceURL liferayResourceURL = null;

		if (friendlyURLMapperEnabled) {

			ResourceURL resourceURL = mimeResponse.createResourceURL();
			liferayResourceURL = new LiferayResourceURLFriendlyImpl(resourceURL, responseNamespace);
		}
		else {
			LiferayURLGenerator liferayURLGenerator = (LiferayURLGenerator) bridgeContext.getAttributes().get(
					RESOURCE_URL_GENERATOR);

			if (liferayURLGenerator == null) {

				ResourceURL resourceURL = mimeResponse.createResourceURL();
				liferayURLGenerator = new LiferayURLGeneratorResourceImpl(resourceURL.toString(), responseNamespace);
				bridgeContext.getAttributes().put(RESOURCE_URL_GENERATOR, liferayURLGenerator);
			}

			liferayResourceURL = new LiferayResourceURLImpl(liferayURLGenerator);
		}

		return liferayResourceURL;
	}

	public LiferayURLFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
