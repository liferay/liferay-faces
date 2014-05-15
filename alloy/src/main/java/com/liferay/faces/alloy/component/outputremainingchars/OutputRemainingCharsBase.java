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

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.outputtext.OutputText;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class OutputRemainingCharsBase extends OutputText implements Styleable, ClientComponent, OutputRemainingCharsAlloy {

	@Override
	public String getAfterCounterChange() {
		return (String) getStateHelper().eval(AFTER_COUNTER_CHANGE, null);
	}

	@Override
	public void setAfterCounterChange(String afterCounterChange) {
		getStateHelper().put(AFTER_COUNTER_CHANGE, afterCounterChange);
	}

	@Override
	public String getAfterDestroyedChange() {
		return (String) getStateHelper().eval(AFTER_DESTROYED_CHANGE, null);
	}

	@Override
	public void setAfterDestroyedChange(String afterDestroyedChange) {
		getStateHelper().put(AFTER_DESTROYED_CHANGE, afterDestroyedChange);
	}

	@Override
	public String getAfterInitializedChange() {
		return (String) getStateHelper().eval(AFTER_INITIALIZED_CHANGE, null);
	}

	@Override
	public void setAfterInitializedChange(String afterInitializedChange) {
		getStateHelper().put(AFTER_INITIALIZED_CHANGE, afterInitializedChange);
	}

	@Override
	public String getAfterInputChange() {
		return (String) getStateHelper().eval(AFTER_INPUT_CHANGE, null);
	}

	@Override
	public void setAfterInputChange(String afterInputChange) {
		getStateHelper().put(AFTER_INPUT_CHANGE, afterInputChange);
	}

	@Override
	public String getAfterMaxLengthChange() {
		return (String) getStateHelper().eval(AFTER_MAX_LENGTH_CHANGE, null);
	}

	@Override
	public void setAfterMaxLengthChange(String afterMaxLengthChange) {
		getStateHelper().put(AFTER_MAX_LENGTH_CHANGE, afterMaxLengthChange);
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
	public String getCounter() {
		return (String) getStateHelper().eval(COUNTER, null);
	}

	@Override
	public void setCounter(String counter) {
		getStateHelper().put(COUNTER, counter);
	}

	@Override
	public Boolean isDestroyed() {
		return (Boolean) getStateHelper().eval(DESTROYED, null);
	}

	@Override
	public void setDestroyed(Boolean destroyed) {
		getStateHelper().put(DESTROYED, destroyed);
	}

	@Override
	public String getFor() {
		return (String) getStateHelper().eval(FOR, null);
	}

	@Override
	public void setFor(String for_) {
		getStateHelper().put(FOR, for_);
	}

	@Override
	public Boolean isInitialized() {
		return (Boolean) getStateHelper().eval(INITIALIZED, null);
	}

	@Override
	public void setInitialized(Boolean initialized) {
		getStateHelper().put(INITIALIZED, initialized);
	}

	@Override
	public String getInput() {
		return (String) getStateHelper().eval(INPUT, null);
	}

	@Override
	public void setInput(String input) {
		getStateHelper().put(INPUT, input);
	}

	@Override
	public Object getMaxLength() {
		return (Object) getStateHelper().eval(MAX_LENGTH, null);
	}

	@Override
	public void setMaxLength(Object maxLength) {
		getStateHelper().put(MAX_LENGTH, maxLength);
	}

	@Override
	public String getOnceMaxlengthReached() {
		return (String) getStateHelper().eval(ONCE_MAXLENGTH_REACHED, null);
	}

	@Override
	public void setOnceMaxlengthReached(String onceMaxlengthReached) {
		getStateHelper().put(ONCE_MAXLENGTH_REACHED, onceMaxlengthReached);
	}

	@Override
	public String getOnCounterChange() {
		return (String) getStateHelper().eval(ON_COUNTER_CHANGE, null);
	}

	@Override
	public void setOnCounterChange(String onCounterChange) {
		getStateHelper().put(ON_COUNTER_CHANGE, onCounterChange);
	}

	@Override
	public String getOnDestroyedChange() {
		return (String) getStateHelper().eval(ON_DESTROYED_CHANGE, null);
	}

	@Override
	public void setOnDestroyedChange(String onDestroyedChange) {
		getStateHelper().put(ON_DESTROYED_CHANGE, onDestroyedChange);
	}

	@Override
	public String getOnInitializedChange() {
		return (String) getStateHelper().eval(ON_INITIALIZED_CHANGE, null);
	}

	@Override
	public void setOnInitializedChange(String onInitializedChange) {
		getStateHelper().put(ON_INITIALIZED_CHANGE, onInitializedChange);
	}

	@Override
	public String getOnInputChange() {
		return (String) getStateHelper().eval(ON_INPUT_CHANGE, null);
	}

	@Override
	public void setOnInputChange(String onInputChange) {
		getStateHelper().put(ON_INPUT_CHANGE, onInputChange);
	}

	@Override
	public String getOnMaxLengthChange() {
		return (String) getStateHelper().eval(ON_MAX_LENGTH_CHANGE, null);
	}

	@Override
	public void setOnMaxLengthChange(String onMaxLengthChange) {
		getStateHelper().put(ON_MAX_LENGTH_CHANGE, onMaxLengthChange);
	}

	@Override
	public String getOnMaxlengthReached() {
		return (String) getStateHelper().eval(ON_MAXLENGTH_REACHED, null);
	}

	@Override
	public void setOnMaxlengthReached(String onMaxlengthReached) {
		getStateHelper().put(ON_MAXLENGTH_REACHED, onMaxlengthReached);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(STYLE, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(STYLE, style);
	}

	@Override
	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}
}
//J+
