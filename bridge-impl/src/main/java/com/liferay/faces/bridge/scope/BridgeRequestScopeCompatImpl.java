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
package com.liferay.faces.bridge.scope;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.liferay.faces.bridge.context.ExternalContextImpl;
import com.liferay.faces.bridge.util.NameValuePair;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeRequestScopeCompatImpl extends BridgeRequestScopeBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeRequestScopeCompatImpl.class);

	// Private Constants
	private static final String BRIDGE_REQ_SCOPE_ATTR_FACES_CONTEXT_ATTRIBUTES =
		"com.liferay.faces.bridge.facescontext.attributes";

	// Private Data Members
	private Flash flash;

	protected void restoreFlashState(ExternalContext externalContext) {

		if (flash != null) {

			while (externalContext instanceof ExternalContextWrapper) {
				ExternalContextWrapper externalContextWrapper = (ExternalContextWrapper) externalContext;
				externalContext = externalContextWrapper.getWrapped();
			}

			if (externalContext instanceof ExternalContextImpl) {
				ExternalContextImpl externalContextImpl = (ExternalContextImpl) externalContext;
				externalContextImpl.setFlash(flash);
			}
			else {
				logger.error("Unable to get access to the bridge ExternalContextImpl");
			}
		}
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

			// FACES-1463: The map must be cleared in order to prevent instances of the protected inner
			// FacesContextAttribute class (defined below) from holding on to a reference to its parent (this class).
			savedFacesContextAttributes.clear();
			logger.debug("Restored FacesContext attributes");
		}
		else {
			logger.debug("Did not restore any FacesContext attributes");
		}
	}

	protected void saveFlashState(ExternalContext externalContext) {
		flash = externalContext.getFlash();
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

	protected class FacesContextAttribute extends NameValuePair<Object, Object> {

		public FacesContextAttribute(Object name, Object value) {
			super(name, value);
		}
	}

}
