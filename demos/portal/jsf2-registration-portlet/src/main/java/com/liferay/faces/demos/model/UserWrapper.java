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

public abstract class UserWrapper implements User {

	public abstract User getWrapped();
	
	public boolean getActive() {
		return getWrapped().getActive();
	}

	public boolean getAgreedToTermsOfUse() {
		return getWrapped().getAgreedToTermsOfUse();
	}

	public String getComments() {
		
		return getWrapped().getComments();
	}

	public long getCompanyId() {
		
		return getWrapped().getCompanyId();
	}

	public long getContactId() {
		
		return getWrapped().getContactId();
	}

	public Date getCreateDate() {
		
		return getWrapped().getCreateDate();
	}

	public boolean getDefaultUser() {
		
		return getWrapped().getDefaultUser();
	}

	public String getEmailAddress() {
		
		return getWrapped().getEmailAddress();
	}

	public int getFailedLoginAttempts() {
		
		return getWrapped().getFailedLoginAttempts();
	}

	public String getFirstName() {
		
		return getWrapped().getFirstName();
	}

	public int getGraceLoginCount() {
		
		return getWrapped().getGraceLoginCount();
	}

	public String getGreeting() {
		
		return getWrapped().getGreeting();
	}

	public String getJobTitle() {
		
		return getWrapped().getJobTitle();
	}

	public String getLanguageId() {
		
		return getWrapped().getLanguageId();
	}

	public Date getLastFailedLoginDate() {
		
		return getWrapped().getLastFailedLoginDate();
	}

	public Date getLastLoginDate() {
		
		return getWrapped().getLastLoginDate();
	}

	public String getLastLoginIP() {
		
		return getWrapped().getLastLoginIP();
	}

	public String getLastName() {
		
		return getWrapped().getLastName();
	}

	public boolean getLockout() {
		
		return getWrapped().getLockout();
	}

	public Date getLockoutDate() {
		
		return getWrapped().getLockoutDate();
	}

	public Date getLoginDate() {
		
		return getWrapped().getLoginDate();
	}

	public String getLoginIP() {
		
		return getWrapped().getLoginIP();
	}

	public String getMiddleName() {
		
		return getWrapped().getMiddleName();
	}

	public Date getModifiedDate() {
		
		return getWrapped().getModifiedDate();
	}

	public String getOpenId() {
		
		return getWrapped().getOpenId();
	}

	public String getPassword() {
		
		return getWrapped().getPassword();
	}

	public boolean getPasswordEncrypted() {
		
		return getWrapped().getPasswordEncrypted();
	}

	public Date getPasswordModifiedDate() {
		
		return getWrapped().getPasswordModifiedDate();
	}

	public boolean getPasswordReset() {
		
		return getWrapped().getPasswordReset();
	}

	public long getPortraitId() {
		
		return getWrapped().getPortraitId();
	}

	public long getPrimaryKey() {
		
		return getWrapped().getPrimaryKey();
	}

	public String getReminderQueryAnswer() {
		
		return getWrapped().getReminderQueryAnswer();
	}

	public String getReminderQueryQuestion() {
		
		return getWrapped().getReminderQueryQuestion();
	}

	public String getScreenName() {
		
		return getWrapped().getScreenName();
	}

	public String getTimeZoneId() {
		
		return getWrapped().getTimeZoneId();
	}

	public long getUserId() {
		
		return getWrapped().getUserId();
	}

	public String getUuid() {
		
		return getWrapped().getUuid();
	}

	public boolean isActive() {
		
		return getWrapped().isActive();
	}

	public boolean isAgreedToTermsOfUse() {
		
		return getWrapped().isAgreedToTermsOfUse();
	}

	public boolean isDefaultUser() {
		
		return getWrapped().isDefaultUser();
	}

	public boolean isLockout() {
		
		return getWrapped().isLockout();
	}

	public boolean isPasswordEncrypted() {
		
		return getWrapped().isPasswordEncrypted();
	}

	public boolean isPasswordReset() {
		
		return getWrapped().isPasswordReset();
	}

	public void setActive(boolean arg0) {
		
		getWrapped().setActive(arg0);
	}

	public void setAgreedToTermsOfUse(boolean arg0) {
		
		getWrapped().setAgreedToTermsOfUse(arg0);
	}

	public void setComments(String arg0) {
		
		getWrapped().setComments(arg0);
	}

	public void setCompanyId(long arg0) {
		
		getWrapped().setCompanyId(arg0);
	}

	public void setContactId(long arg0) {
		
		getWrapped().setContactId(arg0);
	}

	public void setCreateDate(Date arg0) {
		
		getWrapped().setCreateDate(arg0);
	}

	public void setDefaultUser(boolean arg0) {
		
		getWrapped().setDefaultUser(arg0);
	}

	public void setEmailAddress(String arg0) {
		
		getWrapped().setEmailAddress(arg0);
	}

	public void setFailedLoginAttempts(int arg0) {
		
		getWrapped().setFailedLoginAttempts(arg0);
	}

	public void setFirstName(String arg0) {
		
		getWrapped().setFirstName(arg0);
	}

	public void setGraceLoginCount(int arg0) {
		
		getWrapped().setGraceLoginCount(arg0);
	}

	public void setGreeting(String arg0) {
		
		getWrapped().setGreeting(arg0);
	}

	public void setJobTitle(String arg0) {
		
		getWrapped().setJobTitle(arg0);
	}

	public void setLastFailedLoginDate(Date arg0) {
		
		getWrapped().setLastFailedLoginDate(arg0);
	}

	public void setLastLoginDate(Date arg0) {
		
		getWrapped().setLastLoginDate(arg0);
	}

	public void setLastLoginIP(String arg0) {
		
		getWrapped().setLastLoginIP(arg0);
	}

	public void setLastName(String arg0) {
		
		getWrapped().setLastName(arg0);
	}

	public void setLockout(boolean arg0) {
		
		getWrapped().setLockout(arg0);
	}

	public void setLockoutDate(Date arg0) {
		
		getWrapped().setLockoutDate(arg0);
	}

	public void setLoginDate(Date arg0) {
		
		getWrapped().setLoginDate(arg0);
	}

	public void setLoginIP(String arg0) {
		
		getWrapped().setLoginIP(arg0);
	}

	public void setMiddleName(String arg0) {
		
		getWrapped().setMiddleName(arg0);
	}

	public void setModifiedDate(Date arg0) {
		
		getWrapped().setModifiedDate(arg0);
	}

	public void setOpenId(String arg0) {
		
		getWrapped().setOpenId(arg0);
	}

	public void setPassword(String arg0) {
		
		getWrapped().setPassword(arg0);
	}

	public void setPasswordEncrypted(boolean arg0) {
		
		getWrapped().setPasswordEncrypted(arg0);
	}

	public void setPasswordModifiedDate(Date arg0) {
		
		getWrapped().setPasswordModifiedDate(arg0);
	}

	public void setPasswordReset(boolean arg0) {
		
		getWrapped().setPasswordReset(arg0);
	}

	public void setPortraitId(long arg0) {
		
		getWrapped().setPortraitId(arg0);
	}

	public void setPrimaryKey(long arg0) {
		
		getWrapped().setPrimaryKey(arg0);
	}

	public void setReminderQueryAnswer(String arg0) {
		
		getWrapped().setReminderQueryAnswer(arg0);
	}

	public void setReminderQueryQuestion(String arg0) {
		
		getWrapped().setReminderQueryQuestion(arg0);
	}

	public void setScreenName(String arg0) {
		
		getWrapped().setScreenName(arg0);
	}

	public void setUserId(long arg0) {
		
		getWrapped().setUserId(arg0);
	}

	public void setUuid(String arg0) {
		
		getWrapped().setUuid(arg0);
	}

	public User toEscapedModel() {
		
		return getWrapped().toEscapedModel();
	}

	public ExpandoBridge getExpandoBridge() {
		
		return getWrapped().getExpandoBridge();
	}

	public Serializable getPrimaryKeyObj() {
		
		return getWrapped().getPrimaryKeyObj();
	}

	public boolean isCachedModel() {
		
		return getWrapped().isCachedModel();
	}

	public boolean isEscapedModel() {
		
		return getWrapped().isEscapedModel();
	}

	public boolean isNew() {
		
		return getWrapped().isNew();
	}

	public void setCachedModel(boolean arg0) {
		
		getWrapped().setCachedModel(arg0);
	}

	public void setEscapedModel(boolean arg0) {
		
		getWrapped().setEscapedModel(arg0);
	}

	public boolean setNew(boolean arg0) {
		
		return getWrapped().setNew(arg0);
	}

	public String toXmlString() {
		
		return getWrapped().toXmlString();
	}

	public int compareTo(User arg0) {
		
		return getWrapped().compareTo(arg0);
	}

	public Date getBirthday() {
		
		return getWrapped().getBirthday();
	}

	public String getCompanyMx() {
		
		return getWrapped().getCompanyMx();
	}

	public Contact getContact() {
		
		return getWrapped().getContact();
	}

	public String getDisplayURL(ThemeDisplay arg0) {
		
		return getWrapped().getDisplayURL(arg0);
	}

	public String getDisplayURL(String arg0, String arg1) {
		
		return getWrapped().getDisplayURL(arg0, arg1);
	}

	public boolean getFemale() {
		
		return getWrapped().getFemale();
	}

	public String getFullName() {
		
		return getWrapped().getFullName();
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

	public Locale getLocale() {
		
		return getWrapped().getLocale();
	}

	public String getLogin() throws PortalException, SystemException {
		
		return getWrapped().getLogin();
	}

	public boolean getMale() {
		
		return getWrapped().getMale();
	}

	public List<Group> getMyPlaces() {
		
		return getWrapped().getMyPlaces();
	}

	public List<Group> getMyPlaces(int arg0) {
		
		return getWrapped().getMyPlaces(arg0);
	}

	public long[] getOrganizationIds() {
		
		return getWrapped().getOrganizationIds();
	}

	public List<Organization> getOrganizations() {
		
		return getWrapped().getOrganizations();
	}

	public boolean getPasswordModified() {
		
		return getWrapped().getPasswordModified();
	}

	public PasswordPolicy getPasswordPolicy() throws PortalException,
			SystemException {
		
		return getWrapped().getPasswordPolicy();
	}

	public String getPasswordUnencrypted() {
		
		return getWrapped().getPasswordUnencrypted();
	}

	public int getPrivateLayoutsPageCount() {
		
		return getWrapped().getPrivateLayoutsPageCount();
	}

	public int getPublicLayoutsPageCount() {
		
		return getWrapped().getPublicLayoutsPageCount();
	}

	public Set<String> getReminderQueryQuestions() throws PortalException,
			SystemException {
		
		return getWrapped().getReminderQueryQuestions();
	}

	public long[] getRoleIds() {
		
		return getWrapped().getRoleIds();
	}

	public List<Role> getRoles() {
		
		return getWrapped().getRoles();
	}

	public TimeZone getTimeZone() {
		
		return getWrapped().getTimeZone();
	}

	public long[] getUserGroupIds() {
		
		return getWrapped().getUserGroupIds();
	}

	public List<UserGroup> getUserGroups() {
		
		return getWrapped().getUserGroups();
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

	public boolean isFemale() {
		
		return getWrapped().isFemale();
	}

	public boolean isMale() {
		
		return getWrapped().isMale();
	}

	public boolean isPasswordModified() {
		
		return getWrapped().isPasswordModified();
	}

	public void setLanguageId(String arg0) {
		
		getWrapped().setLanguageId(arg0);
	}

	public void setPasswordModified(boolean arg0) {
		
		getWrapped().setPasswordModified(arg0);
	}

	public void setPasswordUnencrypted(String arg0) {
		
		getWrapped().setPasswordUnencrypted(arg0);
	}

	public void setTimeZoneId(String arg0) {
		getWrapped().setTimeZoneId(arg0);
	}

}
