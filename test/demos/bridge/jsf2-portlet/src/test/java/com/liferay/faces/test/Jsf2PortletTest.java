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
//import static org.jboss.arquillian.graphene.Graphene.waitGui;
//import static org.jboss.arquillian.graphene.Graphene.waitModel;

@RunWith(Arquillian.class)
public class Jsf2PortletTest {

	// private static final Logger logger;
	private final static Logger logger = Logger.getLogger(Jsf2PortletTest.class
			.getName());

	// //*[@id="A2677:j_idt3"]/ul/li
	@FindBy(xpath = "//*[@id='A2677:j_idt3']/ul/li")
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
	@FindBy(xpath = "//input[contains(@name,':j_idt8')]")
	private WebElement signInButton;

	// //*[@id="A2677"]
	@FindBy(xpath = "//*[@id='A2677']")
	private WebElement portletBody;

	// @FindBy(xpath = "//input[@type='submit']")
	// private WebElement submitButton;
	//
	// @FindBy(xpath = "//input[@type='submit']")
	// private WebElement suButton;

	// @FindBy(id = "output")
	// @FindBy(id = "A5601:l1:c1:f1:j_idt30")
	// id="A5601:l1:c1:f1:j_idt30"

	// By id = By.id("button");
	// WebElement element = driver.findElement(id);

	// @ArquillianResource
	// URL portalURL;

	@Drone
	WebDriver browser;

	@Before
	public void getNewSession() {
		browser.manage().deleteAllCookies();
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

		assertTrue("emailField is displayed", emailField.isDisplayed());
		assertTrue("passwordField is displayed", passwordField.isDisplayed());
		assertTrue("signInButton is displayed", signInButton.isDisplayed());

		logger.log(Level.INFO, "clearing email textbox");
		emailField.clear();
		logger.log(Level.INFO, "emailField.sendKeys ...");
		emailField.sendKeys("test@liferay.com");

		logger.log(Level.INFO, "passwordField.sendKeys ...");
		passwordField.sendKeys("not_test");

		logger.log(Level.INFO, "signInButton.click() ...");
		signInButton.click();

		// wait until the submit button is displayed
		logger.log(Level.INFO, "starting to wait ...");
		Thread.sleep(1000);
		// Graphene.waitModel(browser).until(Graphene.element(messageError).isPresent()).wait(5);
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

		assertTrue("emailField is displayed", emailField.isDisplayed());
		assertTrue("passwordField is displayed", passwordField.isDisplayed());
		assertTrue("signInButton is displayed", signInButton.isDisplayed());

		logger.log(Level.INFO, "clearing email textbox");
		emailField.clear();
		logger.log(Level.INFO, "emailField.sendKeys ...");
		emailField.sendKeys("test@liferay.com");
		
		passwordField.sendKeys("test");
		signInButton.click();
		logger.log(Level.INFO, "starting to wait ...");
		Thread.sleep(1000);

		logger.log(Level.INFO,
				"portletBody.getText() = " + portletBody.getText());
		assertTrue("portletBody is displayed", portletBody.isDisplayed());
		assertTrue("Authentication passed",
				portletBody.getText().contains("You are signed in"));

		// logger.log(Level.INFO, "browser.getPageSource() = " +
		// browser.getPageSource());
	}

	// @Test
	// @RunAsClient
	// public void renderFacesPortlet() throws Exception {
	// // browser.get(portalURL.toString());
	// // browser.get("http://localhost:8080/");
	// browser.get("http://10.1.1.220:8080/group/bridge-demos/jsf2");
	//
	// String src = browser.getPageSource();
	// logger.log(Level.INFO, "src = " + src);
	//
	// assertTrue("Check that page contains output element",
	// outputField.isDisplayed());
	// // assertEquals("Field has correct value set", "Submit",
	// // outputField.getText());
	// assertEquals("Field has correct value set", "Submit",
	// outputField.getAttribute("value"));
	// }

}
