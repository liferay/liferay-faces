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
package com.liferay.faces.util.event;

import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import com.liferay.faces.util.config.ApplicationConfigUtil;
import com.liferay.faces.util.config.WebConfigParam;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class ApplicationStartupListener implements SystemEventListener {

	// Private Constants
	private static final boolean LIFERAY_FACES_BRIDGE_DETECTED = ProductMap.getInstance().get(
			ProductConstants.LIFERAY_FACES_BRIDGE).isDetected();

	public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {

		// If Liferay Faces Bridge is not present in the classpath, then proceed with processing the configuration
		// files. The reason is because Liferay Faces Bridge has its own configuration file processing that takes place
		// during portlet initialization, rather than at JSF application startup.
		if (!LIFERAY_FACES_BRIDGE_DETECTED) {

			if (systemEvent instanceof PostConstructApplicationEvent) {

				if (ApplicationConfigUtil.getApplicationConfig() == null) {
					FacesContext initFacesContext = FacesContext.getCurrentInstance();
					ExternalContext externalContext = initFacesContext.getExternalContext();
					String initParam = WebConfigParam.ResolveXMLEntities.getStringValue(externalContext);
					boolean resolveEntities = BooleanHelper.toBoolean(initParam, false);
					ApplicationConfigUtil.initializeApplicationConfig(resolveEntities);
				}
			}
		}
	}

	public boolean isListenerForSource(Object source) {

		if ((source != null) && (source instanceof Application)) {
			return true;
		}
		else {
			return false;
		}
	}
}
