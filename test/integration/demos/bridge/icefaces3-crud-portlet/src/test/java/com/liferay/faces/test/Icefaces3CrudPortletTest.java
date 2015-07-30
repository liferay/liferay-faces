/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
//J-

import static org.junit.Assert.assertTrue;

import java.util.logging.Level;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.liferay.faces.test.util.TesterBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author	Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class Icefaces3CrudPortletTest extends TesterBase {

	// form tag found after submitting
	private static final String formTagXpath = "//form[@method='post']";

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
		"//input[contains(@id,':city')]/following-sibling::span[1]/child::span[text()='Value is required']";

	private static final String provinceIdFieldXpath = "//select[contains(@id,':provinceId')]";
	private static final String provinceIdFieldErrorXpath =
		"//select[contains(@id,':provinceId')]/following-sibling::span[1]/child::span[text()='Value is required']";

	// FL element of dropdown
	private static final String provinceFlXpath =
		"//select[contains(@id,':provinceId')]/child::option[contains(@value, '3')]";

	private static final String postalCodeFieldXpath = "//input[contains(@id,':postalCode')]";
	private static final String postalCodeFieldErrorXpath =
		"//input[contains(@id,':postalCode')]/following-sibling::span[1]/child::span[text()='Value is required']";

	// Save button
	private static final String saveButtonXpath = "//input[@type='submit' and @value='Save']";

	// Cancel button
	private static final String cancelButtonXpath = "//input[@type='submit' and @value='Cancel']";

	// Page 2 Button
	private static final String page2ButtonXpath = "//a[contains(@id,'idx2')]";

	// Created User elements
	private static final String createdUserFirstNameXpath = "//tr/td[3]/span[text()='A']";
	private static final String createdUserEmailXpath = "//tr/td[5]/a/span[text()='A@liferay.com']";

	static final String url = baseUrl + webContext + "/ice3-crud";

	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
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
	@FindBy(xpath = page2ButtonXpath)
	private WebElement page2Button;
	@FindBy(xpath = createdUserFirstNameXpath)
	private WebElement createdUserFirstName;
	@FindBy(xpath = createdUserEmailXpath)
	private WebElement createdUserEmail;

	@Drone
	WebDriver browser;

	@Test
	@RunAsClient
	@InSequence(1000)
	public void customerMasterViewMode() throws Exception {

		signIn(browser);
		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		getPortletDisplayName();
		logger.log(Level.INFO, "displayName.getText() = " + displayName.getText());

		getPortletDisplayName();
		assertTrue("displayName.isDisplayed()", displayName.isDisplayed());

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

		logger.log(Level.INFO, "Waiting for firstNameFieldXpath to show on the page ... ");

		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(firstNameFieldXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("firstNameField should be visible after clicking the add button," +
			" but " + firstNameFieldXpath + " is not visible.", false);
		}

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

		logger.log(Level.INFO, "Waiting for firstNameFieldErrorXpath to show on the page ... ");

		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(firstNameFieldErrorXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("firstNameFieldError should be visible after clicking the save button," +
			" but " + firstNameFieldErrorXpath + " is not visible.", false);
		}

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

		logger.log(Level.INFO, "Waiting for addButtonXpath to show on the page ... ");

		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(addButtonXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("addButton should be visible after clicking the cancel button," +
			" but " + addButtonXpath + " is not visible.", false);
		}

		addButton.click();

		logger.log(Level.INFO, "Waiting for firstNameFieldXpath to show on the page ... ");

		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(firstNameFieldXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("firstNameField should be visible after clicking the add button," +
			" but " + firstNameFieldXpath + " is not visible.", false);
		}

		firstNameField.sendKeys("A");
		lastNameField.sendKeys("A");
		emailAddressField.sendKeys("A@liferay.com");
		phoneNumberField.sendKeys("9876543210");
		dateOfBirthField.sendKeys("11/11/1111");
		cityField.sendKeys("A");
		provinceFl.click();
		postalCodeField.sendKeys("11111");
		saveButton.click();

		logger.log(Level.INFO, "Waiting for page2ButtonXpath to show on the page ... ");

		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(page2ButtonXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("page2Button should be visible after clicking the save button," +
			" but " + page2ButtonXpath + " is not visible.", false);
		}

		page2Button.click();

		logger.log(Level.INFO, "Waiting for createdUserFirstNameXpath to show on the page ... ");

		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(createdUserFirstNameXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("createdUserFirstName should be visible after selecting edit mode," +
			" but " + createdUserFirstNameXpath + " is not visible.", false);
		}

		logger.log(Level.INFO, "createdUserFirstName.isDisplayed() = " + createdUserFirstName.isDisplayed());
		assertTrue("The Created User First Name should be displayed on the page at this point but it is not.",
			createdUserFirstName.isDisplayed());

		logger.log(Level.INFO, "createdUserEmail.isDisplayed() = " + createdUserEmail.isDisplayed());
		assertTrue("The Created User Email should be displayed on the page at this point but it is not.",
			createdUserEmail.isDisplayed());
	}

}
//J+
