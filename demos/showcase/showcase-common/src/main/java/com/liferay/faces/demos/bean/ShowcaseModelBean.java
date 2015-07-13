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
package com.liferay.faces.demos.bean;

import java.io.Serializable;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import com.liferay.faces.demos.dto.SelectedComponent;
import com.liferay.faces.demos.dto.SelectedComponentImpl;
import com.liferay.faces.demos.dto.ShowcaseComponent;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


// JSF 2: import javax.faces.bean.ManagedBean;
// JSF 2: import javax.faces.bean.ManagedProperty;
// JSF 2: import javax.faces.bean.ViewScoped;

/**
 * @author  Neil Griffin
 */
// JSF 2: @ManagedBean
// JSF 2: @ViewScoped
public class ShowcaseModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 3339667513222866249L;

	// Private Constants
	private static final boolean LIFERAY_FACES_BRIDGE_DETECTED = ProductMap.getInstance().get(
			ProductConstants.LIFERAY_FACES_BRIDGE).isDetected();

	// Injections
	// @ManagedProperty(name = "listModelBean", value = "#{listModelBean}")
	private transient ListModelBean listModelBean;

	// Private Data Members
	private String deploymentType;
	private SelectedComponent selectedComponent;
	private ViewParameters viewParameters;

	public String getDeploymentType() {

		if (deploymentType == null) {

			if (LIFERAY_FACES_BRIDGE_DETECTED) {
				deploymentType = "portlet";
			}
			else {
				deploymentType = "webapp";
			}
		}

		return deploymentType;
	}

	public ListModelBean getListModelBean() {
		if (this.listModelBean == null) {
			this.listModelBean = getLModelBean(FacesContext.getCurrentInstance());
		}

		return this.listModelBean;
	}

	public void setListModelBean(ListModelBean listModelBean) {
		this.listModelBean = listModelBean;
	}
	
	public SelectedComponent getSelectedComponent() {

		if (selectedComponent == null) {

			ViewParameters viewParameters = getViewParameters();

			if (viewParameters.isValid()) {

				ShowcaseComponent showcaseComponent = getListModelBean().findShowcaseComponent(
						viewParameters.getComponentPrefix(), viewParameters.getComponentName());

				selectedComponent = new SelectedComponentImpl(showcaseComponent, viewParameters.getComponentUseCase());
			}
		}

		return selectedComponent;
	}

	public void setSelectedComponent(SelectedComponent selectedComponent) {
		this.selectedComponent = selectedComponent;
	}

	protected ListModelBean getLModelBean(FacesContext facesContext) {

		Application application = facesContext.getApplication();
		ELResolver elResolver = application.getELResolver();
		ELContext elContext = facesContext.getELContext();

		return (ListModelBean) elResolver.getValue(elContext, null, "listModelBean");
	}
	
	public ViewParameters getViewParameters() {

		if (viewParameters == null) {
			viewParameters = new ViewParameters();
		}

		return viewParameters;
	}

	public class ViewParameters implements Serializable {

		// serialVersionUID
		private static final long serialVersionUID = 1629675419430845173L;

		// Private Data Members
		private String componentPrefix;
		private String componentName;
		private String componentUseCase;

		public String getComponentName() {
			return componentName;
		}

		public void setComponentName(String componentName) {
			this.componentName = componentName;
		}

		public String getComponentPrefix() {
			return componentPrefix;
		}

		public void setComponentPrefix(String componentPrefix) {
			this.componentPrefix = componentPrefix;
		}

		public String getComponentUseCase() {
			return componentUseCase;
		}

		public void setComponentUseCase(String componentUseCase) {
			this.componentUseCase = componentUseCase;
		}

		public boolean isValid() {
			return ((componentPrefix != null) && (componentName != null));
		}
	}
}
