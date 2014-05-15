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
package com.liferay.faces.alloy.component.popover;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;

/**
 * @author Vernon Singleton
 */
@FacesComponent(value = Popover.COMPONENT_TYPE)
public class Popover extends PopoverBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.popover.Popover";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.popover.PopoverRenderer";
	
	private static final boolean LIFERAY_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
			.isDetected();
	private static final String LIFERAY_Z_INDEX_TOOLTIP = "Liferay.zIndex.TOOLTIP";

	public Popover() {
		super();
		setRendererType(RENDERER_TYPE);
	}
	
	@Override
	public Object getAlign() {
		Object align = super.getAlign();

		if (align == null) {
			align = "{ node: '" + getForClientIdEscaped(getFacesContext()) + "' }";
		}

		return align;
	}
	
	// Returns the clientId of the component that the "for" attribute points to.
	protected String getForClientId(FacesContext facesContext) {

		String forClientId = null;

		String forComponent = getFor();

		if (forComponent != null) {
			UIComponent uiComponent = findComponent(forComponent);

			if (uiComponent == null) {
				forClientId = forComponent;
			} else {
				forClientId = uiComponent.getClientId(facesContext);
			}
		}

		return forClientId;
	}

	public String getForClientIdEscaped(FacesContext facesContext) {
		return StringPool.POUND	+ ComponentUtil.escapeClientId(getForClientId(facesContext));
	}
	
	@Override
	public Object getzIndex() {

		Object zIndex = super.getzIndex();

		if ((zIndex == null) && LIFERAY_DETECTED) {
			zIndex = LIFERAY_Z_INDEX_TOOLTIP;
		}

		return zIndex;
	}
	
	@Override
	public Boolean isWidgetRender() {
		return (Boolean) getStateHelper().eval(WIDGET_RENDER, true);
	}
}
