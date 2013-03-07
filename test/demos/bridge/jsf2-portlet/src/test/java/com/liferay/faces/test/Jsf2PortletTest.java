package com.liferay.faces.test;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
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
	private final static Logger logger = Logger
			.getLogger(Jsf2PortletTest.class.getName());
	
	@FindBy(id = "_58_login")
	private WebElement emailField;
	
	@FindBy(id = "_58_password")
	private WebElement passwordField;
	
	@FindBy(id = "j_login")
	private WebElement signInButton;
	
	@FindBy(xpath = "//input[@type='submit']")
	private WebElement submitButton;
	
	@FindBy(xpath = "//input[@type='submit']")
	private WebElement suButton;
	
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
	public void signIn() throws Exception {
		
		browser.get("http://localhost:8080/group/bridge-demos/jsf2");
		
		assertTrue("emailField is displayed", emailField.isDisplayed());
		assertTrue("passwordField is displayed", passwordField.isDisplayed());
		assertTrue("signInButton is displayed", signInButton.isDisplayed());
		
		logger.log(Level.INFO, "browser.getPageSource() = " + browser.getPageSource());
		
		logger.log(Level.INFO, "clearing email textbox");
		emailField.clear();
		logger.log(Level.INFO, "emailField.sendKeys ...");
		emailField.sendKeys("test@liferay.com");
		
		logger.log(Level.INFO, "passwordField.sendKeys ...");
//		passwordField.sendKeys("test");
//		
//		logger.log(Level.INFO, "signInButton.click() ...");
//		signInButton.click();
//		
//		// wait until the submit button is displayed
//		logger.log(Level.INFO, "starting to wait ...");
		
//		Graphene.waitModel(browser).until(Graphene.element(submitButton).isPresent()).wait(10);
//		logger.log(Level.INFO, "done waiting ...");
//		logger.log(Level.INFO, "browser.getPageSource() = " + browser.getPageSource());
		
	}

//	@Test
//	@RunAsClient
//	public void renderFacesPortlet() throws Exception {
//		// browser.get(portalURL.toString());
//		// browser.get("http://localhost:8080/");
//		browser.get("http://10.1.1.220:8080/group/bridge-demos/jsf2");
//
//		String src = browser.getPageSource();
//		logger.log(Level.INFO, "src = " + src);
//
//		assertTrue("Check that page contains output element",
//				outputField.isDisplayed());
//		// assertEquals("Field has correct value set", "Submit",
//		// outputField.getText());
//		assertEquals("Field has correct value set", "Submit",
//				outputField.getAttribute("value"));
//	}

}
