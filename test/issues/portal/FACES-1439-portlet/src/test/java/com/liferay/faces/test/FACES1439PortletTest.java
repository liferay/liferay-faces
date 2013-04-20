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
public class FACES1439PortletTest {
	
	private final static Logger logger = Logger.getLogger(FACES1439PortletTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/signin";
	String url = "http://localhost:8080/web/portal-issues/faces-1439";

	@Drone
	WebDriver browser;
	
	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	
	
	// text area 1
	private static final String showHideOneXpath = "//input[@type='submit' and @value='Show/Hide Editor 1']";
	@FindBy(xpath = showHideOneXpath)
	private WebElement showHideOne;
	
	private static final String comments1OutputXpath = "//span[contains(@id,':comments1Output')]";
	@FindBy(xpath = comments1OutputXpath)
	private WebElement comments1Output;
	
	private static final String iframe1Xpath = "//iframe[contains(@title,':inputEditor1')]";
	@FindBy(xpath = iframe1Xpath)
	private WebElement iframe1;
	
	// text area 2
	private static final String showHideTwoXpath = "//input[@type='submit' and @value='Show/Hide Editor 2']";
	@FindBy(xpath = showHideTwoXpath)
	private WebElement showHideTwo;
	
	private static final String comments2OutputXpath = "//span[contains(@id,':comments2Output')]";
	@FindBy(xpath = comments2OutputXpath)
	private WebElement comments2Output;
	
	private static final String iframe2Xpath = "//iframe[contains(@title,':inputEditor2')]";
	@FindBy(xpath = iframe2Xpath)
	private WebElement iframe2;
	
	// input 3
	private static final String comments3OutputXpath = "//span[contains(@id,':comments3Output')]";
	@FindBy(xpath = comments3OutputXpath)
	private WebElement comments3Output;
	
	// <input id="A7264:f1:j_idt22" type="text" name="A7264:f1:j_idt22" class="focus">
	private static final String inputThreeXpath = "//span[contains(@id,':comments3Output')]/following-sibling::br[1]/following-sibling::input[1]";
	@FindBy(xpath = inputThreeXpath)
	private WebElement inputThree;
	
	@Before
	public void beforeEachTest() {
		
//		browser.manage().deleteAllCookies();
//		logger.log(Level.INFO, "browser.manage().deleteAllCookies() ...");
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES1439PortletViewMode() throws Exception {
		
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		
		logger.log(Level.INFO, "showHideOne.isDisplayed() = " + showHideOne.isDisplayed());
		logger.log(Level.INFO, "showHideTwo.isDisplayed() = " + showHideTwo.isDisplayed());
		
		assertTrue("showHideOne.isDisplayed()", showHideOne.isDisplayed());
		assertTrue("showHideTwo.isDisplayed()", showHideTwo.isDisplayed());
		
		// logger.log(Level.INFO, "browser.getPageSource() = " + browser.getPageSource());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1100)
	public void htmlEditor1() throws Exception {
		
		logger.log(Level.INFO, "Typing into htmlEditor1 ...");
		iframe1.sendKeys("Hello world 1");
		if (comments1Output.getText().contains("Hello world 1")) {
			logger.log(Level.INFO, "comments1Output.getText() = " + comments1Output.getText() + " ... immediately after entering the keys");
		} else {
			Thread.sleep(80);
			if (comments1Output.getText().contains("Hello world 1")) {
				logger.log(Level.INFO, "comments1Output.getText() = " + comments1Output.getText() + " ... after 80 millis");
			} else {
				Thread.sleep(80);
				if (comments1Output.getText().contains("Hello world 1")) {
					logger.log(Level.INFO, "comments1Output.getText() = " + comments1Output.getText() + " ... after 160 millis");
				} else {
					Thread.sleep(80);
					if (comments1Output.getText().contains("Hello world 1")) {
						logger.log(Level.INFO, "comments1Output.getText() = " + comments1Output.getText() + " ... after 240 millis");
					} else {
						Thread.sleep(80);
						if (comments1Output.getText().contains("Hello world 1")) {
							logger.log(Level.INFO, "comments1Output.getText() = " + comments1Output.getText() + " ... after 320 millis");
						} else {
							Thread.sleep(80);
							if (comments1Output.getText().contains("Hello world 1")) {
								logger.log(Level.INFO, "comments1Output.getText() = " + comments1Output.getText() + " ... after 400 millis");
							}
						}
					}
				}
			}
		}
		
		assertTrue("Model bean should have updated via ajax when characters were typed", comments1Output.getText().contains("Hello world 1"));
		
		// hide iframe 1
		showHideOne.click();
		Thread.sleep(250);
		
		logger.log(Level.INFO, "comments1Output.getText() = " + comments1Output.getText() + " ... after hide");
		
		// show iframe 1
		showHideOne.click();
		Thread.sleep(250);
		
		browser.switchTo().frame(iframe1);
		WebElement iframe1active = browser.switchTo().activeElement();
		logger.log(Level.INFO, "iframe1active.getText() = " + iframe1active.getText() + " ... after hide and show");
		String textFromIframe1 = iframe1active.getText();
		browser.switchTo().defaultContent();
		
		assertTrue("The first text editor should contain 'Hello world 1', but contains '"+textFromIframe1+"'", textFromIframe1.contains("Hello world 1"));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1200)
	public void htmlEditor2() throws Exception {
		
		logger.log(Level.INFO, "Typing into htmlEditor2 ...");
		iframe2.click();
		Thread.sleep(500);
		iframe2.sendKeys("Hello world 2");
		Thread.sleep(1000);
		logger.log(Level.INFO, "comments2Output.getText() = " + comments2Output.getText());
		assertFalse("Model bean should not be updated yet since we have not tabbed-out of the field", comments2Output.getText().contains("Hello world 2"));
		
		// iframe2.sendKeys(Keys.TAB);
		// iframe2.sendKeys does not send the keys to the iframe but to the active frame, use browser.switchTo() to make the iframe active, or do this
		
		(new Actions(browser)).sendKeys(Keys.TAB).perform();
		Thread.sleep(500);
		logger.log(Level.INFO, "comments2Output.getText() = " + comments2Output.getText());
		assertTrue("Model bean needs to have been updated via ajax after tabbing-out of field", comments2Output.getText().contains("Hello world 2"));
		
		// hide iframe2
		showHideTwo.click();
		Thread.sleep(250);
		logger.log(Level.INFO, "comments2Output.getText() = " + comments2Output.getText() + " ... after hide");
		
		// show iframe2
		showHideTwo.click();
		Thread.sleep(250);
		
		browser.switchTo().frame(iframe2);
		WebElement iframe2active = browser.switchTo().activeElement();
		logger.log(Level.INFO, "iframe2active.getText() = " + iframe2active.getText() + " ... after hide and show");
		String textFromIframe2 = iframe2active.getText();
		browser.switchTo().defaultContent();
		
		assertTrue("The first text editor should contain 'Hello world 1', but contains '"+textFromIframe2+"'", textFromIframe2.contains("Hello world 2"));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1300)
	public void inputThree() throws Exception {
		
		inputThree.sendKeys("Hello world 3");
		Thread.sleep(50);
		logger.log(Level.INFO, "comments3Output.getText() = " + comments3Output.getText());
		assertFalse("Model bean should not be updated yet since we have not tabbed-out of the field", comments3Output.getText().contains("Hello world 3"));
		
		inputThree.sendKeys(Keys.TAB);
		Thread.sleep(50);
		logger.log(Level.INFO, "comments3Output.getText() = " + comments3Output.getText());
		assertTrue("Model bean needs to have been updated via ajax after tabbing-out of field", comments3Output.getText().contains("Hello world 3"));
		
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
