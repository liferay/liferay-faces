/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.cdi.event;

import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.portlet.RenderRequest;

import com.liferay.cdi.portlet.bridge.CDIRenderRequestImpl;
import com.liferay.cdi.portlet.bridge.HttpServletRequestAdapter;
import com.liferay.cdi.portlet.bridge.HttpServletRequestAdapterImpl;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * Portlet containers like Apache Pluto implement the post-redirect-get pattern for portlet actions. This means that the
 * ACTION_PHASE executes within an HTTP POST and returns a 302 redirect, and the RENDER_PHASE executes within a
 * subsequent HTTP GET. This design causes JBoss Weld to throw an {@link IllegalStateException} because the {@link
 * org.jboss.weld.jsf.WeldPhaseListener} class is not aware that in a portlet environment, {@link
 * Lifecycle#execute(FacesContext)} executes in the HTTP POST request whereas {@link Lifecycle#render(FacesContext)}
 * executes in the HTTP GET request. This class serves as a workaround for that problem and will be automatically
 * registered via the META-INF/faces-config.xml descriptor contained in the liferay-faces-cdi-weld.jar artifact. For
 * more information, see <a href="https://issues.liferay.com/browse/FACES-1719">FACES-1719</a>.
 *
 * @author  Neil Griffin
 */
public class WeldHelperPhaseListener implements PhaseListener {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(WeldHelperPhaseListener.class);

	// serialVersionUID
	private static final long serialVersionUID = 1438216845557374340L;

	// Private Data Members
	private PhaseListener weldPhaseListener;

	public WeldHelperPhaseListener() {

		try {
			Class<?> weldPhaseListenerClass = Class.forName("org.jboss.weld.jsf.WeldPhaseListener");

			try {
				weldPhaseListener = (PhaseListener) weldPhaseListenerClass.newInstance();
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
		catch (ClassNotFoundException e) {
			logger.debug("WeldPhaseListener not found on classpath");
		}
	}

	@Override
	public void afterPhase(PhaseEvent phaseEvent) {
		// Ignore
	}

	@Override
	public void beforePhase(PhaseEvent phaseEvent) {

		// If org.jboss.weld.jsf.WeldPhaseListener was found on the classpath, then
		if (weldPhaseListener != null) {

			try {

				// If the request that invoked the JSF lifecycle was a portlet RenderRequest, then
				FacesContext facesContext = phaseEvent.getFacesContext();
				ExternalContext externalContext = facesContext.getExternalContext();
				Object request = externalContext.getRequest();

				if (request instanceof RenderRequest) {

					// If the JSF lifecycle is currently executing the RESTORE_VIEW phase, then set a flag that can be
					// referenced later-on in the RENDER_RESPONSE phase. This simply means that the portal page was
					// requested with a RenderURL, not an ActionURL.
					PhaseId phaseId = phaseEvent.getPhaseId();
					Map<Object, Object> facesContextAttributes = facesContext.getAttributes();

					if (phaseId.equals(PhaseId.RESTORE_VIEW)) {
						facesContextAttributes.put(PhaseId.RESTORE_VIEW, Boolean.TRUE);
					}

					// Otherwise, if the JSF lifecycle is currently executing the RENDER_RESPONSE phase, then
					else if (phaseId.equals(PhaseId.RENDER_RESPONSE)) {

						// If the flag set earlier in the RESTORE_VIEW phase was set, then simply remove the flag
						// since there is nothing to do.
						if (facesContextAttributes.get(PhaseId.RESTORE_VIEW) != null) {
							facesContextAttributes.remove(PhaseId.RESTORE_VIEW);
						}

						// Otherwise, this means that the RESTORE_VIEW phase must have executed in a prior
						// ActionRequest. Therefore it is necessary to invoke the
						// WeldPhaseListener.beforePhase(PhaseEvent) method, tricking it into thinking that the
						// RESTORE_VIEW phase is executing so that Weld conversation context will be re-associated with
						// the request. Note that it is also necessary to trick Weld into thinking that the
						// "org.jboss.weld.context.AbstractConversationContext" RenderRequest attribute has not been
						// set, so that the request will be re-associated properly.
						else {
							Lifecycle weldHelperLifecycle = new WeldHelperLifecycle();
							PhaseEvent restoreViewPhaseEvent = new PhaseEvent(facesContext, PhaseId.RESTORE_VIEW,
									weldHelperLifecycle);
							RenderRequest renderRequest = (RenderRequest) request;
							HttpServletRequestAdapter httpServletRequestAdapter = new HttpServletRequestAdapterImpl(
									renderRequest);
							WeldHelperRenderRequest weldHelperRenderRequest = new WeldHelperRenderRequest(renderRequest,
									httpServletRequestAdapter);
							externalContext.setRequest(weldHelperRenderRequest);
							weldPhaseListener.beforePhase(restoreViewPhaseEvent);
							externalContext.setRequest(request);
						}
					}
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	public class WeldHelperRenderRequest extends CDIRenderRequestImpl {

		public WeldHelperRenderRequest(RenderRequest renderRequest,
			HttpServletRequestAdapter httpServletRequestAdapter) {
			super(renderRequest, httpServletRequestAdapter);
		}

		@Override
		public Object getAttribute(String name) {

			if ("org.jboss.weld.context.AbstractConversationContext".equals(name)) {
				return null;
			}
			else {
				return super.getAttribute(name);
			}
		}
	}

	protected class WeldHelperLifecycle extends Lifecycle {

		@Override
		public void addPhaseListener(PhaseListener listener) {
			// no-op
		}

		@Override
		public void execute(FacesContext context) throws FacesException {
			// no-op
		}

		@Override
		public void removePhaseListener(PhaseListener listener) {
			// no-op
		}

		@Override
		public void render(FacesContext context) throws FacesException {
			// no-op
		}

		@Override
		public PhaseListener[] getPhaseListeners() {
			return null;
		}

	}
}
