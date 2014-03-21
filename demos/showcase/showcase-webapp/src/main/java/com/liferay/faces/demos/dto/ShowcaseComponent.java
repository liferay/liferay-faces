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
package com.liferay.faces.demos.dto;

import java.io.Serializable;
import java.util.List;

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class ShowcaseComponent implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 1206732371727535048L;

	private String camelCaseName;
	private String lowerCaseName;
	private String prefix;
	private boolean resetToString = true;
	private String toString;
	private List<UseCase> useCases;

	public ShowcaseComponent(String prefix, String camelCaseName, String lowerCaseName, List<UseCase> useCases) {
		this.prefix = prefix;
		this.camelCaseName = camelCaseName;
		this.lowerCaseName = lowerCaseName;
		this.useCases = useCases;
	}

	@Override
	public String toString() {

		if (resetToString) {

			toString = null;

			if ((prefix != null) && (camelCaseName != null)) {

				return prefix + StringPool.COLON + camelCaseName;
			}
		}

		return toString;
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

	public List<UseCase> getUseCases() {
		return useCases;
	}
}
