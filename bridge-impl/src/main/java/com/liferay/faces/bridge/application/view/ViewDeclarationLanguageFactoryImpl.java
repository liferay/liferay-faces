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
package com.liferay.faces.bridge.application.view;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewDeclarationLanguageFactory;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.Bridge.BridgeRenderPolicy;


/**
 * @author  Neil Griffin
 */
public class ViewDeclarationLanguageFactoryImpl extends ViewDeclarationLanguageFactory {

	// Private Data Members
	private ViewDeclarationLanguageFactory wrappedFactory;

	public ViewDeclarationLanguageFactoryImpl(ViewDeclarationLanguageFactory wrappedFactory) {
		this.wrappedFactory = wrappedFactory;
	}

	@Override
	public ViewDeclarationLanguage getViewDeclarationLanguage(String viewId) {

		ViewDeclarationLanguage wrappedViewDeclarationLanguage = getWrappedHack().getViewDeclarationLanguage(viewId);

		boolean facelets = wrappedViewDeclarationLanguage.getClass().toString().toLowerCase().contains("facelet");

		ViewDeclarationLanguage viewDeclarationLanguage;

		if (facelets) {
			viewDeclarationLanguage = wrappedViewDeclarationLanguage;
		}
		else {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			String initParam = externalContext.getInitParameter(Bridge.RENDER_POLICY);

			// If the developer has specified ALWAYS_DELEGATE in the WEB-INF/web.xml descriptor, then execute
			// the Mojarra/MyFaces ViewDeclarationLanguage.
			if (BridgeRenderPolicy.ALWAYS_DELEGATE.toString().equals(initParam)) {
				viewDeclarationLanguage = wrappedViewDeclarationLanguage;
			}

			// Otherwise, if the developer specified NEVER_DELEGATE or didn't specify a value, then execute the
			// bridge's JSP ViewDeclarationLanguage implementation. Note that the spec indicates that if the
			// developer doesn't specify a value, then the bridge first delegates to Mojarra/MyFaces but then
			// tries its own JSP ViewDeclarationLanguage if an exception is thrown. This is non-performant
			// because Mojarra/MyFaces will always throw the exception. This bridge implementation avoids this
			// expensive operation because ViewDeclarationLanguageJspImpl wraps the Mojarra/MyFaces
			// implementation in such a way that Mojarra/MyFaces can execute without throwing an exception.
			else {
				viewDeclarationLanguage = new ViewDeclarationLanguageJspImpl(wrappedViewDeclarationLanguage);
			}
		}

		return viewDeclarationLanguage;
	}

	@Override
	public ViewDeclarationLanguageFactory getWrapped() {
		return wrappedFactory;
	}

	/**
	 * There is (what seems to be) a bug in Mojarra such that this factory implementation gets created twice, and
	 * subsequently appears in the chain-of-responsibility twice. Because of this, the second creation causes this class
	 * to wrap an instance of this same class! So this method is a hack-workaround to ensure that we're always working
	 * with the correct wrapped factory.
	 */
	protected ViewDeclarationLanguageFactory getWrappedHack() {

		ViewDeclarationLanguageFactory wrappedViewDeclarationLanguageFactory = getWrapped();

		if (wrappedViewDeclarationLanguageFactory instanceof ViewDeclarationLanguageFactoryImpl) {
			ViewDeclarationLanguageFactoryImpl viewDeclarationLanguageFactoryImpl = (ViewDeclarationLanguageFactoryImpl)
				wrappedViewDeclarationLanguageFactory;
			wrappedViewDeclarationLanguageFactory = viewDeclarationLanguageFactoryImpl.getWrapped();
		}

		return wrappedViewDeclarationLanguageFactory;
	}

}
