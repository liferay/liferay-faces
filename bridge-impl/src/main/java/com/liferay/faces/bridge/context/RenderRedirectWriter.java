/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context;

import java.io.IOException;
import java.io.Writer;

import javax.portlet.PortletResponse;


/**
 * Wraps a {@link PortletResponse} {@link Writer} in order to support render-redirect by buffering response output.
 *
 * @author  ngriffin
 */
public abstract class RenderRedirectWriter extends WriterWrapper {

	/**
	 * Discards the buffered response output so that it will not be written to the wrapped {@link Writer}.
	 */
	public abstract void discard();

	/**
	 * Renders the buffered response output to the wrapped {@link Writer}.
	 *
	 * @throws  IOException
	 */
	public abstract void render() throws IOException;

}
