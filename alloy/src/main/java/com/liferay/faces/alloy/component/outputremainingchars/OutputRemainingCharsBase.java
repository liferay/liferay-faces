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
	public String getFor() {
		return (String) getStateHelper().eval(FOR, null);
	}

	@Override
	public void setFor(String for_) {
		getStateHelper().put(FOR, for_);
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
	public String getOnMaxlengthReached() {
		return (String) getStateHelper().eval(ON_MAXLENGTH_REACHED, null);
	}

	@Override
	public void setOnMaxlengthReached(String onMaxlengthReached) {
		getStateHelper().put(ON_MAXLENGTH_REACHED, onMaxlengthReached);
	}
}
//J+
