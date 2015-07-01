<%--
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
--%>

<%@ include file="/html/portlet/login/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">

		<%
		String signedInAs = HtmlUtil.escape(user.getFullName());

		if (themeDisplay.isShowMyAccountIcon() && Validator.isNotNull(themeDisplay.getURLMyAccount())) {
			signedInAs = "<a href=\"" + HtmlUtil.escape(themeDisplay.getURLMyAccount().toString()) + "\">" + signedInAs + "</a>";
		}
		%>

		<%= LanguageUtil.format(pageContext, "you-are-signed-in-as-x", signedInAs, false) %>
	</c:when>
	<c:otherwise>

		<%
		String redirect = ParamUtil.getString(request, "redirect", currentURL);

		String login = LoginUtil.getLogin(request, "login", company);
		String password = "";
		boolean rememberMe = ParamUtil.getBoolean(request, "rememberMe");

		if (Validator.isNull(authType)) {
			authType = company.getAuthType();
		}
		%>

		<portlet:actionURL secure="<%= PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS || request.isSecure() %>" var="loginURL">
			<portlet:param name="struts_action" value="/login/login" />
		</portlet:actionURL>

		<aui:form action="<%= loginURL %>" autocomplete='<%= PropsValues.COMPANY_SECURITY_LOGIN_FORM_AUTOCOMPLETE ? "on" : "off" %>' method="post" name="fm">
			<aui:input name="saveLastPath" type="hidden" value="<%= false %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="doActionAfterLogin" type="hidden" value="<%= portletName.equals(PortletKeys.FAST_LOGIN) ? true : false %>" />

			<c:choose>
				<c:when test='<%= SessionMessages.contains(request, "userAdded") %>'>

					<%
					String userEmailAddress = (String)SessionMessages.get(request, "userAdded");
					String userPassword = (String)SessionMessages.get(request, "userAddedPassword");
					%>

					<div class="alert alert-success">
						<c:choose>
							<c:when test="<%= company.isStrangersVerify() || Validator.isNull(userPassword) %>">
								<%= LanguageUtil.get(pageContext, "thank-you-for-creating-an-account") %>

								<c:if test="<%= company.isStrangersVerify() %>">
									<%= LanguageUtil.format(pageContext, "your-email-verification-code-has-been-sent-to-x", userEmailAddress) %>
								</c:if>
							</c:when>
							<c:otherwise>
								<%= LanguageUtil.format(pageContext, "thank-you-for-creating-an-account.-your-password-is-x", userPassword, false) %>
							</c:otherwise>
						</c:choose>

						<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED) %>">
							<%= LanguageUtil.format(pageContext, "your-password-has-been-sent-to-x", userEmailAddress) %>
						</c:if>
					</div>
				</c:when>
				<c:when test='<%= SessionMessages.contains(request, "userPending") %>'>

					<%
					String userEmailAddress = (String)SessionMessages.get(request, "userPending");
					%>

					<div class="alert alert-success">
						<%= LanguageUtil.format(pageContext, "thank-you-for-creating-an-account.-you-will-be-notified-via-email-at-x-when-your-account-has-been-approved", userEmailAddress) %>
					</div>
				</c:when>
			</c:choose>

			<liferay-ui:error exception="<%= AuthException.class %>" message="authentication-failed" />
			<liferay-ui:error exception="<%= CompanyMaxUsersException.class %>" message="unable-to-login-because-the-maximum-number-of-users-has-been-reached" />
			<liferay-ui:error exception="<%= CookieNotSupportedException.class %>" message="authentication-failed-please-enable-browser-cookies" />
			<liferay-ui:error exception="<%= NoSuchUserException.class %>" message="authentication-failed" />
			<liferay-ui:error exception="<%= PasswordExpiredException.class %>" message="your-password-has-expired" />
			<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="authentication-failed" />
			<liferay-ui:error exception="<%= UserLockoutException.class %>" message="this-account-has-been-locked" />
			<liferay-ui:error exception="<%= UserPasswordException.class %>" message="authentication-failed" />
			<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="authentication-failed" />

			<aui:fieldset>

				<%
				String loginLabel = null;

				if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					loginLabel = "email-address";
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					loginLabel = "screen-name";
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
					loginLabel = "id";
				}
				%>

				<aui:input autoFocus="<%= windowState.equals(LiferayWindowState.EXCLUSIVE) || windowState.equals(WindowState.MAXIMIZED) %>" label="<%= loginLabel %>" name="login" showRequiredLabel="<%= false %>" type="text" value="<%= login %>">
					<aui:validator name="required" />
				</aui:input>

				<aui:input name="password" showRequiredLabel="<%= false %>" type="password" value="<%= password %>">
					<aui:validator name="required" />
				</aui:input>

				<span id="<portlet:namespace />passwordCapsLockSpan" style="display: none;"><liferay-ui:message key="caps-lock-is-on" /></span>

				<c:if test="<%= company.isAutoLogin() && !PropsValues.SESSION_DISABLED %>">
					<aui:input checked="<%= rememberMe %>" name="rememberMe" type="checkbox" />
				</c:if>
			</aui:fieldset>

			<aui:button-row>
				<aui:button id="j_login" type="submit" value="sign-in" />
			</aui:button-row>
		</aui:form>

		<liferay-util:include page="/html/portlet/login/navigation.jsp" />

		<aui:script use="aui-base">
			var password = A.one('#<portlet:namespace />password');

			if (password) {
				password.on(
					'keypress',
					function(event) {
						Liferay.Util.showCapsLock(event, '<portlet:namespace />passwordCapsLockSpan');
					}
				);
			}
		</aui:script>
	</c:otherwise>
</c:choose>