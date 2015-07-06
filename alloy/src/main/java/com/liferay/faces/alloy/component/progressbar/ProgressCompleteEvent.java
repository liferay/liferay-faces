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
package com.liferay.faces.alloy.component.progressbar;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;


/**
 * @author  Kyle Stiemann
 */
public class ProgressCompleteEvent extends AjaxBehaviorEvent {

	// Public Constants
	public static final String PROGRESS_COMPLETE = "progressComplete";

	/// serialVersionUID
	private static final long serialVersionUID = 7578789532730213902L;

	public ProgressCompleteEvent(UIComponent component, Behavior behavior) {
		super(component, behavior);
		setPhaseId(PhaseId.INVOKE_APPLICATION);
	}
}
