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
package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;


/**
 * @author  Brian Wing Shun Chan
 * @author  Charles May
 */
public class DLFileEntryPermission {

	public static void check(PermissionChecker permissionChecker, DLFileEntry fileEntry, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, fileEntry, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(PermissionChecker permissionChecker, long fileEntryId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, fileEntryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(PermissionChecker permissionChecker, long groupId, long folderId, String name,
		String actionId) throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, folderId, name, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(PermissionChecker permissionChecker, DLFileEntry fileEntry, String actionId)
		throws PortalException, SystemException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(permissionChecker, fileEntry.getGroupId(),
				DLFileEntry.class.getName(), fileEntry.getFileEntryId(), PortletKeys.DOCUMENT_LIBRARY, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		boolean excludeWorkingCopy = true;
		DLFileVersion latestFileVersion = DLFileVersionLocalServiceUtil.getLatestFileVersion(fileEntry.getFileEntryId(),
				excludeWorkingCopy);

		if (latestFileVersion.isPending()) {
			hasPermission = WorkflowPermissionUtil.hasPermission(permissionChecker, fileEntry.getGroupId(),
					DLFileEntry.class.getName(), fileEntry.getFileEntryId(), actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}

		if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			if (fileEntry.getFolderId() != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				DLFolder folder = DLFolderLocalServiceUtil.getFolder(fileEntry.getFolderId());

				if (!DLFolderPermission.contains(permissionChecker, folder, ActionKeys.ACCESS) &&
						!DLFolderPermission.contains(permissionChecker, folder, ActionKeys.VIEW)) {

					return false;
				}
			}
		}

		if (permissionChecker.hasOwnerPermission(fileEntry.getCompanyId(), DLFileEntry.class.getName(),
					fileEntry.getFileEntryId(), fileEntry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(fileEntry.getGroupId(), DLFileEntry.class.getName(),
				fileEntry.getFileEntryId(), actionId);
	}

	public static boolean contains(PermissionChecker permissionChecker, long fileEntryId, String actionId)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(fileEntryId);

		return contains(permissionChecker, fileEntry, actionId);
	}

	public static boolean contains(PermissionChecker permissionChecker, long groupId, long folderId, String name,
		String actionId) throws PortalException, SystemException {

		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(groupId, folderId, name);

		return contains(permissionChecker, fileEntry, actionId);
	}

}
