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
package com.liferay.faces.bridge.component.visit.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.faces.component.UINamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitContextFactory;
import javax.faces.component.visit.VisitHint;
import javax.faces.context.FacesContext;


/**
 * @author  Vernon Singleton
 */
public class VisitContextFactoryImpl extends VisitContextFactory {

	VisitContextFactory wrappedVisitContextFactory;

	public VisitContextFactoryImpl(VisitContextFactory visitContextFactory) {
		this.wrappedVisitContextFactory = visitContextFactory;
	}

	@Override
	public VisitContext getVisitContext(FacesContext facesContext, Collection<String> ids, Set<VisitHint> hints) {

		// Prepend the ids with the portlet namespace unless they already start with the namespace, or
		// if the id starts with the SeparatorChar
		if (ids != null) {

			UIViewRoot viewRoot = facesContext.getViewRoot();
			String separator = String.valueOf(UINamingContainer.getSeparatorChar(facesContext));
			String containerClientIdAndSeparator = viewRoot.getContainerClientId(facesContext) + separator;

			List<String> newIds = new ArrayList<String>();

			for (String id : ids) {

				if (!id.startsWith(separator) && !id.startsWith(containerClientIdAndSeparator)) {
					id = containerClientIdAndSeparator + id;
				}

				newIds.add(id);
			}

			ids = newIds;

		}

		return wrappedVisitContextFactory.getVisitContext(facesContext, ids, hints);
	}
}
