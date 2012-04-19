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

import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.liferay.faces.bridge.container.liferay.LiferayConstants;
import com.liferay.faces.bridge.helper.BooleanHelper;


/**
 * @author  Neil Griffin
 */
public class FormRendererBridgeImpl extends RendererWrapper {

	// Private Data Members
	private Renderer wrappedRenderer;

	public FormRendererBridgeImpl(Renderer renderer) {
		this.wrappedRenderer = renderer;
	}

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {
		super.decode(facesContext, uiComponent);

		Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
		String formClientId = uiComponent.getClientId();
		UIForm uiForm = (UIForm) uiComponent;

		if (!uiForm.isSubmitted()) {

			if (BooleanHelper.isTrueToken(requestParameterMap.get(LiferayConstants.WSRP))) {

				Set<String> parameterNames = requestParameterMap.keySet();

				for (String parameterName : parameterNames) {
					System.err.println("!@#$ WSRP FORM DOUBLE-CHECK formClientId=" + formClientId + " parameterName=" +
						parameterName + " value=" + requestParameterMap.get(parameterName));

					if (parameterName.startsWith(formClientId)) {
						uiForm.setSubmitted(true);

						break;
					}
				}
			}
		}
		else {
			System.err.println("!@#$ FORM WAS SUBMITTED! formClientId=" + formClientId);
		}
	}

	@Override
	public Renderer getWrapped() {
		return wrappedRenderer;
	}

}
