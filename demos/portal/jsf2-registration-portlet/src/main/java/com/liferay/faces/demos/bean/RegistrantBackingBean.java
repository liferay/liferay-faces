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
package com.liferay.faces.demos.bean;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.demos.model.Registrant;
import com.liferay.faces.demos.service.RegistrantServiceUtil;
import com.liferay.faces.portal.context.LiferayFacesContext;

import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.DuplicateUserScreenNameException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.service.CompanyLocalServiceUtil;


/**
 * This is a JSF backing managed-bean for the registrant.xhtml composition.
 *
 * @author  "Neil Griffin"
 */
@ManagedBean(name = "registrantBackingBean")
@ViewScoped
public class RegistrantBackingBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2947548873495692163L;

	// Logger
	private static final transient Logger logger = LoggerFactory.getLogger(RegistrantBackingBean.class);

	// Self-Injections
	private LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	// Injections
	@ManagedProperty(value = "#{registrantModelBean}")
	private transient RegistrantModelBean registrantModelBean;

	// Private Data Members
	private Boolean captchaRendered;

	public void submit(ActionEvent actionEvent) {

		Registrant submittedRegistrant = registrantModelBean.getRegistrant();
		logger.debug("Adding user firstName=[{0}], lastName=[{1}], emailAddress=[{2}], captchaText=[{3}]",
			new Object[] {
				submittedRegistrant.getFirstName(), submittedRegistrant.getLastName(),
				submittedRegistrant.getEmailAddress(), submittedRegistrant.getCaptchaText()
			});

		long creatorUserId = liferayFacesContext.getUser().getUserId();
		long companyId = liferayFacesContext.getCompanyId();
		Locale locale = liferayFacesContext.getLocale();

		try {
			boolean active = true;
			boolean autoScreenName = false;
			boolean sendEmail = true;
			RegistrantServiceUtil.add(creatorUserId, companyId, locale, submittedRegistrant, active, autoScreenName,
				sendEmail);

			String key = "thank-you-for-registering";
			liferayFacesContext.addGlobalInfoMessage(key, submittedRegistrant.getEmailAddress());
			submittedRegistrant.clearProperties();
		}
		catch (DuplicateUserScreenNameException e) {
			liferayFacesContext.addGlobalErrorMessage("the-screen-name-you-requested-is-already-taken");
		}
		catch (DuplicateUserEmailAddressException e) {
			liferayFacesContext.addGlobalErrorMessage("the-email-address-you-requested-is-already-taken");
		}
		catch (UserPasswordException e) {

			switch (e.getType()) {

			case UserPasswordException.PASSWORD_ALREADY_USED: {
				liferayFacesContext.addGlobalErrorMessage(
					"that-password-has-already-been-used-please-enter-in-a-different-password");

				break;
			}

			case UserPasswordException.PASSWORD_CONTAINS_TRIVIAL_WORDS: {
				liferayFacesContext.addGlobalErrorMessage(
					"that-password-uses-common-words-please-enter-in-a-password-that-is-harder-to-guess-i-e-contains-a-mix-of-numbers-and-letters");

				break;
			}

			case UserPasswordException.PASSWORD_INVALID: {
				liferayFacesContext.addGlobalErrorMessage(
					"that-password-is-invalid-please-enter-in-a-different-password");

				break;
			}

			case UserPasswordException.PASSWORD_LENGTH: {

				try {
					Company company = CompanyLocalServiceUtil.getCompany(companyId);
					PasswordPolicy passwordPolicy = company.getDefaultUser().getPasswordPolicy();
					liferayFacesContext.addGlobalErrorMessage(
						"that-password-is-too-short-or-too-long-please-make-sure-your-password-is-between-x-and-512-characters",
						new Object[] { String.valueOf(passwordPolicy.getMinLength()) });

				}
				catch (Exception e1) {
					logger.error(e.getMessage(), e);
					liferayFacesContext.addGlobalUnexpectedErrorMessage();
				}

				break;
			}

			case UserPasswordException.PASSWORD_NOT_CHANGEABLE: {
				liferayFacesContext.addGlobalErrorMessage("your-password-cannot-be-changed");

				break;
			}

			case UserPasswordException.PASSWORD_SAME_AS_CURRENT: {
				liferayFacesContext.addGlobalErrorMessage(
					"your-new-password-cannot-be-the-same-as-your-old-password-please-enter-in-a-different-password");

				break;
			}

			case UserPasswordException.PASSWORD_TOO_TRIVIAL: {
				liferayFacesContext.addGlobalErrorMessage("that-password-is-too-trivial");

				break;
			}

			case UserPasswordException.PASSWORD_TOO_YOUNG: {

				try {
					Company company = CompanyLocalServiceUtil.getCompany(companyId);
					PasswordPolicy passwordPolicy = company.getDefaultUser().getPasswordPolicy();
					liferayFacesContext.addGlobalErrorMessage(
						"you-cannot-change-your-password-yet-please-wait-at-least-x-before-changing-your-password-again",
						new Object[] { String.valueOf(passwordPolicy.getMinAge() * 1000) });

				}
				catch (Exception e1) {
					logger.error(e.getMessage(), e);
					liferayFacesContext.addGlobalUnexpectedErrorMessage();
				}

				break;
			}

			case UserPasswordException.PASSWORDS_DO_NOT_MATCH: {
				liferayFacesContext.addGlobalErrorMessage(
					"the-passwords-you-entered-do-not-match-each-other-please-re-enter-your-password");

				break;
			}

			default: {
				break;
			}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			liferayFacesContext.addGlobalUnexpectedErrorMessage();
		}
	}

	public boolean isCaptchaRendered() {

		if (captchaRendered == null) {
			captchaRendered = Boolean.valueOf(GetterUtil.getBoolean(
						PropsUtil.get(PropsKeys.CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT)));
		}

		return captchaRendered.booleanValue();
	}

	public void setRegistrantModelBean(RegistrantModelBean registrantModelBean) {

		// Injected via @ManagedProperty annotation
		this.registrantModelBean = registrantModelBean;
	}

}
