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

import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.liferay.faces.test.util.TesterBase;


/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class Jsf2LoginPortletTest extends TesterBase {

	private static final Logger logger = Logger.getLogger(Jsf2LoginPortletTest.class.getName());

	@FindBy(xpath = "//div[@class='portlet-topper']/span")
	private WebElement portletDisplayName;

	@FindBy(xpath = "//form[@method='post']/ul/li")
	private WebElement messageError;

	@FindBy(xpath = "//input[contains(@id,':handle')]")
	private WebElement emailField;

	@FindBy(xpath = "//input[contains(@id,':password')]")
	private WebElement passwordField;

	@FindBy(xpath = "//input[@type='submit' and @value='Sign In']")
	private WebElement signInButton;

	@FindBy(xpath = "//div[contains(text(),'You are signed in as')]")
	private WebElement portletBody;
	
	static final String url = baseUrl + "/web/guest/jsf2-sign-in";
	
	@Drone
	WebDriver browser;

	@Before
	public void getNewSession() {
		browser.manage().deleteAllCookies();
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
	}

	@Test
	@RunAsClient
	@InSequence(1)
	public void failToSignIn() throws Exception {
		
		logger.log(Level.INFO, "url = " + url);

		browser.get(url);

		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue("portlet is called JSF2 Sign-In", portletDisplayName.getText().contains("JSF2 Sign-In"));

		logger.log(Level.INFO, "emailField.isDisplayed() = " + emailField.isDisplayed());
		assertTrue("emailField.isDisplayed()", emailField.isDisplayed());
		logger.log(Level.INFO, "passwordField.isDisplayed() = " + passwordField.isDisplayed());
		assertTrue("passwordField.isDisplayed()", passwordField.isDisplayed());
		logger.log(Level.INFO, "signInButton.isDisplayed() = " + signInButton.isDisplayed());
		assertTrue("signInButton.isDisplayed()", signInButton.isDisplayed());

		logger.log(Level.INFO, "clearing email textbox");
		emailField.clear();
		logger.log(Level.INFO, "emailField.sendKeys ...");
		emailField.sendKeys("test@liferay.com");

		logger.log(Level.INFO, "passwordField.sendKeys ...");
		passwordField.sendKeys("not_test");

		logger.log(Level.INFO, "signInButton.click() ...");
		signInButton.click();

		logger.log(Level.INFO, "starting to wait ...");

		logger.log(Level.INFO, "messageError.getText() = " + messageError.getText());
		assertTrue("messageError is displayed", messageError.isDisplayed());
		assertTrue("Authentication failed", messageError.getText().contains("Authentication failed"));

	}

	@Test
	@RunAsClient
	@InSequence(2)
	public void signIn() throws Exception {

		logger.log(Level.INFO, "url = " + url);
		browser.get(url);

		assertTrue("emailField.isDisplayed()", emailField.isDisplayed());
		assertTrue("passwordField.isDisplayed()", passwordField.isDisplayed());
		assertTrue("signInButton.isDisplayed()", signInButton.isDisplayed());

		logger.log(Level.INFO, "clearing emailField");
		emailField.clear();
		logger.log(Level.INFO, "emailField.sendKeys ...");
		emailField.sendKeys("test@liferay.com");
		logger.log(Level.INFO, "clearing passwordField");
		passwordField.clear();
		logger.log(Level.INFO, "passwordField.sendKeys ...");
		passwordField.sendKeys("test");

		logger.log(Level.INFO, "signInButton.click() ...");
		signInButton.click();
		logger.log(Level.INFO, "starting to wait ...");

		logger.log(Level.INFO, "portletBody.getText() = " + portletBody.getText());
		assertTrue("portletBody is displayed", portletBody.isDisplayed());
		assertTrue("You are signed in", portletBody.getText().contains("You are signed in"));
	}

}
