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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.logging.Level;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.liferay.faces.test.util.TesterBase;


/**
 * @author	Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class Jsf2SpringPortletTest extends TesterBase {

	// form tag found after submitting
	private static final String formTagXpath = "//form[@method='post']";

	// preferences elements
	private static final String datePatternFieldXpath = "//input[contains(@id,':datePattern')]";
	private static final String resetButtonXpath = "//input[@type='submit' and @value='Reset']";

	// elements for Job Applicants
	private static final String logoXpath = "//img[contains(@src,'liferay-logo.png')]";

	private static final String firstNameFieldXpath = "//input[contains(@id,':firstName')]";
	private static final String firstNameFieldErrorXpath = "//input[contains(@id,':firstName')]/following-sibling::*[1]";

	private static final String lastNameFieldXpath = "//input[contains(@id,':lastName')]";
	private static final String lastNameFieldErrorXpath = "//input[contains(@id,':lastName')]/following-sibling::*[1]";

	private static final String emailAddressFieldXpath = "//input[contains(@id,':emailAddress')]";
	private static final String emailAddressFieldErrorXpath = "//input[contains(@id,':emailAddress')]/following-sibling::*[1]";

	private static final String phoneNumberFieldXpath = "//input[contains(@id,':phoneNumber')]";
	private static final String phoneNumberFieldErrorXpath = "//input[contains(@id,':phoneNumber')]/following-sibling::*[1]";

	private static final String dateOfBirthFieldXpath = "//input[contains(@id,':dateOfBirth')]";
	private static final String dateOfBirthFieldErrorXpath = "//input[contains(@id,':dateOfBirth')]/following-sibling::*[1]";

	private static final String cityFieldXpath = "//input[contains(@id,':city')]";
	private static final String cityFieldErrorXpath = "//input[contains(@id,':city')]/following-sibling::*[1]";

	private static final String provinceIdFieldXpath = "//select[contains(@id,':provinceId')]";
	private static final String provinceIdFieldErrorXpath = "//select[contains(@id,':provinceId')]/following-sibling::*[1]";

	private static final String provinceIdSelectorXpath = "";

	private static final String postalCodeFieldXpath = "//input[contains(@id,':postalCode')]";
	private static final String postalCodeFieldErrorXpath = "//input[contains(@id,':postalCode')]/following-sibling::*[1]/following-sibling::*[1]";

	private static final String postalCodeToolTipXpath = "//img[contains(@title,'Type any of these ZIP codes')]";

	private static final String showCommentsLinkXpath = "//a[contains(text(),'Show Comments')]";
	private static final String hideCommentsLinkXpath = "//a[contains(text(),'Hide Comments')]";
	private static final String commentsXpath = "//textarea[contains(@id,':comments')]";

	private static final String fileUploadChooserXpath = "//input[@type='file' and @multiple='multiple']";
	private static final String submitFileXpath = "//form[@method='post' and @enctype='multipart/form-data']/input[@type='submit' and @value='Submit']";
	private static final String uploadedFileXpath = "//tr[@class='portlet-section-body results-row']/td[2]";

	private static final String submitButtonXpath = "//input[@type='submit' and @value='Submit']";
	private static final String preferencesSubmitButtonXpath = "//input[@type='submit' and @value='Submit']";
	private static final String editPreferencesButtonXpath = "//input[@type='submit' and @value='Edit Preferences']";
	private static final String returnLinkXpath = "//a[contains(text(),'Return to Full Page')]";

	private static final String mojarraVersionXpath = "//*[contains(text(),'Mojarra')]";
	private static final String componentLibraryVersionXpath = "//*[contains(text(),'PrimeFaces ')]";
	private static final String alloyVersionXpath = "//*[contains(text(),'Liferay Faces Alloy')]";
	private static final String bridgeVersionXpath = "//*[contains(text(),'Liferay Faces Bridge')]";

	private static final String versionUlXpath = "//*[contains(text(),'Liferay Faces Bridge')]/../../../ul";
	private static final String windowInnerHeightXpath = "//em[@id='window.innerHeight']";
	private static final String windowInnerWidthXpath = "//em[@id='window.innerWidth']";

	static final String url = baseUrl + webContext + "/jsf2-spring";

	@FindBy(xpath = formTagXpath)
	private WebElement formTag;

	@FindBy(xpath = datePatternFieldXpath)
	private WebElement datePatternField;
	@FindBy(xpath = resetButtonXpath)
	private WebElement resetButton;
	@FindBy(xpath = logoXpath)
	private WebElement logo;
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
	@FindBy(xpath = provinceIdSelectorXpath)
	private WebElement provinceIdSelector;
	@FindBy(xpath = provinceIdFieldErrorXpath)
	private WebElement provinceIdFieldError;
	@FindBy(xpath = postalCodeFieldXpath)
	private WebElement postalCodeField;
	@FindBy(xpath = postalCodeFieldErrorXpath)
	private WebElement postalCodeFieldError;
	@FindBy(xpath = postalCodeToolTipXpath)
	private WebElement postalCodeToolTip;
	@FindBy(xpath = showCommentsLinkXpath)
	private WebElement showCommentsLink;
	@FindBy(xpath = hideCommentsLinkXpath)
	private WebElement hideCommentsLink;
	@FindBy(xpath = commentsXpath)
	private WebElement comments;
	@FindBy(xpath = fileUploadChooserXpath)
	private WebElement fileUploadChooser;
	@FindBy(xpath = submitFileXpath)
	private WebElement submitFile;
	@FindBy(xpath = uploadedFileXpath)
	private WebElement uploadedFile;
	@FindBy(xpath = submitButtonXpath)
	private WebElement submitButton;
	@FindBy(xpath = preferencesSubmitButtonXpath)
	private WebElement preferencesSubmitButton;
	@FindBy(xpath = editPreferencesButtonXpath)
	private WebElement editPreferencesButton;
	@FindBy(xpath = returnLinkXpath)
	private WebElement returnLink;
	@FindBy(xpath = mojarraVersionXpath)
	private WebElement mojarraVersion;
	@FindBy(xpath = componentLibraryVersionXpath)
	private WebElement componentLibraryVersion;
	@FindBy(xpath = alloyVersionXpath)
	private WebElement alloyVersion;
	@FindBy(xpath = bridgeVersionXpath)
	private WebElement bridgeVersion;
	@FindBy(xpath = versionUlXpath)
	private WebElement versionUl;
	@FindBy(xpath = windowInnerHeightXpath)
	private WebElement windowInnerHeight;
	@FindBy(xpath = windowInnerWidthXpath)
	private WebElement windowInnerWidth;

	protected String innerHeight = "-1";
	protected String innerWidth = "-1";

	@Drone
	WebDriver browser;

	@Test
	@RunAsClient
	@InSequence(1000)
	public void jobApplicantRenderViewMode() throws Exception {

		logger.log(Level.INFO, "portal = " + portal);
		logger.log(Level.INFO, "baseUrl = " + baseUrl);
		logger.log(Level.INFO, "signInContext = " + signInContext);
		logger.log(Level.INFO, "webContext = " + webContext);

		signIn(browser);

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		getPortletDisplayName();
		logger.log(Level.INFO, "displayName.getText() = " + displayName.getText());

		assertTrue("displayName.isDisplayed()", displayName.isDisplayed());

		if (isThere(browser, logoXpath)) {
			assertTrue("logo.isDisplayed()", logo.isDisplayed());
		}

		assertTrue("firstNameField.isDisplayed()", firstNameField.isDisplayed());
		assertTrue("lastNameField.isDisplayed()", lastNameField.isDisplayed());
		assertTrue("emailAddressField.isDisplayed()", emailAddressField.isDisplayed());
		assertTrue("phoneNumberField.isDisplayed()", phoneNumberField.isDisplayed());

		assertTrue("dateOfBirthField.isDisplayed()", dateOfBirthField.isDisplayed());
		assertTrue("cityField.isDisplayed()", cityField.isDisplayed());
		assertTrue("isThere(browser, provinceIdFieldXpath)", isThere(browser, provinceIdFieldXpath));
		assertTrue("postalCodeField.isDisplayed()", postalCodeField.isDisplayed());
		if (isThere(browser, postalCodeToolTipXpath)) {
			assertTrue("postalCodeToolTip.isDisplayed()", postalCodeToolTip.isDisplayed());
		} else {
			assertTrue("Postal code tool tips should be present, but are not.  No postal code tool tips present", false);
		}

		assertTrue("showCommentsLink.isDisplayed()", showCommentsLink.isDisplayed());

		if (isThere(browser, fileUploadChooserXpath)) {
			logger.log(Level.INFO, "fileUploadChooser.isDisplayed() = " + fileUploadChooser.isDisplayed());
			logger.log(Level.INFO, "submitFile.isDisplayed() = " + submitFile.isDisplayed());
		}

		assertTrue("submitButton.isDisplayed()", submitButton.isDisplayed());
		logger.log(Level.INFO, "submitButton.getTagName() = " + submitButton.getTagName());

		assertTrue("mojarraVersion.isDisplayed()", mojarraVersion.isDisplayed());
		logger.log(Level.INFO, mojarraVersion.getText());

		if (isThere(browser, componentLibraryVersionXpath)) {
			assertTrue("componentLibraryVersion.isDisplayed()", componentLibraryVersion.isDisplayed());
			logger.log(Level.INFO, componentLibraryVersion.getText());
		}

		assertTrue("alloyVersion.isDisplayed()", alloyVersion.isDisplayed());
		logger.log(Level.INFO, alloyVersion.getText());
		assertTrue("bridgeVersion.isDisplayed()", bridgeVersion.isDisplayed());
		logger.log(Level.INFO, bridgeVersion.getText());

		// get the window.innerHeight and window.innerWidth
		try {

			((JavascriptExecutor) browser).executeScript(
				"var w = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; " +
				"var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight; "

				+ "var em1 = document.createElement('em'); "
				+ "var id1 = document.createAttribute('id'); "
				+ "id1.value = 'window.innerHeight'; "
				+ "em1.setAttributeNode(id1); "
				+ "var textNode1 = document.createTextNode(h); "
				+ "em1.appendChild(textNode1); "
				+ "var li1 = document.createElement('li'); "
				+ "li1.appendChild(em1); "

				+ "var em2 = document.createElement('em'); "
				+ "var id2 = document.createAttribute('id'); "
				+ "id2.value = 'window.innerWidth'; "
				+ "em2.setAttributeNode(id2); "
				+ "var textNode2 = document.createTextNode(w); "
				+ "em2.appendChild(textNode2); "
				+ "var li2 = document.createElement('li'); "
				+ "li2.appendChild(em2); "

				+ "arguments[0].appendChild(li1); "
				+ "arguments[0].appendChild(li2); ",

				versionUl
			);

		} catch (Exception e) {
			logger.log(Level.INFO,
					"Exception e.getMessage() = " + e.getMessage());
		}

		waitForElement(browser, windowInnerWidthXpath);
		if (isThere(browser, windowInnerWidthXpath)) {
			logger.log(Level.INFO,
					"window.innerHeight = " + windowInnerHeight.getText());
			logger.log(Level.INFO,
					"window.innerWidth = " + windowInnerWidth.getText());

			innerHeight = windowInnerHeight.getText();
			innerWidth = windowInnerWidth.getText();
		} else {
			logger.log(Level.INFO, windowInnerWidthXpath + " not found.");
		}

		Dimension bodyDimension = browser.findElement(By.xpath("//body[1]"))
				.getSize();
		logger.log(Level.INFO, "bodyDimension.height = " + bodyDimension.height);
		logger.log(Level.INFO, "bodyDimension.width = " + bodyDimension.width);

		Dimension windowDimension = browser.manage().window().getSize();
		logger.log(Level.INFO, "windowDimension.height = " + windowDimension.height);
		logger.log(Level.INFO, "windowDimension.width = " + windowDimension.width);

	}

	@Test
	@RunAsClient
	@InSequence(1500)
	public void dataEntry() throws Exception {
		
		String foo = "";

		logger.log(Level.INFO, "clicking into the firstNameField ...");
		firstNameField.click();
		logger.log(Level.INFO, "tabbing into the next field ...");
		firstNameField.sendKeys(Keys.TAB);
		
		logger.log(Level.INFO, "firstNameField.getAttribute('value') = " + firstNameField.getAttribute("value"));
		logger.log(Level.INFO, "isThere(browser, firstNameFieldErrorXpath) = " + isThere(browser, firstNameFieldErrorXpath));

		if (isThere(browser, firstNameFieldErrorXpath)) { // houston we have a problem
			logger.log(Level.INFO, "firstNameFieldError.isDisplayed() = " + firstNameFieldError.isDisplayed());
			assertFalse(
				"firstNameFieldError should not be displayed after simply tabbing out of the empty field, having never entered any data.  " +
				"But we see '" + firstNameFieldError.getText() + "'", firstNameFieldError.isDisplayed());
		}

		logger.log(Level.INFO, "Shift tabbing back into the firstNameField ...");
		(new Actions(browser)).keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyDown(Keys.SHIFT).perform();

		logger.log(Level.INFO, "isThere(browser, firstNameFieldErrorXpath) = " + isThere(browser, firstNameFieldErrorXpath));

		logger.log(Level.INFO, "entering 'asdf' into the firstNameField and then tabbing out of it...");
		firstNameField.sendKeys("asdf");
		firstNameField.sendKeys(Keys.TAB);

		logger.log(Level.INFO, "firstNameField.getAttribute('value') = " + firstNameField.getAttribute("value"));
		logger.log(Level.INFO, "isThere(browser, firstNameFieldErrorXpath) = " + isThere(browser, firstNameFieldErrorXpath));
		assertTrue("The data 'asdf' should be in the firstNameField after tabbing out of it",
			"asdf".equals(firstNameField.getAttribute("value")));

		logger.log(Level.INFO, "Shift tabbing back into the firstNameField ...");
		(new Actions(browser)).keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();

		firstNameField.click();
		logger.log(Level.INFO,
			"clearing the firstNameField using the BACKSPACE key, and then tabbing out of the firstNameField ...");
		firstNameField.sendKeys(Keys.BACK_SPACE);

		logger.log(Level.INFO, "Waiting for the firstNameField to contain 'asd' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(firstNameFieldXpath), "asd"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, firstNameFieldXpath)) {
				foo = firstNameField.getText();
			}
			logger.log(Level.INFO, "firstNameField.getText() = " + firstNameField.getText());
			assertTrue("firstNameField should contain 'asd', but " + firstNameFieldXpath + " contains '" + foo + "'.", false);
		}

		firstNameField.sendKeys(Keys.BACK_SPACE);

		logger.log(Level.INFO, "Waiting for the firstNameField to contain 'as' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(firstNameFieldXpath), "as"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, firstNameFieldXpath)) {
				foo = firstNameField.getText();
			}
			assertTrue("firstNameField should contain 'as', but " + firstNameFieldXpath + " contains '" + foo + "'.", false);
		}

		firstNameField.sendKeys(Keys.BACK_SPACE);

		logger.log(Level.INFO, "Waiting for the firstNameField to contain 'a' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(firstNameFieldXpath), "a"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, firstNameFieldXpath)) {
				foo = firstNameField.getText();
			}
			assertTrue("firstNameField should contain 'a', but " + firstNameFieldXpath + " contains '" + foo + "'.", false);
		}

		firstNameField.sendKeys(Keys.BACK_SPACE);

		logger.log(Level.INFO, "Waiting for the firstNameField to contain '' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(firstNameFieldXpath), ""));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, firstNameFieldXpath)) {
				foo = firstNameField.getText();
			}
			assertTrue("firstNameField should contain '', but " + firstNameFieldXpath + " contains '" + foo + "'.", false);
		}

		firstNameField.sendKeys(Keys.TAB);

		logger.log(Level.INFO, "Waiting for the firstNameFieldError to contain 'Value is required' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(firstNameFieldErrorXpath), "Value is required"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("firstNameFieldError should be visible after tabbing out with no value," +
			" but " + firstNameFieldErrorXpath + " is not visible.", false);
		}

		logger.log(Level.INFO, "firstNameField.getAttribute('value') = " + firstNameField.getAttribute("value"));
		assertTrue(
			"The data 'asdf' should no longer be in the firstNameField after clearing it out with BACK_SPACE and then tabbing out.	" +
			"But we see '" + firstNameField.getAttribute("value") + "'",
			"".equals(firstNameField.getAttribute("value")));
		logger.log(Level.INFO, "isThere(browser, firstNameFieldErrorXpath) = " + isThere(browser, firstNameFieldErrorXpath));
		assertTrue("The firstNameFieldError should at least be in the DOM somewhere by this point, but it is not there",
			isThere(browser, firstNameFieldErrorXpath));
		logger.log(Level.INFO, "firstNameFieldError.getText() = " + firstNameFieldError.getText());
		assertTrue("The firstNameFieldError should say 'Value is required'",
			firstNameFieldError.getText().contains("Value is required"));

	}

	@Test
	@RunAsClient
	@InSequence(2000)
	public void validateEmail() throws Exception {

		String foo = "";

		// checks an invalid email address
		logger.log(Level.INFO, "Entering an invalid email address 'test' ...");
		emailAddressField.sendKeys("test");
		phoneNumberField.click();

		logger.log(Level.INFO, "Waiting for the emailAddressFieldError to contain 'Invalid e-mail address' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(emailAddressFieldErrorXpath), "Invalid e-mail address"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("emailAddressField should be visible after tabbing out with no value," +
			" but " + emailAddressFieldErrorXpath + " is not visible.", false);
		}
		logger.log(Level.INFO, "emailAddressField.getAttribute('value') = " + emailAddressField.getAttribute("value"));

		assertTrue("Invalid e-mail address validation message displayed",
			emailAddressFieldError.getText().contains("Invalid e-mail address"));
		logger.log(Level.INFO, "emailAddressFieldError.isDisplayed() = " + emailAddressFieldError.isDisplayed());
		logger.log(Level.INFO, "emailAddressFieldError.getText() = " + emailAddressFieldError.getText());

		// checks a valid email address
		logger.log(Level.INFO, "Waiting for the emailAddressFieldError to contain '' ...");
		try {
			emailAddressField.clear();
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(emailAddressFieldXpath), ""));
			logger.log(Level.INFO, "emailAddressField.getText() = '" + emailAddressField.getText() + "'");
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, emailAddressFieldXpath)) {
				foo = emailAddressField.getText();
			}
			assertTrue("emailAddressField should be clear," +
			" but " + emailAddressFieldXpath + " contains '" + foo + "'", false);
		}
		
		logger.log(Level.INFO, "Entering a valid email address 'test@liferay.com' ...");
		emailAddressField.click();
		emailAddressField.sendKeys("test@liferay.com");
		emailAddressField.sendKeys(Keys.TAB);
		phoneNumberField.click();
		
		logger.log(Level.INFO, "Waiting for the emailAddressFieldError to contain 'test@liferay.com' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(emailAddressFieldXpath), "test@liferay.com"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, emailAddressFieldXpath)) {
				foo = emailAddressField.getText();
			}
			assertTrue("emailAddressField should contain 'test@liferay.com'," +
			" but " + emailAddressFieldXpath + " contains '" + foo + "'", false);
		}

		logger.log(Level.INFO, "Waiting for the emailAddressFieldError to disappear ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(emailAddressFieldErrorXpath)));
			logger.log(Level.INFO, "emailAddressField.getAttribute('value') = " + emailAddressField.getAttribute("value"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, emailAddressFieldErrorXpath)) {
				foo = emailAddressFieldError.getText();
			}
			assertTrue("emailAddressFieldError should NOT be visible after entering 'test@liferay.com'," +
			" but " + emailAddressFieldErrorXpath + " is still there showing '" + foo + "'", false);
		}

	}

	@Test
	@RunAsClient
	@InSequence(3000)
	public void preferencesAndEditMode() throws Exception {

		// test for both
		int dateLengthAfterChange = 8;
		int dateLengthAfterReset = 10;

		logger.log(Level.INFO, "preferencesMenuButton.click() ... ");
		logger.log(Level.INFO, "Waiting for datePatternField to show on the page ... ");

		selectEditMode(browser, portal);

		logger.log(Level.INFO, "done with selectEditMode: isThere(browser, datePatternFieldXpath) = " + isThere(browser, datePatternFieldXpath));
		logger.log(Level.INFO, "datePatternField.getAttribute('value') = " + datePatternField.getAttribute("value"));
		logger.log(Level.INFO, "resetButton.isDisplayed() = " + resetButton.isDisplayed());

		// MM/dd/yyyy
		datePatternField.clear();
		datePatternField.sendKeys("MM/dd/yy");
		preferencesSubmitButton.click();

		// after clicking the preferencesSubmitButton, all of the job applicant demos need to end up on the same
		// page.  Here is a log statement that should give you a clue between the different testers as to which ones are
		// different from others
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);

		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dateOfBirthFieldXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("dateOfBirthField should be visible after navigating to " + url + "," +
			" but " + dateOfBirthFieldXpath + " is not visible.", false);
		}
		
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO,
			"dateOfBirthField.getAttribute('value').length() = " + dateOfBirthField.getAttribute("value").length());

		assertTrue("dateOfBirthField should have " + dateLengthAfterChange +
			" characters after changing preferences to MM/dd/yy, but " +
			dateOfBirthField.getAttribute("value").length() + " != " + dateLengthAfterChange,
			dateOfBirthField.getAttribute("value").length() == dateLengthAfterChange);

		if (isThere(browser, editPreferencesButtonXpath)) {
			logger.log(Level.INFO, "editPreferencesButton.click() ...");
			editPreferencesButton.click();
		}
		else {
			logger.log(Level.INFO, "NO editPreferencesButton isThere, so preferencesMenuItem.click() ...");
			selectEditMode(browser, portal);
		}

		logger.log(Level.INFO, "Waiting for the resetButton to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(resetButtonXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("resetButton should be visible after selecting edit mode," +
			" but " + resetButtonXpath + " is not visible.", false);
		}

		logger.log(Level.INFO, "done with selectEditMode: isThere(browser, resetButtonXpath) = " + isThere(browser, resetButtonXpath));
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());

		logger.log(Level.INFO, "resetButton.click() ...");
		resetButton.click();

		// after clicking the resetButton all of the job applicant demos need to end up on the same page Here is a
		// log statement that should give you a clue between the different testers as to which ones are different from
		// others
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);

		logger.log(Level.INFO, "Waiting for the dateOfBirthField to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dateOfBirthFieldXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("dateOfBirthField should be visible after navigating back to view mode," +
			" but " + dateOfBirthFieldXpath + " is not visible.", false);
		}

		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO,
			"dateOfBirthField.getAttribute('value').length() = " + dateOfBirthField.getAttribute("value").length());

		assertTrue("date of birth has " + dateLengthAfterReset + " characters after resetting preferences",
			dateOfBirthField.getAttribute("value").length() == dateLengthAfterReset);

	}

	@Test
	@RunAsClient
	@InSequence(4000)
	public void reset() throws Exception {

		// because some test failures throw us into a strange state,
		// let's reset preferences and the page we are on
		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);

		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "isThere(browser, dateOfBirthFieldXpath) = " + isThere(browser, dateOfBirthFieldXpath));

		logger.log(Level.INFO, "Waiting for the dateOfBirthField to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dateOfBirthFieldXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			refreshBrowser();
			assertTrue("dateOfBirthField should have been visible after navigating back to view mode," +
			" but " + dateOfBirthFieldXpath + " was not visible.", false);
		}

		selectEditMode(browser, portal);
		
		logger.log(Level.INFO, "Waiting for the resetButton to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(resetButtonXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("resetButton should be visible after selecting edit mode," +
			" but " + resetButtonXpath + " is not visible.", false);
		}

		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());

		logger.log(Level.INFO, "resetButton.click() ...");
		resetButton.click();
		
		logger.log(Level.INFO, "Waiting for the firstNameField to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(firstNameFieldXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			refreshBrowser();
			assertTrue("firstNameField should have been visible after clicking the reset button," +
			" but " + firstNameFieldXpath + " was not visible.", false);
		}

	}
	
	// because some test failures throw us into a strange state,
	// let's refresh the browser to get back to the page we need to test
	public void refreshBrowser() throws Exception {

		browser.manage().deleteAllCookies();
		signIn(browser);

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);

		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "isThere(browser, dateOfBirthFieldXpath) = " + isThere(browser, dateOfBirthFieldXpath));

	}

	@Test
	@RunAsClient
	@InSequence(5000)
	public void allFieldsRequiredUponSubmit() throws Exception {

		logger.log(Level.INFO, "Waiting for the firstNameField to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(firstNameFieldXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("firstNameField should be visible after reset," +
			" but " + firstNameFieldXpath + " is not visible.", false);
		}
		
		logger.log(Level.INFO, "clearing fields ...");
		firstNameField.clear();
		lastNameField.clear();
		emailAddressField.clear();
		phoneNumberField.clear();

		try {
			dateOfBirthField.clear();
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
		}

		cityField.clear();
		postalCodeField.clear();
		logger.log(Level.INFO, "clicking submit ...");
		submitButton.click();

		logger.log(Level.INFO, "Waiting for the firstNameFieldError to contain 'Value is required' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(firstNameFieldErrorXpath), "Value is required"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("firstNameFieldError should be visible after tabbing out with no value," +
			" but " + firstNameFieldErrorXpath + " is not visible.", false);
		}

		logger.log(Level.INFO, "firstNameFieldError.getText() = " + firstNameFieldError.getText());
		logger.log(Level.INFO, "lastNameFieldError.getText() = " + lastNameFieldError.getText());
		logger.log(Level.INFO, "emailAddressFieldError.getText() = " + emailAddressFieldError.getText());
		logger.log(Level.INFO, "phoneNumberFieldError.getText() = " + phoneNumberFieldError.getText());
		logger.log(Level.INFO, "dateOfBirthFieldError.getText() = " + dateOfBirthFieldError.getText());
		logger.log(Level.INFO, "cityFieldError.getText() = " + cityFieldError.getText());

		logger.log(Level.INFO, "isThere(browser, provinceIdFieldXpath) = " + isThere(browser, provinceIdFieldXpath));
		logger.log(Level.INFO, "isThere(browser, provinceIdFieldErrorXpath) = " + isThere(browser, provinceIdFieldErrorXpath));

		assertTrue("firstNameFieldError should contain 'Value is required', but instead contains '" +
			firstNameFieldError.getText() + "'", firstNameFieldError.getText().contains("Value is required"));
		assertTrue("lastNameFieldError should contain 'Value is required', but instead contains '" +
			lastNameFieldError.getText() + "'", lastNameFieldError.getText().contains("Value is required"));
		assertTrue("emailAddressFieldError should contain 'Value is required', but instead contains '" +
			emailAddressFieldError.getText() + "'", emailAddressFieldError.getText().contains("Value is required"));
		assertTrue("phoneNumberFieldError should contain 'Value is required', but instead contains '" +
			phoneNumberFieldError.getText() + "'", phoneNumberFieldError.getText().contains("Value is required"));
		assertTrue("dateOfBirthFieldError should contain 'Value is required', but instead contains '" +
			dateOfBirthFieldError.getText() + "'", dateOfBirthFieldError.getText().contains("Value is required"));
		assertTrue("cityFieldError should contain 'Value is required', but instead contains '" +
			cityFieldError.getText() + "'", cityFieldError.getText().contains("Value is required"));
		assertTrue("provinceIdFieldError should contain 'Value is required', but instead contains '" +
			provinceIdFieldError.getText() + "'", provinceIdFieldError.getText().contains("Value is required"));
		assertTrue("postalCodeFieldError should contain 'Value is required', but instead contains '" +
			postalCodeFieldError.getText() + "'", postalCodeFieldError.getText().contains("Value is required"));

	}

	@Test
	@RunAsClient
	@InSequence(6000)
	public void cityAndStateAutoPopulate() throws Exception {

		logger.log(Level.INFO, "Waiting for the cityField to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(cityFieldXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("cityField should be visible after submitting the form," +
			" but " + cityFieldXpath + " is not visible.", false);
		}
		
		logger.log(Level.INFO, "before cityField.getAttribute('value') = " + cityField.getAttribute("value"));
		logger.log(Level.INFO,
			"before provinceIdField.getAttribute('value') = " + provinceIdField.getAttribute("value"));
		logger.log(Level.INFO,
			"before postalCodeField.getAttribute('value') = " + postalCodeField.getAttribute("value"));
		assertTrue("cityField is empty", (cityField.getAttribute("value") == null || cityField.getAttribute("value").length() == 0));
		assertTrue("provinceIdField is empty", (provinceIdField.getAttribute("value") == null || provinceIdField.getAttribute("value").length() == 0));
		assertTrue("postalCodeField is empty", (postalCodeField.getAttribute("value") == null || postalCodeField.getAttribute("value").length() == 0));

		postalCodeField.sendKeys("32801");
		phoneNumberField.click();
		
		logger.log(Level.INFO, "Waiting for the cityField to contain 'Orlando' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(cityFieldXpath), "Orlando"));
			logger.log(Level.INFO, "after cityField.getAttribute('value') = " + cityField.getAttribute("value"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("cityField should conatin 'Orlando' after entering postal code 32801 and clicking into another field," +
			" but " + cityFieldXpath + " contains '" + cityField.getText() + "'", e == null);
		}

		logger.log(Level.INFO,
			"after postalCodeField.getAttribute('value') = " + postalCodeField.getAttribute("value"));
		assertTrue("cityField should contain 'Orlando' after auto populating from postalCode, but instead contains '" +
			cityField.getAttribute("value") + "'", cityField.getAttribute("value").contains("Orlando"));

		// If the (primefaces) selector is empty
		if ("".equals(provinceIdSelectorXpath)) {
			logger.log(Level.INFO, " provinceIdFieldXpath tagName = " + browser.findElement(By.xpath(provinceIdFieldXpath)).getTagName());
			logger.log(Level.INFO, " provinceIdFieldXpath id = " + browser.findElement(By.xpath(provinceIdFieldXpath)).getAttribute("id"));
			logger.log(Level.INFO, " provinceIdField value = " + provinceIdField.getAttribute("value"));
			assertTrue("provinceIdField should contain 3, but instead it contains '" + provinceIdField.getAttribute("value") + "'", provinceIdField.getAttribute("value").contains("3"));
		}
		// otherwise, use the (primefaces) selector
		else {
			logger.log(Level.INFO, " provinceIdSelectorXpath tagName = " + browser.findElement(By.xpath(provinceIdSelectorXpath)).getTagName());
			logger.log(Level.INFO, " provinceIdSelectorXpath id = " + browser.findElement(By.xpath(provinceIdSelectorXpath)).getAttribute("id"));
			logger.log(Level.INFO, " provinceIdSelectorXpath value = " + browser.findElement(By.xpath(provinceIdSelectorXpath)).getAttribute("value"));
			assertTrue("provinceIdSelector should contain 3, but instead it contains '" + provinceIdSelector.getAttribute("value") + "'", provinceIdSelector.getAttribute("value").contains("3"));
		}
		assertTrue("postalCodeField contains 32801", postalCodeField.getAttribute("value").contains("32801"));

	}

	@Test
	@RunAsClient
	@InSequence(7000)
	public void commentsFunctioning() throws Exception {

		String testing123 = "testing 1, 2, 3";

		logger.log(Level.INFO, "Waiting for the showCommentsLink to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(showCommentsLinkXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("showCommentsLink should be visible," +
			" but " + showCommentsLinkXpath + " is not visible.", false);
		}
		 	
		showCommentsLink.click();

		logger.log(Level.INFO, "Waiting for the hideCommentsLink to contain 'Hide Comments' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(hideCommentsLinkXpath), "Hide Comments"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("'Hide Comments' should be visible after clicking 'Show Comments'," +
			" but //a[contains(text(),'Hide Comments')] is not visible.", false);
		}
		
		logger.log(Level.INFO, "comments.isDisplayed() = " + comments.isDisplayed());
		assertTrue("comments textarea is displayed", comments.isDisplayed());
		
		comments.sendKeys(testing123);
		phoneNumberField.click();

		logger.log(Level.INFO, "Waiting for the hideCommentsLinkXpath to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(hideCommentsLinkXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("hideCommentsLink should be visible," +
			" but " + hideCommentsLinkXpath + " is not visible.", false);
		}

		hideCommentsLink.click();

		logger.log(Level.INFO, "Waiting for the showCommentsLink to contain 'Show Comments' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(showCommentsLinkXpath), "Show Comments"));
			logger.log(Level.INFO, "showCommentsLink.isDisplayed() = " + showCommentsLink.isDisplayed());
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("'Show Comments' should be visible after clicking 'Hide Comments'," +
			" but " + showCommentsLinkXpath + " is not visible.", false);
		}

		showCommentsLink.click();

		logger.log(Level.INFO, "Waiting for the commentsXpath to contain '" + testing123 + "' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(commentsXpath), testing123));
			logger.log(Level.INFO,
					"after hide and show comments.getAttribute('value') = " + comments.getAttribute("value"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("comments should be visible after clicking 'Hide Comments'," +
			" but " + commentsXpath + " is not visible.", false);
		}
		
		assertTrue("comments should be there after hide and then show, but comments value is '" +
			comments.getAttribute("value") +"' after clicking show comments.", testing123.equals(comments.getAttribute("value")));

	}

	@Test
	@RunAsClient
	@InSequence(8000)
	public void dateValidation() throws Exception {

		String foo = "";

		// checks an invalid dateOfBirth
		try {
			dateOfBirthField.clear();
			logger.log(Level.INFO, "No exceptions occured when clearing the dateOfBirthField");
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("No exceptions should occur when clearing the dateOfBirthField, but one did occur with the following message: " + e.getMessage(), false);
		}

		logger.log(Level.INFO, "Waiting for the dateOfBirthField to contain '' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(dateOfBirthFieldXpath), ""));
			logger.log(Level.INFO,
					"after clearing dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, dateOfBirthFieldErrorXpath)) {
				foo = dateOfBirthFieldError.getText();
			}
			assertTrue("dateOfBirthField should be empty after clearing," +
			" but " + dateOfBirthFieldXpath + " contains '" + foo + "'.", false);
		}

		logger.log(Level.INFO, "Entering an invalid value for the date of birth ... 12/34/5678 ...");
		dateOfBirthField.sendKeys("12/34/5678");

		logger.log(Level.INFO, "Waiting for the dateOfBirthField to contain '12/34/5678' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(dateOfBirthFieldXpath), "12/34/5678"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, dateOfBirthFieldErrorXpath)) {
				foo = dateOfBirthFieldError.getText();
			}
			assertTrue("dateOfBirthField should contain '12/34/5678'," +
			" but " + dateOfBirthFieldXpath + " contains '" + foo + "'.", false);
		}
		
		submitButton.click();

		logger.log(Level.INFO, "Waiting for the dateOfBirthFieldError to contain 'Invalid date format' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(dateOfBirthFieldErrorXpath), "Invalid date format"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("dateOfBirthFieldError should be visible after submitting with '12/34/5678'," +
			" but " + dateOfBirthFieldErrorXpath + " is not visible.", false);
		}

		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO, "dateOfBirthFieldError.isDisplayed() = " + dateOfBirthFieldError.isDisplayed());
		logger.log(Level.INFO, "dateOfBirthFieldError.getText() = " + dateOfBirthFieldError.getText());
		assertTrue("dateOfBirthFieldError should contain 'Invalid date format', but insteead contains '" +
			dateOfBirthFieldError.getText() + "'", dateOfBirthFieldError.getText().contains("Invalid date format"));

		// checks with no dateOfBirth
		dateOfBirthField.clear();
		
		logger.log(Level.INFO, "Waiting for the dateOfBirthField to contain '' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(dateOfBirthFieldXpath), ""));
			logger.log(Level.INFO,
					"after clearing dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, dateOfBirthFieldErrorXpath)) {
				foo = dateOfBirthFieldError.getText();
			}
			assertTrue("dateOfBirthField should be empty after clearing," +
			" but " + dateOfBirthFieldXpath + " contains '" + foo + "'.", false);
		}
		
		phoneNumberField.click();

		logger.log(Level.INFO, "Waiting for the dateOfBirthFieldError to contain 'Value is required' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(dateOfBirthFieldErrorXpath), "Value is required"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("dateOfBirthFieldError should be visible after entering '12/34/5678' and clicking into another field," +
			" but " + dateOfBirthFieldErrorXpath + " does not contain 'Value is required'.", false);
		}

		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO, "dateOfBirthFieldError.isDisplayed() = " + dateOfBirthFieldError.isDisplayed());
		logger.log(Level.INFO, "dateOfBirthFieldError.getText() = " + dateOfBirthFieldError.getText());

		// checks a valid dateOfBirth
		foo = "";
		dateOfBirthField.clear();

		logger.log(Level.INFO, "Waiting for the dateOfBirthField to contain '' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(dateOfBirthFieldXpath), ""));
			logger.log(Level.INFO,
					"after clearing dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, dateOfBirthFieldErrorXpath)) {
				foo = dateOfBirthFieldError.getText();
			}
			assertTrue("dateOfBirthField should be empty after clearing," +
			" but " + dateOfBirthFieldXpath + " contains '" + foo + "'.", false);
		}
		
		logger.log(Level.INFO, "Entering a valid dateOfBirth = 01/02/3456 ...");
		dateOfBirthField.sendKeys("01/02/3456");
		logger.log(Level.INFO, "Clicking into the phoneNumberField ...");
		phoneNumberField.click();

		logger.log(Level.INFO, "Waiting for the dateOfBirthFieldError to disappear ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(dateOfBirthFieldErrorXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, dateOfBirthFieldErrorXpath)) {
				foo = dateOfBirthFieldError.getText();
			}
			assertTrue("dateOfBirthFieldError should NOT be visible after entering 01/02/3456," +
			" but " + dateOfBirthFieldErrorXpath + " is still there showing '" + foo + "'", false);
		}

	}

	@Test
	@RunAsClient
	@InSequence(8500)
	public void fileUpload() throws Exception {

		boolean uploaded = false;

		if (isThere(browser, fileUploadChooserXpath)) {
			logger.log(Level.INFO, "isThere(browser, fileUploadChooserXpath) = " + isThere(browser, fileUploadChooserXpath));
		}
		else {
			
			// the ice1-portlet seems to render the input type="file" in a separate iframe ... why bother?
			// assuming that this is the jsf2-jsp-portlet and waiting for its 'Add Attachment' button to appear
			logger.log(Level.INFO, "Waiting for the //input[@type='submit' and @value='Add Attachment'] to show on the page ...");
			try {
				WebDriverWait wait = new WebDriverWait(browser, 10);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='submit' and @value='Add Attachment']")));
			}
			catch (Exception e) {
				logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
				assertTrue("'Add Attachment' should be visible," +
				" but //input[@type='submit' and @value='Add Attachment'] is not visible.", false);
			}

			// As of the time of this comment, only the jsf2-jsp-portlet did not render a fileUploadChooser on the
			// front view
			logger.log(Level.INFO, "clicking the Add Attachment button ...");
			browser.findElement(By.xpath("//input[@type='submit' and @value='Add Attachment']")).click();

			logger.log(Level.INFO, "Waiting for the fileUploadChooser to show on the page ...");
			try {
				WebDriverWait wait = new WebDriverWait(browser, 10);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fileUploadChooserXpath)));
			}
			catch (Exception e) {
				logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
				assertTrue("fileUploadChooser should be visible," +
				" but " + fileUploadChooserXpath + " is not visible.", false);
			}
		}

		logger.log(Level.INFO, "fileUploadChooser.getCssValue(transform) = " + fileUploadChooser.getCssValue("transform"));
		logger.log(Level.INFO, "fileUploadChooser.getCssValue(visibility) = " + fileUploadChooser.getCssValue("visibility"));
		logger.log(Level.INFO, "fileUploadChooser.getCssValue(display) = " + fileUploadChooser.getCssValue("display"));
		logger.log(Level.INFO, "fileUploadChooser.getCssValue(display) = " + fileUploadChooser.getCssValue("display"));
		logger.log(Level.INFO, "fileUploadChooser.getCssValue(opacity) = " + fileUploadChooser.getCssValue("opacity"));
		logger.log(Level.INFO, "fileUploadChooser.getCssValue(height) = " + fileUploadChooser.getCssValue("height"));
		logger.log(Level.INFO, "fileUploadChooser.getCssValue(width) = " + fileUploadChooser.getCssValue("width"));
		logger.log(Level.INFO, "fileUploadChooser.getCssValue(overflow) = " + fileUploadChooser.getCssValue("overflow"));

		logger.log(Level.INFO, "fileUploadChooser.getAttribute(type) = " + fileUploadChooser.getAttribute("type"));

		logger.log(Level.INFO, "entering in " + getPathToJerseyFile() + " for fileUploadChooser ...");

		// This was the magic that fixed the primefaces4 fileupload component the transform needed to be set to 'none'
		((JavascriptExecutor)browser).executeScript("arguments[0].style.transform = 'none';", fileUploadChooser);

		// when transform is NOT set to 'none' then we get:
		// fileUploadChooser.getCssValue(transform) = matrix(4, 0, 0, 4, -300, 0)
		logger.log(Level.INFO, "fileUploadChooser.getCssValue(transform) = " + fileUploadChooser.getCssValue("transform"));

		try {

			fileUploadChooser.sendKeys(getPathToJerseyFile());

		} catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());

			if (e.getMessage().contains("Element is not currently visible")) {

				Dimension windowDimension = browser.manage().window().getSize();
				logger.log(Level.INFO, "windowDimension.height = " + windowDimension.height);
				logger.log(Level.INFO, "windowDimension.width = " + windowDimension.width);

				logger.log(Level.INFO, "attempting to resize the browser window ... ");
				browser.manage().window().setSize(new Dimension(1260, 747));

				windowDimension = browser.manage().window().getSize();
				logger.log(Level.INFO, "windowDimension.height = " + windowDimension.height);
				logger.log(Level.INFO, "windowDimension.width = " + windowDimension.width);

				fileUploadChooser.sendKeys(getPathToJerseyFile());

			} else {

				assertTrue("No unexpected exceptions should occur when clearing the dateOfBirthField, but one did occur with the following message: " + e.getMessage(), false);

			}
		}

		// submitFileXpath
		logger.log(Level.INFO, " submitFileXpath tagName = " + browser.findElement(By.xpath(submitFileXpath)).getTagName());
		logger.log(Level.INFO, " submitFileXpath type = " + browser.findElement(By.xpath(submitFileXpath)).getAttribute("type"));

		logger.log(Level.INFO, "Waiting for the submitFile button to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(submitFileXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("submitFile should be visible," +
			" but " + submitFileXpath + " is not visible.", false);
		}
		
		logger.log(Level.INFO, "submitting the uploaded file ...");
		submitFile.click();

		logger.log(Level.INFO, "Waiting for the uploadedFile to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(uploadedFileXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("uploadedFileX should be visible," +
			" but " + uploadedFileXpath + " is not visible.", false);
		}

		if (isThere(browser, uploadedFileXpath)) {
			logger.log(Level.INFO, "uploadedFile.getText() = " + uploadedFile.getText() + " is there.");
			uploaded = true;
		}

		if (uploaded) {
			assertTrue("uploadedFile.getText().contains('jersey') after 3 seconds",
				uploadedFile.getText().contains("jersey"));
		}
		else {
			assertTrue("file should have been uploaded, but was not ...", uploaded);
		}
	}

	@Test
	@RunAsClient
	@InSequence(9000)
	public void submitAndValidate() throws Exception {

		String foo = "";
		logger.log(Level.INFO, "clearing fields ...");
		
		logger.log(Level.INFO, "Waiting for the dateOfBirthField to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dateOfBirthFieldXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("dateOfBirthField should be visible after fileUpload," +
			" but " + dateOfBirthFieldXpath + " is not visible.", false);
		}

		try {
			dateOfBirthField.clear();
			logger.log(Level.INFO, "No exceptions occured when clearing the dateOfBirthField");
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
		}

		emailAddressField.clear();
		postalCodeField.clear();

		if (isThere(browser, hideCommentsLinkXpath)) {
			waitForElement(browser, "//textarea[contains(@id,':comments')]");
		}
		int commentsTextAreas = browser.findElements(By.xpath("//textarea[contains(@id,':comments')]")).size();
		logger.log(Level.INFO, "# of commentsTextAreas = " + commentsTextAreas);

		if (commentsTextAreas == 0) { // if comments were not previously exercised, then we may need to show the
									  // comments text area.
			try {
				waitForElement(browser, showCommentsLinkXpath);
			}
			catch (Exception e) {
				logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
				assertTrue("showCommentsLinkXpath should be visible when their is no text area for comments showing," +
						" but the " + showCommentsLinkXpath + " is not visible.", e == null);
			}

			showCommentsLink.click();

			logger.log(Level.INFO, "Waiting for the comments textrea to show on the page ...");
			try {
				WebDriverWait wait = new WebDriverWait(browser, 10);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(commentsXpath)));
			}
			catch (Exception e) {
				logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
				assertTrue("comments textarea should be visible after clicking 'Show Comments'," +
				" but " + commentsXpath + " is not visible.", false);
			}
			
			commentsTextAreas = browser.findElements(By.xpath(commentsXpath)).size();
			logger.log(Level.INFO, "# of commentsTextAreas = " + commentsTextAreas);
		}

		assertTrue("The commentsTextArea should be showing, but it is not visible.", commentsTextAreas == 1);

		comments.clear();

		logger.log(Level.INFO, "Waiting for the emailAddressField to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(emailAddressFieldXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("emailAddressField should be visible after clearing comments field," +
			" but " + emailAddressFieldXpath + " is not visible.", false);
		}

		logger.log(Level.INFO, "fields were cleared now, but let's see ...");
		logger.log(Level.INFO, "emailAddressField.getAttribute('value') = " + emailAddressField.getAttribute("value"));
		assertTrue("emailAddressField is empty after clearing and clicking into another field",
			"".equals(emailAddressField.getAttribute("value")));

		logger.log(Level.INFO, "entering data ...");
		firstNameField.sendKeys("David");
		lastNameField.sendKeys("Samuel");
		emailAddressField.sendKeys("no_need@just.pray");
		phoneNumberField.sendKeys("(way) too-good");

		try {
			dateOfBirthField.sendKeys("01/02/3456");
			logger.log(Level.INFO, "No exceptions occured when entering the dateOfBirthField");
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
		}

		postalCodeField.sendKeys("32801");
		logger.log(Level.INFO, "Clicking into phone number field ...");
		phoneNumberField.click();

		logger.log(Level.INFO, "Waiting for the commentsXpath to show on the page ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(commentsXpath)));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("comments should be visible after clicking into the phoneNumberField," +
			" but " + commentsXpath + " is not visible.", false);
		}

		String genesis11 = "Indeed the people are one and they all have one language, and this is what they begin to do ...";
		comments.sendKeys(genesis11);

		logger.log(Level.INFO, "Waiting for the comments to contain '" + genesis11 + "' ...");
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(commentsXpath), genesis11));
			logger.log(Level.INFO, "comments.getText() = " + comments.getText());
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			if (isThere(browser, commentsXpath)) {
				foo = comments.getText();
			}
			assertTrue("comments should be correct after entering," +
			" but " + commentsXpath + " contains '" + foo + "'.", false);
		}

		// asserting correct data is still there
		assertTrue("asserting that firstNameField.getText().equals('David'), " + "but it is '" +
			firstNameField.getAttribute("value") + "'", firstNameField.getAttribute("value").equals("David"));
		assertTrue("asserting that lastNameField.getText().equals('Samuel'), " + "but it is '" +
			lastNameField.getAttribute("value") + "'", lastNameField.getAttribute("value").equals("Samuel"));
		assertTrue("asserting that emailAddressField.getText().equals('no_need@just.pray'), " + "but it is '" +
			emailAddressField.getAttribute("value") + "'",
			emailAddressField.getAttribute("value").equals("no_need@just.pray"));
		assertTrue("asserting that phoneNumberField.getText().equals('(way) too-good'), " + "but it is '" +
			phoneNumberField.getAttribute("value") + "'",
			phoneNumberField.getAttribute("value").equals("(way) too-good"));
		assertTrue("asserting that dateOfBirthField.getText().equals('01/02/3456'), " + "but it is '" +
			dateOfBirthField.getAttribute("value") + "'", dateOfBirthField.getAttribute("value").equals("01/02/3456"));
		assertTrue("asserting that postalCodeField.getText().equals('32801'), " + "but it is '" +
			postalCodeField.getAttribute("value") + "'", postalCodeField.getAttribute("value").equals("32801"));
		assertTrue(
			"asserting that comments.getText().equals(genesis11), " +
			"but it is '" + comments.getAttribute("value") + "'",
			comments.getAttribute("value").equals(genesis11));

		logger.log(Level.INFO, "Correct data asserted.  Clicking submit button ...");
		submitButton.click();

		logger.log(Level.INFO, "Waiting for the text 'Dear ' to show on the page ...");		
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(formTagXpath), "Dear "));
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("formTag should contain 'Dear '," +
			" but " + formTagXpath + " does not contain 'Dear '.", false);
		}
	}
}
//J+
