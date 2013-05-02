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
import org.openqa.selenium.WebElement;

import com.liferay.faces.test.util.TesterBase;

/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class Icefaces3CrudPortletTest extends TesterBase {

	// form tag found after submitting
	private static final String formTagXpath = "//form[@method='post']";

	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";
	private static final String menuButtonXpath = "//a[contains(@id,'menuButton')]";

	// Delete button
	private static final String deleteButtonXpath = "//input[@type='submit' and @value='Delete Selected']";

	// Add button
	private static final String addButtonXpath = "//input[@type='submit' and @value='Add New']";

	// Elements for Customers detail view
	private static final String firstNameFieldXpath = "//input[contains(@id,':firstName')]";
	private static final String firstNameFieldErrorXpath =
		"//input[contains(@id,':firstName')]/following-sibling::span[1]/child::node()";

	private static final String lastNameFieldXpath = "//input[contains(@id,':lastName')]";
	private static final String lastNameFieldErrorXpath =
		"//input[contains(@id,':lastName')]/following-sibling::span[1]/child::node()";

	private static final String emailAddressFieldXpath = "//input[contains(@id,':emailAddress')]";
	private static final String emailAddressFieldErrorXpath =
		"//input[contains(@id,':emailAddress')]/following-sibling::span[1]/child::node()";

	private static final String phoneNumberFieldXpath = "//input[contains(@id,':phoneNumber')]";
	private static final String phoneNumberFieldErrorXpath =
		"//input[contains(@id,':phoneNumber')]/following-sibling::span[1]/child::span[text()='Value is required']";

	private static final String dateOfBirthFieldXpath = "//input[contains(@id,':dateOfBirth')]";
	private static final String dateOfBirthFieldErrorXpath =
		"//input[contains(@id,':dateOfBirth')]/following-sibling::span[1]/child::span[text()='Value is required']";

	private static final String cityFieldXpath = "//input[contains(@id,':city')]";
	private static final String cityFieldErrorXpath =
		"//input[contains(@id,':city')]/following-sibling::span[text()='Value is required']";

	private static final String provinceIdFieldXpath = "//select[contains(@id,':provinceId')]";
	private static final String provinceIdFieldErrorXpath =
		"//select[contains(@id,':provinceId')]/following-sibling::span[text()='Value is required']";

	// FL element of dropdown
	private static final String provinceFlXpath =
		"//select[contains(@id,':provinceId')]/child::option[contains(@value, '3')]";

	private static final String postalCodeFieldXpath = "//input[contains(@id,':postalCode')]";
	private static final String postalCodeFieldErrorXpath =
		"//input[contains(@id,':postalCode')]/following-sibling::span[text()='Value is required']";

	// Save button
	private static final String saveButtonXpath = "//input[@type='submit' and @value='Save']";

	// Cancel button
	private static final String cancelButtonXpath = "//input[@type='submit' and @value='Cancel']";

	static final String url = "http://localhost:8080/group/bridge-demos/ice3-crud";

	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	@FindBy(xpath = menuButtonXpath)
	private WebElement menuButton;
	@FindBy(xpath = deleteButtonXpath)
	private WebElement deleteButton;
	@FindBy(xpath = addButtonXpath)
	private WebElement addButton;
	@FindBy(xpath = firstNameFieldXpath)
	private WebElement firstNameField;
	@FindBy(xpath = firstNameFieldErrorXpath)
	private WebElement firstNameFieldError;
	@FindBy(xpath = lastNameFieldXpath)
	private WebElement lastNameField;
	@FindBy(xpath = lastNameFieldErrorXpath)
	private WebElement lastNameFieldError;
	@FindBy(xpath = emailAddressFieldXpath)
	private WebElement emailAddressField;
	@FindBy(xpath = emailAddressFieldErrorXpath)
	private WebElement emailAddressFieldError;
	@FindBy(xpath = phoneNumberFieldXpath)
	private WebElement phoneNumberField;
	@FindBy(xpath = phoneNumberFieldErrorXpath)
	private WebElement phoneNumberFieldError;
	@FindBy(xpath = dateOfBirthFieldXpath)
	private WebElement dateOfBirthField;
	@FindBy(xpath = dateOfBirthFieldErrorXpath)
	private WebElement dateOfBirthFieldError;
	@FindBy(xpath = cityFieldXpath)
	private WebElement cityField;
	@FindBy(xpath = cityFieldErrorXpath)
	private WebElement cityFieldError;
	@FindBy(xpath = provinceIdFieldXpath)
	private WebElement provinceIdField;
	@FindBy(xpath = provinceIdFieldErrorXpath)
	private WebElement provinceIdFieldError;
	@FindBy(xpath = provinceFlXpath)
	private WebElement provinceFl;
	@FindBy(xpath = postalCodeFieldXpath)
	private WebElement postalCodeField;
	@FindBy(xpath = postalCodeFieldErrorXpath)
	private WebElement postalCodeFieldError;
	@FindBy(xpath = saveButtonXpath)
	private WebElement saveButton;
	@FindBy(xpath = cancelButtonXpath)
	private WebElement cancelButton;

	@Test
	@RunAsClient
	@InSequence(1000)
	public void customerMasterViewMode() throws Exception {

		signIn();
		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());

		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue("menuButton.isDisplayed()", menuButton.isDisplayed());

		logger.log(Level.INFO, "addButton.isDisplayed() = " + addButton.isDisplayed());
		assertTrue("The \"Add New\" button should be displayed on the page at this point but it is not.",
			addButton.isDisplayed());

		logger.log(Level.INFO, "deleteButton.isDisplayed() = " + deleteButton.isDisplayed());
		assertTrue("The \"Delete Selected\" button should be displayed on the page at this point but it is not.",
			deleteButton.isDisplayed());

	}

	@Test
	@RunAsClient
	@InSequence(2000)
	public void customerDetailViewMode() throws Exception {

		addButton.click();

		Thread.sleep(500);

		logger.log(Level.INFO, "firstNameField.isDisplayed() = " + firstNameField.isDisplayed());
		assertTrue("The First Name Field should be displayed on the page at this point but it is not.",
			firstNameField.isDisplayed());

		logger.log(Level.INFO, "lastNameField.isDisplayed() = " + lastNameField.isDisplayed());
		assertTrue("The Last Name Field should be displayed on the page at this point but it is not.",
			lastNameField.isDisplayed());

		logger.log(Level.INFO, "emailAddressField.isDisplayed() = " + emailAddressField.isDisplayed());
		assertTrue("The Email Address Field should be displayed on the page at this point but it is not.",
			emailAddressField.isDisplayed());

		logger.log(Level.INFO, "phoneNumberField.isDisplayed() = " + phoneNumberField.isDisplayed());
		assertTrue("The Phone Number Field should be displayed on the page at this point but it is not.",
			phoneNumberField.isDisplayed());

		logger.log(Level.INFO, "dateOfBirthField.isDisplayed() = " + dateOfBirthField.isDisplayed());
		assertTrue("The Date Of Birth Field should be displayed on the page at this point but it is not.",
			dateOfBirthField.isDisplayed());

		logger.log(Level.INFO, "cityField.isDisplayed() = " + cityField.isDisplayed());
		assertTrue("The City Field should be displayed on the page at this point but it is not.",
			cityField.isDisplayed());

		logger.log(Level.INFO, "provinceIdField.isDisplayed() = " + provinceIdField.isDisplayed());
		assertTrue("The Province ID Field should be displayed on the page at this point but it is not.",
			provinceIdField.isDisplayed());

		logger.log(Level.INFO, "postalCodeField.isDisplayed() = " + postalCodeField.isDisplayed());
		assertTrue("The Postal Code Field should be displayed on the page at this point but it is not.",
			postalCodeField.isDisplayed());

		logger.log(Level.INFO, "saveButton.isDisplayed() = " + saveButton.isDisplayed());
		assertTrue("The \"Save\" button should be displayed on the page at this point but it is not.",
			saveButton.isDisplayed());

		logger.log(Level.INFO, "cancelButton.isDisplayed() = " + cancelButton.isDisplayed());
		assertTrue("The \"Cancel\" button should be displayed on the page at this point but it is not.",
			cancelButton.isDisplayed());

	}

	@Test
	@RunAsClient
	@InSequence(3000)
	public void allFieldsRequired() throws Exception {

		saveButton.click();

		Thread.sleep(250);

		logger.log(Level.INFO, "firstNameFieldError.isDisplayed() = " + firstNameFieldError.isDisplayed());
		assertTrue("The First Name Validation Error should be displayed on the page at this point but it is not.",
			firstNameFieldError.isDisplayed());

		logger.log(Level.INFO, "lastNameFieldError.isDisplayed() = " + lastNameFieldError.isDisplayed());
		assertTrue("The Last Name Validation Error should be displayed on the page at this point but it is not.",
			lastNameFieldError.isDisplayed());

		logger.log(Level.INFO, "emailAddressFieldError..isDisplayed() = " + emailAddressFieldError.isDisplayed());
		assertTrue("The Email Address Validation Error should be displayed on the page at this point but it is not.",
			emailAddressFieldError.isDisplayed());

		logger.log(Level.INFO, "phoneNumberFieldError.isDisplayed() = " + phoneNumberFieldError.isDisplayed());
		assertTrue("The Phone Number Validation Error should be displayed on the page at this point but it is not.",
			phoneNumberFieldError.isDisplayed());

		logger.log(Level.INFO, "dateOfBirthFieldError.isDisplayed() = " + dateOfBirthFieldError.isDisplayed());
		assertTrue("The Date of Birth Validation Error should be displayed on the page at this point but it is not.",
			dateOfBirthFieldError.isDisplayed());

		logger.log(Level.INFO, "cityFieldError.isDisplayed() = " + cityFieldError.isDisplayed());
		assertTrue("The City Validation Error should be displayed on the page at this point but it is not.",
			cityFieldError.isDisplayed());

		logger.log(Level.INFO, "provinceIdFieldError.isDisplayed() = " + provinceIdFieldError.isDisplayed());
		assertTrue("The Province ID Validation Error should be displayed on the page at this point but it is not.",
			provinceIdFieldError.isDisplayed());

		logger.log(Level.INFO, "postalCodeFieldError..isDisplayed() = " + postalCodeFieldError.isDisplayed());
		assertTrue("The Postal Code Validation Error should be displayed on the page at this point but it is not.",
			postalCodeFieldError.isDisplayed());

		cancelButton.click();

	}

	@Test
	@RunAsClient
	@InSequence(4000)
	public void addCustomer() throws Exception {

		Thread.sleep(250);

		addButton.click();

		Thread.sleep(250);

		firstNameField.sendKeys("A");
		lastNameField.sendKeys("A");
		emailAddressField.sendKeys("A@liferay.com");
		phoneNumberField.sendKeys("9876543210");
		dateOfBirthField.sendKeys("11/11/1111");
		cityField.sendKeys("A");
		provinceFl.click();
		postalCodeField.sendKeys("11111");
		saveButton.click();

		Thread.sleep(250);

		logger.log(Level.INFO, "firstNameFieldError.isThere() = " + isThere(firstNameFieldErrorXpath));
		assertFalse("There should NOT be a First Name Validation Error on the but there is.",
			isThere(firstNameFieldErrorXpath));

		logger.log(Level.INFO, "lastNameFieldError.isThere() = " + isThere(lastNameFieldErrorXpath));
		assertFalse("There should NOT be a Last Name Validation Error on the but there is.",
			isThere(lastNameFieldErrorXpath));

		logger.log(Level.INFO, "emailAddressFieldError.isThere() = " + isThere(emailAddressFieldErrorXpath));
		assertFalse("There should NOT be a Email Validation Error on the but there is.",
			isThere(emailAddressFieldErrorXpath));

		logger.log(Level.INFO, "phoneNumberFieldError.isThere() = " + isThere(phoneNumberFieldErrorXpath));
		assertFalse("There should NOT be a Phone Number Validation Error on the but there is.",
			isThere(phoneNumberFieldErrorXpath));

		logger.log(Level.INFO, "dateOfBirthFieldError.isThere() = " + isThere(dateOfBirthFieldErrorXpath));
		assertFalse("There should NOT be a Date of Birth Validation Error on the but there is.",
			isThere(dateOfBirthFieldErrorXpath));

		logger.log(Level.INFO, "cityFieldError.isThere() = " + isThere(cityFieldErrorXpath));
		assertFalse("There should NOT be a City Validation Error on the but there is.", isThere(cityFieldErrorXpath));

		logger.log(Level.INFO, "provinceIdFieldError.isThere() = " + isThere(provinceIdFieldErrorXpath));
		assertFalse("There should NOT be a province Validation Error on the but there is.",
			isThere(provinceIdFieldErrorXpath));

		logger.log(Level.INFO, "postalCodeFieldError.isThere() = " + isThere(postalCodeFieldErrorXpath));
		assertFalse("There should NOT be a Postal Code Validation Error on the but there is.",
			isThere(postalCodeFieldErrorXpath));

	}

}
