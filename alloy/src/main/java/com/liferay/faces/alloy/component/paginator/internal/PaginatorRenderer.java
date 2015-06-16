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
package com.liferay.faces.alloy.component.paginator.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIParameter;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.commandlink.CommandLink;
import com.liferay.faces.alloy.component.outputtext.OutputText;
import com.liferay.faces.alloy.component.paginator.Paginator;
import com.liferay.faces.alloy.render.internal.AlloyRendererUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Neil Griffin
 */
//J-
@FacesRenderer(componentFamily = Paginator.COMPONENT_FAMILY, rendererType = Paginator.RENDERER_TYPE)
@ListenerFor(systemEventClass = PostAddToViewEvent.class, sourceClass = Paginator.class)
@ResourceDependencies(
	{
		@ResourceDependency(library = "javax.faces", name = "jsf.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css")
	}
)
//J+
public class PaginatorRenderer extends PaginatorRendererBase implements ComponentSystemEventListener {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PaginatorRenderer.class);

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		boolean activatedPaginationControl = false;

		// Decode the client behaviors.
		RendererUtil.decodeClientBehaviors(facesContext, uiComponent);

		// If there is a UIData component associated with the paginator, then
		Paginator paginator = (Paginator) uiComponent;
		UIData uiData = paginator.getUIData();

		if (uiData != null) {

			// Determine the pagination option selected by the user
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
			String paginatorActionParamName = uiComponent.getClientId().concat("_paginatorAction");
			String paginatorAction = requestParameterMap.get(paginatorActionParamName);

			if (paginatorAction != null) {

				// If the user clicked on the "First Page" link, then cause the associated UIData component to display
				// the first row in the data model.
				if ("firstPage".equals(paginatorAction)) {
					uiData.setFirst(0);
					activatedPaginationControl = true;
				}

				// Otherwise, if the user clicked on the "Previous Page" link, then cause the associated UIData
				// component to display the previous set of rows in the data model.
				else if ("previousPage".equals(paginatorAction)) {
					int rows = uiData.getRows();
					int first = uiData.getFirst();
					first -= rows;

					if (first >= 0) {
						uiData.setFirst(first);
					}

					activatedPaginationControl = true;
				}

				// Otherwise, if the user clicked on the "Next Page" link, then cause the associated UIData component to
				// display the next set of rows in the data model.
				else if ("nextPage".equals(paginatorAction)) {
					int rows = uiData.getRows();
					int rowCount = uiData.getRowCount();
					int first = uiData.getFirst();
					first += rows;

					if (first < rowCount) {
						uiData.setFirst(first);
					}

					activatedPaginationControl = true;
				}

				// Otherwise, if the user clicked on the "Last Page" link, then cause the associated UIData component to
				// display the last set of rows in the data model.
				else if ("lastPage".equals(paginatorAction)) {

					uiData.setFirst((uiData.getRowCount() / uiData.getRows()) * uiData.getRows());
					activatedPaginationControl = true;
				}

				// Otherwise, assume that the user clicked on a page number and cause the associated UIData component to
				// display the corresponding set of rows.
				else {

					try {

						int pageNumber = Integer.parseInt(paginatorAction);
						int pageIndex = pageNumber - 1;
						int first = pageIndex * uiData.getRows();

						if (first > uiData.getRowCount()) {
							first = 0;
						}

						uiData.setFirst(first);
						activatedPaginationControl = true;
					}
					catch (NumberFormatException e) {
						logger.error("Invalid parameter value paginatorAction=[{0}]", paginatorAction);
					}
				}
			}

			// If the user activated one of the pagination controls, then bypass the PROCESS_VALIDATIONS,
			// UPDATE_MODEL_VALUES, and INVOKE_APPLICATION phases of the JSF lifecycle and go directly to the
			// RENDER_RESPONSE phase.
			if (activatedPaginationControl) {
				facesContext.renderResponse();
			}
		}
		else {
			logger.error(
				"The alloy:paginator must have it's for attribute set or it must be inside of an f:facet of an alloy:dataTable or alloy:dataList");
		}
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the alloy:paginator.
		Paginator paginator = (Paginator) uiComponent;
		String clientId = paginator.getClientId(facesContext);
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);
		responseWriter.writeAttribute("id", clientId, null);
		RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent);

		// Determine the current pagination state of the associated UIData component.
		UIViewRoot viewRoot = facesContext.getViewRoot();
		Locale locale = viewRoot.getLocale();
		UIData uiData = paginator.getUIData();
		int first = uiData.getFirst();
		int rows = uiData.getRows();
		int curPage = (first / rows) + 1;
		int rowCount = uiData.getRowCount();
		int pageCount = (int) Math.ceil((double) rowCount / (double) rows);

		// If the summary is to be positioned above the pagination controls, then encode the summary.
		String summaryPosition = paginator.getSummaryPosition();

		if ("top".equals(summaryPosition)) {
			encodeSummary(responseWriter, paginator, summaryPosition, locale, first, curPage, rows, rowCount,
				pageCount);
		}

		// If any of the pagination controls are the be encoded, then
		boolean showPageNumberControls = paginator.isShowPageNumberControls();
		boolean showFirstPageControl = paginator.isShowFirstPageControl();
		boolean showLastPageControl = paginator.isShowLastPageControl();
		boolean showNextPageControl = paginator.isShowNextPageControl();
		boolean showPreviousPageControl = paginator.isShowPreviousPageControl();

		if (showPageNumberControls || showFirstPageControl || showLastPageControl || showNextPageControl ||
				showPreviousPageControl) {

			// Encode the starting <div> element that represents the Bootstrap pagination component.
			responseWriter.startElement("div", uiComponent);
			responseWriter.writeAttribute("class", "pagination", null);

			// Encode the starting <ul> element that represents the unordered list of pagination controls.
			responseWriter.startElement("ul", uiComponent);

			// Determine whether or not parameters need to be namespaced (as in a portlet environment).
			String namingContainerId = null;

			if (viewRoot instanceof NamingContainer) {
				namingContainerId = viewRoot.getContainerClientId(facesContext);
			}

			// If the summary is to be positioned on the left of the pagination controls, then encode the
			// summary.
			if ("left".equals(summaryPosition)) {
				encodeSummary(responseWriter, paginator, summaryPosition, locale, first, curPage, rows, rowCount,
					pageCount);
			}

			// Encode the "First Page" pagination control.
			if (showFirstPageControl) {
				encodeFirstPageListItem(facesContext, responseWriter, paginator, clientId, namingContainerId, first);
			}

			// Encode the "Previous Page" pagination control.
			if (showPreviousPageControl) {
				encodePrevPageListItem(facesContext, responseWriter, paginator, clientId, namingContainerId, first);
			}

			// If pagination controls for page numbers are to be encoded, then
			if (showPageNumberControls) {

				// Determine the first and last page numbers controls.
				int firstPage = paginator.getFirstPage();
				int lastPage = firstPage + paginator.getMaxPageNumberControls() - 1;

				while (curPage < firstPage) {
					firstPage -= 1;
					lastPage -= 1;
				}

				while (curPage > lastPage) {
					firstPage += 1;
					lastPage += 1;
				}

				// Remember the state of the first page number that is displayed.
				paginator.setFirstPage(firstPage);

				if (lastPage > pageCount) {
					lastPage = pageCount;
				}

				// Encode the pagination controls for the appropriate page numbers.
				for (int i = firstPage; i <= lastPage; i++) {
					encodePageNumberListItem(facesContext, responseWriter, paginator, clientId, namingContainerId, i,
						curPage, first, rows, rowCount);
				}
			}

			// Encode the "Next Page" pagination control.
			if (showNextPageControl) {
				encodeNextPageListItem(facesContext, responseWriter, paginator, uiData, clientId, namingContainerId);
			}

			// Encode the "Last Page" pagination control.
			if (showLastPageControl) {
				encodeLastPageListItem(facesContext, responseWriter, paginator, uiData, clientId, namingContainerId);
			}

			// If the summary is to be positioned on the right of the pagination controls, then encode the summary.
			if ("right".equals(summaryPosition)) {
				encodeSummary(responseWriter, paginator, summaryPosition, locale, first, curPage, rows, rowCount,
					pageCount);
			}

			// Encode the closing </div> element that represents the unordered list of pagination controls.
			responseWriter.endElement("ul");

			// Encode the closing </div> element that represents the Bootstrap pagination component.
			responseWriter.endElement("div");
		}

		// If the summary is to be positioned beneath the pagination controls, then encode the summary.
		if ("bottom".equals(summaryPosition)) {
			encodeSummary(responseWriter, paginator, summaryPosition, locale, first, curPage, rows, rowCount,
				pageCount);
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the closing </div> element that represents the alloy:paginator.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");
	}

	@Override
	public void processEvent(ComponentSystemEvent componentSystemEvent) throws AbortProcessingException {

		Paginator paginator = (Paginator) componentSystemEvent.getComponent();

		if (paginator.isAjax()) {
			AlloyRendererUtil.addDefaultAjaxBehavior(paginator, paginator.getExecute(), paginator.getProcess(), "@this",
				paginator.getRender(), paginator.getUpdate(), "@this @for");
		}
	}

	protected void encodeFirstPageListItem(FacesContext facesContext, ResponseWriter responseWriter,
		Paginator paginator, String clientId, String namingContainerId, int first) throws IOException {

		boolean enabled = (first > 0);
		String firstPageLabel = paginator.getFirstPageLabel();

		encodeUnorderedListItem(facesContext, responseWriter, paginator, clientId, namingContainerId, "firstPage",
			firstPageLabel, enabled, false);
	}

	protected void encodeLastPageListItem(FacesContext facesContext, ResponseWriter responseWriter, Paginator paginator,
		UIData uiData, String clientId, String namingContainerId) throws IOException {

		boolean enabled = ((uiData.getFirst() + uiData.getRows()) < uiData.getRowCount());
		String lastPageLabel = paginator.getLastPageLabel();
		encodeUnorderedListItem(facesContext, responseWriter, paginator, clientId, namingContainerId, "lastPage",
			lastPageLabel, enabled, false);
	}

	protected void encodeNextPageListItem(FacesContext facesContext, ResponseWriter responseWriter, Paginator paginator,
		UIData uiData, String clientId, String namingContainerId) throws IOException {

		boolean enabled = ((uiData.getFirst() + uiData.getRows()) < uiData.getRowCount());
		String nextPageLabel = paginator.getNextPageLabel();
		encodeUnorderedListItem(facesContext, responseWriter, paginator, clientId, namingContainerId, "nextPage",
			nextPageLabel, enabled, false);
	}

	protected void encodePageNumberListItem(FacesContext facesContext, ResponseWriter responseWriter,
		Paginator paginator, String clientId, String namingContainerId, int pageNumber, int curPageNumber, int first,
		int rows, int rowCount) throws IOException {

		boolean current = (pageNumber == curPageNumber);
		boolean enabled = (!current || ((first + rows) < rowCount));
		String paginatorAction = Integer.toString(pageNumber);

		// If the page number is a single digit, then for formatting purposes, add a blank space before and after the
		// digit. This will basically provide a fixed width so that button links don't grow/shrink due to the number of
		// digits.
		String pageNumberLabel = paginatorAction;

		if (pageNumber < 10) {
			pageNumberLabel = "&nbsp;".concat(pageNumberLabel).concat("&nbsp;");
		}

		encodeUnorderedListItem(facesContext, responseWriter, paginator, clientId, namingContainerId, paginatorAction,
			pageNumberLabel, enabled, current);
	}

	protected void encodePrevPageListItem(FacesContext facesContext, ResponseWriter responseWriter, Paginator paginator,
		String clientId, String namingContainerId, int first) throws IOException {

		boolean enabled = (first > 0);
		String previousPageLabel = paginator.getPreviousPageLabel();
		encodeUnorderedListItem(facesContext, responseWriter, paginator, clientId, namingContainerId, "previousPage",
			previousPageLabel, enabled, false);
	}

	protected void encodeSummary(ResponseWriter responseWriter, Paginator paginator, String summaryPostion,
		Locale locale, int first, int curPageNumber, int rows, int rowCount, int pageCount) throws IOException {

		// If the paginator is to appear above or below the pagination controls, then
		boolean encodePaginationDiv = ("top".equals(summaryPostion) || "bottom".equals(summaryPostion));

		if (encodePaginationDiv) {

			// Encode the starting <div> element that represents the Bootstrap pagination component.
			responseWriter.startElement("div", paginator);
			responseWriter.writeAttribute("class", "pagination", null);
			responseWriter.startElement("ul", paginator);
		}

		// Determine the first page number and the last page number.
		int paginatorFirst = first + 1;
		int paginatorLast = Math.min(first + rows, rowCount);

		// Get an internationalized message that contains the pagination summary.
		MessageContextFactory messageContextFactory = (MessageContextFactory) FactoryExtensionFinder.getFactory(
				MessageContextFactory.class);
		MessageContext messageContext = messageContextFactory.getMessageContext();
		String message = messageContext.getMessage(locale, "results-x-x-of-x-page-x-of-x", paginatorFirst,
				paginatorLast, rowCount, curPageNumber, pageCount);

		// Encode a list item inside the Bootstrap pagination component that contains the pagination summary message.
		responseWriter.startElement("li", paginator);
		responseWriter.startElement("span", paginator);
		responseWriter.writeAttribute("class", "disabled", null);
		responseWriter.writeText(message, paginator, null);
		responseWriter.endElement("span");
		responseWriter.endElement("li");

		// If the paginator is to appear above or below the pagination controls, then
		if (encodePaginationDiv) {

			// Encode the closing </div> element that represents the Bootstrap pagination component.
			responseWriter.endElement("ul");
			responseWriter.endElement("div");
		}
	}

	protected void encodeUnorderedListItem(FacesContext facesContext, ResponseWriter responseWriter,
		Paginator paginator, String clientId, String namingContainerId, String paginatorAction, String text,
		boolean enabled, boolean current) throws IOException {

		// Encode the starting <li> element that contains the Bootstrap pagination control.
		responseWriter.startElement("li", paginator);

		if (current) {
			responseWriter.writeAttribute("class", "active", null);
		}
		else {

			if (!enabled) {
				responseWriter.writeAttribute("class", "disabled", null);
			}
		}

		// If the pagination control is enabled, then
		if (enabled) {

			// If the alloy:paginator has a nested f:ajax tag, then encode a hyperlink that contains the client
			// behavior script in the onclick attribute.
			String clientBehaviorScript = getClientBehaviorScript(facesContext, paginator, clientId, namingContainerId,
					paginatorAction);

			if (clientBehaviorScript != null) {
				responseWriter.startElement("a", paginator);
				responseWriter.writeAttribute("href", "javascript:void(0);", null);
				responseWriter.writeAttribute("onclick", clientBehaviorScript, null);
				responseWriter.write(text);
				responseWriter.endElement("a");
			}

			// Otherwise, encode an alloy:commandLink that can submit the form via full-page postback.
			else {

				Application application = facesContext.getApplication();
				CommandLink commandLink = (CommandLink) application.createComponent(facesContext,
						CommandLink.COMPONENT_TYPE, CommandLink.RENDERER_TYPE);
				commandLink.setAjax(paginator.isAjax());

				OutputText outputText = (OutputText) application.createComponent(facesContext,
						OutputText.COMPONENT_TYPE, OutputText.RENDERER_TYPE);

				List<UIComponent> paginatorChildren = paginator.getChildren();
				paginatorChildren.add(commandLink);

				UIParameter uiParameter = new UIParameter();

				String paginatorActionParamName = clientId.concat("_paginatorAction");
				uiParameter.setName(paginatorActionParamName);
				uiParameter.setValue(paginatorAction);

				List<UIComponent> commandLinkChildren = commandLink.getChildren();
				commandLinkChildren.add(uiParameter);
				commandLinkChildren.add(outputText);
				outputText.setEscape(false);
				outputText.setValue(text);
				commandLink.encodeAll(facesContext);
				commandLinkChildren.remove(outputText);
				commandLinkChildren.remove(uiParameter);
				paginatorChildren.remove(commandLink);
			}
		}

		// Otherwise, since the pagination control is not enabled, simply encode a span containing the label of the
		// control.
		else {
			responseWriter.startElement("span", paginator);
			responseWriter.write(text);
			responseWriter.endElement("span");
		}

		// Encode the closing <li> element that contains the Bootstrap pagination link.
		responseWriter.endElement("li");
	}

	protected String getClientBehaviorScript(FacesContext facesContext, Paginator paginator, String clientId,
		String namingContainerId, String paginatorAction) {

		String clientBehaviorScript = null;
		Map<String, List<ClientBehavior>> clientBehaviorMap = paginator.getClientBehaviors();
		String defaultEventName = paginator.getDefaultEventName();
		String paginatorActionParamName = clientId.concat("_paginatorAction");
		List<ClientBehavior> clientBehaviorsForEvent = clientBehaviorMap.get(defaultEventName);

		if (clientBehaviorsForEvent != null) {

			for (ClientBehavior clientBehavior : clientBehaviorsForEvent) {

				List<ClientBehaviorContext.Parameter> parameters = new ArrayList<ClientBehaviorContext.Parameter>();
				parameters.add(new ClientBehaviorContext.Parameter(paginatorActionParamName, paginatorAction));

				if (namingContainerId != null) {
					parameters.add(new ClientBehaviorContext.Parameter("'com.sun.faces.namingContainerId'",
							namingContainerId));
				}

				ClientBehaviorContext clientBehaviorContext = ClientBehaviorContext.createClientBehaviorContext(
						facesContext, paginator, defaultEventName, clientId, parameters);
				clientBehaviorScript = clientBehavior.getScript(clientBehaviorContext);
			}
		}

		return clientBehaviorScript;
	}
}
