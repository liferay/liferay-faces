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
package com.liferay.faces.demos.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.liferay.faces.demos.dto.ShowcaseComponent;
import com.liferay.faces.demos.dto.UseCase;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@RequestScoped
public class ShowcaseModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 3339667513222866249L;

	// Injections
	@ManagedProperty(name = "listModelBean", value = "#{listModelBean}")
	private transient ListModelBean listModelBean;

	// Private Data Members;
	private SelectedComponent selectedComponent;
	private ViewParameters viewParameters;

	public void setListModelBean(ListModelBean listModelBean) {
		this.listModelBean = listModelBean;
	}

	public SelectedComponent getSelectedComponent() {

		if (selectedComponent == null) {
			selectedComponent = new SelectedComponent(getViewParameters());
		}

		return selectedComponent;
	}

	public ViewParameters getViewParameters() {

		if (viewParameters == null) {
			viewParameters = new ViewParameters();
		}

		return viewParameters;
	}

	public class SelectedComponent {

		private String camelCaseName;
		private String lowerCaseName;
		private String prefix;
		private String useCaseName;
		private String[] useCaseSourceFileNames;

		public SelectedComponent(ViewParameters viewParameters) {
			ShowcaseComponent showcaseComponent = listModelBean.findShowcaseComponent(
					viewParameters.getComponentPrefix(), viewParameters.getComponentName());
			this.camelCaseName = showcaseComponent.getCamelCaseName();
			this.lowerCaseName = showcaseComponent.getLowerCaseName();
			this.prefix = showcaseComponent.getPrefix();

			List<UseCase> useCases = showcaseComponent.getUseCases();

			for (UseCase useCase : useCases) {

				if (useCase.getName().equals(viewParameters.getComponentUseCase())) {
					this.useCaseName = useCase.getName();
					this.useCaseSourceFileNames = useCase.getSourceFilenames();

					break;
				}
			}
		}

		public String getCamelCaseName() {
			return camelCaseName;
		}

		public String getLowerCaseName() {
			return lowerCaseName;
		}

		public String getPrefix() {
			return prefix;
		}

		public String getUseCaseName() {
			return useCaseName;
		}

		public String[] getUseCaseSourceFileNames() {
			return useCaseSourceFileNames;
		}
	}

	public class ViewParameters {

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
	}
}
