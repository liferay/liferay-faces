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

import javax.annotation.Generated;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public interface CharCounterAlloy {

	// Public Constants
	public static final String AFTER_COUNTER_CHANGE = "afterCounterChange";
	public static final String AFTER_DESTROYED_CHANGE = "afterDestroyedChange";
	public static final String AFTER_INITIALIZED_CHANGE = "afterInitializedChange";
	public static final String AFTER_INPUT_CHANGE = "afterInputChange";
	public static final String AFTER_MAX_LENGTH_CHANGE = "afterMaxLengthChange";
	public static final String COUNTER = "counter";
	public static final String DESTROYED = "destroyed";
	public static final String FOR = "for";
	public static final String ID = "id";
	public static final String INITIALIZED = "initialized";
	public static final String INPUT = "input";
	public static final String MAX_LENGTH = "maxLength";
	public static final String ON_COUNTER_CHANGE = "onCounterChange";
	public static final String ON_DESTROYED_CHANGE = "onDestroyedChange";
	public static final String ON_INITIALIZED_CHANGE = "onInitializedChange";
	public static final String ON_INPUT_CHANGE = "onInputChange";
	public static final String ON_MAX_LENGTH_CHANGE = "onMaxLengthChange";
	public static final String ON_MAXLENGTH_REACHED = "onMaxlengthReached";
	public static final String ONCE_MAXLENGTH_REACHED = "onceMaxlengthReached";
	public static final String RENDERED = "rendered";

	public String getAfterCounterChange();

	public void setAfterCounterChange(String afterCounterChange);

	public String getAfterDestroyedChange();

	public void setAfterDestroyedChange(String afterDestroyedChange);

	public String getAfterInitializedChange();

	public void setAfterInitializedChange(String afterInitializedChange);

	public String getAfterInputChange();

	public void setAfterInputChange(String afterInputChange);

	public String getAfterMaxLengthChange();

	public void setAfterMaxLengthChange(String afterMaxLengthChange);

	public String getCounter();

	public void setCounter(String counter);

	public Boolean isDestroyed();

	public void setDestroyed(Boolean destroyed);

	public String getFor();

	public void setFor(String for_);

	public String getId();

	public void setId(String id);

	public Boolean isInitialized();

	public void setInitialized(Boolean initialized);

	public String getInput();

	public void setInput(String input);

	public Object getMaxLength();

	public void setMaxLength(Object maxLength);

	public String getOnceMaxlengthReached();

	public void setOnceMaxlengthReached(String onceMaxlengthReached);

	public String getOnCounterChange();

	public void setOnCounterChange(String onCounterChange);

	public String getOnDestroyedChange();

	public void setOnDestroyedChange(String onDestroyedChange);

	public String getOnInitializedChange();

	public void setOnInitializedChange(String onInitializedChange);

	public String getOnInputChange();

	public void setOnInputChange(String onInputChange);

	public String getOnMaxLengthChange();

	public void setOnMaxLengthChange(String onMaxLengthChange);

	public String getOnMaxlengthReached();

	public void setOnMaxlengthReached(String onMaxlengthReached);

	public boolean isRendered();

	public void setRendered(boolean rendered);
}
//J+
