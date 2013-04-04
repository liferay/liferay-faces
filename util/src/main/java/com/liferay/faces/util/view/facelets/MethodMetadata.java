/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.view.facelets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.el.MethodExpression;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagAttributeException;


/**
 * @author  Neil Griffin
 */
public class MethodMetadata extends Metadata {

	// Private Data Members
	Class<?>[] args;
	TagAttribute tagAttribute;
	Method writeMethod;

	public MethodMetadata(TagAttribute tagAttribute, Method writeMethod, Class<?>[] args) {
		this.tagAttribute = tagAttribute;
		this.writeMethod = writeMethod;
		this.args = args;
	}

	@Override
	public void applyMetadata(FaceletContext faceletContext, Object instance) {
		MethodExpression methodExpression = tagAttribute.getMethodExpression(faceletContext, null, args);

		try {
			writeMethod.invoke(instance, methodExpression);
		}
		catch (InvocationTargetException e) {
			throw new TagAttributeException(tagAttribute, e.getCause());
		}
		catch (Exception e) {
			throw new TagAttributeException(tagAttribute, e);
		}
	}

}
