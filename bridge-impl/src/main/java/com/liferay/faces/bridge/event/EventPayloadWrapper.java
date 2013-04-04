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
package com.liferay.faces.bridge.event;

import java.io.Serializable;

import javax.faces.FacesWrapper;
import javax.portlet.StateAwareResponse;


/**
 * <p>The purpose of this class is to provide support a vendor-specific feature of Liferay Portal that provides the
 * ability to broadcast Portlet 2.0 Events to portlets that exist on a different portal page. For more information,
 * refer to the "portlet.event.distribution" key inside of the Liferay Portal portal.properties file.</p>
 *
 * <p>This class provides the ability to wrap a {@link Serializable} event payload that is intended to be sent via
 * {@link StateAwareResponse#setEvent(String, Serializable)} or {@link
 * StateAwareResponse#setEvent(javax.xml.namespace.QName, Serializable)}. It also provides the ability for the recipient
 * of the event to determine whether or not a redirect is taking place.</p>
 *
 * @author  Neil Griffin
 */
public class EventPayloadWrapper implements FacesWrapper<Serializable>, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 9167031956551424140L;

	// Private Data Members
	private boolean redirect;
	private Serializable wrappedPayload;

	/**
	 * Constructs a new {@link EventPayloadWrapper} instance.
	 *
	 * @param  payload   The {@link Serializable} payload that is to be wrapped.
	 * @param  redirect  The flag indicating whether or not a redirect is taking place.
	 */
	public EventPayloadWrapper(Serializable payload, boolean redirect) {
		this.wrappedPayload = payload;
		this.redirect = redirect;
	}

	public boolean isRedirect() {
		return redirect;
	}

	public Serializable getWrapped() {
		return wrappedPayload;
	}
}
