/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.component.inputrichtext;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIInput;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputRichTextBase extends UIInput implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.inputrichtext.InputRichText";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.inputrichtext.InputRichTextRenderer";

	// Protected Enumerations
	protected enum InputRichTextPropertyKeys {
		configParams,
		contentsLanguageId,
		editorKey,
		fileBrowserParams,
		label,
		maxPlainTextChars,
		minPlainTextChars,
		onblur,
		onchange,
		onfocus,
		resizable,
		skipEditorLoading,
		style,
		styleClass,
		toolbarSet
	}

	public InputRichTextBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@SuppressWarnings("unchecked")
	public java.util.Map<String,String> getConfigParams() {
		return (java.util.Map<String,String>) getStateHelper().eval(InputRichTextPropertyKeys.configParams, null);
	}

	public void setConfigParams(java.util.Map<String,String> configParams) {
		getStateHelper().put(InputRichTextPropertyKeys.configParams, configParams);
	}

	public String getContentsLanguageId() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.contentsLanguageId, null);
	}

	public void setContentsLanguageId(String contentsLanguageId) {
		getStateHelper().put(InputRichTextPropertyKeys.contentsLanguageId, contentsLanguageId);
	}

	public String getEditorKey() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.editorKey, "editor.wysiwyg.default");
	}

	public void setEditorKey(String editorKey) {
		getStateHelper().put(InputRichTextPropertyKeys.editorKey, editorKey);
	}

	@SuppressWarnings("unchecked")
	public java.util.Map<String,String> getFileBrowserParams() {
		return (java.util.Map<String,String>) getStateHelper().eval(InputRichTextPropertyKeys.fileBrowserParams, null);
	}

	public void setFileBrowserParams(java.util.Map<String,String> fileBrowserParams) {
		getStateHelper().put(InputRichTextPropertyKeys.fileBrowserParams, fileBrowserParams);
	}

	public String getLabel() {

		String label = (String) getStateHelper().eval(InputRichTextPropertyKeys.label, null);

		if (label == null) {

			javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();

			if (facesContext.getCurrentPhaseId() == javax.faces.event.PhaseId.PROCESS_VALIDATIONS) {
				label = com.liferay.faces.util.component.ComponentUtil.getComponentLabel(this);
			}
		}

		return label;
	}

	public void setLabel(String label) {
		getStateHelper().put(InputRichTextPropertyKeys.label, label);
	}

	public int getMaxPlainTextChars() {
		return (Integer) getStateHelper().eval(InputRichTextPropertyKeys.maxPlainTextChars, Integer.MAX_VALUE);
	}

	public void setMaxPlainTextChars(int maxPlainTextChars) {
		getStateHelper().put(InputRichTextPropertyKeys.maxPlainTextChars, maxPlainTextChars);
	}

	public int getMinPlainTextChars() {
		return (Integer) getStateHelper().eval(InputRichTextPropertyKeys.minPlainTextChars, 0);
	}

	public void setMinPlainTextChars(int minPlainTextChars) {
		getStateHelper().put(InputRichTextPropertyKeys.minPlainTextChars, minPlainTextChars);
	}

	public String getOnblur() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.onblur, null);
	}

	public void setOnblur(String onblur) {
		getStateHelper().put(InputRichTextPropertyKeys.onblur, onblur);
	}

	public String getOnchange() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.onchange, null);
	}

	public void setOnchange(String onchange) {
		getStateHelper().put(InputRichTextPropertyKeys.onchange, onchange);
	}

	public String getOnfocus() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.onfocus, null);
	}

	public void setOnfocus(String onfocus) {
		getStateHelper().put(InputRichTextPropertyKeys.onfocus, onfocus);
	}

	public boolean isResizable() {
		return (Boolean) getStateHelper().eval(InputRichTextPropertyKeys.resizable, true);
	}

	public void setResizable(boolean resizable) {
		getStateHelper().put(InputRichTextPropertyKeys.resizable, resizable);
	}

	public boolean isSkipEditorLoading() {
		return (Boolean) getStateHelper().eval(InputRichTextPropertyKeys.skipEditorLoading, false);
	}

	public void setSkipEditorLoading(boolean skipEditorLoading) {
		getStateHelper().put(InputRichTextPropertyKeys.skipEditorLoading, skipEditorLoading);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(InputRichTextPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(InputRichTextPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(InputRichTextPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-input-rich-text");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(InputRichTextPropertyKeys.styleClass, styleClass);
	}

	public String getToolbarSet() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.toolbarSet, null);
	}

	public void setToolbarSet(String toolbarSet) {
		getStateHelper().put(InputRichTextPropertyKeys.toolbarSet, toolbarSet);
	}
}
//J+
