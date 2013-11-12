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
package com.liferay.faces.bridge.context.url;

import javax.faces.context.FacesContext;
import javax.portlet.faces.Bridge;


/**
 * This interface represents a bridge "resource" URL, meaning a URL that has convenience methods for representing URLs
 * according to the deviation requirements of {@link javax.faces.context.ExternalContext#encodeResourceURL(String)}
 * listed in Section 6.1.3.1 of the Bridge Spec.
 *
 * @author  Neil Griffin
 */
public interface BridgeResourceURL extends BridgeURL {

	/**
	 * Replaces the value of the "javax.portlet.BackLink" parameter with an encoded action URL that represents a link to
	 * the current Faces viewId.
	 *
	 * @param  facesContext  The current {@link FacesContext} instance.
	 */
	void replaceBackLinkParameter(FacesContext facesContext);

	/**
	 * Sets a flag indicating whether or not the URL must satisfy "in-protocol" resource serving.
	 *
	 * @param  inProtocol  <code>true</code> if the URL must satisfy "in-protocol" resource serving, otherwise <code>
	 *                     false</code>.
	 */
	void setInProtocol(boolean inProtocol);

	/**
	 * Sets a flag indicating whether or not the URL is a view-link to a Faces view, which is a type of navigation. For
	 * more information, refer to the documentation at {@link Bridge#VIEW_LINK}.
	 */
	void setViewLink(boolean viewLink);
}
