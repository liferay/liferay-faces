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
package com.liferay.faces.bridge.application.view;

import java.beans.BeanInfo;
import java.io.IOException;
import java.util.List;

import javax.faces.FacesWrapper;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.AttachedObjectHandler;
import javax.faces.view.StateManagementStrategy;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewMetadata;


/**
 * @author  Neil Griffin
 */
public abstract class ViewDeclarationLanguageWrapper extends ViewDeclarationLanguage
	implements FacesWrapper<ViewDeclarationLanguage> {

	@Override
	public void buildView(FacesContext facesContext, UIViewRoot uiViewRoot) throws IOException {
		getWrapped().buildView(facesContext, uiViewRoot);
	}

	@Override
	public UIViewRoot createView(FacesContext facesContext, String viewId) {
		return getWrapped().createView(facesContext, viewId);
	}

	@Override
	public void renderView(FacesContext facesContext, UIViewRoot uiViewRoot) throws IOException {
		getWrapped().renderView(facesContext, uiViewRoot);
	}

	@Override
	public UIViewRoot restoreView(FacesContext facesContext, String viewId) {
		return getWrapped().restoreView(facesContext, viewId);
	}

	@Override
	public void retargetAttachedObjects(FacesContext facesContext, UIComponent uiComponent,
		List<AttachedObjectHandler> handlers) {
		getWrapped().retargetAttachedObjects(facesContext, uiComponent, handlers);
	}

	@Override
	public void retargetMethodExpressions(FacesContext facesContext, UIComponent uiComponent) {
		getWrapped().retargetMethodExpressions(facesContext, uiComponent);
	}

	@Override
	public boolean viewExists(FacesContext facesContext, String viewId) {
		return getWrapped().viewExists(facesContext, viewId);
	}

	@Override
	public BeanInfo getComponentMetadata(FacesContext facesContext, Resource componentResource) {
		return getWrapped().getComponentMetadata(facesContext, componentResource);
	}

	@Override
	public String getId() {
		return getWrapped().getId();
	}

	@Override
	public Resource getScriptComponentResource(FacesContext facesContext, Resource componentResource) {
		return getWrapped().getScriptComponentResource(facesContext, componentResource);
	}

	@Override
	public StateManagementStrategy getStateManagementStrategy(FacesContext facesContext, String viewId) {
		return getWrapped().getStateManagementStrategy(facesContext, viewId);
	}

	@Override
	public ViewMetadata getViewMetadata(FacesContext facesContext, String viewId) {
		return getWrapped().getViewMetadata(facesContext, viewId);
	}

	public abstract ViewDeclarationLanguage getWrapped();

}
