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
package com.liferay.faces.bridge.application;

import javax.faces.application.Resource;
import javax.faces.application.ResourceWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class ResourceRichFacesImpl extends ResourceWrapper {

	public static final String RICHFACES_PATH_TOKEN = "/rfRes/";

	// Private Data Members
	private Resource wrappedResource;

	public ResourceRichFacesImpl(Resource resource) {
		this.wrappedResource = resource;
	}

	@Override
	public String getRequestPath() {

		String requestPath = super.getRequestPath();

		if (requestPath != null) {

			// If the /rfRes/ token is found in the request path, then RichFaces has likely added a dynamic resource.
			// Such resources have not had the request path processed by ExternalContext.encodeResourceURL(String) and
			// are therefore incompatible with a portlet environment.
			int pos = requestPath.indexOf(RICHFACES_PATH_TOKEN);

			if (pos > 0) {

				// Some resources like fileUploadProgress will have an extension like ".xhtml" appended to them which
				// must be removed.
				requestPath = requestPath.replaceAll("[.]faces", StringPool.EMPTY);
				requestPath = requestPath.replaceAll("[.]jsf", StringPool.EMPTY);
				requestPath = requestPath.replaceAll("[.]xhtml", StringPool.EMPTY);

				// Encode the request path as a portlet ResourceURL.
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				StringBuilder buf = new StringBuilder();
				buf.append(StringPool.FORWARD_SLASH);
				buf.append(BridgeConstants.JAVAX_FACES_RESOURCE);
				buf.append(StringPool.FORWARD_SLASH);
				buf.append(requestPath.substring(pos + RICHFACES_PATH_TOKEN.length()));
				requestPath = externalContext.encodeResourceURL(buf.toString());
			}
		}

		return requestPath;
	}

	@Override
	public Resource getWrapped() {
		return wrappedResource;
	}

}
