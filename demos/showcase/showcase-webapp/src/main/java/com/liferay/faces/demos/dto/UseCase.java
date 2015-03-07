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
public class UseCase implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 5675514126286108464L;

	// Private Data Members
	private String name;
	private List<CodeExample> codeExamples;

	public UseCase(String name, List<CodeExample> codeExamples) {
		this.name = name;
		this.codeExamples = codeExamples;
	}

	public List<CodeExample> getCodeExamples() {
		return codeExamples;
	}

	public String getName() {
		return name;
	}
}
