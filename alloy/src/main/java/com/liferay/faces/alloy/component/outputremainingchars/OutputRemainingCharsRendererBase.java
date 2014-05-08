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
package com.liferay.faces.alloy.component.outputremainingchars;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.renderkit.DelegatingAlloyRendererBase;
import com.liferay.faces.alloy.util.AlloyConstants;
import com.liferay.faces.util.lang.StringPool;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class OutputRemainingCharsRendererBase extends DelegatingAlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "OutputRemainingChars";
	private static final String ALLOY_MODULE_NAME = "aui-char-counter";
	private static final String COUNTER_CHANGE = "counterChange";
	private static final String DESTROYED_CHANGE = "destroyedChange";
	private static final String INITIALIZED_CHANGE = "initializedChange";
	private static final String INPUT_CHANGE = "inputChange";
	private static final String MAX_LENGTH_CHANGE = "maxLengthChange";

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		OutputRemainingCharsAlloy outputRemainingCharsAlloy = (OutputRemainingCharsAlloy) uiComponent;
		boolean first = true;

		String counter = outputRemainingCharsAlloy.getCounter();

		if (counter != null) {

			encodeCounter(responseWriter, outputRemainingCharsAlloy, counter, first);
			first = false;
		}

		Boolean destroyed = outputRemainingCharsAlloy.isDestroyed();

		if (destroyed != null) {

			encodeDestroyed(responseWriter, outputRemainingCharsAlloy, destroyed, first);
			first = false;
		}

		String for_ = outputRemainingCharsAlloy.getFor();

		if (for_ != null) {

			encodeFor(responseWriter, outputRemainingCharsAlloy, for_, first);
			first = false;
		}

		String id = outputRemainingCharsAlloy.getId();

		if (id != null) {

			encodeId(responseWriter, outputRemainingCharsAlloy, id, first);
			first = false;
		}

		Boolean initialized = outputRemainingCharsAlloy.isInitialized();

		if (initialized != null) {

			encodeInitialized(responseWriter, outputRemainingCharsAlloy, initialized, first);
			first = false;
		}

		String input = outputRemainingCharsAlloy.getInput();

		if (input != null) {

			encodeInput(responseWriter, outputRemainingCharsAlloy, input, first);
			first = false;
		}

		Object maxLength = outputRemainingCharsAlloy.getMaxLength();

		if (maxLength != null) {

			encodeMaxLength(responseWriter, outputRemainingCharsAlloy, maxLength, first);
			first = false;
		}

		String onceMaxlengthReached = outputRemainingCharsAlloy.getOnceMaxlengthReached();

		if (onceMaxlengthReached != null) {

			encodeOnceMaxlengthReached(responseWriter, outputRemainingCharsAlloy, onceMaxlengthReached, first);
			first = false;
		}

		String onMaxlengthReached = outputRemainingCharsAlloy.getOnMaxlengthReached();

		if (onMaxlengthReached != null) {

			encodeOnMaxlengthReached(responseWriter, outputRemainingCharsAlloy, onMaxlengthReached, first);
			first = false;
		}

		Boolean rendered = outputRemainingCharsAlloy.isRendered();

		if (rendered != null) {

			encodeRendered(responseWriter, outputRemainingCharsAlloy, rendered, first);
			first = false;
		}

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String afterCounterChange = outputRemainingCharsAlloy.getAfterCounterChange();

		if (afterCounterChange != null) {

			encodeAfterCounterChange(responseWriter, outputRemainingCharsAlloy, afterCounterChange, first);
			first = false;
		}

		String afterDestroyedChange = outputRemainingCharsAlloy.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, outputRemainingCharsAlloy, afterDestroyedChange, first);
			first = false;
		}

		String afterInitializedChange = outputRemainingCharsAlloy.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, outputRemainingCharsAlloy, afterInitializedChange, first);
			first = false;
		}

		String afterInputChange = outputRemainingCharsAlloy.getAfterInputChange();

		if (afterInputChange != null) {

			encodeAfterInputChange(responseWriter, outputRemainingCharsAlloy, afterInputChange, first);
			first = false;
		}

		String afterMaxLengthChange = outputRemainingCharsAlloy.getAfterMaxLengthChange();

		if (afterMaxLengthChange != null) {

			encodeAfterMaxLengthChange(responseWriter, outputRemainingCharsAlloy, afterMaxLengthChange, first);
			first = false;
		}

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String onCounterChange = outputRemainingCharsAlloy.getOnCounterChange();

		if (onCounterChange != null) {

			encodeOnCounterChange(responseWriter, outputRemainingCharsAlloy, onCounterChange, first);
			first = false;
		}

		String onDestroyedChange = outputRemainingCharsAlloy.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, outputRemainingCharsAlloy, onDestroyedChange, first);
			first = false;
		}

		String onInitializedChange = outputRemainingCharsAlloy.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, outputRemainingCharsAlloy, onInitializedChange, first);
			first = false;
		}

		String onInputChange = outputRemainingCharsAlloy.getOnInputChange();

		if (onInputChange != null) {

			encodeOnInputChange(responseWriter, outputRemainingCharsAlloy, onInputChange, first);
			first = false;
		}

		String onMaxLengthChange = outputRemainingCharsAlloy.getOnMaxLengthChange();

		if (onMaxLengthChange != null) {

			encodeOnMaxLengthChange(responseWriter, outputRemainingCharsAlloy, onMaxLengthChange, first);
			first = false;
		}

		// End encoding "on" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeAfterCounterChange(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String afterCounterChange, boolean first) throws IOException {
		encodeEvent(responseWriter, COUNTER_CHANGE, afterCounterChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterInputChange(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String afterInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INPUT_CHANGE, afterInputChange, first);
	}

	protected void encodeAfterMaxLengthChange(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String afterMaxLengthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MAX_LENGTH_CHANGE, afterMaxLengthChange, first);
	}

	protected void encodeCounter(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String counter, boolean first) throws IOException {
		encodeString(responseWriter, OutputRemainingCharsAlloy.COUNTER, counter, first);
	}

	protected void encodeDestroyed(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, Boolean destroyed, boolean first) throws IOException {
		encodeBoolean(responseWriter, OutputRemainingCharsAlloy.DESTROYED, destroyed, first);
	}

	protected void encodeFor(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String for_, boolean first) throws IOException {
		encodeString(responseWriter, OutputRemainingCharsAlloy.FOR, for_, first);
	}

	protected void encodeId(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String id, boolean first) throws IOException {
		encodeString(responseWriter, OutputRemainingCharsAlloy.ID, id, first);
	}

	protected void encodeInitialized(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, Boolean initialized, boolean first) throws IOException {
		encodeBoolean(responseWriter, OutputRemainingCharsAlloy.INITIALIZED, initialized, first);
	}

	protected void encodeInput(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String input, boolean first) throws IOException {
		encodeString(responseWriter, OutputRemainingCharsAlloy.INPUT, input, first);
	}

	protected void encodeMaxLength(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, Object maxLength, boolean first) throws IOException {
		encodeNumber(responseWriter, OutputRemainingCharsAlloy.MAX_LENGTH, maxLength, first);
	}

	protected void encodeOnceMaxlengthReached(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String onceMaxlengthReached, boolean first) throws IOException {
		encodeString(responseWriter, OutputRemainingCharsAlloy.ONCE_MAXLENGTH_REACHED, onceMaxlengthReached, first);
	}

	protected void encodeOnCounterChange(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String onCounterChange, boolean first) throws IOException {
		encodeEvent(responseWriter, COUNTER_CHANGE, onCounterChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnInputChange(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String onInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INPUT_CHANGE, onInputChange, first);
	}

	protected void encodeOnMaxLengthChange(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String onMaxLengthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MAX_LENGTH_CHANGE, onMaxLengthChange, first);
	}

	protected void encodeOnMaxlengthReached(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String onMaxlengthReached, boolean first) throws IOException {
		encodeString(responseWriter, OutputRemainingCharsAlloy.ON_MAXLENGTH_REACHED, onMaxlengthReached, first);
	}

	protected void encodeRendered(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, Boolean rendered, boolean first) throws IOException {
		encodeBoolean(responseWriter, OutputRemainingCharsAlloy.RENDERED, rendered, first);
	}
}
//J+
