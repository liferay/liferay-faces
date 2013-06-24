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

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.liferay.faces.test.util.TesterBase;


/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class Jsf2CdiPortletTest extends TesterBase {

	// form tag found after submitting
	private static final String formTagXpath = "//form[@method='post']";

	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";
	private static final String menuButtonXpath = "//a[contains(@id,'menuButton')]";
	private static final String menuPreferencesXpath = "//a[contains(@id,'menu_preferences')]";

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

	// xpath for specific tests
	private static final String dateValidationXpath = "//input[contains(@id,':dateOfBirth')]/../child::node()";

	static final String url = baseUrl + "/group/bridge-demos/jsf2-cdi";

	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	@FindBy(xpath = menuButtonXpath)
	private WebElement menuButton;
	@FindBy(xpath = menuPreferencesXpath)
	private WebElement menuPreferences;
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
	
	protected int dateValidationXpathModifier = 1;

	@Test
	@RunAsClient
	@InSequence(1000)
	public void jobApplicantRenderViewMode() throws Exception {

		signIn();
		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());

		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue("menuButton.isDisplayed()", menuButton.isDisplayed());
		assertFalse("menuPreferences is NOT displayed()", menuPreferences.isDisplayed());

		if (isThere(logoXpath)) {
			assertTrue("logo.isDisplayed()", logo.isDisplayed());
		}

		assertTrue("firstNameField.isDisplayed()", firstNameField.isDisplayed());
		assertTrue("lastNameField.isDisplayed()", lastNameField.isDisplayed());
		assertTrue("emailAddressField.isDisplayed()", emailAddressField.isDisplayed());
		assertTrue("phoneNumberField.isDisplayed()", phoneNumberField.isDisplayed());

		assertTrue("dateOfBirthField.isDisplayed()", dateOfBirthField.isDisplayed());
		assertTrue("cityField.isDisplayed()", cityField.isDisplayed());
		assertTrue("provinceIdField.isDisplayed()", provinceIdField.isDisplayed());
		assertTrue("postalCodeField.isDisplayed()", postalCodeField.isDisplayed());
		assertTrue("postalCodeToolTip.isDisplayed()", postalCodeToolTip.isDisplayed());

		assertTrue("showCommentsLink.isDisplayed()", showCommentsLink.isDisplayed());

		if (isThere(fileUploadChooserXpath)) {
			logger.log(Level.INFO, "fileUploadChooser.isDisplayed() = " + fileUploadChooser.isDisplayed());
			logger.log(Level.INFO, "submitFile.isDisplayed() = " + submitFile.isDisplayed());
		}

		assertTrue("submitButton.isDisplayed()", submitButton.isDisplayed());
		logger.log(Level.INFO, "submitButton.getTagName() = " + submitButton.getTagName());

		assertTrue("mojarraVersion.isDisplayed()", mojarraVersion.isDisplayed());
		logger.log(Level.INFO, mojarraVersion.getText());

		if (isThere(componentLibraryVersionXpath)) {
			assertTrue("componentLibraryVersion.isDisplayed()", componentLibraryVersion.isDisplayed());
			logger.log(Level.INFO, componentLibraryVersion.getText());
		}

		assertTrue("alloyVersion.isDisplayed()", alloyVersion.isDisplayed());
		logger.log(Level.INFO, alloyVersion.getText());
		assertTrue("bridgeVersion.isDisplayed()", bridgeVersion.isDisplayed());
		logger.log(Level.INFO, bridgeVersion.getText());

	}

	@Test
	@RunAsClient
	@InSequence(1500)
	public void dataEntry() throws Exception {

		logger.log(Level.INFO, "clicking into the firstNameField ...");
		firstNameField.click();
		Thread.sleep(50);
		logger.log(Level.INFO, "tabbing into the next field ...");
		firstNameField.sendKeys(Keys.TAB);
		Thread.sleep(500);
		logger.log(Level.INFO, "firstNameField.getAttribute('value') = " + firstNameField.getAttribute("value"));
		logger.log(Level.INFO, "isThere(firstNameFieldErrorXpath) = " + isThere(firstNameFieldErrorXpath));

		if (isThere(firstNameFieldErrorXpath)) { // houston we have a problem
			logger.log(Level.INFO, "firstNameFieldError.isDisplayed() = " + firstNameFieldError.isDisplayed());
			assertFalse(
				"firstNameFieldError should not be displayed after simply tabbing out of the empty field, having never entered any data.  " +
				"But we see '" + firstNameFieldError.getText() + "'", firstNameFieldError.isDisplayed());
		}

		logger.log(Level.INFO, "Shift tabbing back into the firstNameField ...");
		(new Actions(browser)).keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyDown(Keys.SHIFT).perform();
		Thread.sleep(50);
		logger.log(Level.INFO, "isThere(firstNameFieldErrorXpath) = " + isThere(firstNameFieldErrorXpath));

		logger.log(Level.INFO, "entering 'asdf' into the firstNameField and then tabbing out of it...");
		firstNameField.sendKeys("asdf");
		firstNameField.sendKeys(Keys.TAB);
		Thread.sleep(50);
		logger.log(Level.INFO, "firstNameField.getAttribute('value') = " + firstNameField.getAttribute("value"));
		logger.log(Level.INFO, "isThere(firstNameFieldErrorXpath) = " + isThere(firstNameFieldErrorXpath));
		assertTrue("The data 'asdf' should be in the firstNameField after tabbing out of it",
			"asdf".equals(firstNameField.getAttribute("value")));

		logger.log(Level.INFO, "Shift tabbing back into the firstNameField ...");
		(new Actions(browser)).keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyDown(Keys.SHIFT).perform();
		Thread.sleep(50);
		logger.log(Level.INFO,
			"clearing the firstNameField using the BACKSPACE key, and then tabbing out of the firstNameField ...");
		firstNameField.sendKeys(Keys.BACK_SPACE);
		Thread.sleep(50);
		firstNameField.sendKeys(Keys.BACK_SPACE);
		Thread.sleep(50);
		firstNameField.sendKeys(Keys.BACK_SPACE);
		Thread.sleep(50);
		firstNameField.sendKeys(Keys.BACK_SPACE);
		Thread.sleep(50);
		firstNameField.sendKeys(Keys.TAB);
		Thread.sleep(50);
		logger.log(Level.INFO, "firstNameField.getAttribute('value') = " + firstNameField.getAttribute("value"));
		assertTrue(
			"The data 'asdf' should no longer be in the firstNameField after clearing it out with BACK_SPACE and then tabbing out.  " +
			"But we see '" + firstNameField.getAttribute("value") + "'",
			"".equals(firstNameField.getAttribute("value")));
		logger.log(Level.INFO, "isThere(firstNameFieldErrorXpath) = " + isThere(firstNameFieldErrorXpath));
		assertTrue("The firstNameFieldError should at least be in the DOM somewhere by this point, but it is not there",
			isThere(firstNameFieldErrorXpath));
		logger.log(Level.INFO, "firstNameFieldError.getText() = " + firstNameFieldError.getText());
		assertTrue("The firstNameFieldError should say 'Value is required'",
			firstNameFieldError.getText().contains("Value is required"));

	}

	@Test
	@RunAsClient
	@InSequence(2000)
	public void validateEmail() throws Exception {

		int tags = 0;
		int tagsWhileValid = 0;

		// checks an invalid email address
		logger.log(Level.INFO, "Entering an invalid email address 'test' ...");
		emailAddressField.sendKeys("test");
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "emailAddressField.getAttribute('value') = " + emailAddressField.getAttribute("value"));
		tags = browser.findElements(By.xpath("//span[contains(text(),'Invalid e-mail address')]")).size();
		logger.log(Level.INFO, "# of error tags = " + tags);
		assertTrue("There should be an 'Invalid e-mail address' messaged displayed, but " + tags +
			" error messages are displayed", tags > tagsWhileValid);
		assertTrue("Invalid e-mail address validation message displayed",
			emailAddressFieldError.getText().contains("Invalid e-mail address"));
		logger.log(Level.INFO, "emailAddressFieldError.isDisplayed() = " + emailAddressFieldError.isDisplayed());
		logger.log(Level.INFO, "emailAddressFieldError.getText() = " + emailAddressFieldError.getText());

		// checks a valid email address
		logger.log(Level.INFO, "Entering a valid email address 'test@liferay.com' ...");
		emailAddressField.clear();
		Thread.sleep(500);
		emailAddressField.sendKeys("test@liferay.com");
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "emailAddressField.getAttribute('value') = " + emailAddressField.getAttribute("value"));
		tags = browser.findElements(By.xpath("//span[contains(text(),'Invalid e-mail address')]")).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("# of error tags == tagsWhileValid", tags == tagsWhileValid);

	}

	@Test
	@RunAsClient
	@InSequence(3000)
	public void preferencesAndEditMode() throws Exception {

		// test for both
		int dateLengthAfterChange = 8;
		int dateLengthAfterReset = 10;

		menuButton.click();
		Thread.sleep(500);
		menuPreferences.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "datePatternField.getAttribute('value') = " + datePatternField.getAttribute("value"));
		logger.log(Level.INFO, "resetButton.isDisplayed() = " + resetButton.isDisplayed());

		// MM/dd/yyyy
		datePatternField.clear();
		datePatternField.sendKeys("MM/dd/yy");
		preferencesSubmitButton.click();

		// TODO after clicking the preferencesSubmitButton all of the job applicant demos need to end up on the same
		// page Here is a log statement that should give you a clue between the different tester as to which ones are
		// different from others
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		Thread.sleep(1000);
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO,
			"dateOfBirthField.getAttribute('value').length() = " + dateOfBirthField.getAttribute("value").length());

		assertTrue("dateOfBirthField should have " + dateLengthAfterChange +
			" characters after changing preferences to MM/dd/yy, but " +
			dateOfBirthField.getAttribute("value").length() + " != " + dateLengthAfterChange,
			dateOfBirthField.getAttribute("value").length() == dateLengthAfterChange);

		if (isThere(editPreferencesButtonXpath)) {
			editPreferencesButton.click();
			Thread.sleep(500);
			logger.log(Level.INFO, "editPreferencesButton.click() ...");
		}
		else {
			logger.log(Level.INFO, "NO editPreferencesButton isThere, so menuPreferences.click() ...");
			menuButton.click();
			Thread.sleep(500);
			menuPreferences.click();
			Thread.sleep(500);
		}

		resetButton.click();
		logger.log(Level.INFO, "resetButton.click() ...");
		Thread.sleep(1000);

		// TODO after clicking the resetButton all of the job applicant demos need to end up on the same page Here is a
		// log statement that should give you a clue between the different tester as to which ones are different from
		// others
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);

		waitForElement(dateOfBirthFieldXpath);

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
		Thread.sleep(500);
		menuButton.click();
		Thread.sleep(500);
		menuPreferences.click();
		Thread.sleep(500);
		resetButton.click();
		logger.log(Level.INFO, "resetButton.click() ...");
		Thread.sleep(500);
		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		Thread.sleep(500);
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		assertTrue("We are on the correct page, which should be, url = " + url, browser.getCurrentUrl().contains(url));

	}

	@Test
	@RunAsClient
	@InSequence(5000)
	public void allFieldsRequiredUponSubmit() throws Exception {

		int tagsWhileInvalid = 1;

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
		Thread.sleep(500);

		logger.log(Level.INFO, "checking for error tags on firstNameField ...");

		int tags = browser.findElements(By.xpath(firstNameFieldErrorXpath)).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("# of error tags == tagsWhileInvalid", tags == tagsWhileInvalid);

		logger.log(Level.INFO, "firstNameFieldError.getText() = " + firstNameFieldError.getText());
		logger.log(Level.INFO, "lastNameFieldError.getText() = " + lastNameFieldError.getText());
		logger.log(Level.INFO, "emailAddressFieldError.getText() = " + emailAddressFieldError.getText());
		logger.log(Level.INFO, "phoneNumberFieldError.getText() = " + phoneNumberFieldError.getText());
		logger.log(Level.INFO, "dateOfBirthFieldError.getText() = " + dateOfBirthFieldError.getText());
		logger.log(Level.INFO, "cityFieldError.getText() = " + cityFieldError.getText());
		logger.log(Level.INFO, "provinceIdFieldError.getText() = " + provinceIdFieldError.getText());
		logger.log(Level.INFO, "postalCodeFieldError.getText() = " + postalCodeFieldError.getText());

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

		logger.log(Level.INFO, "before cityField.getAttribute('value') = " + cityField.getAttribute("value"));
		logger.log(Level.INFO,
			"before provinceIdField.getAttribute('value') = " + provinceIdField.getAttribute("value"));
		logger.log(Level.INFO,
			"before postalCodeField.getAttribute('value') = " + postalCodeField.getAttribute("value"));
		assertTrue("cityField is empty", (cityField.getAttribute("value").length() == 0));
		assertTrue("provinceIdField is empty", (provinceIdField.getAttribute("value").length() == 0));
		assertTrue("postalCodeField is empty", (postalCodeField.getAttribute("value").length() == 0));

		postalCodeField.sendKeys("32801");
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "after cityField.getAttribute('value') = " + cityField.getAttribute("value"));
		logger.log(Level.INFO,
			"after provinceIdField.getAttribute('value') = " + provinceIdField.getAttribute("value"));
		logger.log(Level.INFO,
			"after postalCodeField.getAttribute('value') = " + postalCodeField.getAttribute("value"));
		assertTrue("cityField should contain 'Orlando' after auto populating from postalCode, but instead contains '" +
			cityField.getAttribute("value") + "'", cityField.getAttribute("value").contains("Orlando"));
		assertTrue("provinceIdField contains 3", provinceIdField.getAttribute("value").contains("3"));
		assertTrue("postalCodeField contains 32801", postalCodeField.getAttribute("value").contains("32801"));

	}

	@Test
	@RunAsClient
	@InSequence(7000)
	public void commentsFunctioning() throws Exception {

		String testing123 = "testing 1, 2, 3";
		int tags = 0;
		int tagsWhileHidden = 1;
		int tagsWhileShowing = 2;

		showCommentsLink.click();
		Thread.sleep(500);

		int hideCommentsLinks = browser.findElements(By.xpath("//a[contains(text(),'Hide Comments')]")).size();
		logger.log(Level.INFO, "# of hideCommentsLinks = " + hideCommentsLinks);
		assertTrue("# of hideCommentsLinks == 1", hideCommentsLinks == 1);
		logger.log(Level.INFO, "comments.isDisplayed() = " + comments.isDisplayed());
		assertTrue("comments textarea is displayed", comments.isDisplayed());
		tags = browser.findElements(By.xpath("//a[contains(text(),'Hide Comments')]/../../child::node()")).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("tag for Hide and tag for textarea are showing", tags == tagsWhileShowing);

		comments.sendKeys(testing123);
		phoneNumberField.click();
		Thread.sleep(500);
		hideCommentsLink.click();
		Thread.sleep(500);
		tags = browser.findElements(By.xpath("//a[contains(text(),'Show Comments')]/../../child::node()")).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("no textarea is showing", tags == tagsWhileHidden);
		logger.log(Level.INFO, "showCommentsLink.isDisplayed() = " + showCommentsLink.isDisplayed());
		showCommentsLink.click();
		Thread.sleep(500);
		logger.log(Level.INFO,
			"after hide and show comments.getAttribute('value') = " + comments.getAttribute("value"));
		assertTrue("comments are still there after hide and show", testing123.equals(comments.getAttribute("value")));

	}

	@Test
	@RunAsClient
	@InSequence(8000)
	public void dateValidation() throws Exception {

		int tags = 0;
		int tagsWhileValid = 0;
		String foo = "";

		// checks an invalid dateOfBirth
		try {
			dateOfBirthField.clear();
			logger.log(Level.INFO, "No exceptions occured when clearing the dateOfBirthField");
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("No exceptions occured when clearing the dateOfBirthField", false);
		}

		Thread.sleep(500);
		logger.log(Level.INFO, "Entering an invalid value for the date of birth ... 12/34/5678 ...");
		dateOfBirthField.sendKeys("12/34/5678");
		Thread.sleep(500);
		submitButton.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO, "dateOfBirthFieldError.isDisplayed() = " + dateOfBirthFieldError.isDisplayed());
		logger.log(Level.INFO, "dateOfBirthFieldError.getText() = " + dateOfBirthFieldError.getText());
		assertTrue("dateOfBirthFieldError should contain 'Invalid date format', but insteead contains '" +
			dateOfBirthFieldError.getText() + "'", dateOfBirthFieldError.getText().contains("Invalid date format"));
		tags = browser.findElements(By.xpath(dateValidationXpath)).size() - dateValidationXpathModifier;
		logger.log(Level.INFO, "tags = " + tags);
		logger.log(Level.INFO, "asserting: tags > tagsWhileValid? " + tags + " > " + tagsWhileValid + "? ...");
		assertTrue("There should be some kind of error message showing under the dateOfBirthField, " + "but " + tags +
			" messages are there", tags > tagsWhileValid);

		// checks with no dateOfBirth
		dateOfBirthField.clear();
		logger.log(Level.INFO, "clearing the dateOfBirthField and then clicking into the phoneNumberField ...");
		Thread.sleep(500);
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO, "dateOfBirthFieldError.isDisplayed() = " + dateOfBirthFieldError.isDisplayed());
		logger.log(Level.INFO, "dateOfBirthFieldError.getText() = " + dateOfBirthFieldError.getText());

		// Should I be this lenient?
		assertTrue("dateOfBirthField validation message should be displayed when no date is entered",
			dateOfBirthFieldError.getText().contains("Value is required") ||
			dateOfBirthFieldError.getText().contains("Invalid date format"));
		tags = browser.findElements(By.xpath(dateValidationXpath)).size() - dateValidationXpathModifier;
		logger.log(Level.INFO, "tags = " + tags);
		logger.log(Level.INFO, "asserting: tags > tagsWhileValid? " + tags + " > " + tagsWhileValid + "? ...");
		assertTrue("There should be some kind of error message showing under the dateOfBirthField, " + "but " + tags +
			" messages are there", tags > tagsWhileValid);

		// checks a valid dateOfBirth
		foo = "";
		dateOfBirthField.clear();
		Thread.sleep(500);
		logger.log(Level.INFO, "Entering a valid dateOfBirth = 01/02/3456 ...");
		dateOfBirthField.sendKeys("01/02/3456");
		Thread.sleep(500);
		logger.log(Level.INFO, "Clicking into the phoneNumberField ...");
		phoneNumberField.click();
		Thread.sleep(1000);
		logger.log(Level.INFO,
			"Now the dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		assertTrue("dateOfBirthField is currently showing 01/02/3456 ?",
			"01/02/3456".equals(dateOfBirthField.getAttribute("value")));
		tags = browser.findElements(By.xpath(dateValidationXpath)).size() - dateValidationXpathModifier;
		logger.log(Level.INFO, "tags = " + tags);

		if (tags > tagsWhileValid) {
			foo = dateOfBirthFieldError.getText();
		}

		assertTrue("There should be no dateOfBirth validation errors showing when a valid date has been submitted, " +
			"but '" + foo + "' is now showing there", tags == tagsWhileValid);

	}

	@Test
	@RunAsClient
	@InSequence(8500)
	public void fileUpload() throws Exception {

		boolean uploaded = false;

		if (isThere(fileUploadChooserXpath)) {
			logger.log(Level.INFO, "isThere(fileUploadChooserXpath) = " + isThere(fileUploadChooserXpath));
		}
		else {

			// As of the time of this comment, only the jsf2-jsp-portlet did not render a fileUploadChooser on the
			// front view
			logger.log(Level.INFO, "clicking the Add Attachment button ...");
			browser.findElement(By.xpath("//input[@type='submit' and @value='Add Attachment']")).click();
			Thread.sleep(500);
		}

		logger.log(Level.INFO, "entering in " + getPathToJerseyFile() + " for fileUploadChooser ...");
		fileUploadChooser.sendKeys(getPathToJerseyFile());

		Thread.sleep(50);
		logger.log(Level.INFO, "submitting the uploaded file ...");
		submitFile.click();

		if (isThere(uploadedFileXpath)) {
			logger.log(Level.INFO, "uploadedFile.getText() = " + uploadedFile.getText() + " was there immediately");
			uploaded = true;
		}
		else {
			Thread.sleep(1000);

			if (isThere(uploadedFileXpath)) {
				logger.log(Level.INFO,
					"uploadedFile.getText() = " + uploadedFile.getText() + " was there after 1 second");
				uploaded = true;
			}
			else {
				Thread.sleep(1000);

				if (isThere(uploadedFileXpath)) {
					logger.log(Level.INFO,
						"uploadedFile.getText() = " + uploadedFile.getText() + " was there after 2 seconds");
					uploaded = true;
				}
				else {
					Thread.sleep(1000);

					if (isThere(uploadedFileXpath)) {
						logger.log(Level.INFO,
							"uploadedFile.getText() = " + uploadedFile.getText() + " was there after 3 seconds");
						uploaded = true;
					}
					else {
						logger.log(Level.INFO, "uploadedFile was NOT there after 3 seconds");
					}
				}
			}
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

		logger.log(Level.INFO, "clearing fields ...");

		try {
			dateOfBirthField.clear();
			logger.log(Level.INFO, "No exceptions occured when clearing the dateOfBirthField");
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
		}

		emailAddressField.clear();
		postalCodeField.clear();

		int commentsTextAreas = browser.findElements(By.xpath("//textarea[contains(@id,':comments')]")).size();
		logger.log(Level.INFO, "# of commentsTextAreas = " + commentsTextAreas);

		if (commentsTextAreas == 0) { // if comments were not previously exercised, then we may need to show the
									  // comments text area.
			showCommentsLink.click();
			Thread.sleep(500);
			commentsTextAreas = browser.findElements(By.xpath("//textarea[contains(@id,':comments')]")).size();
			logger.log(Level.INFO, "# of commentsTextAreas = " + commentsTextAreas);
		}

		assertTrue("# of commentsTextAreas == 1", commentsTextAreas == 1);

		comments.clear();
		Thread.sleep(500);
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
		phoneNumberField.click();
		Thread.sleep(500);
		comments.sendKeys("If as one people speaking the same language, they have begun to do this ...");
		Thread.sleep(500);

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
			"asserting that comments.getText().equals('If as one people speaking the same language, they have begun to do this ...'), " +
			"but it is '" + comments.getAttribute("value") + "'",
			comments.getAttribute("value").equals(
				"If as one people speaking the same language, they have begun to do this ..."));

		submitButton.click();
		Thread.sleep(500);

		logger.log(Level.INFO, "formTag.getText() = " + formTag.getText());
		assertTrue("The text 'Dear David' should be showing in the portlet after submitting valid data, " +
			"but it is not", formTag.getText().contains("Dear David"));

	}

}
