package com.liferay.faces.test;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
//import org.jboss.arquillian.graphene.*;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	private final static Logger logger = Logger.getLogger(Jsf2PortletTest.class
			.getName());
	
	// //*[@id="portlet_1_WAR_jsf2loginportlet"]/header/h1/span[2]
	// @FindBy(xpath = "//*[@id='portlet_1_WAR_jsf2loginportlet']/header/h1/span[2]")
	@FindBy(xpath = "//section[@id='portlet_1_WAR_jsf2loginportlet']/header/h1/span[2]")
	private WebElement portletDisplayName;
	
	// //*[@id="A2677:j_idt3"]/ul/li
	// Authentication failed. Please try again.
	// //*[@id="A2677:j_idt4"]/ul/li
	// //ul/li[@class="portlet-msg-error"]
	// Authentication failed. Please try again.
	// //*[@id="A2677:j_idt4"]/ul/li
	// //form[@id='A2677:j_idt4']/ul/li
	// //*[@id="A2677"]
	// //*[@id="A2677:j_idt4"]/ul/li
	// @FindBy(xpath = "//form[@id='A2677:j_idt4']/ul/li")
	@FindBy(xpath = "//form[@method='post']/ul/li")
	private WebElement messageError;

	// @FindBy(id = "_58_login")
	// //*[@id="A2677:j_idt6:j_idt8:j_idt9:handle"]
	// //input[contains(@id,'buttonTargetNav2')]
	@FindBy(xpath = "//input[contains(@id,':handle')]")
	private WebElement emailField;

	// @FindBy(id = "_58_password")
	// //*[@id="A2677:j_idt6:j_idt8:j_idt9:password"]
	@FindBy(xpath = "//input[contains(@id,':password')]")
	private WebElement passwordField;

	// @FindBy(id = "j_login")
	// //*[@id="A2677:j_idt6:j_idt8"]/div/input
	// //*[@id="A2677:j_idt4:j_idt6"]/div/input
	// <input type="submit" name="A2677:j_idt4:j_idt6:j_idt9" value="Sign In">
	// //input[@type='submit' and @value='Sign In']
	@FindBy(xpath = "//input[@type='submit' and @value='Sign In']")
	private WebElement signInButton;

	// //*[@id="A2677"]
	// <div class="portlet-body" id="aui_3_4_0_1_490">
	//   <div id="A2677" class="liferay-faces-bridge-body">You are signed in as Test Test.</div> </div>
	// //section[@id='portlet_1_WAR_jsf2loginportlet']
	// //div[contains(text(),'You are signed in as')]
	// @FindBy(xpath = "//*[@id='A2677']")
	@FindBy(xpath = "//div[contains(text(),'You are signed in as')]")
	private WebElement portletBody;

	// By id = By.id("button");
	// WebElement element = driver.findElement(id);

	// @ArquillianResource
	// URL portalURL;

	@Drone
	WebDriver browser;

	@Before
	public void getNewSession() {
		browser.manage().deleteAllCookies();
		// Shut its dirty mouth
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
	}

	@Test
	@RunAsClient
	public void failToSignIn() throws Exception {

		// String url =
		// "http://localhost:8080/group/bridge-demos/jsf2?js_fast_load=0";
		// String url = "http://localhost:8080/group/bridge-demos/jsf2";
		String url = "http://localhost:8080/web/guest/signin";
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
		logger.log(Level.INFO, "emailField.getText() = " + emailField.getText());

		logger.log(Level.INFO, "passwordField.sendKeys ...");
		passwordField.sendKeys("not_test");
		logger.log(Level.INFO, "passwordField.getText() = " + passwordField.getText());

		logger.log(Level.INFO, "signInButton.click() ...");
		signInButton.click();

		// wait until the submit button is displayed
		logger.log(Level.INFO, "starting to wait ...");
		waitAjax(browser);
		// Thread.sleep(3000);
		// Graphene.waitModel(browser).until(Graphene.element(messageError).isPresent()).wait(5);
		// logger.log(Level.INFO, "browser.getPageSource() = " + browser.getPageSource());
		
		logger.log(Level.INFO,
				"messageError.getText() = " + messageError.getText());
		assertTrue("messageError is displayed", messageError.isDisplayed());
		assertTrue("Authentication failed",
				messageError.getText().contains("Authentication failed"));

	}

	@Test
	@RunAsClient
	public void signIn() throws Exception {

		String url = "http://localhost:8080/web/guest/signin";

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
		// Thread.sleep(3000);
		waitModel(browser);

		logger.log(Level.INFO,
				"portletBody.getText() = " + portletBody.getText());
		assertTrue("portletBody is displayed", portletBody.isDisplayed());
		assertTrue("You are signed in",
				portletBody.getText().contains("You are signed in"));
	}

}
