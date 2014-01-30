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
package com.liferay.faces.demos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.liferay.faces.demos.expando.UserExpando;
import com.liferay.faces.demos.model.Registrant;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ListTypeServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.PhoneUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;


/**
 * Service API for adding/updating registrants.
 *
 * @author  Neil Griffin
 */
public class RegistrantServiceUtil {

	private static final Logger logger = LoggerFactory.getLogger(RegistrantServiceUtil.class);

	public static final String CONTACT_CLASS_NAME = "com.liferay.portal.model.Contact";
	public static final String PHONE_CLASS_NAME = "com.liferay.portal.model.Contact.phone";

	public static Registrant add(long creatorUserId, long companyId, Locale locale, Registrant registrant,
		boolean active, boolean autoScreenName, boolean sendEmail) throws PortalException, SystemException {
		boolean autoPassword = false;
		String password1 = registrant.getPassword1();
		String password2 = registrant.getPassword2();
		String screenName = null;

		if (autoScreenName) {
			screenName = StringPool.BLANK;
		}
		else {
			screenName = registrant.getScreenName();
		}

		String emailAddress = registrant.getEmailAddress();
		long facebookId = 0;
		String openId = StringPool.BLANK;
		String firstName = registrant.getFirstName();
		String middleName = registrant.getMiddleName();
		String lastName = registrant.getLastName();
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = 1;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = new long[] {};
		long[] organizationIds = new long[] {};
		long[] roleIds = new long[] {};

		long[] userGroupIds = new long[] {};
		ServiceContext serviceContext = new ServiceContext();

		// Add the user to the Liferay database (create an account).
		User user = UserLocalServiceUtil.addUser(creatorUserId, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, facebookId, openId, locale, firstName, middleName, lastName,
				prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
				roleIds, userGroupIds, sendEmail, serviceContext);

		registrant.setUserId(user.getUserId());
		registrant.setContactId(user.getContactId());

		// Disable the ability to login until someone approves the account.
		if (!active) {
			UserLocalServiceUtil.updateStatus(user.getUserId(), user.getStatus());
		}

		// Add mobile phone.
		updateMobilePhone(creatorUserId, companyId, registrant);

		// Update Expandos
		updateExpandos(companyId, registrant);

		return registrant;
	}

	private static void updateExpandos(long companyId, Registrant registrant) throws PortalException, SystemException {

		// Set the expando column (custom field) values. Note that since the registration portlet is being used
		// by a "Guest" user, we have to trick the Liferay permissionChecker into thinking that the current
		// user is the Administrator user (someone who has the permission to set expando column values).
		PermissionChecker permissionCheckerBackup = PermissionThreadLocal.getPermissionChecker();
		PermissionThreadLocal.setPermissionChecker(getAdministratorPermissionChecker(companyId));

		ExpandoBridge expandoBridge = registrant.getExpandoBridge();
		expandoBridge.setAttribute(UserExpando.COMPANY_NAME.getName(), registrant.getCompanyName());
		expandoBridge.setAttribute(UserExpando.FAVORITE_COLOR.getName(), registrant.getFavoriteColor());

		PermissionThreadLocal.setPermissionChecker(permissionCheckerBackup);
	}

	private static void updateMobilePhone(long creatorUserId, long companyId, Registrant registrant)
		throws SystemException, PortalException {
		List<Phone> phones = new ArrayList<Phone>();
		String mobilePhone = registrant.getMobilePhone();

		if (Validator.isNotNull(mobilePhone)) {

			if (Validator.isPhoneNumber(mobilePhone)) {
				Phone phone = PhoneUtil.create(0L);
				phone.setUserId(registrant.getUserId());
				phone.setCompanyId(companyId);
				phone.setNumber(mobilePhone);
				phone.setPrimary(true);
				phone.setTypeId(getMobilePhoneTypeId());
				phones.add(phone);

				PermissionChecker permissionCheckerBackup = PermissionThreadLocal.getPermissionChecker();
				PermissionThreadLocal.setPermissionChecker(getAdministratorPermissionChecker(companyId));

				// Note: Exception will be thrown if we don't set the PrinicpalThreadLocal name.
				String principalNameBackup = PrincipalThreadLocal.getName();
				PrincipalThreadLocal.setName(creatorUserId);
				UsersAdminUtil.updatePhones(Contact.class.getName(), registrant.getContactId(), phones);
				PrincipalThreadLocal.setName(principalNameBackup);
				PermissionThreadLocal.setPermissionChecker(permissionCheckerBackup);
			}
			else {

				if (!"N/A".equalsIgnoreCase(mobilePhone)) {
					logger.error("Invalid mobilePhone=[{0}] for registrant=[{1}]", mobilePhone,
						registrant.getFullName());
				}
			}
		}
	}

	private static PermissionChecker getAdministratorPermissionChecker(long companyId) throws PortalException,
		SystemException {
		PermissionChecker administratorPermissionChecker = null;
		Role administratorRole = RoleLocalServiceUtil.getRole(companyId, RoleConstants.ADMINISTRATOR);
		List<User> administratorUsers = UserLocalServiceUtil.getRoleUsers(administratorRole.getRoleId());

		if ((administratorUsers != null) && (administratorUsers.size() > 0)) {

			User administratorUser = administratorUsers.get(0);

			try {
				administratorPermissionChecker = PermissionCheckerFactoryUtil.getPermissionCheckerFactory().create(
						administratorUser);
			}
			catch (Exception e) {
				throw new SystemException(e.getMessage(), e);
			}
		}
		else {
			throw new SystemException("Unable to find a user with the Administrator role! Impossible!");
		}

		return administratorPermissionChecker;
	}

	private static int getMobilePhoneTypeId() throws SystemException {
		int phoneTypeId = 0;
		List<ListType> phoneTypes = ListTypeServiceUtil.getListTypes(PHONE_CLASS_NAME);

		for (ListType phoneType : phoneTypes) {

			if (phoneType.getName().equals("mobile-phone")) {
				phoneTypeId = phoneType.getListTypeId();

				break;
			}
		}

		return phoneTypeId;
	}
}
