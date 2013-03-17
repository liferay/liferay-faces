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
import java.io.File;
import org.apache.commons.io.FileUtils;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;
// import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.jboss.arquillian.graphene.Graphene.waitGui;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

@RunWith(Arquillian.class)
public class Jsf2PortletTest {
	
	private final static Logger logger = Logger.getLogger(Jsf2PortletTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/signin";
	String url = "http://localhost:8080/group/bridge-demos/jsf2";

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
	private WebElement signedInText;
	
	// form tag found after submitting
	@FindBy(xpath = "//form[@method='post']")
	private WebElement formTag;
	
	// portlet topper and menu elements
	@FindBy(xpath = "//header[@class='portlet-topper']/h1/span")
	private WebElement portletDisplayName;
	@FindBy(xpath = "//a[contains(@id,'menuButton')]")
	private WebElement menuButton;
	@FindBy(xpath = "//a[contains(@id,'menu_preferences')]")
	private WebElement menuPreferences;
	
	// preferences elements
	@FindBy(xpath = "//input[contains(@id,':datePattern')]")
	private WebElement datePatternField;
	@FindBy(xpath = "//input[@type='submit' and @value='Reset']")
	private WebElement resetButton;
	
	// elements for Job Applicants
	@FindBy(xpath = "//img[contains(@src,'liferay-logo.png')]")
	private WebElement logo;
	
	@FindBy(xpath = "//input[contains(@id,':firstName')]")
	private WebElement firstNameField;
	@FindBy(xpath = "//input[contains(@id,':firstName')]/following-sibling::*[1]")
	private WebElement firstNameFieldError;
	
	@FindBy(xpath = "//input[contains(@id,':lastName')]")
	private WebElement lastNameField;
	@FindBy(xpath = "//input[contains(@id,':lastName')]/following-sibling::*[1]")
	private WebElement lastNameFieldError;
	
	@FindBy(xpath = "//input[contains(@id,':emailAddress')]")
	private WebElement emailAddressField;
	@FindBy(xpath = "//input[contains(@id,':emailAddress')]/following-sibling::*[1]")
	private WebElement emailAddressFieldError;
	
	@FindBy(xpath = "//input[contains(@id,':phoneNumber')]")
	private WebElement phoneNumberField;
	@FindBy(xpath = "//input[contains(@id,':phoneNumber')]/following-sibling::*[1]")
	private WebElement phoneNumberFieldError;
	
	@FindBy(xpath = "//input[contains(@id,':dateOfBirth')]")
	private WebElement dateOfBirthField;
	@FindBy(xpath = "//input[contains(@id,':dateOfBirth')]/following-sibling::*[1]")
	private WebElement dateOfBirthFieldError;
	
	@FindBy(xpath = "//input[contains(@id,':city')]")
	private WebElement cityField;
	@FindBy(xpath = "//input[contains(@id,':city')]/following-sibling::*[1]")
	private WebElement cityFieldError;
	
	@FindBy(xpath = "//select[contains(@id,':provinceId')]")
	private WebElement provinceIdField;
	@FindBy(xpath = "//select[contains(@id,':provinceId')]/following-sibling::*[1]")
	private WebElement provinceIdFieldError;
	
	@FindBy(xpath = "//input[contains(@id,':postalCode')]")
	private WebElement postalCodeField;
	@FindBy(xpath = "//input[contains(@id,':postalCode')]/following-sibling::*[1]/following-sibling::*[1]")
	private WebElement postalCodeFieldError;
	
	@FindBy(xpath = "//img[contains(@title,'Type any of these ZIP codes')]")
	private WebElement postalCodeToolTip;
	
	@FindBy(xpath = "//input[@type='file' and @multiple='multiple']")
	private WebElement multiFileUploadButton;
	@FindBy(xpath = "//form[@enctype='multipart/form-data']/input[@type='submit' and @value='Submit']")
	private WebElement submitFilesButton;
	
	@FindBy(xpath = "//a[contains(text(),'Show Comments')]")
	private WebElement showCommentsLink;
	@FindBy(xpath = "//a[contains(text(),'Hide Comments')]")
	private WebElement hideCommentsLink;
	@FindBy(xpath = "//textarea[contains(@id,':comments')]")
	private WebElement comments;
	
	@FindBy(xpath = "//input[@type='submit' and @value='Submit']")
	private WebElement submitButton;
	@FindBy(xpath = "//input[@type='submit' and @value='Submit']")
	private WebElement preferencesSubmitButton;
	@FindBy(xpath = "//input[@type='submit' and @value='Edit Preferences']")
	private WebElement editPreferencesButton;
	@FindBy(xpath = "//a[contains(text(),'Return to Full Page')]")
	private WebElement returnLink;
	
	@FindBy(xpath = "//*[contains(text(),'Mojarra')]")
	private WebElement mojarraVersion;
	// component
	// library version
	@FindBy(xpath = "//*[contains(text(),'Liferay Faces Alloy')]")
	private WebElement alloyVersion;
	@FindBy(xpath = "//*[contains(text(),'Liferay Faces Bridge')]")
	private WebElement bridgeVersion;
	
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
	public void jobApplicantFieldsRender() throws Exception {
			
		signIn();
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		
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
		
		logger.log(Level.INFO, "multiFileUploadButton.getAttribute('id') = " + multiFileUploadButton.getAttribute("id"));
		logger.log(Level.INFO, "multiFileUploadButton.getAttribute('class') = " + multiFileUploadButton.getAttribute("class"));
		assertTrue("multiFileUploadButton.isDisplayed()", multiFileUploadButton.isDisplayed());
		assertTrue("submitFilesButton.isDisplayed()", submitFilesButton.isDisplayed());
		logger.log(Level.INFO, "submitFilesButton.getTagName() = " + submitFilesButton.getTagName());
		
		assertTrue("showCommentsLink.isDisplayed()", showCommentsLink.isDisplayed());
		
		assertTrue("submitButton.isDisplayed()", submitButton.isDisplayed());
		logger.log(Level.INFO, "submitButton.getTagName() = " + submitButton.getTagName());
		assertTrue("editPreferencesButton.isDisplayed()", editPreferencesButton.isDisplayed());
		logger.log(Level.INFO, "editPreferencesButton.getTagName() = " + editPreferencesButton.getTagName());
		
		assertTrue("mojarraVersion.isDisplayed()", mojarraVersion.isDisplayed());
		// component library version ... maybe liferay faces alloy
		assertTrue("alloyVersion.isDisplayed()", alloyVersion.isDisplayed());
		assertTrue("bridgeVersion.isDisplayed()", bridgeVersion.isDisplayed());

		logger.log(Level.INFO, mojarraVersion.getText());
		// component library version
		logger.log(Level.INFO, alloyVersion.getText());
		logger.log(Level.INFO, bridgeVersion.getText());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(2000)
	public void validateEmail() throws Exception {
		
		int tags = 0;
		int tagsWhileInvalid = 1;
		int tagsWhileValid = 0;
		
		// checks an invalid email address
		emailAddressField.sendKeys("test");
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "emailAddressField.getAttribute('value') = " + emailAddressField.getAttribute("value"));
		logger.log(Level.INFO, "emailAddressFieldError.isDisplayed() = " + emailAddressFieldError.isDisplayed());
		logger.log(Level.INFO, "emailAddressFieldError.getText() = " + emailAddressFieldError.getText());
		tags = browser.findElements(By.xpath("//span[contains(text(),'Invalid e-mail address')]")).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("Invalid e-mail address validation message displayed", 
				emailAddressFieldError.getText().contains("Invalid e-mail address"));
		assertTrue("# of error tags == tagsWhileInvalid", tags == tagsWhileInvalid);
		
		// checks a valid email address
		emailAddressField.clear();
		Thread.sleep(500);
		emailAddressField.sendKeys("test@liferay.com");
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "emailAddressField.getAttribute('value') = " + emailAddressField.getAttribute("value"));
		tags = browser.findElements(By.xpath("//span[contains(text(),'Invalid e-mail address')]")).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("# of error tags == tagsWhileValid", tags == tagsWhileValid);
		
	}
	
	@Test
	@RunAsClient
	@InSequence(3000)
	public void preferencesAreWorking() throws Exception {
		
		// test for both
		int dateLengthAfterChange = 8;
		int dateLengthAfterReset = 10;
		
		if (dateOfBirthField.getAttribute("value").equals("")) {
			// try because it may be read-only (as is the case with richfaces4)
			try {
				dateOfBirthField.sendKeys("07/04/1776");
			} catch (Exception e) {
				logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
				assertTrue("No exceptions occured when entering a dateOfBirth", false);
			}
		}
		
		menuButton.click();
		Thread.sleep(500);
		menuPreferences.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "datePatternField.getAttribute('value') = " + datePatternField.getAttribute("value"));
		logger.log(Level.INFO, "resetButton.isDisplayed() = " + resetButton.isDisplayed());
		// MM/dd/yyyy
		datePatternField.clear();
		datePatternField.sendKeys("MM/dd/yy");
		preferencesSubmitButton.click();
		// preferencesSubmitButton takes us back to the job app submission form
		// so we expect to see date of birth next
		// we can check it for the reset datePattern, no need for extra navigation
		Thread.sleep(1000);
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value').length() = " + 
				dateOfBirthField.getAttribute("value").length()
			);
		
		assertTrue(
				"date of birth has "+dateLengthAfterChange+" characters after changing preferences to MM/dd/yy", 
				dateOfBirthField.getAttribute("value").length() == dateLengthAfterChange
			);
		
		editPreferencesButton.click();
		logger.log(Level.INFO, "editPreferencesButton.click() ...");
		Thread.sleep(500);
		resetButton.click();
		logger.log(Level.INFO, "resetButton.click() ...");
		Thread.sleep(500);
		// reset button takes us back to the job app submission form when using the jsf2-portlet
		// so we expect to see date of birth next
		// we can check then the dateOfBirthField for the reset datePattern
		// no confirmation of the reset is given in the jsf2-portlet 
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value').length() = " + dateOfBirthField.getAttribute("value").length());
		
		assertTrue(
				"date of birth has "+dateLengthAfterReset+" characters after resetting preferences", 
				dateOfBirthField.getAttribute("value").length() == dateLengthAfterReset
			);
		
	}
	
	@Test
	@RunAsClient
	@InSequence(4000)
	public void reset() throws Exception {
		
		// because some test failures throw us into a strange state, 
		// let's reset preferences and the page we are on
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		Thread.sleep(500);
		menuButton.click();
		Thread.sleep(500);
		menuPreferences.click();
		Thread.sleep(500);
		resetButton.click();
		logger.log(Level.INFO, "resetButton.click() ...");
		Thread.sleep(500);
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		Thread.sleep(500);
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		assertTrue("We are on the correct page, which should be, url = " + url, browser.getCurrentUrl().contains(url));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(5000)
	public void allFieldsRequiredUponSubmit() throws Exception {
		
		firstNameField.clear();
		lastNameField.clear();
		emailAddressField.clear();
		phoneNumberField.clear();
		dateOfBirthField.clear();
		cityField.clear();
		postalCodeField.clear();
		submitButton.click();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "firstNameFieldError.getText() = " + firstNameFieldError.getText());
		logger.log(Level.INFO, "lastNameFieldError.getText() = " + lastNameFieldError.getText());
		logger.log(Level.INFO, "emailAddressFieldError.getText() = " + emailAddressFieldError.getText());
		logger.log(Level.INFO, "phoneNumberFieldError.getText() = " + phoneNumberFieldError.getText());
		logger.log(Level.INFO, "dateOfBirthFieldError.getText() = " + dateOfBirthFieldError.getText());
		logger.log(Level.INFO, "cityFieldError.getText() = " + cityFieldError.getText());
		logger.log(Level.INFO, "provinceIdFieldError.getText() = " + provinceIdFieldError.getText());
		logger.log(Level.INFO, "postalCodeFieldError.getText() = " + postalCodeFieldError.getText());
		
		assertTrue("firstNameFieldError contains Value is required", firstNameFieldError.getText().contains("Value is required"));
		assertTrue("lastNameFieldError contains Value is required", lastNameFieldError.getText().contains("Value is required"));
		assertTrue("emailAddressFieldError contains Value is required", emailAddressFieldError.getText().contains("Value is required"));
		assertTrue("phoneNumberFieldError contains Value is required", phoneNumberFieldError.getText().contains("Value is required"));
		assertTrue("dateOfBirthFieldError contains Value is required", dateOfBirthFieldError.getText().contains("Value is required"));
		assertTrue("cityFieldError contains Value is required", cityFieldError.getText().contains("Value is required"));
		assertTrue("provinceIdFieldError contains Value is required", provinceIdFieldError.getText().contains("Value is required"));
		assertTrue("postalCodeFieldError contains Value is required", postalCodeFieldError.getText().contains("Value is required"));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(6000)
	public void cityAndStateAutoPopulate() throws Exception {
		
		logger.log(Level.INFO, "before cityField.getAttribute('value') = " + cityField.getAttribute("value"));
		logger.log(Level.INFO, "before provinceIdField.getAttribute('value') = " + provinceIdField.getAttribute("value"));
		logger.log(Level.INFO, "before postalCodeField.getAttribute('value') = " + postalCodeField.getAttribute("value"));
		assertTrue("cityField is empty", (cityField.getAttribute("value").length() == 0));
		assertTrue("provinceIdField is empty", (provinceIdField.getAttribute("value").length() == 0));
		assertTrue("postalCodeField is empty", (postalCodeField.getAttribute("value").length() == 0));
		
		postalCodeField.sendKeys("32801");
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "after cityField.getAttribute('value') = " + cityField.getAttribute("value"));
		logger.log(Level.INFO, "after provinceIdField.getAttribute('value') = " + provinceIdField.getAttribute("value"));
		logger.log(Level.INFO, "after postalCodeField.getAttribute('value') = " + postalCodeField.getAttribute("value"));
		assertTrue("cityField contains Orlando", cityField.getAttribute("value").contains("Orlando"));
		assertTrue("provinceIdField contains 3", provinceIdField.getAttribute("value").contains("3"));
		assertTrue("postalCodeField contains 32801", postalCodeField.getAttribute("value").contains("32801"));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(7000)
	public void commentsFunctioning() throws Exception {
		
		String testing123 = "testing 1, 2, 3";
		int tags = 0;
		int tagsWhileHidden = 1;
		int tagsWhileShowing = 2;
		
		showCommentsLink.click();
		Thread.sleep(500);
		
		int hideCommentsLinks = browser.findElements(By.xpath("//a[contains(text(),'Hide Comments')]")).size();
		logger.log(Level.INFO, "# of hideCommentsLinks = " + hideCommentsLinks);
		assertTrue("# of hideCommentsLinks == 1", hideCommentsLinks == 1);
		logger.log(Level.INFO, "comments.isDisplayed() = " + comments.isDisplayed());
		assertTrue("comments textarea is displayed", comments.isDisplayed());
		tags = browser.findElements(By.xpath("//a[contains(text(),'Hide Comments')]/../../child::node()")).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("tag for Hide and tag for textarea are showing", tags == tagsWhileShowing);
		
		comments.sendKeys(testing123);
		phoneNumberField.click();
		Thread.sleep(500);
		hideCommentsLink.click();
		Thread.sleep(500);
		tags = browser.findElements(By.xpath("//a[contains(text(),'Show Comments')]/../../child::node()")).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("no textarea is showing", tags == tagsWhileHidden);
		logger.log(Level.INFO, "showCommentsLink.isDisplayed() = " + showCommentsLink.isDisplayed());
		showCommentsLink.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "after hide and show comments.getAttribute('value') = " + comments.getAttribute("value"));
		assertTrue("comments are still there after hide and show", testing123.equals(comments.getAttribute("value")));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(8000)
	public void dateValidation() throws Exception {
		
		int tags = 0;
		int tagsWhileInvalid = 2;
		int tagsWhileValid = 1;
		
		// checks an invalid dateOfBirth
		try {
			dateOfBirthField.clear();
			logger.log(Level.INFO, "No exceptions occured when clearing the dateOfBirthField");
		} catch (Exception e) {
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("No exceptions occured when clearing the dateOfBirthField", false);
		}
		Thread.sleep(500);
		dateOfBirthField.sendKeys("12/34/5678");
		Thread.sleep(500);
		submitButton.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO, "dateOfBirthFieldError.isDisplayed() = " + dateOfBirthFieldError.isDisplayed());
		logger.log(Level.INFO, "dateOfBirthFieldError.getText() = " + dateOfBirthFieldError.getText());
		assertTrue("Invalid dateOfBirthField validation message displayed", 
				dateOfBirthFieldError.getText().contains("Invalid date format"));
		tags = browser.findElements(By.xpath("//input[contains(@id,':dateOfBirth')]/../child::node()")).size();
		logger.log(Level.INFO, "tags = " + tags);
		logger.log(Level.INFO, "asserting: tags == tagsWhileInvalid? "+tags+" == "+tagsWhileInvalid+"? ...");
		assertTrue("tags == tagsWhileInvalid? "+tags+" == "+tagsWhileInvalid+"?", tags == tagsWhileInvalid);
		
		// checks with no dateOfBirth
		dateOfBirthField.clear();
		Thread.sleep(500);
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		logger.log(Level.INFO, "dateOfBirthFieldError.isDisplayed() = " + dateOfBirthFieldError.isDisplayed());
		logger.log(Level.INFO, "dateOfBirthFieldError.getText() = " + dateOfBirthFieldError.getText());
		// Should I be this lenient?
		assertTrue("Value is required for dateOfBirthField message displayed", 
				dateOfBirthFieldError.getText().contains("Value is required") ||
				dateOfBirthFieldError.getText().contains("Invalid date format")
			);
		tags = browser.findElements(By.xpath("//input[contains(@id,':dateOfBirth')]/../child::node()")).size();
		logger.log(Level.INFO, "tags = " + tags);
		logger.log(Level.INFO, "asserting: tags == tagsWhileInvalid? "+tags+" == "+tagsWhileInvalid+"? ...");
		assertTrue("tags == tagsWhileInvalid? "+tags+" == "+tagsWhileInvalid+"?", tags == tagsWhileInvalid);
		
		// checks a valid dateOfBirth
		dateOfBirthField.clear();
		Thread.sleep(500);
		logger.log(Level.INFO, "Entering a valid dateOfBirth = 01/02/3456 ...");
		dateOfBirthField.sendKeys("01/02/3456");
		Thread.sleep(500);
		logger.log(Level.INFO, "Clicking into the phoneNumberField ...");
		phoneNumberField.click();
		Thread.sleep(500);
		logger.log(Level.INFO, "Should still contain the valid dateOfBirthField.getAttribute('value') = " + dateOfBirthField.getAttribute("value"));
		assertTrue("dateOfBirthField is currently showing 01/02/3456 ?", "01/02/3456".equals(dateOfBirthField.getAttribute("value")));
		tags = browser.findElements(By.xpath("//input[contains(@id,':dateOfBirth')]/../child::node()")).size();
		logger.log(Level.INFO, "tags = " + tags);
		assertTrue("tags == tagsWhileValid? "+tags+" == "+tagsWhileValid+"?", tags == tagsWhileValid);
		
	}
	
	@Test
	@RunAsClient
	@InSequence(9000)
	public void submitAndValidate() throws Exception {
		
		logger.log(Level.INFO, "clearing fields ...");
		dateOfBirthField.clear();
		emailAddressField.clear();
		postalCodeField.clear();
		
		int commentsTextAreas = browser.findElements(By.xpath("//textarea[contains(@id,':comments')]")).size();
		logger.log(Level.INFO, "# of commentsTextAreas = " + commentsTextAreas);
		assertTrue("# of commentsTextAreas == 1", commentsTextAreas == 1);
		
		comments.clear();
		Thread.sleep(500);
		logger.log(Level.INFO, "fields were cleared now, but let's see ...");
		logger.log(Level.INFO, "emailAddressField.getAttribute('value') = " + emailAddressField.getAttribute("value"));
		assertTrue("emailAddressField is empty after clearing and clicking into another field", "".equals(emailAddressField.getAttribute("value")));
		
		logger.log(Level.INFO, "entering data ...");
		firstNameField.sendKeys("David");
		lastNameField.sendKeys("Samuel");
		emailAddressField.sendKeys("no_need@just.pray");
		phoneNumberField.sendKeys("(way) too-good");
		dateOfBirthField.sendKeys("01/02/3456");
		postalCodeField.sendKeys("32801");
		phoneNumberField.click();
		Thread.sleep(500);
		comments.sendKeys("If as one people speaking the same language, they have begun to do this ...");
		Thread.sleep(500);
		
		// asserting correct data is still there
		assertTrue("firstNameField.getText().equals('David')", firstNameField.getAttribute("value").equals("David"));
		assertTrue("lastNameField.getText().equals('Samuel')", lastNameField.getAttribute("value").equals("Samuel"));
		assertTrue("emailAddressField.getText().equals('no_need@just.pray')", emailAddressField.getAttribute("value").equals("no_need@just.pray"));
		assertTrue("phoneNumberField.getText().equals('(way) too-good')", phoneNumberField.getAttribute("value").equals("(way) too-good"));
		assertTrue("dateOfBirthField.getText().equals('01/02/3456')", dateOfBirthField.getAttribute("value").equals("01/02/3456"));
		assertTrue("postalCodeField.getText().equals('32801')", postalCodeField.getAttribute("value").equals("32801"));
		assertTrue("comments.getText().equals('If as one people speaking the same language, they have begun to do this ...')",
				comments.getAttribute("value").equals("If as one people speaking the same language, they have begun to do this ..."));
		
		submitButton.click();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "formTag.getText() = " + formTag.getText());
		assertTrue("Dear David", formTag.getText().contains("Dear David"));
		
	}

}
