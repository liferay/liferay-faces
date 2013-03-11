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
public class Icefaces3UsersPortletTest {
	
	// private static final Logger logger;
	private final static Logger logger = Logger.getLogger(Icefaces3UsersPortletTest.class.getName());
	
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

	// elements for Icefaces3Users
	// <span class="portlet-title-text">ICEfaces3 Users</span>
	@FindBy(xpath = "//span[@class='portlet-title-text']")
	private WebElement portletDisplayName;
	
	@FindBy(xpath = "//a[contains(@id,'jsf2portlet') and contains(@id,'menuButton')]")
	private WebElement menuButton;
	@FindBy(xpath = "//a[contains(@id,'jsf2portlet') and contains(@id,'menu_preferences')]")
	private WebElement menuPreferences;
	
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
	@FindBy(xpath = "//img[contains(@title,'Type any of these ZIP codes')]")
	private WebElement postalCodeToolTip;
	
	@FindBy(xpath = "//input[@type='file' and @multiple='multiple']")
	private WebElement multiFileUploadButton;
	@FindBy(xpath = "//form[@enctype='multipart/form-data']/input[@type='submit' and @value='Submit']")
	private WebElement submitFilesButton;
	
	@FindBy(xpath = "//a[contains(text(),'Show Comments')]")
	private WebElement showCommentsLink;
	
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
		browser.manage().deleteAllCookies();
		logger.log(Level.INFO, "browser.manage().deleteAllCookies() ...");
	}

	public void signIn() throws Exception {
		
		url = "http://localhost:8080/web/guest/signin";
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		// waitModel(browser);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " before signing in ...");
		
		emailField.clear();
		emailField.sendKeys("test@liferay.com");
		passwordField.clear();
		passwordField.sendKeys("test");
		signInButton.click();
		// waitModel(browser);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " after clicking the sign in button and waiting");
		logger.log(Level.INFO, portletBody.getText());
		// assertTrue("You are signed in", portletBody.getText().contains("You are signed in"));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1)
	public void Icefaces3Users() throws Exception {
			
		signIn();
		url = "http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_icefaces3usersportlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view";
		// http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_icefaces3usersportlet
		// http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_icefaces3usersportlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view
		
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		
		// org.openqa.selenium.WebDriverException: 
		// com.gargoylesoftware.htmlunit.ScriptException: 
		// TypeError: Cannot find function removeAttribute in object [object CSSStyleDeclaration]. 
		// (http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_icefaces3usersportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&p_p_col_id=&p_p_col_count=0&_1_WAR_icefaces3usersportlet_javax.faces.resource=util%2Face-jquery.js&_1_WAR_icefaces3usersportlet_ln=icefaces.ace#23)
		// http://bugs.jquery.com/ticket/10394
		// ??? fix ???
		// https://github.com/jquery/jquery/commit/07e50933c4293818c5b36d368368656844e4df88
		// http://bugs.jquery.com/query?status=closed&group=resolution&milestone=1.8
		// Looks like it might have been resolved in 1.8
		
		// waitModel(browser);
//		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
//		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
//		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
//		
//		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
//		assertTrue("menuButton.isDisplayed()", menuButton.isDisplayed());
//		assertTrue("menuPreferences is NOT displayed()", !menuPreferences.isDisplayed());
//		
//		// logger.log(Level.INFO, "browser.getPageSource() = " + browser.getPageSource());
//		
//		assertTrue("logo.isDisplayed()",logo.isDisplayed());
//		
//		assertTrue("firstNameField.isDisplayed()", firstNameField.isDisplayed());
//		assertTrue("lastNameField.isDisplayed()", lastNameField.isDisplayed());
//		assertTrue("emailAddressField.isDisplayed()", emailAddressField.isDisplayed());
//		assertTrue("phoneNumberField.isDisplayed()", phoneNumberField.isDisplayed());
//		
//		assertTrue("dateOfBirthField.isDisplayed()", dateOfBirthField.isDisplayed());
//		assertTrue("cityField.isDisplayed()", cityField.isDisplayed());
//		assertTrue("provinceIdField.isDisplayed()", provinceIdField.isDisplayed());
//		assertTrue("postalCodeField.isDisplayed()", postalCodeField.isDisplayed());
//		assertTrue("postalCodeToolTip.isDisplayed()", postalCodeToolTip.isDisplayed());
//		
//		assertTrue("multiFileUploadButton.isDisplayed()", multiFileUploadButton.isDisplayed());
//		assertTrue("submitFilesButton.isDisplayed()", submitFilesButton.isDisplayed());
//		
//		assertTrue("showCommentsLink.isDisplayed()", showCommentsLink.isDisplayed());
//		
//		assertTrue("submitButton.isDisplayed()", submitButton.isDisplayed());
//		assertTrue("editPreferencesButton.isDisplayed()", editPreferencesButton.isDisplayed());
//		
//		assertTrue("mojarraVersion.isDisplayed()", mojarraVersion.isDisplayed());
//		assertTrue("alloyVersion.isDisplayed()", alloyVersion.isDisplayed());
//		assertTrue("bridgeVersion.isDisplayed()", bridgeVersion.isDisplayed());
//
//		logger.log(Level.INFO, mojarraVersion.getText());
//		logger.log(Level.INFO, alloyVersion.getText());
//		logger.log(Level.INFO, bridgeVersion.getText());
		
		// signInButton.click();
		// waitModel(browser);
		
	}

}
