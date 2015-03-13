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
package com.liferay.faces.util.config.internal;

import java.io.Serializable;
import java.util.List;


/**
 * @author  Neil Griffin
 */
public class FacesConfigDescriptorImpl implements FacesConfigDescriptor, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 5479857091609259746L;

	// Private Data Members
	private String name;

	private boolean isWebInfFacesConfig;
	private List<String> absoluteOrdering;
	private Ordering ordering;

	private String url;

	public FacesConfigDescriptorImpl(FacesConfigDescriptor facesConfigDescriptor) {
		this.name = facesConfigDescriptor.getName();
		this.isWebInfFacesConfig = facesConfigDescriptor.isWebInfFacesConfig();
		this.absoluteOrdering = facesConfigDescriptor.getAbsoluteOrdering();
		this.ordering = facesConfigDescriptor.getOrdering();

//      String[][] routes = ordering.getRoutes();
//      System.err.println("FacesConfigDescriptorImpl: name = " + name + " routes[1][1] = " + routes[1][1]);
		this.url = facesConfigDescriptor.getURL();
	}

	public FacesConfigDescriptorImpl(String name, String url, boolean isWebInfFacesConfig,
		List<String> absoluteOrdering, Ordering ordering) {
		this.name = name;
		this.isWebInfFacesConfig = isWebInfFacesConfig;
		this.absoluteOrdering = absoluteOrdering;
		this.ordering = ordering;
		this.url = url;
	}

	@Override
	public List<String> getAbsoluteOrdering() {
		return absoluteOrdering;
	}

	@Override
	public boolean isWebInfFacesConfig() {
		return isWebInfFacesConfig;
	}

	@Override
	public String getName() {

		if (name == null) {
			name = "";
		}

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Ordering getOrdering() {
		return ordering;
	}

	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}

	@Override
	public String getURL() {
		return url;
	}

}
