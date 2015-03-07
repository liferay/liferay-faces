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
package com.liferay.faces.demos.portlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.faces.render.ResponseStateManager;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.faces.GenericFacesPortlet;


/**
 * @author  Neil Griffin
 */
public class ActionURLDemoPortlet extends GenericFacesPortlet {

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException,
		IOException {

		String contentType = actionRequest.getContentType();
		boolean fileUpload = ((contentType != null) && contentType.toLowerCase().startsWith("multipart/"));

		String viewState = actionRequest.getParameter(ResponseStateManager.VIEW_STATE_PARAM);

		if ((viewState == null) && (!fileUpload)) {
			actionResponse.setRenderParameter("Non-Faces-Postback", Boolean.TRUE.toString());

			String foo = actionRequest.getParameter("foo");

			if (foo != null) {
				actionResponse.setRenderParameter("foo", foo);
			}
		}
		else {
			super.processAction(actionRequest, actionResponse);
		}
	}

	@Override
	protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		String nonFacesPostback = renderRequest.getParameter("Non-Faces-Postback");

		if (nonFacesPostback == null) {
			super.doView(renderRequest, renderResponse);
		}
		else {
			PrintWriter writer = renderResponse.getWriter();
			writer.write("<p>");
			writer.write("Successfully executed ");
			writer.write("<strong>non-JSF postback</strong> in ");
			writer.write(ActionURLDemoPortlet.class.getName());
			writer.write("</p>");

			String foo = renderRequest.getParameter("foo");
			writer.write("<p>");
			writer.write("Action Parameter <strong>foo</strong>=" + foo);
			writer.write("</p>");

			PortletURL renderURL = renderResponse.createRenderURL();
			renderURL.setParameter("componentPrefix", "portlet");
			renderURL.setParameter("componentName", "actionurl");
			renderURL.setParameter("componentUseCase", "general");
			writer.write("<p>");
			writer.write("<a href=\"" + renderURL.toString() + "\">");
			writer.write("Return to portlet:actionURL in the Liferay Faces Showcase");
			writer.write("</a>");
			writer.write("</p>");
		}
	}
}
