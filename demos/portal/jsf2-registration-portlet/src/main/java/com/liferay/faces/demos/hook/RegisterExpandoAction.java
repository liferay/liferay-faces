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
package com.liferay.faces.demos.hook;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.demos.expando.UserExpando;
import com.liferay.faces.demos.security.PermissionUtil;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.RoleLocalServiceUtil;

import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexer;


/**
 * This is a startup action hook that registers the expando attributes.
 *
 * @author  Neil Griffin
 */
public class RegisterExpandoAction extends SimpleAction {

	Logger logger = LoggerFactory.getLogger(RegisterExpandoAction.class);

	@Override
	public void run(String[] companyIds) throws ActionException {

		try {

			for (String companyIdAsString : companyIds) {
				long companyId = Long.parseLong(companyIdAsString);
				addExpandoColumns(companyId);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	protected void addExpandoColumn(long companyId, String modelClassName, String expandoColumnName,
		int expandoColumnType, boolean indexable) throws PortalException, SystemException {

		ExpandoTable expandoTable = null;

		try {
			expandoTable = ExpandoTableLocalServiceUtil.getDefaultTable(companyId, modelClassName);
		}
		catch (NoSuchTableException e) {
			expandoTable = ExpandoTableLocalServiceUtil.addDefaultTable(companyId, modelClassName);
			logger.debug("Added expando table for modelClassName=[{0}]", modelClassName);
		}

		ExpandoColumn expandoColumn = null;

		try {
			expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(expandoTable.getTableId(), expandoColumnName);
			logger.debug("Expando column=[{0}] exists for modelClassName=[{1}]", expandoColumnName, modelClassName);
		}
		catch (NoSuchColumnException e) {
			expandoColumn = ExpandoColumnLocalServiceUtil.addColumn(expandoTable.getTableId(), expandoColumnName,
					expandoColumnType);
			logger.debug("Added expando column=[{0}] to modelClassName=[{1}]", expandoColumnName, modelClassName);
		}

		if (indexable) {
			UnicodeProperties properties;
			properties = new UnicodeProperties();
			properties.setProperty(ExpandoBridgeIndexer.INDEXABLE, Boolean.valueOf(true).toString());
			expandoColumn.setTypeSettingsProperties(properties);
			ExpandoColumnLocalServiceUtil.updateExpandoColumn(expandoColumn);
		}

		if (expandoColumn != null) {

			// Add permissions to the column so that all users can VIEW and UPDATE.
			Role userRole = RoleLocalServiceUtil.getRole(companyId, RoleConstants.USER);
			String resourceId = ExpandoColumn.class.getName();
			String primKey = String.valueOf(expandoColumn.getColumnId());
			String[] actionKeys = new String[] { ActionKeys.VIEW, ActionKeys.UPDATE };
			PermissionUtil.grantPermissions(companyId, userRole.getRoleId(), resourceId,
				ResourceConstants.SCOPE_INDIVIDUAL, primKey, actionKeys);
		}
	}

	protected void addExpandoColumns(long companyId) throws SystemException, PortalException {

		for (UserExpando userExpando : UserExpando.values()) {
			addExpandoColumn(companyId, userExpando.getModelClassName(), userExpando.getName(),
				userExpando.getExpandoType(), userExpando.isIndexable());
		}
	}

}
