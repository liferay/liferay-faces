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
package com.liferay.faces.bridge.tck.application.view;

import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewDeclarationLanguageFactory;

import com.liferay.faces.bridge.application.view.ViewDeclarationLanguageJspImpl;


/**
 * @author  Neil Griffin
 */
public class ViewDeclarationLanguageFactoryTCKImpl extends ViewDeclarationLanguageFactory {

	// Private Data Members
	private ViewDeclarationLanguageFactory wrappedFactory;

	public ViewDeclarationLanguageFactoryTCKImpl(ViewDeclarationLanguageFactory wrappedFactory) {
		this.wrappedFactory = wrappedFactory;
	}

	@Override
	public ViewDeclarationLanguage getViewDeclarationLanguage(String viewId) {
		ViewDeclarationLanguage viewDeclarationLanguage = getWrapped().getViewDeclarationLanguage(viewId);

		if (viewDeclarationLanguage instanceof ViewDeclarationLanguageJspImpl) {
			viewDeclarationLanguage = new ViewDeclarationLanguageJspTCKImpl(viewDeclarationLanguage);
		}

		return viewDeclarationLanguage;
	}

	@Override
	public ViewDeclarationLanguageFactory getWrapped() {
		return wrappedFactory;
	}

}
