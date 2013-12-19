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
package com.liferay.faces.portal.component;

import javax.el.ValueExpression;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import com.liferay.portal.kernel.util.Validator;


/**
 * @author  Ed Shin
 */
public class PermissionsURL extends UIOutput {

	// Private Constants
	private static final String COMPONENT_TYPE = "com.liferay.faces.portal.PermissionsURL";
	private static final String RENDERER_TYPE = "com.liferay.faces.portal.renderkit.PermissionsURLRenderer";

	// Private Data Members
	private String modelResource;
	private String modelResourceDescription;
	private String redirect;
	private String resourcePrimKey;

	public PermissionsURL() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public void restoreState(FacesContext facesContext, Object state) {
		Object[] values = (Object[]) state;

		super.restoreState(facesContext, values[0]);
		this.redirect = (String) values[1];
		this.modelResource = (String) values[2];
		this.modelResourceDescription = (String) values[3];
		this.resourcePrimKey = (String) values[4];
	}

	@Override
	public Object saveState(FacesContext _context) {
		Object[] values = new Object[5];

		values[0] = super.saveState(_context);
		values[1] = redirect;
		values[2] = modelResource;
		values[3] = modelResourceDescription;
		values[4] = resourcePrimKey;

		return values;
	}

	@Override
	public String getFamily() {
		return COMPONENT_TYPE;
	}

	public String getModelResource() {

		if (Validator.isNotNull(modelResource)) {
			return modelResource;
		}

		ValueExpression valueExpression = getValueExpression("modelResource");

		if (Validator.isNotNull(valueExpression)) {
			return (String) valueExpression.getValue(getFacesContext().getELContext());
		}
		else {
			return null;
		}
	}

	public void setModelResource(String modelResource) {
		this.modelResource = modelResource;
	}

	public String getModelResourceDescription() {

		if (Validator.isNotNull(modelResourceDescription)) {
			return modelResourceDescription;
		}

		ValueExpression valueExpression = getValueExpression("modelResourceDescription");

		if (Validator.isNotNull(valueExpression)) {
			return (String) valueExpression.getValue(getFacesContext().getELContext());
		}
		else {
			return null;
		}
	}

	public void setModelResourceDescription(String modelResourceDescription) {
		this.modelResourceDescription = modelResourceDescription;
	}

	public String getRedirect() {

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		ValueExpression valueExpression = getValueExpression("redirect");

		if (Validator.isNotNull(valueExpression)) {
			return (String) valueExpression.getValue(getFacesContext().getELContext());
		}
		else {
			return null;
		}
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getResourcePrimKey() {

		if (Validator.isNotNull(resourcePrimKey)) {
			return resourcePrimKey;
		}

		ValueExpression valueExpression = getValueExpression("resourcePrimKey");

		if (Validator.isNotNull(valueExpression)) {
			return (String) valueExpression.getValue(getFacesContext().getELContext());
		}
		else {
			return null;
		}
	}

	public void setResourcePrimKey(String resourcePrimKey) {
		this.resourcePrimKey = resourcePrimKey;
	}

}
