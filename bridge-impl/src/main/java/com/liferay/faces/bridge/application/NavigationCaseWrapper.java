/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.application;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.faces.FacesWrapper;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public abstract class NavigationCaseWrapper extends NavigationCase implements FacesWrapper<NavigationCase> {

	public NavigationCaseWrapper() {
		super((String) null, (String) null, (String) null, (String) null, (String) null,
			(Map<String, List<String>>) null, false, false);
	}

	@Override
	public boolean equals(Object o) {
		return getWrapped().equals(o);
	}

	@Override
	public boolean hasCondition() {
		return getWrapped().hasCondition();
	}

	@Override
	public int hashCode() {
		return getWrapped().hashCode();
	}

	@Override
	public String toString() {
		return getWrapped().toString();
	}

	@Override
	public URL getActionURL(FacesContext context) throws MalformedURLException {
		return getWrapped().getActionURL(context);
	}

	@Override
	public URL getBookmarkableURL(FacesContext context) throws MalformedURLException {
		return getWrapped().getBookmarkableURL(context);
	}

	@Override
	public Boolean getCondition(FacesContext context) {
		return getWrapped().getCondition(context);
	}

	@Override
	public String getFromAction() {
		return getWrapped().getFromAction();
	}

	@Override
	public String getFromOutcome() {
		return getWrapped().getFromOutcome();
	}

	@Override
	public String getFromViewId() {
		return getWrapped().getFromViewId();
	}

	@Override
	public Map<String, List<String>> getParameters() {
		return getWrapped().getParameters();
	}

	@Override
	public URL getRedirectURL(FacesContext context) throws MalformedURLException {
		return getWrapped().getRedirectURL(context);
	}

	@Override
	public URL getResourceURL(FacesContext context) throws MalformedURLException {
		return getWrapped().getResourceURL(context);
	}

	@Override
	public boolean isIncludeViewParams() {
		return getWrapped().isIncludeViewParams();
	}

	@Override
	public boolean isRedirect() {
		return getWrapped().isRedirect();
	}

	@Override
	public String getToViewId(FacesContext context) {
		return getWrapped().getToViewId(context);
	}

	public abstract NavigationCase getWrapped();

}
