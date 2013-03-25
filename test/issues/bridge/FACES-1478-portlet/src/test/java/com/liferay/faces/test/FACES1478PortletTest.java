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
public class FACES1478PortletTest {
	
	private final static Logger logger = Logger.getLogger(FACES1478PortletTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/signin";
	String url = "http://localhost:8080/web/guest/faces-1478";

	@Drone
	WebDriver browser;
	
	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	
	private static final String formTagXpath = "//form[@method='post']";
	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	
	private static final String secondLinkXpath = "//form[@method='post']/a[2]";
	@FindBy(xpath = secondLinkXpath)
	private WebElement secondLink;
	
	@Before
	public void beforeEachTest() {
		
//		browser.manage().deleteAllCookies();
//		logger.log(Level.INFO, "browser.manage().deleteAllCookies() ...");
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES1478PortletParameters() throws Exception {
		
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		logger.log(Level.INFO, "formTag.getText() = " + formTag.getText());
		logger.log(Level.INFO, "secondLink.getAttribute('href') = " + secondLink.getAttribute("href"));
		
		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue("formTag.isDisplayed()", formTag.isDisplayed());
		assertTrue("secondLink.isDisplayed()", secondLink.isDisplayed());
		
		int firstParameter = secondLink.getAttribute("href").indexOf("testParam=foo");
		logger.log(Level.INFO, "firstParameter = " + firstParameter);
		int secondParameter = secondLink.getAttribute("href").indexOf("testParam=bar");
		logger.log(Level.INFO, "secondParameter = " + secondParameter);
		
		assertTrue("firstParameter is in the url", firstParameter > -1);
		assertTrue("secondParameter is in the url", secondParameter > -1);
		assertTrue("firstParameter occurs before the secondParameter", firstParameter < secondParameter);
		
		// logger.log(Level.INFO, "browser.getPageSource() = " + browser.getPageSource());
		
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
