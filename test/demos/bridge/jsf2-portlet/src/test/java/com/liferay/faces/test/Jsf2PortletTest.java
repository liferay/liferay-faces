package com.liferay.faces.test;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
//import org.jboss.arquillian.graphene.*;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;
//import java.net.URL;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.jboss.arquillian.graphene.Graphene.waitGui;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

@RunWith(Arquillian.class)
public class Jsf2PortletTest {
	
	// private static final Logger logger;
	private final static Logger logger = Logger.getLogger(Jsf2PortletTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String url = "http://localhost:8080/web/guest/signin";

	@Drone
	WebDriver browser;
	
	// elements for logging in
	@FindBy(xpath = "//input[contains(@id,':handle')]")
	private WebElement emailField;
	@FindBy(xpath = "//input[contains(@id,':password')]")
	private WebElement passwordField;
	@FindBy(xpath = "//input[@type='submit' and @value='Sign In']")
	private WebElement signInButton;
	@FindBy(xpath = "//div[contains(text(),'You are signed in as')]")
	private WebElement portletBody;

	// elements for Job Applicants
	@FindBy(xpath = "//header[@class='portlet-topper']/h1/span")
	private WebElement portletDisplayName;
	@FindBy(xpath = "//img[contains(@src,'liferay-logo.png')]")
	private WebElement logo;
	
	@FindBy(xpath = "//input[contains(@id,':firstName')]")
	private WebElement firstNameField;
	@FindBy(xpath = "//input[contains(@id,':lastName')]")
	private WebElement lastNameField;
	@FindBy(xpath = "//input[contains(@id,':emailAddress')]")
	private WebElement emailAddressField;
	@FindBy(xpath = "//input[contains(@id,':phoneNumber')]")
	private WebElement phoneNumberField;
	
	@FindBy(xpath = "//input[contains(@id,':dateOfBirth')]")
	private WebElement dateOfBirthField;
	@FindBy(xpath = "//input[contains(@id,':city')]")
	private WebElement cityField;
	@FindBy(xpath = "//select[contains(@id,':provinceId')]")
	private WebElement provinceIdField;
	@FindBy(xpath = "//input[contains(@id,':postalCode')]")
	private WebElement postalCodeField;
	// <img src="http://localhost:8080/group/bridge-demos/jsf2?p_p_id=1_WAR_jsf2portlet_INSTANCE_ABCD&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-1&amp;p_p_col_count=1&amp;_1_WAR_jsf2portlet_INSTANCE_ABCD_javax.faces.resource=icon-help.png&amp;_1_WAR_jsf2portlet_INSTANCE_ABCD_ln=example" title="Type any of these ZIP codes: 19806, 30329, 32801, 21224, 28202, 07030, 12205, 29201, 24013">
	// //*[@id="A5601:l1:c1:f1:fs1:c1b:postalCodeField"]/span/span/img
	// //img[contains(@title,'Type any of these ZIP codes')]
	@FindBy(xpath = "//img[contains(@title,'Type any of these ZIP codes')]")
	private WebElement postalCodeToolTip;
	
	// <input id="A5601:l1:c2:c2a:f3:j_idt45" name="A5601:l1:c2:c2a:f3:j_idt45" type="file" multiple="multiple">
	@FindBy(xpath = "//input[@type='file' and @multiple='multiple']")
	private WebElement multiFileUploadButton;
	// <input type="submit" name="A5601:l1:c2:c2a:f3:j_idt46" value="Submit" id="aui_3_4_0_1_571">
	@FindBy(xpath = "//form[@enctype='multipart/form-data']/input[@type='submit' and @value='Submit']")
	private WebElement submitFilesButton;
	
	@FindBy(xpath = "//a[contains(text(),'Show Comments')]")
	private WebElement showCommentsLink;
	
	// //input[@type='submit' and @value='Submit']
	@FindBy(xpath = "//input[@type='submit' and @value='Submit']")
	private WebElement submitButton;
	@FindBy(xpath = "//input[@type='submit' and @value='Edit Preferences']")
	private WebElement editPreferencesButton;
	
	// //ul/li/em[contains(text(),'Mojarra 2.1.18')]
	@FindBy(xpath = "//ul/li/em[contains(text(),'Mojarra')]")
	private WebElement mojarraVersion;
	// //ul/li/em[contains(text(),'Liferay Faces Alloy 3.1.2')]
	@FindBy(xpath = "//ul/li/em[contains(text(),'Liferay Faces Alloy')]")
	private WebElement alloyVersion;
	// //ul/li/em[contains(text(),'Liferay Faces Bridge 3.1.2')]
	@FindBy(xpath = "//ul/li/em[contains(text(),'Liferay Faces Bridge')]")
	private WebElement bridgeVersion;
	
	@Before
	public void getNewSession() {
		// Shut its dirty mouth
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
	}

	public void signIn() throws Exception {
		
		// browser.manage().deleteAllCookies();
		// logger.log(Level.INFO, "browser.manage().deleteAllCookies() ...");
		
		url = "http://localhost:8080/web/guest/signin";
		logger.log(Level.INFO, "Signing in using browser.navigate().to(url) ...");
		browser.navigate().to(url);
		waitModel(browser);
		Thread.sleep(1000);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " before signing in ...");
		
		emailField.clear();
		emailField.sendKeys("test@liferay.com");
		passwordField.clear();
		passwordField.sendKeys("test");
		signInButton.click();
		waitModel(browser);
		Thread.sleep(1000);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " after clicking the sign in button and waiting");
		logger.log(Level.INFO, portletBody.getText());
		// assertTrue("You are signed in", portletBody.getText().contains("You are signed in"));
	}
	
	@Test
	@RunAsClient
	@InSequence(1)
	public void createJobApplicant() throws Exception {
		
		signIn();
		
		url = "http://localhost:8080/group/bridge-demos/jsf2";
		logger.log(Level.INFO, "url = " + url);
		
		logger.log(Level.INFO, "second browser.navigate().to ...");
		browser.navigate().to("http://localhost:8080/group/bridge-demos/jsf2");
		waitModel(browser);
		Thread.sleep(2000);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		assertTrue("portletDisplayName.isDisplayed()",portletDisplayName.isDisplayed());
		
		// logger.log(Level.INFO, "browser.getPageSource() = " + browser.getPageSource());
		
		assertTrue("logo.isDisplayed()",logo.isDisplayed());
		
		assertTrue("firstNameField.isDisplayed()",firstNameField.isDisplayed());
		assertTrue("lastNameField.isDisplayed()",lastNameField.isDisplayed());
		assertTrue("emailAddressField.isDisplayed()",emailAddressField.isDisplayed());
		assertTrue("phoneNumberField.isDisplayed()",phoneNumberField.isDisplayed());
		
		assertTrue("dateOfBirthField.isDisplayed()",dateOfBirthField.isDisplayed());
		assertTrue("cityField.isDisplayed()",cityField.isDisplayed());
		assertTrue("provinceIdField.isDisplayed()",provinceIdField.isDisplayed());
		assertTrue("postalCodeField.isDisplayed()",postalCodeField.isDisplayed());
		assertTrue("postalCodeToolTip.isDisplayed()",postalCodeToolTip.isDisplayed());
		
		assertTrue("multiFileUploadButton.isDisplayed()",multiFileUploadButton.isDisplayed());
		assertTrue("submitFilesButton.isDisplayed()",submitFilesButton.isDisplayed());
		
		assertTrue("showCommentsLink.isDisplayed()",showCommentsLink.isDisplayed());
		
		assertTrue("submitButton.isDisplayed()",submitButton.isDisplayed());
		assertTrue("editPreferencesButton.isDisplayed()",editPreferencesButton.isDisplayed());
		
		assertTrue("mojarraVersion.isDisplayed()",mojarraVersion.isDisplayed());
		assertTrue("alloyVersion.isDisplayed()",alloyVersion.isDisplayed());
		assertTrue("bridgeVersion.isDisplayed()",bridgeVersion.isDisplayed());

		logger.log(Level.INFO, mojarraVersion.getText());
		logger.log(Level.INFO, alloyVersion.getText());
		logger.log(Level.INFO, bridgeVersion.getText());
		
		// signInButton.click();
		// waitModel(browser);
		
	}

}
