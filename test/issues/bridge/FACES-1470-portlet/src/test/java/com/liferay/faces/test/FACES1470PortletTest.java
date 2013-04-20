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
public class FACES1470PortletTest {
	
	private final static Logger logger = Logger.getLogger(FACES1470PortletTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/signin";
	String url = "http://localhost:8080/web/bridge-issues/faces-1470";

	@Drone
	WebDriver browser;
	
	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	
	private static final String formTagXpath = "//form[@method='post']";
	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	
	// <input id="A5773:f1:j_idt18" type="submit" name="A5773:f1:j_idt18" value="Click me to navigate to view2.xhtml via Ajax" onclick="mojarra.ab(this,event,'action','@form',0);return false">
	private static final String ajaxButtonXpath = "//input[contains(@value,'Click me to navigate to view2.xhtml via Ajax')]";
	@FindBy(xpath = ajaxButtonXpath)
	private WebElement ajaxButton;
	
	// <input type="submit" name="A5773:f1:j_idt19" value="Click me to navigate to view2.xhtml via non-Ajax (full postback)" id="aui_3_4_0_1_533">
	private static final String fullPostBackButtonXpath = "//input[contains(@value,'Click me to navigate to view2.xhtml via non-Ajax')]";
	@FindBy(xpath = fullPostBackButtonXpath)
	private WebElement fullPostBackButton;
	
	private static final String ajaxButton2Xpath = "//input[contains(@value,'Click me to navigate to view1.xhtml via Ajax')]";
	@FindBy(xpath = ajaxButton2Xpath)
	private WebElement ajaxButton2;
	
	private static final String fullPostBackButton2Xpath = "//input[contains(@value,'Click me to navigate to view1.xhtml via non-Ajax')]";
	@FindBy(xpath = fullPostBackButton2Xpath)
	private WebElement fullPostBackButton2;
	
	@Before
	public void beforeEachTest() {
		
//		browser.manage().deleteAllCookies();
//		logger.log(Level.INFO, "browser.manage().deleteAllCookies() ...");
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES1470PortletViewMode() throws Exception {
		
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		
		logger.log(Level.INFO, "ajaxButton.isDisplayed() = " + ajaxButton.isDisplayed());
		logger.log(Level.INFO, "fullPostBackButton.isDisplayed() = " + fullPostBackButton.isDisplayed());
		
		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue("ajaxButton should be displayed, but it is not", ajaxButton.isDisplayed());
		assertTrue("fullPostBackButton should be displayed, but it is not", fullPostBackButton.isDisplayed());
		
		// logger.log(Level.INFO, "browser.getPageSource() = " + browser.getPageSource());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(2000)
	public void View2ViaAjax() throws Exception {
		
		ajaxButton.click();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		
		logger.log(Level.INFO, "ajaxButton2.isDisplayed() = " + ajaxButton2.isDisplayed());
		logger.log(Level.INFO, "fullPostBackButton2.isDisplayed() = " + fullPostBackButton2.isDisplayed());
		
		assertTrue("ajaxButton2 should be displayed, but it is not", ajaxButton2.isDisplayed());
		assertTrue("fullPostBackButton2 should be displayed, but it is not", fullPostBackButton2.isDisplayed());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(3000)
	public void View1ViaAjax() throws Exception {
		
		ajaxButton2.click();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		
		logger.log(Level.INFO, "ajaxButton.isDisplayed() = " + ajaxButton.isDisplayed());
		logger.log(Level.INFO, "fullPostBackButton.isDisplayed() = " + fullPostBackButton.isDisplayed());
		
		assertTrue("ajaxButton should be displayed, but it is not", ajaxButton.isDisplayed());
		assertTrue("fullPostBackButton should be displayed, but it is not", fullPostBackButton.isDisplayed());
		
		// TODO Assert Ajax ?
		
	}
	
	@Test
	@RunAsClient
	@InSequence(4000)
	public void View2PostBack() throws Exception {
		
		fullPostBackButton.click();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		
		logger.log(Level.INFO, "ajaxButton2.isDisplayed() = " + ajaxButton2.isDisplayed());
		logger.log(Level.INFO, "fullPostBackButton2.isDisplayed() = " + fullPostBackButton2.isDisplayed());
		
		assertTrue("ajaxButton2 should be displayed, but it is not", ajaxButton2.isDisplayed());
		assertTrue("fullPostBackButton2 should be displayed, but it is not", fullPostBackButton2.isDisplayed());
		
		// TODO Assert Ajax ?
		
	}
	
	@Test
	@RunAsClient
	@InSequence(5000)
	public void View1PostBack() throws Exception {
		
		fullPostBackButton2.click();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		
		logger.log(Level.INFO, "ajaxButton.isDisplayed() = " + ajaxButton.isDisplayed());
		logger.log(Level.INFO, "fullPostBackButton.isDisplayed() = " + fullPostBackButton.isDisplayed());
		
		assertTrue("ajaxButton should be displayed, but it is not", ajaxButton.isDisplayed());
		assertTrue("fullPostBackButton should be displayed, but it is not", fullPostBackButton.isDisplayed());
		
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
