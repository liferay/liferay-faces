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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liferay.faces.alloy.component.select.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


/**
 * @author  Kyle Stiemann
 */
public class TableSuppressingResponseWriter extends DelegationResponseWriterBase {

	public TableSuppressingResponseWriter(ResponseWriter responseWriter) {
		super(responseWriter);
	}

	@Override
	public void endElement(String name) throws IOException {

		if ("table".equals(name)) {
			super.endElement("div");
		}
		else if ("tbody".equals(name) || "td".equals(name) || "thead".equals(name) || "tr".equals(name)) {
			// no-op
		}
		else {
			super.endElement(name);
		}
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		if ("table".equals(name)) {
			super.startElement("div", component);
		}
		else if ("tbody".equals(name) || "td".equals(name) || "thead".equals(name) || "tr".equals(name)) {
			// no-op
		}
		else {
			super.startElement(name, component);
		}
	}
}
