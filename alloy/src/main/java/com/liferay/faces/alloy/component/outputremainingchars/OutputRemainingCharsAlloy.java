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

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public interface OutputRemainingCharsAlloy {

	// Public Constants
	public static final String COUNTER = "counter";
	public static final String FOR = "for";
	public static final String INPUT = "input";
	public static final String MAX_LENGTH = "maxLength";
	public static final String ON_MAXLENGTH_REACHED = "onMaxlengthReached";
	public static final String ONCE_MAXLENGTH_REACHED = "onceMaxlengthReached";

	public String getCounter();

	public void setCounter(String counter);

	public String getFor();

	public void setFor(String for_);

	public String getInput();

	public void setInput(String input);

	public Object getMaxLength();

	public void setMaxLength(Object maxLength);

	public String getOnceMaxlengthReached();

	public void setOnceMaxlengthReached(String onceMaxlengthReached);

	public String getOnMaxlengthReached();

	public void setOnMaxlengthReached(String onMaxlengthReached);
}
//J+
