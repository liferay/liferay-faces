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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.logging.Level;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
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
public class Primefaces3UsersPortletTest extends TesterBase {

	// Elements for reseting the John Adams user before running the test
	private static final String dropdownTestSetupXpath = "//span[contains(text(),'Go To')]/..";
	private static final String controlPanelTestSetupXpath = "//span[text()=' Control Panel ']";
	private static final String usersLinkTestSetupXpath = "//a[text()=' Users and Organizations ']";
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

	// elements for Primefaces3Users
	private static final String primefaces3UsersLinkTestSetupXpath = "//a[contains(@href,'primefaces3usersportlet')]";
	private static final String portletTitleTextXpath = "//span[@class='portlet-title-text']";

	private static final String menuButtonXpath = "//a[contains(@id,'jsf2portlet') and contains(@id,'menuButton')]";
	private static final String menuPreferencesXpath =
		"//a[contains(@id,'jsf2portlet') and contains(@id,'menu_preferences')]";

	private static final String logoXpath = "//img[contains(@src,'liferay-logo.png')]";

	// Elements for users' list
	private static final String screenNameColumnHeaderXpath = "//th[contains(@id,'users:screenName')]/child::span[1]";
	private static final String screenNameColumnSortIconXpath =
		"//th[contains(@id,'users:screenName')]/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String screenNameColumnFilterXpath = "//input[contains(@id,'users:screenName:filter')]";

	private static final String lastNameColumnHeaderXpath = "//th[contains(@id,'users:lastName')]/child::span[1]";
	private static final String lastNameColumnSortIconXpath =
		"//th[contains(@id,'users:lastName')]/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String lastNameColumnFilterXpath = "//input[contains(@id,'users:lastName:filter')]";

	private static final String firstNameColumnHeaderXpath = "//th[contains(@id,'users:firstName')]/child::span[1]";
	private static final String firstNameColumnSortIconXpath =
		"//th[contains(@id,'users:firstName')]/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String firstNameColumnFilterXpath = "//input[contains(@id,'users:firstName:filter')]";

	private static final String middleNameColumnHeaderXpath = "//th[contains(@id,'users:middleName')]/child::span[1]";
	private static final String middleNameColumnSortIconXpath =
		"//th[contains(@id,'users:middleName')]/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String middleNameColumnFilterXpath = "//input[contains(@id,'users:middleName:filter')]";

	private static final String emailAddressColumnHeaderXpath =
		"//th[contains(@id,'users:emailAddress')]/child::span[1]";
	private static final String emailAddressColumnSortIconXpath =
		"//th[contains(@id,'users:emailAddress')]/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String emailAddressColumnFilterXpath = "//input[contains(@id,'users:emailAddress:filter')]";

	private static final String jobTitleColumnHeaderXpath = "//th[contains(@id,'users:jobTitle')]/child::span[1]";
	private static final String jobTitleColumnSortIconXpath =
		"//th[contains(@id,'users:jobTitle')]/child::span[contains(@class,'ui-sortable-column-icon')]";
	private static final String jobTitleColumnFilterXpath = "//input[contains(@id,'users:jobTitle:filter')]";

	// The Test user's list view
	private static final String johnAdamsUserScreenNameCellXpath =
		"//span[contains(@id,':screenNameCell') and contains(text(), 'john.adams')]";
	private static final String johnAdamsUserLastNameCellXpath =
		"//span[contains(@id,':lastNameCell') and contains(text(), 'Adams')]";
	private static final String johnAdamsUserFirstNameCellXpath =
		"//span[contains(@id,':firstNameCell') and contains(text(), 'John')]";
	private static final String johnAdamsUserEmailAddressCellXpath =
		"//a[contains(@href,'mailto:') and contains(text(), 'john.adams@liferay.com')]";

	// Samuel Adams screen name in users' list
	private static final String samuelAdamsUserScreenNameCellXpath =
		"//span[contains(@id,':screenNameCell') and text()='samuel.adams']";

	// Elements for column 1 of the test user's detailed user view
	private static final String firstNameFieldXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':firstName')]";
	private static final String firstNameFieldErrorXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':firstName')]/../span[@class='portlet-msg-error' and text()='Value is required']";
	private static final String middleNameFieldXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':middleName')]";
	private static final String lastNameFieldXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':lastName')]";
	private static final String lastNameFieldErrorXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':lastName')]/../span[@class='portlet-msg-error' and text()='Value is required']";
	private static final String emailAddressFieldXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':emailAddress')]";
	private static final String emailAddressFieldErrorXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':emailAddress')]/../span[@class='portlet-msg-error' and text()='Value is required']";
	private static final String jobTitleFieldXpath =
		"//span[@class='aui-field-element']/input[contains(@id,':jobTitle')]";
	private static final String submitButtonXpath = "//button[contains(@id, ':pushButtonSubmit') and @type='submit']";
	private static final String cancelButtonXpath = "//button[contains(@id, ':pushButtonCancel') and @type='submit']";

	// Elements for column 2 of John Adam's detailed user view
	private static final String dropdownActiveFieldXpath = "//select[contains(@id,':s1')]/option[text()='Active']";
	private static final String dropdownActiveSelectedFieldXpath =
		"//select[contains(@id,':s1')]/option[@selected='selected' and text()='Active']";
	private static final String dropdownInactiveFieldXpath = "//select[contains(@id,':s1')]/option[text()='Inactive']";
	private static final String dropdownInactiveSelectedFieldXpath =
		"//select[contains(@id,':s1')]/option[@selected='selected' and text()='Inactive']";

	// Elements for column 3 of the test user's detailed user view
	private static final String portraitXpath =
		"//img[contains(@id, ':portrait') and contains(@src, 'user_male_portrait')]";
	private static final String fileUploadButtonXpath =
		"//label[@role='button']/span[text()='Choose' and contains(@class, 'ui-button-text')]/following-sibling::input[@type='file' and contains(@id, ':fileEntryComp_input')]/..";
	private static final String changedPortraitXpath =
		"//input[@type='file' and contains(@id, ':fileEntryComp_input')]/../../../../img[contains(@id, ':portrait') and not(contains(@src, 'user_male_portrait'))]";
	private static final String fileEntryXpath = "//input[@type='file' and contains(@id, ':fileEntryComp_input')]";

	// Elements for the changed version of John Adams
	private static final String changedUserLastNameCellXpath = "//span[contains(@id,':lastNameCell') and text()='Aa']";
	private static final String changedUserFirstNameCellXpath =
		"//span[contains(@id,':firstNameCell') and text()='Aa']";
	private static final String changedUserMiddleNameCellXpath =
		"//span[contains(@id,':middleNameCell') and text()='Aa']";
	private static final String changedUserEmailAddressCellXpath = "//a[@href='mailto:A@A.com']";
	private static final String changedUserJobTitleCellXpath = "//span[contains(@id,':jobTitleCell') and text()='Aa']";

	static final String url =
		"http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_primefaces3usersportlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view";

	@FindBy(xpath = dropdownTestSetupXpath)
	private WebElement dropdownTestSetup;
	@FindBy(xpath = controlPanelTestSetupXpath)
	private WebElement controlPanelTestSetup;
	@FindBy(xpath = usersLinkTestSetupXpath)
	private WebElement usersLinkTestSetup;
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
	@FindBy(xpath = primefaces3UsersLinkTestSetupXpath)
	private WebElement primefaces3UsersLinkTestSetup;
	@FindBy(xpath = portletTitleTextXpath)
	private WebElement portletTitleText;
	@FindBy(xpath = menuButtonXpath)
	private WebElement menuButton;
	@FindBy(xpath = menuPreferencesXpath)
	private WebElement menuPreferences;
	@FindBy(xpath = logoXpath)
	private WebElement logo;
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
	@FindBy(xpath = fileUploadButtonXpath)
	private WebElement fileUploadButton;
	@FindBy(xpath = changedPortraitXpath)
	private WebElement changedPortrait;
	@FindBy(xpath = fileEntryXpath)
	private WebElement fileEntry;
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
	public void testSetupActivateUser() throws Exception { // For 3.1.x
		signIn();
		(new Actions(browser)).click(dropdownTestSetup);
		controlPanelTestSetup.click();
		waitForElement(usersLinkTestSetupXpath);
		usersLinkTestSetup.click();
		waitForElement(searchAllUsersLinkTestSetupXpath);
		searchAllUsersLinkTestSetup.click();
		waitForElement(selectStatusTestSetupXpath);
		
		if (isThere(advancedSearchLinkTestSetupXpath) && advancedSearchLinkTestSetup.isDisplayed()) {
			advancedSearchLinkTestSetup.click();
		}
		
		selectStatusTestSetup.click();
		(new Actions(browser)).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.TAB).perform();
		selectStatusTestSetup.submit();
		
		Thread.sleep(250);		
		
		if (isThere(johnAdamsTestSetupXpath)) {
			johnAdamsMenuTestSetup.click();
			activateJohnAdamsTestSetup.click();
		}
		
		waitForElement(usersLinkTestSetupXpath);
	}
	
	@Test
	@RunAsClient
	@InSequence(500)
	public void testSetup() throws Exception {

		browser.manage().deleteAllCookies();
		signIn();
//		waitForElement(dropdownTestSetupXpath);
		(new Actions(browser)).click(dropdownTestSetup);
		controlPanelTestSetup.click();
		waitForElement(usersLinkTestSetupXpath);
		usersLinkTestSetup.click();		
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

		primefaces3UsersLinkTestSetup.click();
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
		
		waitForElement(johnAdamsUserScreenNameCellXpath);
		johnAdamsUserScreenNameCell.click();
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
		logger.log(Level.INFO, "isThere(fileEntryXpath) = " + isThere(fileEntryXpath));
		assertTrue("The File Entry should be displayed on the page at this point but it is not.",
			isThere(fileEntryXpath));
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
	public void testFileUpload() throws Exception {

		waitForElement(johnAdamsUserScreenNameCellXpath);
		johnAdamsUserScreenNameCell.click();

		waitForElement(cancelButtonXpath);

		fileEntry.sendKeys(getPathToJerseyFile());
		fileUploadButton.click();

		waitForElement(changedPortraitXpath);

		logger.log(Level.INFO, "changedPortrait.isDisplayed() = " + changedPortrait.isDisplayed());
		assertTrue("The Changed User Portrait should be displayed on the page at this point but it is not.",
			changedPortrait.isDisplayed());

		cancelButton.click();

		waitForElement(johnAdamsUserScreenNameCellXpath);

		johnAdamsUserScreenNameCell.click();

		waitForElement(submitButtonXpath);

		logger.log(Level.INFO, "portrait.isDisplayed() = " + portrait.isDisplayed());
		assertTrue("The User Portrait should be displayed on the page at this point but it is not.",
			portrait.isDisplayed());

		fileEntry.sendKeys(getPathToJerseyFile());
		fileUploadButton.click();

		waitForElement(changedPortraitXpath);

		submitButton.click();

		waitForElement(johnAdamsUserScreenNameCellXpath);

		johnAdamsUserScreenNameCell.click();

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
		johnAdamsUserScreenNameCell.click();

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

		johnAdamsUserScreenNameCell.click();

		waitForElement(firstNameFieldXpath);

		firstNameField.clear();
		firstNameField.sendKeys("John");
		middleNameField.clear();
		lastNameField.clear();
		lastNameField.sendKeys("Adams");
		emailAddressField.clear();
		emailAddressField.sendKeys("john.adams@liferay.com");
		jobTitleField.clear();
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

	@Test
	@RunAsClient
	@InSequence(8000)
	public void deactivateUser() throws Exception{
		
		waitForElement(johnAdamsUserScreenNameCellXpath);
		johnAdamsUserScreenNameCell.click();

		waitForElement(dropdownInactiveFieldXpath);
		
		dropdownInactiveField.click();
				
		submitButton.click();
	
		waitForElement(johnAdamsUserScreenNameCellXpath);
		johnAdamsUserScreenNameCell.click();

		waitForElement(dropdownActiveFieldXpath);
		
		logger.log(Level.INFO, "dropdownInactiveSelectedField.isDisplayed() = " + dropdownInactiveSelectedField.isDisplayed());
		assertTrue("The dropdown Inactive Field should be selected now, but it is not.",
			dropdownInactiveSelectedField.isDisplayed());
		
		dropdownActiveField.click();
		
		submitButton.click();
		
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
		
		johnAdamsUserScreenNameCell.click();
		waitForElement(dropdownInactiveFieldXpath);
		
		logger.log(Level.INFO, "dropdownActiveSelectedField.isDisplayed() = " + dropdownActiveSelectedField.isDisplayed());
		assertTrue("The dropdown Active Field should be selected now, but it is not.",
			dropdownActiveSelectedField.isDisplayed());
	}
}

