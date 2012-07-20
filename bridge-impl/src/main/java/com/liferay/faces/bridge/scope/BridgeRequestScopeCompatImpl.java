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
package com.liferay.faces.bridge.scope;

import javax.faces.context.FacesContext;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeRequestScopeCompatImpl extends BridgeRequestScopeBaseImpl {

public class BridgeRequestScopeCompatImpl {

	protected void restoreFlashState(FacesContext facesContext) {
		// no-op for JSF 1.x
	}

	protected void restoreJSF2FacesContextAttributes(FacesContext facesContext) {

		@SuppressWarnings("unchecked")
		List<FacesContextAttribute> savedFacesContextAttributes = (List<FacesContextAttribute>) getAttribute(
				BRIDGE_REQ_SCOPE_ATTR_FACES_CONTEXT_ATTRIBUTES);

		boolean restoredFacesContextAttibutes = false;

		if (savedFacesContextAttributes != null) {
			Map<Object, Object> currentFacesContextAttributes = facesContext.getAttributes();

			for (FacesContextAttribute facesContextAttribute : savedFacesContextAttributes) {
				Object name = facesContextAttribute.getName();

				Object value = facesContextAttribute.getValue();
				logger.trace("Restoring FacesContext attribute name=[{0}] value=[{1}]", name, value);
				currentFacesContextAttributes.put(name, value);
				restoredFacesContextAttibutes = true;
			}
		}

		if (restoredFacesContextAttibutes) {
			logger.debug("Restored FacesContext attributes");
		}
		else {
			logger.debug("Did not restore any FacesContext attributes");
		}
	}

	protected void saveFlashState(FacesContext facesContext) {
		// no-op for JSF 1.x
	}

	protected void saveJSF2FacesContextAttributes(FacesContext facesContext) {
		Map<Object, Object> currentFacesContextAttributes = facesContext.getAttributes();
		int mapSize = currentFacesContextAttributes.size();
		List<FacesContextAttribute> savedFacesContextAttributes = new ArrayList<FacesContextAttribute>(mapSize);
		Iterator<Map.Entry<Object, Object>> itr = currentFacesContextAttributes.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Object, Object> mapEntry = itr.next();
			Object name = mapEntry.getKey();
			Object value = mapEntry.getValue();
			logger.trace("Saving FacesContext attribute name=[{0}] value=[{1}]", name, value);
			savedFacesContextAttributes.add(new FacesContextAttribute(name, value));
		}

		setAttribute(BRIDGE_REQ_SCOPE_ATTR_FACES_CONTEXT_ATTRIBUTES, savedFacesContextAttributes);
	}
}
