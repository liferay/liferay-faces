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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.openqa.selenium.WebElement;

import com.liferay.faces.test.util.TesterBase;


/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class Icefaces3DirectoryPortletTest extends TesterBase {

	// elements for Icefaces3Directory
	private static final String portletTitleTextXpath = "//span[@class='portlet-title-text']";

	private static final String menuButtonXpath = "//a[contains(@id,'jsf2portlet') and contains(@id,'menuButton')]";
	private static final String menuPreferencesXpath =
		"//a[contains(@id,'jsf2portlet') and contains(@id,'menu_preferences')]";

	private static final String logoXpath = "//img[contains(@src,'liferay-logo.png')]";

	// Elements for the directory search
	private static final String lastNameSearchInputXpath =
		"//label[@class='aui-field-label' and contains(text(), 'Last Name')]/following-sibling::span[@class='aui-field-element']/input[contains(@class, 'iceInpTxt')]";
	private static final String firstNameSearchInputXpath =
		"//label[@class='aui-field-label' and contains(text(), 'First Name')]/following-sibling::span[@class='aui-field-element']/input[contains(@class, 'iceInpTxt')]";
	private static final String emailAddressSearchInputXpath =
		"//label[@class='aui-field-label' and contains(text(), 'Email Address')]/following-sibling::span[@class='aui-field-element']/input[contains(@class, 'iceInpTxt')]";
//	private static final String dropdownSearchStatusXpath =
//		"//select[contains(@id,':s1')]/option[@selected='true' and contains(text(), 'Any Status')]/..";
	private static final String dropdownSearchOperatoreXpath =
		"//select[contains(@id,':s1')]/option[@selected='true' and contains(text(), 'Any')]/..";
	private static final String searchButtonXpath =
		"//input[contains(@id, 'cmdButton') and @type='submit' and contains(@value, 'Search')]";

	// Elements for users' list
	private static final String pictureColumnHeaderXpath =
		"//th[contains(@class, 'iceDatTblColHdr') and @scope='col']/span[contains(@id,':users:') and text()='Picture']";
	private static final String lastNameColumnHeaderXpath = "//td[text()='Last Name']";
	private static final String firstNameColumnHeaderXpath =
		"//th[contains(@class, 'iceDatTblColHdr') and @scope='col']/a[1]/span[contains(@id,':users:') and text()='First Name']";
	private static final String emailAddressColumnHeaderXpath =
		"//th[contains(@class, 'iceDatTblColHdr') and @scope='col']/a[1]/span[contains(@id,':users:') and text()='Email Address']";
	private static final String jobTitleColumnHeaderXpath =
		"//th[contains(@class, 'iceDatTblColHdr') and @scope='col']/a[1]/span[contains(@id,':users:') and text()='Job Title']";

	// John Adams user's cells in the list view
	private static final String johnAdamsUserLastNameCellXpath =
		"//tr[contains(@class, 'iceDatTblRow') and contains(@id, ':users:')]/td[contains(@class, 'iceDatTblCol2')]/span[contains(text(), 'Adams')]";
	private static final String johnAdamsUserFirstNameCellXpath =
		"//tr[contains(@class, 'iceDatTblRow') and contains(@id, ':users:')]/td[contains(@class, 'iceDatTblCol1')]/span[contains(text(), 'John')]";
	private static final String johnAdamsUserEmailAddressCellXpath = "//a[@href='mailto:john.adams@liferay.com']";

	static final String url = "http://localhost:8080/group/portal-demos/ice3-dir";

	@FindBy(xpath = portletTitleTextXpath)
	private WebElement portletTitleText;
	@FindBy(xpath = menuButtonXpath)
	private WebElement menuButton;
	@FindBy(xpath = menuPreferencesXpath)
	private WebElement menuPreferences;
	@FindBy(xpath = logoXpath)
	private WebElement logo;
	@FindBy(xpath = lastNameSearchInputXpath)
	private WebElement lastNameSearchInput;
	@FindBy(xpath = firstNameSearchInputXpath)
	private WebElement firstNameSearchInput;
	@FindBy(xpath = emailAddressSearchInputXpath)
	private WebElement emailAddressSearchInput;
//	@FindBy(xpath = dropdownSearchStatusXpath)
//	private WebElement dropdownSearchStatusField;
	@FindBy(xpath = dropdownSearchOperatoreXpath)
	private WebElement dropdownSearchOperatorField;
	@FindBy(xpath = searchButtonXpath)
	private WebElement searchButton;
	@FindBy(xpath = pictureColumnHeaderXpath)
	private WebElement pictureColumnHeader;
	@FindBy(xpath = lastNameColumnHeaderXpath)
	private WebElement lastNameColumnHeader;
	@FindBy(xpath = firstNameColumnHeaderXpath)
	private WebElement firstNameColumnHeader;
	@FindBy(xpath = emailAddressColumnHeaderXpath)
	private WebElement emailAddressColumnHeader;
	@FindBy(xpath = jobTitleColumnHeaderXpath)
	private WebElement jobTitleColumnHeader;
	@FindBy(xpath = johnAdamsUserLastNameCellXpath)
	private WebElement johnAdamsUserLastNameCell;
	@FindBy(xpath = johnAdamsUserFirstNameCellXpath)
	private WebElement johnAdamsUserFirstNameCell;
	@FindBy(xpath = johnAdamsUserEmailAddressCellXpath)
	private WebElement johnAdamsUserEmailAddressCell;

	@Test
	@RunAsClient
	@InSequence(1000)
	public void usersListView() throws Exception {

		signIn();

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);

		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletTitleText.getText() = " + portletTitleText.getText());

		assertTrue("portletTitleText.isDisplayed()", portletTitleText.isDisplayed());

		logger.log(Level.INFO, "lastNameSearchInput.isDisplayed() = " + lastNameSearchInput.isDisplayed());
		assertTrue("The Last Name Search Box should be displayed on the page at this point but it is not.",
			lastNameSearchInput.isDisplayed());
		logger.log(Level.INFO, "firstNameSearchInput.isDisplayed() = " + firstNameSearchInput.isDisplayed());
		assertTrue("The First Name Search Box should be displayed on the page at this point but it is not.",
			firstNameSearchInput.isDisplayed());
		logger.log(Level.INFO, "emailAddressSearchInput.isDisplayed() = " + emailAddressSearchInput.isDisplayed());
		assertTrue("The Email Address Search Box should be displayed on the page at this point but it is not.",
			emailAddressSearchInput.isDisplayed());
//		logger.log(Level.INFO, "dropdownSearchStatusField.isDisplayed() = " + dropdownSearchStatusField.isDisplayed());
//		assertTrue("The Status Dropdown should be displayed on the page at this point but it is not.",
//			dropdownSearchStatusField.isDisplayed());
		logger.log(Level.INFO,
			"dropdownSearchOperatorField.isDisplayed() = " + dropdownSearchOperatorField.isDisplayed());
		assertTrue("The Search Operator Dropdown should be displayed on the page at this point but it is not.",
			dropdownSearchOperatorField.isDisplayed());
		logger.log(Level.INFO, "searchButton.isDisplayed() = " + searchButton.isDisplayed());
		assertTrue("The Search Button should be displayed on the page at this point but it is not.",
			searchButton.isDisplayed());

		logger.log(Level.INFO, "pictureColumnHeader.isDisplayed() = " + pictureColumnHeader.isDisplayed());
		assertTrue("The Screen Name Column Header should be displayed on the page at this point but it is not.",
			pictureColumnHeader.isDisplayed());

		logger.log(Level.INFO, "lastNameColumnHeader.isDisplayed() = " + lastNameColumnHeader.isDisplayed());
		assertTrue("The Last Name Column Header should be displayed on the page at this point but it is not.",
			lastNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "firstNameColumnHeader.isDisplayed() = " + firstNameColumnHeader.isDisplayed());
		assertTrue("The First Name Column Header should be displayed on the page at this point but it is not.",
			firstNameColumnHeader.isDisplayed());
		logger.log(Level.INFO, "emailAddressColumnHeader.isDisplayed() = " + emailAddressColumnHeader.isDisplayed());
		assertTrue("The Email Address Column Header should be displayed on the page at this point but it is not.",
			emailAddressColumnHeader.isDisplayed());
		logger.log(Level.INFO, "jobTitleColumnHeader.isDisplayed() = " + jobTitleColumnHeader.isDisplayed());
		assertTrue("The Job Title Column Header should be displayed on the page at this point but it is not.",
			jobTitleColumnHeader.isDisplayed());
	}

	@Test
	@RunAsClient
	@InSequence(2000)
	public void search() throws Exception {

		lastNameSearchInput.sendKeys("adams");

		searchButton.click();

		Thread.sleep(1000);

		logger.log(Level.INFO, "testUserLastNameCell.isDisplayed() = " + johnAdamsUserLastNameCell.isDisplayed());
		assertTrue(
			"The Last Name Cell of the Test user should be displayed on the page as Test at this point but it is not.",
			johnAdamsUserLastNameCell.isDisplayed());
		logger.log(Level.INFO, "testUserFirstNameCell.isDisplayed() = " + johnAdamsUserFirstNameCell.isDisplayed());
		assertTrue(
			"The First Name Cell of the Test user should be displayed on the page as Test at this point but it is not.",
			johnAdamsUserFirstNameCell.isDisplayed());
		logger.log(Level.INFO, "testUserEmailAddressCell.isDisplayed() = " + johnAdamsUserEmailAddressCell.isDisplayed());
		assertTrue(
			"The Email Address Cell of the Test user should be displayed on the page as test@liferay.com at this point but it is not.",
			johnAdamsUserEmailAddressCell.isDisplayed());
	}

}
