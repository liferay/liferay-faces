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
package com.liferay.faces.util.context.internal;

import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;

import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitContextFactory;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextWrapper;
import javax.faces.event.PhaseId;

import com.liferay.faces.util.component.UICleanup;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * <p>This class is a wrapper around the {@link PartialViewContext}. Its purpose is to provide a way to provide a way
 * for components to handle the situation of moving from rendered="true" to rendered="false", since JSF does not provide
 * a way for components to handle this special "cleanup" case during the RENDER_RESPONSE phase.</p>
 *
 * @author      Neil Griffin
 * @deprecated  No replacement provided.
 */
@Deprecated
public class PartialViewContextCleanupImpl extends PartialViewContextWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PartialViewContextCleanupImpl.class);

	// Private Data Members
	private FacesContext facesContext;
	private PartialViewContext wrappedPartialViewContext;

	public PartialViewContextCleanupImpl(PartialViewContext partialViewContext, FacesContext facesContext) {
		this.wrappedPartialViewContext = partialViewContext;
		this.facesContext = facesContext;
	}

	@Override
	public void processPartial(PhaseId phaseId) {

		if (phaseId == PhaseId.RENDER_RESPONSE) {

			try {

				// Render the cleanup prior to delegating to the wrapped processPartial(PhaseId) method. Since rendered
				// cleanup is JavaScript (rather than markup), components that implement {@link UICleanup} need to
				// "render" all of their JavaScript into the {@link ExtFacesContext#getJavaScriptMap} ahead of time.
				// This allows the {@link PartialResponseWriterCleanupImpl} or the ICEfaces {@link
				// DOMPartialViewContext} to place the JavaScript at the end of the partial-response.
				if (isRenderAll()) {
					cleanupAll(facesContext);
				}
				else {
					cleanupPartial(facesContext, wrappedPartialViewContext.getRenderIds());
				}

				wrappedPartialViewContext.processPartial(phaseId);
			}
			catch (IOException e) {

				// Unfortunately the signature for {@link PartialViewContext#processPartial(PhaseId)} does throw
				// IOException
				logger.error(e);
				throw new RuntimeException(e);
			}
		}
		else {
			wrappedPartialViewContext.processPartial(phaseId);
		}
	}

	protected void cleanupAll(FacesContext facesContext) throws IOException {
		encodeCleanup(facesContext, facesContext.getViewRoot(), true);
	}

	protected void cleanupPartial(FacesContext facesContext, Collection<String> renderIds) {

		if ((renderIds != null) && (renderIds.size() > 0)) {

			VisitContextFactory visitContextFactory = (VisitContextFactory) FactoryFinder.getFactory(
					FactoryFinder.VISIT_CONTEXT_FACTORY);

			EnumSet<VisitHint> visitHints = EnumSet.of(VisitHint.EXECUTE_LIFECYCLE);
			VisitContext visitContext = visitContextFactory.getVisitContext(facesContext, renderIds, visitHints);
			VisitCallback visitCallback = new VisitCallbackCleanupImpl();
			facesContext.getViewRoot().visitTree(visitContext, visitCallback);
		}
	}

	protected void encodeCleanup(FacesContext facesContext, UIComponent uiComponent, boolean parentRendered)
		throws IOException {

		// FACES-1497: Push the specified UIComponent to the EL in order to ensure that EL expressions like
		// "#{component}" and "{cc}" resolve to the specified UIComponent before attempting to call
		// UIComponent.isRendered() below.
		uiComponent.pushComponentToEL(facesContext, uiComponent);

		// Determine whether or not the specified UIComponent is rendered, taking into consideration the
		// specified flag that indicates whether or not the parent UIComponent is rendered.
		boolean rendered = (parentRendered && uiComponent.isRendered());

		// If the specified UIComponent has cleanup abilities and it is not rendered, then instruct it to encode its
		// cleanup markup to the response.
		if (uiComponent instanceof UICleanup) {
			UICleanup uiCleanup = (UICleanup) uiComponent;

			if (!rendered) {
				uiCleanup.encodeCleanup(facesContext);
			}
		}

		// Otherwise, recurse through all of the children.
		else {

			// FACES-1956: If the parent component is data-iterator type of component then its children cannot undergo
			// cleanup since they may have EL-expressions that depend on the current iteration variable.
			if (!(uiComponent instanceof UIData)) {

				Iterator<UIComponent> itr = uiComponent.getFacetsAndChildren();

				while (itr.hasNext()) {
					UIComponent childUIComponent = itr.next();
					encodeCleanup(facesContext, childUIComponent, rendered);
				}
			}
		}

		// FACES-1497: Pop the specified UIComponent from the EL.
		uiComponent.popComponentFromEL(facesContext);
	}

	/**
	 * This method is missing from the {@link PartialViewContextWrapper} class so it must be implemented here.
	 */
	@Override
	public void setPartialRequest(boolean isPartialRequest) {
		wrappedPartialViewContext.setPartialRequest(isPartialRequest);
	}

	@Override
	public PartialViewContext getWrapped() {
		return wrappedPartialViewContext;
	}

	protected class VisitCallbackCleanupImpl implements VisitCallback {

		public VisitResult visit(VisitContext visitContext, UIComponent uiComponent) {

			boolean parentRendered = true;
			UIComponent parent = uiComponent.getParent();

			if (parent != null) {
				parentRendered = parent.isRendered();
			}

			try {
				encodeCleanup(facesContext, uiComponent, parentRendered);
			}
			catch (IOException e) {
				logger.error(e);
			}

			return VisitResult.REJECT;
		}

	}
}
