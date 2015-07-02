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

import java.util.ArrayList;
import java.util.List;

import com.liferay.faces.util.config.ConfiguredElement;
import com.liferay.faces.util.config.ConfiguredManagedBean;
import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.config.ConfiguredSystemEventListener;
import com.liferay.faces.util.config.FacesConfig;


/**
 * @author  Neil Griffin
 */
public class FacesConfigImpl implements FacesConfig {

	// Private Data Members
	private List<ConfiguredElement> configuredApplicationExtensions;
	private List<ConfiguredElement> configuredFactoryExtensions;
	private List<ConfiguredServletMapping> configuredFacesServletMappings;
	private List<ConfiguredManagedBean> configuredManagedBeans;
	private List<String> configuredSuffixes;
	private List<ConfiguredSystemEventListener> configuredSystemEventListeners;

	public FacesConfigImpl() {
		this.configuredApplicationExtensions = new ArrayList<ConfiguredElement>();
		this.configuredFacesServletMappings = new ArrayList<ConfiguredServletMapping>();
		this.configuredFactoryExtensions = new ArrayList<ConfiguredElement>();
		this.configuredManagedBeans = new ArrayList<ConfiguredManagedBean>();
		this.configuredSuffixes = new ArrayList<String>();
		this.configuredSystemEventListeners = new ArrayList<ConfiguredSystemEventListener>();
	}

	public FacesConfigImpl(List<ConfiguredServletMapping> configuredFacesServletMappings,
		List<String> configuredSuffixes) {
		this();
		this.configuredFacesServletMappings = configuredFacesServletMappings;
		this.configuredSuffixes = configuredSuffixes;
	}

	public FacesConfigImpl(List<ConfiguredElement> configuredApplicationExtensions,
		List<ConfiguredElement> configuredFactoryExtensions,
		List<ConfiguredServletMapping> configuredFacesServletMappings,
		List<ConfiguredManagedBean> configuredManagedBeans, List<String> configuredSuffixes,
		List<ConfiguredSystemEventListener> configuredSystemEventListeners) {

		this.configuredApplicationExtensions = configuredApplicationExtensions;
		this.configuredSuffixes = configuredSuffixes;
		this.configuredFacesServletMappings = configuredFacesServletMappings;
		this.configuredFactoryExtensions = configuredFactoryExtensions;
		this.configuredManagedBeans = configuredManagedBeans;
		this.configuredSystemEventListeners = configuredSystemEventListeners;
	}

	public List<ConfiguredElement> getConfiguredApplicationExtensions() {
		return configuredApplicationExtensions;
	}

	public List<ConfiguredServletMapping> getConfiguredFacesServletMappings() {
		return configuredFacesServletMappings;
	}

	public List<ConfiguredElement> getConfiguredFactoryExtensions() {
		return configuredFactoryExtensions;
	}

	public List<ConfiguredManagedBean> getConfiguredManagedBeans() {
		return configuredManagedBeans;
	}

	public List<String> getConfiguredSuffixes() {
		return configuredSuffixes;
	}

	public List<ConfiguredSystemEventListener> getConfiguredSystemEventListeners() {
		return configuredSystemEventListeners;
	}
}
