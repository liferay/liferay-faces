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
package com.liferay.faces.alloy.component.paginator;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIComponentBase;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PaginatorBase extends UIComponentBase implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.paginator.Paginator";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.paginator.PaginatorRenderer";

	// Protected Enumerations
	protected enum PaginatorPropertyKeys {
		ajax,
		execute,
		firstPage,
		firstPageLabel,
		for_,
		lastPageLabel,
		maxPageNumberControls,
		nextPageLabel,
		previousPageLabel,
		process,
		render,
		showFirstPageControl,
		showLastPageControl,
		showNextPageControl,
		showPageNumberControls,
		showPreviousPageControl,
		style,
		styleClass,
		summaryPosition,
		update
	}

	public PaginatorBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public boolean isAjax() {
		return (Boolean) getStateHelper().eval(PaginatorPropertyKeys.ajax, true);
	}

	public void setAjax(boolean ajax) {
		getStateHelper().put(PaginatorPropertyKeys.ajax, ajax);
	}

	public String getExecute() {
		return (String) getStateHelper().eval(PaginatorPropertyKeys.execute, "@this @for");
	}

	public void setExecute(String execute) {
		getStateHelper().put(PaginatorPropertyKeys.execute, execute);
	}

	public int getFirstPage() {
		return (Integer) getStateHelper().eval(PaginatorPropertyKeys.firstPage, 1);
	}

	public void setFirstPage(int firstPage) {
		getStateHelper().put(PaginatorPropertyKeys.firstPage, firstPage);
	}

	public String getFirstPageLabel() {
		return (String) getStateHelper().eval(PaginatorPropertyKeys.firstPageLabel, "&laquo;");
	}

	public void setFirstPageLabel(String firstPageLabel) {
		getStateHelper().put(PaginatorPropertyKeys.firstPageLabel, firstPageLabel);
	}

	public String getFor() {
		return (String) getStateHelper().eval(PaginatorPropertyKeys.for_, null);
	}

	public void setFor(String for_) {
		getStateHelper().put(PaginatorPropertyKeys.for_, for_);
	}

	public String getLastPageLabel() {
		return (String) getStateHelper().eval(PaginatorPropertyKeys.lastPageLabel, "&raquo;");
	}

	public void setLastPageLabel(String lastPageLabel) {
		getStateHelper().put(PaginatorPropertyKeys.lastPageLabel, lastPageLabel);
	}

	public int getMaxPageNumberControls() {
		return (Integer) getStateHelper().eval(PaginatorPropertyKeys.maxPageNumberControls, 10);
	}

	public void setMaxPageNumberControls(int maxPageNumberControls) {
		getStateHelper().put(PaginatorPropertyKeys.maxPageNumberControls, maxPageNumberControls);
	}

	public String getNextPageLabel() {
		return (String) getStateHelper().eval(PaginatorPropertyKeys.nextPageLabel, "&rsaquo;");
	}

	public void setNextPageLabel(String nextPageLabel) {
		getStateHelper().put(PaginatorPropertyKeys.nextPageLabel, nextPageLabel);
	}

	public String getPreviousPageLabel() {
		return (String) getStateHelper().eval(PaginatorPropertyKeys.previousPageLabel, "&lsaquo;");
	}

	public void setPreviousPageLabel(String previousPageLabel) {
		getStateHelper().put(PaginatorPropertyKeys.previousPageLabel, previousPageLabel);
	}

	public String getProcess() {
		return (String) getStateHelper().eval(PaginatorPropertyKeys.process, getExecute());
	}

	public void setProcess(String process) {
		getStateHelper().put(PaginatorPropertyKeys.process, process);
	}

	public String getRender() {
		return (String) getStateHelper().eval(PaginatorPropertyKeys.render, "@this @for");
	}

	public void setRender(String render) {
		getStateHelper().put(PaginatorPropertyKeys.render, render);
	}

	public boolean isShowFirstPageControl() {
		return (Boolean) getStateHelper().eval(PaginatorPropertyKeys.showFirstPageControl, true);
	}

	public void setShowFirstPageControl(boolean showFirstPageControl) {
		getStateHelper().put(PaginatorPropertyKeys.showFirstPageControl, showFirstPageControl);
	}

	public boolean isShowLastPageControl() {
		return (Boolean) getStateHelper().eval(PaginatorPropertyKeys.showLastPageControl, true);
	}

	public void setShowLastPageControl(boolean showLastPageControl) {
		getStateHelper().put(PaginatorPropertyKeys.showLastPageControl, showLastPageControl);
	}

	public boolean isShowNextPageControl() {
		return (Boolean) getStateHelper().eval(PaginatorPropertyKeys.showNextPageControl, true);
	}

	public void setShowNextPageControl(boolean showNextPageControl) {
		getStateHelper().put(PaginatorPropertyKeys.showNextPageControl, showNextPageControl);
	}

	public boolean isShowPageNumberControls() {
		return (Boolean) getStateHelper().eval(PaginatorPropertyKeys.showPageNumberControls, true);
	}

	public void setShowPageNumberControls(boolean showPageNumberControls) {
		getStateHelper().put(PaginatorPropertyKeys.showPageNumberControls, showPageNumberControls);
	}

	public boolean isShowPreviousPageControl() {
		return (Boolean) getStateHelper().eval(PaginatorPropertyKeys.showPreviousPageControl, true);
	}

	public void setShowPreviousPageControl(boolean showPreviousPageControl) {
		getStateHelper().put(PaginatorPropertyKeys.showPreviousPageControl, showPreviousPageControl);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(PaginatorPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(PaginatorPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(PaginatorPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PaginatorPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-paginator");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(PaginatorPropertyKeys.styleClass, styleClass);
	}

	public String getSummaryPosition() {
		return (String) getStateHelper().eval(PaginatorPropertyKeys.summaryPosition, "bottom");
	}

	public void setSummaryPosition(String summaryPosition) {
		getStateHelper().put(PaginatorPropertyKeys.summaryPosition, summaryPosition);
	}

	public String getUpdate() {
		return (String) getStateHelper().eval(PaginatorPropertyKeys.update, getRender());
	}

	public void setUpdate(String update) {
		getStateHelper().put(PaginatorPropertyKeys.update, update);
	}
}
//J+
