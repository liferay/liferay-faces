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
package com.liferay.faces.demos.list;

import com.liferay.portal.kernel.workflow.WorkflowConstants;


/**
 * @author  Neil Griffin
 */
public class SearchCriteria {

	private String andSearch = Boolean.FALSE.toString();
	private String emailAddress;
	private boolean formatExpressions = false;
	private String firstName;
	private String keywords;
	private String lastName;
	private String middleName;
	private String screenName;
	private String status = Integer.toString(WorkflowConstants.STATUS_ANY);

	protected String formatExpression(String value) {

		String expression = null;

		if (value != null) {
			String trimmedValue = value.trim();

			if (trimmedValue.length() > 0) {
				expression = "%" + trimmedValue.toLowerCase() + "%";
			}
		}

		return expression;
	}

	public String getAndSearch() {
		return andSearch;
	}

	public void setAndSearch(String andSearch) {
		this.andSearch = andSearch;
	}

	public String getEmailAddress() {

		if (formatExpressions) {
			return formatExpression(emailAddress);
		}
		else {
			return emailAddress;
		}
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {

		if (formatExpressions) {
			return formatExpression(firstName);
		}
		else {
			return firstName;
		}
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setFormatExpressions(boolean formatExpressions) {
		this.formatExpressions = formatExpressions;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getLastName() {

		if (formatExpressions) {
			return formatExpression(lastName);
		}
		else {
			return lastName;
		}
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {

		if (formatExpressions) {
			return formatExpression(middleName);
		}
		else {
			return middleName;
		}
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public boolean isFormatExpressions() {
		return formatExpressions;
	}

	public String getScreenName() {

		if (formatExpressions) {
			return formatExpression(screenName);
		}
		else {
			return screenName;
		}
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
