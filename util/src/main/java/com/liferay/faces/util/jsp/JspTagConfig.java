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
package com.liferay.faces.util.jsp;

import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagConfig;


/**
 * This is a dummy class that exists in order to prevent an exception from being thrown during startup on JBoss AS. For
 * more information, see <a href="http://issues.liferay.com/browse/FACES-1576">FACES-1576</a>.
 *
 * @author  Neil Griffin
 */
public class JspTagConfig implements TagConfig {

	// Private Data Members
	private FaceletHandler nextHandler;
	private Tag tag;
	private String tagId;

	public FaceletHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(FaceletHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

}
