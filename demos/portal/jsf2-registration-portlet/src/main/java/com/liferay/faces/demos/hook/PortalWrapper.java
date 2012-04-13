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

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalPortEventListener;

import com.liferay.portlet.expando.model.ExpandoBridge;


/**
 * @author  Neil Griffin
 */
public abstract class PortalWrapper implements Portal {
	public void addPageDescription(String description, HttpServletRequest request) {
		getWrapped().addPageDescription(description, request);
	}

	public void addPageKeywords(String keywords, HttpServletRequest request) {
		getWrapped().addPageKeywords(keywords, request);
	}

	public void addPageSubtitle(String subtitle, HttpServletRequest request) {
		getWrapped().addPageSubtitle(subtitle, request);
	}

	public void addPageTitle(String title, HttpServletRequest request) {
		getWrapped().addPageTitle(title, request);
	}

	public void addPortalPortEventListener(PortalPortEventListener portalPortEventListener) {
		getWrapped().addPortalPortEventListener(portalPortEventListener);
	}

	public void addPortletBreadcrumbEntry(HttpServletRequest request, String title, String url) {
		getWrapped().addPortletBreadcrumbEntry(request, title, url);
	}

	public void addPortletBreadcrumbEntry(HttpServletRequest request, String title, String url,
		Map<String, Object> data) {
		getWrapped().addPortletBreadcrumbEntry(request, title, url, data);
	}

	public void addPortletDefaultResource(HttpServletRequest request, Portlet portlet) throws PortalException,
		SystemException {
		getWrapped().addPortletDefaultResource(request, portlet);
	}

	public void addPortletDefaultResource(long companyId, Layout layout, Portlet portlet) throws PortalException,
		SystemException {
		getWrapped().addPortletDefaultResource(companyId, layout, portlet);
	}

	public String addPreservedParameters(ThemeDisplay themeDisplay, String url) {
		return getWrapped().addPreservedParameters(themeDisplay, url);
	}

	public String addPreservedParameters(ThemeDisplay themeDisplay, Layout layout, String url, boolean doAsUser) {
		return getWrapped().addPreservedParameters(themeDisplay, layout, url, doAsUser);
	}

	public void clearRequestParameters(RenderRequest renderRequest) {
		getWrapped().clearRequestParameters(renderRequest);
	}

	public void copyRequestParameters(ActionRequest actionRequest, ActionResponse actionResponse) {
		getWrapped().copyRequestParameters(actionRequest, actionResponse);
	}

	public String escapeRedirect(String url) {
		return getWrapped().escapeRedirect(url);
	}

	public String generateRandomKey(HttpServletRequest request, String input) {
		return getWrapped().generateRandomKey(request, input);
	}

	public void initCustomSQL() {
		getWrapped().initCustomSQL();
	}

	public void removePortalPortEventListener(PortalPortEventListener portalPortEventListener) {
		getWrapped().removePortalPortEventListener(portalPortEventListener);
	}

	public String renderPage(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response,
		String path) throws IOException, ServletException {
		return getWrapped().renderPage(servletContext, request, response, path);
	}

	public String renderPortlet(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response,
		Portlet portlet, String queryString, boolean writeOutput) throws IOException, ServletException {
		return getWrapped().renderPortlet(servletContext, request, response, portlet, queryString, writeOutput);
	}

	public String renderPortlet(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response,
		Portlet portlet, String queryString, String columnId, Integer columnPos, Integer columnCount,
		boolean writeOutput) throws IOException, ServletException {
		return getWrapped().renderPortlet(servletContext, request, response, portlet, queryString, columnId, columnPos,
				columnCount, writeOutput);
	}

	public String renderPortlet(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response,
		Portlet portlet, String queryString, String columnId, Integer columnPos, Integer columnCount, String path,
		boolean writeOutput) throws IOException, ServletException {
		return getWrapped().renderPortlet(servletContext, request, response, portlet, queryString, columnId, columnPos,
				columnCount, path, writeOutput);
	}

	public void resetCDNHosts() {
		getWrapped().resetCDNHosts();
	}

	public Set<String> resetPortletAddDefaultResourceCheckWhitelist() {
		return getWrapped().resetPortletAddDefaultResourceCheckWhitelist();
	}

	public Set<String> resetPortletAddDefaultResourceCheckWhitelistActions() {
		return getWrapped().resetPortletAddDefaultResourceCheckWhitelistActions();
	}

	public void sendError(Exception e, ActionRequest actionRequest, ActionResponse actionResponse) throws IOException {
		getWrapped().sendError(e, actionRequest, actionResponse);
	}

	public void sendError(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException,
		ServletException {
		getWrapped().sendError(e, request, response);
	}

	public void sendError(int status, Exception e, ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {
		getWrapped().sendError(status, e, actionRequest, actionResponse);
	}

	public void sendError(int status, Exception e, HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		getWrapped().sendError(status, e, request, response);
	}

	public void storePreferences(PortletPreferences portletPreferences) throws IOException, ValidatorException {
		getWrapped().storePreferences(portletPreferences);
	}

	public String[] stripURLAnchor(String url, String separator) {
		return getWrapped().stripURLAnchor(url, separator);
	}

	public String transformCustomSQL(String sql) {
		return getWrapped().transformCustomSQL(sql);
	}

	public PortletMode updatePortletMode(String portletId, User user, Layout layout, PortletMode portletMode,
		HttpServletRequest request) {
		return getWrapped().updatePortletMode(portletId, user, layout, portletMode, request);
	}

	public String updateRedirect(String redirect, String oldPath, String newPath) {
		return getWrapped().updateRedirect(redirect, oldPath, newPath);
	}

	public WindowState updateWindowState(String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest request) {
		return getWrapped().updateWindowState(portletId, user, layout, windowState, request);
	}

	public String getActualURL(long groupId, boolean privateLayout, String mainPath, String friendlyURL,
		Map<String, String[]> params, Map<String, Object> requestContext) throws PortalException, SystemException {
		return getWrapped().getActualURL(groupId, privateLayout, mainPath, friendlyURL, params, requestContext);
	}

	public String getAlternateURL(HttpServletRequest request, String canonicalURL, Locale locale) {
		return getWrapped().getAlternateURL(request, canonicalURL, locale);
	}

	public Set<String> getAuthTokenIgnoreActions() {
		return getWrapped().getAuthTokenIgnoreActions();
	}

	public Set<String> getAuthTokenIgnorePortlets() {
		return getWrapped().getAuthTokenIgnorePortlets();
	}

	public BaseModel<?> getBaseModel(Resource resource) throws PortalException, SystemException {
		return getWrapped().getBaseModel(resource);
	}

	public BaseModel<?> getBaseModel(ResourcePermission resourcePermission) throws PortalException, SystemException {
		return getWrapped().getBaseModel(resourcePermission);
	}

	public BaseModel<?> getBaseModel(String modelName, String primKey) throws PortalException, SystemException {
		return getWrapped().getBaseModel(modelName, primKey);
	}

	public long getBasicAuthUserId(HttpServletRequest request) throws PortalException, SystemException {
		return getWrapped().getBasicAuthUserId(request);
	}

	public long getBasicAuthUserId(HttpServletRequest request, long companyId) throws PortalException, SystemException {
		return getWrapped().getBasicAuthUserId(request, companyId);
	}

	public String getCanonicalURL(String completeURL, ThemeDisplay themeDisplay) throws PortalException,
		SystemException {
		return getWrapped().getCanonicalURL(completeURL, themeDisplay);
	}

	@SuppressWarnings("deprecation")
	public String getCDNHost() {
		return getWrapped().getCDNHost();
	}

	public String getCDNHost(boolean secure) {
		return getWrapped().getCDNHost(secure);
	}

	public String getCDNHost(HttpServletRequest request) throws PortalException, SystemException {
		return getWrapped().getCDNHost(request);
	}

	public String getCDNHostHttp(long companyId) {
		return getWrapped().getCDNHostHttp(companyId);
	}

	public String getCDNHostHttps(long companyId) {
		return getWrapped().getCDNHostHttps(companyId);
	}

	public String getClassName(long classNameId) {
		return getWrapped().getClassName(classNameId);
	}

	public long getClassNameId(Class<?> clazz) {
		return getWrapped().getClassNameId(clazz);
	}

	public long getClassNameId(String value) {
		return getWrapped().getClassNameId(value);
	}

	public String getClassNamePortletId(String className) {
		return getWrapped().getClassNamePortletId(className);
	}

	public Company getCompany(HttpServletRequest request) throws PortalException, SystemException {
		return getWrapped().getCompany(request);
	}

	public Company getCompany(PortletRequest portletRequest) throws PortalException, SystemException {
		return getWrapped().getCompany(portletRequest);
	}

	public long getCompanyId(HttpServletRequest requestuest) {
		return getWrapped().getCompanyId(requestuest);
	}

	public long getCompanyId(PortletRequest portletRequest) {
		return getWrapped().getCompanyId(portletRequest);
	}

	public long[] getCompanyIds() {
		return getWrapped().getCompanyIds();
	}

	public String getComputerAddress() {
		return getWrapped().getComputerAddress();
	}

	public String getComputerName() {
		return getWrapped().getComputerName();
	}

	public String getControlPanelCategory(String portletId, ThemeDisplay themeDisplay) throws SystemException {
		return getWrapped().getControlPanelCategory(portletId, themeDisplay);
	}

	public String getControlPanelFullURL(long scopeGroupId, String ppid, Map<String, String[]> params)
		throws PortalException, SystemException {
		return getWrapped().getControlPanelFullURL(scopeGroupId, ppid, params);
	}

	public Set<Portlet> getControlPanelPortlets(long companyId, String category) throws SystemException {
		return getWrapped().getControlPanelPortlets(companyId, category);
	}

	public List<Portlet> getControlPanelPortlets(String category, ThemeDisplay themeDisplay) throws SystemException {
		return getWrapped().getControlPanelPortlets(category, themeDisplay);
	}

	public String getCreateAccountURL(HttpServletRequest request, ThemeDisplay themeDisplay) throws Exception {
		return getWrapped().getCreateAccountURL(request, themeDisplay);
	}

	public String getCurrentCompleteURL(HttpServletRequest request) {
		return getWrapped().getCurrentCompleteURL(request);
	}

	public String getCurrentURL(HttpServletRequest request) {
		return getWrapped().getCurrentURL(request);
	}

	public String getCurrentURL(PortletRequest portletRequest) {
		return getWrapped().getCurrentURL(portletRequest);
	}

	public String getCustomSQLFunctionIsNotNull() {
		return getWrapped().getCustomSQLFunctionIsNotNull();
	}

	public String getCustomSQLFunctionIsNull() {
		return getWrapped().getCustomSQLFunctionIsNull();
	}

	public boolean isValidResourceId(String resourceId) {
		return getWrapped().isValidResourceId(resourceId);
	}

	public Date getDate(int month, int day, int year) {
		return getWrapped().getDate(month, day, year);
	}

	public Date getDate(int month, int day, int year, PortalException pe) throws PortalException {
		return getWrapped().getDate(month, day, year, pe);
	}

	public Date getDate(int month, int day, int year, TimeZone timeZone, PortalException pe) throws PortalException {
		return getWrapped().getDate(month, day, year, timeZone, pe);
	}

	public Date getDate(int month, int day, int year, int hour, int min, PortalException pe) throws PortalException {
		return getWrapped().getDate(month, day, year, hour, min, pe);
	}

	public Date getDate(int month, int day, int year, int hour, int min, TimeZone timeZone, PortalException pe)
		throws PortalException {
		return getWrapped().getDate(month, day, year, hour, min, timeZone, pe);
	}

	public long getDefaultCompanyId() {
		return getWrapped().getDefaultCompanyId();
	}

	public long getDigestAuthUserId(HttpServletRequest request) throws PortalException, SystemException {
		return getWrapped().getDigestAuthUserId(request);
	}

	public boolean isAllowAddPortletDefaultResource(HttpServletRequest request, Portlet portlet) throws PortalException,
		SystemException {
		return getWrapped().isAllowAddPortletDefaultResource(request, portlet);
	}

	public boolean isCompanyControlPanelVisible(ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return getWrapped().isCompanyControlPanelVisible(themeDisplay);
	}

	public boolean isLayoutFirstPageable(Layout layout) {
		return getWrapped().isLayoutFirstPageable(layout);
	}

	public boolean isLayoutFirstPageable(String type) {
		return getWrapped().isLayoutFirstPageable(type);
	}

	public boolean isLayoutFriendliable(Layout layout) {
		return getWrapped().isLayoutFriendliable(layout);
	}

	public boolean isLayoutFriendliable(String type) {
		return getWrapped().isLayoutFriendliable(type);
	}

	public boolean isLayoutParentable(Layout layout) {
		return getWrapped().isLayoutParentable(layout);
	}

	public boolean isLayoutParentable(String type) {
		return getWrapped().isLayoutParentable(type);
	}

	public boolean isLayoutSitemapable(Layout layout) {
		return getWrapped().isLayoutSitemapable(layout);
	}

	public boolean isSecure(HttpServletRequest request) {
		return getWrapped().isSecure(request);
	}

	public boolean isSystemRole(String roleName) {
		return getWrapped().isSystemRole(roleName);
	}

	public boolean isUpdateAvailable() throws SystemException {
		return getWrapped().isUpdateAvailable();
	}

	public String getEmailFromAddress(PortletPreferences preferences, long companyId, String defaultValue)
		throws SystemException {
		return getWrapped().getEmailFromAddress(preferences, companyId, defaultValue);
	}

	public String getEmailFromName(PortletPreferences preferences, long companyId, String defaultValue)
		throws SystemException {
		return getWrapped().getEmailFromName(preferences, companyId, defaultValue);
	}

	public Map<String, Serializable> getExpandoBridgeAttributes(ExpandoBridge expandoBridge,
		PortletRequest portletRequest) throws PortalException, SystemException {
		return getWrapped().getExpandoBridgeAttributes(expandoBridge, portletRequest);
	}

	public Serializable getExpandoValue(PortletRequest portletRequest, String name, int type, String displayType)
		throws PortalException, SystemException {
		return getWrapped().getExpandoValue(portletRequest, name, type, displayType);
	}

	public String getFacebookURL(Portlet portlet, String facebookCanvasPageURL, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {
		return getWrapped().getFacebookURL(portlet, facebookCanvasPageURL, themeDisplay);
	}

	public String getFirstPageLayoutTypes(PageContext pageContext) {
		return getWrapped().getFirstPageLayoutTypes(pageContext);
	}

	public String getGlobalLibDir() {
		return getWrapped().getGlobalLibDir();
	}

	public String getGoogleGadgetURL(Portlet portlet, ThemeDisplay themeDisplay) throws PortalException,
		SystemException {
		return getWrapped().getGoogleGadgetURL(portlet, themeDisplay);
	}

	public String getGroupFriendlyURL(Group group, boolean privateLayoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {
		return getWrapped().getGroupFriendlyURL(group, privateLayoutSet, themeDisplay);
	}

	public String[] getGroupPermissions(HttpServletRequest request) {
		return getWrapped().getGroupPermissions(request);
	}

	public String[] getGroupPermissions(PortletRequest portletRequest) {
		return getWrapped().getGroupPermissions(portletRequest);
	}

	public String[] getGuestPermissions(HttpServletRequest request) {
		return getWrapped().getGuestPermissions(request);
	}

	public String[] getGuestPermissions(PortletRequest portletRequest) {
		return getWrapped().getGuestPermissions(portletRequest);
	}

	public String getHomeURL(HttpServletRequest request) throws PortalException, SystemException {
		return getWrapped().getHomeURL(request);
	}

	public String getHost(HttpServletRequest request) {
		return getWrapped().getHost(request);
	}

	public String getHost(PortletRequest portletRequest) {
		return getWrapped().getHost(portletRequest);
	}

	public HttpServletRequest getHttpServletRequest(PortletRequest portletRequest) {
		return getWrapped().getHttpServletRequest(portletRequest);
	}

	public HttpServletResponse getHttpServletResponse(PortletResponse portletResponse) {
		return getWrapped().getHttpServletResponse(portletResponse);
	}

	public String getJournalArticleActualURL(long groupId, String mainPath, String friendlyURL,
		Map<String, String[]> params, Map<String, Object> requestContext) throws PortalException, SystemException {
		return getWrapped().getJournalArticleActualURL(groupId, mainPath, friendlyURL, params, requestContext);
	}

	public String getJsSafePortletId(String portletId) {
		return getWrapped().getJsSafePortletId(portletId);
	}

	public String getLayoutActualURL(Layout layout) {
		return getWrapped().getLayoutActualURL(layout);
	}

	public String getLayoutActualURL(Layout layout, String mainPath) {
		return getWrapped().getLayoutActualURL(layout, mainPath);
	}

	public String getLayoutActualURL(long groupId, boolean privateLayout, String mainPath, String friendlyURL)
		throws PortalException, SystemException {
		return getWrapped().getLayoutActualURL(groupId, privateLayout, mainPath, friendlyURL);
	}

	public String getLayoutActualURL(long groupId, boolean privateLayout, String mainPath, String friendlyURL,
		Map<String, String[]> params, Map<String, Object> requestContext) throws PortalException, SystemException {
		return getWrapped().getLayoutActualURL(groupId, privateLayout, mainPath, friendlyURL, params, requestContext);
	}

	public String getLayoutEditPage(Layout layout) {
		return getWrapped().getLayoutEditPage(layout);
	}

	public String getLayoutEditPage(String type) {
		return getWrapped().getLayoutEditPage(type);
	}

	public String getLayoutFriendlyURL(Layout layout, ThemeDisplay themeDisplay) throws PortalException,
		SystemException {
		return getWrapped().getLayoutFriendlyURL(layout, themeDisplay);
	}

	public String getLayoutFriendlyURL(Layout layout, ThemeDisplay themeDisplay, Locale locale) throws PortalException,
		SystemException {
		return getWrapped().getLayoutFriendlyURL(layout, themeDisplay, locale);
	}

	public String getLayoutFullURL(ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return getWrapped().getLayoutFullURL(themeDisplay);
	}

	public String getLayoutFullURL(Layout layout, ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return getWrapped().getLayoutFullURL(layout, themeDisplay);
	}

	public String getLayoutFullURL(long groupId, String portletId) throws PortalException, SystemException {
		return getWrapped().getLayoutFullURL(groupId, portletId);
	}

	public String getLayoutFullURL(Layout layout, ThemeDisplay themeDisplay, boolean doAsUser) throws PortalException,
		SystemException {
		return getWrapped().getLayoutFullURL(layout, themeDisplay, doAsUser);
	}

	public String getLayoutFullURL(long groupId, String portletId, boolean secure) throws PortalException,
		SystemException {
		return getWrapped().getLayoutFullURL(groupId, portletId, secure);
	}

	public String getLayoutSetFriendlyURL(LayoutSet layoutSet, ThemeDisplay themeDisplay) throws PortalException,
		SystemException {
		return getWrapped().getLayoutSetFriendlyURL(layoutSet, themeDisplay);
	}

	public String getLayoutTarget(Layout layout) {
		return getWrapped().getLayoutTarget(layout);
	}

	public String getLayoutURL(ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return getWrapped().getLayoutURL(themeDisplay);
	}

	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return getWrapped().getLayoutURL(layout, themeDisplay);
	}

	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay, boolean doAsUser) throws PortalException,
		SystemException {
		return getWrapped().getLayoutURL(layout, themeDisplay, doAsUser);
	}

	public String getLayoutViewPage(Layout layout) {
		return getWrapped().getLayoutViewPage(layout);
	}

	public String getLayoutViewPage(String type) {
		return getWrapped().getLayoutViewPage(type);
	}

	public LiferayPortletRequest getLiferayPortletRequest(PortletRequest portletRequest) {
		return getWrapped().getLiferayPortletRequest(portletRequest);
	}

	public LiferayPortletResponse getLiferayPortletResponse(PortletResponse portletResponse) {
		return getWrapped().getLiferayPortletResponse(portletResponse);
	}

	public Locale getLocale(HttpServletRequest request) {
		return getWrapped().getLocale(request);
	}

	public Locale getLocale(RenderRequest renderRequest) {
		return getWrapped().getLocale(renderRequest);
	}

	public String getMailId(String mx, String popPortletPrefix, Object... ids) {
		return getWrapped().getMailId(mx, popPortletPrefix, ids);
	}

	@SuppressWarnings("deprecation")
	public boolean isCommunityAdmin(User user, long groupId) throws Exception {
		return getWrapped().isCommunityAdmin(user, groupId);
	}

	public boolean isCompanyAdmin(User user) throws Exception {
		return getWrapped().isCompanyAdmin(user);
	}

	public boolean isGroupAdmin(User user, long groupId) throws Exception {
		return getWrapped().isGroupAdmin(user, groupId);
	}

	public boolean isOmniadmin(long userId) {
		return getWrapped().isOmniadmin(userId);
	}

	public String getNetvibesURL(Portlet portlet, ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return getWrapped().getNetvibesURL(portlet, themeDisplay);
	}

	public String getNewPortletTitle(String portletTitle, String oldScopeName, String newScopeName) {
		return getWrapped().getNewPortletTitle(portletTitle, oldScopeName, newScopeName);
	}

	public HttpServletRequest getOriginalServletRequest(HttpServletRequest request) {
		return getWrapped().getOriginalServletRequest(request);
	}

	public String getOuterPortletId(HttpServletRequest request) {
		return getWrapped().getOuterPortletId(request);
	}

	public boolean isSystemGroup(String groupName) {
		return getWrapped().isSystemGroup(groupName);
	}

	public void setPageDescription(String description, HttpServletRequest request) {
		getWrapped().setPageDescription(description, request);
	}

	public void setPageKeywords(String keywords, HttpServletRequest request) {
		getWrapped().setPageKeywords(keywords, request);
	}

	public void setPageSubtitle(String subtitle, HttpServletRequest request) {
		getWrapped().setPageSubtitle(subtitle, request);
	}

	public void setPageTitle(String title, HttpServletRequest request) {
		getWrapped().setPageTitle(title, request);
	}

	public long getParentGroupId(long scopeGroupId) throws PortalException, SystemException {
		return getWrapped().getParentGroupId(scopeGroupId);
	}

	public String getPathContext() {
		return getWrapped().getPathContext();
	}

	public String getPathFriendlyURLPrivateGroup() {
		return getWrapped().getPathFriendlyURLPrivateGroup();
	}

	public String getPathFriendlyURLPrivateUser() {
		return getWrapped().getPathFriendlyURLPrivateUser();
	}

	public String getPathFriendlyURLPublic() {
		return getWrapped().getPathFriendlyURLPublic();
	}

	public String getPathImage() {
		return getWrapped().getPathImage();
	}

	public String getPathMain() {
		return getWrapped().getPathMain();
	}

	public String getPathProxy() {
		return getWrapped().getPathProxy();
	}

	public long getPlidFromFriendlyURL(long companyId, String friendlyURL) {
		return getWrapped().getPlidFromFriendlyURL(companyId, friendlyURL);
	}

	public long getPlidFromPortletId(long groupId, String portletId) throws PortalException, SystemException {
		return getWrapped().getPlidFromPortletId(groupId, portletId);
	}

	public long getPlidFromPortletId(long groupId, boolean privateLayout, String portletId) throws PortalException,
		SystemException {
		return getWrapped().getPlidFromPortletId(groupId, privateLayout, portletId);
	}

	public String getPortalLibDir() {
		return getWrapped().getPortalLibDir();
	}

	@SuppressWarnings("deprecation")
	public int getPortalPort() {
		return getWrapped().getPortalPort();
	}

	@SuppressWarnings("deprecation")
	public int getPortalPort(boolean secure) {
		return getWrapped().getPortalPort();
	}

	public void setPortalPort(HttpServletRequest request) {
		getWrapped().setPortalPort(request);
	}

	public Properties getPortalProperties() {
		return getWrapped().getPortalProperties();
	}

	public String getPortalURL(HttpServletRequest request) {
		return getWrapped().getPortalURL(request);
	}

	public String getPortalURL(PortletRequest portletRequest) {
		return getWrapped().getPortalURL(portletRequest);
	}

	public String getPortalURL(ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return getWrapped().getPortalURL(themeDisplay);
	}

	public String getPortalURL(HttpServletRequest request, boolean secure) {
		return getWrapped().getPortalURL(request, secure);
	}

	public String getPortalURL(Layout layout, ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return getWrapped().getPortalURL(layout, themeDisplay);
	}

	public String getPortalURL(PortletRequest portletRequest, boolean secure) {
		return getWrapped().getPortalURL(portletRequest, secure);
	}

	public String getPortalURL(String serverName, int serverPort, boolean secure) {
		return getWrapped().getPortalURL(serverName, serverPort, secure);
	}

	public String getPortalWebDir() {
		return getWrapped().getPortalWebDir();
	}

	public Set<String> getPortletAddDefaultResourceCheckWhitelist() {
		return getWrapped().getPortletAddDefaultResourceCheckWhitelist();
	}

	public Set<String> getPortletAddDefaultResourceCheckWhitelistActions() {
		return getWrapped().getPortletAddDefaultResourceCheckWhitelistActions();
	}

	@SuppressWarnings("deprecation")
	public List<BreadcrumbEntry> getPortletBreadcrumbList(HttpServletRequest request) {
		return getWrapped().getPortletBreadcrumbList(request);
	}

	public List<BreadcrumbEntry> getPortletBreadcrumbs(HttpServletRequest request) {
		return getWrapped().getPortletBreadcrumbs(request);
	}

	public String getPortletDescription(Portlet portlet, User user) {
		return getWrapped().getPortletDescription(portlet, user);
	}

	public String getPortletDescription(String portletId, Locale locale) {
		return getWrapped().getPortletDescription(portletId, locale);
	}

	public String getPortletDescription(String portletId, String languageId) {
		return getWrapped().getPortletDescription(portletId, languageId);
	}

	public String getPortletDescription(String portletId, User user) {
		return getWrapped().getPortletDescription(portletId, user);
	}

	public String getPortletDescription(Portlet portlet, ServletContext servletContext, Locale locale) {
		return getWrapped().getPortletDescription(portlet, servletContext, locale);
	}

	public String getPortletId(HttpServletRequest request) {
		return getWrapped().getPortletId(request);
	}

	public String getPortletId(PortletRequest portletRequest) {
		return getWrapped().getPortletId(portletRequest);
	}

	public String getPortletLongTitle(Portlet portlet, Locale locale) {
		return getWrapped().getPortletLongTitle(portlet, locale);
	}

	public String getPortletLongTitle(Portlet portlet, String languageId) {
		return getWrapped().getPortletLongTitle(portlet, languageId);
	}

	public String getPortletLongTitle(Portlet portlet, User user) {
		return getWrapped().getPortletLongTitle(portlet, user);
	}

	public String getPortletLongTitle(String portletId, Locale locale) {
		return getWrapped().getPortletLongTitle(portletId, locale);
	}

	public String getPortletLongTitle(String portletId, String languageId) {
		return getWrapped().getPortletLongTitle(portletId, languageId);
	}

	public String getPortletLongTitle(String portletId, User user) {
		return getWrapped().getPortletLongTitle(portletId, user);
	}

	public String getPortletLongTitle(Portlet portlet, ServletContext servletContext, Locale locale) {
		return getWrapped().getPortletLongTitle(portlet, servletContext, locale);
	}

	public String getPortletNamespace(String portletId) {
		return getWrapped().getPortletNamespace(portletId);
	}

	public String getPortletTitle(RenderResponse renderResponse) {
		return getWrapped().getPortletTitle(renderResponse);
	}

	public String getPortletTitle(Portlet portlet, Locale locale) {
		return getWrapped().getPortletTitle(portlet, locale);
	}

	public String getPortletTitle(Portlet portlet, String languageId) {
		return getWrapped().getPortletTitle(portlet, languageId);
	}

	public String getPortletTitle(Portlet portlet, User user) {
		return getWrapped().getPortletTitle(portlet, user);
	}

	public String getPortletTitle(String portletId, Locale locale) {
		return getWrapped().getPortletTitle(portletId, locale);
	}

	public String getPortletTitle(String portletId, String languageId) {
		return getWrapped().getPortletTitle(portletId, languageId);
	}

	public String getPortletTitle(String portletId, User user) {
		return getWrapped().getPortletTitle(portletId, user);
	}

	public String getPortletTitle(Portlet portlet, ServletContext servletContext, Locale locale) {
		return getWrapped().getPortletTitle(portlet, servletContext, locale);
	}

	public String getPortletXmlFileName() throws SystemException {
		return getWrapped().getPortletXmlFileName();
	}

	public PortletPreferences getPreferences(HttpServletRequest request) {
		return getWrapped().getPreferences(request);
	}

	public PreferencesValidator getPreferencesValidator(Portlet portlet) {
		return getWrapped().getPreferencesValidator(portlet);
	}

	@SuppressWarnings("deprecation")
	public boolean isCommunityOwner(User user, long groupId) throws Exception {
		return getWrapped().isCommunityOwner(user, groupId);
	}

	public boolean isGroupOwner(User user, long groupId) throws Exception {
		return getWrapped().isGroupOwner(user, groupId);
	}

	public boolean isReservedParameter(String name) {
		return getWrapped().isReservedParameter(name);
	}

	public String getRelativeHomeURL(HttpServletRequest request) throws PortalException, SystemException {
		return getWrapped().getRelativeHomeURL(request);
	}

	public long getScopeGroupId(HttpServletRequest request) throws PortalException, SystemException {
		return getWrapped().getScopeGroupId(request);
	}

	public long getScopeGroupId(Layout layout) {
		return getWrapped().getScopeGroupId(layout);
	}

	public long getScopeGroupId(long plid) {
		return getWrapped().getScopeGroupId(plid);
	}

	public long getScopeGroupId(PortletRequest portletRequest) throws PortalException, SystemException {
		return getWrapped().getScopeGroupId(portletRequest);
	}

	public long getScopeGroupId(HttpServletRequest request, String portletId) throws PortalException, SystemException {
		return getWrapped().getScopeGroupId(request, portletId);
	}

	public long getScopeGroupId(Layout layout, String portletId) {
		return getWrapped().getScopeGroupId(layout, portletId);
	}

	public long getScopeGroupId(HttpServletRequest request, String portletId, boolean checkStagingGroup)
		throws PortalException, SystemException {
		return getWrapped().getScopeGroupId(request, portletId, checkStagingGroup);
	}

	public User getSelectedUser(HttpServletRequest request) throws PortalException, SystemException {
		return getWrapped().getSelectedUser(request);
	}

	public User getSelectedUser(PortletRequest portletRequest) throws PortalException, SystemException {
		return getWrapped().getSelectedUser(portletRequest);
	}

	public User getSelectedUser(HttpServletRequest request, boolean checkPermission) throws PortalException,
		SystemException {
		return getWrapped().getSelectedUser(request, checkPermission);
	}

	public User getSelectedUser(PortletRequest portletRequest, boolean checkPermission) throws PortalException,
		SystemException {
		return getWrapped().getSelectedUser(portletRequest, checkPermission);
	}

	public ServletContext getServletContext(Portlet portlet, ServletContext servletContext) {
		return getWrapped().getServletContext(portlet, servletContext);
	}

	public String getSiteLoginURL(ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return getWrapped().getSiteLoginURL(themeDisplay);
	}

	public String getStaticResourceURL(HttpServletRequest request, String uri) {
		return getWrapped().getStaticResourceURL(request, uri);
	}

	public String getStaticResourceURL(HttpServletRequest request, String uri, long timestamp) {
		return getWrapped().getStaticResourceURL(request, uri, timestamp);
	}

	public String getStaticResourceURL(HttpServletRequest request, String uri, String queryString) {
		return getWrapped().getStaticResourceURL(request, uri, queryString);
	}

	public String getStaticResourceURL(HttpServletRequest request, String uri, String queryString, long timestamp) {
		return getWrapped().getStaticResourceURL(request, uri, queryString, timestamp);
	}

	public String getStrutsAction(HttpServletRequest request) {
		return getWrapped().getStrutsAction(request);
	}

	public String[] getSystemGroups() {
		return getWrapped().getSystemGroups();
	}

	public String[] getSystemOrganizationRoles() {
		return getWrapped().getSystemOrganizationRoles();
	}

	public String[] getSystemRoles() {
		return getWrapped().getSystemRoles();
	}

	public String[] getSystemSiteRoles() {
		return getWrapped().getSystemSiteRoles();
	}

	public boolean isCompanyControlPanelPortlet(String portletId, ThemeDisplay themeDisplay) throws PortalException,
		SystemException {
		return getWrapped().isCompanyControlPanelPortlet(portletId, themeDisplay);
	}

	public boolean isCompanyControlPanelPortlet(String portletId, String category, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {
		return getWrapped().isCompanyControlPanelPortlet(portletId, category, themeDisplay);
	}

	public boolean isControlPanelPortlet(String portletId, ThemeDisplay themeDisplay) throws SystemException {
		return getWrapped().isControlPanelPortlet(portletId, themeDisplay);
	}

	public boolean isControlPanelPortlet(String portletId, String category, ThemeDisplay themeDisplay)
		throws SystemException {
		return getWrapped().isControlPanelPortlet(portletId, category, themeDisplay);
	}

	public boolean isLayoutDescendant(Layout layout, long layoutId) throws PortalException, SystemException {
		return getWrapped().isLayoutDescendant(layout, layoutId);
	}

	public boolean isMethodGet(PortletRequest portletRequest) {
		return getWrapped().isMethodGet(portletRequest);
	}

	public boolean isMethodPost(PortletRequest portletRequest) {
		return getWrapped().isMethodPost(portletRequest);
	}

	public boolean isMultipartRequest(HttpServletRequest request) {
		return getWrapped().isMultipartRequest(request);
	}

	public UploadPortletRequest getUploadPortletRequest(PortletRequest portletRequest) {
		return getWrapped().getUploadPortletRequest(portletRequest);
	}

	public UploadServletRequest getUploadServletRequest(HttpServletRequest request) {
		return getWrapped().getUploadServletRequest(request);
	}

	public Date getUptime() {
		return getWrapped().getUptime();
	}

	public String getURLWithSessionId(String url, String sessionId) {
		return getWrapped().getURLWithSessionId(url, sessionId);
	}

	public User getUser(HttpServletRequest request) throws PortalException, SystemException {
		return getWrapped().getUser(request);
	}

	public User getUser(PortletRequest portletRequest) throws PortalException, SystemException {
		return getWrapped().getUser(portletRequest);
	}

	public String getUserEmailAddress(long userId) throws SystemException {
		return getWrapped().getUserEmailAddress(userId);
	}

	public long getUserId(HttpServletRequest request) {
		return getWrapped().getUserId(request);
	}

	public long getUserId(PortletRequest portletRequest) {
		return getWrapped().getUserId(portletRequest);
	}

	public String getUserName(long userId, String defaultUserName) {
		return getWrapped().getUserName(userId, defaultUserName);
	}

	public String getUserName(long userId, String defaultUserName, HttpServletRequest request) {
		return getWrapped().getUserName(userId, defaultUserName, request);
	}

	public String getUserName(long userId, String defaultUserName, String userAttribute) {
		return getWrapped().getUserName(userId, defaultUserName, userAttribute);
	}

	public String getUserName(long userId, String defaultUserName, String userAttribute, HttpServletRequest request) {
		return getWrapped().getUserName(userId, defaultUserName, userAttribute, request);
	}

	public String getUserPassword(HttpServletRequest request) {
		return getWrapped().getUserPassword(request);
	}

	public String getUserPassword(HttpSession session) {
		return getWrapped().getUserPassword(session);
	}

	public String getUserPassword(PortletRequest portletRequest) {
		return getWrapped().getUserPassword(portletRequest);
	}

	public String getUserValue(long userId, String param, String defaultValue) throws SystemException {
		return getWrapped().getUserValue(userId, param, defaultValue);
	}

	public long getValidUserId(long companyId, long userId) throws PortalException, SystemException {
		return getWrapped().getValidUserId(companyId, userId);
	}

	public String getVirtualLayoutActualURL(long groupId, boolean privateLayout, String mainPath, String friendlyURL,
		Map<String, String[]> params, Map<String, Object> requestContext) throws PortalException, SystemException {
		return getWrapped().getVirtualLayoutActualURL(groupId, privateLayout, mainPath, friendlyURL, params,
				requestContext);
	}

	public String getWidgetURL(Portlet portlet, ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return getWrapped().getWidgetURL(portlet, themeDisplay);
	}

	public abstract Portal getWrapped();
}
