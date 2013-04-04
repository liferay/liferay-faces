/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.component.icefaces;

import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;


/**
 * This class is part of a workaround for <a href="http://jira.icesoft.org/browse/ICE-6398">ICE-6398</a>.
 *
 * @author  Neil Griffin
 */
public abstract class DataPaginator extends HtmlPanelGroup implements ActionSource {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.icesoft.faces.DataScroller";
	public static final String RENDERER_TYPE = "com.icesoft.faces.DataScroller";

	public abstract UIData findUIData(FacesContext facesContext) throws Exception;

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public boolean isDisabled() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public boolean isKeyboardNavigationEnabled() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public boolean isLastPage() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public boolean isRenderFacetsIfSinglePage() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public UIComponent getFastForward() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public UIComponent getFastRewind() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public int getFastStep() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public UIComponent getFirst() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public int getFirstRow() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public boolean isVertical() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public UIComponent getLast() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public UIComponent getNext() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public int getPageCount() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public int getPageIndex() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public int getPaginatorMaxPages() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public UIComponent getPrevious() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public boolean isPaginator() {
		return false;
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public int getRowCount() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public int getRows() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public boolean isModelResultSet() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is only present so that the JSF implementation knows the data type.
	 */
	public int getTabindex() {
		throw new UnsupportedOperationException();
	}

	public abstract UIData getUIData() throws Exception;

	public abstract void setUIData(UIData uiData) throws Exception;
}
