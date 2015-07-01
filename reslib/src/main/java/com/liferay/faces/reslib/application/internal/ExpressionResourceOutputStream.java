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

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.io.Filterable;
import com.liferay.faces.util.io.ResourceOutputStream;


/**
 * @author  Neil Griffin
 */
public class ExpressionResourceOutputStream extends ResourceOutputStream implements Filterable {

	// Private Constants
	private static final String RESOURCE_TOKEN_BEGIN = "#{resource['";
	private static final String RESOURCE_TOKEN_END = "']}";

	public ExpressionResourceOutputStream(Resource resource, int size) {
		super(resource, size);
	}

	public void filter() throws IOException {

		String text = toString();

		int startPos = text.indexOf(RESOURCE_TOKEN_BEGIN);

		while (startPos > 0) {
			int finishPos = text.indexOf(RESOURCE_TOKEN_END, startPos);

			if (finishPos > 0) {
				String resourcePair = text.substring(startPos + RESOURCE_TOKEN_BEGIN.length(), finishPos);

				if (resourcePair.indexOf(":") > 0) {
					String[] resourceTokens = resourcePair.split(":");
					String libraryName = resourceTokens[0];
					String resourceName = resourceTokens[1];
					FacesContext facesContext = FacesContext.getCurrentInstance();
					ResourceHandler resourceHandlerChain = facesContext.getApplication().getResourceHandler();
					Resource resource = resourceHandlerChain.createResource(resourceName, libraryName);

					if (resource != null) {
						String requestPath = resource.getRequestPath();

						if (requestPath != null) {
							String resourceURL = facesContext.getExternalContext().encodeResourceURL(requestPath);
							text = text.substring(0, startPos) + resourceURL +
								text.substring(finishPos + RESOURCE_TOKEN_END.length());
						}
					}
				}
			}

			startPos = text.indexOf(RESOURCE_TOKEN_BEGIN);
		}

		reset();
		write(text.getBytes());
	}
}
