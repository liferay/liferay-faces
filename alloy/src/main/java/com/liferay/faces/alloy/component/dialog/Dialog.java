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
package com.liferay.faces.alloy.component.dialog;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = Dialog.COMPONENT_TYPE)
public class Dialog extends DialogBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.dialog.Dialog";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.dialog.DialogRenderer";

	public static final String DELEGATE_COMPONENT_FAMILY = COMPONENT_FAMILY;
	public static final String DELEGATE_RENDERER_TYPE = "javax.faces.Group";

	public static final String STYLE_CLASS_NAME = "alloy-dialog";
	public static final String DEFAULT_LAYOUT = "block";
	private static final String LIFERAY_Z_INDEX_OVERLAY = "Liferay.zIndex.OVERLAY";

	public Dialog() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public Object getzIndex() {

		Object zIndex = super.getzIndex();

		if (zIndex == null) {
			zIndex = LIFERAY_Z_INDEX_OVERLAY;
		}

		return zIndex;
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(CLIENT_KEY, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(CLIENT_KEY, clientKey);
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

	@Override
	public void setDisabled(boolean disabled) {
		getStateHelper().put(DISABLED, disabled);
	}

	// Returns the clientId of the component that the "for" attribute points to.
	protected String getForClientId(FacesContext facesContext) {

		String forClientId = null;

		String forComponent = getFor();

		if (forComponent != null) {
			UIComponent uiComponent = findComponent(forComponent);

			if (uiComponent == null) {
				forClientId = forComponent;
			}
			else {
				forClientId = uiComponent.getClientId(facesContext);
			}
		}

		return forClientId;
	}

	public String getForClientIdEscaped(FacesContext facesContext) {
		return StringPool.POUND + ComponentUtil.escapeClientId(getForClientId(facesContext));
	}

	@Override
	public Boolean isModal() {
		Boolean modal = super.isModal();

		if (modal == null) {
			modal = true;
		}

		return modal;
	}

	@Override
	public String getLayout() {
		String layout = super.getLayout();

		if (layout == null) {
			layout = DEFAULT_LAYOUT;
		}

		return layout;
	}

	@Override
	public String getStyle() {
		String style = super.getStyle();

		// do not blink
		if (style == null) {
			style = "display: none;";
		}
		else {
			style = style + "; display: none;";
		}

		return style;
	}

	@Override
	public String getStyleClass() {

		String styleClass = (String) getStateHelper().eval(STYLE_CLASS, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}

	@Override
	public Boolean isAutoShow() {
		Boolean autoShow = super.isAutoShow();

		if (autoShow == null) {
			autoShow = false;
		}

		return autoShow;
	}

}
