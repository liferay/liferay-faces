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
package com.liferay.faces.util.application.internal;

import com.liferay.faces.util.application.ResourceValidator;
import com.liferay.faces.util.application.ResourceValidatorFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import javax.faces.application.ResourceHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @author  Neil Griffin
 */
public class ResourceValidatorFactoryImpl extends ResourceValidatorFactory {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceValidatorFactoryImpl.class);

	// Private Data Members
	private List<Pattern> excludeResourcePatterns;
	private List<Pattern> excludeLibraryPatterns;

	public ResourceValidatorFactoryImpl() {

		String excludeResourceExtensions = null;
		FacesContext startupFacesContext = FacesContext.getCurrentInstance();

		if (startupFacesContext != null) {

			ExternalContext externalContext = startupFacesContext.getExternalContext();

			String resourceExcludes = externalContext.getInitParameter(ResourceHandler.RESOURCE_EXCLUDES_PARAM_NAME);

			if ((resourceExcludes == null) || (resourceExcludes.trim().length() == 0)) {
				excludeResourceExtensions = ResourceHandler.RESOURCE_EXCLUDES_DEFAULT_VALUE;
			}
		}

		if (excludeResourceExtensions != null) {

			String[] extensions = excludeResourceExtensions.split(" ");

			this.excludeResourcePatterns = new ArrayList<Pattern>(extensions.length + 1);

			for (String extension : extensions) {
				Pattern pattern = Pattern.compile(".*\\" + extension + ".*");
				excludeResourcePatterns.add(pattern);
				logger.debug("Excluding resource pattern=[{0}]", pattern);
			}

			// Prevent for a leading dot character for resource names and library names
			Pattern pattern = Pattern.compile("^\\..*");
			this.excludeResourcePatterns.add(pattern);
			logger.debug("Excluding resource pattern=[{0}]", pattern);
			this.excludeLibraryPatterns = new ArrayList<Pattern>(1);
			this.excludeLibraryPatterns.add(pattern);
			logger.debug("Excluding library pattern=[{0}]", pattern);
		}
	}

	@Override
	public ResourceValidator getResourceValidator() {
		return new ResourceValidatorImpl(excludeResourcePatterns, excludeLibraryPatterns);
	}

	@Override
	public ResourceValidatorFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
