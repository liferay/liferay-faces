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
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.Bridge.PortletPhase;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeException;

import com.liferay.faces.bridge.application.BridgeNavigationHandler;
import com.liferay.faces.bridge.application.BridgeNavigationHandlerImpl;
import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.IncongruityContext;
import com.liferay.faces.bridge.context.RenderRedirectWriter;
import com.liferay.faces.bridge.context.url.BridgeRedirectURL;
import com.liferay.faces.bridge.event.IPCPhaseListener;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgePhaseRenderImpl extends BridgePhaseCompatImpl {

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
			catch (Throwable t) {
				throw new BridgeException(t);
			}
			finally {
				cleanup();
			}

			logger.debug(Logger.SEPARATOR);
		}
	}

	@Override
	protected void cleanup() {

		// If required, cause the BridgeRequestScope to go out-of-scope.
		if ((bridgeContext != null) && !bridgeContext.isBridgeRequestScopePreserved()) {
			bridgeRequestScopeCache.remove(bridgeRequestScope);
		}

		super.cleanup();
	}

	protected void doFacesHeaders(RenderRequest renderRequest, RenderResponse renderResponse) {
		logger.trace("doFacesHeaders(RenderRequest, RenderResponse) this=[{0}]", this);
	}

	protected void execute(BridgeRedirectURL renderRedirectURL) throws BridgeDefaultViewNotSpecifiedException,
		BridgeException, IOException {

		init(renderRequest, renderResponse, Bridge.PortletPhase.RENDER_PHASE);

		// If the portlet mode has not changed, then restore the faces view root and messages that would
		// have been saved during the ACTION_PHASE of the portlet lifecycle. Section 5.4.1 requires that the
		// BridgeRequestScope must not be restored if there is a change in portlet modes detected.
		boolean facesLifecycleExecuted = bridgeRequestScope.isFacesLifecycleExecuted();
		bridgeRequestScope.restoreState(facesContext);

		if (bridgeRequestScope.isPortletModeChanged()) {
			bridgeRequestScopeCache.remove(bridgeRequestScope);
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
		if (bridgeRequestScope.getBeganInPhase() == Bridge.PortletPhase.ACTION_PHASE) {
			facesContext.getExternalContext().getRequestMap().put(Bridge.IS_POSTBACK_ATTRIBUTE, Boolean.TRUE);
		}

		logger.debug("portletName=[{0}] facesLifecycleExecuted=[{1}]", portletName, facesLifecycleExecuted);

		// If the JSF lifecycle executed back in the ACTION_PHASE of the portlet lifecycle, then
		if (facesLifecycleExecuted) {

			// TCK TestPage054: prpUpdatedFromActionTest
			PhaseEvent restoreViewPhaseEvent = new PhaseEvent(facesContext, PhaseId.RESTORE_VIEW, facesLifecycle);
			PhaseListener[] phaseListeners = facesLifecycle.getPhaseListeners();

			for (PhaseListener phaseListener : phaseListeners) {

				if (phaseListener instanceof IPCPhaseListener) {
					phaseListener.afterPhase(restoreViewPhaseEvent);

					break;
				}
			}
		}

		// Otherwise, in accordance with Section 5.2.6 of the Spec, execute the JSF lifecycle so that ONLY the
		// RESTORE_VIEW phase executes. Note that this is accomplished by the RenderRequestPhaseListener.
		else {

			try {
				String viewId = bridgeContext.getFacesViewId();
				logger.debug("Executing Faces lifecycle for viewId=[{0}]", viewId);
			}
			catch (BridgeException e) {
				logger.error("Unable to get viewId due to {0}", e.getClass().getSimpleName());
				throw e;
			}

			facesLifecycle.execute(facesContext);

		}

		// If there were any "handled" exceptions queued, then throw a BridgeException.
		Throwable handledException = getJSF2HandledException(facesContext);

		if (handledException != null) {
			throw new BridgeException(handledException);
		}

		// Otherwise, if there were any "unhandled" exceptions queued, then throw a BridgeException.
		Throwable unhandledException = getJSF2UnhandledException(facesContext);

		if (unhandledException != null) {
			throw new BridgeException(unhandledException);
		}

		// Otherwise, if the PortletMode has changed, and a navigation-rule hasn't yet fired (which could have happened
		// in the EVENT_PHASE), then switch to the appropriate PortletMode and navigate to the current viewId in the
		// UIViewRoot.
		if (bridgeRequestScope.isPortletModeChanged() && !bridgeRequestScope.isNavigationOccurred()) {
			BridgeNavigationHandler bridgeNavigationHandler = getBridgeNavigationHandler(facesContext);
			PortletMode fromPortletMode = bridgeRequestScope.getPortletMode();
			PortletMode toPortletMode = renderRequest.getPortletMode();
			bridgeNavigationHandler.handleNavigation(facesContext, fromPortletMode, toPortletMode);
		}

		// Determines whether or not lifecycle incongruities should be managed.
		boolean manageIncongruities = BooleanHelper.toBoolean(bridgeContext.getInitParameter(
					BridgeConfigConstants.PARAM_MANAGE_INCONGRUITIES), true);

		// Now that we're executing the RENDER_PHASE of the Portlet lifecycle, before the JSF
		// RENDER_RESPONSE phase is executed, we have to fix some incongruities between the Portlet
		// lifecycle and the JSF lifecycle that may have occurred during the ACTION_PHASE of the Portlet
		// lifecycle.
		if (manageIncongruities) {
			incongruityContext.makeCongruous(facesContext);
		}

		// Execute the RENDER_RESPONSE phase of the faces lifecycle.
		logger.debug("Executing Faces render");
		facesLifecycle.render(facesContext);

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
			// markup from the original Faces view to be included with the markup of Faces view found in the
			// redirect URL.
			if (writer instanceof RenderRedirectWriter) {
				RenderRedirectWriter responseOutputWriter = (RenderRedirectWriter) writer;
				responseOutputWriter.discard();
			}

			// Recursively call this method with the render-redirect URL so that the RENDER_RESPONSE phase of the
			// JSF lifecycle will be re-executed according to the new Faces viewId found in the redirect URL.
			execute(bridgeContext.getRenderRedirectURL());
		}

		// Otherwise,
		else {

			// In the case that a render-redirect took place, need to render the buffered markup to the response.
			if (writer instanceof RenderRedirectWriter) {
				RenderRedirectWriter responseOutputWriter = (RenderRedirectWriter) writer;
				responseOutputWriter.render();
			}
		}
	}

	@Override
	protected void initBridgeRequestScope(PortletRequest portletRequest, PortletResponse portletResponse,
		PortletPhase portletPhase, PortletContainer portletContainer, IncongruityContext incongruityContext) {

		super.initBridgeRequestScope(portletRequest, portletResponse, portletPhase, portletContainer,
			incongruityContext);

		// If the portlet container does not support the POST-REDIRECT-GET design pattern, then the ACTION_PHASE and
		// RENDER_PHASE are both part of a single HTTP POST request. In such cases, the excluded request attributes must
		// be pro-actively removed here in the RENDER_PHASE (providing that the bridge request scope was created in the
		// ACTION_PHASE). Note that this must take place prior to the FacesContext getting constructed. This is because
		// the FacesContextFactory delegation chain might consult a request attribute that is supposed to be excluded.
		// This is indeed the case with Apache Trinidad {@link
		// org.apache.myfaces.trinidadinternal.context.FacesContextFactoryImpl.CacheRenderKit} constructor, which
		// consults a request attribute named "org.apache.myfaces.trinidad.util.RequestStateMap" that must first be
		// excluded.
		if (!portletContainer.isPostRedirectGetSupported() &&
				(bridgeRequestScope.getBeganInPhase() == Bridge.PortletPhase.ACTION_PHASE)) {
			bridgeRequestScope.removeExcludedAttributes(renderRequest);
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
