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
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class OutputRemainingCharsBase extends OutputText implements Styleable, ClientComponent {

	// Protected Enumerations
	protected enum OutputRemainingCharsPropertyKeys {
		clientKey,
		for_,
		maxLength,
		onMaxlengthReached,
		onceMaxlengthReached
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(OutputRemainingCharsPropertyKeys.clientKey, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(OutputRemainingCharsPropertyKeys.clientKey, clientKey);
	}

	public String getFor() {
		return (String) getStateHelper().eval(OutputRemainingCharsPropertyKeys.for_, null);
	}

	public void setFor(String for_) {
		getStateHelper().put(OutputRemainingCharsPropertyKeys.for_, for_);
	}

	public Integer getMaxLength() {
		return (Integer) getStateHelper().eval(OutputRemainingCharsPropertyKeys.maxLength, null);
	}

	public void setMaxLength(Integer maxLength) {
		getStateHelper().put(OutputRemainingCharsPropertyKeys.maxLength, maxLength);
	}

	public String getOnceMaxlengthReached() {
		return (String) getStateHelper().eval(OutputRemainingCharsPropertyKeys.onceMaxlengthReached, null);
	}

	public void setOnceMaxlengthReached(String onceMaxlengthReached) {
		getStateHelper().put(OutputRemainingCharsPropertyKeys.onceMaxlengthReached, onceMaxlengthReached);
	}

	public String getOnMaxlengthReached() {
		return (String) getStateHelper().eval(OutputRemainingCharsPropertyKeys.onMaxlengthReached, null);
	}

	public void setOnMaxlengthReached(String onMaxlengthReached) {
		getStateHelper().put(OutputRemainingCharsPropertyKeys.onMaxlengthReached, onMaxlengthReached);
	}
}
//J+
