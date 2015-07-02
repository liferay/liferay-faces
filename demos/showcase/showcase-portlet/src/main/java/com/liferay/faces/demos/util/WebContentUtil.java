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
package com.liferay.faces.demos.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;


/**
 * @author  Neil Griffin
 */
public class WebContentUtil {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(WebContentUtil.class);

	public static JournalArticle getArticle(long companyId, long userId, long groupId, long folderId, Locale locale,
		String title, String content) {

		JournalArticle journalArticle = null;

		try {

			try {
				String urlTitle = getUrlTitle(title);
				journalArticle = JournalArticleLocalServiceUtil.getArticleByUrlTitle(groupId, urlTitle);
			}
			catch (NoSuchArticleException e) {

				Map<Locale, String> titleMap = new HashMap<Locale, String>();
				titleMap.put(locale, title);

				Map<Locale, String> descriptionMap = Collections.emptyMap();
				String ddmStructureKey = "";
				String ddmTemplateKey = "";

				ServiceContext serviceContext = new ServiceContext();
				serviceContext.setCompanyId(companyId);
				serviceContext.setScopeGroupId(groupId);
				serviceContext.setUserId(userId);
				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);

				String defaultLanguageId = "";
				content = LocalizationUtil.updateLocalization("", "static-content", content, defaultLanguageId,
						defaultLanguageId, true, false);

				journalArticle = JournalArticleLocalServiceUtil.addArticle(userId, groupId, folderId, titleMap,
						descriptionMap, content, ddmStructureKey, ddmTemplateKey, serviceContext);
			}
		}
		catch (Exception e) {
			logger.error(e);
		}

		return journalArticle;
	}

	public static String getUrlTitle(String title) {

		title = title.toLowerCase();
		title = title.replaceAll(" ", "-");

		return title;
	}
}
