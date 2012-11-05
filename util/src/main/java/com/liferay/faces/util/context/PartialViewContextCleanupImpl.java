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
package com.liferay.faces.util.context;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextWrapper;
import javax.faces.event.PhaseId;

import com.liferay.faces.util.component.UICleanup;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * <p>This class is a wrapper around the {@link PartialViewContext}. Its purpose is to provide a way to provide a way
 * for components to handle the situation of moving from rendered="true" to rendered="false", since JSF does not provide
 * a way for components to handle this special "cleanup" case during the RENDER_RESPONSE phase.</p>
 *
 * @author  Neil Griffin
 */
public class PartialViewContextCleanupImpl extends PartialViewContextWrapper {

	// Private Constants
	private static final boolean ICEFACES_DETECTED = ProductMap.getInstance().get(ProductConstants.ICEFACES)
		.isDetected();

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

        if (phaseId != PhaseId.RENDER_RESPONSE) {
            wrappedPartialViewContext.processPartial(phaseId);
            return;
        }

        // According to documentation of PartialViewContext
        // only certain components should be processed
        Set<String> processIds = new HashSet<String>();
        if (isRenderAll()) {
            processIds.add(facesContext.getViewRoot().getClientId());
        }
        else {
            processIds.addAll(getRenderIds());
        }

		try {

			// If ICEfaces is detected, then call encodeCleanup(...) prior to delegating to the wrapped
			// processPartial(PhaseId) method. The reason for this ordering is due the design of the ICEfaces {@link
			// DOMPartialViewContext#processPartial(PhaseId)} method. After the processPartial method has encoded all of
			// the components in the view, it then calls the ICEfaces {@link
			// JavaScriptRunner#collateScripts(FacesContext)} method, which concatenates all of the scripts that were
			// added via the {@link JavaScriptRunner#runScript(FacesContext, String)} method. Because of this design,
			// any calls to {@link JavaScriptRunner#runScript(FacesContext, String)} must be made prior to the
			// completion of the component tree being rendered in its entirety.
			if (ICEFACES_DETECTED) {
				walkTree(facesContext, facesContext.getViewRoot(), processIds);
				wrappedPartialViewContext.processPartial(phaseId);
			}

			// Otherwise, delegate to the wrapped processPartial(PhaseId) method prior to calling encodeCleanup(...).
			// This will ensure that any clean up <script>...</script> elements appear at the end of the
			// partial-response, which is important because script execution blocks rendering.
			else {
				wrappedPartialViewContext.processPartial(phaseId);
                walkTree(facesContext, facesContext.getViewRoot(), processIds);
			}
		}
		catch (IOException e) {

			// Unfortunately the signature for {@link PartialViewContext#processPartial(PhaseId)} does throw
			// IOException
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

    private void walkTree(FacesContext facesContext, UIComponent uiComponent, Collection<String> processIds)
            throws IOException {
        final String id = uiComponent.getClientId();
        if (processIds.contains(id)) {
            // assume here that rendered is true because this is more possible than false
            encodeCleanup(facesContext, uiComponent, true);
            processIds.remove(id);
        }
        else {
            Iterator<UIComponent> itr = uiComponent.getFacetsAndChildren();

            while (itr.hasNext()) {
                UIComponent childComponent = itr.next();
                walkTree(facesContext, childComponent, processIds);
            }
        }
    }

	protected void encodeCleanup(FacesContext facesContext, UIComponent uiComponent, boolean parentRendered)
		throws IOException {
		boolean rendered = (parentRendered && uiComponent.isRendered());

		if (uiComponent instanceof UICleanup) {
			UICleanup uiCleanup = (UICleanup) uiComponent;

			if (!rendered) {
				uiCleanup.encodeCleanup(facesContext);
			}
		}
		else {
			Iterator<UIComponent> itr = uiComponent.getFacetsAndChildren();

			while (itr.hasNext()) {
				UIComponent childUIComponet = itr.next();
				encodeCleanup(facesContext, childUIComponet, rendered);
			}
		}
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

}
