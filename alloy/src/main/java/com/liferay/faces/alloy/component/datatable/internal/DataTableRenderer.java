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
package com.liferay.faces.alloy.component.datatable.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.html.HtmlColumn;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.column.Column;
import com.liferay.faces.alloy.component.commandlink.CommandLink;
import com.liferay.faces.alloy.component.datatable.DataTable;
import com.liferay.faces.alloy.component.datatable.RowDeselectEvent;
import com.liferay.faces.alloy.component.datatable.RowDeselectRangeEvent;
import com.liferay.faces.alloy.component.datatable.RowSelectEvent;
import com.liferay.faces.alloy.component.datatable.RowSelectRangeEvent;
import com.liferay.faces.alloy.component.outputtext.OutputText;
import com.liferay.faces.alloy.render.internal.JavaScriptFragment;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.SortCriterion;
import com.liferay.faces.util.model.Sortable;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Neil Griffin
 */
//J-
@FacesRenderer(componentFamily = DataTable.COMPONENT_FAMILY, rendererType = DataTable.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
	}
)
//J+
public class DataTableRenderer extends DataTableRendererBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DataTableRenderer.class);

	// Private Data Members
	private static final String[] MODULES = { "aui-datatable", "node-event-simulate" };

	@Override
	public void decodeClientState(FacesContext facesContext, UIComponent uiComponent) {

		// Apply the client-side state of the selected index.
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		DataTable dataTable = (DataTable) uiComponent;
		String dataTableClientId = dataTable.getClientId(facesContext);
		decodeSortCriteria(facesContext, requestParameterMap, dataTable, dataTableClientId);
		decodeRowSelection(requestParameterMap, dataTable, dataTableClientId);
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		DataTable dataTable = (DataTable) uiComponent;
		DataTableInfo dataTableInfo = new DataTableInfo(dataTable);
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		int totalRenderedColumns = dataTableInfo.getTotalRenderedColumns();

		if (totalRenderedColumns == 0) {
			responseWriter.startElement("tbody", dataTable);
			responseWriter.endElement("tbody");
		}
		else {

			int rows = dataTable.getRows();
			int rowIndex = dataTable.getFirst() - 1;
			int totalRowsEncoded = 0;

			int[] bodyRows = dataTable.toIntArray(dataTable.getBodyrows());

			boolean wroteTBody = false;

			if (bodyRows == null) {
				responseWriter.startElement("tbody", dataTable);
				wroteTBody = true;
			}

			if (rows >= 0) {

				ItemCycler rowClasses = new ItemCycler(dataTable.getRowClasses());
				ItemCycler columnClasses = new ItemCycler(dataTable.getColumnClasses());

				while ((totalRowsEncoded < rows) || (rows == 0)) {

					rowIndex++;
					dataTable.setRowIndex(rowIndex);
					columnClasses.reset();

					// If there is data in the model for the current row index, then encode the row.
					if (dataTable.isRowAvailable()) {

						if (bodyRows != null) {

							for (int bodyRow : bodyRows) {

								if (bodyRow == rowIndex) {

									if (wroteTBody) {
										responseWriter.endElement("tbody");
									}

									responseWriter.startElement("tbody", dataTable);
									wroteTBody = true;

									break;
								}
							}
						}

						encodeRow(facesContext, responseWriter, dataTable, rowIndex, rowClasses, columnClasses);

						totalRowsEncoded++;
					}

					// Otherwise, encoding of rows is complete since the last row has been encoded.
					else {

						break;
					}
				}
			}

			if (totalRowsEncoded == 0) {

				String selectionMode = dataTable.getSelectionMode();

				if ("checkbox".equals(selectionMode)) {
					totalRenderedColumns += 1;
				}

				encodeEmptyTableRow(responseWriter, dataTable, totalRenderedColumns);
			}

			responseWriter.endElement("tbody");
		}
	}

	@Override
	public void encodeClientState(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {

		// Set the rowIndex to -1 so that the UIData.getClientId(FacesContext) method will return a clientId that does
		// not append the rowIndex. This is necessary to ensure that state saving/restoring will take place correctly,
		// since the state is referenced by the clientId.
		DataTable dataTable = (DataTable) uiComponent;
		dataTable.setRowIndex(-1);

		// Encode the hidden field that contains the client-side state of the selected index.
		String hiddenFieldName = dataTable.getClientId(facesContext).concat("_selectedRowIndexes");
		responseWriter.startElement("input", dataTable);
		responseWriter.writeAttribute("id", hiddenFieldName, null);
		responseWriter.writeAttribute("name", hiddenFieldName, null);
		responseWriter.writeAttribute("type", "hidden", null);
		responseWriter.writeAttribute("value", dataTable.getSelectedRowIndexes(), null);
		responseWriter.endElement("input");
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		DataTable dataTable = (DataTable) uiComponent;
		String selectionMode = dataTable.getSelectionMode();

		if ("checkbox".equals(selectionMode) || "radio".equals(selectionMode)) {

			String dataTableClientId = dataTable.getClientId(facesContext);
			String escapedDataTableClientId = escapeClientId(dataTableClientId);
			String hiddenFieldClientId = dataTableClientId.concat("_selectedRowIndexes");
			String escapedHiddenFieldClientId = escapeClientId(hiddenFieldClientId);
			ResponseWriter responseWriter = facesContext.getResponseWriter();

			// rowSelect
			JavaScriptFragment rowSelectClientBehaviorScript = getRowEventClientBehaviorScript(facesContext, dataTable,
					dataTableClientId, RowSelectEvent.ROW_SELECT, "rowIndex");

			// rowSelectRange
			JavaScriptFragment rowSelectRangeClientBehaviorScript = getRowEventClientBehaviorScript(facesContext,
					dataTable, dataTableClientId, RowSelectRangeEvent.ROW_SELECT_RANGE, "rowIndexRange");

			// rowDeselect
			JavaScriptFragment rowDeselectClientBehaviorScript = getRowEventClientBehaviorScript(facesContext,
					dataTable, dataTableClientId, RowDeselectEvent.ROW_DESELECT, "rowIndex");

			// rowDeSelectRange
			JavaScriptFragment rowDeselectRangeClientBehaviorScript = getRowEventClientBehaviorScript(facesContext,
					dataTable, dataTableClientId, RowDeselectRangeEvent.ROW_DESELECT_RANGE, "rowIndexRange");

			if ("checkbox".equals(selectionMode)) {

				// Register the onclick event callback for the "Select All" checkbox.
				String selectAllCheckboxClientId = dataTableClientId.concat("_selectAll");
				String escapedSelectAllCheckboxClientId = escapeClientId(selectAllCheckboxClientId);
				encodeFunctionCall(responseWriter, "LFAI.initDataTableSelectAllCheckbox", 'A', escapedDataTableClientId,
					escapedSelectAllCheckboxClientId, rowSelectRangeClientBehaviorScript,
					rowDeselectRangeClientBehaviorScript);

				// Register the onclick event callback for each row-level checkbox.
				encodeFunctionCall(responseWriter, "LFAI.initDataTableCheckboxSelection", 'A', escapedDataTableClientId,
					escapedHiddenFieldClientId, rowSelectClientBehaviorScript, rowDeselectClientBehaviorScript);
			}
			else if ("radio".equals(selectionMode)) {

				// Register the onclick event callback for each row-level radio button.
				encodeFunctionCall(responseWriter, "LFAI.initDataTableRadioSelection", 'A', escapedDataTableClientId,
					escapedHiddenFieldClientId, rowSelectClientBehaviorScript, rowDeselectClientBehaviorScript);
			}
		}
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// If the rows attribute has changed since the last render, then reset the first row that is to be displayed
		// back to zero. This takes care of any page number rendering difficulties.
		DataTable dataTable = (DataTable) uiComponent;
		Map<String, Object> dataTableAttributes = dataTable.getAttributes();
		Integer oldRows = (Integer) dataTableAttributes.remove("oldRows");

		if ((oldRows != null) && (oldRows != dataTable.getRows())) {
			dataTable.setFirst(0);
		}

		// Encode the starting <table> element that represents the alloy:table.
		DataTableInfo dataTableInfo = new DataTableInfo(dataTable);
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("table", dataTable);
		responseWriter.writeAttribute("id", dataTable.getClientId(facesContext), "id");
		RendererUtil.encodeStyleable(responseWriter, dataTable);

		// If present, encode the child <f:facet name="caption" ... />
		encodeCaptionFacet(facesContext, responseWriter, dataTable);

		// If present, encode the child <f:facet name="colGroups" ... />
		encodeColGroupsFacet(facesContext, dataTable);

		// Encode the table <thead> ... </thead> section.
		encodeHeader(facesContext, responseWriter, dataTable, dataTableInfo);

		// Encode the table <tfoot> ... </tfoot> section.
		encodeFooter(facesContext, responseWriter, dataTable, dataTableInfo);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the closing <table> element that represents the alloy:table.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("table");
	}

	protected void decodeRowSelection(Map<String, String> requestParameterMap, DataTable dataTable,
		String dataTableClientId) {

		String hiddenFieldName = dataTableClientId + "_selectedRowIndexes";
		String selectedRowIndexes = requestParameterMap.get(hiddenFieldName);

		if (selectedRowIndexes != null) {
			dataTable.setSelectedRowIndexes(selectedRowIndexes);
		}
	}

	protected void decodeSortCriteria(FacesContext facesContext, Map<String, String> requestParameterMap,
		DataTable dataTable, String dataTableClientId) {

		Object dataTableValue = dataTable.getValue();

		if ((dataTableValue != null) && (dataTableValue instanceof Sortable)) {

			String sortColumnClientIdParamName = dataTableClientId.concat("_sortColumnClientId");
			String sortColumnClientId = requestParameterMap.get(sortColumnClientIdParamName);

			if (sortColumnClientId != null) {

				List<Column> alloySortColumns = new ArrayList<Column>();
				String eventMetaKeyParamName = dataTableClientId.concat("_eventMetaKey");
				boolean eventMetaKey = BooleanHelper.toBoolean(requestParameterMap.get(eventMetaKeyParamName));
				boolean multiColumnSort = dataTable.isMultiColumnSort();

				List<UIComponent> children = dataTable.getChildren();

				for (UIComponent child : children) {

					if (child instanceof Column) {

						Column alloyColumn = (Column) child;
						String alloyColumndId = alloyColumn.getClientId(facesContext);

						String alloyColumnSortOrder = alloyColumn.getSortOrder();

						Map<String, Object> alloyColumnAttributes = alloyColumn.getAttributes();

						if (alloyColumndId.equals(sortColumnClientId)) {

							// Toggle the value from ascending->descending or from descending->ascending.
							SortCriterion.Order sortCriterionOrder;

							if ("ASCENDING".equals(alloyColumnSortOrder)) {
								sortCriterionOrder = SortCriterion.Order.DESCENDING;
							}
							else {
								sortCriterionOrder = SortCriterion.Order.ASCENDING;
							}

							// Set the state of the column so that the sort indicator will appear correctly.
							alloyColumn.setSortOrder(sortCriterionOrder.toString());
							alloyColumnAttributes.put("sortTime", System.currentTimeMillis());

							// Add the sort criterion to the list of sort criteria.
							alloySortColumns.add(alloyColumn);
						}
						else {

							if (multiColumnSort && eventMetaKey) {

								if (alloyColumnSortOrder != null) {
									alloySortColumns.add(alloyColumn);
								}
							}
							else {
								alloyColumn.setSortOrder(null);
								alloyColumnAttributes.remove("sortTime");
							}
						}
					}
				}

				Collections.sort(alloySortColumns, new ColumnSortTimeComparator());

				List<SortCriterion> sortCriteria = new ArrayList<SortCriterion>();

				for (Column alloyColumn : alloySortColumns) {

					String alloyColumnFieldName = getAlloyColumnFieldName(alloyColumn);
					String alloyColumnSortOrder = alloyColumn.getSortOrder();
					SortCriterion.Order sortCriterionOrder;

					if ("ASCENDING".equals(alloyColumnSortOrder)) {
						sortCriterionOrder = SortCriterion.Order.ASCENDING;
					}
					else {
						sortCriterionOrder = SortCriterion.Order.DESCENDING;
					}

					SortCriterion sortCriterion = new SortCriterion(alloyColumnFieldName, sortCriterionOrder);
					sortCriteria.add(sortCriterion);
				}

				if (logger.isDebugEnabled()) {

					for (SortCriterion sortCriterion : sortCriteria) {
						logger.debug("sortCriterion columnId=[{0}], order=[{1}]", sortCriterion.getColumnId(),
							sortCriterion.getOrder());
					}
				}

				Sortable sortable = (Sortable) dataTableValue;
				sortable.setSortCriteria(sortCriteria);
			}
		}
	}

	protected void encodeCaptionFacet(FacesContext facesContext, ResponseWriter responseWriter, DataTable dataTable)
		throws IOException {

		UIComponent captionFacet = dataTable.getFacet("caption");

		if (captionFacet != null) {

			responseWriter.startElement("caption", null);

			String captionClass = dataTable.getCaptionClass();

			if (captionClass != null) {
				responseWriter.writeAttribute("class", captionClass, "captionClass");
			}

			String captionStyle = dataTable.getCaptionStyle();

			if (captionStyle != null) {
				responseWriter.writeAttribute("style", captionStyle, "captionStyle");
			}

			encodeRecurse(facesContext, captionFacet);

			responseWriter.endElement("caption");
		}
	}

	protected void encodeColGroupsFacet(FacesContext facesContext, DataTable dataTable) throws IOException {

		UIComponent colGroupsFacet = dataTable.getFacet("colGroups");

		if (colGroupsFacet != null) {
			encodeRecurse(facesContext, colGroupsFacet);
		}
	}

	protected void encodeEmptyTableRow(ResponseWriter responseWriter, DataTable dataTable, int totalColumns)
		throws IOException {

		responseWriter.startElement("tr", dataTable);

		for (int i = 0; i < totalColumns; i++) {
			responseWriter.startElement("td", dataTable);
			responseWriter.endElement("td");
		}

		responseWriter.endElement("tr");
	}

	protected void encodeFooter(FacesContext facesContext, ResponseWriter responseWriter, DataTable dataTable,
		DataTableInfo dataTableInfo) throws IOException {

		UIComponent footerFacet = dataTable.getFacet("footer");

		if ((footerFacet != null) || dataTableInfo.isFooterFacetPresentInColumn()) {
			responseWriter.startElement("tfoot", null);
		}

		String footerClass = dataTable.getFooterClass();

		if (dataTableInfo.isFooterFacetPresentInColumn()) {

			responseWriter.startElement("tr", null);

			List<UIComponent> children = dataTable.getChildren();

			for (UIComponent child : children) {

				if (child instanceof HtmlColumn) {

					HtmlColumn htmlColumn = (HtmlColumn) child;

					if (htmlColumn.isRendered()) {

						responseWriter.startElement("td", null);

						String columnFooterClass = htmlColumn.getFooterClass();

						if (columnFooterClass != null) {
							responseWriter.writeAttribute("class", columnFooterClass, "columnFooterClass");
						}
						else if (footerClass != null) {
							responseWriter.writeAttribute("class", footerClass, "footerClass");
						}

						UIComponent columnFooterFacet = htmlColumn.getFacet("footer");

						if (columnFooterFacet != null) {
							encodeRecurse(facesContext, columnFooterFacet);
						}

						responseWriter.endElement("td");
					}
				}
			}

			responseWriter.endElement("tr");
		}

		int totalRenderedColumns = dataTableInfo.getTotalRenderedColumns();
		int colspan = totalRenderedColumns;
		String selectionMode = dataTable.getSelectionMode();

		if ("checkbox".equals(selectionMode) || "radio".equals(selectionMode)) {
			colspan++;
		}

		if (footerFacet != null) {

			responseWriter.startElement("tr", null);
			responseWriter.startElement("td", null);

			if (footerClass == null) {
				responseWriter.writeAttribute("class", "facet", "footerClass");
			}
			else {
				responseWriter.writeAttribute("class", footerClass.concat(" facet"), "footerClass");
			}

			if (totalRenderedColumns > 1) {
				responseWriter.writeAttribute("colspan", colspan, null);
			}

			encodeRecurse(facesContext, footerFacet);
			responseWriter.endElement("td");
			responseWriter.endElement("tr");
		}

		if ((footerFacet != null) || dataTableInfo.isFooterFacetPresentInColumn()) {
			responseWriter.endElement("tfoot");
		}
	}

	protected void encodeHeader(FacesContext facesContext, ResponseWriter responseWriter, DataTable dataTable,
		DataTableInfo dataTableInfo) throws IOException {

		UIComponent headerFacet = dataTable.getFacet("header");

		if ((headerFacet != null) || dataTableInfo.isHeaderFacetOrTextPresentInColumn()) {
			responseWriter.startElement("thead", null);
			responseWriter.writeAttribute("class", "table-columns", null);
		}

		int totalRenderedColumns = dataTableInfo.getTotalRenderedColumns();
		int colspan = totalRenderedColumns;
		String selectionMode = dataTable.getSelectionMode();

		if ("checkbox".equals(selectionMode) || "radio".equals(selectionMode)) {
			colspan++;
		}

		String headerClass = dataTable.getHeaderClass();

		if (headerFacet != null) {

			responseWriter.startElement("tr", null);
			responseWriter.startElement("th", null);

			if (headerClass == null) {
				responseWriter.writeAttribute("class", "facet", "headerClass");
			}
			else {
				responseWriter.writeAttribute("class", headerClass.concat(" facet"), "headerClass");
			}

			if (totalRenderedColumns > 1) {
				responseWriter.writeAttribute("colspan", colspan, null);
			}

			responseWriter.writeAttribute("scope", "colgroup", null);
			encodeRecurse(facesContext, headerFacet);
			responseWriter.endElement("th");
			responseWriter.endElement("tr");
		}

		if (dataTableInfo.isHeaderFacetOrTextPresentInColumn()) {

			// Determine whether or not parameters need to be namespaced (as in a portlet environment).
			String namingContainerId = null;

			UIViewRoot viewRoot = facesContext.getViewRoot();

			if (viewRoot instanceof NamingContainer) {
				namingContainerId = viewRoot.getContainerClientId(facesContext);
			}

			responseWriter.startElement("tr", null);

			List<UIComponent> children = dataTable.getChildren();

			if ("checkbox".equals(selectionMode) || "radio".equals(selectionMode)) {
				responseWriter.startElement("th", null);

				if ("checkbox".equals(selectionMode)) {
					responseWriter.startElement("input", null);

					String checkboxClientId = dataTable.getClientId(facesContext).concat("_selectAll");
					responseWriter.writeAttribute("id", checkboxClientId, null);
					responseWriter.writeAttribute("type", "checkbox", null);
					responseWriter.endElement("input");
				}

				responseWriter.endElement("th");
			}

			for (UIComponent child : children) {

				if (child instanceof UIColumn) {

					UIColumn uiColumn = (UIColumn) child;

					if (uiColumn.isRendered()) {
						responseWriter.startElement("th", null);

						if (child instanceof HtmlColumn) {

							HtmlColumn htmlColumn = (HtmlColumn) child;
							String columnHeaderClass = htmlColumn.getHeaderClass();

							String sortClass = null;
							Column alloyColumn = null;

							if (child instanceof Column) {

								alloyColumn = (Column) htmlColumn;

								String sortOrder = alloyColumn.getSortOrder();

								if ("ASCENDING".equals(sortOrder)) {
									sortClass = " table-sortable-column table-sorted";
								}
								else if ("DESCENDING".equals(sortOrder)) {
									sortClass = " table-sortable-column table-sorted table-sorted-desc";
								}
							}

							if (columnHeaderClass != null) {

								if (sortClass != null) {
									columnHeaderClass = columnHeaderClass.concat(sortClass);
								}

								responseWriter.writeAttribute("class", columnHeaderClass, "columnHeaderClass");
							}
							else if (headerClass != null) {

								if (sortClass != null) {
									headerClass = headerClass.concat(sortClass);
								}

								responseWriter.writeAttribute("class", headerClass, "headerClass");
							}
							else if (sortClass != null) {
								responseWriter.writeAttribute("class", sortClass, null);
							}

							responseWriter.writeAttribute("scope", "col", null);

							if (alloyColumn != null) {

								String headerText = alloyColumn.getHeaderText();

								if (headerText != null) {
									encodeHeaderText(facesContext, responseWriter, dataTable, alloyColumn, headerText,
										namingContainerId);
								}
							}

							UIComponent columnHeaderFacet = htmlColumn.getFacet("header");

							if (columnHeaderFacet != null) {
								encodeRecurse(facesContext, columnHeaderFacet);
							}
						}

						responseWriter.endElement("th");
					}
				}
			}

			responseWriter.endElement("tr");
		}

		if ((headerFacet != null) || dataTableInfo.isHeaderFacetOrTextPresentInColumn()) {
			responseWriter.endElement("thead");
		}
	}

	protected void encodeHeaderText(FacesContext facesContext, ResponseWriter responseWriter, DataTable dataTable,
		Column column, String headerText, String namingContainerId) throws IOException {

		ValueExpression sortByValueExpression = column.getValueExpression("sortBy");

		if (sortByValueExpression == null) {
			responseWriter.writeText(headerText, column, null);
		}
		else {

			responseWriter.startElement("div", dataTable);
			responseWriter.writeAttribute("class", "table-sort-liner", null);

			// If the alloy:column has a nested f:ajax tag, then encode a hyperlink that contains the client
			// behavior script in the onclick attribute.
			String dataTableClientId = dataTable.getClientId(facesContext);
			String clientBehaviorScript = getColumnClientBehaviorScript(facesContext, dataTable, column,
					dataTableClientId, namingContainerId);

			if (clientBehaviorScript != null) {

				// Write the client behavior script in the onclick attribute on the <div> element because writing it on
				// the <a> element will have the side-effect of a new browser tab opening for each sort column that is
				// selected with Left Click + Meta.
				responseWriter.writeAttribute("onclick", clientBehaviorScript, null);
				responseWriter.startElement("a", null);
				responseWriter.writeText(headerText, null);
				responseWriter.startElement("span", dataTable);
				responseWriter.writeAttribute("class", "table-sort-indicator", null);
				responseWriter.endElement("span");
				responseWriter.endElement("a");
			}

			// Otherwise, encode an alloy:commandLink that can submit the form via full-page postback.
			else {
				Application application = facesContext.getApplication();
				CommandLink commandLink = (CommandLink) application.createComponent(facesContext,
						CommandLink.COMPONENT_TYPE, CommandLink.RENDERER_TYPE);
				commandLink.setAjax(column.isAjax());

				OutputText outputText1 = (OutputText) application.createComponent(facesContext,
						OutputText.COMPONENT_TYPE, OutputText.RENDERER_TYPE);
				outputText1.setValue(headerText);

				OutputText outputText2 = (OutputText) application.createComponent(facesContext,
						OutputText.COMPONENT_TYPE, OutputText.RENDERER_TYPE);
				outputText2.setStyleClass("table-sort-indicator");

				List<UIComponent> paginatorChildren = column.getChildren();
				paginatorChildren.add(commandLink);

				UIParameter uiParameter = new UIParameter();
				String sortColumnClientIdParamName = dataTableClientId.concat("_sortColumnClientId");
				uiParameter.setName(sortColumnClientIdParamName);
				uiParameter.setValue(column.getClientId(facesContext));

				List<UIComponent> commandLinkChildren = commandLink.getChildren();
				commandLinkChildren.add(uiParameter);
				commandLinkChildren.add(outputText1);
				commandLinkChildren.add(outputText2);
				outputText2.setEscape(false);
				commandLink.encodeAll(facesContext);
				commandLinkChildren.remove(outputText2);
				commandLinkChildren.remove(outputText1);
				commandLinkChildren.remove(uiParameter);
				paginatorChildren.remove(commandLink);
			}

			responseWriter.endElement("div");
		}
	}

	protected void encodeRecurse(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		if (uiComponent.isRendered()) {

			uiComponent.encodeBegin(facesContext);

			if (uiComponent.getRendersChildren()) {
				uiComponent.encodeChildren(facesContext);
			}
			else {
				List<UIComponent> children = uiComponent.getChildren();

				for (UIComponent child : children) {
					encodeRecurse(facesContext, child);
				}
			}

			uiComponent.encodeEnd(facesContext);
		}
	}

	protected void encodeRow(FacesContext facesContext, ResponseWriter responseWriter, DataTable dataTable,
		int rowIndex, ItemCycler rowClasses, ItemCycler columnClasses) throws IOException {

		responseWriter.startElement("tr", dataTable);

		String rowClass = rowClasses.getNextItem();

		if (rowClass != null) {
			responseWriter.writeAttribute("class", rowClass, "rowClasses");
		}

		String selectionMode = dataTable.getSelectionMode();

		if ("checkbox".equals(selectionMode) || "radio".equals(selectionMode)) {

			Set<String> selectedRowIndexSet = new HashSet<String>();
			String selectedRowIndexes = dataTable.getSelectedRowIndexes();

			if (selectedRowIndexes != null) {
				String[] selectedRowIndexArray = selectedRowIndexes.split(",");
				selectedRowIndexSet = new HashSet<String>(Arrays.asList(selectedRowIndexArray));
			}

			String rowIndexAsString = Integer.toString(rowIndex);

			if (selectedRowIndexSet.contains(rowIndexAsString)) {
				responseWriter.writeAttribute("class", "info", null);
			}

			responseWriter.startElement("td", null);
			responseWriter.startElement("input", null);

			String checkboxClientId = dataTable.getClientId(facesContext);
			responseWriter.writeAttribute("id", checkboxClientId, null);
			responseWriter.writeAttribute("type", selectionMode, null);

			if (selectedRowIndexSet.contains(rowIndexAsString)) {
				responseWriter.writeAttribute("checked", "checked", null);
			}

			responseWriter.endElement("input");
			responseWriter.endElement("td");
		}

		List<UIComponent> children = dataTable.getChildren();

		for (UIComponent child : children) {

			if (child instanceof HtmlColumn) {

				HtmlColumn htmlColumn = (HtmlColumn) child;

				if (htmlColumn.isRendered()) {

					if (htmlColumn.isRowHeader()) {
						responseWriter.startElement("th", htmlColumn);
						responseWriter.writeAttribute("scope", "row", null);
					}
					else {
						responseWriter.startElement("td", htmlColumn);
					}

					String columnClass = columnClasses.getNextItem();

					if (columnClass != null) {
						responseWriter.writeAttribute("class", columnClass, "columnClasses");
					}

					List<UIComponent> htmlColumnChildren = htmlColumn.getChildren();

					for (UIComponent htmlColumnChild : htmlColumnChildren) {
						encodeRecurse(facesContext, htmlColumnChild);
					}

					if (htmlColumn.isRowHeader()) {
						responseWriter.endElement("th");
					}
					else {
						responseWriter.endElement("td");
					}
				}
			}
		}

		responseWriter.endElement("tr");
	}

	protected String getAlloyColumnFieldName(Column column) {

		String columnFieldName = column.getId();

		if ((columnFieldName == null) || columnFieldName.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {

			ValueExpression sortByValueExpression = column.getValueExpression("sortBy");

			if (sortByValueExpression != null) {

				String expressionString = sortByValueExpression.getExpressionString();

				if (expressionString != null) {

					// Assuming an expression like "#{customer.firstName}", remove "#{" from  the front of the
					// expression and "}" from the end.
					expressionString = expressionString.substring(2, expressionString.length() - 1);

					// Assuming a trimmed expression like "customer.firstName", return "firstName"
					columnFieldName = expressionString.substring(expressionString.lastIndexOf(".") + 1);
				}
			}
		}

		return columnFieldName;
	}

	protected String getColumnClientBehaviorScript(FacesContext facesContext, DataTable dataTable, Column column,
		String clientId, String namingContainerId) {

		String clientBehaviorScript = null;
		Map<String, List<ClientBehavior>> clientBehaviorMap = column.getClientBehaviors();
		String defaultEventName = column.getDefaultEventName();
		List<ClientBehavior> clientBehaviorsForEvent = clientBehaviorMap.get(defaultEventName);

		if (clientBehaviorsForEvent != null) {

			for (ClientBehavior clientBehavior : clientBehaviorsForEvent) {

				List<ClientBehaviorContext.Parameter> parameters = new ArrayList<ClientBehaviorContext.Parameter>();
				String sortColumnClientIdParamName = clientId.concat("_sortColumnClientId");
				String sortColumnClientId = column.getClientId(facesContext);
				parameters.add(new ClientBehaviorContext.Parameter(sortColumnClientIdParamName, sortColumnClientId));

				String eventMetaKeyParamName = clientId.concat("_eventMetaKey");
				parameters.add(new ClientBehaviorContext.Parameter(eventMetaKeyParamName, "event.metaKey"));

				if (namingContainerId != null) {
					parameters.add(new ClientBehaviorContext.Parameter("'com.sun.faces.namingContainerId'",
							namingContainerId));
				}

				ClientBehaviorContext clientBehaviorContext = ClientBehaviorContext.createClientBehaviorContext(
						facesContext, dataTable, defaultEventName, clientId, parameters);
				clientBehaviorScript = clientBehavior.getScript(clientBehaviorContext);
			}
		}

		if (clientBehaviorScript != null) {
			clientBehaviorScript = clientBehaviorScript.replaceFirst("'event.metaKey'", "event.metaKey");
		}

		return clientBehaviorScript;
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return MODULES;
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}

	protected JavaScriptFragment getRowEventClientBehaviorScript(FacesContext facesContext, DataTable dataTable,
		String dataTableClientId, String eventName, String parameterName) {

		StringBuilder scriptBuilder = new StringBuilder();
		scriptBuilder.append("function(");
		scriptBuilder.append(parameterName);
		scriptBuilder.append(", event){");

		Map<String, List<ClientBehavior>> clientBehaviorMap = dataTable.getClientBehaviors();
		List<ClientBehavior> clientBehaviorsForEvent = clientBehaviorMap.get(eventName);

		if (clientBehaviorsForEvent != null) {

			for (ClientBehavior clientBehavior : clientBehaviorsForEvent) {

				List<ClientBehaviorContext.Parameter> parameters = new ArrayList<ClientBehaviorContext.Parameter>();
				String eventMetaKeyParamName = dataTableClientId.concat("_").concat(parameterName);
				parameters.add(new ClientBehaviorContext.Parameter(eventMetaKeyParamName, parameterName));

				ClientBehaviorContext clientBehaviorContext = ClientBehaviorContext.createClientBehaviorContext(
						facesContext, dataTable, eventName, dataTableClientId, parameters);
				String script = clientBehavior.getScript(clientBehaviorContext);

				if (script != null) {
					String quotedParamName = "'".concat(parameterName).concat("'");
					script = script.replaceFirst(quotedParamName, parameterName);
				}

				scriptBuilder.append(script);
			}
		}

		scriptBuilder.append("}");

		return new JavaScriptFragment(scriptBuilder.toString());
	}

}
