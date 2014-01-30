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
package com.liferay.faces.bridge.component.icefaces;

import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;


/**
 * This class is part of a workaround for <a href="http://jira.icesoft.org/browse/ICE-6398">ICE-6398</a>.
 *
 * @author  Neil Griffin
 */
public abstract class DataPaginator extends HtmlPanelGroup implements ActionSource {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.icesoft.faces.DataScroller";
	public static final String RENDERER_TYPE = "com.icesoft.faces.DataScroller";

	public abstract void addActionListener(ActionListener listener);

	@Override
	public abstract void broadcast(FacesEvent event) throws AbortProcessingException;

	public abstract UIData findUIData(FacesContext facesContext) throws Exception;

	public abstract String getscrollButtonCellClass();

	public abstract void gotoFastForward();

	public abstract void gotoFastRewind();

	public abstract void gotoFirstPage();

	public abstract void gotoLastPage();

	public abstract void gotoNextPage();

	public abstract void gotoPreviousPage();

	@Override
	public abstract void queueEvent(FacesEvent event);

	public abstract void removeActionListener(ActionListener listener);

	@Override
	public abstract void restoreState(FacesContext context, Object state);

	@Override
	public abstract Object saveState(FacesContext context);

	@SuppressWarnings("deprecation")
	public abstract javax.faces.el.MethodBinding getAction();

	@SuppressWarnings("deprecation")
	public abstract void setAction(javax.faces.el.MethodBinding action);

	@SuppressWarnings("deprecation")
	public abstract javax.faces.el.MethodBinding getActionListener();

	@SuppressWarnings("deprecation")
	public abstract void setActionListener(javax.faces.el.MethodBinding actionListener);

	public abstract ActionListener[] getActionListeners();

	public abstract String getBaseStyleClass();

	public abstract String getComponentType();

	public abstract boolean isDisabled();

	public abstract boolean isKeyboardNavigationEnabled();

	@Override
	public abstract boolean isRendered();

	public abstract void setDisabled(boolean disabled);

	public abstract String getDisplayedRowsCountVar();

	public abstract void setDisplayedRowsCountVar(String displayedRowsCountVar);

	public abstract boolean isImmediate();

	public abstract boolean isLastPage();

	public abstract boolean isRenderFacetsIfSinglePage();

	public abstract String getEnabledOnUserRole();

	public abstract void setEnabledOnUserRole(String enabledOnUserRole);

	@Override
	public abstract String getFamily();

	public abstract UIComponent getFastForward();

	public abstract void setFastForward(UIComponent previous);

	public abstract UIComponent getFastRewind();

	public abstract void setFastRewind(UIComponent previous);

	public abstract int getFastStep();

	public abstract void setFastStep(int fastStep);

	public abstract UIComponent getFirst();

	public abstract void setFirst(UIComponent first);

	public abstract int getFirstRow();

	public abstract String getFirstRowIndexVar();

	public abstract void setFirstRowIndexVar(String firstRowIndexVar);

	public abstract String getFor();

	public abstract void setFor(String forValue);

	public abstract void setImmediate(boolean immediate);

	public abstract void setKeyboardNavigationEnabled(boolean keyboardNavigationEnabled);

	public abstract boolean isVertical();

	public abstract UIComponent getLast();

	public abstract void setLast(UIComponent last);

	public abstract String getLastRowIndexVar();

	public abstract void setLastRowIndexVar(String lastRowIndexVar);

	public abstract UIComponent getNext();

	public abstract void setNext(UIComponent next);

	public abstract int getPageCount();

	public abstract String getPageCountVar();

	public abstract void setPageCountVar(String pageCountVar);

	public abstract int getPageIndex();

	public abstract String getPageIndexVar();

	public abstract void setPageIndexVar(String pageIndexVar);

	public abstract void setPaginator(boolean paginator);

	public abstract String getPaginatorActiveColumnClass();

	public abstract String getPaginatorColumnClass();

	public abstract int getPaginatorMaxPages();

	public abstract void setPaginatorMaxPages(int paginatorMaxPages);

	public abstract String getPaginatorTableClass();

	public abstract UIComponent getPrevious();

	public abstract void setPrevious(UIComponent previous);

	public abstract boolean isPaginator();

	public abstract String getRenderedOnUserRole();

	public abstract void setRenderedOnUserRole(String renderedOnUserRole);

	@Override
	public abstract String getRendererType();

	public abstract void setRenderFacetsIfSinglePage(boolean renderFacetsIfSinglePage);

	@Override
	public abstract boolean getRendersChildren();

	public abstract int getRowCount();

	public abstract int getRows();

	public abstract String getRowsCountVar();

	public abstract void setRowsCountVar(String rowsCountVar);

	@Override
	public abstract String getStyle();

	@Override
	public abstract void setStyle(String style);

	@Override
	public abstract String getStyleClass();

	@Override
	public abstract void setStyleClass(String styleClass);

	public abstract boolean isModelResultSet();

	public abstract int getTabindex();

	public abstract void setTabindex(int tabindex);

	public abstract UIData getUIData() throws Exception;

	public abstract void setUIData(UIData uiData) throws Exception;

	public abstract void setVertical(boolean vertical);

}
