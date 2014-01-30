/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.issues.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@RequestScoped
public class TestBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = -591128176912990631L;

	public String getRenderUrl() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		MimeResponse mimeResponse = (MimeResponse) externalContext.getResponse();
		PortletURL renderURL = mimeResponse.createRenderURL();
		renderURL.setParameter("testParam", new String[] { "foo", "bar" });

		return renderURL.toString();
	}

}
