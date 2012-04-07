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
package com.liferay.faces.bridge.renderkit.icefaces;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.liferay.faces.bridge.component.icefaces.DataPaginator;
import com.liferay.faces.bridge.component.icefaces.DataPaginatorBridgeImpl;
import com.liferay.faces.bridge.component.icefaces.DataPaginatorWrapper;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.bridge.renderkit.html_basic.RendererWrapper;


/**
 * This class is part of a workaround for <a href="http://jira.icesoft.org/browse/ICE-6398">ICE-6398</a>.
 *
 * @author  Neil Griffin
 */
public class DataPaginatorRenderer extends RendererWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DataPaginatorRenderer.class);

	// Private Constants
	private static final String DATA_PAGINATOR_FQCN = "com.icesoft.faces.component.datapaginator.DataPaginator";

	// Private Data Members
	private Renderer wrappedRenderer;

	public DataPaginatorRenderer(Renderer renderer) {
		this.wrappedRenderer = renderer;
	}

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {
		DataPaginatorWrapper dataPaginatorWrapper = (DataPaginatorWrapper) uiComponent;
		UIComponent wrappedUIComponent = (UIComponent) dataPaginatorWrapper.getWrapped();
		wrappedRenderer.decode(facesContext, wrappedUIComponent);
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		if ((uiComponent != null) && uiComponent.getClass().getName().equals(DATA_PAGINATOR_FQCN)) {

			DataPaginator dataPaginator = new DataPaginatorBridgeImpl(uiComponent);

			try {
				dataPaginator.setUIData(dataPaginator.findUIData(facesContext));
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		wrappedRenderer.encodeBegin(facesContext, uiComponent);
	}

	@Override
	public Renderer getWrapped() {
		return wrappedRenderer;
	}
}
