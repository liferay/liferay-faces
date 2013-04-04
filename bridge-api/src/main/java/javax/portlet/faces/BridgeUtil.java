/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package javax.portlet.faces;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public class BridgeUtil {

	public static Bridge.PortletPhase getPortletRequestPhase() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();

		return (Bridge.PortletPhase) requestMap.get(Bridge.PORTLET_LIFECYCLE_PHASE);
	}

	public static boolean isPortletRequest() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		Bridge.PortletPhase portletPhase = (Bridge.PortletPhase) requestMap.get(Bridge.PORTLET_LIFECYCLE_PHASE);

		return (portletPhase != null);
	}
}
