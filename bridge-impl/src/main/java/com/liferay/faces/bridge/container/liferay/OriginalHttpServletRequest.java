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
package com.liferay.faces.bridge.container.liferay;

import java.util.List;

import javax.portlet.MimeResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * This class wraps an instance of javax.servlet.http.HttpServletRequest which represents the "original" request that
 * Liferay received that initiated the rendering of the current portlet.
 */
public class OriginalHttpServletRequest extends HttpServletRequestWrapper {
	private HttpServletRequest wrappedHttpServletRequest;

	public OriginalHttpServletRequest(HttpServletRequest httpServletRequest) {
		super(httpServletRequest);
		this.wrappedHttpServletRequest = httpServletRequest;
	}

	@SuppressWarnings("unchecked")
	public List<String> getMarkupHeadElements() {
		return (List<String>) wrappedHttpServletRequest.getAttribute(MimeResponse.MARKUP_HEAD_ELEMENT);
	}

	public void setMarkupHeadElements(List<String> markupHeadElements) {
		wrappedHttpServletRequest.setAttribute(MimeResponse.MARKUP_HEAD_ELEMENT, markupHeadElements);
	}

}
