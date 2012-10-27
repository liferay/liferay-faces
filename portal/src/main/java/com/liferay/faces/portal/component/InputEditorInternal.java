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
package com.liferay.faces.portal.component;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.liferay.faces.portal.renderkit.InputEditorInternalRenderer;
import com.liferay.faces.util.component.UICleanup;


/**
 * @author  Neil Griffin
 */
public class InputEditorInternal extends UIOutput implements UICleanup {

	// Private Constants
	private static final String COMPONENT_TYPE = "com.liferay.faces.portal.InputEditorInternal";
	private static final String PREVIOUSLY_RENDERED = "previouslyRendered";
	private static final String RENDERER_TYPE = "com.liferay.faces.portal.InputEditorInternalRenderer";

	public InputEditorInternal() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public void encodeCleanup(FacesContext facesContext) throws IOException {
		InputEditorInternalRenderer inputEditorInternalRenderer = (InputEditorInternalRenderer) getRenderer(
				facesContext);
		inputEditorInternalRenderer.encodeCleanup(facesContext, this);
	}

	@Override
	public void restoreState(FacesContext facesContext, Object state) {
		Object[] _state = (Object[]) state;
		super.restoreState(facesContext, _state[0]);
		setPreviouslyRendered((Boolean) _state[1]);
	}

	@Override
	public Object saveState(FacesContext facesContext) {
		Object[] _state = new Object[3];
		_state[0] = super.saveState(facesContext);

		// FACES-1439: If this component (and all of its parent components in the tree) are currently rendered in the
		// view, then set the state of "previouslyRendered" to true. Otherwise, set the state to false.
		_state[1] = Boolean.TRUE;

		UIComponent uiComponent = this;

		while (!(uiComponent.getParent() instanceof UIViewRoot)) {

			if (!uiComponent.isRendered()) {
				_state[1] = Boolean.FALSE;

				break;
			}

			uiComponent = uiComponent.getParent();
		}

		return _state;
	}

	public boolean isPreviouslyRendered() {
		return (Boolean) getStateHelper().eval(PREVIOUSLY_RENDERED, Boolean.FALSE);
	}

	@Override
	public String getFamily() {
		return COMPONENT_TYPE;
	}

	public void setPreviouslyRendered(boolean previouslyRendered) {
		getStateHelper().put(PREVIOUSLY_RENDERED, previouslyRendered);
	}

}
