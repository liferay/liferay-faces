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

	protected void restoreFlashState(FacesContext facesContext) {
		// no-op for JSF 1.x
	}

	protected void restoreJSF2FacesContextAttributes(FacesContext facesContext) {
		// no-op for JSF 1.x
	}

	protected void saveFlashState(FacesContext facesContext) {
		// no-op for JSF 1.x
	}

	protected void saveJSF2FacesContextAttributes(FacesContext facesContext) {
		// no-op for JSF 1.x
	}
}
