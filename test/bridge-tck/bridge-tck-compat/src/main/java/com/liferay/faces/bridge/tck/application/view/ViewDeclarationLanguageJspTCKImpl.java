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
package com.liferay.faces.bridge.tck.application.view;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlHead;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewDeclarationLanguageWrapper;


/**
 * @author  Neil Griffin
 */
public class ViewDeclarationLanguageJspTCKImpl extends ViewDeclarationLanguageWrapper {

	// Private Data Members
	private ViewDeclarationLanguage wrappedViewDeclarationLanguage;

	public ViewDeclarationLanguageJspTCKImpl(ViewDeclarationLanguage viewDeclarationLanguage) {
		this.wrappedViewDeclarationLanguage = viewDeclarationLanguage;
	}

	@Override
	public void buildView(FacesContext facesContext, UIViewRoot uiViewRoot) throws IOException {
		super.buildView(facesContext, uiViewRoot);

		UIComponent coreForm = findCoreFormRecurse(uiViewRoot);

		if (coreForm != null) {

			// Since the JSF-1.2-based TCK does not have h:head tags in any of the JSPs, need to add one dynamically so
			// that the jsf.js script resource can be rendered. This is necessary so that Trinidad Partial Page
			// Rendering (PPR) will work properly.
			HtmlHead htmlHead = new HtmlHead();
			uiViewRoot.getChildren().add(htmlHead);

			// Add the "jsf.js" script resource to the h:head component.
			UIOutput uiOutput = new UIOutput();
			uiOutput.setRendererType("javax.faces.resource.Script");
			uiOutput.getAttributes().put("name", "jsf.js");
			uiOutput.getAttributes().put("library", "javax.faces");
			uiViewRoot.addComponentResource(facesContext, uiOutput, "head");

			// Due to a bug in the Trinidad tr:form renderer, need to add the javax.faces.encodedURL hidden field
			// dynamically. See: https://issues.apache.org/jira/browse/TRINIDAD-2284
			String viewId = uiViewRoot.getViewId();
			String actionURL = facesContext.getApplication().getViewHandler().getActionURL(facesContext, viewId);
			ExternalContext externalContext = facesContext.getExternalContext();
			String encodedPartialActionURL = externalContext.encodePartialActionURL(actionURL);
			EncodedURLHiddenField encodedURLHiddenField = new EncodedURLHiddenField();
			encodedURLHiddenField.setValue(encodedPartialActionURL);
			coreForm.getChildren().add(encodedURLHiddenField);
		}
	}

	protected UIComponent findCoreFormRecurse(UIComponent uiComponent) {
		UIComponent coreForm = null;

		if (uiComponent.getClass().toString().endsWith("CoreForm")) {
			coreForm = uiComponent;
		}
		else {
			List<UIComponent> children = uiComponent.getChildren();

			for (UIComponent child : children) {
				coreForm = findCoreFormRecurse(child);

				if (coreForm != null) {
					break;
				}
			}
		}

		return coreForm;
	}

	@Override
	public ViewDeclarationLanguage getWrapped() {
		return wrappedViewDeclarationLanguage;
	}

}
