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
package com.liferay.faces.alloy.component.datatable;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.DelegationResponseWriterBase;


/**
 * @author  Neil Griffin
 */
public class DataTableResponseWriter extends DelegationResponseWriterBase {

	// Private Constants
	private static final String HEADER_FACET = "header-facet";
	private static final String TH = "th";
	private static final String THEAD = "thead";
	private static final String TR = "tr";
	private static final String YUI3_DATATABLE_COLUMNS = "yui3-datatable-columns";
	private static final String YUI3_DATATABLE_FIRST_HEADER = "yui3-datatable-first-header";

	// Private Data Members
	private String elementName;
	private boolean firstColumnHeader;
	private boolean hasTableHeaderFacet;
	private boolean writingTableHeaderFacet;
	private boolean writingTH;

	public DataTableResponseWriter(ResponseWriter responseWriter, boolean hasTableHeaderFacet) {
		super(responseWriter);
		this.hasTableHeaderFacet = hasTableHeaderFacet;
	}

	@Override
	public void endElement(String name) throws IOException {

		super.endElement(name);
		
		if (TH.equals(name)) {
			writingTH = false;
		}
		else if (TR.equals(name)) {
			if (hasTableHeaderFacet) {
				writingTableHeaderFacet = false;
			}
		}
		elementName = null;
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		super.startElement(name, component);

		if (TH.equals(name)) {
			writingTH = true;
		}
		else if (TR.equals(name)) {
			firstColumnHeader = true;
		}
		else if (THEAD.equals(name)) {
			writingTableHeaderFacet = true;
			writeAttribute(StringPool.CLASS, YUI3_DATATABLE_COLUMNS, null);
		}
		elementName = name;
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (StringPool.CLASS.equals(name)) {

			if (writingTH) {
				
				if (writingTableHeaderFacet) {
					if (TH.equals(elementName)) {
						value = value + StringPool.SPACE + HEADER_FACET;
					}
				}
				else {
					if (firstColumnHeader) {
						value = value + StringPool.SPACE + YUI3_DATATABLE_FIRST_HEADER;
					}
				}
				firstColumnHeader = false;
			}
		}

		super.writeAttribute(name, value, property);
	}
}
