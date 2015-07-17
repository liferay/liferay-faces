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
package com.liferay.faces.alloy.component.progressbar.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.render.ClientBehaviorRenderer;


/**
 * @author  Kyle Stiemann
 */
public class ProgressBarAjaxBehavior extends AjaxBehaviorWrapper {

	// Private Members
	private AjaxBehavior ajaxBehavior;
	private String pollingOnsuccessExecutableCallback;
	private String pollingOnerrorExecutableCallback;

	/**
	 * @param  ajaxBehavior              The wrapped AjaxBehavior.
	 * @param  pollingOnsuccessCallback  The name of the callback which should be executed after a successful Ajax call.
	 *                                   This argument is used to facilitate a recursive call to the polling function of
	 *                                   the progressBar.
	 * @param  pollingOnerrorCallback    The name of the callback which should be executed after an error within an Ajax
	 *                                   call. This argument is used to stop polling the server when an error occurs.
	 */
	public ProgressBarAjaxBehavior(AjaxBehavior ajaxBehavior, String pollingOnsuccessCallback,
		String pollingOnerrorCallback) {

		this.ajaxBehavior = ajaxBehavior;
		this.pollingOnsuccessExecutableCallback = getExecutableCallback(pollingOnsuccessCallback);
		this.pollingOnerrorExecutableCallback = getExecutableCallback(pollingOnerrorCallback);
	}

	private String getExecutableCallback(String callback) {

		String executableCallback;

		if (callback == null) {
			executableCallback = "";
		}

		// Otherwise if the developer specified function is anonymous, surround it with parentheses and append
		// "(data)" so that it is executed.
		else if (callback.startsWith("function(")) {
			executableCallback = "(" + callback + ")(data)";
		}

		// Otherwise, append "(data)" so that the developer specified function is executed.
		else {
			executableCallback = callback.concat("(data)");
		}

		return executableCallback;
	}

	@Override
	public Collection<String> getExecute() {

		Collection<String> executeCollection = super.getExecute();
		List<String> executeList = new ArrayList<String>(executeCollection);

		if (!executeList.contains("@this")) {
			executeList.add("@this");
		}

		return executeList;
	}

	@Override
	public String getOnerror() {

		String onerror = super.getOnerror();
		String onerrorExecutableCallback = getExecutableCallback(onerror);

		// Add the pollingOnerror() callback to the onerror() callback in order to stop polling when an error occurs.
		StringBuilder onerrorStringBuilder = new StringBuilder();
		onerrorStringBuilder.append("function(data){");
		onerrorStringBuilder.append(pollingOnerrorExecutableCallback);
		onerrorStringBuilder.append(";");
		onerrorStringBuilder.append(onerrorExecutableCallback);
		onerrorStringBuilder.append(";}");

		return onerrorStringBuilder.toString();
	}

	/**
	 * The intention of the JSF API for this method is to return the name of the JavaScript function that will be called
	 * in one of three circumstances:
	 *
	 * <ol>
	 *   <li>Before the Ajax request is dispatched (data.status==="begin")</li>
	 *   <li>After the Ajax response is received (data.status==="complete")</li>
	 *   <li>After the partial response has been parsed and the DOM has been updated (data.status==="success")</li>
	 * </ol>
	 *
	 * In the case of progressBar, rather than returning a function name, this method needs to return an anonymous
	 * function that recursively calls the polling function when data.status==="success".
	 */
	@Override
	public String getOnevent() {

		String onevent = super.getOnevent();
		String oneventExecutableCallback = getExecutableCallback(onevent);

		// Add the onsuccess() callback to the onevent() callback in order to allow for a recursive call to our
		// polling function.
		StringBuilder oneventStringBuilder = new StringBuilder();
		oneventStringBuilder.append("function(data){if(data.status==='success'){");
		oneventStringBuilder.append(pollingOnsuccessExecutableCallback);
		oneventStringBuilder.append(";}");
		oneventStringBuilder.append(oneventExecutableCallback);
		oneventStringBuilder.append(";}");

		return oneventStringBuilder.toString();
	}

	@Override
	public Collection<String> getRender() {

		Collection<String> renderCollection = super.getRender();
		List<String> renderList = new ArrayList<String>(renderCollection);

		if (!renderList.contains("@this")) {
			renderList.add("@this");
		}

		return renderList;
	}

	@Override
	public String getScript(ClientBehaviorContext behaviorContext) {

		String script = null;
		FacesContext facesContext = behaviorContext.getFacesContext();
		ClientBehaviorRenderer clientBehaviorRenderer = getRenderer(facesContext);

		if (clientBehaviorRenderer != null) {
			script = clientBehaviorRenderer.getScript(behaviorContext, this);
		}

		return script;
	}

	@Override
	public AjaxBehavior getWrapped() {
		return ajaxBehavior;
	}
}
