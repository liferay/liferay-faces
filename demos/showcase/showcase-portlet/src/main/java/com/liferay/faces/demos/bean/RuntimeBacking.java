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
package com.liferay.faces.demos.bean;

import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.liferay.faces.demos.util.WebContentUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.journal.model.JournalArticle;


/**
 * @author  Juan Gonzalez
 */
@ManagedBean
@ApplicationScoped
public class RuntimeBacking {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(RuntimeBacking.class);

	// Private Data Members
	private String preferencesArticle1;
	private String preferencesArticle2;
	private String preferencesArticle3;

	@PostConstruct
	public void postConstruct() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, Object> requestAttributeMap = facesContext.getExternalContext().getRequestMap();
		ThemeDisplay themeDisplay = (ThemeDisplay) requestAttributeMap.get(WebKeys.THEME_DISPLAY);

		try {
			long companyId = themeDisplay.getCompanyId();
			long userId = themeDisplay.getUserId();
			long groupId = themeDisplay.getScopeGroupId();
			long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			Locale locale = themeDisplay.getLocale();

			JournalArticle article1 = WebContentUtil.getArticle(companyId, userId, groupId, folderId, locale,
					"Liferay Portal",
					"Liferay Portal is an enterprise web platform for building business solutions that deliver " +
					"immediate results and long-term value.");
			this.preferencesArticle1 = getPreferences(article1.getGroupId(), article1.getArticleId());

			JournalArticle article2 = WebContentUtil.getArticle(companyId, userId, groupId, folderId, locale,
					"Liferay Faces",
					"Liferay Faces is an umbrella project that provides support for the JavaServer™ Faces (JSF) " +
					"standard within Liferay Portal.");
			this.preferencesArticle2 = getPreferences(article2.getGroupId(), article2.getArticleId());
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	protected String getPreferences(long groupId, String articleId) {

		StringBuilder buf = new StringBuilder();
		buf.append("<portlet-preferences>");
		buf.append("<preference>");
		buf.append("<name>groupId</name>");
		buf.append("<value>");
		buf.append(groupId);
		buf.append("</value>");
		buf.append("</preference>");
		buf.append("<preference>");
		buf.append("<name>articleId</name>");
		buf.append("<value>");
		buf.append(articleId);
		buf.append("</value>");
		buf.append("</preference>");
		buf.append("<preference>");
		buf.append("<name>portletSetupShowBorders</name>");
		buf.append("<value>true</value>");
		buf.append("</preference>");
		buf.append("</portlet-preferences>");

		return buf.toString();
	}

	public String getPreferencesArticle1() {
		return preferencesArticle1;
	}

	public String getPreferencesArticle2() {
		return preferencesArticle2;
	}
}
