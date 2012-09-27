<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<%@ include file="/html/portlet/login/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">

		<%
		String signedInAs = user.getFullName();

		if (themeDisplay.isShowMyAccountIcon()) {
			signedInAs = "<a href=\"" + HtmlUtil.escape(themeDisplay.getURLMyAccount().toString()) + "\">" + signedInAs + "</a>";
		}
		%>

		<%= LanguageUtil.format(pageContext, "you-are-signed-in-as-x", signedInAs) %>
	</c:when>
	<c:otherwise>

		<%
		String redirect = ParamUtil.getString(renderRequest, "redirect");

		String login = LoginUtil.getLogin(request, "login", company);
		String password = StringPool.BLANK;
		boolean rememberMe = ParamUtil.getBoolean(request, "rememberMe");

		if (Validator.isNull(authType)) {
			authType = company.getAuthType();
		}
		%>

		<form action="<portlet:actionURL secure="<%= PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS || request.isSecure() %>"><portlet:param name="saveLastPath" value="0" /><portlet:param name="struts_action" value="/login/login" /></portlet:actionURL>" class="uni-form" method="post" name="<portlet:namespace />fm">
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
		<input id="<portlet:namespace />rememberMe" name="<portlet:namespace />rememberMe" type="hidden" value="<%= rememberMe %>" />

		<c:if test='<%= SessionMessages.contains(request, "user_added") %>'>

			<%
			String userEmailAddress = (String)SessionMessages.get(request, "user_added");
			String userPassword = (String)SessionMessages.get(request, "user_added_password");
			%>

			<span class="portlet-msg-success">
				<c:choose>
					<c:when test="<%= company.isStrangersVerify() || Validator.isNull(userPassword) %>">
						<%= LanguageUtil.format(pageContext, "thank-you-for-creating-an-account-your-password-has-been-sent-to-x", userEmailAddress) %>
					</c:when>
					<c:otherwise>
						<%= LanguageUtil.format(pageContext, "thank-you-for-creating-an-account-your-password-is-x", new Object[] {userPassword, userEmailAddress}) %>
					</c:otherwise>
				</c:choose>
			</span>
		</c:if>

		<liferay-ui:error exception="<%= AuthException.class %>" message="authentication-failed" />
		<liferay-ui:error exception="<%= CookieNotSupportedException.class %>" message="authentication-failed-please-enable-browser-cookies" />
		<liferay-ui:error exception="<%= NoSuchUserException.class %>" message="please-enter-a-valid-login" />
		<liferay-ui:error exception="<%= PasswordExpiredException.class %>" message="your-password-has-expired" />
		<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="please-enter-a-valid-login" />
		<liferay-ui:error exception="<%= UserLockoutException.class %>" message="this-account-has-been-locked" />
		<liferay-ui:error exception="<%= UserPasswordException.class %>" message="please-enter-a-valid-password" />
		<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="please-enter-a-valid-screen-name" />

		<fieldset class="block-labels">
			<div class="ctrl-holder">

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

				<label for="<portlet:namespace />login"><liferay-ui:message key="<%= loginLabel %>" /></label>

				<input name="<portlet:namespace />login" type="text" value="<%= HtmlUtil.escape(login) %>" />
			</div>

			<div class="ctrl-holder">
				<label for="<portlet:namespace />password"><liferay-ui:message key="password" /></label>

				<input id="<portlet:namespace />password" name="<portlet:namespace />password" type="password" value="<%= password %>" />

				<span id="<portlet:namespace />passwordCapsLockSpan" style="display: none;"><liferay-ui:message key="caps-lock-is-on" /></span>
			</div>

			<c:if test="<%= company.isAutoLogin() && !PropsValues.SESSION_DISABLED %>">
				<div class="ctrl-holder inline-label">
					<label for="<portlet:namespace />rememberMeCheckbox"><liferay-ui:message key="remember-me" /></label>

					<input <%= rememberMe ? "checked" : "" %> id="<portlet:namespace />rememberMeCheckbox" type="checkbox" />
				</div>
			</c:if>

			<div class="button-holder">
				<input id="j_login" type="submit" value="<liferay-ui:message key="sign-in" />" />
			</div>
		</fieldset>

		</form>

		<%@ include file="/html/portlet/login/navigation.jspf" %>

		<script type="text/javascript">
			jQuery(
				function() {
					jQuery('#<portlet:namespace />password').keypress(
						function(event) {
							Liferay.Util.showCapsLock(event, '<portlet:namespace />passwordCapsLockSpan');
						}
					);

					jQuery('#<portlet:namespace />rememberMeCheckbox').click(
						function() {
							var checked = 'off';

							if (this.checked) {
								checked = 'on';
							}

							jQuery('#<portlet:namespace />rememberMe').val(checked);
						}
					);
				}
			);

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />login);
			</c:if>
		</script>
	</c:otherwise>
</c:choose>