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
package com.liferay.faces.bridge.context.url;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeResourceURLWrapper extends BridgeURLWrapper implements BridgeResourceURL,
	Wrapper<BridgeResourceURL> {

	public void replaceBackLinkParameter(FacesContext facesContext) {
		getWrapped().replaceBackLinkParameter(facesContext);
	}

	public void setInProtocol(boolean inProtocol) {
		getWrapped().setInProtocol(inProtocol);
	}

	public boolean isEncodedFaces2ResourceURL() {
		return getWrapped().isEncodedFaces2ResourceURL();
	}

	public boolean isFaces2ResourceURL() {
		return getWrapped().isFaces2ResourceURL();
	}

	public void setViewLink(boolean viewLink) {
		getWrapped().setViewLink(viewLink);
	}

	@Override
	public abstract BridgeResourceURL getWrapped();

}
