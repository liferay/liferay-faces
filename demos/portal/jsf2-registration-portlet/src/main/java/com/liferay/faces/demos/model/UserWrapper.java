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
package com.liferay.faces.demos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.theme.ThemeDisplay;

import com.liferay.portlet.expando.model.ExpandoBridge;


/**
 * @author  Neil Griffin
 */
public abstract class UserWrapper implements User {

	// serialVersionUID
	private static final long serialVersionUID = -7549937505863296269L;

	@Override
	public Object clone() {
		return null;
	}

	public int compareTo(User arg0) {

		return getWrapped().compareTo(arg0);
	}

	public boolean hasCompanyMx() {

		return getWrapped().hasCompanyMx();
	}

	public boolean hasCompanyMx(String arg0) {

		return getWrapped().hasCompanyMx(arg0);
	}

	public boolean hasMyPlaces() {

		return getWrapped().hasMyPlaces();
	}

	public boolean hasOrganization() {

		return getWrapped().hasOrganization();
	}

	public boolean hasPrivateLayouts() {

		return getWrapped().hasPrivateLayouts();
	}

	public boolean hasPublicLayouts() {

		return getWrapped().hasPublicLayouts();
	}

	public User toEscapedModel() {

		return getWrapped().toEscapedModel();
	}

	public String toXmlString() {

		return getWrapped().toXmlString();
	}

	public boolean getActive() {
		return getWrapped().getActive();
	}

	public void setActive(boolean arg0) {

		getWrapped().setActive(arg0);
	}

	public boolean getAgreedToTermsOfUse() {
		return getWrapped().getAgreedToTermsOfUse();
	}

	public void setAgreedToTermsOfUse(boolean arg0) {

		getWrapped().setAgreedToTermsOfUse(arg0);
	}

	public Date getBirthday() {

		return getWrapped().getBirthday();
	}

	public void setCachedModel(boolean arg0) {

		getWrapped().setCachedModel(arg0);
	}

	public String getComments() {

		return getWrapped().getComments();
	}

	public void setComments(String arg0) {

		getWrapped().setComments(arg0);
	}

	public long getCompanyId() {

		return getWrapped().getCompanyId();
	}

	public void setCompanyId(long arg0) {

		getWrapped().setCompanyId(arg0);
	}

	public String getCompanyMx() {

		return getWrapped().getCompanyMx();
	}

	public Contact getContact() {

		return getWrapped().getContact();
	}

	public long getContactId() {

		return getWrapped().getContactId();
	}

	public void setContactId(long arg0) {

		getWrapped().setContactId(arg0);
	}

	public Date getCreateDate() {

		return getWrapped().getCreateDate();
	}

	public void setCreateDate(Date arg0) {

		getWrapped().setCreateDate(arg0);
	}

	public boolean isPasswordEncrypted() {

		return getWrapped().isPasswordEncrypted();
	}

	public boolean isPasswordModified() {

		return getWrapped().isPasswordModified();
	}

	public boolean getDefaultUser() {

		return getWrapped().getDefaultUser();
	}

	public void setDefaultUser(boolean arg0) {

		getWrapped().setDefaultUser(arg0);
	}

	public String getDisplayURL(ThemeDisplay arg0) {

		return getWrapped().getDisplayURL(arg0);
	}

	public String getDisplayURL(String arg0, String arg1) {

		return getWrapped().getDisplayURL(arg0, arg1);
	}

	public boolean isActive() {

		return getWrapped().isActive();
	}

	public boolean isAgreedToTermsOfUse() {

		return getWrapped().isAgreedToTermsOfUse();
	}

	public boolean isFemale() {

		return getWrapped().isFemale();
	}

	public boolean isMale() {

		return getWrapped().isMale();
	}

	public String getEmailAddress() {

		return getWrapped().getEmailAddress();
	}

	public void setEmailAddress(String arg0) {

		getWrapped().setEmailAddress(arg0);
	}

	public void setEscapedModel(boolean arg0) {

		getWrapped().setEscapedModel(arg0);
	}

	public ExpandoBridge getExpandoBridge() {

		return getWrapped().getExpandoBridge();
	}

	public int getFailedLoginAttempts() {

		return getWrapped().getFailedLoginAttempts();
	}

	public void setFailedLoginAttempts(int arg0) {

		getWrapped().setFailedLoginAttempts(arg0);
	}

	public boolean getFemale() {

		return getWrapped().getFemale();
	}

	public String getFirstName() {

		return getWrapped().getFirstName();
	}

	public void setFirstName(String arg0) {

		getWrapped().setFirstName(arg0);
	}

	public String getFullName() {

		return getWrapped().getFullName();
	}

	public int getGraceLoginCount() {

		return getWrapped().getGraceLoginCount();
	}

	public void setGraceLoginCount(int arg0) {

		getWrapped().setGraceLoginCount(arg0);
	}

	public String getGreeting() {

		return getWrapped().getGreeting();
	}

	public void setGreeting(String arg0) {

		getWrapped().setGreeting(arg0);
	}

	public Group getGroup() {

		return getWrapped().getGroup();
	}

	public long[] getGroupIds() {

		return getWrapped().getGroupIds();
	}

	public List<Group> getGroups() {

		return getWrapped().getGroups();
	}

	public String getJobTitle() {

		return getWrapped().getJobTitle();
	}

	public void setJobTitle(String arg0) {

		getWrapped().setJobTitle(arg0);
	}

	public boolean isCachedModel() {

		return getWrapped().isCachedModel();
	}

	public boolean isEscapedModel() {

		return getWrapped().isEscapedModel();
	}

	public String getLanguageId() {

		return getWrapped().getLanguageId();
	}

	public void setLanguageId(String arg0) {

		getWrapped().setLanguageId(arg0);
	}

	public Date getLastFailedLoginDate() {

		return getWrapped().getLastFailedLoginDate();
	}

	public void setLastFailedLoginDate(Date arg0) {

		getWrapped().setLastFailedLoginDate(arg0);
	}

	public Date getLastLoginDate() {

		return getWrapped().getLastLoginDate();
	}

	public void setLastLoginDate(Date arg0) {

		getWrapped().setLastLoginDate(arg0);
	}

	public String getLastLoginIP() {

		return getWrapped().getLastLoginIP();
	}

	public void setLastLoginIP(String arg0) {

		getWrapped().setLastLoginIP(arg0);
	}

	public String getLastName() {

		return getWrapped().getLastName();
	}

	public void setLastName(String arg0) {

		getWrapped().setLastName(arg0);
	}

	public Locale getLocale() {

		return getWrapped().getLocale();
	}

	public boolean getLockout() {

		return getWrapped().getLockout();
	}

	public void setLockout(boolean arg0) {

		getWrapped().setLockout(arg0);
	}

	public Date getLockoutDate() {

		return getWrapped().getLockoutDate();
	}

	public void setLockoutDate(Date arg0) {

		getWrapped().setLockoutDate(arg0);
	}

	public String getLogin() throws PortalException, SystemException {

		return getWrapped().getLogin();
	}

	public Date getLoginDate() {

		return getWrapped().getLoginDate();
	}

	public void setLoginDate(Date arg0) {

		getWrapped().setLoginDate(arg0);
	}

	public String getLoginIP() {

		return getWrapped().getLoginIP();
	}

	public void setLoginIP(String arg0) {

		getWrapped().setLoginIP(arg0);
	}

	public boolean getMale() {

		return getWrapped().getMale();
	}

	public String getMiddleName() {

		return getWrapped().getMiddleName();
	}

	public void setMiddleName(String arg0) {

		getWrapped().setMiddleName(arg0);
	}

	public Date getModifiedDate() {

		return getWrapped().getModifiedDate();
	}

	public void setModifiedDate(Date arg0) {

		getWrapped().setModifiedDate(arg0);
	}

	public List<Group> getMyPlaces() {

		return getWrapped().getMyPlaces();
	}

	public List<Group> getMyPlaces(int arg0) {

		return getWrapped().getMyPlaces(arg0);
	}

	public boolean setNew(boolean arg0) {

		return getWrapped().setNew(arg0);
	}

	public String getOpenId() {

		return getWrapped().getOpenId();
	}

	public void setOpenId(String arg0) {

		getWrapped().setOpenId(arg0);
	}

	public long[] getOrganizationIds() {

		return getWrapped().getOrganizationIds();
	}

	public List<Organization> getOrganizations() {

		return getWrapped().getOrganizations();
	}

	public String getPassword() {

		return getWrapped().getPassword();
	}

	public void setPassword(String arg0) {

		getWrapped().setPassword(arg0);
	}

	public boolean getPasswordEncrypted() {

		return getWrapped().getPasswordEncrypted();
	}

	public void setPasswordEncrypted(boolean arg0) {

		getWrapped().setPasswordEncrypted(arg0);
	}

	public boolean getPasswordModified() {

		return getWrapped().getPasswordModified();
	}

	public void setPasswordModified(boolean arg0) {

		getWrapped().setPasswordModified(arg0);
	}

	public Date getPasswordModifiedDate() {

		return getWrapped().getPasswordModifiedDate();
	}

	public void setPasswordModifiedDate(Date arg0) {

		getWrapped().setPasswordModifiedDate(arg0);
	}

	public PasswordPolicy getPasswordPolicy() throws PortalException, SystemException {

		return getWrapped().getPasswordPolicy();
	}

	public boolean getPasswordReset() {

		return getWrapped().getPasswordReset();
	}

	public void setPasswordReset(boolean arg0) {

		getWrapped().setPasswordReset(arg0);
	}

	public String getPasswordUnencrypted() {

		return getWrapped().getPasswordUnencrypted();
	}

	public void setPasswordUnencrypted(String arg0) {

		getWrapped().setPasswordUnencrypted(arg0);
	}

	public long getPortraitId() {

		return getWrapped().getPortraitId();
	}

	public void setPortraitId(long arg0) {

		getWrapped().setPortraitId(arg0);
	}

	public long getPrimaryKey() {

		return getWrapped().getPrimaryKey();
	}

	public void setPrimaryKey(long arg0) {

		getWrapped().setPrimaryKey(arg0);
	}

	public Serializable getPrimaryKeyObj() {

		return getWrapped().getPrimaryKeyObj();
	}

	public int getPrivateLayoutsPageCount() {

		return getWrapped().getPrivateLayoutsPageCount();
	}

	public int getPublicLayoutsPageCount() {

		return getWrapped().getPublicLayoutsPageCount();
	}

	public boolean isDefaultUser() {

		return getWrapped().isDefaultUser();
	}

	public String getReminderQueryAnswer() {

		return getWrapped().getReminderQueryAnswer();
	}

	public void setReminderQueryAnswer(String arg0) {

		getWrapped().setReminderQueryAnswer(arg0);
	}

	public String getReminderQueryQuestion() {

		return getWrapped().getReminderQueryQuestion();
	}

	public void setReminderQueryQuestion(String arg0) {

		getWrapped().setReminderQueryQuestion(arg0);
	}

	public Set<String> getReminderQueryQuestions() throws PortalException, SystemException {

		return getWrapped().getReminderQueryQuestions();
	}

	public long[] getRoleIds() {

		return getWrapped().getRoleIds();
	}

	public List<Role> getRoles() {

		return getWrapped().getRoles();
	}

	public String getScreenName() {

		return getWrapped().getScreenName();
	}

	public void setScreenName(String arg0) {

		getWrapped().setScreenName(arg0);
	}

	public boolean isLockout() {

		return getWrapped().isLockout();
	}

	public boolean isPasswordReset() {

		return getWrapped().isPasswordReset();
	}

	public TimeZone getTimeZone() {

		return getWrapped().getTimeZone();
	}

	public String getTimeZoneId() {

		return getWrapped().getTimeZoneId();
	}

	public void setTimeZoneId(String arg0) {
		getWrapped().setTimeZoneId(arg0);
	}

	public long[] getUserGroupIds() {

		return getWrapped().getUserGroupIds();
	}

	public List<UserGroup> getUserGroups() {

		return getWrapped().getUserGroups();
	}

	public long getUserId() {

		return getWrapped().getUserId();
	}

	public void setUserId(long arg0) {

		getWrapped().setUserId(arg0);
	}

	public String getUuid() {

		return getWrapped().getUuid();
	}

	public void setUuid(String arg0) {

		getWrapped().setUuid(arg0);
	}

	public boolean isNew() {

		return getWrapped().isNew();
	}

	public abstract User getWrapped();

}
