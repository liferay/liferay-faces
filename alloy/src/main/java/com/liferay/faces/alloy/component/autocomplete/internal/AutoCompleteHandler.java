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
package com.liferay.faces.alloy.component.autocomplete.internal;

import java.lang.reflect.Method;
import java.util.List;

import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;

import com.liferay.faces.alloy.component.autocomplete.AutoComplete;
import com.liferay.faces.util.view.facelets.MethodMetadata;


/**
 * @author  Kyle Stiemann
 */
public class AutoCompleteHandler extends ComponentHandler {

	public AutoCompleteHandler(ComponentConfig config) {
		super(config);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected MetaRuleset createMetaRuleset(Class type) {
		MetaRuleset metaRuleset = super.createMetaRuleset(type);
		metaRuleset.addRule(new AutoCompleteMethodRule());

		return metaRuleset;
	}

	protected class AutoCompleteMethodRule extends MetaRule {

		@Override
		public Metadata applyRule(String name, TagAttribute tagAttribute, MetadataTarget metadataTarget) {

			Metadata metadata = null;

			if ((metadataTarget != null) && (metadataTarget.isTargetInstanceOf(AutoComplete.class))) {

				if (AutoCompleteRenderer.SERVER_CUSTOM_FILTER.equals(name)) {
					Method writeMethod = metadataTarget.getWriteMethod(name);
					Class<?>[] args = new Class[] { String.class, List.class };
					metadata = new MethodMetadata(tagAttribute, writeMethod, args);
				}
			}

			return metadata;
		}
	}
}
