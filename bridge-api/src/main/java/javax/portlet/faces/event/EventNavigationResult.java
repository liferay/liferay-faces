/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package javax.portlet.faces.event;

/**
 * @author  Neil Griffin
 */
public class EventNavigationResult {

	private String fromAction;
	private String outcome;

	public EventNavigationResult() {
	}

	public EventNavigationResult(String fromAction, String outcome) {
		this.fromAction = fromAction;
		this.outcome = outcome;
	}

	public String getFromAction() {
		return fromAction;
	}

	public void setFromAction(String fromAction) {
		this.fromAction = fromAction;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
}
