package com.liferay.faces.test;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import org.apache.commons.io.FileUtils;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;
// import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.jboss.arquillian.graphene.Graphene.waitGui;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

@RunWith(Arquillian.class)
public class Jsf2EventsTest {
	
	private final static Logger logger = Logger.getLogger(Jsf2EventsTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/jsf2-sign-in";
	String url = "http://localhost:8080/group/bridge-demos/jsf2-events";

	@Drone
	WebDriver browser;
	
	// elements for logging in
	private static final String emailFieldXpath = "//input[contains(@id,':handle')]";
	@FindBy(xpath = emailFieldXpath)
	private WebElement emailField;
	private static final String passwordFieldXpath = "//input[contains(@id,':password')]";
	@FindBy(xpath = passwordFieldXpath)
	private WebElement passwordField;
	private static final String signInButtonXpath = "//input[@type='submit' and @value='Sign In']";
	@FindBy(xpath = signInButtonXpath)
	private WebElement signInButton;
	private static final String signedInTextXpath = "//div[contains(text(),'You are signed in as')]";
	@FindBy(xpath = signedInTextXpath)
	private WebElement signedInText;
	
	// form tag found after submitting
	private static final String formTagXpath = "//form[@method='post']";
	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	
	// portlet topper for customer
	private static final String customerPortletDisplayNameXpath = "(//header[@class='portlet-topper']/h1/span)[1]";
	@FindBy(xpath = customerPortletDisplayNameXpath)
	private WebElement customerPortletDisplayName;
	
	private static final String briansInputXpath = "//input[@type='image']/../following-sibling::td[1][contains(text(),'1')]/../td[1]/input";
	@FindBy(xpath = briansInputXpath)
	private WebElement briansInput;
	
	private static final String briansFirstNameXpath = "//input[@type='image']/../following-sibling::td[1][contains(text(),'1')]/following-sibling::*[1]";
	@FindBy(xpath = briansFirstNameXpath)
	private WebElement briansFirstName;
	
	private static final String briansLastNameXpath = "//input[@type='image']/../following-sibling::td[1][contains(text(),'1')]/following-sibling::*[1]/following-sibling::*[1]";
	@FindBy(xpath = briansLastNameXpath)
	private WebElement briansLastName;
	
	private static final String lizsInputXpath = "//input[@type='image']/../following-sibling::td[1][contains(text(),'2')]/../td[1]/input";
	@FindBy(xpath = lizsInputXpath)
	private WebElement lizsInput;
	
	private static final String lizsFirstNameXpath = "//input[@type='image']/../following-sibling::td[1][contains(text(),'2')]/following-sibling::*[1]";
	@FindBy(xpath = lizsFirstNameXpath)
	private WebElement lizsFirstName;
	
	private static final String lizsLastNameXpath = "//input[@type='image']/../following-sibling::td[1][contains(text(),'2')]/following-sibling::*[1]/following-sibling::*[1]";
	@FindBy(xpath = lizsLastNameXpath)
	private WebElement lizsLastName;
	
	// portlet topper for bookings
	private static final String bookingsPortletDisplayNameXpath = "(//header[@class='portlet-topper']/h1/span)[2]";
	@FindBy(xpath = bookingsPortletDisplayNameXpath)
	private WebElement bookingsPortletDisplayName;
	
	// <input id="A8622:f1:firstName" type="text" name="A8622:f1:firstName" value="Brian" class="focus">
	private static final String firstNameXpath = "//input[contains(@id,':firstName')]";
	@FindBy(xpath = firstNameXpath)
	private WebElement firstName;
	
	// <input id="A8622:f1:firstName" type="text" name="A8622:f1:firstName" value="Brian" class="focus">
	private static final String lastNameXpath = "//input[contains(@id,':lastName')]";
	@FindBy(xpath = lastNameXpath)
	private WebElement lastName;
	
	// <select id="A8622:f1:j_idt14:0:bookingTypeId" name="A8622:f1:j_idt14:0:bookingTypeId" size="1"> <option value="">-- Select --</option> <option value="1">Airfare</option> <option value="2">Cruise</option> <option value="3">Hotel</option> <option value="4">Play/Theatre</option> <option value="5" selected="selected">Rental Car</option> <option value="6">Theme Park</option> <option value="7">Train</option> </select>
	private static final String bookingTypeIdXpath = "(//select[contains(@id,':bookingTypeId')])[1]";
	@FindBy(xpath = bookingTypeIdXpath)
	private WebElement bookingTypeId;
	
	// <input id="A8622:f1:j_idt14:0:startDate" type="text" name="A8622:f1:j_idt14:0:startDate" value="04/20/2013" class="focus">
	private static final String startDateXpath = "(//input[contains(@id,':startDate')])[1]";
	@FindBy(xpath = startDateXpath)
	private WebElement startDate;
	
	// <input id="A8622:f1:j_idt14:0:finishDate" type="text" name="A8622:f1:j_idt14:0:finishDate" value="04/27/2013" class="focus">
	private static final String finishDateXpath = "(//input[contains(@id,':finishDate')])[1]";
	@FindBy(xpath = finishDateXpath)
	private WebElement finishDate;
	
	// <input type="submit" name="A8622:f1:j_idt28" value="Submit" id="aui_3_4_0_1_2331">
	private static final String submitXpath = "//input[@type='submit' and @value='Submit']";
	@FindBy(xpath = submitXpath)
	private WebElement submit;
	
	@Before
	public void beforeEachTest() {
		
//		browser.manage().deleteAllCookies();
//		logger.log(Level.INFO, "browser.manage().deleteAllCookies() ...");
		
	}

	public void signIn() throws Exception {
		
		// Shut its dirty mouth
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		
		logger.log(Level.INFO, "browser.navigate().to("+signInUrl+")");
		browser.navigate().to(signInUrl);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " before signing in ...");
		
		emailField.clear();
		emailField.sendKeys("test@liferay.com");
		passwordField.clear();
		passwordField.sendKeys("test");
		signInButton.click();
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle() + " after clicking the sign in button and waiting");
		logger.log(Level.INFO, signedInText.getText());
		// assertTrue("You are signed in", signedInText.getText().contains("You are signed in"));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1000)
	public void renderViewMode() throws Exception {
			
		signIn();
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "customerPortletDisplayName.getText() = " + customerPortletDisplayName.getText());
		logger.log(Level.INFO, "bookingsPortletDisplayName.getText() = " + bookingsPortletDisplayName.getText());
		
		assertTrue("customerPortletDisplayName.isDisplayed()", customerPortletDisplayName.isDisplayed());
		assertTrue("bookingsPortletDisplayName.isDisplayed()", bookingsPortletDisplayName.isDisplayed());
		
		logger.log(Level.INFO, "browser.findElements(By.xpath(portletDisplayNameXpath)).size() = " + browser.findElements(By.xpath(customerPortletDisplayNameXpath)).size());
		logger.log(Level.INFO, "browser.findElements(By.xpath(briansInputXpath)).size() = " + browser.findElements(By.xpath(briansInputXpath)).size());
		logger.log(Level.INFO, "browser.findElements(By.xpath(lizsInputXpath)).size() = " + browser.findElements(By.xpath(lizsInputXpath)).size());
		
		logger.log(Level.INFO, "briansFirstName.getText() = " + briansFirstName.getText());
		logger.log(Level.INFO, "briansLastName.getText() = " + briansLastName.getText());
		
		logger.log(Level.INFO, "lizsFirstName.getText() = " + lizsFirstName.getText());
		logger.log(Level.INFO, "lizsLastName.getText() = " + lizsLastName.getText());
		
		// logger.log(Level.INFO, "browser.getPageSource() = " + browser.getPageSource());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(2000)
	public void checkBriansBookings() throws Exception {
		
		briansInput.click();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "firstName.getAttribute(value) = " + firstName.getAttribute("value"));
		logger.log(Level.INFO, "lastName.getAttribute(value) = " + lastName.getAttribute("value"));
		logger.log(Level.INFO, "startDate.getAttribute(value) = " + startDate.getAttribute("value"));
		logger.log(Level.INFO, "finishDate.getAttribute(value) = " + finishDate.getAttribute("value"));
		
		assertTrue("customer first name should be the same in the bookings but it is '"+briansFirstName.getText()+"' in the customer module,"+
				" and '"+firstName.getAttribute("value")+"' in bookings",
				firstName.getAttribute("value").contains(briansFirstName.getText())
			);
		assertTrue("customer last name should be the same in the bookings but it is '"+briansLastName.getText()+"' in the customer module,"+
				" and '"+lastName.getAttribute("value")+"' in bookings",
				lastName.getAttribute("value").contains(briansLastName.getText())
			);
		
	}
	
	@Test
	@RunAsClient
	@InSequence(3000)
	public void changeBriansBookings() throws Exception {
		
		logger.log(Level.INFO, "lastName.clear() ...");
		lastName.clear();
		logger.log(Level.INFO, "lastName.sendKeys('Greeny') ...");
		lastName.sendKeys("Greeny");
		finishDate.clear();
		finishDate.sendKeys("04/20/2099");
		submit.click();
		Thread.sleep(250);
		
		logger.log(Level.INFO, "briansLastName.getText() = " + briansLastName.getText());
		logger.log(Level.INFO, "lastName.getAttribute(value) = " + lastName.getAttribute("value"));
		logger.log(Level.INFO, "finishDate.getAttribute(value) = " + finishDate.getAttribute("value"));
		
		assertTrue("customer first name should be the same in the bookings but it is '"+briansLastName.getText()+"' in the customer module,"+
				" and '"+lastName.getAttribute("value")+"' in bookings",
				lastName.getAttribute("value").contains(briansLastName.getText())
			);
		
		assertTrue("Customer last name should contain 'Greeny', but it contains '"+briansLastName.getText()+"'", briansLastName.getText().contains("Greeny"));
		assertTrue("Bookings last name should contain 'Greeny', but it contains '"+lastName.getAttribute("value")+"'", lastName.getAttribute("value").contains("Greeny"));
	}
	
	@Test
	@RunAsClient
	@InSequence(4000)
	public void checkLizsBookings() throws Exception {
		
		lizsInput.click();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "firstName.getAttribute(value) = " + firstName.getAttribute("value"));
		logger.log(Level.INFO, "lastName.getAttribute(value) = " + lastName.getAttribute("value"));
		logger.log(Level.INFO, "startDate.getAttribute(value) = " + startDate.getAttribute("value"));
		logger.log(Level.INFO, "finishDate.getAttribute(value) = " + finishDate.getAttribute("value"));
		
		assertTrue("customer first name should be the same in the bookings but it is '"+lizsFirstName.getText()+"' in the customer module,"+
				" and '"+firstName.getAttribute("value")+"' in bookings",
				firstName.getAttribute("value").contains(lizsFirstName.getText())
			);
		assertTrue("customer last name should be the same in the bookings but it is '"+lizsLastName.getText()+"' in the customer module,"+
				" and '"+lastName.getAttribute("value")+"' in bookings",
				lastName.getAttribute("value").contains(lizsLastName.getText())
			);
		
	}
	
	@Test
	@RunAsClient
	@InSequence(5000)
	public void changeLizsBookings() throws Exception {
		
		logger.log(Level.INFO, "firstName.clear() ...");
		firstName.clear();
		logger.log(Level.INFO, "firstName.sendKeys('Lizzy') ...");
		firstName.sendKeys("Lizzy");
		finishDate.clear();
		finishDate.sendKeys("12/25/2999");
		submit.click();
		Thread.sleep(250);
		
		logger.log(Level.INFO, "lizsFirstName.getText() = " + lizsFirstName.getText());
		logger.log(Level.INFO, "firstName.getAttribute(value) = " + firstName.getAttribute("value"));
		logger.log(Level.INFO, "finishDate.getAttribute(value) = " + finishDate.getAttribute("value"));
		
		assertTrue("customer first name should be the same in the bookings but it is '"+lizsFirstName.getText()+"' in the customer module,"+
				" and '"+firstName.getAttribute("value")+"' in bookings",
				firstName.getAttribute("value").contains(lizsFirstName.getText())
			);
		
		assertTrue("Customer first name should contain 'Lizzy', but it contains '"+lizsFirstName.getText()+"'", lizsFirstName.getText().contains("Lizzy"));
		assertTrue("Bookings first name should contain 'Lizzy', but it contains '"+firstName.getAttribute("value")+"'", firstName.getAttribute("value").contains("Lizzy"));
	}
	
	@Test
	@RunAsClient
	@InSequence(6000)
	public void checkBriansBookingsAgain() throws Exception {
		
		briansInput.click();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "firstName.getAttribute(value) = " + firstName.getAttribute("value"));
		logger.log(Level.INFO, "lastName.getAttribute(value) = " + lastName.getAttribute("value"));
		logger.log(Level.INFO, "startDate.getAttribute(value) = " + startDate.getAttribute("value"));
		logger.log(Level.INFO, "finishDate.getAttribute(value) = " + finishDate.getAttribute("value"));
		
		assertTrue("customer first name should be the same in the bookings but it is '"+briansFirstName.getText()+"' in the customer module,"+
				" and '"+firstName.getAttribute("value")+"' in bookings",
				firstName.getAttribute("value").contains(briansFirstName.getText())
			);
		assertTrue("customer last name should be the same in the bookings but it is '"+briansLastName.getText()+"' in the customer module,"+
				" and '"+lastName.getAttribute("value")+"' in bookings",
				lastName.getAttribute("value").contains(briansLastName.getText())
			);
		
		assertTrue("Brian's first finishDate should be '04/20/2099', but it is '"+finishDate.getAttribute("value")+"'",
				finishDate.getAttribute("value").contains("04/20/2099")
			);
		
	}
	
	public boolean isThere(String xpath) {
		boolean isThere = false;
		int count = 0;
		count = browser.findElements(By.xpath(xpath)).size();
		if (count == 0) { isThere = false; }
		if (count > 0) { isThere = true; }
		if (count > 1) {
			logger.log(Level.WARNING, "The method 'isThere(xpath)' found "+count+" matches using xpath = " + xpath + 
					" ... the word 'is' implies singluar, or 1, not " + count
				);
		}
		return isThere;
	}

}
