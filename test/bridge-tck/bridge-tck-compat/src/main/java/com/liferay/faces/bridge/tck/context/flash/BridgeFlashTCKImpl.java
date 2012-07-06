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
package com.liferay.faces.bridge.tck.context.flash;

import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextWrapper;
import javax.faces.context.Flash;
import javax.portlet.PortletResponse;

import com.liferay.faces.bridge.context.flash.BridgeFlash;
import com.liferay.faces.bridge.context.flash.BridgeFlashWrapper;
import com.liferay.faces.bridge.context.flash.FlashHttpServletResponse;


/**
 * <p>This class serves as a wrapper around the {@link BridgeFlash} provided by the bridge. It's purpose is to
 * work-around a ClassCastException in the TestPage204 (JSP_ELTest) introduced by the TCK dependency on Trinidad. For
 * more information, see: http://issues.liferay.com/browse/FACES-1342</p>
 *
 * @author  Neil Griffin
 */
public class BridgeFlashTCKImpl extends BridgeFlashWrapper {

	// Private Data Members
	private BridgeFlash wrappedBridgeFlash;

	public BridgeFlashTCKImpl(BridgeFlash bridgeFlash) {
		this.wrappedBridgeFlash = bridgeFlash;
	}

	@Override
	public void doPostPhaseActions(FacesContext facesContext) {
		TrinidadFacesContext trinidadFacesContext = new TrinidadFacesContext(facesContext);
		super.doPostPhaseActions(trinidadFacesContext);
	}

	@Override
	public boolean isServletResponseRequired() {
		return wrappedBridgeFlash.isServletResponseRequired();
	}

	@Override
	public Flash getWrapped() {
		return wrappedBridgeFlash;
	}

	protected class TrinidadExternalContext extends ExternalContextWrapper {

		// Private Data Members
		private ExternalContext wrappedExternalContext;

		public TrinidadExternalContext(ExternalContext externalContext) {
			this.wrappedExternalContext = externalContext;
		}

		@Override
		public Object getResponse() {

			Object response = super.getResponse();

			if (response.getClass().equals(
						"org.apache.myfaces.trinidadinternal.config.dispatch.DispatchRenderResponse")) {

				// Avoid "ClassCastException: DispatchRenderResponse cannot be cast to HttpServletResponse".
				Locale requestLocale = super.getRequestLocale();
				PortletResponse portletResponse = (PortletResponse) response;
				response = new FlashHttpServletResponse(portletResponse, requestLocale);
			}

			return response;
		}

		@Override
		public ExternalContext getWrapped() {
			return wrappedExternalContext;
		}
	}

	protected class TrinidadFacesContext extends FacesContextWrapper {

		// Private Data Members
		private FacesContext wrappedFacesContext;

		public TrinidadFacesContext(FacesContext facesContext) {
			this.wrappedFacesContext = facesContext;
		}

		@Override
		public ExternalContext getExternalContext() {
			return new TrinidadExternalContext(super.getExternalContext());
		}

		@Override
		public FacesContext getWrapped() {
			return wrappedFacesContext;
		}

	}
}
