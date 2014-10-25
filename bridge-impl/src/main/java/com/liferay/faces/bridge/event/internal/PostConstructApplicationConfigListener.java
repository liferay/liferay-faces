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
package com.liferay.faces.bridge.event.internal;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import com.liferay.faces.bridge.application.MojarraApplicationAssociate;
import com.liferay.faces.bridge.bean.MojarraInjectionProvider;
import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.event.PostConstructApplicationConfigEvent;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * <p>The purpose of this class is to listen for {@link PostConstructApplicationConfigEvent} during startup and save the
 * following as servlet context attributes:
 *
 * <table>
 *   <thead>
 *   <tr>
 *     <th>Attribute Name</th>
 *     <th>Attribute Value</th>
 *   </tr>
 *   </thead>
 *
 *   <tbody>
 *     <tr>
 *       <td>com.liferay.faces.bridge.bean.MojarraInjectionProvider</td>
 *       <td>Singleton instance of the Mojarra injection provider.</td>
 *     </tr>
 *     <tr>
 *       <td>com.liferay.faces.bridge.context.</td>
 *     </tr>
 *   </tbody>
 * </table>
 * </p>
 *
 * @author  Neil Griffin
 */
public class PostConstructApplicationConfigListener implements SystemEventListener {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PostConstructApplicationConfigListener.class);

	@Override
	public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {

		Product jsf = ProductMap.getInstance().get(ProductConstants.JSF);

		if (jsf.isDetected() && ProductConstants.MOJARRA.equals(jsf.getTitle())) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			MojarraInjectionProvider mojarraInjectionProvider = MojarraApplicationAssociate.getInjectionProvider(
					externalContext);
			applicationMap.put(MojarraInjectionProvider.class.getName(), mojarraInjectionProvider);

			if (mojarraInjectionProvider == null) {
				logger.error("Unable to discover Mojarra InjectionProvider during startup");
			}
			else {
				logger.debug("Mojarra injectionProvider=[{0}]", mojarraInjectionProvider);
			}
		}
	}

	@Override
	public boolean isListenerForSource(Object source) {
		return ((source != null) && (source instanceof ApplicationConfig));
	}
}
