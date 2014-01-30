/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.renderkit.html_basic;

import java.util.Set;

import javax.faces.bean.ViewScoped;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.portlet.PortletRequest;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeUtil;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * <p>This class is a JSF {@link PhaseListener} that listens to the {@link PhaseId#INVOKE_APPLICATION} and {@link
 * PhaseId#RENDER_RESPONSE} phases of the JSF lifecycle. Along with {@link HeadManagedBean} and {@link
 * HeadRendererBridgeImpl}, this class helps provides a solution to an issue regarding Ajax-initiated execution of
 * navigation-rules in a portlet. When a portal page is first rendered by the portal, all of the portlets on the page
 * participate in the {@link PortletRequest#RENDER_PHASE} of the Portlet lifecycle. During this initial HTTP-GET
 * operation, the bridge has the ability to add JavaScript and CSS resources to the &lt;head&gt; section of the rendered
 * portal page. Subsequent Ajax-initiated execution of the JSF lifecycle via the {@link PortletRequest#RESOURCE_PHASE}
 * are NOT ABLE add resources to the to the &lt;head&gt; section.</p>
 *
 * @see     http://issues.liferay.com/browse/FACES-180
 * @author  Neil Griffin
 */
public class HeadPhaseListener implements PhaseListener {

	// Private Constants
	private static final String HEAD_RESOURCE_IDS = "HEAD_RESOURCE_IDS";

	// serialVersionUID
	private static final long serialVersionUID = 8502242430265622811L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(HeadPhaseListener.class);

	public void afterPhase(PhaseEvent phaseEvent) {

		// This method just does some logging. It's useful to the developer to determine if a navigation-rule
		// fired, causing new JSF view to be restored after the INVOKE_APPLICATION phase finished.
		if (logger.isDebugEnabled() && (phaseEvent.getPhaseId() == PhaseId.INVOKE_APPLICATION)) {
			FacesContext facesContext = phaseEvent.getFacesContext();
			String viewId = facesContext.getViewRoot().getViewId();
			logger.debug("After INVOKE_APPLICATION: viewId=[{0}]", viewId);
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {

		Bridge.PortletPhase portletRequestPhase = BridgeUtil.getPortletRequestPhase();

		if ((portletRequestPhase == Bridge.PortletPhase.RESOURCE_PHASE) ||
				(portletRequestPhase == Bridge.PortletPhase.RENDER_PHASE)) {

			if (phaseEvent.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES) {
				beforeApplyRequestValuesPhase(phaseEvent);
			}
			else if (phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE) {
				FacesContext facesContext = phaseEvent.getFacesContext();

				if (facesContext.getPartialViewContext().isAjaxRequest()) {
					beforeAjaxifiedRenderResponsePhase(phaseEvent);
				}
			}
		}

	}

	/**
	 * <p>This method is called before the {@link PhaseId#RENDER_RESPONSE} phase of the JSF lifecycle is executed. The
	 * purpose of this timing is to pick up where the {@link #beforeInvokeApplicationPhase(PhaseEvent)} method left off.
	 * It might be the case that a navigation-rule has fired and a NEW JSF view has been loaded up after the {@link
	 * PhaseId#APPLY_REQUEST_VALUES} phase (in the case of immediate="true") or the {@link PhaseId#INVOKE_APPLICATION}
	 * phase (in the case of immediate="false") has completed. If this is the case, then the list of head resourceIds in
	 * the {@link HeadManagedBean} needs to be repopulated from the list found in the Flash scope.</p>
	 */
	protected void beforeAjaxifiedRenderResponsePhase(PhaseEvent phaseEvent) {
		FacesContext facesContext = phaseEvent.getFacesContext();
		Flash flash = facesContext.getExternalContext().getFlash();
		String viewId = facesContext.getViewRoot().getViewId();

		@SuppressWarnings("unchecked")
		Set<String> headResourceIdsFromFlash = (Set<String>) flash.get(HEAD_RESOURCE_IDS);

		if (headResourceIdsFromFlash != null) {
			HeadManagedBean headManagedBean = HeadManagedBean.getInstance(facesContext);
			Set<String> managedBeanResourceIds = headManagedBean.getHeadResourceIds();

			for (String resourceIdFromFlash : headResourceIdsFromFlash) {

				if (!managedBeanResourceIds.contains(resourceIdFromFlash)) {
					managedBeanResourceIds.add(resourceIdFromFlash);
					logger.debug(
						"Added resourceId=[{0}] from the Flash scope to the list of resourceIds in the HeadManagedBean for viewId=[{1}]",
						resourceIdFromFlash, viewId);
				}
			}
		}
	}

	/**
	 * <p>This method is called before the {@link PhaseId#APPLY_REQUEST_VALUES} phase of the JSF lifecycle is executed.
	 * The purpose of this timing is to handle the case when the user clicks on a {@link UICommand} component (like
	 * h:commandButton or h:commandLink) that has been either Auto-ajaxified by ICEfaces, or manually Ajaxified by the
	 * developer using code like the following:</p>
	 *
	 * <p><code>&lt;f:ajax execute="@form" render=" @form" /&gt;</code></p>
	 *
	 * <p>When this happens, we need to somehow remember the list of JavaScript and/or CSS resources that are currently
	 * in the &lt;head&gt; section of the portal page. This is because a navigation-rule might fire which could cause a
	 * new view to be rendered in the {@link PhaseId#RENDER_RESPONSE} phase that is about to follow this {@link
	 * PhaseId#APPLY_REQUEST_VALUES} phase. The list of resources would be contained in the {@link HeadManagedBean}
	 * {@link ViewScoped} instance that is managed by the JSF managed-bean facility. The list would have been populated
	 * initially in the {@link HeadManagedBean} by the {@link HeadRender} during the initial HTTP-GET of the portal
	 * page. The way we "remember" the list is by placing it into the JSF 2 {@link Flash} scope. This scope is used
	 * because it is very short-lived and survives any navigation-rules that might fire, thereby causing the rendering
	 * of a new JSF view.</p>
	 *
	 * <p>The story is continued in the {@link #beforeRenderResponsePhase(PhaseEvent)} method below...</p>
	 */
	protected void beforeApplyRequestValuesPhase(PhaseEvent phaseEvent) {

		// Get the list of resourceIds that might be contained in the Flash scope. Note that they would have been
		// placed into the Flash scope by this very same method, except during in the case below for the
		// RENDER_RESPONSE phase.
		FacesContext facesContext = phaseEvent.getFacesContext();
		Flash flash = facesContext.getExternalContext().getFlash();

		@SuppressWarnings("unchecked")
		Set<String> headResourceIdsFromFlash = (Set<String>) flash.get(HEAD_RESOURCE_IDS);

		// Log the viewId so that it can be visually compared with the value that is to be logged after the
		// INVOKE_APPLICATION phase completes.
		logger.debug("Before APPLY_REQUEST_VALUES: viewId=[{0}]", facesContext.getViewRoot().getViewId());

		// If the Flash scope does not yet contain a list of head resourceIds, then the scope needs to be populated
		// with a list so that the {@link #beforeRenderResponsePhase(PhaseEvent)} method below can retrieve it.
		if (headResourceIdsFromFlash == null) {

			HeadManagedBean headManagedBean = HeadManagedBean.getInstance(facesContext);

			// Note that in the case where a portlet RESOURCE_PHASE was invoked with a "portlet:resource" type of URL,
			// there will be no HeadManagedBean available.
			if (headManagedBean != null) {
				flash.put(HEAD_RESOURCE_IDS, headManagedBean.getHeadResourceIds());
			}
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
