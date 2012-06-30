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
package javax.portlet.faces.component;

import java.io.Serializable;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.faces.annotation.PortletNamingContainer;


/**
 * This class satisfies namespacing requirements of Section 6.6 of the specification.
 *
 * @author  Neil Griffin
 */
@PortletNamingContainer
public class PortletNamingContainerUIViewRoot extends UIViewRoot implements NamingContainer, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 6744332823172081041L;

	// Private Data Members
	private String namespace;

	@Override
	public String getContainerClientId(FacesContext facesContext) {

		if (namespace == null) {
			ExternalContext externalContext = facesContext.getExternalContext();
			namespace = externalContext.encodeNamespace("");
		}

		return namespace;
	}

}
