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

		String for_ = outputRemainingCharsAlloy.getFor();

		if (for_ != null) {

			encodeFor(responseWriter, outputRemainingCharsAlloy, for_, first);
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
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeCounter(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String counter, boolean first) throws IOException {
		encodeString(responseWriter, OutputRemainingCharsAlloy.COUNTER, counter, first);
	}

	protected void encodeFor(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String for_, boolean first) throws IOException {
		encodeString(responseWriter, OutputRemainingCharsAlloy.FOR, for_, first);
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

	protected void encodeOnMaxlengthReached(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy, String onMaxlengthReached, boolean first) throws IOException {
		encodeString(responseWriter, OutputRemainingCharsAlloy.ON_MAXLENGTH_REACHED, onMaxlengthReached, first);
	}
}
//J+
