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
package com.liferay.faces.bridge.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.Bridge.PortletPhase;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.bean.BeanManager;
import com.liferay.faces.bridge.bean.BeanManagerFactory;
import com.liferay.faces.bridge.context.BridgeContext;


/**
 * <p>According to the JSF 2.0 JavaDocs for {@link ExternalContext.getApplicationMap}, before a managed-bean is removed
 * from the map, any public no-argument void return methods annotated with javax.annotation.PreDestroy must be called
 * first. This would be equally true of any custom JSF 2.0 scope, such as the bridgeRequestScope. This class is a JSF
 * PhaseListener that listens after the RENDER_RESPONSE phase completes. Its purpose is to force the managed-beans in
 * bridgeRequestScope and requestScope to go out-of-scope which will in turn cause any annotated PreDestroy methods to
 * be called.</p>
 *
 * <p>Note that this functionality is implemented as a PhaseListener because I couldn't get it to work after the
 * lifecycle terminated. My suspicion is that Mojarra has some servlet dependency stuff going on. Specifically,
 * Mojarra's WebappLifecycleListener might be getting invoked or something. The strange thing is that it appears that
 * Mojarra makes managed-beans go away, but not by calling the map.clear() or map.remove() methods. Mojarra apparently
 * handles things with a listener that captures ServletContext attribute events, which may also be playing a role.
 * Anyway, doing this with a PhaseListener seems to work.</p>
 *
 * @author  Neil Griffin
 */
public class ManagedBeanScopePhaseListener implements PhaseListener {

	// serialVersionUID
	private static final long serialVersionUID = 1713704308484763548L;

	public void afterPhase(PhaseEvent phaseEvent) {

		if (phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE) {

			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			PortletPhase portletRequestPhase = bridgeContext.getPortletRequestPhase();

			if ((portletRequestPhase == Bridge.PortletPhase.RENDER_PHASE) ||
					(portletRequestPhase == Bridge.PortletPhase.RESOURCE_PHASE)) {

				// Remove any managed-beans in request scope. According to the JSF 2.0 JavaDocs for {@link
				// ExternalContext.getRequestMap}, before a managed-bean is removed from the map, any public no-argument
				// void return methods annotated with javax.annotation.PreDestroy must be called first. Note that the
				// bridge {@link RequestAttributeMap.remove(Object)} method will ensure that any @PreDestroy method(s)
				// are called. The JavaDocs also state that this should only be the case for objects that are actually
				// managed-beans.
				FacesContext facesContext = FacesContext.getCurrentInstance();
				Map<String, Object> requestScope = facesContext.getExternalContext().getRequestMap();
				List<String> managedBeanKeysToRemove = new ArrayList<String>();
				Set<Map.Entry<String, Object>> mapEntries = requestScope.entrySet();

				if (mapEntries != null) {

					BeanManagerFactory beanManagerFactory = (BeanManagerFactory) BridgeFactoryFinder.getFactory(
							BeanManagerFactory.class);
					BeanManager beanManager = beanManagerFactory.getBeanManager();

					for (Map.Entry<String, Object> mapEntry : mapEntries) {
						String managedBeanKey = mapEntry.getKey();
						Object obj = mapEntry.getValue();

						if (beanManager.isManagedBean(obj)) {
							managedBeanKeysToRemove.add(managedBeanKey);
						}
					}
				}

				for (String managedBeanKey : managedBeanKeysToRemove) {
					requestScope.remove(managedBeanKey);
				}
			}
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {
		// This method is required by the PhaseListener interface but is not used.
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
}
