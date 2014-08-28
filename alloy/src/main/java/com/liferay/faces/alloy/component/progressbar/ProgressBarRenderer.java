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
package com.liferay.faces.alloy.component.progressbar;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.progressbar.internal.ProgressBarAjaxBehavior;
import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.context.ExtFacesContext;
import com.liferay.faces.util.js.JavaScriptFragment;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.BufferedScriptResponseWriter;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = ProgressBar.COMPONENT_FAMILY, rendererType = ProgressBar.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
	}
)
//J+
public class ProgressBarRenderer extends ProgressBarRendererBase {

	private static final String COMPLETE = "complete";
	private static final String CONTENT_BOX_SUFFIX = "_contentBox";
	private static final String HIDDEN_SUFFIX = "_hidden";
	private static final String PAGE_DIRECTION = "pageDirection";
	private static final String TOKEN = "{0}";
	private static final String VALUE_CHANGE = "valueChange";
	private static final String VERTICAL = "vertical";

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		ProgressBar progressBar = (ProgressBar) uiComponent;
		String clientVarName = ComponentUtil.getClientVarName(facesContext, progressBar);
		String clientKey = progressBar.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}

		Map<String, List<ClientBehavior>> clientBehaviorMap = progressBar.getClientBehaviors();
		List<ClientBehavior> pollEventClientBehaviors = clientBehaviorMap.get(ProgressBar.POLL);

		// If the developer has specified <f:ajax event="poll" />, then
		if ((pollEventClientBehaviors != null) && !pollEventClientBehaviors.isEmpty()) {

			// Build up an anonymous function, which contains all clientBehaviors for the "poll" event, so that it can
			// be passed to LFAI.initProgressBarServerMode().

			//J-
			//	function(pollingFunction) {
			//		jsf.ajax.request(clientId, 'poll', {
			//			render: clientId,
			//			execute: clientId,
			//			onevent: function(data){
			//				if(data.status==='success'){
			//					pollingFunction();
			//				}
			//			}
			//		});
			//		jsf.ajax.request(...);
			//		...
			//		jsf.ajax.request(...);
			//	}
			//J+
			StringBuilder buf = new StringBuilder();
			buf.append("function(pollingFunction){");

			String clientId = progressBar.getClientId(facesContext);
			ClientBehaviorContext clientBehaviorContext = ClientBehaviorContext.createClientBehaviorContext(
					facesContext, progressBar, ProgressBar.POLL, clientId, null);
			int size = pollEventClientBehaviors.size();

			// It is possible to specify multiple <f:ajax event="poll" /> tags (even though there is no benefit).
			for (int i = 0; i < size; i++) {

				ClientBehavior pollEventClientBehavior = pollEventClientBehaviors.get(i);

				// Ensure that "@this" is present in the render attribute for one of the clientBehaviors. That will
				// cause this renderer to be invoked when a polling ajax request occurs.
				if (i == 0) {

					AjaxBehavior ajaxBehavior = (AjaxBehavior) pollEventClientBehavior;
					pollEventClientBehavior = new ProgressBarAjaxBehavior(ajaxBehavior, clientId, "pollingFunction");
				}

				buf.append(pollEventClientBehavior.getScript(clientBehaviorContext));
				buf.append(";");
			}

			buf.append("}");

			//J-
			//	Liferay.component('clientKey')
			//J+
			JavaScriptFragment liferayComponent = new JavaScriptFragment("Liferay.component('" + clientKey + "')");
			Integer pollingDelay = progressBar.getPollingDelay();

			//J-
			//	LFAI.initProgressBarServerMode(Liferay.component('clientKey'), 'clientId', pollingDelay,
			//		function(pollingFunction) {
			//			jsf.ajax.request(clientId, 'poll', {
			//				render: clientId,
			//				execute: clientId,
			//				onevent: function(data){
			//					if(data.status==='success'){
			//						pollingFunction();
			//					}
			//				}
			//			});
			//			jsf.ajax.request(...);
			//			...
			//			jsf.ajax.request(...);
			//		}
			//	);
			//J+
			RendererUtil.encodeFunctionCall(responseWriter, "LFAI.initProgressBarServerMode", liferayComponent,
				clientId, pollingDelay, buf);
		}

		// Otherwise the component is in client mode.
		else {

			//J-
			//	LFAI.initProgressBarClientMode(Liferay.component('clientKey'), projectStageDevelopment);
			//J+
			JavaScriptFragment liferayComponent = new JavaScriptFragment("Liferay.component('" + clientKey + "')");
			RendererUtil.encodeFunctionCall(responseWriter, "LFAI.initProgressBarClientMode", liferayComponent);
		}
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// If the component is not currently polling the server via an Ajax request, then render markup.
		// Otherwise, only the JavaScript necessary to update the progressBar is rendered.
		if (!isAjaxPolling(facesContext, uiComponent)) {

			// Start the encoding of the outermost <div> element.
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.startElement(StringPool.DIV, uiComponent);

			String clientId = uiComponent.getClientId(facesContext);
			responseWriter.writeAttribute(StringPool.ID, clientId, StringPool.ID);

			Styleable styleable = (Styleable) uiComponent;
			RendererUtil.encodeStyleable(responseWriter, styleable);

			// If the developer has specified <f:ajax event="poll" />, then render the hidden input which flags
			// whether or not the component is currently polling.
			if (isServerPollingEnabled(uiComponent)) {

				String hiddenClientId = clientId.concat(HIDDEN_SUFFIX);
				responseWriter.startElement(StringPool.INPUT, null);
				responseWriter.writeAttribute(StringPool.ID, hiddenClientId, null);
				responseWriter.writeAttribute(StringPool.NAME, hiddenClientId, null);
				responseWriter.writeAttribute(StringPool.TYPE, StringPool.HIDDEN, null);
				responseWriter.writeAttribute(StringPool.VALUE, StringPool.BLANK, null);
				responseWriter.endElement(StringPool.INPUT);
			}

			// Encode the contentBox of the progressBar.
			String contentBoxClientId = clientId.concat(CONTENT_BOX_SUFFIX);
			responseWriter.startElement(StringPool.DIV, null);
			responseWriter.writeAttribute(StringPool.ID, contentBoxClientId, null);
			responseWriter.endElement(StringPool.DIV);
		}
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// If the component is not currently polling the server via an Ajax request, then render markup.
		// Otherwise, only the JavaScript necessary to update the progressBar is rendered.
		if (!isAjaxPolling(facesContext, uiComponent)) {

			// Finish the encoding of the outermost </div> element.
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.endElement(StringPool.DIV);
		}
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter,
		ProgressBar progressBar, boolean first) throws IOException {

		// render: true
		encodeWidgetRender(responseWriter, first);
		first = false;

		// contentBox
		String clientId = progressBar.getClientId(facesContext);
		String contentBoxClientId = clientId.concat(CONTENT_BOX_SUFFIX);
		encodeClientId(responseWriter, AlloyRendererUtil.CONTENT_BOX, contentBoxClientId, first);
		first = false;

		// Begin encoding event listeners that occur on the event.
		encodeNonEscapedObject(responseWriter, StringPool.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		// complete
		boolean onFirst = true;
		String oncomplete = progressBar.getOncomplete();
		Map<String, List<ClientBehavior>> clientBehaviorMap = progressBar.getClientBehaviors();
		List<ClientBehavior> clientBehaviorsForComplete = clientBehaviorMap.get(
				ProgressCompleteEvent.PROGRESS_COMPLETE);

		if ((oncomplete != null) || ((clientBehaviorsForComplete != null) && !clientBehaviorsForComplete.isEmpty())) {

			//J-
			//	function(event) {
			//		oncomplete();
			//		jsf.ajax.request(...);
			//		jsf.ajax.request(...);
			//		...
			//		jsf.ajax.request(...);
			//	}
			//J+
			StringBuilder onCompleteBuilder = new StringBuilder();
			onCompleteBuilder.append("function(event){");

			if (oncomplete != null) {
				onCompleteBuilder.append(oncomplete);
			}

			if ((clientBehaviorsForComplete != null) && !clientBehaviorsForComplete.isEmpty()) {

				ClientBehaviorContext clientBehaviorContext = ClientBehaviorContext.createClientBehaviorContext(
						facesContext, progressBar, ProgressCompleteEvent.PROGRESS_COMPLETE, clientId, null);

				for (ClientBehavior clientBehaviorForComplete : clientBehaviorsForComplete) {
					onCompleteBuilder.append(clientBehaviorForComplete.getScript(clientBehaviorContext));
					onCompleteBuilder.append(";");
				}
			}

			onCompleteBuilder.append("}");
			encodeNonEscapedObject(responseWriter, COMPLETE, onCompleteBuilder.toString(), onFirst);
			onFirst = false;
		}

		// label
		String label = progressBar.getLabel();

		// If the developer has specified a label and the label contains the TOKEN representin the value, then create
		// a callback which will update the label each time the value changes.

		//J-
		//	valueChange: function(event) {
		//		this.set('label','escapedLabel'.replace(LFAI.TOKEN_REGEX, event.newVal));
		//	}
		//J+
		if ((label != null) && label.contains(TOKEN)) {

			String escapedLabel = RendererUtil.escapeJavaScript(label);
			StringBuilder onValueChangeStringBuilder = new StringBuilder();
			onValueChangeStringBuilder.append("function(event){this.set('label','");
			onValueChangeStringBuilder.append(escapedLabel);
			onValueChangeStringBuilder.append("'.replace(LFAI.TOKEN_REGEX, event.newVal));}");
			encodeNonEscapedObject(responseWriter, VALUE_CHANGE, onValueChangeStringBuilder.toString(), onFirst);
			onFirst = false;
		}

		// Finish encoding event listeners that occur on the event.
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		first = false;
	}

	/**
	 * This method is being overridden in order to set the value of the progressBar when server-side polling occurs.
	 * Otherwise, this method simply calls super.encodeJavaScript() in order to render the component normally.
	 */
	@Override
	protected void encodeJavaScript(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		if (isAjaxPolling(facesContext, uiComponent)) {

			ProgressBar progressBar = (ProgressBar) uiComponent;
			String clientVarName = ComponentUtil.getClientVarName(facesContext, progressBar);
			String clientKey = progressBar.getClientKey();

			if (clientKey == null) {
				clientKey = clientVarName;
			}

			// Buffer all JavaScript so that it is rendered in the <eval> section of the partial response.
			BufferedScriptResponseWriter bufferedScriptResponseWriter = new BufferedScriptResponseWriter();
			String clientId = progressBar.getClientId(facesContext);
			String hiddenClientId = clientId.concat(HIDDEN_SUFFIX);

			//J-
			//	Liferay.component('clientKey')
			//J+
			JavaScriptFragment liferayComponent = new JavaScriptFragment("Liferay.component('" + clientKey + "')");
			Integer value = progressBar.getValue();

			//J-
			//	LFAI.setProgressBarServerValue('hiddenClientId', Liferay.component('clientKey'), value);
			//J+
			RendererUtil.encodeFunctionCall(bufferedScriptResponseWriter, "LFAI.setProgressBarServerValue",
				hiddenClientId, liferayComponent, value);

			String bufferedScriptString = bufferedScriptResponseWriter.toString();
			Map<String, String> javaScriptMap = ExtFacesContext.getInstance().getJavaScriptMap();
			javaScriptMap.put(clientId, bufferedScriptString);
		}
		else {
			super.encodeJavaScript(facesContext, uiComponent);
		}
	}

	@Override
	protected void encodeLabel(ResponseWriter responseWriter, ProgressBar progressBar, String label, boolean first)
		throws IOException {

		Integer value = progressBar.getValue();
		label = label.replace(TOKEN, value.toString());
		super.encodeLabel(responseWriter, progressBar, label, first);
	}

	@Override
	protected void encodeOrientation(ResponseWriter responseWriter, ProgressBar progressBar, String layout,
		boolean first) throws IOException {

		if (PAGE_DIRECTION.equals(layout)) {
			super.encodeOrientation(responseWriter, progressBar, VERTICAL, first);
		}
	}

	private boolean isServerPollingEnabled(UIComponent uiComponent) {

		ProgressBar progressBar = (ProgressBar) uiComponent;
		Map<String, List<ClientBehavior>> clientBehaviorMap = progressBar.getClientBehaviors();
		List<ClientBehavior> clientBehaviorsForPolling = clientBehaviorMap.get(ProgressBar.POLL);

		return ((clientBehaviorsForPolling != null) && !clientBehaviorsForPolling.isEmpty());
	}

	private boolean isAjaxPolling(FacesContext facesContext, UIComponent uiComponent) {

		boolean polling = false;

		if (isServerPollingEnabled(uiComponent) && isAjax(facesContext)) {

			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
			String hiddenClientId = uiComponent.getClientId(facesContext) + HIDDEN_SUFFIX;
			String pollingString = requestParameterMap.get(hiddenClientId);
			polling = Boolean.parseBoolean(pollingString);
		}

		return polling;
	}
}
