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

import org.primefaces.component.datatable.DataTable;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * This class extends the PrimeFaces {@link LazyDataModel} in order to provide a lazy-loaded list of {@link User}
 * objects to the p:dataTable in the users.xhtml Facelet view.
 *
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class UserLazyDataModel extends LazyDataModel<User> implements Serializable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UserLazyDataModel.class);

	// serialVersionUID
	private static final long serialVersionUID = 2087063071907939066L;

	// Private Constants
	private static final String DEFAULT_SORT_CRITERIA = "lastName";
	private static final boolean DEFAULT_SORT_ASCENDING = true;

	// Private Data Members
	private long companyId;

	public UserLazyDataModel(long companyId, int pageSize) {

		this.companyId = companyId;
		setPageSize(pageSize);
		setRowCount(countRows());
	}

	public int countRows() {

		int totalCount = 0;

		try {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("expandoAttributes", null);

			Sort sort = new Sort(DEFAULT_SORT_CRITERIA, Sort.STRING_TYPE, !DEFAULT_SORT_ASCENDING);

			boolean andSearch = true;
			int status = WorkflowConstants.STATUS_ANY;

			String firstName = null;
			String middleName = null;
			String lastName = null;
			String screenName = null;
			String emailAddress = null;

			Hits hits = UserLocalServiceUtil.search(companyId, firstName, middleName, lastName, screenName,
					emailAddress, status, params, andSearch, QueryUtil.ALL_POS, QueryUtil.ALL_POS, sort);
			totalCount = hits.getLength();

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return totalCount;
	}

	/**
	 * This method is called by the PrimeFaces {@link DataTable} according to the rows specified in the currently
	 * displayed page of data.
	 *
	 * @param  first      The zero-relative first row index.
	 * @param  pageSize   The number of rows to fetch.
	 * @param  sortField  The name of the field which the table is sorted by.
	 * @param  sortOrder  The sort order, which can be either ascending (default) or descending.
	 * @param  filters    The query criteria. Note that in order for the filtering to work with the Liferay API, the
	 *                    end-user must specify complete, matching words. Wildcards and partial matches are not
	 *                    supported.
	 */
	@Override
	public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder,
		Map<String, String> filters) {

		List<User> users = null;

		Sort sort;

		// sort
		if (sortField != null) {

			boolean ascending = true;

			if (sortOrder.equals(SortOrder.DESCENDING)) {
				ascending = false;
			}

			sort = new Sort(sortField, Sort.STRING_TYPE, !ascending);

		}
		else {
			sort = new Sort(DEFAULT_SORT_CRITERIA, Sort.STRING_TYPE, !DEFAULT_SORT_ASCENDING);
		}

		try {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int liferayOneRelativeFinishRow = first + pageSize + 1;

			boolean andSearch = true;
			int status = WorkflowConstants.STATUS_ANY;

			String firstName = trimExpresssion(filters.get("firstName"));
			String middleName = trimExpresssion(filters.get("middleName"));
			String lastName = trimExpresssion(filters.get("lastName"));
			String screenName = trimExpresssion(filters.get("screenName"));
			String emailAddress = trimExpresssion(filters.get("emailAddress"));

			// For the sake of speed, search for users in the index rather than
			// querying the database directly.
			Hits hits = UserLocalServiceUtil.search(companyId, firstName, middleName, lastName, screenName,
					emailAddress, status, params, andSearch, first, liferayOneRelativeFinishRow, sort);

			List<Document> documentHits = hits.toList();

			logger.debug(
				("filters firstName=[{0}] middleName=[{1}] lastName=[{2}] screenName=[{3}] emailAddress=[{4}] active=[{5}] andSearch=[{6}] startRow=[{7}] liferayOneRelativeFinishRow=[{8}] sortColumn=[{9}] reverseOrder=[{10}] hitCount=[{11}]"),
				firstName, middleName, lastName, screenName, emailAddress, status, andSearch, first,
				liferayOneRelativeFinishRow, sortField, sort.isReverse(), documentHits.size());

			// Convert the results from the search index into a list of user
			// objects.
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
}
