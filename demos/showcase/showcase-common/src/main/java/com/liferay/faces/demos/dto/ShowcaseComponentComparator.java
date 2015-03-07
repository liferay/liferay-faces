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

import java.util.Comparator;


/**
 * @author  Neil Griffin
 */
public class ShowcaseComponentComparator implements Comparator<ShowcaseComponent> {

	@Override
	public int compare(ShowcaseComponent showcaseComponent1, ShowcaseComponent showcaseComponent2) {
		String fullName1 = showcaseComponent1.getPrefix() + showcaseComponent1.getLowerCaseName();
		String fullName2 = showcaseComponent2.getPrefix() + showcaseComponent2.getLowerCaseName();

		return fullName1.compareTo(fullName2);
	}
}
