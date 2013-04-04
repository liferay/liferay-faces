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

import javax.faces.context.FacesContext;


/**
 * This interface defines the contract for a Portlet 2.0 Public Render Parameter "handler" that enables JSF portlet
 * developers to perform any processing that might be necessary after the bridge pushes public render parameter values
 * into the model. Handlers are registered in the portlet.xml descriptor using an init-param with name
 * "bridgePublicRenderParameterHandler" as defined in {@link Bridge#BRIDGE_PUBLIC_RENDER_PARAMETER_HANDLER}.
 *
 * @author  Neil Griffin
 */
public interface BridgePublicRenderParameterHandler {

	/**
	 * This method is called after the bridge has pushed public render parameter values into the model, which occurs
	 * after the RESTORE_VIEW phase of the JSF lifecycle.
	 *
	 * @param  facesContext  The current FacesContext.
	 */
	public void processUpdates(FacesContext facesContext);
}
