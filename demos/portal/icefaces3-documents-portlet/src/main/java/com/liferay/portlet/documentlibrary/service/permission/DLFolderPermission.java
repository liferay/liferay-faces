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
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;


/**
 * @author  Brian Wing Shun Chan
 */
public class DLFolderPermission {

	public static void check(PermissionChecker permissionChecker, DLFolder folder, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, folder, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(PermissionChecker permissionChecker, long groupId, long folderId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, folderId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(PermissionChecker permissionChecker, DLFolder folder, String actionId)
		throws PortalException, SystemException {

		if (actionId.equals(ActionKeys.ADD_FOLDER)) {
			actionId = ActionKeys.ADD_SUBFOLDER;
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(permissionChecker, folder.getGroupId(),
				DLFolder.class.getName(), folder.getFolderId(), PortletKeys.DOCUMENT_LIBRARY, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		long folderId = folder.getFolderId();

		if (actionId.equals(ActionKeys.VIEW)) {

			while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				folder = DLFolderLocalServiceUtil.getFolder(folderId);

				folderId = folder.getParentFolderId();

				if (
					!permissionChecker.hasOwnerPermission(folder.getCompanyId(), DLFolder.class.getName(),
							folder.getFolderId(), folder.getUserId(), actionId) &&
						!permissionChecker.hasPermission(folder.getGroupId(), DLFolder.class.getName(),
							folder.getFolderId(), actionId)) {

					return false;
				}

				if (!PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
					break;
				}
			}

			return true;
		}
		else {

			while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				folder = DLFolderLocalServiceUtil.getFolder(folderId);

				folderId = folder.getParentFolderId();

				if (permissionChecker.hasOwnerPermission(folder.getCompanyId(), DLFolder.class.getName(),
							folder.getFolderId(), folder.getUserId(), actionId)) {

					return true;
				}

				if (permissionChecker.hasPermission(folder.getGroupId(), DLFolder.class.getName(), folder.getFolderId(),
							actionId)) {

					return true;
				}
			}

			return false;
		}
	}

	public static boolean contains(PermissionChecker permissionChecker, long groupId, long folderId, String actionId)
		throws PortalException, SystemException {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return DLPermission.contains(permissionChecker, groupId, actionId);
		}
		else {
			DLFolder folder = DLFolderLocalServiceUtil.getFolder(folderId);

			return contains(permissionChecker, folder, actionId);
		}
	}

}
