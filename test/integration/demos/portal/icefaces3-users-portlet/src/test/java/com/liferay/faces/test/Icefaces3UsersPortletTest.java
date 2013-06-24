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
package com.liferay.faces.test;

import java.util.logging.Level;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.liferay.faces.test.util.TesterBase;


/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class Icefaces3UsersPortletTest extends TesterBase {

	// elements for Icefaces3Users
	private static final String portletTitleTextXpath = "//span[@class='portlet-title-text']";

	private static final String menuButtonXpath = "//a[contains(@id,'jsf2portlet') and contains(@id,'menuButton')]";
	private static final String menuPreferencesXpath =
		"//a[contains(@id,'jsf2portlet') and contains(@id,'menu_preferences')]";

	private static final String logoXpath = "//img[contains(@src,'liferay-logo.png')]";

	// Elements for reseting the John Adams user before running the test
	private static final String controlPanelTestSetupXpath = "//span[text()=' Control Panel ']";
	private static final String usersAndOrganizationsLinkTestSetupXpath = "//a[text()=' Users and Organizations ']";
	private static final String searchAllUsersLinkTestSetupXpath = "//a[text()='Search All Users']";
	private static final String advancedSearchLinkTestSetupXpath = "//a[contains(text(), 'Advanced')]";
	private static final String selectStatusTestSetupXpath = "//select[contains(@id, 'status')]";
	private static final String optionInactiveTestSetupXpath = "//option[text()=' Inactive ']";
	private static final String johnAdamsTestSetupXpath = "//a[text()='john.adams']";
	private static final String johnAdamsMenuTestSetupXpath =
		"//a[contains(@id, 'john-adams_menuButton')]/span[text()='Actions']"; ///child::img[contains(@src, 'activate.png')]";
	private static final String activateJohnAdamsTestSetupXpath = "//a[contains(@id, 'john-adams_menu_activate')]"; ///child::img[contains(@src, 'activate.png')]";
	private static final String deleteLinkTestSetupXpath = "//span[@class='taglib-text' and text()='Delete']";
	private static final String emailInputTestSetupXpath = "//input[contains(@id, 'emailAddress')]";
	private static final String firstNameInputTestSetupXpath = "//input[contains(@id, 'firstName')]";
	private static final String middleNameInputTestSetupXpath = "//input[contains(@id, 'middleName')]";
	private static final String lastNameInputTestSetupXpath = "//input[contains(@id, 'lastName')]";
	private static final String jobTitleInputTestSetupXpath = "//input[contains(@id, 'jobTitle')]";
	private static final String saveButtonTestSetupXpath = "//input[@type='submit' and @value='Save']";
	private static final String errorMessageTestSetupXpath =
		"//div[@class='portlet-msg-error' and text()='Your request failed to complete.']";
	@FindBy(xpath = errorMessageTestSetupXpath)
	private static WebElement errorMessageTestSetup;
	private static final String errorPassword1TestSetupXpath =
		"//input[contains(@id, 'password1') and @type='password']";
	@FindBy(xpath = errorPassword1TestSetupXpath)
	private static WebElement errorPassword1TestSetup;
	private static final String errorPassword2TestSetupXpath =
		"//input[contains(@id, 'password2') and @type='password']";
	@FindBy(xpath = errorPassword2TestSetupXpath)
	private static WebElement errorPassword2TestSetup;

	// Elements for users' list
	private static final String screenNameColumnHeaderXpath =
		"//span[contains(@id,':users:screenName_text') and text()='Screen Name']";
	private static final String screenNameColumnSortIconXpath =
		"//div[contains(@id,'users:screenName')]/child::span[@class='ui-header-right']/child::span[@class='ui-sortable-control']/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String screenNameColumnFilterXpath = "//input[contains(@id,'users:screenName_filter')]";

	private static final String lastNameColumnHeaderXpath =
		"//span[contains(@id,':users:lastName_text') and text()='Last Name']";
	private static final String lastNameColumnSortIconXpath =
		"//div[contains(@id,'users:lastName')]/child::span[@class='ui-header-right']/child::span[@class='ui-sortable-control']/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String lastNameColumnFilterXpath = "//input[contains(@id,'users:lastName_filter')]";

	private static final String firstNameColumnHeaderXpath =
		"//span[contains(@id,':users:firstName_text') and text()='First Name']";
	private static final String firstNameColumnSortIconXpath =
		"//div[contains(@id,'users:firstName')]/child::span[@class='ui-header-right']/child::span[@class='ui-sortable-control']/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String firstNameColumnFilterXpath = "//input[contains(@id,'users:firstName_filter')]";

	private static final String middleNameColumnHeaderXpath =
		"//span[contains(@id,':users:middleName_text') and text()='Middle Name']";
	private static final String middleNameColumnSortIconXpath =
		"//div[contains(@id,'users:middleName')]/child::span[@class='ui-header-right']/child::span[@class='ui-sortable-control']/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String middleNameColumnFilterXpath = "//input[contains(@id,'users:middleName_filter')]";

	private static final String emailAddressColumnHeaderXpath =
		"//span[contains(@id,':users:emailAddress_text') and text()='Email Address']";
	private static final String emailAddressColumnSortIconXpath =
		"//div[contains(@id,'users:emailAddress')]/child::span[@class='ui-header-right']/child::span[@class='ui-sortable-control']/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String emailAddressColumnFilterXpath = "//input[contains(@id,'users:emailAddress_filter')]";

	private static final String jobTitleColumnHeaderXpath =
		"//span[contains(@id,':users:jobTitle_text') and text()='Job Title']";
	private static final String jobTitleColumnSortIconXpath =
		"//div[contains(@id,'users:jobTitle')]/child::span[@class='ui-header-right']/child::span[@class='ui-sortable-control']/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String jobTitleColumnFilterXpath = "//input[contains(@id,'users:jobTitle_filter')]";

	// John Adams users' list view
	private static final String johnAdamsUserScreenNameCellXpath =
		"//span[contains(@id,':screenNameCell') and text()='john.adams']";
	private static final String johnAdamsUserLastNameCellXpath =
		"//span[contains(@id,':lastNameCell') and text()='Adams']";
	private static final String johnAdamsUserFirstNameCellXpath =
		"//span[contains(@id,':firstNameCell') and text()='John']";
	private static final String johnAdamsUserEmailAddressCellXpath = "//a[@href='mailto:john.adams@liferay.com']";

	// Samuel Adams screen name in users' list
	private static final String samuelAdamsUserScreenNameCellXpath =
		"//span[contains(@id,':screenNameCell') and text()='samuel.adams']";

	// Elements for column 1 of John Adam's detailed user view
	private static final String firstNameFieldXpath =
		"//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':firstName_input')]";
	private static final String firstNameFieldErrorXpath =
		"//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':firstName_input')]/../../following-sibling::span[1]/span[@class='portlet-msg-error' and text()='Value is required']";
	private static final String middleNameFieldXpath =
		"//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':middleName_input')]";
	private static final String lastNameFieldXpath =
		"//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':lastName_input')]";
	private static final String lastNameFieldErrorXpath =
		"//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':lastName_input')]/../../following-sibling::span[1]/span[@class='portlet-msg-error' and text()='Value is required']";
	private static final String emailAddressFieldXpath =
		"//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':emailAddress_input')]";
	private static final String emailAddressFieldErrorXpath =
		"//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':emailAddress_input')]/../../following-sibling::span[1]/span[@class='portlet-msg-error' and text()='Value is required']";
	private static final String jobTitleFieldXpath =
		"//input[contains(@class, 'ui-inputfield') and contains(@class, 'ui-textentry') and contains(@id,':jobTitle_input')]";
	private static final String submitButtonXpath =
		"//span[text()='Submit']/parent::button[contains(@name, ':pushButtonSubmit_button') and @type='button']";
	private static final String cancelButtonXpath =
		"//span[text()='Cancel']/parent::button[contains(@name, ':pushButtonCancel_button') and @type='button']";

	// Elements for column 2 of John Adam's detailed user view
	private static final String dropdownActiveFieldXpath = "//select[contains(@id,':s1')]/option[text()='Active']";
	private static final String dropdownActiveSelectedFieldXpath =
		"//select[contains(@id,':s1')]/option[@selected='selected' and text()='Active']";
	private static final String dropdownInactiveFieldXpath = "//select[contains(@id,':s1')]/option[text()='Inactive']";
	private static final String dropdownInactiveSelectedFieldXpath =
		"//select[contains(@id,':s1')]/option[@selected='selected' and text()='Inactive']";

	// Elements for column 3 of John Adam's detailed user view
	private static final String portraitXpath =
		"//input[@type='file' and contains(@id, ':fileEntryComp')]/../../../img[contains(@src, 'user_male_portrait')]";
	private static final String changedPortraitXpath =
		"//input[@type='file' and contains(@id, ':fileEntryComp')]/../../../img[not(contains(@src, 'user_male_portrait'))]";
	private static final String fileEntryXpath = "//input[@type='file' and contains(@id, ':fileEntryComp')]";
	private static final String fileUploadButtonXpath = "//input[@type='submit' and contains(@value, 'Upload Image')]";

	// Elements for the changed version of John Adams
	private static final String changedUserLastNameCellXpath = "//span[contains(@id,':lastNameCell') and text()='Aa']";
	private static final String changedUserFirstNameCellXpath =
		"//span[contains(@id,':firstNameCell') and text()='Aa']";
	private static final String changedUserMiddleNameCellXpath =
		"//span[contains(@id,':middleNameCell') and text()='Aa']";
	private static final String changedUserEmailAddressCellXpath = "//a[@href='mailto:A@A.com']";
	private static final String changedUserJobTitleCellXpath = "//span[contains(@id,':jobTitleCell') and text()='Aa']";

	static final String url =
			baseUrl + "/group/control_panel/manage?p_p_id=1_WAR_icefaces3usersportlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&doAsGroupId=10180&refererPlid=10183";

	@FindBy(xpath = portletTitleTextXpath)
	private WebElement portletTitleText;
	@FindBy(xpath = menuButtonXpath)
	private WebElement menuButton;
	@FindBy(xpath = menuPreferencesXpath)
	private WebElement menuPreferences;
	@FindBy(xpath = logoXpath)
	private WebElement logo;
	@FindBy(xpath = controlPanelTestSetupXpath)
	private WebElement controlPanelTestSetup;
	@FindBy(xpath = usersAndOrganizationsLinkTestSetupXpath)
	private WebElement usersAndOrganizationsLinkTestSetup;
	@FindBy(xpath = searchAllUsersLinkTestSetupXpath)
	private WebElement searchAllUsersLinkTestSetup;
	@FindBy(xpath = advancedSearchLinkTestSetupXpath)
	private WebElement advancedSearchLinkTestSetup;
	@FindBy(xpath = selectStatusTestSetupXpath)
	private WebElement selectStatusTestSetup;
	@FindBy(xpath = optionInactiveTestSetupXpath)
	private WebElement optionInactiveTestSetup;
	@FindBy(xpath = johnAdamsTestSetupXpath)
	private WebElement johnAdamsTestSetup;
	@FindBy(xpath = johnAdamsMenuTestSetupXpath)
	private WebElement johnAdamsMenuTestSetup;
	@FindBy(xpath = activateJohnAdamsTestSetupXpath)
	private WebElement activateJohnAdamsTestSetup;
	@FindBy(xpath = deleteLinkTestSetupXpath)
	private WebElement deleteLinkTestSetup;
	@FindBy(xpath = emailInputTestSetupXpath)
	private WebElement emailInputTestSetup;
	@FindBy(xpath = firstNameInputTestSetupXpath)
	private WebElement firstNameInputTestSetup;
	@FindBy(xpath = middleNameInputTestSetupXpath)
	private WebElement middleNameInputTestSetup;
	@FindBy(xpath = lastNameInputTestSetupXpath)
	private WebElement lastNameInputTestSetup;
	@FindBy(xpath = jobTitleInputTestSetupXpath)
	private WebElement jobTitleInputTestSetup;
	@FindBy(xpath = saveButtonTestSetupXpath)
	private WebElement saveButtonTestSetup;
	@FindBy(xpath = screenNameColumnHeaderXpath)
	private WebElement screenNameColumnHeader;
	@FindBy(xpath = screenNameColumnSortIconXpath)
	private WebElement screenNameColumnSortIcon;
	@FindBy(xpath = screenNameColumnFilterXpath)
	private WebElement screenNameColumnFilter;
	@FindBy(xpath = lastNameColumnHeaderXpath)
	private WebElement lastNameColumnHeader;
	@FindBy(xpath = lastNameColumnSortIconXpath)
	private WebElement lastNameColumnSortIcon;
	@FindBy(xpath = lastNameColumnFilterXpath)
	private WebElement lastNameColumnFilter;
	@FindBy(xpath = firstNameColumnHeaderXpath)
	private WebElement firstNameColumnHeader;
	@FindBy(xpath = firstNameColumnSortIconXpath)
	private WebElement firstNameColumnSortIcon;
	@FindBy(xpath = firstNameColumnFilterXpath)
	private WebElement firstNameColumnFilter;
	@FindBy(xpath = middleNameColumnHeaderXpath)
	private WebElement middleNameColumnHeader;
	@FindBy(xpath = middleNameColumnSortIconXpath)
	private WebElement middleNameColumnSortIcon;
	@FindBy(xpath = middleNameColumnFilterXpath)
	private WebElement middleNameColumnFilter;
	@FindBy(xpath = emailAddressColumnHeaderXpath)
	private WebElement emailAddressColumnHeader;
	@FindBy(xpath = emailAddressColumnSortIconXpath)
	private WebElement emailAddressColumnSortIcon;
	@FindBy(xpath = emailAddressColumnFilterXpath)
	private WebElement emailAddressColumnFilter;
	@FindBy(xpath = jobTitleColumnHeaderXpath)
	private WebElement jobTitleColumnHeader;
	@FindBy(xpath = jobTitleColumnSortIconXpath)
	private WebElement jobTitleColumnSortIcon;
	@FindBy(xpath = jobTitleColumnFilterXpath)
	private WebElement jobTitleColumnFilter;
	@FindBy(xpath = johnAdamsUserScreenNameCellXpath)
	private WebElement johnAdamsUserScreenNameCell;
	@FindBy(xpath = johnAdamsUserLastNameCellXpath)
	private WebElement johnAdamsUserLastNameCell;
	@FindBy(xpath = johnAdamsUserFirstNameCellXpath)
	private WebElement johnAdamsUserFirstNameCell;
	@FindBy(xpath = johnAdamsUserEmailAddressCellXpath)
	private WebElement johnAdamsUserEmailAddressCell;
	@FindBy(xpath = samuelAdamsUserScreenNameCellXpath)
	private WebElement samuelAdamsUserScreenNameCell;
	@FindBy(xpath = firstNameFieldXpath)
	private WebElement firstNameField;
	@FindBy(xpath = firstNameFieldErrorXpath)
	private WebElement firstNameFieldError;
	@FindBy(xpath = middleNameFieldXpath)
	private WebElement middleNameField;
	@FindBy(xpath = lastNameFieldXpath)
	private WebElement lastNameField;
	@FindBy(xpath = lastNameFieldErrorXpath)
	private WebElement lastNameFieldError;
	@FindBy(xpath = emailAddressFieldXpath)
	private WebElement emailAddressField;
	@FindBy(xpath = emailAddressFieldErrorXpath)
	private WebElement emailAddressFieldError;
	@FindBy(xpath = jobTitleFieldXpath)
	private WebElement jobTitleField;
	@FindBy(xpath = submitButtonXpath)
	private WebElement submitButton;
	@FindBy(xpath = cancelButtonXpath)
	private WebElement cancelButton;
	@FindBy(xpath = dropdownActiveFieldXpath)
	private WebElement dropdownActiveField;
	@FindBy(xpath = dropdownActiveSelectedFieldXpath)
	private WebElement dropdownActiveSelectedField;
	@FindBy(xpath = dropdownInactiveFieldXpath)
	private WebElement dropdownInactiveField;
	@FindBy(xpath = dropdownInactiveSelectedFieldXpath)
	private WebElement dropdownInactiveSelectedField;
	@FindBy(xpath = portraitXpath)
	private WebElement portrait;
	@FindBy(xpath = changedPortraitXpath)
	private WebElement changedPortrait;
	@FindBy(xpath = fileEntryXpath)
	private WebElement fileEntry;
	@FindBy(xpath = fileUploadButtonXpath)
	private WebElement fileUploadButton;
	@FindBy(xpath = changedUserLastNameCellXpath)
	private WebElement changedUserLastNameCell;
	@FindBy(xpath = changedUserFirstNameCellXpath)
	private WebElement changedUserFirstNameCell;
	@FindBy(xpath = changedUserMiddleNameCellXpath)
	private WebElement changedUserMiddleNameCell;
	@FindBy(xpath = changedUserEmailAddressCellXpath)
	private WebElement changedUserEmailAddressCell;
	@FindBy(xpath = changedUserJobTitleCellXpath)
	private WebElement changedUserJobTitleCell;

	@Test
	@RunAsClient
	@InSequence(0)
	public void testSetup() throws Exception {

		browser.manage().deleteAllCookies();
		signIn();
		controlPanelTestSetup.click();
		waitForElement(usersAndOrganizationsLinkTestSetupXpath);
		usersAndOrganizationsLinkTestSetup.click();
		waitForElement(searchAllUsersLinkTestSetupXpath);
		searchAllUsersLinkTestSetup.click();
		Thread.sleep(1000);

		if (isThere(advancedSearchLinkTestSetupXpath) && advancedSearchLinkTestSetup.isDisplayed()) {
			advancedSearchLinkTestSetup.click();
		}

		waitForElement(selectStatusTestSetupXpath);

		selectStatusTestSetup.click();
		(new Actions(browser)).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.TAB).perform();
		selectStatusTestSetup.submit();

		if (isThere(johnAdamsTestSetupXpath)) {
			johnAdamsMenuTestSetup.click();
			activateJohnAdamsTestSetup.click();
		}

		usersAndOrganizationsLinkTestSetup.click();
		waitForElement(johnAdamsTestSetupXpath);
		johnAdamsTestSetup.click();

		waitForElement(emailInputTestSetupXpath);

		if (isThere(deleteLinkTestSetupXpath) && deleteLinkTestSetup.isDisplayed()) {
			deleteLinkTestSetup.click();
		}

		emailInputTestSetup.clear();
		emailInputTestSetup.sendKeys("john.adams@liferay.com");
		firstNameInputTestSetup.clear();
		firstNameInputTestSetup.sendKeys("John");
		middleNameInputTestSetup.clear();
		lastNameInputTestSetup.clear();
		lastNameInputTestSetup.sendKeys("Adams");
		jobTitleInputTestSetup.clear();
		saveButtonTestSetup.click();

		if (isThere(errorMessageTestSetupXpath)) {
			errorPassword1TestSetup.clear();
			errorPassword1TestSetup.sendKeys("testtest");
			errorPassword2TestSetup.clear();
			errorPassword2TestSetup.sendKeys("testtest");
			saveButtonTestSetup.click();
		}
	}

	@Test
	@RunAsClient
	@InSequence(1000)
	public void usersListView() throws Exception {

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletTitleText.getText() = " + portletTitleText.getText());

		assertTrue("portletTitleText.isDisplayed()", portletTitleText.isDisplayed());

		logger.log(Level.INFO, "screenNameColumnHeader.isDisplayed() = " + screenNameColumnHeader.isDisplayed());
		assertTrue("The Screen Name Column Header should be displayed on the page at this point but it is not.",
			screenNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnSortIcon.isDisplayed() = " + screenNameColumnSortIcon.isDisplayed());
		assertTrue("The Screen Name Column Sort Icon should be displayed on the page at this point but it is not.",
			screenNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnFilter.isDisplayed() = " + screenNameColumnFilter.isDisplayed());
		assertTrue("The Screen Name Column Text Filter should be displayed on the page at this point but it is not.",
			screenNameColumnFilter.isDisplayed());

		logger.log(Level.INFO, "lastNameColumnHeader.isDisplayed() = " + lastNameColumnHeader.isDisplayed());
		assertTrue("The Last Name Column Header should be displayed on the page at this point but it is not.",
			lastNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "lastNameColumnSortIcon.isDisplayed() = " + lastNameColumnSortIcon.isDisplayed());
		assertTrue("The Last Name Column Sort Icon should be displayed on the page at this point but it is not.",
			lastNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "lastNameColumnFilter.isDisplayed() = " + lastNameColumnFilter.isDisplayed());
		assertTrue("The Last Name Column Text Filter should be displayed on the page at this point but it is not.",
			lastNameColumnFilter.isDisplayed());

		logger.log(Level.INFO, "firstNameColumnHeader.isDisplayed() = " + firstNameColumnHeader.isDisplayed());
		assertTrue("The First Name Column Header should be displayed on the page at this point but it is not.",
			firstNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "firstNameColumnSortIcon.isDisplayed() = " + firstNameColumnSortIcon.isDisplayed());
		assertTrue("The First Name Column Sort Icon should be displayed on the page at this point but it is not.",
			firstNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "firstNameColumnFilter.isDisplayed() = " + firstNameColumnFilter.isDisplayed());
		assertTrue("The First Name Column Text Filter should be displayed on the page at this point but it is not.",
			firstNameColumnFilter.isDisplayed());

		logger.log(Level.INFO, "middleNameColumnHeader.isDisplayed() = " + middleNameColumnHeader.isDisplayed());
		assertTrue("The Middle Name Column Header should be displayed on the page at this point but it is not.",
			middleNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "middleNameColumnSortIcon.isDisplayed() = " + middleNameColumnSortIcon.isDisplayed());
		assertTrue("The Middle Name Column Sort Icon should be displayed on the page at this point but it is not.",
			middleNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "middleNameColumnFilter.isDisplayed() = " + middleNameColumnFilter.isDisplayed());
		assertTrue("The Middle Name Column Text Filter should be displayed on the page at this point but it is not.",
			middleNameColumnFilter.isDisplayed());

		logger.log(Level.INFO, "emailAddressColumnHeader.isDisplayed() = " + emailAddressColumnHeader.isDisplayed());
		assertTrue("The Email Address Column Header should be displayed on the page at this point but it is not.",
			emailAddressColumnHeader.isDisplayed());
		logger.log(Level.INFO,
			"emailAddressColumnSortIcon.isDisplayed() = " + emailAddressColumnSortIcon.isDisplayed());
		assertTrue("The Email Address Column Sort Icon should be displayed on the page at this point but it is not.",
			emailAddressColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "emailAddressColumnFilter.isDisplayed() = " + emailAddressColumnFilter.isDisplayed());
		assertTrue("The Email Address Column Text Filter should be displayed on the page at this point but it is not.",
			emailAddressColumnFilter.isDisplayed());

		logger.log(Level.INFO, "jobTitleColumnHeader.isDisplayed() = " + jobTitleColumnHeader.isDisplayed());
		assertTrue("The Job Title Column Header should be displayed on the page at this point but it is not.",
			jobTitleColumnHeader.isDisplayed());
		logger.log(Level.INFO, "jobTitleColumnSortIcon.isDisplayed() = " + jobTitleColumnSortIcon.isDisplayed());
		assertTrue("The Job Title Column Sort Icon should be displayed on the page at this point but it is not.",
			jobTitleColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "jobTitleColumnFilter.isDisplayed() = " + jobTitleColumnFilter.isDisplayed());
		assertTrue("The Job Title Column Text Filter should be displayed on the page at this point but it is not.",
			jobTitleColumnFilter.isDisplayed());

		logger.log(Level.INFO,
			"johnAdamsUserScreenNameCell.isDisplayed() = " + johnAdamsUserScreenNameCell.isDisplayed());
		assertTrue(
			"The Screen Name Cell of the John Adams user should be displayed on the page as john.adams at this point but it is not.",
			johnAdamsUserScreenNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserLastNameCell.isDisplayed() = " + johnAdamsUserLastNameCell.isDisplayed());
		assertTrue(
			"The Last Name Cell of the John Adams user should be displayed on the page as Adams at this point but it is not.",
			johnAdamsUserLastNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserLastNameCell.isDisplayed() = " + johnAdamsUserFirstNameCell.isDisplayed());
		assertTrue(
			"The First Name Cell of the John Adams user should be displayed on the page as John at this point but it is not.",
			johnAdamsUserFirstNameCell.isDisplayed());
		logger.log(Level.INFO,
			"johnAdamsUserEmailAddressCell.isDisplayed() = " + johnAdamsUserEmailAddressCell.isDisplayed());
		assertTrue(
			"The Email Address Cell of the John Adams user should be displayed on the page as john.adams@liferay.com at this point but it is not.",
			johnAdamsUserEmailAddressCell.isDisplayed());
	}

	@Test
	@RunAsClient
	@InSequence(2000)
	public void specificUserView() throws Exception {

		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();

		waitForElement(firstNameFieldXpath);

		logger.log(Level.INFO, "firstNameField.isDisplayed() = " + firstNameField.isDisplayed());
		assertTrue("The First Name Text Entry Field should be displayed on the page at this point but it is not.",
			firstNameField.isDisplayed());
		logger.log(Level.INFO, "middleNameField.isDisplayed() = " + middleNameField.isDisplayed());
		assertTrue("The Middle Name Text Entry Field should be displayed on the page at this point but it is not.",
			middleNameField.isDisplayed());
		logger.log(Level.INFO, "lastNameField.isDisplayed() = " + lastNameField.isDisplayed());
		assertTrue("The Last Name Text Entry Field should be displayed on the page at this point but it is not.",
			lastNameField.isDisplayed());
		logger.log(Level.INFO, "emailAddressField.isDisplayed() = " + emailAddressField.isDisplayed());
		assertTrue("The Email Address Text Entry Field should be displayed on the page at this point but it is not.",
			emailAddressField.isDisplayed());
		logger.log(Level.INFO, "jobTitleField.isDisplayed() = " + jobTitleField.isDisplayed());
		assertTrue("The Job Title Text Entry Field should be displayed on the page at this point but it is not.",
			jobTitleField.isDisplayed());
		logger.log(Level.INFO, "submitButton.isDisplayed() = " + submitButton.isDisplayed());
		assertTrue("The Submit Button should be displayed on the page at this point but it is not.",
			submitButton.isDisplayed());
		logger.log(Level.INFO, "cancelButton.isDisplayed() = " + cancelButton.isDisplayed());
		assertTrue("The Cancel Button should be displayed on the page at this point but it is not.",
			cancelButton.isDisplayed());

		logger.log(Level.INFO, "dropdownActiveField.isDisplayed() = " + dropdownActiveField.isDisplayed());
		assertTrue(
			"The The Dropdown Menu should be displayed  on the page and Active should be the selected item at this point but it is not.",
			dropdownActiveField.isDisplayed());

		logger.log(Level.INFO, "portrait.isDisplayed() = " + portrait.isDisplayed());
		assertTrue("The User Portrait should be displayed on the page at this point but it is not.",
			portrait.isDisplayed());
		logger.log(Level.INFO, "fileEntry.isDisplayed() = " + fileEntry.isDisplayed());
		assertTrue("The File Entry should be displayed on the page at this point but it is not.",
			fileEntry.isDisplayed());
		logger.log(Level.INFO, "fileUploadButton.isDisplayed() = " + fileUploadButton.isDisplayed());
		assertTrue("The File Upload Button should be displayed on the page at this point but it is not.",
			fileUploadButton.isDisplayed());

	}

	@Test
	@RunAsClient
	@InSequence(3000)
	public void checkRequiredFieldsAndCancel() throws Exception {

		firstNameField.clear();
		middleNameField.clear();
		lastNameField.clear();
		emailAddressField.clear();
		jobTitleField.clear();
		submitButton.click();

		waitForElement(firstNameFieldErrorXpath);

		logger.log(Level.INFO, "firstNameFieldError.isDisplayed() = " + firstNameFieldError.isDisplayed());
		assertTrue("The First Name Validation Error should be displayed on the page at this point but it is not.",
			firstNameFieldError.isDisplayed());
		logger.log(Level.INFO, "lastNameFieldError.isDisplayed() = " + lastNameFieldError.isDisplayed());
		assertTrue("The Last Name Validation Error should be displayed on the page at this point but it is not.",
			lastNameFieldError.isDisplayed());
		logger.log(Level.INFO, "emailAddressFieldError.isDisplayed() = " + emailAddressFieldError.isDisplayed());
		assertTrue("The Email Address Validation Error should be displayed on the page at this point but it is not.",
			emailAddressFieldError.isDisplayed());

		cancelButton.click();

	}

	@Test
	@RunAsClient
	@InSequence(4000)
	public void screenNameColumnFilter() throws Exception {

		waitForElement(screenNameColumnFilterXpath);
		screenNameColumnFilter.sendKeys("john.adams");

		Thread.sleep(1000);

		logger.log(Level.INFO,
			"johnAdamsUserScreenNameCell.isDisplayed() = " + johnAdamsUserScreenNameCell.isDisplayed());
		assertTrue(
			"The Screen Name Cell of the John Adams user should be displayed on the page as john.adams at this point but it is not.",
			johnAdamsUserScreenNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserLastNameCell.isDisplayed() = " + johnAdamsUserLastNameCell.isDisplayed());
		assertTrue(
			"The Last Name Cell of the John Adams user should be displayed on the page as John at this point but it is not.",
			johnAdamsUserLastNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserLastNameCell.isDisplayed() = " + johnAdamsUserFirstNameCell.isDisplayed());
		assertTrue(
			"The First Name Cell of the John Adams user should be displayed on the page as Adams at this point but it is not.",
			johnAdamsUserFirstNameCell.isDisplayed());
		logger.log(Level.INFO,
			"johnAdamsUserEmailAddressCell.isDisplayed() = " + johnAdamsUserEmailAddressCell.isDisplayed());
		assertTrue(
			"The Email Address Cell of the John Adams user should be displayed on the page as john.adams@liferay.com at this point but it is not.",
			johnAdamsUserEmailAddressCell.isDisplayed());

		logger.log(Level.INFO,
			"isThere(samuelAdamsUserScreenNameCellXpath) = " + isThere(samuelAdamsUserScreenNameCellXpath));
		assertFalse("The row for Samuel Adams should NOT be displayed now becuase of filtering, but it is displayed.",
			isThere(samuelAdamsUserScreenNameCellXpath));

		for (int i = 0; i < "john.adams".length(); i++) {
			screenNameColumnFilter.sendKeys(Keys.BACK_SPACE);
		}

		Thread.sleep(1000);

		logger.log(Level.INFO,
			"isThere(samuelAdamsUserScreenNameCellXpath) = " + isThere(samuelAdamsUserScreenNameCellXpath));
		assertTrue("The row for Samuel Adams should be displayed now becuase of filtering, but it is not.",
			isThere(samuelAdamsUserScreenNameCellXpath));

	}

	@Test
	@RunAsClient
	@InSequence(6000)
	public void testImageChosenButNotUploaded() throws Exception {

		waitForElement(johnAdamsUserScreenNameCellXpath);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();

		waitForElement(fileEntryXpath);

		fileEntry.sendKeys(getPathToJerseyFile());

		logger.log(Level.INFO, "portrait.isDisplayed() = " + portrait.isDisplayed());
		assertTrue("The User Portrait should be displayed on the page at this point but it is not.",
			portrait.isDisplayed());

		cancelButton.click();

		waitForElement(johnAdamsUserScreenNameCellXpath);

		logger.log(Level.INFO, "screenNameColumnHeader.isDisplayed() = " + screenNameColumnHeader.isDisplayed());
		assertTrue("The Screen Name Column Header should be displayed on the page at this point but it is not.",
			screenNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnSortIcon.isDisplayed() = " + screenNameColumnSortIcon.isDisplayed());
		assertTrue("The Screen Name Column Sort Icon should be displayed on the page at this point but it is not.",
			screenNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnFilter.isDisplayed() = " + screenNameColumnFilter.isDisplayed());
		assertTrue("The Screen Name Column Text Filter should be displayed on the page at this point but it is not.",
			screenNameColumnFilter.isDisplayed());

		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();

		waitForElement(fileEntryXpath);

		logger.log(Level.INFO, "portrait.isDisplayed() = " + portrait.isDisplayed());
		assertTrue("The User Portrait should be displayed on the page at this point but it is not.",
			portrait.isDisplayed());

		fileEntry.sendKeys(getPathToJerseyFile());

		logger.log(Level.INFO, "portrait.isDisplayed() = " + portrait.isDisplayed());
		assertTrue("The User Portrait should be displayed on the page at this point but it is not.",
			portrait.isDisplayed());

		submitButton.click();

		waitForElement(johnAdamsUserScreenNameCellXpath);

		logger.log(Level.INFO, "screenNameColumnHeader.isDisplayed() = " + screenNameColumnHeader.isDisplayed());
		assertTrue("The Screen Name Column Header should be displayed on the page at this point but it is not.",
			screenNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnSortIcon.isDisplayed() = " + screenNameColumnSortIcon.isDisplayed());
		assertTrue("The Screen Name Column Sort Icon should be displayed on the page at this point but it is not.",
			screenNameColumnSortIcon.isDisplayed());
		logger.log(Level.INFO, "screenNameColumnFilter.isDisplayed() = " + screenNameColumnFilter.isDisplayed());
		assertTrue("The Screen Name Column Text Filter should be displayed on the page at this point but it is not.",
			screenNameColumnFilter.isDisplayed());

		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();

		waitForElement(cancelButtonXpath);

		logger.log(Level.INFO, "portrait.isDisplayed() = " + portrait.isDisplayed());
		assertTrue("The User Portrait should be displayed on the page at this point but it is not.",
			portrait.isDisplayed());

		cancelButton.click();

	}

	@Test
	@RunAsClient
	@InSequence(6000)
	public void testFileUpload() throws Exception {

		waitForElement(johnAdamsUserScreenNameCellXpath);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();

		waitForElement(fileEntryXpath);

		fileEntry.sendKeys(getPathToJerseyFile());
		fileUploadButton.click();

		waitForElement(changedPortraitXpath);

		logger.log(Level.INFO, "changedPortrait.isDisplayed() = " + changedPortrait.isDisplayed());
		assertTrue("The Changed User Portrait should be displayed on the page at this point but it is not.",
			changedPortrait.isDisplayed());

		cancelButton.click();

		waitForElement(johnAdamsUserScreenNameCellXpath);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();

		waitForElement(fileEntryXpath);

		logger.log(Level.INFO, "portrait.isDisplayed() = " + portrait.isDisplayed());
		assertTrue("The User Portrait should be displayed on the page at this point but it is not.",
			portrait.isDisplayed());

		fileEntry.sendKeys(getPathToJerseyFile());
		fileUploadButton.click();

		waitForElement(changedPortraitXpath);

		submitButton.click();

		waitForElement(johnAdamsUserScreenNameCellXpath);

		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();

		waitForElement(changedPortraitXpath);

		logger.log(Level.INFO, "changedPortrait.isDisplayed() = " + changedPortrait.isDisplayed());
		assertTrue("The Changed User Portrait should be displayed on the page at this point but it is not.",
			changedPortrait.isDisplayed());

		cancelButton.click();
	}

	@Test
	@RunAsClient
	@InSequence(7000)
	public void changeUserAttributes() throws Exception {

		waitForElement(johnAdamsUserScreenNameCellXpath);

		waitForElement(johnAdamsUserScreenNameCellXpath);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();

		waitForElement(firstNameFieldXpath);

		firstNameField.clear();
		firstNameField.sendKeys("Aa");
		middleNameField.clear();
		middleNameField.sendKeys("Aa");
		lastNameField.clear();
		lastNameField.sendKeys("Aa");
		emailAddressField.clear();
		emailAddressField.sendKeys("A@A.com");
		jobTitleField.clear();
		jobTitleField.sendKeys("Aa");
		dropdownInactiveField.click();
		submitButton.click();

		logger.log(Level.INFO, "firstNameFieldError.isThere() = " + isThere(firstNameFieldErrorXpath));
		assertFalse("The First Name Validation Error should be displayed on the page at this point but it is not.",
			isThere(firstNameFieldErrorXpath));

		logger.log(Level.INFO, "lastNameFieldError.isThere() = " + isThere(lastNameFieldErrorXpath));
		assertFalse("The Last Name Validation Error should be displayed on the page at this point but it is not.",
			isThere(lastNameFieldErrorXpath));

		logger.log(Level.INFO, "emailAddressFieldError.isThere() = " + isThere(emailAddressFieldErrorXpath));
		assertFalse("The Email Address Validation Error should be displayed on the page at this point but it is not.",
			isThere(emailAddressFieldErrorXpath));

		waitForElement(johnAdamsUserScreenNameCellXpath);

		logger.log(Level.INFO, "changedUserLastNameCell.isDisplayed() = " + changedUserLastNameCell.isDisplayed());
		assertTrue(
			"The Last Name Cell of the changed user should be displayed on the page as A at this point but it is not.",
			changedUserLastNameCell.isDisplayed());
		logger.log(Level.INFO, "changedUserLastNameCell.isDisplayed() = " + changedUserFirstNameCell.isDisplayed());
		assertTrue(
			"The First Name Cell of the changed user should be displayed on the page as A at this point but it is not.",
			changedUserFirstNameCell.isDisplayed());
		logger.log(Level.INFO, "changedUserMiddleNameCell.isDisplayed() = " + changedUserMiddleNameCell.isDisplayed());
		assertTrue(
			"The Middle Name Cell of the changed user should be displayed on the page as A at this point but it is not.",
			changedUserMiddleNameCell.isDisplayed());
		logger.log(Level.INFO,
			"changedUserEmailAddressCell.isDisplayed() = " + changedUserEmailAddressCell.isDisplayed());
		assertTrue(
			"The Email Address Cell of the changed user should be displayed on the page as A@A.com at this point but it is not.",
			changedUserEmailAddressCell.isDisplayed());
		logger.log(Level.INFO, "changedUserJobTitleCell.isDisplayed() = " + changedUserJobTitleCell.isDisplayed());
		assertTrue(
			"The Job Title Cell of the changed user should be displayed on the page as A at this point but it is not.",
			changedUserJobTitleCell.isDisplayed());

		waitForElement(johnAdamsUserScreenNameCellXpath);

		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();
		Thread.sleep(250);
		(new Actions(browser)).doubleClick(johnAdamsUserScreenNameCell).perform();

		waitForElement(firstNameFieldXpath);

		logger.log(Level.INFO, "dropdownInactiveField.isDisplayed() = " + dropdownInactiveField.isDisplayed());
		assertTrue("The dropdown Inactive Field should be selected now, but it is not.",
			dropdownInactiveField.isDisplayed());

		firstNameField.clear();
		firstNameField.sendKeys("John");
		middleNameField.clear();
		lastNameField.clear();
		lastNameField.sendKeys("Adams");
		emailAddressField.clear();
		emailAddressField.sendKeys("john.adams@liferay.com");
		jobTitleField.clear();
		dropdownActiveField.click();
		submitButton.click();

		logger.log(Level.INFO, "firstNameFieldError.isThere() = " + isThere(firstNameFieldErrorXpath));
		assertFalse("The First Name Validation Error should be displayed on the page at this point but it is not.",
			isThere(firstNameFieldErrorXpath));
		logger.log(Level.INFO, "lastNameFieldError.isThere() = " + isThere(lastNameFieldErrorXpath));
		assertFalse("The Last Name Validation Error should be displayed on the page at this point but it is not.",
			isThere(lastNameFieldErrorXpath));
		logger.log(Level.INFO, "emailAddressFieldError.isThere() = " + isThere(emailAddressFieldErrorXpath));
		assertFalse("The Email Address Validation Error should be displayed on the page at this point but it is not.",
			isThere(emailAddressFieldErrorXpath));

		waitForElement(johnAdamsUserScreenNameCellXpath);

		logger.log(Level.INFO,
			"johnAdamsUserScreenNameCell.isDisplayed() = " + johnAdamsUserScreenNameCell.isDisplayed());
		assertTrue(
			"The Screen Name Cell of the John Adams user should be displayed on the page as john.adams at this point but it is not.",
			johnAdamsUserScreenNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserLastNameCell.isDisplayed() = " + johnAdamsUserLastNameCell.isDisplayed());
		assertTrue(
			"The Last Name Cell of the John Adams user should be displayed on the page as John at this point but it is not.",
			johnAdamsUserLastNameCell.isDisplayed());
		logger.log(Level.INFO, "johnAdamsUserLastNameCell.isDisplayed() = " + johnAdamsUserFirstNameCell.isDisplayed());
		assertTrue(
			"The First Name Cell of the John Adams user should be displayed on the page as Adams at this point but it is not.",
			johnAdamsUserFirstNameCell.isDisplayed());
		logger.log(Level.INFO,
			"johnAdamsUserEmailAddressCell.isDisplayed() = " + johnAdamsUserEmailAddressCell.isDisplayed());
		assertTrue(
			"The Email Address Cell of the John Adams user should be displayed on the page as john.adams@liferay.com at this point but it is not.",
			johnAdamsUserEmailAddressCell.isDisplayed());
	}

}
