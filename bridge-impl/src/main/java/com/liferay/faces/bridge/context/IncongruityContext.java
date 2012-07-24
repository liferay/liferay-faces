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
package com.liferay.faces.bridge.context;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


/**
 * <p>Occasionally there is an incongruity (a mismatch) between the Portlet lifecycle and the JSF lifecycle with regard
 * to execution of methods on the {@link ExternalContext}. This class is designed to compensate for these incongruous
 * actions as much as possible.</p>
 *
 * <p>Example: Sometimes the JSF implementation or a JSF {@link ActionListener} will call methods like {@link
 * ExternalContext#setResponseStatus(int)} during the Portlet {@link javax.portlet.PortletRequest#ACTION_PHASE}. In this
 * case, the Portlet lifecycle is working with a {@link javax.portlet.ActionResponse} but there is no corresponding
 * method. Technically the only time such a method could be called would be during the {@link
 * javax.portlet.PortletRequest#RESOURCE_PHASE} because the {@link javax.portlet.ResourceResponse} class has a
 * setReponseStatus(int) method.</p>
 *
 * @author  Neil Griffin
 */
public abstract class IncongruityContext extends ExternalContext {

	/**
	 * This method is designed to be called during the RENDER_PHASE of the portlet lifecycle, and will execute {@link
	 * ExternalContext} methods that were inappropriately called during the ACTION_PHASE or EVENT_PHASE.
	 *
	 * @param   facesContext  The current {@link FacesContext}.
	 *
	 * @throws  IOException  If an error occurs.
	 */
	public abstract void makeCongruous(FacesContext facesContext) throws IOException;

	/**
	 * Returns a mutable map of attributes.
	 *
	 * @return  The attribute map.
	 */
	public abstract Map<String, Object> getAttributes();

	/**
	 * @since  JSF 2.0
	 */
	public abstract void setRequestContentLength(int length);

	/**
	 * @since  JSF 2.0
	 */
	public abstract void setRequestContentType(String contentType);

	/**
	 * @since  JSF 2.0
	 */
	public abstract void setResponseCommitted(boolean committed);

	/**
	 * @since  JSF 2.0
	 */
	public abstract int getResponseContentLength();
}
