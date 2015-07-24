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
package com.liferay.faces.test.util;
//J-

import java.util.logging.Level;
import java.util.logging.Logger;

// import org.jboss.arquillian.drone.api.annotation.Drone;
// import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * This class provides methods that may be used to help test portlets
 */
public class TesterBase {

	protected static final Logger logger = Logger.getLogger(TesterBase.class.getName());

	// elements for logging into liferay
	private static final String emailFieldXpath = "//input[contains(@id,'_58_login')]";
	private static final String passwordFieldXpath = "//input[contains(@id,'_58_password')]";
	private static final String signInButtonXpath = "//input[@type='submit' and @value='Sign In']";
	private static final String signedInTextXpath = "//div[contains(text(),'You are signed in as')]";

	@FindBy(xpath = emailFieldXpath)
	private WebElement emailField;
	@FindBy(xpath = passwordFieldXpath)
	private WebElement passwordField;
	@FindBy(xpath = signInButtonXpath)
	private WebElement signInButton;
	@FindBy(xpath = signedInTextXpath)
	private WebElement signedInText;

	// elements for logging into pluto
	private static final String userNameXpath = "//input[contains(@id,'j_username')]";
	private static final String passwordXpath = "//input[contains(@id,'j_password')]";
	private static final String loginButtonXpath = "//input[@type='submit' and @value='Login']";
	private static final String logoutXpath = "//a[contains(text(),'Logout')]";

	@FindBy(xpath = userNameXpath)
	private WebElement userName;
	@FindBy(xpath = passwordXpath)
	private WebElement password;
	@FindBy(xpath = loginButtonXpath)
	private WebElement loginButton;
	@FindBy(xpath = logoutXpath)
	private WebElement logout;

	// portlet name for liferay
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";
	@FindBy(xpath = portletDisplayNameXpath)
	protected WebElement portletDisplayName;

	// portlet name element for pluto
	private static final String plutoPortletDisplayNameXpath = "//td[@class='header']/h2";
	@FindBy(xpath = plutoPortletDisplayNameXpath)
	protected WebElement plutoPortletDisplayName;

	protected String displayNameXpath;
	protected WebElement displayName;

	// elements for switching to edit mode in liferay
	private static final String menuButtonXpath = "//*[contains(text(),'Options')]/..";
	private static final String menuPreferencesXpath = "//img[contains(@src,'/edit.png')]";

	@FindBy(xpath = menuButtonXpath)
	private WebElement menuButton;
	@FindBy(xpath = menuPreferencesXpath)
	private WebElement menuPreferences;

	// xpath for switching to edit mode in pluto
	private static final String plutoMenuButtonXpath = "//form[@name='modeSelectionForm']/select";

	private static final String JERSEY_FILE = "liferay-jsf-jersey.png";

	public static final String portal = System.getProperty("integration.portal", "liferay");
	public static final String baseUrl = System.getProperty("integration.url", "http://localhost:8080");
	public static final String signInContext = System.getProperty("integration.signin", "/web/guest/home");
	public static final String webContext = System.getProperty("integration.context", "/web/bridge-demos/");
	protected static final String signInUrl = baseUrl + signInContext;

	// @Drone
	// public WebDriver browser;

	public void signIn(WebDriver browser) throws Exception {
		logger.log(Level.INFO, "portal = " + portal);
		if ("liferay".equals(portal)) {
			signIn(browser, emailField, passwordField, signInButton, signedInText, "test@liferay.com", "test");
		} else if ("pluto".equals(portal)) {
			signIn(browser, userName, password, loginButton, logout, "pluto", "pluto");
		} else {
			logger.log(Level.SEVERE, "not a supported portal for this tester base: portal = " + portal + "");
		}
	}

	public void signIn(WebDriver browser, WebElement user, WebElement pass, WebElement button, WebElement text, String u, String p) throws Exception {

		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);

		logger.log(Level.INFO, "browser.navigate().to(" + signInUrl + ")");
		browser.navigate().to(signInUrl);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " before signing in ...");

		if (browser.getTitle().contains("Status")) {
			logger.log(Level.INFO, "welcome-theme installed ...");
			String welcomeSignInUrl = signInUrl.replace("home", "welcome");
			logger.log(Level.INFO, "browser.navigate().to(" + welcomeSignInUrl + ")");
			browser.navigate().to(welcomeSignInUrl);
			logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " before signing in ...");
		} else {
			logger.log(Level.INFO, "no welcome-theme, no problem ...");
		}

		if (isThere(browser, "//div[contains(text()[2],'was not found')]")) {

			// attempt to go to a Bridge Demos to get to the login page
			logger.log(Level.INFO, "Attempting to go to a Bridge Demos to get to the login page ...");
			String bridgeDemosSignInUrl = baseUrl + "/group/bridge-demos/jsf2";
			logger.log(Level.INFO, "browser.navigate().to(" + bridgeDemosSignInUrl + ")");
			browser.navigate().to(bridgeDemosSignInUrl);
			logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " before signing in ...");

			waitForElement(browser, emailFieldXpath);
			user.clear();
			user.sendKeys(u);
			pass.clear();
			pass.sendKeys(p);
			button.click();

			waitForElement(browser, "//span[contains(text(),'Bridge Demos')]");

			return;
		}

		user.clear();
		user.sendKeys(u);
		pass.clear();
		pass.sendKeys(p);
		button.click();
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " after clicking the sign in button.	Now waiting ...");
		waitForElement(browser, signedInTextXpath);
		logger.log(Level.INFO, text.getText());

	}

	public void selectEditMode(WebDriver browser, String portal) {
		logger.log(Level.INFO, "portal = " + portal);
		if (portal.equals("liferay")) {
			selectEditModeInLiferay();
		} else if ("pluto".equals(portal)) {
			selectEditModeInPluto(browser);
		}
	}

	public void selectEditModeInPluto(WebDriver browser) {
		Select select = new Select(browser.findElement(By.xpath(plutoMenuButtonXpath)));
		// select.deselectAll();  // You may only deselect all options of a multi-select
		select.selectByVisibleText("EDIT");
	}

	public void selectEditModeInLiferay() {
		if (menuPreferences.isDisplayed()) {
			logger.log(Level.INFO, "menuPreferences is already displayed ... why is that? ");
		} else {
			menuButton.click();
		}
		logger.log(Level.INFO, "menuPreferences.click() ... ");
		if (menuPreferences.isDisplayed()) {
			menuPreferences.click();
		} else {
			logger.log(Level.INFO, "menuPreferences should be displayed at this point, but it is not ... ");
		}
	}

	// in order for displayName not to be null
	// this method must be called after the tester is instantiated (and we are on the right page, of course)
	// if you try to declare and initialize displayName earlier or when this class is instantiated,
	// it will result in some errors with no messages.
	// Also if you try to access the displayName before calling this method it will result in messageless errors.
	// i think it is a NullPointerException, but again, it does not appear in the logs so who knows?
	public WebElement getPortletDisplayName() {
		this.displayNameXpath = ("liferay".equals(portal)) ? portletDisplayNameXpath : plutoPortletDisplayNameXpath;
		this.displayName = ("liferay".equals(portal)) ? portletDisplayName : plutoPortletDisplayName;
		return displayName;
	}

	public String getPathToJerseyFile() {
		String path = "/tmp/";

		String os = System.getProperty("os.name");

		if (os.indexOf("win") > -1) {
			path = "C:\\WINDOWS\\Temp\\";
		}

		return path + JERSEY_FILE;
	}

	public void waitForElement(WebDriver browser, String xpath) {
		WebDriverWait wait = new WebDriverWait(browser, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByXPath.xpath(xpath)));
	}

	public boolean isThere(WebDriver browser, String xpath) {
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
//J+
