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
package com.liferay.faces.alloy.component.charcounter;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.renderkit.AlloyRendererBase;
import com.liferay.faces.alloy.util.AlloyConstants;
import com.liferay.faces.util.lang.StringPool;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class CharCounterRendererBase extends AlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "CharCounter";
	private static final String ALLOY_MODULE_NAME = "aui-char-counter";
	private static final String COUNTER_CHANGE = "counterChange";
	private static final String DESTROYED_CHANGE = "destroyedChange";
	private static final String INITIALIZED_CHANGE = "initializedChange";
	private static final String INPUT_CHANGE = "inputChange";
	private static final String MAX_LENGTH_CHANGE = "maxLengthChange";

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	protected void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		CharCounterAlloy charCounterAlloy = (CharCounterAlloy) uiComponent;
		boolean first = true;

		String counter = charCounterAlloy.getCounter();

		if (counter != null) {

			encodeCounter(responseWriter, charCounterAlloy, counter, first);
			first = false;
		}

		Boolean destroyed = charCounterAlloy.isDestroyed();

		if (destroyed != null) {

			encodeDestroyed(responseWriter, charCounterAlloy, destroyed, first);
			first = false;
		}

		String for_ = charCounterAlloy.getFor();

		if (for_ != null) {

			encodeFor(responseWriter, charCounterAlloy, for_, first);
			first = false;
		}

		String id = charCounterAlloy.getId();

		if (id != null) {

			encodeId(responseWriter, charCounterAlloy, id, first);
			first = false;
		}

		Boolean initialized = charCounterAlloy.isInitialized();

		if (initialized != null) {

			encodeInitialized(responseWriter, charCounterAlloy, initialized, first);
			first = false;
		}

		String input = charCounterAlloy.getInput();

		if (input != null) {

			encodeInput(responseWriter, charCounterAlloy, input, first);
			first = false;
		}

		Object maxLength = charCounterAlloy.getMaxLength();

		if (maxLength != null) {

			encodeMaxLength(responseWriter, charCounterAlloy, maxLength, first);
			first = false;
		}

		String onceMaxlengthReached = charCounterAlloy.getOnceMaxlengthReached();

		if (onceMaxlengthReached != null) {

			encodeOnceMaxlengthReached(responseWriter, charCounterAlloy, onceMaxlengthReached, first);
			first = false;
		}

		String onMaxlengthReached = charCounterAlloy.getOnMaxlengthReached();

		if (onMaxlengthReached != null) {

			encodeOnMaxlengthReached(responseWriter, charCounterAlloy, onMaxlengthReached, first);
			first = false;
		}

		Boolean rendered = charCounterAlloy.isRendered();

		if (rendered != null) {

			encodeRendered(responseWriter, charCounterAlloy, rendered, first);
			first = false;
		}

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String afterCounterChange = charCounterAlloy.getAfterCounterChange();

		if (afterCounterChange != null) {

			encodeAfterCounterChange(responseWriter, charCounterAlloy, afterCounterChange, first);
			first = false;
		}

		String afterDestroyedChange = charCounterAlloy.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, charCounterAlloy, afterDestroyedChange, first);
			first = false;
		}

		String afterInitializedChange = charCounterAlloy.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, charCounterAlloy, afterInitializedChange, first);
			first = false;
		}

		String afterInputChange = charCounterAlloy.getAfterInputChange();

		if (afterInputChange != null) {

			encodeAfterInputChange(responseWriter, charCounterAlloy, afterInputChange, first);
			first = false;
		}

		String afterMaxLengthChange = charCounterAlloy.getAfterMaxLengthChange();

		if (afterMaxLengthChange != null) {

			encodeAfterMaxLengthChange(responseWriter, charCounterAlloy, afterMaxLengthChange, first);
			first = false;
		}

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String onCounterChange = charCounterAlloy.getOnCounterChange();

		if (onCounterChange != null) {

			encodeOnCounterChange(responseWriter, charCounterAlloy, onCounterChange, first);
			first = false;
		}

		String onDestroyedChange = charCounterAlloy.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, charCounterAlloy, onDestroyedChange, first);
			first = false;
		}

		String onInitializedChange = charCounterAlloy.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, charCounterAlloy, onInitializedChange, first);
			first = false;
		}

		String onInputChange = charCounterAlloy.getOnInputChange();

		if (onInputChange != null) {

			encodeOnInputChange(responseWriter, charCounterAlloy, onInputChange, first);
			first = false;
		}

		String onMaxLengthChange = charCounterAlloy.getOnMaxLengthChange();

		if (onMaxLengthChange != null) {

			encodeOnMaxLengthChange(responseWriter, charCounterAlloy, onMaxLengthChange, first);
			first = false;
		}

		// End encoding "on" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	protected String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeAfterCounterChange(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String afterCounterChange, boolean first) throws IOException {
		encodeEvent(responseWriter, COUNTER_CHANGE, afterCounterChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterInputChange(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String afterInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INPUT_CHANGE, afterInputChange, first);
	}

	protected void encodeAfterMaxLengthChange(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String afterMaxLengthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MAX_LENGTH_CHANGE, afterMaxLengthChange, first);
	}

	protected void encodeCounter(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String counter, boolean first) throws IOException {
		encodeString(responseWriter, CharCounterAlloy.COUNTER, counter, first);
	}

	protected void encodeDestroyed(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, Boolean destroyed, boolean first) throws IOException {
		encodeBoolean(responseWriter, CharCounterAlloy.DESTROYED, destroyed, first);
	}

	protected void encodeFor(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String for_, boolean first) throws IOException {
		encodeString(responseWriter, CharCounterAlloy.FOR, for_, first);
	}

	protected void encodeId(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String id, boolean first) throws IOException {
		encodeString(responseWriter, CharCounterAlloy.ID, id, first);
	}

	protected void encodeInitialized(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, Boolean initialized, boolean first) throws IOException {
		encodeBoolean(responseWriter, CharCounterAlloy.INITIALIZED, initialized, first);
	}

	protected void encodeInput(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String input, boolean first) throws IOException {
		encodeString(responseWriter, CharCounterAlloy.INPUT, input, first);
	}

	protected void encodeMaxLength(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, Object maxLength, boolean first) throws IOException {
		encodeNumber(responseWriter, CharCounterAlloy.MAX_LENGTH, maxLength, first);
	}

	protected void encodeOnceMaxlengthReached(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String onceMaxlengthReached, boolean first) throws IOException {
		encodeString(responseWriter, CharCounterAlloy.ONCE_MAXLENGTH_REACHED, onceMaxlengthReached, first);
	}

	protected void encodeOnCounterChange(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String onCounterChange, boolean first) throws IOException {
		encodeEvent(responseWriter, COUNTER_CHANGE, onCounterChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnInputChange(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String onInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INPUT_CHANGE, onInputChange, first);
	}

	protected void encodeOnMaxLengthChange(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String onMaxLengthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MAX_LENGTH_CHANGE, onMaxLengthChange, first);
	}

	protected void encodeOnMaxlengthReached(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, String onMaxlengthReached, boolean first) throws IOException {
		encodeString(responseWriter, CharCounterAlloy.ON_MAXLENGTH_REACHED, onMaxlengthReached, first);
	}

	protected void encodeRendered(ResponseWriter responseWriter, CharCounterAlloy charCounterAlloy, Boolean rendered, boolean first) throws IOException {
		encodeBoolean(responseWriter, CharCounterAlloy.RENDERED, rendered, first);
	}
}
//J+
