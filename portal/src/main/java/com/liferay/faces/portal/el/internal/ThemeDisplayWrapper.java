package com.liferay.faces.portal.el.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;

import javax.faces.FacesWrapper;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public abstract class ThemeDisplayWrapper extends ThemeDisplay implements FacesWrapper<ThemeDisplay> {

	public abstract ThemeDisplay getWrapped();

	@Override
	public Object clone() throws CloneNotSupportedException {
		return getWrapped().clone();
	}

	@Override
	public Account getAccount() {
		return getWrapped().getAccount();
	}

	@Override
	public String getCDNBaseURL() {
		return getWrapped().getCDNBaseURL();
	}

	@Override
	public String getCDNDynamicResourcesHost() {
		return getWrapped().getCDNDynamicResourcesHost();
	}

	@Override
	public String getCDNHost() {
		return getWrapped().getCDNHost();
	}

	@Override
	public ColorScheme getColorScheme() {
		return getWrapped().getColorScheme();
	}

	@Override
	public String getColorSchemeId() {
		return getWrapped().getColorSchemeId();
	}

	@Override
	public Company getCompany() {
		return getWrapped().getCompany();
	}

	@Override
	public long getCompanyGroupId() {
		return getWrapped().getCompanyGroupId();
	}

	@Override
	public long getCompanyId() {
		return getWrapped().getCompanyId();
	}

	@Override
	public String getCompanyLogo() {
		return getWrapped().getCompanyLogo();
	}

	@Override
	public int getCompanyLogoHeight() {
		return getWrapped().getCompanyLogoHeight();
	}

	@Override
	public int getCompanyLogoWidth() {
		return getWrapped().getCompanyLogoWidth();
	}

	@Override
	public Contact getContact() {
		return getWrapped().getContact();
	}

	@Override
	public String getControlPanelCategory() {
		return getWrapped().getControlPanelCategory();
	}

	@Override
	public User getDefaultUser() throws PortalException, SystemException {
		return getWrapped().getDefaultUser();
	}

	@Override
	public long getDefaultUserId() throws PortalException, SystemException {
		return getWrapped().getDefaultUserId();
	}

	@Override
	public Device getDevice() {
		return getWrapped().getDevice();
	}

	@Override
	public long getDoAsGroupId() {
		return getWrapped().getDoAsGroupId();
	}

	@Override
	public String getDoAsUserId() {
		return getWrapped().getDoAsUserId();
	}

	@Override
	public String getDoAsUserLanguageId() {
		return getWrapped().getDoAsUserLanguageId();
	}

	@Override
	public String getFacebookCanvasPageURL() {
		return getWrapped().getFacebookCanvasPageURL();
	}

	@Override
	public String getI18nLanguageId() {
		return getWrapped().getI18nLanguageId();
	}

	@Override
	public String getI18nPath() {
		return getWrapped().getI18nPath();
	}

	@Override
	public String getLanguageId() {
		return getWrapped().getLanguageId();
	}

	@Override
	public Layout getLayout() {
		return getWrapped().getLayout();
	}

	@Override
	public List<Layout> getLayouts() {
		return getWrapped().getLayouts();
	}

	@Override
	public LayoutSet getLayoutSet() {
		return getWrapped().getLayoutSet();
	}

	@Override
	public String getLayoutSetLogo() {
		return getWrapped().getLayoutSetLogo();
	}

	@Override
	public LayoutTypePortlet getLayoutTypePortlet() {
		return getWrapped().getLayoutTypePortlet();
	}

	@Override
	public String getLifecycle() {
		return getWrapped().getLifecycle();
	}

	@Override
	public Locale getLocale() {
		return getWrapped().getLocale();
	}

	@Override
	public MDRRuleGroupInstance getMDRRuleGroupInstance() {
		return getWrapped().getMDRRuleGroupInstance();
	}

	@Override
	public Group getParentGroup() {
		return getWrapped().getParentGroup();
	}

	@Override
	public long getParentGroupId() {
		return getWrapped().getParentGroupId();
	}

	@Override
	public String getParentGroupName() throws PortalException, SystemException {
		return getWrapped().getParentGroupName();
	}

	@Override
	public String getPathApplet() {
		return getWrapped().getPathApplet();
	}

	@Override
	public String getPathCms() {
		return getWrapped().getPathCms();
	}

	@Override
	public String getPathColorSchemeImages() {
		return getWrapped().getPathColorSchemeImages();
	}

	@Override
	public String getPathContext() {
		return getWrapped().getPathContext();
	}

	@Override
	public String getPathFlash() {
		return getWrapped().getPathFlash();
	}

	@Override
	public String getPathFriendlyURLPrivateGroup() {
		return getWrapped().getPathFriendlyURLPrivateGroup();
	}

	@Override
	public String getPathFriendlyURLPrivateUser() {
		return getWrapped().getPathFriendlyURLPrivateUser();
	}

	@Override
	public String getPathFriendlyURLPublic() {
		return getWrapped().getPathFriendlyURLPublic();
	}

	@Override
	public String getPathImage() {
		return getWrapped().getPathImage();
	}

	@Override
	public String getPathJavaScript() {
		return getWrapped().getPathJavaScript();
	}

	@Override
	public String getPathMain() {
		return getWrapped().getPathMain();
	}

	@Override
	public String getPathSound() {
		return getWrapped().getPathSound();
	}

	@Override
	public String getPathThemeCss() {
		return getWrapped().getPathThemeCss();
	}

	@Override
	public String getPathThemeImage() {
		return getWrapped().getPathThemeImage();
	}

	@Override
	public String getPathThemeImages() {
		return getWrapped().getPathThemeImages();
	}

	@Override
	public String getPathThemeJavaScript() {
		return getWrapped().getPathThemeJavaScript();
	}

	@Override
	public String getPathThemeRoot() {
		return getWrapped().getPathThemeRoot();
	}

	@Override
	public String getPathThemeTemplates() {
		return getWrapped().getPathThemeTemplates();
	}

	@Override
	public PermissionChecker getPermissionChecker() {
		return getWrapped().getPermissionChecker();
	}

	@Override
	public long getPlid() {
		return getWrapped().getPlid();
	}

	@Override
	public String getPortalURL() {
		return getWrapped().getPortalURL();
	}

	@Override
	public PortletDisplay getPortletDisplay() {
		return getWrapped().getPortletDisplay();
	}

	@Override
	public long getPortletGroupId() {
		return getWrapped().getPortletGroupId();
	}

	@Override
	public String getPpid() {
		return getWrapped().getPpid();
	}

	@Override
	public String getRealCompanyLogo() {
		return getWrapped().getRealCompanyLogo();
	}

	@Override
	public int getRealCompanyLogoHeight() {
		return getWrapped().getRealCompanyLogoHeight();
	}

	@Override
	public int getRealCompanyLogoWidth() {
		return getWrapped().getRealCompanyLogoWidth();
	}

	@Override
	public User getRealUser() {
		return getWrapped().getRealUser();
	}

	@Override
	public long getRealUserId() {
		return getWrapped().getRealUserId();
	}

	@Override
	public long getRefererGroupId() {
		return getWrapped().getRefererGroupId();
	}

	@Override
	public long getRefererPlid() {
		return getWrapped().getRefererPlid();
	}

	@Override
	public HttpServletRequest getRequest() {
		return getWrapped().getRequest();
	}

	@Override
	public Group getScopeGroup() {
		return getWrapped().getScopeGroup();
	}

	@Override
	public long getScopeGroupId() {
		return getWrapped().getScopeGroupId();
	}

	@Override
	public long getScopeGroupIdOrLiveGroupId() throws PortalException, SystemException {
		return getWrapped().getScopeGroupIdOrLiveGroupId();
	}

	@Override
	public String getScopeGroupName() throws PortalException, SystemException {
		return getWrapped().getScopeGroupName();
	}

	@Override
	public Layout getScopeLayout() throws PortalException, SystemException {
		return getWrapped().getScopeLayout();
	}

	@Override
	public String getServerName() {
		return getWrapped().getServerName();
	}

	@Override
	public int getServerPort() {
		return getWrapped().getServerPort();
	}

	@Override
	public String getSessionId() {
		return getWrapped().getSessionId();
	}

	@Override
	public Locale getSiteDefaultLocale() {
		return getWrapped().getSiteDefaultLocale();
	}

	@Override
	public Group getSiteGroup() {
		return getWrapped().getSiteGroup();
	}

	@Override
	public long getSiteGroupId() {
		return getWrapped().getSiteGroupId();
	}

	@Override
	public long getSiteGroupIdOrLiveGroupId() throws PortalException, SystemException {
		return getWrapped().getSiteGroupIdOrLiveGroupId();
	}

	@Override
	public String getSiteGroupName() throws PortalException, SystemException {
		return getWrapped().getSiteGroupName();
	}

	@Override
	public Theme getTheme() {
		return getWrapped().getTheme();
	}

	@Override
	public String getThemeId() {
		return getWrapped().getThemeId();
	}

	@Override
	public String getThemeSetting(String key) {
		return getWrapped().getThemeSetting(key);
	}

	@Override
	public Properties getThemeSettings() {
		return getWrapped().getThemeSettings();
	}

	@Override
	public String getTilesContent() {
		return getWrapped().getTilesContent();
	}

	@Override
	public String getTilesTitle() {
		return getWrapped().getTilesTitle();
	}

	@Override
	public TimeZone getTimeZone() {
		return getWrapped().getTimeZone();
	}

	@Override
	public List<Layout> getUnfilteredLayouts() {
		return getWrapped().getUnfilteredLayouts();
	}

	@Override
	public String getURLAddContent() {
		return getWrapped().getURLAddContent();
	}

	@Override
	public String getURLControlPanel() {
		return getWrapped().getURLControlPanel();
	}

	@Override
	public String getURLCurrent() {
		return getWrapped().getURLCurrent();
	}

	@Override
	public String getURLHome() {
		return getWrapped().getURLHome();
	}

	@Override
	public String getURLLayoutTemplates() {
		return getWrapped().getURLLayoutTemplates();
	}

	@Override
	public PortletURL getURLManageSiteMemberships() {
		return getWrapped().getURLManageSiteMemberships();
	}

	@Override
	public PortletURL getURLMyAccount() {
		return getWrapped().getURLMyAccount();
	}

	@Override
	public PortletURL getURLPageSettings() {
		return getWrapped().getURLPageSettings();
	}

	@Override
	public String getURLPortal() {
		return getWrapped().getURLPortal();
	}

	@Override
	public PortletURL getURLPublishToLive() {
		return getWrapped().getURLPublishToLive();
	}

	@Override
	public String getURLSignIn() {
		return getWrapped().getURLSignIn();
	}

	@Override
	public String getURLSignOut() {
		return getWrapped().getURLSignOut();
	}

	@Override
	public String getURLSiteAdministration() {
		return getWrapped().getURLSiteAdministration();
	}

	@Override
	public String getURLSiteContent() {
		return getWrapped().getURLSiteContent();
	}

	@Override
	public PortletURL getURLSiteMapSettings() {
		return getWrapped().getURLSiteMapSettings();
	}

	@Override
	public PortletURL getURLSiteSettings() {
		return getWrapped().getURLSiteSettings();
	}

	@Override
	public PortletURL getURLUpdateManager() {
		return getWrapped().getURLUpdateManager();
	}

	@Override
	public User getUser() {
		return getWrapped().getUser();
	}

	@Override
	public long getUserId() {
		return getWrapped().getUserId();
	}

	@Override
	public boolean isAddSessionIdToURL() {
		return getWrapped().isAddSessionIdToURL();
	}

	@Override
	public boolean isAjax() {
		return getWrapped().isAjax();
	}

	@Override
	public boolean isFacebook() {
		return getWrapped().isFacebook();
	}

	@Override
	public boolean isFreeformLayout() {
		return getWrapped().isFreeformLayout();
	}

	@Override
	public boolean isI18n() {
		return getWrapped().isI18n();
	}

	@Override
	public boolean isImpersonated() {
		return getWrapped().isImpersonated();
	}

	@Override
	public boolean isIncludedJs(String js) {
		return getWrapped().isIncludedJs(js);
	}

	@Override
	public boolean isIncludePortletCssJs() {
		return getWrapped().isIncludePortletCssJs();
	}

	@Override
	public boolean isIsolated() {
		return getWrapped().isIsolated();
	}

	@Override
	public boolean isLifecycleAction() {
		return getWrapped().isLifecycleAction();
	}

	@Override
	public boolean isLifecycleEvent() {
		return getWrapped().isLifecycleEvent();
	}

	@Override
	public boolean isLifecycleRender() {
		return getWrapped().isLifecycleRender();
	}

	@Override
	public boolean isLifecycleResource() {
		return getWrapped().isLifecycleResource();
	}

	@Override
	public boolean isSecure() {
		return getWrapped().isSecure();
	}

	@Override
	public boolean isShowAddContentIcon() {
		return getWrapped().isShowAddContentIcon();
	}

	@Override
	public boolean isShowAddContentIconPermission() {
		return getWrapped().isShowAddContentIconPermission();
	}

	@Override
	public boolean isShowControlPanelIcon() {
		return getWrapped().isShowControlPanelIcon();
	}

	@Override
	public boolean isShowHomeIcon() {
		return getWrapped().isShowHomeIcon();
	}

	@Override
	public boolean isShowLayoutTemplatesIcon() {
		return getWrapped().isShowLayoutTemplatesIcon();
	}

	@Override
	public boolean isShowManageSiteMembershipsIcon() {
		return getWrapped().isShowManageSiteMembershipsIcon();
	}

	@Override
	public boolean isShowMyAccountIcon() {
		return getWrapped().isShowMyAccountIcon();
	}

	@Override
	public boolean isShowPageCustomizationIcon() {
		return getWrapped().isShowPageCustomizationIcon();
	}

	@Override
	public boolean isShowPageSettingsIcon() {
		return getWrapped().isShowPageSettingsIcon();
	}

	@Override
	public boolean isShowPortalIcon() {
		return getWrapped().isShowPortalIcon();
	}

	@Override
	public boolean isShowSignInIcon() {
		return getWrapped().isShowSignInIcon();
	}

	@Override
	public boolean isShowSignOutIcon() {
		return getWrapped().isShowSignOutIcon();
	}

	@Override
	public boolean isShowSiteAdministrationIcon() {
		return getWrapped().isShowSiteAdministrationIcon();
	}

	@Override
	public boolean isShowSiteContentIcon() {
		return getWrapped().isShowSiteContentIcon();
	}

	@Override
	public boolean isShowSiteMapSettingsIcon() {
		return getWrapped().isShowSiteMapSettingsIcon();
	}

	@Override
	public boolean isShowSiteSettingsIcon() {
		return getWrapped().isShowSiteSettingsIcon();
	}

	@Override
	public boolean isShowStagingIcon() {
		return getWrapped().isShowStagingIcon();
	}

	@Override
	public boolean isSignedIn() {
		return getWrapped().isSignedIn();
	}

	@Override
	public boolean isStateExclusive() {
		return getWrapped().isStateExclusive();
	}

	@Override
	public boolean isStateMaximized() {
		return getWrapped().isStateMaximized();
	}

	@Override
	public boolean isStatePopUp() {
		return getWrapped().isStatePopUp();
	}

	@Override
	public boolean isThemeCssFastLoad() {
		return getWrapped().isThemeCssFastLoad();
	}

	@Override
	public boolean isThemeImagesFastLoad() {
		return getWrapped().isThemeImagesFastLoad();
	}

	@Override
	public boolean isThemeJsBarebone() {
		return getWrapped().isThemeJsBarebone();
	}

	@Override
	public boolean isThemeJsFastLoad() {
		return getWrapped().isThemeJsFastLoad();
	}

	@Override
	public boolean isTilesSelectable() {
		return getWrapped().isTilesSelectable();
	}

	@Override
	public boolean isWapTheme() {
		return getWrapped().isWapTheme();
	}

	@Override
	public boolean isWidget() {
		return getWrapped().isWidget();
	}

	@Override
	public ThemeDisplay merge(ThemeDisplay themeDisplay) {
		return getWrapped().merge(themeDisplay);
	}

	@Override
	public void setAccount(Account account) {
		getWrapped().setAccount(account);
	}

	@Override
	public void setAddSessionIdToURL(boolean addSessionIdToURL) {
		getWrapped().setAddSessionIdToURL(addSessionIdToURL);
	}

	@Override
	public void setAjax(boolean ajax) {
		getWrapped().setAjax(ajax);
	}

	@Override
	public void setCDNBaseURL(String cdnBase) {
		getWrapped().setCDNBaseURL(cdnBase);
	}

	@Override
	public void setCDNDynamicResourcesHost(String cdnDynamicResourcesHost) {
		getWrapped().setCDNDynamicResourcesHost(cdnDynamicResourcesHost);
	}

	@Override
	public void setCDNHost(String cdnHost) {
		getWrapped().setCDNHost(cdnHost);
	}

	@Override
	public void setCompany(Company company) throws PortalException, SystemException {
		getWrapped().setCompany(company);
	}

	@Override
	public void setCompanyLogo(String companyLogo) {
		getWrapped().setCompanyLogo(companyLogo);
	}

	@Override
	public void setCompanyLogoHeight(int companyLogoHeight) {
		getWrapped().setCompanyLogoHeight(companyLogoHeight);
	}

	@Override
	public void setCompanyLogoWidth(int companyLogoWidth) {
		getWrapped().setCompanyLogoWidth(companyLogoWidth);
	}

	@Override
	public void setContact(Contact contact) {
		getWrapped().setContact(contact);
	}

	@Override
	public void setControlPanelCategory(String controlPanelCategory) {
		getWrapped().setControlPanelCategory(controlPanelCategory);
	}

	@Override
	public void setDevice(Device device) {
		getWrapped().setDevice(device);
	}

	@Override
	public void setDoAsGroupId(long doAsGroupId) {
		getWrapped().setDoAsGroupId(doAsGroupId);
	}

	@Override
	public void setDoAsUserId(String doAsUserId) {
		getWrapped().setDoAsUserId(doAsUserId);
	}

	@Override
	public void setDoAsUserLanguageId(String doAsUserLanguageId) {
		getWrapped().setDoAsUserLanguageId(doAsUserLanguageId);
	}

	@Override
	public void setFacebookCanvasPageURL(String facebookCanvasPageURL) {
		getWrapped().setFacebookCanvasPageURL(facebookCanvasPageURL);
	}

	@Override
	public void setFreeformLayout(boolean freeformLayout) {
		getWrapped().setFreeformLayout(freeformLayout);
	}

	@Override
	public void setI18nLanguageId(String i18nLanguageId) {
		getWrapped().setI18nLanguageId(i18nLanguageId);
	}

	@Override
	public void setI18nPath(String i18nPath) {
		getWrapped().setI18nPath(i18nPath);
	}

	@Override
	public void setIncludePortletCssJs(boolean includePortletCssJs) {
		getWrapped().setIncludePortletCssJs(includePortletCssJs);
	}

	@Override
	public void setIsolated(boolean isolated) {
		getWrapped().setIsolated(isolated);
	}

	@Override
	public void setLanguageId(String languageId) {
		getWrapped().setLanguageId(languageId);
	}

	@Override
	public void setLayout(Layout layout) {
		getWrapped().setLayout(layout);
	}

	@Override
	public void setLayouts(List<Layout> layouts) {
		getWrapped().setLayouts(layouts);
	}

	@Override
	public void setLayoutSet(LayoutSet layoutSet) {
		getWrapped().setLayoutSet(layoutSet);
	}

	@Override
	public void setLayoutSetLogo(String layoutSetLogo) {
		getWrapped().setLayoutSetLogo(layoutSetLogo);
	}

	@Override
	public void setLayoutTypePortlet(LayoutTypePortlet layoutTypePortlet) {
		getWrapped().setLayoutTypePortlet(layoutTypePortlet);
	}

	@Override
	public void setLifecycle(String lifecycle) {
		getWrapped().setLifecycle(lifecycle);
	}

	@Override
	public void setLifecycleAction(boolean lifecycleAction) {
		getWrapped().setLifecycleAction(lifecycleAction);
	}

	@Override
	public void setLifecycleEvent(boolean lifecycleEvent) {
		getWrapped().setLifecycleEvent(lifecycleEvent);
	}

	@Override
	public void setLifecycleRender(boolean lifecycleRender) {
		getWrapped().setLifecycleRender(lifecycleRender);
	}

	@Override
	public void setLifecycleResource(boolean lifecycleResource) {
		getWrapped().setLifecycleResource(lifecycleResource);
	}

	@Override
	public void setLocale(Locale locale) {
		getWrapped().setLocale(locale);
	}

	@Override
	public void setLookAndFeel(Theme theme, ColorScheme colorScheme) {
		getWrapped().setLookAndFeel(theme, colorScheme);
	}

	@Override
	public void setMDRRuleGroupInstance(MDRRuleGroupInstance mdrRuleGroupInstance) {
		getWrapped().setMDRRuleGroupInstance(mdrRuleGroupInstance);
	}

	@Override
	public void setParentGroupId(long parentGroupId) {
		getWrapped().setParentGroupId(parentGroupId);
	}

	@Override
	public void setPathApplet(String pathApplet) {
		getWrapped().setPathApplet(pathApplet);
	}

	@Override
	public void setPathCms(String pathCms) {
		getWrapped().setPathCms(pathCms);
	}

	@Override
	public void setPathColorSchemeImages(String pathColorSchemeImages) {
		getWrapped().setPathColorSchemeImages(pathColorSchemeImages);
	}

	@Override
	public void setPathContext(String pathContext) {
		getWrapped().setPathContext(pathContext);
	}

	@Override
	public void setPathFlash(String pathFlash) {
		getWrapped().setPathFlash(pathFlash);
	}

	@Override
	public void setPathFriendlyURLPrivateGroup(String pathFriendlyURLPrivateGroup) {
		getWrapped().setPathFriendlyURLPrivateGroup(pathFriendlyURLPrivateGroup);
	}

	@Override
	public void setPathFriendlyURLPrivateUser(String pathFriendlyURLPrivateUser) {
		getWrapped().setPathFriendlyURLPrivateUser(pathFriendlyURLPrivateUser);
	}

	@Override
	public void setPathFriendlyURLPublic(String pathFriendlyURLPublic) {
		getWrapped().setPathFriendlyURLPublic(pathFriendlyURLPublic);
	}

	@Override
	public void setPathImage(String pathImage) {
		getWrapped().setPathImage(pathImage);
	}

	@Override
	public void setPathJavaScript(String pathJavaScript) {
		getWrapped().setPathJavaScript(pathJavaScript);
	}

	@Override
	public void setPathMain(String pathMain) {
		getWrapped().setPathMain(pathMain);
	}

	@Override
	public void setPathSound(String pathSound) {
		getWrapped().setPathSound(pathSound);
	}

	@Override
	public void setPathThemeCss(String pathThemeCss) {
		getWrapped().setPathThemeCss(pathThemeCss);
	}

	@Override
	public void setPathThemeImages(String pathThemeImages) {
		getWrapped().setPathThemeImages(pathThemeImages);
	}

	@Override
	public void setPathThemeJavaScript(String pathThemeJavaScript) {
		getWrapped().setPathThemeJavaScript(pathThemeJavaScript);
	}

	@Override
	public void setPathThemeRoot(String pathThemeRoot) {
		getWrapped().setPathThemeRoot(pathThemeRoot);
	}

	@Override
	public void setPathThemeTemplates(String pathThemeTemplates) {
		getWrapped().setPathThemeTemplates(pathThemeTemplates);
	}

	@Override
	public void setPermissionChecker(PermissionChecker permissionChecker) {
		getWrapped().setPermissionChecker(permissionChecker);
	}

	@Override
	public void setPlid(long plid) {
		getWrapped().setPlid(plid);
	}

	@Override
	public void setPortalURL(String portalURL) {
		getWrapped().setPortalURL(portalURL);
	}

	@Override
	public void setPpid(String ppid) {
		getWrapped().setPpid(ppid);
	}

	@Override
	public void setRealCompanyLogo(String realCompanyLogo) {
		getWrapped().setRealCompanyLogo(realCompanyLogo);
	}

	@Override
	public void setRealCompanyLogoHeight(int realCompanyLogoHeight) {
		getWrapped().setRealCompanyLogoHeight(realCompanyLogoHeight);
	}

	@Override
	public void setRealCompanyLogoWidth(int realCompanyLogoWidth) {
		getWrapped().setRealCompanyLogoWidth(realCompanyLogoWidth);
	}

	@Override
	public void setRealUser(User realUser) {
		getWrapped().setRealUser(realUser);
	}

	@Override
	public void setRefererGroupId(long refererGroupId) {
		getWrapped().setRefererGroupId(refererGroupId);
	}

	@Override
	public void setRefererPlid(long refererPlid) {
		getWrapped().setRefererPlid(refererPlid);
	}

	@Override
	public void setRequest(HttpServletRequest request) {
		getWrapped().setRequest(request);
	}

	@Override
	public void setScopeGroupId(long scopeGroupId) {
		getWrapped().setScopeGroupId(scopeGroupId);
	}

	@Override
	public void setSecure(boolean secure) {
		getWrapped().setSecure(secure);
	}

	@Override
	public void setServerName(String serverName) {
		getWrapped().setServerName(serverName);
	}

	@Override
	public void setServerPort(int serverPort) {
		getWrapped().setServerPort(serverPort);
	}

	@Override
	public void setSessionId(String sessionId) {
		getWrapped().setSessionId(sessionId);
	}

	@Override
	public void setShowAddContentIcon(boolean showAddContentIcon) {
		getWrapped().setShowAddContentIcon(showAddContentIcon);
	}

	@Override
	public void setShowAddContentIconPermission(boolean showAddContentIconPermission) {
		getWrapped().setShowAddContentIconPermission(showAddContentIconPermission);
	}

	@Override
	public void setShowControlPanelIcon(boolean showControlPanelIcon) {
		getWrapped().setShowControlPanelIcon(showControlPanelIcon);
	}

	@Override
	public void setShowHomeIcon(boolean showHomeIcon) {
		getWrapped().setShowHomeIcon(showHomeIcon);
	}

	@Override
	public void setShowLayoutTemplatesIcon(boolean showLayoutTemplatesIcon) {
		getWrapped().setShowLayoutTemplatesIcon(showLayoutTemplatesIcon);
	}

	@Override
	public void setShowManageSiteMembershipsIcon(boolean showManageSiteMembershipsIcon) {
		getWrapped().setShowManageSiteMembershipsIcon(showManageSiteMembershipsIcon);
	}

	@Override
	public void setShowMyAccountIcon(boolean showMyAccountIcon) {
		getWrapped().setShowMyAccountIcon(showMyAccountIcon);
	}

	@Override
	public void setShowPageCustomizationIcon(boolean showPageCustomizationIcon) {
		getWrapped().setShowPageCustomizationIcon(showPageCustomizationIcon);
	}

	@Override
	public void setShowPageSettingsIcon(boolean showPageSettingsIcon) {
		getWrapped().setShowPageSettingsIcon(showPageSettingsIcon);
	}

	@Override
	public void setShowPortalIcon(boolean showPortalIcon) {
		getWrapped().setShowPortalIcon(showPortalIcon);
	}

	@Override
	public void setShowSignInIcon(boolean showSignInIcon) {
		getWrapped().setShowSignInIcon(showSignInIcon);
	}

	@Override
	public void setShowSignOutIcon(boolean showSignOutIcon) {
		getWrapped().setShowSignOutIcon(showSignOutIcon);
	}

	@Override
	public void setShowSiteAdministrationIcon(boolean showSiteAdministrationIcon) {
		getWrapped().setShowSiteAdministrationIcon(showSiteAdministrationIcon);
	}

	@Override
	public void setShowSiteContentIcon(boolean showSiteContentIcon) {
		getWrapped().setShowSiteContentIcon(showSiteContentIcon);
	}

	@Override
	public void setShowSiteMapSettingsIcon(boolean showSiteMapSettingsIcon) {
		getWrapped().setShowSiteMapSettingsIcon(showSiteMapSettingsIcon);
	}

	@Override
	public void setShowSiteSettingsIcon(boolean showSiteSettingsIcon) {
		getWrapped().setShowSiteSettingsIcon(showSiteSettingsIcon);
	}

	@Override
	public void setShowStagingIcon(boolean showStagingIcon) {
		getWrapped().setShowStagingIcon(showStagingIcon);
	}

	@Override
	public void setSignedIn(boolean signedIn) {
		getWrapped().setSignedIn(signedIn);
	}

	@Override
	public void setSiteDefaultLocale(Locale siteDefaultLocale) {
		getWrapped().setSiteDefaultLocale(siteDefaultLocale);
	}

	@Override
	public void setSiteGroupId(long siteGroupId) {
		getWrapped().setSiteGroupId(siteGroupId);
	}

	@Override
	public void setStateExclusive(boolean stateExclusive) {
		getWrapped().setStateExclusive(stateExclusive);
	}

	@Override
	public void setStateMaximized(boolean stateMaximized) {
		getWrapped().setStateMaximized(stateMaximized);
	}

	@Override
	public void setStatePopUp(boolean statePopUp) {
		getWrapped().setStatePopUp(statePopUp);
	}

	@Override
	public void setThemeCssFastLoad(boolean themeCssFastLoad) {
		getWrapped().setThemeCssFastLoad(themeCssFastLoad);
	}

	@Override
	public void setThemeImagesFastLoad(boolean themeImagesFastLoad) {
		getWrapped().setThemeImagesFastLoad(themeImagesFastLoad);
	}

	@Override
	public void setThemeJsBarebone(boolean themeJsBarebone) {
		getWrapped().setThemeJsBarebone(themeJsBarebone);
	}

	@Override
	public void setThemeJsFastLoad(boolean themeJsFastLoad) {
		getWrapped().setThemeJsFastLoad(themeJsFastLoad);
	}

	@Override
	public void setTilesContent(String tilesContent) {
		getWrapped().setTilesContent(tilesContent);
	}

	@Override
	public void setTilesSelectable(boolean tilesSelectable) {
		getWrapped().setTilesSelectable(tilesSelectable);
	}

	@Override
	public void setTilesTitle(String tilesTitle) {
		getWrapped().setTilesTitle(tilesTitle);
	}

	@Override
	public void setTimeZone(TimeZone timeZone) {
		getWrapped().setTimeZone(timeZone);
	}

	@Override
	public void setUnfilteredLayouts(List<Layout> unfilteredLayouts) {
		getWrapped().setUnfilteredLayouts(unfilteredLayouts);
	}

	@Override
	public void setURLAddContent(String urlAddContent) {
		getWrapped().setURLAddContent(urlAddContent);
	}

	@Override
	public void setURLControlPanel(String urlControlPanel) {
		getWrapped().setURLControlPanel(urlControlPanel);
	}

	@Override
	public void setURLCurrent(String urlCurrent) {
		getWrapped().setURLCurrent(urlCurrent);
	}

	@Override
	public void setURLHome(String urlHome) {
		getWrapped().setURLHome(urlHome);
	}

	@Override
	public void setURLLayoutTemplates(String urlLayoutTemplates) {
		getWrapped().setURLLayoutTemplates(urlLayoutTemplates);
	}

	@Override
	public void setURLManageSiteMemberships(PortletURL urlManageSiteMemberships) {
		getWrapped().setURLManageSiteMemberships(urlManageSiteMemberships);
	}

	@Override
	public void setURLMyAccount(PortletURL urlMyAccount) {
		getWrapped().setURLMyAccount(urlMyAccount);
	}

	@Override
	public void setURLPageSettings(PortletURL urlPageSettings) {
		getWrapped().setURLPageSettings(urlPageSettings);
	}

	@Override
	public void setURLPortal(String urlPortal) {
		getWrapped().setURLPortal(urlPortal);
	}

	@Override
	public void setURLPublishToLive(PortletURL urlPublishToLive) {
		getWrapped().setURLPublishToLive(urlPublishToLive);
	}

	@Override
	public void setURLSignIn(String urlSignIn) {
		getWrapped().setURLSignIn(urlSignIn);
	}

	@Override
	public void setURLSignOut(String urlSignOut) {
		getWrapped().setURLSignOut(urlSignOut);
	}

	@Override
	public void setURLSiteAdministration(String urlSiteAdministration) {
		getWrapped().setURLSiteAdministration(urlSiteAdministration);
	}

	@Override
	public void setURLSiteContent(String urlSiteContent) {
		getWrapped().setURLSiteContent(urlSiteContent);
	}

	@Override
	public void setURLSiteMapSettings(PortletURL urlSiteMapSettings) {
		getWrapped().setURLSiteMapSettings(urlSiteMapSettings);
	}

	@Override
	public void setURLSiteSettings(PortletURL urlSiteSettings) {
		getWrapped().setURLSiteSettings(urlSiteSettings);
	}

	@Override
	public void setURLUpdateManager(PortletURL urlUpdateManager) {
		getWrapped().setURLUpdateManager(urlUpdateManager);
	}

	@Override
	public void setUser(User user) throws PortalException, SystemException {
		getWrapped().setUser(user);
	}

	@Override
	public void setWidget(boolean widget) {
		getWrapped().setWidget(widget);
	}

	@Override
	public String translate(String key) {
		return getWrapped().translate(key);
	}

	@Override
	public String translate(String pattern, Object argument) {
		return getWrapped().translate(pattern, argument);
	}

	@Override
	public String translate(String pattern, Object[] arguments) {
		return getWrapped().translate(pattern, arguments);
	}
}
