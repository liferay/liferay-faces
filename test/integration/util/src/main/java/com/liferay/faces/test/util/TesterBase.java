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
package com.liferay.faces.test.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * This class provides methods that may be used to help test portlets
 */
public class TesterBase {

	protected static final Logger logger = Logger.getLogger(TesterBase.class.getName());

	// elements for logging in
	private static final String emailFieldXpath = "//input[contains(@id,'_login')]";
	private static final String passwordFieldXpath = "//input[contains(@id,'_password')]";
	private static final String signInButtonXpath = "//input[@type='submit' and @value='Sign In']";
	private static final String signedInTextXpath = "//div[contains(text(),'You are signed in as')]";

	private static final String JERSEY_FILE = "liferay-jsf-jersey.png";

	@FindBy(xpath = emailFieldXpath)
	private WebElement emailField;
	@FindBy(xpath = passwordFieldXpath)
	private WebElement passwordField;
	@FindBy(xpath = signInButtonXpath)
	private WebElement signInButton;
	@FindBy(xpath = signedInTextXpath)
	private WebElement signedInText;

	static final String signInUrl = "http://localhost:8080/web/guest/home";

	@Drone
	public WebDriver browser;

	public void signIn() throws Exception {

		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);

		logger.log(Level.INFO, "browser.navigate().to(" + signInUrl + ")");
		browser.navigate().to(signInUrl);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " before signing in ...");

		emailField.clear();
		emailField.sendKeys("test@liferay.com");
		passwordField.clear();
		passwordField.sendKeys("test");
		signInButton.click();
		logger.log(Level.INFO,
			"browser.getTitle() = " + browser.getTitle() + " after clicking the sign in button and waiting");
		logger.log(Level.INFO, signedInText.getText());

	}

	public String getPathToJerseyFile() {
		String path = "/tmp/";

		String os = System.getProperty("os.name");

		if (os.indexOf("win") > -1) {
			path = "C:\\WINDOWS\\Temp\\";
		}

		return path + JERSEY_FILE;
	}

	public void waitForElement(String xpath) {
		WebDriverWait wait = new WebDriverWait(browser, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByXPath.xpath(xpath)));
	}

	public boolean isThere(String xpath) {
		boolean isThere = false;
		int count = 0;
		count = browser.findElements(By.xpath(xpath)).size();

		if (count == 0) {
			isThere = false;
		}

		if (count > 0) {
			isThere = true;
		}

		if (count > 1) {
			logger.log(Level.WARNING,
				"The method 'isThere(xpath)' found " + count + " matches using xpath = " + xpath +
				" ... the word 'is' implies singluar, or 1, not " + count);
		}

		return isThere;
	}

}
