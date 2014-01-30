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
package com.liferay.faces.bridge.taglib.liferay;

import java.io.Serializable;


/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal.
 * Specifically, the {@link com.liferay.taglib.util.HtmlTopTag} works fine on Liferay Portal 6.0 (and above) but
 * requires an override of {@link com.liferay.taglib.util.HtmlTopTag#doAfterBody()} for Liferay 5.2.
 *
 * @author  Neil Griffin
 */
public class HtmlTopTagCompat extends com.liferay.taglib.util.HtmlTopTag implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 9062668299205537055L;

}
