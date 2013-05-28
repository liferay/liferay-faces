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
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
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
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalPortEventListener;

import com.liferay.portlet.expando.model.ExpandoBridge;


/**
 * @author  Neil Griffin
 */
public class PortalWrapper implements Portal {

	private Portal _portal;

	public PortalWrapper(Portal portal) {
		_portal = portal;
	}

	public void addPageDescription(String description, HttpServletRequest request) {
		_portal.addPageDescription(description, request);
	}

	public void addPageKeywords(String keywords, HttpServletRequest request) {
		_portal.addPageKeywords(keywords, request);
	}

	public void addPageSubtitle(String subtitle, HttpServletRequest request) {
		_portal.addPageSubtitle(subtitle, request);
	}

	public void addPageTitle(String title, HttpServletRequest request) {
		_portal.addPageTitle(title, request);
	}

	public void addPortalPortEventListener(PortalPortEventListener portalPortEventListener) {
		_portal.addPortalPortEventListener(portalPortEventListener);
	}

	public void addPortletBreadcrumbEntry(HttpServletRequest request, String title, String url) {
		_portal.addPortletBreadcrumbEntry(request, title, url);
	}

	public void addPortletBreadcrumbEntry(HttpServletRequest request, String title, String url,
		Map<String, Object> data) {
		_portal.addPortletBreadcrumbEntry(request, title, url, data);
	}

	public void addPortletDefaultResource(HttpServletRequest request, Portlet portlet) throws PortalException,
		SystemException {
		_portal.addPortletDefaultResource(request, portlet);
	}

	public void addPortletDefaultResource(long companyId, Layout layout, Portlet portlet) throws PortalException,
		SystemException {
		_portal.addPortletDefaultResource(companyId, layout, portlet);
	}

	public String addPreservedParameters(ThemeDisplay themeDisplay, String url) {
		return _portal.addPreservedParameters(themeDisplay, url);
	}

	public String addPreservedParameters(ThemeDisplay themeDisplay, Layout layout, String url, boolean doAsUser) {
		return _portal.addPreservedParameters(themeDisplay, layout, url, doAsUser);
	}

	public void clearRequestParameters(RenderRequest renderRequest) {
		_portal.clearRequestParameters(renderRequest);
	}

	public void copyRequestParameters(ActionRequest actionRequest, ActionResponse actionResponse) {
		_portal.copyRequestParameters(actionRequest, actionResponse);
	}

	public String escapeRedirect(String url) {
		return _portal.escapeRedirect(url);
	}

	public String generateRandomKey(HttpServletRequest request, String input) {
		return _portal.generateRandomKey(request, input);
	}

	public void initCustomSQL() {
		_portal.initCustomSQL();
	}

	@Override
	public User initUser(HttpServletRequest request) throws Exception {
		return _portal.initUser(request);
	}

	@Override
	public void invokeTaglibDiscussion(PortletConfig portletConfig, ActionRequest actionRequest,
		ActionResponse actionResponse) throws Exception {
		_portal.invokeTaglibDiscussion(portletConfig, actionRequest, actionResponse);
	}

	public void removePortalPortEventListener(PortalPortEventListener portalPortEventListener) {
		_portal.removePortalPortEventListener(portalPortEventListener);
	}

	public void resetCDNHosts() {
		_portal.resetCDNHosts();
	}

	public Set<String> resetPortletAddDefaultResourceCheckWhitelist() {
		return _portal.resetPortletAddDefaultResourceCheckWhitelist();
	}

	public Set<String> resetPortletAddDefaultResourceCheckWhitelistActions() {
		return _portal.resetPortletAddDefaultResourceCheckWhitelistActions();
	}

	public void sendError(Exception e, ActionRequest actionRequest, ActionResponse actionResponse) throws IOException {
		_portal.sendError(e, actionRequest, actionResponse);
	}

	public void sendError(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException,
		ServletException {
		_portal.sendError(e, request, response);
	}

	public void sendError(int status, Exception e, ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {
		_portal.sendError(status, e, actionRequest, actionResponse);
	}

	public void sendError(int status, Exception e, HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		_portal.sendError(status, e, request, response);
	}

	@Override
	public void sendRSSFeedsDisabledError(HttpServletRequest request, HttpServletResponse response) throws IOException,
		ServletException {
		_portal.sendRSSFeedsDisabledError(request, response);
	}

	@Override
	public void sendRSSFeedsDisabledError(PortletRequest request, PortletResponse response) throws IOException,
		ServletException {
		_portal.sendRSSFeedsDisabledError(request, response);
	}

	public void storePreferences(PortletPreferences portletPreferences) throws IOException, ValidatorException {
		_portal.storePreferences(portletPreferences);
	}

	public String[] stripURLAnchor(String url, String separator) {
		return _portal.stripURLAnchor(url, separator);
	}

	public String transformCustomSQL(String sql) {
		return _portal.transformCustomSQL(sql);
	}

	@Override
	public String transformSQL(String sql) {
		return _portal.transformSQL(sql);
	}

	public PortletMode updatePortletMode(String portletId, User user, Layout layout, PortletMode portletMode,
		HttpServletRequest request) {
		return _portal.updatePortletMode(portletId, user, layout, portletMode, request);
	}

	public String updateRedirect(String redirect, String oldPath, String newPath) {
		return _portal.updateRedirect(redirect, oldPath, newPath);
	}

	public WindowState updateWindowState(String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest request) {
		return _portal.updateWindowState(portletId, user, layout, windowState, request);
	}

	public String getActualURL(long groupId, boolean privateLayout, String mainPath, String friendlyURL,
		Map<String, String[]> params, Map<String, Object> requestContext) throws PortalException, SystemException {
		return _portal.getActualURL(groupId, privateLayout, mainPath, friendlyURL, params, requestContext);
	}

	public Locale[] getAlternateLocales(HttpServletRequest request) throws PortalException, SystemException {
		return _portal.getAlternateLocales(request);
	}

	public String getAlternateURL(String canonicalURL, ThemeDisplay themeDisplay, Locale locale) {
		return _portal.getAlternateURL(canonicalURL, themeDisplay, locale);
	}

	public Set<String> getAuthTokenIgnoreActions() {
		return _portal.getAuthTokenIgnoreActions();
	}

	public Set<String> getAuthTokenIgnorePortlets() {
		return _portal.getAuthTokenIgnorePortlets();
	}

	public BaseModel<?> getBaseModel(ResourcePermission resourcePermission) throws PortalException, SystemException {
		return _portal.getBaseModel(resourcePermission);
	}

	public BaseModel<?> getBaseModel(String modelName, String primKey) throws PortalException, SystemException {
		return _portal.getBaseModel(modelName, primKey);
	}

	public long getBasicAuthUserId(HttpServletRequest request) throws PortalException, SystemException {
		return _portal.getBasicAuthUserId(request);
	}

	public long getBasicAuthUserId(HttpServletRequest request, long companyId) throws PortalException, SystemException {
		return _portal.getBasicAuthUserId(request, companyId);
	}

	public String getCanonicalURL(String completeURL, ThemeDisplay themeDisplay, Layout layout) throws PortalException,
		SystemException {
		return _portal.getCanonicalURL(completeURL, themeDisplay, layout);
	}

	@Override
	public String getCanonicalURL(String completeURL, ThemeDisplay themeDisplay, Layout layout,
		boolean forceLayoutFriendlyURL) throws PortalException, SystemException {
		return _portal.getCanonicalURL(completeURL, themeDisplay, layout, forceLayoutFriendlyURL);
	}

	@SuppressWarnings("deprecation")
	public String getCDNHost() {
		return _portal.getCDNHost();
	}

	public String getCDNHost(boolean secure) {
		return _portal.getCDNHost(secure);
	}

	public String getCDNHost(HttpServletRequest request) throws PortalException, SystemException {
		return _portal.getCDNHost(request);
	}

	public String getCDNHostHttp(long companyId) {
		return _portal.getCDNHostHttp(companyId);
	}

	public String getCDNHostHttps(long companyId) {
		return _portal.getCDNHostHttps(companyId);
	}

	public String getClassName(long classNameId) {
		return _portal.getClassName(classNameId);
	}

	public long getClassNameId(Class<?> clazz) {
		return _portal.getClassNameId(clazz);
	}

	public long getClassNameId(String value) {
		return _portal.getClassNameId(value);
	}

	public String getClassNamePortletId(String className) {
		return _portal.getClassNamePortletId(className);
	}

	public Company getCompany(HttpServletRequest request) throws PortalException, SystemException {
		return _portal.getCompany(request);
	}

	public Company getCompany(PortletRequest portletRequest) throws PortalException, SystemException {
		return _portal.getCompany(portletRequest);
	}

	public long getCompanyId(HttpServletRequest requestuest) {
		return _portal.getCompanyId(requestuest);
	}

	public long getCompanyId(PortletRequest portletRequest) {
		return _portal.getCompanyId(portletRequest);
	}

	public long[] getCompanyIds() {
		return _portal.getCompanyIds();
	}

	public String getComputerAddress() {
		return _portal.getComputerAddress();
	}

	public String getComputerName() {
		return _portal.getComputerName();
	}

	public String getControlPanelCategory(String portletId, ThemeDisplay themeDisplay) throws SystemException {
		return _portal.getControlPanelCategory(portletId, themeDisplay);
	}

	public String getControlPanelFullURL(long scopeGroupId, String ppid, Map<String, String[]> params)
		throws PortalException, SystemException {
		return _portal.getControlPanelFullURL(scopeGroupId, ppid, params);
	}

	@Override
	public long getControlPanelPlid(long companyId) throws PortalException, SystemException {
		return _portal.getControlPanelPlid(companyId);
	}

	@Override
	public long getControlPanelPlid(PortletRequest portletRequest) throws PortalException, SystemException {
		return _portal.getControlPanelPlid(portletRequest);
	}

	public Set<Portlet> getControlPanelPortlets(long companyId, String category) throws SystemException {
		return _portal.getControlPanelPortlets(companyId, category);
	}

	public List<Portlet> getControlPanelPortlets(String category, ThemeDisplay themeDisplay) throws SystemException {
		return _portal.getControlPanelPortlets(category, themeDisplay);
	}

	@Override
	public PortletURL getControlPanelPortletURL(HttpServletRequest request, String portletId, long referrerPlid,
		String lifecycle) {
		return _portal.getControlPanelPortletURL(request, portletId, referrerPlid, lifecycle);
	}

	@Override
	public PortletURL getControlPanelPortletURL(PortletRequest portletRequest, String portletId, long referrerPlid,
		String lifecycle) {
		return _portal.getControlPanelPortletURL(portletRequest, portletId, referrerPlid, lifecycle);
	}

	public String getCreateAccountURL(HttpServletRequest request, ThemeDisplay themeDisplay) throws Exception {
		return _portal.getCreateAccountURL(request, themeDisplay);
	}

	public String getCurrentCompleteURL(HttpServletRequest request) {
		return _portal.getCurrentCompleteURL(request);
	}

	public String getCurrentURL(HttpServletRequest request) {
		return _portal.getCurrentURL(request);
	}

	public String getCurrentURL(PortletRequest portletRequest) {
		return _portal.getCurrentURL(portletRequest);
	}

	public String getCustomSQLFunctionIsNotNull() {
		return _portal.getCustomSQLFunctionIsNotNull();
	}

	public String getCustomSQLFunctionIsNull() {
		return _portal.getCustomSQLFunctionIsNull();
	}

	public boolean isCDNDynamicResourcesEnabled(HttpServletRequest request) throws PortalException, SystemException {
		return _portal.isCDNDynamicResourcesEnabled(request);
	}

	public boolean isCDNDynamicResourcesEnabled(long companyId) {
		return _portal.isCDNDynamicResourcesEnabled(companyId);
	}

	@Override
	public boolean isRSSFeedsEnabled() {
		return _portal.isRSSFeedsEnabled();
	}

	public boolean isValidResourceId(String resourceId) {
		return _portal.isValidResourceId(resourceId);
	}

	public Date getDate(int month, int day, int year) {
		return _portal.getDate(month, day, year);
	}

	public Date getDate(int month, int day, int year, Class<? extends PortalException> clazz) throws PortalException {
		return _portal.getDate(month, day, year, clazz);
	}

	public Date getDate(int month, int day, int year, TimeZone timeZone, Class<? extends PortalException> clazz)
		throws PortalException {
		return _portal.getDate(month, day, year, timeZone, clazz);
	}

	public Date getDate(int month, int day, int year, int hour, int min, Class<? extends PortalException> clazz)
		throws PortalException {
		return _portal.getDate(month, day, year, hour, min, clazz);
	}

	public Date getDate(int month, int day, int year, int hour, int min, TimeZone timeZone,
		Class<? extends PortalException> clazz) throws PortalException {
		return _portal.getDate(month, day, year, hour, min, timeZone, clazz);
	}

	public long getDefaultCompanyId() {
		return _portal.getDefaultCompanyId();
	}

	public long getDigestAuthUserId(HttpServletRequest request) throws PortalException, SystemException {
		return _portal.getDigestAuthUserId(request);
	}

	public boolean isAllowAddPortletDefaultResource(HttpServletRequest request, Portlet portlet) throws PortalException,
		SystemException {
		return _portal.isAllowAddPortletDefaultResource(request, portlet);
	}

	public boolean isCompanyControlPanelVisible(ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return _portal.isCompanyControlPanelVisible(themeDisplay);
	}

	public boolean isLayoutFirstPageable(Layout layout) {
		return _portal.isLayoutFirstPageable(layout);
	}

	public boolean isLayoutFirstPageable(String type) {
		return _portal.isLayoutFirstPageable(type);
	}

	public boolean isLayoutFriendliable(Layout layout) {
		return _portal.isLayoutFriendliable(layout);
	}

	public boolean isLayoutFriendliable(String type) {
		return _portal.isLayoutFriendliable(type);
	}

	public boolean isLayoutParentable(Layout layout) {
		return _portal.isLayoutParentable(layout);
	}

	public boolean isLayoutParentable(String type) {
		return _portal.isLayoutParentable(type);
	}

	public boolean isLayoutSitemapable(Layout layout) {
		return _portal.isLayoutSitemapable(layout);
	}

	public boolean isSecure(HttpServletRequest request) {
		return _portal.isSecure(request);
	}

	public boolean isSystemRole(String roleName) {
		return _portal.isSystemRole(roleName);
	}

	public boolean isUpdateAvailable() throws SystemException {
		return _portal.isUpdateAvailable();
	}

	public String getEmailFromAddress(PortletPreferences preferences, long companyId, String defaultValue)
		throws SystemException {
		return _portal.getEmailFromAddress(preferences, companyId, defaultValue);
	}

	public String getEmailFromName(PortletPreferences preferences, long companyId, String defaultValue)
		throws SystemException {
		return _portal.getEmailFromName(preferences, companyId, defaultValue);
	}

	public Map<String, Serializable> getExpandoBridgeAttributes(ExpandoBridge expandoBridge,
		PortletRequest portletRequest) throws PortalException, SystemException {
		return _portal.getExpandoBridgeAttributes(expandoBridge, portletRequest);
	}

	public Map<String, Serializable> getExpandoBridgeAttributes(ExpandoBridge expandoBridge,
		UploadPortletRequest uploadPortletRequest) throws PortalException, SystemException {
		return _portal.getExpandoBridgeAttributes(expandoBridge, uploadPortletRequest);
	}

	public Serializable getExpandoValue(PortletRequest portletRequest, String name, int type, String displayType)
		throws PortalException, SystemException {
		return _portal.getExpandoValue(portletRequest, name, type, displayType);
	}

	public Serializable getExpandoValue(UploadPortletRequest uploadPortletRequest, String name, int type,
		String displayType) throws PortalException, SystemException {
		return _portal.getExpandoValue(uploadPortletRequest, name, type, displayType);
	}

	public String getFacebookURL(Portlet portlet, String facebookCanvasPageURL, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {
		return _portal.getFacebookURL(portlet, facebookCanvasPageURL, themeDisplay);
	}

	public String getFirstPageLayoutTypes(PageContext pageContext) {
		return _portal.getFirstPageLayoutTypes(pageContext);
	}

	public Portlet getFirstSiteAdministrationPortlet(ThemeDisplay themeDisplay) throws SystemException {
		return _portal.getFirstSiteAdministrationPortlet(themeDisplay);
	}

	@Override
	public String getFullName(String firstName, String middleName, String lastName) {
		return _portal.getFullName(firstName, middleName, lastName);
	}

	public String getGlobalLibDir() {
		return _portal.getGlobalLibDir();
	}

	public String getGoogleGadgetURL(Portlet portlet, ThemeDisplay themeDisplay) throws PortalException,
		SystemException {
		return _portal.getGoogleGadgetURL(portlet, themeDisplay);
	}

	public String getGroupFriendlyURL(Group group, boolean privateLayoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {
		return _portal.getGroupFriendlyURL(group, privateLayoutSet, themeDisplay);
	}

	@Override
	public String getGroupFriendlyURL(Group group, boolean privateLayoutSet, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException, SystemException {
		return _portal.getGroupFriendlyURL(group, privateLayoutSet, themeDisplay, locale);
	}

	public String[] getGroupPermissions(HttpServletRequest request) {
		return _portal.getGroupPermissions(request);
	}

	public String[] getGroupPermissions(PortletRequest portletRequest) {
		return _portal.getGroupPermissions(portletRequest);
	}

	@Override
	public String[] getGroupPermissions(HttpServletRequest request, String className) {
		return _portal.getGroupPermissions(request, className);
	}

	@Override
	public String[] getGroupPermissions(PortletRequest portletRequest, String className) {
		return _portal.getGroupPermissions(portletRequest, className);
	}

	public String[] getGuestPermissions(HttpServletRequest request) {
		return _portal.getGuestPermissions(request);
	}

	public String[] getGuestPermissions(PortletRequest portletRequest) {
		return _portal.getGuestPermissions(portletRequest);
	}

	@Override
	public String[] getGuestPermissions(HttpServletRequest request, String className) {
		return _portal.getGuestPermissions(request, className);
	}

	@Override
	public String[] getGuestPermissions(PortletRequest portletRequest, String className) {
		return _portal.getGuestPermissions(portletRequest, className);
	}

	public String getHomeURL(HttpServletRequest request) throws PortalException, SystemException {
		return _portal.getHomeURL(request);
	}

	public String getHost(HttpServletRequest request) {
		return _portal.getHost(request);
	}

	public String getHost(PortletRequest portletRequest) {
		return _portal.getHost(portletRequest);
	}

	public HttpServletRequest getHttpServletRequest(PortletRequest portletRequest) {
		return _portal.getHttpServletRequest(portletRequest);
	}

	public HttpServletResponse getHttpServletResponse(PortletResponse portletResponse) {
		return _portal.getHttpServletResponse(portletResponse);
	}

	@Override
	public String getJournalArticleActualURL(long groupId, boolean privateLayout, String mainPath, String friendlyURL,
		Map<String, String[]> params, Map<String, Object> requestContext) throws PortalException, SystemException {
		return _portal.getJournalArticleActualURL(groupId, privateLayout, mainPath, friendlyURL, params,
				requestContext);
	}

	public String getJsSafePortletId(String portletId) {
		return _portal.getJsSafePortletId(portletId);
	}

	@Override
	public boolean isGroupFriendlyURL(String fullURL, String groupFriendlyURL, String layoutFriendlyURL) {
		return _portal.isGroupFriendlyURL(fullURL, groupFriendlyURL, layoutFriendlyURL);
	}

	public String getLayoutActualURL(Layout layout) {
		return _portal.getLayoutActualURL(layout);
	}

	public String getLayoutActualURL(Layout layout, String mainPath) {
		return _portal.getLayoutActualURL(layout, mainPath);
	}

	public String getLayoutActualURL(long groupId, boolean privateLayout, String mainPath, String friendlyURL)
		throws PortalException, SystemException {
		return _portal.getLayoutActualURL(groupId, privateLayout, mainPath, friendlyURL);
	}

	public String getLayoutActualURL(long groupId, boolean privateLayout, String mainPath, String friendlyURL,
		Map<String, String[]> params, Map<String, Object> requestContext) throws PortalException, SystemException {
		return _portal.getLayoutActualURL(groupId, privateLayout, mainPath, friendlyURL, params, requestContext);
	}

	public String getLayoutEditPage(Layout layout) {
		return _portal.getLayoutEditPage(layout);
	}

	public String getLayoutEditPage(String type) {
		return _portal.getLayoutEditPage(type);
	}

	public String getLayoutFriendlyURL(Layout layout, ThemeDisplay themeDisplay) throws PortalException,
		SystemException {
		return _portal.getLayoutFriendlyURL(layout, themeDisplay);
	}

	public String getLayoutFriendlyURL(Layout layout, ThemeDisplay themeDisplay, Locale locale) throws PortalException,
		SystemException {
		return _portal.getLayoutFriendlyURL(layout, themeDisplay, locale);
	}

	public String getLayoutFullURL(ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return _portal.getLayoutFullURL(themeDisplay);
	}

	public String getLayoutFullURL(Layout layout, ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return _portal.getLayoutFullURL(layout, themeDisplay);
	}

	public String getLayoutFullURL(long groupId, String portletId) throws PortalException, SystemException {
		return _portal.getLayoutFullURL(groupId, portletId);
	}

	public String getLayoutFullURL(Layout layout, ThemeDisplay themeDisplay, boolean doAsUser) throws PortalException,
		SystemException {
		return _portal.getLayoutFullURL(layout, themeDisplay, doAsUser);
	}

	public String getLayoutFullURL(long groupId, String portletId, boolean secure) throws PortalException,
		SystemException {
		return _portal.getLayoutFullURL(groupId, portletId, secure);
	}

	public String getLayoutSetFriendlyURL(LayoutSet layoutSet, ThemeDisplay themeDisplay) throws PortalException,
		SystemException {
		return _portal.getLayoutSetFriendlyURL(layoutSet, themeDisplay);
	}

	public String getLayoutTarget(Layout layout) {
		return _portal.getLayoutTarget(layout);
	}

	public String getLayoutURL(ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return _portal.getLayoutURL(themeDisplay);
	}

	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return _portal.getLayoutURL(layout, themeDisplay);
	}

	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay, boolean doAsUser) throws PortalException,
		SystemException {
		return _portal.getLayoutURL(layout, themeDisplay, doAsUser);
	}

	public String getLayoutViewPage(Layout layout) {
		return _portal.getLayoutViewPage(layout);
	}

	public String getLayoutViewPage(String type) {
		return _portal.getLayoutViewPage(type);
	}

	public LiferayPortletRequest getLiferayPortletRequest(PortletRequest portletRequest) {
		return _portal.getLiferayPortletRequest(portletRequest);
	}

	public LiferayPortletResponse getLiferayPortletResponse(PortletResponse portletResponse) {
		return _portal.getLiferayPortletResponse(portletResponse);
	}

	public Locale getLocale(HttpServletRequest request) {
		return _portal.getLocale(request);
	}

	public Locale getLocale(RenderRequest renderRequest) {
		return _portal.getLocale(renderRequest);
	}

	@Override
	public Locale getLocale(HttpServletRequest request, HttpServletResponse response, boolean initialize) {
		return _portal.getLocale(request, response, initialize);
	}

	public String getMailId(String mx, String popPortletPrefix, Object... ids) {
		return _portal.getMailId(mx, popPortletPrefix, ids);
	}

	@SuppressWarnings("deprecation")
	public boolean isCommunityAdmin(User user, long groupId) throws Exception {
		return _portal.isCommunityAdmin(user, groupId);
	}

	public boolean isCompanyAdmin(User user) throws Exception {
		return _portal.isCompanyAdmin(user);
	}

	public boolean isGroupAdmin(User user, long groupId) throws Exception {
		return _portal.isGroupAdmin(user, groupId);
	}

	public boolean isOmniadmin(long userId) {
		return _portal.isOmniadmin(userId);
	}

	public String getNetvibesURL(Portlet portlet, ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return _portal.getNetvibesURL(portlet, themeDisplay);
	}

	public String getNewPortletTitle(String portletTitle, String oldScopeName, String newScopeName) {
		return _portal.getNewPortletTitle(portletTitle, oldScopeName, newScopeName);
	}

	public HttpServletRequest getOriginalServletRequest(HttpServletRequest request) {
		return _portal.getOriginalServletRequest(request);
	}

	public boolean isSystemGroup(String groupName) {
		return _portal.isSystemGroup(groupName);
	}

	public void setPageDescription(String description, HttpServletRequest request) {
		_portal.setPageDescription(description, request);
	}

	public void setPageKeywords(String keywords, HttpServletRequest request) {
		_portal.setPageKeywords(keywords, request);
	}

	public void setPageSubtitle(String subtitle, HttpServletRequest request) {
		_portal.setPageSubtitle(subtitle, request);
	}

	public void setPageTitle(String title, HttpServletRequest request) {
		_portal.setPageTitle(title, request);
	}

	@SuppressWarnings("deprecation")
	public long getParentGroupId(long scopeGroupId) throws PortalException, SystemException {
		return _portal.getParentGroupId(scopeGroupId);
	}

	public String getPathContext() {
		return _portal.getPathContext();
	}

	public String getPathFriendlyURLPrivateGroup() {
		return _portal.getPathFriendlyURLPrivateGroup();
	}

	public String getPathFriendlyURLPrivateUser() {
		return _portal.getPathFriendlyURLPrivateUser();
	}

	public String getPathFriendlyURLPublic() {
		return _portal.getPathFriendlyURLPublic();
	}

	public String getPathImage() {
		return _portal.getPathImage();
	}

	public String getPathMain() {
		return _portal.getPathMain();
	}

	@Override
	public String getPathModule() {
		return _portal.getPathModule();
	}

	public String getPathProxy() {
		return _portal.getPathProxy();
	}

	public long getPlidFromFriendlyURL(long companyId, String friendlyURL) {
		return _portal.getPlidFromFriendlyURL(companyId, friendlyURL);
	}

	public long getPlidFromPortletId(long groupId, String portletId) throws PortalException, SystemException {
		return _portal.getPlidFromPortletId(groupId, portletId);
	}

	public long getPlidFromPortletId(long groupId, boolean privateLayout, String portletId) throws PortalException,
		SystemException {
		return _portal.getPlidFromPortletId(groupId, privateLayout, portletId);
	}

	public String getPortalLibDir() {
		return _portal.getPortalLibDir();
	}

	@SuppressWarnings("deprecation")
	public int getPortalPort() {
		return _portal.getPortalPort();
	}

	@SuppressWarnings("deprecation")
	public int getPortalPort(boolean secure) {
		return _portal.getPortalPort();
	}

	public void setPortalPort(HttpServletRequest request) {
		_portal.setPortalPort(request);
	}

	public Properties getPortalProperties() {
		return _portal.getPortalProperties();
	}

	public String getPortalURL(HttpServletRequest request) {
		return _portal.getPortalURL(request);
	}

	public String getPortalURL(PortletRequest portletRequest) {
		return _portal.getPortalURL(portletRequest);
	}

	public String getPortalURL(ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return _portal.getPortalURL(themeDisplay);
	}

	public String getPortalURL(HttpServletRequest request, boolean secure) {
		return _portal.getPortalURL(request, secure);
	}

	public String getPortalURL(Layout layout, ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return _portal.getPortalURL(layout, themeDisplay);
	}

	public String getPortalURL(PortletRequest portletRequest, boolean secure) {
		return _portal.getPortalURL(portletRequest, secure);
	}

	public String getPortalURL(String serverName, int serverPort, boolean secure) {
		return _portal.getPortalURL(serverName, serverPort, secure);
	}

	public String getPortalWebDir() {
		return _portal.getPortalWebDir();
	}

	public Set<String> getPortletAddDefaultResourceCheckWhitelist() {
		return _portal.getPortletAddDefaultResourceCheckWhitelist();
	}

	public Set<String> getPortletAddDefaultResourceCheckWhitelistActions() {
		return _portal.getPortletAddDefaultResourceCheckWhitelistActions();
	}

	@SuppressWarnings("deprecation")
	public List<BreadcrumbEntry> getPortletBreadcrumbList(HttpServletRequest request) {
		return _portal.getPortletBreadcrumbList(request);
	}

	public List<BreadcrumbEntry> getPortletBreadcrumbs(HttpServletRequest request) {
		return _portal.getPortletBreadcrumbs(request);
	}

	@Override
	public PortletConfig getPortletConfig(long companyId, String portletId, ServletContext servletContext)
		throws PortletException, SystemException {
		return getPortletConfig(companyId, portletId, servletContext);
	}

	public String getPortletDescription(Portlet portlet, User user) {
		return _portal.getPortletDescription(portlet, user);
	}

	public String getPortletDescription(String portletId, Locale locale) {
		return _portal.getPortletDescription(portletId, locale);
	}

	public String getPortletDescription(String portletId, String languageId) {
		return _portal.getPortletDescription(portletId, languageId);
	}

	public String getPortletDescription(String portletId, User user) {
		return _portal.getPortletDescription(portletId, user);
	}

	public String getPortletDescription(Portlet portlet, ServletContext servletContext, Locale locale) {
		return _portal.getPortletDescription(portlet, servletContext, locale);
	}

	public String getPortletId(HttpServletRequest request) {
		return _portal.getPortletId(request);
	}

	public String getPortletId(PortletRequest portletRequest) {
		return _portal.getPortletId(portletRequest);
	}

	public String getPortletLongTitle(Portlet portlet, Locale locale) {
		return _portal.getPortletLongTitle(portlet, locale);
	}

	public String getPortletLongTitle(Portlet portlet, String languageId) {
		return _portal.getPortletLongTitle(portlet, languageId);
	}

	public String getPortletLongTitle(Portlet portlet, User user) {
		return _portal.getPortletLongTitle(portlet, user);
	}

	public String getPortletLongTitle(String portletId, Locale locale) {
		return _portal.getPortletLongTitle(portletId, locale);
	}

	public String getPortletLongTitle(String portletId, String languageId) {
		return _portal.getPortletLongTitle(portletId, languageId);
	}

	public String getPortletLongTitle(String portletId, User user) {
		return _portal.getPortletLongTitle(portletId, user);
	}

	public String getPortletLongTitle(Portlet portlet, ServletContext servletContext, Locale locale) {
		return _portal.getPortletLongTitle(portlet, servletContext, locale);
	}

	public String getPortletNamespace(String portletId) {
		return _portal.getPortletNamespace(portletId);
	}

	@Override
	public String getPortletTitle(RenderRequest renderRequest) {
		return _portal.getPortletTitle(renderRequest);
	}

	public String getPortletTitle(RenderResponse renderResponse) {
		return _portal.getPortletTitle(renderResponse);
	}

	public String getPortletTitle(Portlet portlet, Locale locale) {
		return _portal.getPortletTitle(portlet, locale);
	}

	public String getPortletTitle(Portlet portlet, String languageId) {
		return _portal.getPortletTitle(portlet, languageId);
	}

	public String getPortletTitle(Portlet portlet, User user) {
		return _portal.getPortletTitle(portlet, user);
	}

	public String getPortletTitle(String portletId, Locale locale) {
		return _portal.getPortletTitle(portletId, locale);
	}

	public String getPortletTitle(String portletId, String languageId) {
		return _portal.getPortletTitle(portletId, languageId);
	}

	public String getPortletTitle(String portletId, User user) {
		return _portal.getPortletTitle(portletId, user);
	}

	public String getPortletTitle(Portlet portlet, ServletContext servletContext, Locale locale) {
		return _portal.getPortletTitle(portlet, servletContext, locale);
	}

	public String getPortletXmlFileName() throws SystemException {
		return _portal.getPortletXmlFileName();
	}

	public PortletPreferences getPreferences(HttpServletRequest request) {
		return _portal.getPreferences(request);
	}

	public PreferencesValidator getPreferencesValidator(Portlet portlet) {
		return _portal.getPreferencesValidator(portlet);
	}

	@SuppressWarnings("deprecation")
	public boolean isCommunityOwner(User user, long groupId) throws Exception {
		return _portal.isCommunityOwner(user, groupId);
	}

	public boolean isGroupOwner(User user, long groupId) throws Exception {
		return _portal.isGroupOwner(user, groupId);
	}

	public boolean isReservedParameter(String name) {
		return _portal.isReservedParameter(name);
	}

	public String getRelativeHomeURL(HttpServletRequest request) throws PortalException, SystemException {
		return _portal.getRelativeHomeURL(request);
	}

	public long getScopeGroupId(HttpServletRequest request) throws PortalException, SystemException {
		return _portal.getScopeGroupId(request);
	}

	public long getScopeGroupId(Layout layout) {
		return _portal.getScopeGroupId(layout);
	}

	public long getScopeGroupId(long plid) {
		return _portal.getScopeGroupId(plid);
	}

	public long getScopeGroupId(PortletRequest portletRequest) throws PortalException, SystemException {
		return _portal.getScopeGroupId(portletRequest);
	}

	public long getScopeGroupId(HttpServletRequest request, String portletId) throws PortalException, SystemException {
		return _portal.getScopeGroupId(request, portletId);
	}

	public long getScopeGroupId(Layout layout, String portletId) {
		return _portal.getScopeGroupId(layout, portletId);
	}

	public long getScopeGroupId(HttpServletRequest request, String portletId, boolean checkStagingGroup)
		throws PortalException, SystemException {
		return _portal.getScopeGroupId(request, portletId, checkStagingGroup);
	}

	public User getSelectedUser(HttpServletRequest request) throws PortalException, SystemException {
		return _portal.getSelectedUser(request);
	}

	public User getSelectedUser(PortletRequest portletRequest) throws PortalException, SystemException {
		return _portal.getSelectedUser(portletRequest);
	}

	public User getSelectedUser(HttpServletRequest request, boolean checkPermission) throws PortalException,
		SystemException {
		return _portal.getSelectedUser(request, checkPermission);
	}

	public User getSelectedUser(PortletRequest portletRequest, boolean checkPermission) throws PortalException,
		SystemException {
		return _portal.getSelectedUser(portletRequest, checkPermission);
	}

	public PortletURL getSiteAdministrationURL(PortletResponse portletResponse, ThemeDisplay themeDisplay)
		throws SystemException {
		return _portal.getSiteAdministrationURL(portletResponse, themeDisplay);
	}

	public PortletURL getSiteAdministrationURL(PortletResponse response, ThemeDisplay themeDisplay,
		String portletName) {
		return _portal.getSiteAdministrationURL(response, themeDisplay, portletName);
	}

	@Override
	public long[] getSiteAndCompanyGroupIds(long groupId) throws PortalException, SystemException {
		return _portal.getSiteAndCompanyGroupIds(groupId);
	}

	@Override
	public long[] getSiteAndCompanyGroupIds(ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return _portal.getSiteAndCompanyGroupIds(themeDisplay);
	}

	@Override
	public long getSiteGroupId(long groupId) throws PortalException, SystemException {
		return _portal.getSiteGroupId(groupId);
	}

	public String getSiteLoginURL(ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return _portal.getSiteLoginURL(themeDisplay);
	}

	public String getStaticResourceURL(HttpServletRequest request, String uri) {
		return _portal.getStaticResourceURL(request, uri);
	}

	public String getStaticResourceURL(HttpServletRequest request, String uri, long timestamp) {
		return _portal.getStaticResourceURL(request, uri, timestamp);
	}

	public String getStaticResourceURL(HttpServletRequest request, String uri, String queryString) {
		return _portal.getStaticResourceURL(request, uri, queryString);
	}

	public String getStaticResourceURL(HttpServletRequest request, String uri, String queryString, long timestamp) {
		return _portal.getStaticResourceURL(request, uri, queryString, timestamp);
	}

	public String getStrutsAction(HttpServletRequest request) {
		return _portal.getStrutsAction(request);
	}

	public String[] getSystemGroups() {
		return _portal.getSystemGroups();
	}

	public String[] getSystemOrganizationRoles() {
		return _portal.getSystemOrganizationRoles();
	}

	public String[] getSystemRoles() {
		return _portal.getSystemRoles();
	}

	public String[] getSystemSiteRoles() {
		return _portal.getSystemSiteRoles();
	}

	public boolean isCompanyControlPanelPortlet(String portletId, ThemeDisplay themeDisplay) throws PortalException,
		SystemException {
		return _portal.isCompanyControlPanelPortlet(portletId, themeDisplay);
	}

	public boolean isCompanyControlPanelPortlet(String portletId, String category, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {
		return _portal.isCompanyControlPanelPortlet(portletId, category, themeDisplay);
	}

	public boolean isControlPanelPortlet(String portletId, ThemeDisplay themeDisplay) throws SystemException {
		return _portal.isControlPanelPortlet(portletId, themeDisplay);
	}

	public boolean isControlPanelPortlet(String portletId, String category, ThemeDisplay themeDisplay)
		throws SystemException {
		return _portal.isControlPanelPortlet(portletId, category, themeDisplay);
	}

	public boolean isLayoutDescendant(Layout layout, long layoutId) throws PortalException, SystemException {
		return _portal.isLayoutDescendant(layout, layoutId);
	}

	public boolean isMethodGet(PortletRequest portletRequest) {
		return _portal.isMethodGet(portletRequest);
	}

	public boolean isMethodPost(PortletRequest portletRequest) {
		return _portal.isMethodPost(portletRequest);
	}

	public boolean isMultipartRequest(HttpServletRequest request) {
		return _portal.isMultipartRequest(request);
	}

	public String getUniqueElementId(HttpServletRequest request, String namespace, String id) {
		return _portal.getUniqueElementId(request, namespace, id);
	}

	public String getUniqueElementId(PortletRequest request, String namespace, String id) {
		return _portal.getUniqueElementId(request, namespace, id);
	}

	public UploadPortletRequest getUploadPortletRequest(PortletRequest portletRequest) {
		return _portal.getUploadPortletRequest(portletRequest);
	}

	public UploadServletRequest getUploadServletRequest(HttpServletRequest request) {
		return _portal.getUploadServletRequest(request);
	}

	public Date getUptime() {
		return _portal.getUptime();
	}

	public String getURLWithSessionId(String url, String sessionId) {
		return _portal.getURLWithSessionId(url, sessionId);
	}

	public User getUser(HttpServletRequest request) throws PortalException, SystemException {
		return _portal.getUser(request);
	}

	public User getUser(PortletRequest portletRequest) throws PortalException, SystemException {
		return _portal.getUser(portletRequest);
	}

	public String getUserEmailAddress(long userId) throws SystemException {
		return _portal.getUserEmailAddress(userId);
	}

	public long getUserId(HttpServletRequest request) {
		return _portal.getUserId(request);
	}

	public long getUserId(PortletRequest portletRequest) {
		return _portal.getUserId(portletRequest);
	}

	@Override
	public String getUserName(BaseModel<?> baseModel) {
		return _portal.getUserName(baseModel);
	}

	public String getUserName(long userId, String defaultUserName) {
		return _portal.getUserName(userId, defaultUserName);
	}

	public String getUserName(long userId, String defaultUserName, HttpServletRequest request) {
		return _portal.getUserName(userId, defaultUserName, request);
	}

	public String getUserName(long userId, String defaultUserName, String userAttribute) {
		return _portal.getUserName(userId, defaultUserName, userAttribute);
	}

	public String getUserName(long userId, String defaultUserName, String userAttribute, HttpServletRequest request) {
		return _portal.getUserName(userId, defaultUserName, userAttribute, request);
	}

	public String getUserPassword(HttpServletRequest request) {
		return _portal.getUserPassword(request);
	}

	public String getUserPassword(HttpSession session) {
		return _portal.getUserPassword(session);
	}

	public String getUserPassword(PortletRequest portletRequest) {
		return _portal.getUserPassword(portletRequest);
	}

	public String getUserValue(long userId, String param, String defaultValue) throws SystemException {
		return _portal.getUserValue(userId, param, defaultValue);
	}

	public long getValidUserId(long companyId, long userId) throws PortalException, SystemException {
		return _portal.getValidUserId(companyId, userId);
	}

	public String getVirtualLayoutActualURL(long groupId, boolean privateLayout, String mainPath, String friendlyURL,
		Map<String, String[]> params, Map<String, Object> requestContext) throws PortalException, SystemException {
		return _portal.getVirtualLayoutActualURL(groupId, privateLayout, mainPath, friendlyURL, params, requestContext);
	}

	public String getWidgetURL(Portlet portlet, ThemeDisplay themeDisplay) throws PortalException, SystemException {
		return _portal.getWidgetURL(portlet, themeDisplay);
	}

	public Portal getWrapped() {
		return _portal;
	}
}
