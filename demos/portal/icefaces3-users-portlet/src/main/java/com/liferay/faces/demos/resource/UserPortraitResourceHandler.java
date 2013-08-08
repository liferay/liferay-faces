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
package com.liferay.faces.demos.resource;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletSession;
import javax.portlet.ResourceResponse;

import com.liferay.faces.bridge.application.ResourceHandlerBridgeImpl;
import com.liferay.faces.util.application.ResourceConstants;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * This class serves as a JSF2 {@link ResourceHandler} that has the ability to write the contents of either the uploaded
 * file or the portrait image from the Liferay database to the underlying portlet {@link ResourceResponse}.
 *
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class UserPortraitResourceHandler extends ResourceHandlerBridgeImpl {

	// Public Constants
	public static final String LIBRARY_NAME = "userPortraitResources";
	public static final String PARAM_NAME_USER_ID = "UserId";
	public static final String PARAM_NAME_UPLOADED_FILE_ID = "uploadedFileId";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UserPortraitResourceHandler.class);

	public UserPortraitResourceHandler(ResourceHandler wrappedResourceHandler) {
		super(wrappedResourceHandler);
	}

	@Override
	public Resource createResource(String resourceName, String libraryName) {

		// If the specified library name is known by this resource handler, then
		if (LIBRARY_NAME.equals(libraryName)) {

			// If the specified resource name is a portrait resource, then
			if (UserPortraitResource.RESOURCE_NAME.equals(resourceName)) {

				// Assume that this method is being called by the handleResourceRequest method. In this case, this
				// resource handler was invoked by a JSF2 resource URL that was determined by the
				// UserPortraitResource.getRequestPath() method. The resource URL will contain a "userId" parameter and
				// optionally an "uploadedFileId" parameter.
				FacesContext facesContext = FacesContext.getCurrentInstance();
				Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
				String userId = requestParameterMap.get(PARAM_NAME_USER_ID);
				String uploadedFileId = requestParameterMap.get(PARAM_NAME_UPLOADED_FILE_ID);

				// Determine the current "sessionId" so that the UserPortraitResource class will be able to find the
				// uploaded file.
				ExternalContext externalContext = facesContext.getExternalContext();
				PortletSession portletSession = (PortletSession) externalContext.getSession(true);
				String sessionId = portletSession.getId();

				// Get the user associated with the "userId" URL parameter.
				User user = null;

				try {
					user = UserLocalServiceUtil.getUser(Long.parseLong(userId));
				}
				catch (Exception e) {
					logger.error(e);
				}

				// Return an instance of UserPortraitResource that has the ability to retrieve the uploaded file or
				// portrait image from the Liferay database.
				return new UserPortraitResource(user, uploadedFileId, sessionId);
			}

			// Otherwise, delegate creation of the resource to the delegation-chain.
			else {
				return super.createResource(resourceName, libraryName);
			}
		}

		// Otherwise, delegate creation of the resource to the delegation-chain.
		else {
			return super.createResource(resourceName, libraryName);
		}
	}

	@Override
	public void handleResourceRequest(FacesContext facesContext) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		String libraryName = externalContext.getRequestParameterMap().get(ResourceConstants.LN);
		String resourceName = externalContext.getRequestParameterMap().get(ResourceConstants.JAVAX_FACES_RESOURCE);

		if (LIBRARY_NAME.equals(libraryName) && UserPortraitResource.RESOURCE_NAME.equals(resourceName)) {

			Resource resource = createResource(resourceName, libraryName);
			handleResource(facesContext, resource);
		}
		else {
			super.handleResourceRequest(facesContext);
		}
	}

	@Override
	public boolean libraryExists(String libraryName) {

		if (LIBRARY_NAME.equals(libraryName)) {
			return true;
		}
		else {
			return super.libraryExists(libraryName);
		}
	}

}
