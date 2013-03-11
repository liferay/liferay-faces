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
public class Icefaces3PortletTest {
	
	// private static final Logger logger;
	private final static Logger logger = Logger.getLogger(Icefaces3PortletTest.class.getName());
	
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
	@FindBy(xpath = "//a[contains(@id,'icefaces3portlet') and contains(@id,'menuButton')]")
	private WebElement menuButton;
	@FindBy(xpath = "//a[contains(@id,'icefaces3portlet') and contains(@id,'menu_preferences')]")
	private WebElement menuPreferences;
	
	@FindBy(xpath = "//img[contains(@src,'liferay-logo.png')]")
	private WebElement logo;
	
	@FindBy(xpath = "//input[contains(@name,':firstName_input')]")
	private WebElement firstNameField;
	@FindBy(xpath = "//input[contains(@name,':lastName_input')]")
	private WebElement lastNameField;
	@FindBy(xpath = "//input[contains(@name,':emailAddress_input')]")
	private WebElement emailAddressField;
	@FindBy(xpath = "//input[contains(@name,':phoneNumber_input')]")
	private WebElement phoneNumberField;
	
	@FindBy(xpath = "//input[contains(@name,':dateOfBirth_input')]")
	private WebElement dateOfBirthField;
	@FindBy(xpath = "//input[contains(@name,':city_input')]")
	private WebElement cityField;
	@FindBy(xpath = "//select[contains(@id,':provinceId')]")
	private WebElement provinceIdField;
	@FindBy(xpath = "//input[contains(@name,':postalCode_input')]")
	private WebElement postalCodeField;
	@FindBy(xpath = "//img[contains(@title,'Type any of these ZIP codes')]")
	private WebElement postalCodeToolTip;
	
	// //input[@type='file']
	@FindBy(xpath = "//input[@type='file']")
	private WebElement fileUploadButton;
	// <input id="A9748:l1:c2:f1:c2a:_t40" name="A9748:l1:c2:f1:c2a:_t40" type="submit" value="Add Attachment" style="">
	@FindBy(xpath = "//input[@type='submit' and @value='Add Attachment']")
	private WebElement submitFileButton;
	
	@FindBy(xpath = "//a[contains(text(),'Show Comments')]")
	private WebElement showCommentsLink;
	
	// @FindBy(xpath = "//input[@type='submit' and @value='Submit']")
	@FindBy(xpath = "//input[@type='submit' and @value='Submit']")
	private WebElement submitButton;
	
	// //ul/li/em/span[contains(text(),'Mojarra 2.1.18')]
	@FindBy(xpath = "//ul/li/em/span[contains(text(),'Mojarra')]")
	private WebElement mojarraVersion;
	// <span id="A9748:j_idt42:j_idt43:_t47">ICEfaces 3.1.0</span>
	// //ul/li/em/span[contains(text(),'ICEfaces 3.1.0')]
	@FindBy(xpath = "//ul/li/em/span[contains(text(),'ICEfaces')]")
	private WebElement iceFacesVersion;
	// //ul/li/em/span[contains(text(),'Liferay Faces Alloy 3.1.2')]
	@FindBy(xpath = "//ul/li/em/span[contains(text(),'Liferay Faces Alloy')]")
	private WebElement alloyVersion;
	// //ul/li/em/span[contains(text(),'Liferay Faces Bridge 3.1.2')]
	@FindBy(xpath = "//ul/li/em/span[contains(text(),'Liferay Faces Bridge')]")
	private WebElement bridgeVersion;
	
	@Before
	public void getNewSession() {
		// Shut its dirty mouth
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		browser.manage().deleteAllCookies();
		logger.log(Level.INFO, "browser.manage().deleteAllCookies() ...");
	}

	public void signIn() throws Exception {
		
		url = "http://localhost:8080/web/guest/signin";
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
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
		Thread.sleep(1000);
	}
	
	@Test
	@RunAsClient
	@InSequence(1)
	public void jobApplicant() throws Exception {
		
		// You think i like this?  You think I like magic numbers?
		int foo = 0;
		while (!browser.getTitle().contains("ICE3") && foo < 10) {
			
			signIn();
			url = "http://localhost:8080/group/bridge-demos/ice3";
			logger.log(Level.INFO, "browser.navigate().to("+url+")");
			browser.navigate().to(url);
			waitModel(browser);
			Thread.sleep(2000);
			logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
			logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
			logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
			
		foo++;
		}
		logger.log(Level.INFO, "A measure of how much foo we have at this point ... foo = " + foo);
		
		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue("menuButton.isDisplayed()", menuButton.isDisplayed());
		assertTrue("menuPreferences is NOT displayed()", !menuPreferences.isDisplayed());
		
		// logger.log(Level.INFO, "browser.getPageSource() = " + browser.getPageSource());
		
		assertTrue("logo.isDisplayed()",logo.isDisplayed());
		
		assertTrue("firstNameField.isDisplayed()", firstNameField.isDisplayed());
		assertTrue("lastNameField.isDisplayed()", lastNameField.isDisplayed());
		assertTrue("emailAddressField.isDisplayed()", emailAddressField.isDisplayed());
		assertTrue("phoneNumberField.isDisplayed()", phoneNumberField.isDisplayed());
		
		assertTrue("dateOfBirthField.isDisplayed()", dateOfBirthField.isDisplayed());
		assertTrue("cityField.isDisplayed()", cityField.isDisplayed());
		assertTrue("provinceIdField.isDisplayed()", provinceIdField.isDisplayed());
		assertTrue("postalCodeField.isDisplayed()", postalCodeField.isDisplayed());
		assertTrue("postalCodeToolTip.isDisplayed()", postalCodeToolTip.isDisplayed());
		
		assertTrue("fileUploadButton.isDisplayed()", fileUploadButton.isDisplayed());
		assertTrue("submitFileButton.isDisplayed()", submitFileButton.isDisplayed());
		logger.log(Level.INFO, "submitFileButton.getTagName() = " + submitFileButton.getTagName());
		assertTrue("showCommentsLink.isDisplayed()", showCommentsLink.isDisplayed());
		
		assertTrue("submitButton.isDisplayed()", submitButton.isDisplayed());
		logger.log(Level.INFO, "submitButton.getTagName() = " + submitButton.getTagName());
		
		assertTrue("mojarraVersion.isDisplayed()", mojarraVersion.isDisplayed());
		assertTrue("alloyVersion.isDisplayed()", iceFacesVersion.isDisplayed());
		assertTrue("alloyVersion.isDisplayed()", alloyVersion.isDisplayed());
		assertTrue("bridgeVersion.isDisplayed()", bridgeVersion.isDisplayed());

		logger.log(Level.INFO, mojarraVersion.getText());
		logger.log(Level.INFO, iceFacesVersion.getText());
		logger.log(Level.INFO, alloyVersion.getText());
		logger.log(Level.INFO, bridgeVersion.getText());
		
		// signInButton.click();
		// waitModel(browser);
		
	}

}
