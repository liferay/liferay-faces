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
package com.liferay.faces.alloy.component.accordion.internal;

import java.lang.reflect.Method;

import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;

import com.liferay.faces.alloy.component.accordion.Accordion;
import com.liferay.faces.alloy.component.tab.TabCollapseEvent;
import com.liferay.faces.alloy.component.tab.TabExpandEvent;
import com.liferay.faces.util.view.facelets.MethodMetadata;


/**
 * @author  Neil Griffin
 */
public class AccordionHandler extends ComponentHandler {

	public AccordionHandler(ComponentConfig config) {
		super(config);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected MetaRuleset createMetaRuleset(Class type) {
		MetaRuleset metaRuleset = super.createMetaRuleset(type);
		metaRuleset.addRule(new AccordionMethodRule());

		return metaRuleset;
	}

	protected class AccordionMethodRule extends MetaRule {

		@Override
		public Metadata applyRule(String name, TagAttribute tagAttribute, MetadataTarget metadataTarget) {

			Metadata metadata = null;

			if ((metadataTarget != null) && (metadataTarget.isTargetInstanceOf(Accordion.class))) {

				if (AccordionRenderer.TAB_COLLAPSE_LISTENER.equals(name)) {
					Method writeMethod = metadataTarget.getWriteMethod(name);
					Class<?>[] args = new Class[] { TabCollapseEvent.class };
					metadata = new MethodMetadata(tagAttribute, writeMethod, args);
				}
				else if (AccordionRenderer.TAB_EXPAND_LISTENER.equals(name)) {
					Method writeMethod = metadataTarget.getWriteMethod(name);
					Class<?>[] args = new Class[] { TabExpandEvent.class };
					metadata = new MethodMetadata(tagAttribute, writeMethod, args);
				}
			}

			return metadata;
		}
	}
}
