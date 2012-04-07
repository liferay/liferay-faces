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
package com.liferay.faces.bridge.renderkit.html_basic;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


/**
 * <p>This class is a JSF {@link ViewScoped} {@link ManagedBean} that maintains a list of JavaScript and/or CSS
 * resources that have been added to the &lt;head&gt; section of the portal page. Along with {@link HeadPhaseListener}
 * and {@link HeadRendererBridgeImpl}, this class helps provides a solution to an issue regarding Ajax-initiated execution of
 * navigation-rules in a portlet. See the class-level comments in the {@link HeadPhaseListener} for more details.</p>
 *
 * @author  Neil Griffin
 */
@ManagedBean
@ViewScoped
public class HeadManagedBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 3829127137783852729L;

	// Private Data Members
	private Set<String> headResourceIds = new HashSet<String>();

	public static HeadManagedBean getInstance(FacesContext facesContext) {
		String elExpression = "headManagedBean";
		ELResolver elResolver = facesContext.getApplication().getELResolver();

		return (HeadManagedBean) elResolver.getValue(facesContext.getELContext(), null, elExpression);
	}

	public Set<String> getHeadResourceIds() {
		return headResourceIds;
	}

}
