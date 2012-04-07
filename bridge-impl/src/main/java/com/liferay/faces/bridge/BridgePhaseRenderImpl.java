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
package com.liferay.faces.bridge;

import java.io.IOException;
import java.io.Writer;

import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeException;

import com.liferay.faces.bridge.application.BridgeNavigationHandler;
import com.liferay.faces.bridge.application.BridgeNavigationHandlerImpl;
import com.liferay.faces.bridge.context.ExternalContextImpl;
import com.liferay.faces.bridge.context.ResponseOutputWriter;
import com.liferay.faces.bridge.context.flash.BridgeFlash;
import com.liferay.faces.bridge.context.url.BridgeRedirectURL;
import com.liferay.faces.bridge.event.IPCPhaseListener;
import com.liferay.faces.bridge.event.ManagedBeanScopePhaseListener;
import com.liferay.faces.bridge.event.RenderRequestPhaseListener;
import com.liferay.faces.bridge.lifecycle.LifecycleIncongruityManager;
import com.liferay.faces.bridge.lifecycle.LifecycleIncongruityMap;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgePhaseRenderImpl extends BridgePhaseBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgePhaseRenderImpl.class);

	// Private Data Members
	private RenderRequest renderRequest;
	private RenderResponse renderResponse;

	public BridgePhaseRenderImpl(RenderRequest renderRequest, RenderResponse renderResponse,
		PortletConfig portletConfig) {
		super(portletConfig);
		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;
	}

	public void execute() throws BridgeDefaultViewNotSpecifiedException, BridgeException {

		logger.debug(Logger.SEPARATOR);
		logger.debug("execute(RenderRequest, RenderResponse) portletName=[{0}] portletMode=[{1}]", portletName,
			renderRequest.getPortletMode());

		Object renderPartAttribute = renderRequest.getAttribute(RenderRequest.RENDER_PART);

		if ((renderPartAttribute != null) && renderPartAttribute.equals(RenderRequest.RENDER_HEADERS)) {
			doFacesHeaders(renderRequest, renderResponse);
		}
		else {

			try {
				execute(null);
			}
			catch (BridgeException e) {
				throw e;
			}
			catch (Exception e) {
				throw new BridgeException(e);
			}
			finally {

				if ((facesContext != null)) {
					facesContext.release();
				}

				// If required, cause the BridgeRequestScope to go out-of-scope.
				if (!bridgeContext.isBridgeRequestScopePreserved()) {
					bridgeContext.getBridgeRequestScopeManager().removeBridgeRequestScope(bridgeRequestScope,
						portletContext);
				}

				cleanup(renderRequest);
			}

			logger.debug(Logger.SEPARATOR);
		}
	}

	protected void doFacesHeaders(RenderRequest renderRequest, RenderResponse renderResponse) {
		logger.trace("doFacesHeaders(RenderRequest, RenderResponse) this=[{0}]", this);
	}

	protected void execute(BridgeRedirectURL renderRedirectURL) throws BridgeDefaultViewNotSpecifiedException,
		BridgeException, IOException {

		init(renderRequest, renderResponse, Bridge.PortletPhase.RENDER_PHASE);

		// String[] testPRP = renderRequest.getPublicParameterMap().get("testPRP");
		// String modelPRP = (String) renderRequest.getAttribute("modelPRP");
		// System.err.println("!@#$ testPRP[]=" + testPRP + " modelPRP=" + modelPRP);

		// If the portlet mode has not changed, then restore the faces view root and messages that would
		// have been saved during the ACTION_PHASE of the portlet lifecycle. Section 5.4.1 requires that the
		// BridgeRequestScope must not be restored if there is a change in portlet modes detected.
		boolean facesLifecycleAlreadyExecuted = false;
		PortletMode fromPortletMode = bridgeRequestScope.getPortletMode();
		PortletMode toPortletMode = renderRequest.getPortletMode();

		if ((fromPortletMode != null) && (toPortletMode != null)) {

			String fromPortletModeAsString = fromPortletMode.toString();
			String toPortletModeAsString = toPortletMode.toString();

			if (fromPortletModeAsString.equals(toPortletModeAsString)) {
				facesLifecycleAlreadyExecuted = bridgeRequestScope.restoreScopedData(facesContext);
			}
			else {
				bridgeRequestScope.setPortletModeChanged(true);
				bridgeContext.getBridgeRequestScopeManager().removeBridgeRequestScope(bridgeRequestScope,
					portletContext);
			}
		}

		// NOTE: PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-201
		// Restore the flash scope.
		BridgeFlash bridgeFlash = (BridgeFlash) bridgeRequestScope.getFlash();

		if (bridgeFlash != null) {
			ExternalContext externalContext = facesContext.getExternalContext();

			while (externalContext instanceof ExternalContextWrapper) {
				ExternalContextWrapper externalContextWrapper = (ExternalContextWrapper) externalContext;
				externalContext = externalContextWrapper.getWrapped();
			}

			if (externalContext instanceof ExternalContextImpl) {
				ExternalContextImpl externalContextImpl = (ExternalContextImpl) externalContext;
				externalContextImpl.setBridgeFlash(bridgeFlash);
			}
			else {
				logger.error("Unable to get access to the bridge ExternalContextImpl");
			}
		}

		// If a render-redirect URL was specified, then it is necessary to create a new view from the URL and place it
		// in the FacesContext.
		if (renderRedirectURL != null) {
			bridgeContext.setRenderRedirectURL(renderRedirectURL);
			bridgeContext.setRenderRedirectAfterDispatch(true);

			ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
			UIViewRoot uiViewRoot = viewHandler.createView(facesContext, renderRedirectURL.getContextRelativePath());
			facesContext.setViewRoot(uiViewRoot);

			String viewId = bridgeContext.getFacesViewId();
			logger.debug("Performed render-redirect to viewId=[{0}]", viewId);
		}

		// NOTE: PROPOSE-FOR-BRIDGE3-API Actually, the proposal would be to REMOVE
		// Bridge.IS_POSTBACK_ATTRIBUTE from the Bridge API, because JSF 2.0 introduced the
		// FacesContext#isPostBack() method.
		// http://javaserverfaces.java.net/nonav/docs/2.0/javadocs/javax/faces/context/FacesContext.html#isPostback()
		if (facesContext.isPostback()) {
			facesContext.getExternalContext().getRequestMap().put(Bridge.IS_POSTBACK_ATTRIBUTE, Boolean.TRUE);
		}

		// If necessary, execute the appropriate phases of the faces lifecycle. In the case of an initial
		// (HTTP GET) request, the only phase that will be executed is RESTORE_VIEW. In the case of a
		// postback request, the RESTORE_VIEW, APPLY_REQUEST_VALUES, PROCESS_VALIDATIONS, UPDATE_MODEL, and
		// INVOKE_APPLICATION phases will normally be executed.
		logger.debug("portletName=[{0}] facesLifecycleAlreadyExecuted=[{1}]", portletName,
			facesLifecycleAlreadyExecuted);

		if (!facesLifecycleAlreadyExecuted) {

			// Section 5.2.6 of the JSR 329 Spec requires that a phase listener be registered in order to
			// handle Portlet 2.0 Public Render Parameters after the RESTORE_VIEW phase of the JSF lifecycle
			// executes. The IPCPhaseListener satisfies this requirement.
			PhaseListener ipcPhaseListener = new IPCPhaseListener(bridgeConfig, portletContext, portletName,
					renderRequest, renderResponse);

			// Section 5.2.6 also indicates that the bridge must proactively ensure that only the
			// RESTORE_VIEW phase executes, and Section 6.4 indicates that a PhaseListener must be used. The
			// RenderRequestPhaseListener satisfies this requirement.
			PhaseListener renderRequestPhaseListener = new RenderRequestPhaseListener();

			// Add the phase listeners to the Faces lifecycle.
			facesLifecycle.addPhaseListener(ipcPhaseListener);
			facesLifecycle.addPhaseListener(renderRequestPhaseListener);

			// Execute the Faces lifecycle.
			try {
				String viewId = bridgeContext.getFacesViewId();
				logger.debug("Executing Faces lifecycle for viewId=[{0}]", viewId);
			}
			catch (BridgeException e) {
				logger.error("Unable to get viewId due to {0}", e.getClass().getSimpleName());
				throw e;
			}

			facesLifecycle.execute(facesContext);

			// Remove the phase listeners from the Faces lifecycle.
			facesLifecycle.removePhaseListener(ipcPhaseListener);
			facesLifecycle.removePhaseListener(renderRequestPhaseListener);
		}

		// If the PortletMode has changed, then switch to the appropriate PortletMode and navigate to the
		// current viewId in the UIViewRoot.
		BridgeNavigationHandler bridgeNavigationHandler = getBridgeNavigationHandler(facesContext);
		bridgeNavigationHandler.handleNavigation(facesContext, fromPortletMode, toPortletMode);

		// Now that we're executing the RENDER_PHASE of the Portlet lifecycle, before the JSF
		// RENDER_RESPONSE phase is executed, we have to fix some incongruities between the Portlet
		// lifecycle and the JSF lifecycle that may have occurred during the ACTION_PHASE of the Portlet
		// lifecycle.
		ExternalContext externalContext = facesContext.getExternalContext();
		LifecycleIncongruityMap lifecycleIncongruityMap = new LifecycleIncongruityMap(externalContext.getRequestMap());
		LifecycleIncongruityManager lifecycleIncongruityManager = new LifecycleIncongruityManager(
				lifecycleIncongruityMap);
		lifecycleIncongruityManager.makeCongruous(externalContext);

		// Execute the RENDER_RESPONSE phase of the faces lifecycle. Note that we need to add the
		// ManagedBeanScopePhaseListener so that after the RENDER_RESPONSE phase, the managed-beans in
		// bridgeRequestScope will go out-of-scope which will in turn cause any annotated PreDestroy methods
		// to be called.
		PhaseListener managedBeanScopePhaseListener = new ManagedBeanScopePhaseListener();
		facesLifecycle.addPhaseListener(managedBeanScopePhaseListener);
		logger.debug("Executing Faces render");
		facesLifecycle.render(facesContext);
		facesLifecycle.removePhaseListener(managedBeanScopePhaseListener);

		// Set the view history according to Section 5.4.3 of the Bridge Spec.
		setViewHistory(facesContext.getViewRoot().getViewId());

		// Spec 6.6 (Namespacing)
		indicateNamespacingToConsumers(facesContext.getViewRoot(), renderResponse);

		// If a render-redirect occurred, then
		Writer writer = bridgeContext.getResponseOutputWriter();

		if (bridgeContext.isRenderRedirect()) {

			// Cleanup the old FacesContext since a new one will be created in the recursive method call below.
			facesContext.responseComplete();
			facesContext.release();

			// If the render-redirect standard feature is enabled in web.xml or portlet.xml, then the
			// ResponseOutputWriter has buffered up markup that must be discarded. This is because we don't want the
			// markup from the original Faces view to be included with the markup of Faces view found in the redirect
			// URL.
			if (writer instanceof ResponseOutputWriter) {
				ResponseOutputWriter responseOutputWriter = (ResponseOutputWriter) writer;
				responseOutputWriter.discard();
			}

			// Recursively call this method with the render-redirect URL so that the RENDER_RESPONSE phase of the JSF
			// lifecycle will be re-executed according to the new Faces viewId found in the redirect URL.
			execute(bridgeContext.getRenderRedirectURL());
		}

		// Otherwise,
		else {

			// In the case that a render-redirect took place, need to render the buffered markup to the response.
			if (writer instanceof ResponseOutputWriter) {
				ResponseOutputWriter responseOutputWriter = (ResponseOutputWriter) writer;
				responseOutputWriter.render();
			}
		}
	}

	protected BridgeNavigationHandler getBridgeNavigationHandler(FacesContext facesContext) {
		BridgeNavigationHandler bridgeNavigationHandler = null;
		NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

		if (navigationHandler instanceof BridgeNavigationHandler) {
			bridgeNavigationHandler = (BridgeNavigationHandler) navigationHandler;
		}
		else {
			bridgeNavigationHandler = new BridgeNavigationHandlerImpl(navigationHandler);
		}

		return bridgeNavigationHandler;
	}

	/**
	 * Sets the "javax.portlet.faces.viewIdHistory.<code>portletMode</code>" session attribute according to the
	 * requirements in Section 5.4.3 of the Bridge Spec. There is no corresponding getter method, because the value is
	 * meant to be retrieved by developers via an EL expression.
	 *
	 * @param  viewId  The current Faces viewId.
	 */
	protected void setViewHistory(String viewId) {
		StringBuilder buf = new StringBuilder();
		buf.append(Bridge.VIEWID_HISTORY);
		buf.append(BridgeConstants.CHAR_PERIOD);
		buf.append(renderRequest.getPortletMode());

		String attributeName = buf.toString();
		PortletSession portletSession = renderRequest.getPortletSession();
		portletSession.setAttribute(attributeName, viewId);
	}

}
