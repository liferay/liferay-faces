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
package com.liferay.faces.alloy.component.autocomplete.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.autocomplete.AutoComplete;
import com.liferay.faces.alloy.render.internal.JavaScriptFragment;
import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;
import com.liferay.faces.util.render.internal.BufferedScriptResponseWriter;


/**
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = AutoComplete.COMPONENT_FAMILY, rendererType = AutoComplete.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
	}
)
//J+
public class AutoCompleteRenderer extends AutoCompleteRendererBase {

	// Private Constants
	private static final String ALLOW_BROWSER_AUTOCOMPLETE = "allowBrowserAutocomplete";
	private static final String AUTOCOMPLETE_FILTERS = "autocomplete-filters";
	private static final String AUTOCOMPLETE_HIGHLIGHTERS = "autocomplete-highlighters";
	private static final String CONTENT_BOX_SUFFIX = "_contentBox";
	private static final String HIDDEN_SUFFIX = "_hidden";
	private static final String INPUT_NODE = "inputNode";
	private static final String INPUT_SUFFIX = "_input";
	private static final String NODE_EVENT_SIMULATE = "node-event-simulate";
	private static final String SOURCE = "source";
	private static final String VALUE_CHANGE = "valueChange";
	private static final String VALUE_CHANGE_SCRIPT =
		"{select: function(event) {this.get('inputNode').simulate('change');}}";

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// If the developer has specified a server-side filtering, then
		if (isServerFilteringEnabled(uiComponent)) {

			ResponseWriter responseWriter = facesContext.getResponseWriter();
			ClientComponent clientComponent = (ClientComponent) uiComponent;
			String clientVarName = getClientVarName(facesContext, clientComponent);
			String clientKey = clientComponent.getClientKey();

			if (clientKey == null) {
				clientKey = clientVarName;
			}

			//J-
			// var clientVarName = Liferay.component('clientKey');
			//J+
			encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);

			JavaScriptFragment clientVarNameJSFragment = new JavaScriptFragment(clientVarName);
			String clientId = uiComponent.getClientId(facesContext);
			String hiddenClientId = clientId + HIDDEN_SUFFIX;

			String namingContainerId = null;
			UIViewRoot viewRoot = facesContext.getViewRoot();

			if (viewRoot instanceof NamingContainer) {
				namingContainerId = viewRoot.getContainerClientId(facesContext);
			}

			//J-
			// LFAI.setAutoCompleteEventListeners(A, clientVarName, 'escapedHiddenClientId', 'clientId',
			//	'namingContainerId');
			//J+
			encodeFunctionCall(responseWriter, "LFAI.initAutoCompleteServerMode", clientVarNameJSFragment,
				hiddenClientId, clientId, namingContainerId);
		}
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// If the component is not currently filtering on the server via an Ajax request, then render markup.
		// Otherwise, only the JavaScript necessary to update the autoComplete results is rendered.
		if (!isAjaxFiltering(facesContext, uiComponent)) {

			// Start the encoding of the outermost <div> element.
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.startElement(StringPool.DIV, uiComponent);

			// Encode the "id" attribute on the outermost <div> element.
			String clientId = uiComponent.getClientId(facesContext);
			responseWriter.writeAttribute(StringPool.ID, clientId, StringPool.ID);

			// Encode the "class" and "style" attributes on the outermost <div> element.
			Styleable styleable = (Styleable) uiComponent;
			RendererUtil.encodeStyleable(responseWriter, styleable);

			// Encode the text input by delegating to the renderer from the JSF runtime.
			AutoCompleteInputResponseWriter autoCompleteInputResponseWriter = new AutoCompleteInputResponseWriter(
					responseWriter, StringPool.INPUT, clientId + INPUT_SUFFIX);
			super.encodeAll(facesContext, uiComponent, autoCompleteInputResponseWriter);

			// Encode the contentBox of the autoComplete.
			responseWriter.startElement(StringPool.DIV, uiComponent);
			responseWriter.writeAttribute(StringPool.ID, clientId + CONTENT_BOX_SUFFIX, null);
			responseWriter.endElement(StringPool.DIV);

			// If the developer has specified a server-side filtering, then render the hidden input which is used to
			// submit the query for server-side filtering.
			if (isServerFilteringEnabled(uiComponent)) {

				// Encode the hidden input which will be used to submit the query to the server.
				responseWriter.startElement(StringPool.INPUT, uiComponent);
				responseWriter.writeAttribute(StringPool.ID, clientId + HIDDEN_SUFFIX, null);
				responseWriter.writeAttribute(StringPool.NAME, clientId + HIDDEN_SUFFIX, null);
				responseWriter.writeAttribute(StringPool.TYPE, StringPool.HIDDEN, null);
				responseWriter.writeAttribute(StringPool.VALUE, StringPool.BLANK, null);
				responseWriter.endElement(StringPool.INPUT);
			}
		}
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// If the component is not currently filtering on the server via an Ajax request, then render markup.
		// Otherwise, only the JavaScript necessary to update the autoComplete results is rendered.
		if (!isAjaxFiltering(facesContext, uiComponent)) {

			// Finish the encoding of the outermost </div> element.
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.endElement(StringPool.DIV);
		}
	}

	@Override
	protected void encodeClientFilterType(ResponseWriter responseWriter, AutoComplete autoComplete,
		String clientFilterType, boolean first) throws IOException {

		String clientCustomFilter = autoComplete.getClientCustomFilter();

		if (!isServerFilteringEnabled(autoComplete) && (clientCustomFilter == null)) {
			super.encodeResultFilters(responseWriter, autoComplete, clientFilterType, first);
		}
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter,
		AutoComplete autoComplete, boolean first) throws IOException {
		encodeWidgetRender(responseWriter, first);
		first = false;

		encodeHiddenAttributeSource(facesContext, responseWriter, autoComplete, first);
		first = false;

		String contentBoxClientId = autoComplete.getClientId() + CONTENT_BOX_SUFFIX;
		encodeClientId(responseWriter, CONTENT_BOX, contentBoxClientId, first);
		first = false;

		String inputClientId = autoComplete.getClientId() + INPUT_SUFFIX;
		encodeClientId(responseWriter, INPUT_NODE, inputClientId, first);
		first = false;

		String autocompleteAttr = autoComplete.getAutocomplete();

		if (StringPool.ON.equals(autocompleteAttr)) {

			encodeBoolean(responseWriter, ALLOW_BROWSER_AUTOCOMPLETE, true, first);
			first = false;
		}

		// If <f:ajax event="valueChange" /> is specified in the view, then render a script that fires a change event
		// when an item is selected.
		Map<String, List<ClientBehavior>> clientBehaviorMap = autoComplete.getClientBehaviors();
		List<ClientBehavior> valueChangeClientBehaviors = clientBehaviorMap.get(VALUE_CHANGE);

		if ((valueChangeClientBehaviors != null) && !valueChangeClientBehaviors.isEmpty()) {

			encodeNonEscapedObject(responseWriter, StringPool.AFTER, VALUE_CHANGE_SCRIPT, first);
			first = false;
		}
	}

	protected void encodeHiddenAttributeSource(FacesContext facesContext, ResponseWriter responseWriter,
		AutoComplete autoComplete, boolean first) throws IOException {

		// If the developer has not specified a server-side filtering, then encode the array of autoComplete items.
		if (!isServerFilteringEnabled(autoComplete)) {

			//J-
			// source: ['item1', 'item2', 'item3', ... 'itemN']
			//J+
			encodeNonEscapedObject(responseWriter, SOURCE, StringPool.BLANK, first);

			List<String> results = new ArrayList<String>();
			results.addAll(autoComplete.getAllItems(facesContext));

			responseWriter.write(StringPool.OPEN_BRACKET);

			for (int i = 0; i < results.size(); i++) {

				if (i > 0) {
					responseWriter.write(StringPool.COMMA);
				}

				responseWriter.write(StringPool.APOSTROPHE);
				responseWriter.write(results.get(i));
				responseWriter.write(StringPool.APOSTROPHE);
			}

			responseWriter.write(StringPool.CLOSE_BRACKET);
		}
	}

	/**
	 * This method is being overridden in order to allow server-side filtering of autoComplete items when server-side
	 * filtering is enabled during an Ajax request and there is a query. Otherwise, this method simply calls
	 * super.encodeJavaScript() in order to render the component normally.
	 */
	@Override
	protected void encodeJavaScript(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		AutoComplete autoComplete = (AutoComplete) uiComponent;

		// If the developer has specified a server-side filtering during Ajax, then
		if (isServerFilteringEnabled(autoComplete) && isAjax(facesContext)) {

			// If the user has specified a query, then
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
			String clientId = uiComponent.getClientId(facesContext);
			String hiddenClientId = clientId + HIDDEN_SUFFIX;
			String query = requestParameterMap.get(hiddenClientId);

			if ((query != null) && (query.length() > 0)) {

				// Get the entire list of completion items.
				List<String> items = autoComplete.getAllItems(facesContext);

				// If the developer has specified a serverCustomFilter, then call their custom filtering method.
				MethodExpression serverCustomFilter = autoComplete.getServerCustomFilter();

				if (serverCustomFilter != null) {
					items = invokeServerCustomFilter(facesContext.getELContext(), serverCustomFilter, query, items);
				}

				// Otherwise, if the developer has specified a serverFilterType, then call the corresponding filtering
				// method.
				else {

					String serverFilterType = autoComplete.getServerFilterType();

					if (serverFilterType != null) {

						Locale locale = facesContext.getViewRoot().getLocale();
						AutoCompleteFilterFactory autoCompleteFilterFactory = new AutoCompleteFilterFactoryImpl();
						AutoCompleteFilter autoCompleteFilter = autoCompleteFilterFactory.getAutoCompleteFilter(
								serverFilterType);

						if (autoCompleteFilter != null) {
							boolean caseSensitive = serverFilterType.contains("Case");
							items = autoCompleteFilter.doFilter(query, items, caseSensitive, locale);
						}
						else {
							throw new IOException(serverFilterType + " is not a valid serverFilterType.");
						}
					}
				}

				// Build up a fragment of JavaScript that gets the client-side component.
				ClientComponent clientComponent = (ClientComponent) uiComponent;
				String clientVarName = getClientVarName(facesContext, clientComponent);
				String clientKey = clientComponent.getClientKey();

				if (clientKey == null) {
					clientKey = clientVarName;
				}

				//J-
				// Liferay.component('clientKey')
				//J+
				JavaScriptFragment liferayComponentJavaScriptFragment = new JavaScriptFragment("Liferay.component('" +
						clientKey + "')");

				// Build up a fragment of JavaScript that contains an array of the results.

				//J-
				// ['item1', 'item2', 'item3', ... 'itemN']
				//J+
				StringBuilder resultArrayStringBuilder = new StringBuilder();

				resultArrayStringBuilder.append(StringPool.OPEN_BRACKET);

				for (int i = 0; i < items.size(); i++) {

					if (i > 0) {
						resultArrayStringBuilder.append(StringPool.COMMA);
					}

					resultArrayStringBuilder.append(StringPool.APOSTROPHE);
					resultArrayStringBuilder.append(items.get(i));
					resultArrayStringBuilder.append(StringPool.APOSTROPHE);
				}

				resultArrayStringBuilder.append(StringPool.CLOSE_BRACKET);

				// Buffer all JavaScript so that it is rendered in the <eval> section of the partial response.
				BufferedScriptResponseWriter bufferedScriptResponseWriter = new BufferedScriptResponseWriter();

				//J-
				// LFAI.recieveAutoCompleteResults(Liferay.component('clientKey'), ['item1', 'item2', 'item3'],
				//		'hiddenClientId')
				//J+
				encodeFunctionCall(bufferedScriptResponseWriter, "LFAI.setAutoCompleteServerResults",
					liferayComponentJavaScriptFragment, resultArrayStringBuilder, hiddenClientId);

				String[] modules = getModules(facesContext, uiComponent);
				renderScript(facesContext, bufferedScriptResponseWriter.toString(), modules);
			}
			else {
				super.encodeJavaScript(facesContext, uiComponent);
			}
		}
		else {
			super.encodeJavaScript(facesContext, uiComponent);
		}
	}

	@Override
	protected void encodeQueryDelimiter(ResponseWriter responseWriter, AutoComplete autoComplete, String delimiter,
		boolean first) throws IOException {

		// If listItemRequired="true", the delimiter attribute must be ignored.
		if (!autoComplete.isListItemRequired()) {
			super.encodeQueryDelimiter(responseWriter, autoComplete, delimiter, first);
		}
	}

	@Override
	protected void encodeResultFilters(ResponseWriter responseWriter, AutoComplete autoComplete,
		String clientCustomFilter, boolean first) throws IOException {

		// If the developer has specified a server-side filtering, then the clientCustomFilter attribute must be
		// ignored.
		if (!isServerFilteringEnabled(autoComplete)) {
			encodeNonEscapedObject(responseWriter, RESULT_FILTERS, clientCustomFilter, first);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<String> invokeServerCustomFilter(ELContext elContext, MethodExpression methodExpression,
		String query, List<String> items) {
		Object[] params = new Object[] { query, items };

		return (List<String>) methodExpression.invoke(elContext, params);
	}

	protected boolean isServerFilteringEnabled(AutoComplete autoComplete) {

		MethodExpression serverCustomFilter = autoComplete.getServerCustomFilter();
		String serverFilterType = autoComplete.getServerFilterType();
		String clientCustomFitler = autoComplete.getClientCustomFilter();
		String clientFilterType = autoComplete.getClientFilterType();

		return (serverCustomFilter != null) || (serverFilterType != null) ||
			((clientCustomFitler == null) && (clientFilterType == null));
	}

	protected boolean isServerFilteringEnabled(UIComponent uiComponent) {

		AutoComplete autoComplete = (AutoComplete) uiComponent;

		return isServerFilteringEnabled(autoComplete);
	}

	protected boolean isAjaxFiltering(FacesContext facesContext, UIComponent uiComponent) {

		boolean querying = false;

		if (isServerFilteringEnabled(uiComponent) || isAjax(facesContext)) {

			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
			String hiddenClientId = uiComponent.getClientId(facesContext) + HIDDEN_SUFFIX;
			String query = requestParameterMap.get(hiddenClientId);
			querying = (query != null) && (query.length() != 0);
		}

		return querying;
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {

		List<String> modules = new ArrayList<String>(Arrays.asList(MODULES));
		AutoComplete autoComplete = (AutoComplete) uiComponent;
		String clientFilterType = autoComplete.getClientFilterType();
		String clientCustomFilter = autoComplete.getClientCustomFilter();

		// If the developer has specified client-side built-in filtering and does not have a custom filter, add the
		// "autocomplete-filters" module.
		if (!isServerFilteringEnabled(autoComplete) && (clientFilterType != null) && (clientFilterType.length() > 0) &&
				(clientCustomFilter == null)) {
			modules.add(AUTOCOMPLETE_FILTERS);
		}

		String highlighterType = autoComplete.getHighlighterType();

		if (highlighterType != null) {
			modules.add(AUTOCOMPLETE_HIGHLIGHTERS);
		}

		Map<String, List<ClientBehavior>> clientBehaviorMap = autoComplete.getClientBehaviors();
		List<ClientBehavior> valueChangeClientBehaviors = clientBehaviorMap.get(VALUE_CHANGE);

		if ((valueChangeClientBehaviors != null) && !valueChangeClientBehaviors.isEmpty()) {
			modules.add(NODE_EVENT_SIMULATE);
		}

		return modules.toArray(new String[] {});
	}
}
