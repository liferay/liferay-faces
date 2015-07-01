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
package com.liferay.faces.demos.dto;

import java.io.Serializable;
import java.util.List;


/**
 * @author  Neil Griffin
 */
public class SelectedComponentImpl extends ShowcaseComponentWrapper implements SelectedComponent, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 3286285311354181810L;

	// Private Data Members
	private boolean rendered;
	private boolean required;
	private String useCaseKey;
	private String useCaseName;
	private List<CodeExample> useCaseCodeExamples;
	private ShowcaseComponent wrappedShowcaseComponent;

	public SelectedComponentImpl(ShowcaseComponent showcaseComponent, String useCaseName) {

		this.wrappedShowcaseComponent = showcaseComponent;
		this.rendered = true;
		this.required = false;
		this.useCaseName = useCaseName;

		List<UseCase> useCases = showcaseComponent.getUseCases();

		for (UseCase useCase : useCases) {

			if (useCase.getName().equals(useCaseName)) {
				this.useCaseCodeExamples = useCase.getCodeExamples();

				break;
			}
		}

		this.useCaseKey = showcaseComponent.getKey() + "-" + useCaseName;
	}

	@Override
	public boolean isRendered() {
		return rendered;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	@Override
	public void setRequired(boolean required) {
		this.required = required;
	}

	@Override
	public List<CodeExample> getUseCaseCodeExamples() {
		return useCaseCodeExamples;
	}

	@Override
	public String getUseCaseKey() {
		return useCaseKey;
	}

	@Override
	public String getUseCaseName() {
		return useCaseName;
	}

	@Override
	public ShowcaseComponent getWrapped() {
		return wrappedShowcaseComponent;
	}
}
