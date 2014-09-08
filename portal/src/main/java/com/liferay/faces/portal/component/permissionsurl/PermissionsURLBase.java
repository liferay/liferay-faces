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
package com.liferay.faces.portal.component.permissionsurl;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIData;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PermissionsURLBase extends UIData {

	// Protected Enumerations
	protected enum PermissionsURLPropertyKeys {
		modelResource,
		modelResourceDescription,
		redirect,
		resourceGroupId,
		resourcePrimKey,
		roleTypes,
		var,
		windowState
	}

	public String getModelResource() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.modelResource, "");
	}

	public void setModelResource(String modelResource) {
		getStateHelper().put(PermissionsURLPropertyKeys.modelResource, modelResource);
	}

	public String getModelResourceDescription() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.modelResourceDescription, "");
	}

	public void setModelResourceDescription(String modelResourceDescription) {
		getStateHelper().put(PermissionsURLPropertyKeys.modelResourceDescription, modelResourceDescription);
	}

	public boolean isRedirect() {
		return (Boolean) getStateHelper().eval(PermissionsURLPropertyKeys.redirect, false);
	}

	public void setRedirect(boolean redirect) {
		getStateHelper().put(PermissionsURLPropertyKeys.redirect, redirect);
	}

	public String getResourceGroupId() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.resourceGroupId, null);
	}

	public void setResourceGroupId(String resourceGroupId) {
		getStateHelper().put(PermissionsURLPropertyKeys.resourceGroupId, resourceGroupId);
	}

	public String getResourcePrimKey() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.resourcePrimKey, null);
	}

	public void setResourcePrimKey(String resourcePrimKey) {
		getStateHelper().put(PermissionsURLPropertyKeys.resourcePrimKey, resourcePrimKey);
	}

	public int[] getRoleTypes() {
		return (int[]) getStateHelper().eval(PermissionsURLPropertyKeys.roleTypes, null);
	}

	public void setRoleTypes(int[] roleTypes) {
		getStateHelper().put(PermissionsURLPropertyKeys.roleTypes, roleTypes);
	}

	public String getVar() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.var, null);
	}

	public void setVar(String var) {
		getStateHelper().put(PermissionsURLPropertyKeys.var, var);
	}

	public String getWindowState() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.windowState, null);
	}

	public void setWindowState(String windowState) {
		getStateHelper().put(PermissionsURLPropertyKeys.windowState, windowState);
	}
}
//J+
