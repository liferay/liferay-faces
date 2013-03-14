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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.icefaces.ace.component.datatable.DataTable;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * This class extends the ICEfaces {@link LazyDataModel} in order to provide a lazy-loaded list of {@link User} objects
 * to the ace:dataTable in the users.xhtml Facelet view.
 *
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class UserLazyDataModel extends LazyDataModel<User> implements Serializable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UserLazyDataModel.class);

	// serialVersionUID
	private static final long serialVersionUID = 9147332173339935105L;

	// Private Constants
	private static final String DEFAULT_SORT_CRITERIA = "lastName";

	// Private Data Members
	private long companyId;
	private int rowsPerPage;

	public UserLazyDataModel(long companyId, int rowsPerPage) {

		this.companyId = companyId;
		setRowsPerPage(rowsPerPage);
		setRowCount(countRows());
	}

	public int countRows() {

		int totalCount = 0;

		try {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("expandoAttributes", null);

			Sort sort = SortFactoryUtil.create(DEFAULT_SORT_CRITERIA, Sort.STRING_TYPE, true);

			boolean andSearch = true;
			boolean active = true;

			String firstName = null;
			String middleName = null;
			String lastName = null;
			String screenName = null;
			String emailAddress = null;

			Hits hits = UserLocalServiceUtil.search(companyId, firstName, middleName, lastName, screenName,
					emailAddress, active, params, andSearch, QueryUtil.ALL_POS, QueryUtil.ALL_POS, sort);
			totalCount = hits.getLength();

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return totalCount;
	}

	/**
	 * This method is called by the ICEfaces {@link DataTable} according to the rows specified in the currently
	 * displayed page of data.
	 *
	 * @param  first          The zero-relative first row index.
	 * @param  pageSize       The number of rows to fetch.
	 * @param  sortCriterias  The array of sort criteria objects. Note that since the Liferay API does not support
	 *                        multiple sort criterias, the length of this array will never be greater than one.
	 * @param  filters        The query criteria. Note that in order for the filtering to work with the Liferay API, the
	 *                        end-user must specify complete, matching words. Wildcards and partial matches are not
	 *                        supported.
	 */
	@Override
	public List<User> load(int first, int pageSize, SortCriteria[] sortCriterias, Map<String, String> filters) {

		List<User> users = null;

		Sort sort;

		// sort
		if ((sortCriterias != null) && (sortCriterias.length != 0)) {

			if (!sortCriterias[0].isAscending()) {
				sort = SortFactoryUtil.create(sortCriterias[0].getPropertyName(), Sort.STRING_TYPE, false);
			}
			else {
				sort = SortFactoryUtil.create(sortCriterias[0].getPropertyName(), Sort.STRING_TYPE, true);
			}
		}
		else {
			sort = SortFactoryUtil.create(DEFAULT_SORT_CRITERIA, Sort.STRING_TYPE, true);
		}

		try {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int liferayOneRelativeFinishRow = first + pageSize + 1;

			boolean andSearch = true;
			boolean active = true;

			String firstName = trimExpresssion(filters.get("firstName"));
			String middleName = trimExpresssion(filters.get("middleName"));
			String lastName = trimExpresssion(filters.get("lastName"));
			String screenName = trimExpresssion(filters.get("screenName"));
			String emailAddress = trimExpresssion(filters.get("emailAddress"));

			// For the sake of speed, search for users in the index rather than querying the database directly.
			Hits hits = UserLocalServiceUtil.search(companyId, firstName, middleName, lastName, screenName,
					emailAddress, active, params, andSearch, first, liferayOneRelativeFinishRow, sort);

			List<Document> documentHits = hits.toList();

			logger.debug(
				("filters firstName=[{0}] middleName=[{1}] lastName=[{2}] screenName=[{3}] emailAddress=[{4}] active=[{5}] andSearch=[{6}] startRow=[{7}] liferayOneRelativeFinishRow=[{8}] sortColumn=[{9}] reverseOrder=[{10}] hitCount=[{11}]"),
				firstName, middleName, lastName, screenName, emailAddress, active, andSearch, first,
				liferayOneRelativeFinishRow, sort.getFieldName(), sort.isReverse(), documentHits.size());

			// Convert the results from the search index into a list of user objects.
			users = new ArrayList<User>(documentHits.size());

			for (Document document : documentHits) {

				long userId = GetterUtil.getLong(document.get(Field.USER_ID));

				try {
					User user = UserLocalServiceUtil.getUserById(userId);
					users.add(user);
				}
				catch (NoSuchUserException nsue) {
					logger.error("User with userId=[{0}] does not exist in the search index. Please reindex.");
				}
			}

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return users;

	}

	protected String trimExpresssion(String value) {

		String expression = null;

		if (value != null) {
			String trimmedValue = value.trim();

			if (trimmedValue.length() > 0) {
				expression = trimmedValue.toLowerCase();
			}
		}

		return expression;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
}
