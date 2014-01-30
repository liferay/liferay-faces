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
package com.liferay.faces.demos.list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.LazyDataModel;

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
 * @author  Neil Griffin
 */
public class UserLazyDataModel extends LazyDataModel<User> {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UserLazyDataModel.class);

	// Private Constants
	private static final String DEFAULT_SORT_CRITERIA = "lastName";

	// Private Data Members
	private long companyId;
	private SearchCriteria searchCriteria;

	public UserLazyDataModel(long companyId, int rowsPerPage, SearchCriteria searchCriteria) {
		this.companyId = companyId;
		this.searchCriteria = searchCriteria;
		setRowsPerPage(rowsPerPage);
		setSortColumn(DEFAULT_SORT_CRITERIA);
		setSortAscending(true);
	}

	@Override
	public int countRows() {

		int totalCount = 0;

		searchCriteria.setFormatExpressions(true);

		try {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("expandoAttributes", searchCriteria.getKeywords());

			Sort sort = SortFactoryUtil.create(DEFAULT_SORT_CRITERIA, Sort.STRING_TYPE, true);

			boolean andSearch = searchCriteria.isAndSearch();
			boolean active = searchCriteria.isActive();

			Hits hits = UserLocalServiceUtil.search(companyId, searchCriteria.getFirstName(),
					searchCriteria.getMiddleName(), searchCriteria.getLastName(), searchCriteria.getScreenName(),
					searchCriteria.getEmailAddress(), active, params, andSearch, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					sort);
			totalCount = hits.getLength();

			logger.debug(
				"searchCriteria firstName=[{0}] middleName=[{1}] lastName=[{2}] screenName=[{3}] emailAddress=[{4}] active=[{5}] andSearch=[{6}] totalCount=[{7}]",
				searchCriteria.getFirstName(), searchCriteria.getMiddleName(), searchCriteria.getLastName(),
				searchCriteria.getScreenName(), searchCriteria.getEmailAddress(), active, andSearch, totalCount);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		searchCriteria.setFormatExpressions(false);

		return totalCount;
	}

	@Override
	public void deleteRow(Object primaryKey) throws IOException {
		// no-op
	}

	@Override
	public List<User> findRows(int startRow, int finishRow) {
		List<User> users = null;
		searchCriteria.setFormatExpressions(true);

		Sort sort;

		// sort
		if (!isSortAscending()) {
			sort = SortFactoryUtil.create(getSortColumn(), Sort.STRING_TYPE, false);
		}
		else {
			sort = SortFactoryUtil.create(getSortColumn(), Sort.STRING_TYPE, true);
		}

		try {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int liferayOneRelativeFinishRow = finishRow + 1;

			boolean andSearch = searchCriteria.isAndSearch();
			boolean active = searchCriteria.isActive();

			Hits hits = UserLocalServiceUtil.search(companyId, searchCriteria.getFirstName(),
					searchCriteria.getMiddleName(), searchCriteria.getLastName(), searchCriteria.getScreenName(),
					searchCriteria.getEmailAddress(), active, params, andSearch, startRow, liferayOneRelativeFinishRow,
					sort);

			List<Document> documentHits = hits.toList();

			logger.debug(
				"searchCriteria firstName=[{0}] middleName=[{1}] lastName=[{2}] screenName=[{3}] emailAddress=[{4}] active=[{5}] andSearch=[{6}] startRow=[{7}] liferayOneRelativeFinishRow=[{8}] sortColumn=[{9}] reverseOrder=[{10}] hitCount=[{11}]",
				searchCriteria.getFirstName(), searchCriteria.getMiddleName(), searchCriteria.getLastName(),
				searchCriteria.getScreenName(), searchCriteria.getEmailAddress(), active, andSearch, startRow,
				liferayOneRelativeFinishRow, sort.getFieldName(), sort.isReverse(), documentHits.size());

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

		searchCriteria.setFormatExpressions(false);

		return users;
	}

	@Override
	public Object getPrimaryKey(User user) {
		return user.getUserId();
	}

}
