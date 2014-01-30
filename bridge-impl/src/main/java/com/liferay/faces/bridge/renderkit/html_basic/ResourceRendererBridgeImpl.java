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

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.render.Renderer;

import com.liferay.faces.bridge.application.ResourceInfo;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.RendererWrapper;


/**
 * This class serves as a wrapper around the renderer provided by the JSF runtime (Mojarra, MyFaces) for either the
 * h:outputScript or h:outputStylesheet components. The main purpose of wrapping the renderer is to provide a way for
 * the bridge to keep track of which resources are being added to the &lt;head&gt;...&lt;/head&gt; section of the portal
 * page.
 *
 * @author  Neil Griffin
 */
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
public class ResourceRendererBridgeImpl extends RendererWrapper implements ComponentSystemEventListener, StateHolder {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceRendererBridgeImpl.class);

	// Private Data Members
	private boolean transientFlag;
	private Renderer wrappedRenderer;

	/**
	 * This zero-arg constructor is required by the {@link javax.faces.component.StateHolderSaver} class during the
	 * RESTORE_VIEW phase of the JSF lifecycle. The reason why this class is involved in restoring state is because the
	 * {@link javax.faces.component.UIComponent.ComponentSystemEventListenerAdapter} implements {@link
	 * javax.faces.component.StateHolder} and will attempt to restore the state of any class in the restored view that
	 * implements {@link ComponentSystemEventListener}. It does this first by instantiating the class with a zero-arg
	 * constructor, and then calls the {@link #restoreState(FacesContext, Object)} method.
	 */
	public ResourceRendererBridgeImpl() {
		// Defer initialization of wrappedRenderer until restoreState(FacesContext, Object) is called.
	}

	public ResourceRendererBridgeImpl(Renderer renderer) {
		this.wrappedRenderer = renderer;
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResourceInfo resourceInfo = new ResourceInfo(uiComponent);
		String resourceId = resourceInfo.getId();

		// Determine whether or not the specified resource is already present in the <head> section of the portal page.
		HeadManagedBean headManagedBean = HeadManagedBean.getInstance(facesContext);

		Set<String> headResourceIdsFromManagedBean = null;

		if (headManagedBean == null) {

			// Since the HeadManagedBean can't be found, this request is likely executing in a JSP environment.
			headResourceIdsFromManagedBean = new HashSet<String>();
		}
		else {
			headResourceIdsFromManagedBean = headManagedBean.getHeadResourceIds();
		}

		boolean alreadyPresentInPortalPageHead = headResourceIdsFromManagedBean.contains(resourceId);

		// If the specified resource is NOT already in the <head> section of the portal page, then
		if (!alreadyPresentInPortalPageHead) {

			boolean ajaxRequest = facesContext.getPartialViewContext().isAjaxRequest();

			// If this is taking place during an Ajax request, then:
			if (ajaxRequest) {

				// Set a custom response writer that knows how to remove double-encoded ampersands from URLs.
				ResponseWriter responseWriter = facesContext.getResponseWriter();
				facesContext.setResponseWriter(new ResponseWriterResourceImpl(responseWriter));

				// Ask the wrapped renderer to encode the script to a custom ResponseWriter
				super.encodeEnd(facesContext, uiComponent);

				// Restore the original response writer.
				facesContext.setResponseWriter(responseWriter);
			}

			// Otherwise:
			else {

				// Ask the wrapped renderer to encode the script to a custom ResponseWriter
				super.encodeEnd(facesContext, uiComponent);

				// If the h:head part of the component tree is being rendered, then
				if (facesContext.getResponseWriter() instanceof HeadResponseWriter) {

					// Mark the resource as having been added to the head.
					headResourceIdsFromManagedBean.add(resourceId);

					logger.debug("Marking resource resourceId=[{0}] as being present in the head", resourceId);
				}
			}
		}
	}

	/**
	 * Since the Mojarra {@link com.sun.faces.renderkit.html_basic.ScriptStyleBaseRenderer} class implements {@link
	 * ComponentSystemEventListener}, this class must implement that interface too, since this is a wrapper type of
	 * class. Mojarra uses this method to intercept {@link PostAddToViewEvent} in order to add script and link resources
	 * to the head (if the target attribute has a value of "head").
	 */
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {

		// If the wrapped renderer has the ability to listen to component system events, then invoke the event
		// processing on the wrapped renderer. This is necessary when wrapping the Mojarra ScriptRenderer or
		// StylesheetRenderer because they extend ScriptStyleBaseRenderer which attempts to add the component
		// associated with the specified event as a resource on the view root.
		if (wrappedRenderer instanceof ComponentSystemEventListener) {
			ComponentSystemEventListener wrappedListener = (ComponentSystemEventListener) wrappedRenderer;
			wrappedListener.processEvent(event);
		}
		else {
			logger.debug("Wrapped renderer=[{0}] does not implement ComponentSystemEventListener", wrappedRenderer);
		}
	}

	public void restoreState(FacesContext facesContext, Object state) {

		if (wrappedRenderer == null) {

			try {
				String wrappedRendererClassName = (String) state;
				Class<?> wrappedRendererClass = Class.forName(wrappedRendererClassName);
				wrappedRenderer = (Renderer) wrappedRendererClass.newInstance();
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public Object saveState(FacesContext facesContext) {
		return wrappedRenderer.getClass().getName();
	}

	public boolean isTransient() {
		return transientFlag;
	}

	public void setTransient(boolean newTransientValue) {
		this.transientFlag = newTransientValue;
	}

	@Override
	public Renderer getWrapped() {
		return wrappedRenderer;
	}
}
