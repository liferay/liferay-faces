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
package com.liferay.faces.alloy.component.datalist.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.dataitem.DataItem;
import com.liferay.faces.alloy.component.datalist.DataList;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Vernon Singleton
 */
//J-
@FacesRenderer(componentFamily = DataList.COMPONENT_FAMILY, rendererType = DataList.RENDERER_TYPE)
//J+
public class DataListRenderer extends DataListRendererBase {

	// Private constants
	private static final String ITEM = "item";
	private static final String DESCRIPTION = "description";
	private static final String ORDERED = "ordered";
	private static final String UNORDERED = "unordered";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DataListRenderer.class);

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		DataList dataList = (DataList) uiComponent;
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String clientId = uiComponent.getClientId(facesContext);

		// Encode the starting <ul>, <ol>, or <dl> element that represents the component.
		String type = dataList.getType();

		if (UNORDERED.equals(type)) {
			responseWriter.startElement("ul", uiComponent);
		}
		else if (ORDERED.equals(type)) {
			responseWriter.startElement("ol", uiComponent);
		}
		else if (DESCRIPTION.equals(type)) {
			responseWriter.startElement("dl", uiComponent);
		}

		responseWriter.writeAttribute("id", clientId, "id");
		RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent);
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		DataList dataList = (DataList) uiComponent;
		String type = dataList.getType();
		UIComponent facet = dataList.getFacet(DESCRIPTION);
		String styleClass = dataList.getStyleClass();

		Object value = dataList.getValue();
		String var = dataList.getVar();
		boolean iterateOverDataModel = ((value != null) && (var != null));
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		String itemTag = "li";

		if (DESCRIPTION.equals(type)) {
			itemTag = "dt";
		}

		if (iterateOverDataModel) {

			// Get the first child and use it as a prototype.
			DataItem prototypeChildDataItem = getFirstChildDataItem(dataList);

			if (prototypeChildDataItem == null) {
				logger.warn("Unable to iterate because alloy:dataList does not have an alloy:dataItem child element.");
			}
			else {

				int rowCount = dataList.getRowCount();

				for (int i = 0; i < rowCount; i++) {
					dataList.setRowIndex(i);

					// Encode the starting element that represents the specified child's content.
					responseWriter.startElement(itemTag, prototypeChildDataItem);
					RendererUtil.encodeStyleable(responseWriter, prototypeChildDataItem);

					// Encode the children of the specified child as the actual content.
					prototypeChildDataItem.encodeAll(facesContext);

					// Encode the closing element for the specified child.
					responseWriter.endElement(itemTag);

					if (facet != null) {
						responseWriter.startElement("dd", uiComponent);
						responseWriter.writeAttribute("class", styleClass + "-" + DESCRIPTION,
							"class");
						facet.encodeAll(facesContext);
						responseWriter.endElement("dd");
					}
				}

				dataList.setRowIndex(-1);
			}
		}

		// Otherwise, encode content for each child of the specified dataList.
		else {
			List<UIComponent> children = uiComponent.getChildren();
			int childCount = children.size();

			for (int i = 0; i < childCount; i++) {

				UIComponent child = children.get(i);

				if (child.isRendered() && (child instanceof DataItem)) {
					DataItem childDataItem = (DataItem) child;

					// Encode the starting element that represents the specified child's content.
					responseWriter.startElement(itemTag, childDataItem);
					RendererUtil.encodeStyleable(responseWriter, childDataItem);

					// Encode the children of the specified child as the actual content.
					childDataItem.encodeAll(facesContext);

					// Encode the closing element for the specified child.
					responseWriter.endElement(itemTag);
				}
				else {
					logger.warn("Unable to render child element of alloy:dataList since it is not alloy:dataItem");
				}
			}
		}

		dataList.setRowIndex(-1);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		DataList dataList = (DataList) uiComponent;
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		// Encode the closing <ul>, <ol>, or <dl> element that represents the component.
		String type = dataList.getType();

		if (UNORDERED.equals(type)) {
			responseWriter.endElement("ul");
		}
		else if (ORDERED.equals(type)) {
			responseWriter.endElement("ol");
		}
		else if (DESCRIPTION.equals(type)) {
			responseWriter.endElement("dl");
		}
	}

	protected List<DataItem> getChildDataItems(UIData uiData) {

		List<DataItem> childDataItems = new ArrayList<DataItem>();

		List<UIComponent> children = uiData.getChildren();

		for (UIComponent child : children) {

			if (child instanceof DataItem) {
				childDataItems.add((DataItem) child);
			}
		}

		return childDataItems;
	}

	protected DataItem getFirstChildDataItem(UIData uiData) {

		DataItem prototypeChildType = null;

		List<DataItem> childDataItems = getChildDataItems(uiData);

		if (childDataItems.size() > 0) {
			prototypeChildType = childDataItems.get(0);
		}

		return prototypeChildType;
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
