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

import java.util.List;

import javax.faces.FacesWrapper;


/**
 * @author  Neil Griffin
 */
public abstract class ShowcaseComponentWrapper implements ShowcaseComponent, FacesWrapper<ShowcaseComponent> {

	@Override
	public String getCamelCaseName() {
		return getWrapped().getCamelCaseName();
	}

	@Override
	public int getCategoryIndex() {
		return getWrapped().getCategoryIndex();
	}

	@Override
	public String getFullName() {
		return getWrapped().getFullName();
	}

	@Override
	public String getKey() {
		return getWrapped().getKey();
	}

	@Override
	public String getLowerCaseName() {
		return getWrapped().getLowerCaseName();
	}

	@Override
	public String getPrefix() {
		return getWrapped().getPrefix();
	}

	@Override
	public List<UseCase> getUseCases() {
		return getWrapped().getUseCases();
	}

	@Override
	public abstract ShowcaseComponent getWrapped();
}
