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

import com.liferay.faces.portal.renderkit.InputEditorInternalRenderer;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

/**
 * @author  Neil Griffin
 */
public class InputEditorInternal extends UIOutput {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputEditorInternalRenderer.class);

	private static final String COMPONENT_TYPE = "com.liferay.faces.portal.InputEditorInternal";
	private static final String RENDERER_TYPE = "com.liferay.faces.portal.InputEditorInternalRenderer";

	private static final String CURRENTLY_VISIBLE = "currentlyVisible";
	private static final String PREVIOUSLY_VISIBLE = "previouslyVisible";

	public InputEditorInternal() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getFamily() {
		return COMPONENT_TYPE;
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		Object[] _state = (Object[]) state;
		super.restoreState(context, _state[0]);
		_setCurrentlyVisible(_state[1]);
		_setPreviouslyVisible(_state[2]);

		if (logger.isDebugEnabled()) {
			logger.debug("restoreState: " + _state[1] + _state[2]);
		}
	}

	@Override
	public Object saveState(FacesContext context) {
		Object[] _state = new Object[3];
		_state[0] = super.saveState(context);
		_state[1] = _isCurrentlyVisible();
		_state[2] = _isPreviouslyVisible();

		if (logger.isDebugEnabled()) {
			logger.debug("saveState: " + _state[1] + _state[2]);
		}

		return _state;
	}

	public boolean isVisible() {
		String _currentVisibility = _isCurrentlyVisible().toString();
		return Boolean.valueOf(_currentVisibility);
	}

	public boolean isPreviouslyVisible() {
		String _previousVisibility = _isPreviouslyVisible().toString();
		return Boolean.valueOf(_previousVisibility);
	}

	public void setVisible(boolean visible) {
		boolean _previousVisibility = isVisible();
		_setPreviouslyVisible(_previousVisibility);
		_setCurrentlyVisible(Boolean.valueOf(visible));
	}

	private Object _isCurrentlyVisible() {
		return getStateHelper().eval(
			CURRENTLY_VISIBLE, Boolean.TRUE);
	}

	private Object _isPreviouslyVisible() {
		return getStateHelper().eval(
			PREVIOUSLY_VISIBLE, Boolean.FALSE);
	}

	private void _setCurrentlyVisible(Object currentlyVisible) {
		getStateHelper().put(CURRENTLY_VISIBLE, currentlyVisible);
	}

	private void _setPreviouslyVisible(Object previouslyVisible) {
		getStateHelper().put(PREVIOUSLY_VISIBLE, previouslyVisible);
	}

}
