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
package com.liferay.faces.demos.list;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.LazyDataModel;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;


/**
 * @author  Neil Griffin
 */
public class DocumentDataModel extends LazyDataModel<DocLibFileEntry> implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4895165386116316346L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DocumentDataModel.class);

	// Private Data Members
	private DLFolder dlFolder;
	private String portalURL;
	private String pathContext;
	private PermissionChecker permissionChecker;

	public DocumentDataModel(DLFolder dlFolder, int rowsPerPage, String portalURL, String pathContext,
		PermissionChecker permissionChecker) {

		this.dlFolder = dlFolder;
		setRowsPerPage(rowsPerPage);
		this.portalURL = portalURL;
		this.pathContext = pathContext;
		this.permissionChecker = permissionChecker;
		setSortColumn("title");
		setSortAscending(true);
	}

	@Override
	public int countRows() {
		return findViewableDocuments().size();
	}

	@Override
	public void deleteRow(Object primaryKey) throws IOException {
		long fileEntryId = (Long) primaryKey;

		try {
			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(fileEntryId);
			DLFileEntryServiceUtil.deleteFileEntry(dlFolder.getGroupId(), dlFolder.getFolderId(),
				dlFileEntry.getName());
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public List<DocLibFileEntry> findRows(int startRow, int finishRow) {

		List<DocLibFileEntry> viewableDocuments = findViewableDocuments();
		int totalViewableDocuments = viewableDocuments.size();

		if ((totalViewableDocuments > 0) && (startRow != QueryUtil.ALL_POS) && (finishRow != QueryUtil.ALL_POS)) {

			if (startRow > totalViewableDocuments) {
				startRow = totalViewableDocuments;
			}

			if (finishRow > totalViewableDocuments) {
				finishRow = totalViewableDocuments;
			}

			int includeFinishRowToo = finishRow + 1;
			viewableDocuments = viewableDocuments.subList(startRow, includeFinishRowToo);
		}

		return viewableDocuments;
	}

	protected List<DocLibFileEntry> findViewableDocuments() {
		List<DocLibFileEntry> viewableDocuments = new ArrayList<DocLibFileEntry>();

		try {
			long folderGroupId = dlFolder.getGroupId();
			long folderId = dlFolder.getFolderId();

			List<DLFileEntry> dlFileEntries = null;

			if (DLFolderPermission.contains(permissionChecker, folderGroupId, folderId, ActionKeys.VIEW)) {
				OrderByComparator orderByComparator = DocumentComparatorFactory.getComparator(getSortColumn(),
						isSortAscending());

				dlFileEntries = DLFileEntryServiceUtil.getFileEntries(folderGroupId, folderId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, orderByComparator);
			}

			if (dlFileEntries != null) {

				for (DLFileEntry dlFileEntry : dlFileEntries) {
					boolean permittedToViewDocument = false;

					try {
						permittedToViewDocument = DLFileEntryPermission.contains(permissionChecker, dlFileEntry,
								ActionKeys.VIEW);
					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
					}

					viewableDocuments.add(new DocLibFileEntry(dlFileEntry, portalURL, pathContext, folderGroupId,
							permittedToViewDocument));
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return viewableDocuments;
	}

	@Override
	public Object getPrimaryKey(DocLibFileEntry docLibFileEntry) {
		return docLibFileEntry.getFileEntryId();
	}

}
