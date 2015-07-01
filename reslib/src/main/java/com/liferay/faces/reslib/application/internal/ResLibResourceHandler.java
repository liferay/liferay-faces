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
package com.liferay.faces.reslib.application.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.reslib.config.ResLibConfigParam;
import com.liferay.faces.util.HttpHeaders;
import com.liferay.faces.util.application.ResourceHandlerWrapperBase;
import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.config.FacesConfig;
import com.liferay.faces.util.io.ResourceOutputStream;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * This is a resource handler that is only necessary in a non-Liferay (webapp) environment. The purpose of this class is
 * to provide Alloy/YUI/Bootstrap resources.
 *
 * @author  Neil Griffin
 */
public class ResLibResourceHandler extends ResourceHandlerWrapperBase {

	// Public Constants
	public static final String LIBRARY_NAME = "liferay-faces-reslib";

	// Private Constants
	private static final String LIFERAY_JS = "liferay.js";
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();
	private static final String MINIFIER_TYPE = "minifierType";
	private static final Set<String> PROTECTED_PARAMTERS;

	static {
		PROTECTED_PARAMTERS = new HashSet<String>();
		PROTECTED_PARAMTERS.add("b");
		PROTECTED_PARAMTERS.add("browserId");
		PROTECTED_PARAMTERS.add("languageId");
		PROTECTED_PARAMTERS.add("ln");
		PROTECTED_PARAMTERS.add(MINIFIER_TYPE);
		PROTECTED_PARAMTERS.add("t");
	}

	public ResLibResourceHandler(ResourceHandler resourceHandler) {
		super(resourceHandler);
	}

	@Override
	public Resource createResource(String resourceName, String libraryName) {

		if (!LIFERAY_PORTAL_DETECTED && LIBRARY_NAME.equals(libraryName)) {

			if (ComboResource.RESOURCE_NAME.equals(resourceName)) {
				return new ComboResource();
			}
			else if (ScriptResource.RESOURCE_NAME.equals(resourceName)) {
				return new ScriptResource();
			}
			else {
				return super.createResource(resourceName, libraryName);
			}
		}
		else {
			return super.createResource(resourceName, libraryName);
		}
	}

	@Override
	public void handleResourceRequest(FacesContext facesContext) throws IOException {

		if (LIFERAY_PORTAL_DETECTED) {
			getWrapped().handleResourceRequest(facesContext);
		}
		else {
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
			String libraryName = requestParameterMap.get("ln");

			if (LIBRARY_NAME.equals(libraryName)) {

				String resourceName = getResourceName(externalContext);

				// If the resource that is to be rendered is "liferay.js" then let this resource handler have an
				// opportunity to expand EL expressions before writing the contents of the resource to the response.
				if (LIFERAY_JS.equals(resourceName)) {
					Resource resource = createResource(resourceName, libraryName);
					handleResource(facesContext, resource);
				}

				// Otherwise, if the resource that is to be rendered is a combo or script module resource, then let this
				// resource handler write the contents of the resource to the response.
				else if (ComboResource.RESOURCE_NAME.equals(resourceName) ||
						ScriptResource.RESOURCE_NAME.equals(resourceName)) {

					List<String> modulePaths = getModulePaths(externalContext);
					boolean modulePathExtensionsValid = validateModulePathExtensions(externalContext, modulePaths);

					if ((modulePaths.size() > 0) && (modulePathExtensionsValid)) {

						if (ComboResource.RESOURCE_NAME.equals(resourceName)) {
							ComboResource comboResource = (ComboResource) createResource(resourceName, libraryName);
							comboResource.setModulePaths(modulePaths);
							handleResource(facesContext, comboResource);
						}
						else {
							ScriptResource scriptResource = (ScriptResource) createResource(resourceName, libraryName);
							scriptResource.setModulePath(modulePaths.get(0));
							handleResource(facesContext, scriptResource);
						}
					}
					else {
						externalContext.setResponseHeader(HttpHeaders.CACHE_CONTROL,
							HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
						externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
					}
				}
				else {
					getWrapped().handleResourceRequest(facesContext);
				}
			}

			// Otherwise, pass responsibility for handling the resource to the resource-handler delegation chain.
			else {
				getWrapped().handleResourceRequest(facesContext);
			}
		}
	}

	@Override
	public boolean libraryExists(String libraryName) {

		if (!LIFERAY_PORTAL_DETECTED && LIBRARY_NAME.equals(libraryName)) {
			return true;
		}
		else {
			return super.libraryExists(libraryName);
		}
	}

	protected boolean validateModulePathExtensions(ExternalContext externalContext, List<String> modulePaths) {
		String[] comboAllowedFileExtensions = ResLibConfigParam.ComboAllowedFileExtensions.getStringValue(
				externalContext).split(",");

		boolean modulePathExtensionsValid = true;

		for (String modulePath : modulePaths) {

			boolean validFileExtension = false;

			for (String comboAllowedFileExtension : comboAllowedFileExtensions) {

				if (modulePath.endsWith(comboAllowedFileExtension)) {
					validFileExtension = true;

					break;
				}
			}

			if (!validFileExtension) {
				modulePathExtensionsValid = false;

				break;
			}
		}

		return modulePathExtensionsValid;
	}

	protected List<String> getModulePaths(ExternalContext externalContext) {

		List<String> modulePaths = new ArrayList<String>();
		Iterator<String> requestParameterNames = externalContext.getRequestParameterNames();

		while (requestParameterNames.hasNext()) {
			String paramterName = requestParameterNames.next();

			if (!PROTECTED_PARAMTERS.contains(paramterName)) {
				modulePaths.add(paramterName);
			}
		}

		return modulePaths;
	}

	protected String getResourceName(ExternalContext externalContext) {

		// Attempt to get the resource name from the "javax.faces.resource" request parameter. If it exists, then
		// this is probably a non-Liferay portlet environment like Pluto.
		String resourceName = externalContext.getRequestParameterMap().get("javax.faces.resource");

		if (resourceName == null) {

			// If the specified request was extension-mapped (suffix-mapped), then determine the resource name based
			// on the configured mappings to the Faces Servlet.
			Object request = externalContext.getRequest();

			if (request instanceof HttpServletRequest) {
				HttpServletRequest httpServletRequest = (HttpServletRequest) request;
				String servletPath = httpServletRequest.getServletPath();

				if ((servletPath != null) && servletPath.startsWith(RESOURCE_IDENTIFIER)) {

					Map<String, Object> applicationMap = externalContext.getApplicationMap();
					String appConfigAttrName = ApplicationConfig.class.getName();
					ApplicationConfig applicationConfig = (ApplicationConfig) applicationMap.get(appConfigAttrName);
					FacesConfig facesConfig = applicationConfig.getFacesConfig();
					List<ConfiguredServletMapping> configuredFacesServletMappings =
						facesConfig.getConfiguredFacesServletMappings();

					resourceName = servletPath.substring(RESOURCE_IDENTIFIER.length() + 1);

					for (ConfiguredServletMapping configuredFacesServletMapping : configuredFacesServletMappings) {

						String configuredExtension = configuredFacesServletMapping.getExtension();

						if (servletPath.endsWith(configuredExtension)) {
							resourceName = resourceName.substring(0,
									resourceName.length() - configuredExtension.length());

							break;
						}
					}
				}

				// Otherwise, it must be path-mapped.
				else {
					resourceName = httpServletRequest.getPathInfo();
				}
			}
		}

		return resourceName;
	}

	@Override
	protected ResourceOutputStream getResourceOutputStream(Resource resource, int size) {

		String resourceName = resource.getResourceName();

		// If the specified resource is "liferay.js" then filter the output stream so that #{resource['...']}
		// expressions will be expanded.
		if (LIFERAY_JS.equals(resourceName) || ComboResource.RESOURCE_NAME.equals(resourceName)) {
			return new ExpressionResourceOutputStream(resource, size);
		}

		// Otherwise, return a normal resource output stream.
		else {
			return super.getResourceOutputStream(resource, size);
		}
	}
}
