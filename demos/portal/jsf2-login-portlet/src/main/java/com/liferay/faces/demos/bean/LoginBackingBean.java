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
package com.liferay.faces.demos.bean;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.CompanyMaxUsersException;
import com.liferay.portal.CookieNotSupportedException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PasswordExpiredException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserLockoutException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ClassResolverUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassInvoker;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@RequestScoped
public class LoginBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LoginBackingBean.class);

	// Private Constants
	private static final String LOGIN_UTIL_FQCN = "com.liferay.portlet.login.util.LoginUtil";
	private static final String LOGIN_METHOD = "login";
	private static final Class<?>[] LOGIN_PARAM_TYPES = new Class<?>[] {
			HttpServletRequest.class, HttpServletResponse.class, String.class, String.class, boolean.class, String.class
		};
	private static final String NAMESPACE_SERVLET_REQUEST_FQCN = "com.liferay.portal.servlet.NamespaceServletRequest";

	// Injections
	@ManagedProperty(value = "#{loginModelBean}")
	private LoginModelBean loginModelBean;

	// Private Data Members
	private String authType;
	private String handleLabel;

	public void authenticate() {

		LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
		ActionRequest actionRequest = (ActionRequest) liferayFacesContext.getPortletRequest();
		ActionResponse actionResponse = (ActionResponse) liferayFacesContext.getPortletResponse();
		ThemeDisplay themeDisplay = liferayFacesContext.getThemeDisplay();
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(actionRequest);

		// If the request object is a wrapper that handles special namespacing considerations for portlet session
		// attributes, then get the inner-most wrapped request. This will ensure that the following call to
		// LoginUtil.login(...) will be able to work with a session that has an attribute map shared by the portal.
		if (httpServletRequest.getClass().getName().equals(NAMESPACE_SERVLET_REQUEST_FQCN)) {

			while (httpServletRequest instanceof HttpServletRequestWrapper) {
				HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) httpServletRequest;
				httpServletRequest = (HttpServletRequest) httpServletRequestWrapper.getRequest();
			}
		}

		HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(actionResponse);

		String handle = loginModelBean.getHandle();
		String password = loginModelBean.getPassword();
		boolean rememberMe = loginModelBean.isRememberMe();

		boolean authenticated = false;
		String feedbackMessageId = null;

		try {
			Class<?> loginUtilClass = ClassResolverUtil.resolveByPortalClassLoader(LOGIN_UTIL_FQCN);
			MethodKey methodKey = new MethodKey(loginUtilClass, LOGIN_METHOD, LOGIN_PARAM_TYPES);
			PortalClassInvoker.invoke(false, methodKey, httpServletRequest, httpServletResponse, handle, password,
				rememberMe, authType);
			authenticated = true;
		}
		catch (AuthException e) {
			feedbackMessageId = "authentication-failed";
		}
		catch (CompanyMaxUsersException e) {
			feedbackMessageId = "unable-to-login-because-the-maximum-number-of-users-has-been-reached";
		}
		catch (CookieNotSupportedException e) {
			feedbackMessageId = "authentication-failed-please-enable-browser-cookies";
		}
		catch (NoSuchUserException e) {
			feedbackMessageId = "authentication-failed";
		}
		catch (PasswordExpiredException e) {
			feedbackMessageId = "your-password-has-expired";
		}
		catch (UserEmailAddressException e) {
			feedbackMessageId = "authentication-failed";
		}
		catch (UserLockoutException e) {
			feedbackMessageId = "this-account-has-been-locked";
		}
		catch (UserPasswordException e) {
			feedbackMessageId = "authentication-failed";
		}
		catch (UserScreenNameException e) {
			feedbackMessageId = "authentication-failed";
		}
		catch (Exception e) {
			logger.error(e);
		}

		if (authenticated) {

			try {
				ExternalContext externalContext = liferayFacesContext.getExternalContext();

				if (PropsValues.PORTAL_JAAS_ENABLE) {

					externalContext.redirect(themeDisplay.getPathMain() + "/portal/protected");
				}
				else {
					String redirect = ParamUtil.getString(actionRequest, "redirect");

					if (Validator.isNotNull(redirect)) {
						redirect = PortalUtil.escapeRedirect(redirect);

						if (!redirect.startsWith(Http.HTTP)) {
							redirect = getCompleteRedirectURL(httpServletRequest, redirect);
						}

						externalContext.redirect(redirect);
					}
					else {
						boolean doActionAfterLogin = ParamUtil.getBoolean(actionRequest, "doActionAfterLogin");

						if (doActionAfterLogin) {
							return;
						}
						else {

							redirect = getCompleteRedirectURL(httpServletRequest, themeDisplay.getPathMain());
							externalContext.redirect(redirect);
						}
					}
				}
			}
			catch (IOException e) {
				logger.error(e);
				liferayFacesContext.addGlobalUnexpectedErrorMessage();
			}
		}
		else {

			if (feedbackMessageId != null) {
				liferayFacesContext.addGlobalErrorMessage(feedbackMessageId);
			}
		}
	}

	public String getAuthType() {

		if (authType == null) {

			LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

			try {
				ThemeDisplay themeDisplay = liferayFacesContext.getThemeDisplay();
				Company company = themeDisplay.getCompany();

				authType = company.getAuthType();
			}
			catch (SystemException e) {
				logger.error(e);
				liferayFacesContext.addGlobalErrorMessage("Unable to determine authentication type");
				authType = CompanyConstants.AUTH_TYPE_EA;
			}
		}

		return authType;
	}

	protected String getCompleteRedirectURL(HttpServletRequest request, String redirect) {

		HttpSession session = request.getSession();

		Boolean httpsInitial = (Boolean) session.getAttribute(WebKeys.HTTPS_INITIAL);

		String portalURL = null;

		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS && !PropsValues.SESSION_ENABLE_PHISHING_PROTECTION &&
				(httpsInitial != null) && !httpsInitial.booleanValue()) {

			portalURL = PortalUtil.getPortalURL(request, false);
		}
		else {
			portalURL = PortalUtil.getPortalURL(request);
		}

		return portalURL.concat(redirect);
	}

	public String getHandleLabel() {

		if (handleLabel == null) {

			String authType = getAuthType();

			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				handleLabel = "email-address";
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				handleLabel = "screen-name";
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				handleLabel = "id";
			}
		}

		return handleLabel;
	}

	public void setLoginModelBean(LoginModelBean loginModelBean) {
		this.loginModelBean = loginModelBean;
	}
}
