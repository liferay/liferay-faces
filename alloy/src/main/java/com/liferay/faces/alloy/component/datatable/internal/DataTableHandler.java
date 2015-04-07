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
package com.liferay.faces.alloy.component.datatable.internal;

import java.lang.reflect.Method;

import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;

import com.liferay.faces.alloy.component.datatable.DataTable;
import com.liferay.faces.alloy.component.datatable.RowDeselectEvent;
import com.liferay.faces.alloy.component.datatable.RowDeselectRangeEvent;
import com.liferay.faces.alloy.component.datatable.RowSelectEvent;
import com.liferay.faces.alloy.component.datatable.RowSelectRangeEvent;
import com.liferay.faces.util.view.facelets.MethodMetadata;


/**
 * @author  Neil Griffin
 */
public class DataTableHandler extends ComponentHandler {

	public DataTableHandler(ComponentConfig config) {
		super(config);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected MetaRuleset createMetaRuleset(Class type) {
		MetaRuleset metaRuleset = super.createMetaRuleset(type);
		metaRuleset.addRule(new DataTableMetaRule());

		return metaRuleset;
	}

	protected class DataTableMetaRule extends MetaRule {

		@Override
		public Metadata applyRule(String name, TagAttribute tagAttribute, MetadataTarget metadataTarget) {

			Metadata metadata = null;

			if ((metadataTarget != null) && (metadataTarget.isTargetInstanceOf(DataTable.class))) {

				Class<?>[] args = null;

				if (DataTableRenderer.ROW_SELECT_LISTENER.equals(name)) {
					args = new Class[] { RowSelectEvent.class };
				}
				else if (DataTableRenderer.ROW_SELECT_RANGE_LISTENER.equals(name)) {
					args = new Class[] { RowSelectRangeEvent.class };
				}
				else if (DataTableRenderer.ROW_DESELECT_LISTENER.equals(name)) {
					args = new Class[] { RowDeselectEvent.class };
				}
				else if (DataTableRenderer.ROW_DESELECT_RANGE_LISTENER.equals(name)) {
					args = new Class[] { RowDeselectRangeEvent.class };
				}

				if (args != null) {
					Method writeMethod = metadataTarget.getWriteMethod(name);
					metadata = new MethodMetadata(tagAttribute, writeMethod, args);
				}
			}

			return metadata;
		}
	}
}
