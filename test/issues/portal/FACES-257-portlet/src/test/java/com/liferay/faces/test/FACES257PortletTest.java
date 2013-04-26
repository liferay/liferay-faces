package com.liferay.faces.test;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.graphene.javascript.JSInterfaceFactory;
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
import org.openqa.selenium.JavascriptExecutor;

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
public class FACES257PortletTest {
	
	private final static Logger logger = Logger.getLogger(FACES257PortletTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/signin";
	String url = "http://localhost:8080/web/portal-issues/faces-257";
	
	@Drone
	WebDriver browser;
	
	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	
	private static final String anchor1Xpath = "//a[contains(text(), 'alpha=1 beta=2 gamma=0')]";
	@FindBy(xpath = anchor1Xpath)
	private WebElement anchor1;
	
	private static final String assert1Xpath = anchor1Xpath + "/following-sibling::*[1]/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";
	@FindBy(xpath = assert1Xpath)
	private WebElement assert1;
	
	// alpha=1 beta=2 gamma=3
	private static final String anchor2Xpath = "//a[contains(text(), 'alpha=1 beta=2 gamma=3')]";
	@FindBy(xpath = anchor2Xpath)
	private WebElement anchor2;
	
	private static final String assert2Xpath = anchor2Xpath + "/following-sibling::*[1]/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";
	@FindBy(xpath = assert2Xpath)
	private WebElement assert2;
	
	// alpha=1 beta=2 gamma=3
	private static final String button1Xpath = "//input[@type='button' and contains(@value, 'alpha=4 beta=5 gamma=0')]";
	@FindBy(xpath = button1Xpath)
	private WebElement button1;
	
	private static final String assert3Xpath = button1Xpath + "/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";
	@FindBy(xpath = assert3Xpath)
	private WebElement assert3;
	
	private static final String button2Xpath = "//input[@type='button' and contains(@value, 'alpha=4 beta=5 gamma=6')]";
	@FindBy(xpath = button2Xpath)
	private WebElement button2;
	
	private static final String assert4Xpath = button2Xpath + "/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";
	@FindBy(xpath = assert4Xpath)
	private WebElement assert4;
	
	private static final String alphaXpath = "//span[contains(@id, ':alpha')]";
	@FindBy(xpath = alphaXpath)
	private WebElement alpha;
	
	private static final String betaXpath = "//span[contains(@id, ':beta')]";
	@FindBy(xpath = betaXpath)
	private WebElement beta;
	
	private static final String gammaXpath = "//span[contains(@id, ':gamma')]";
	@FindBy(xpath = gammaXpath)
	private WebElement gamma;
	
	private static final String requestedUrlXpath = "//span[contains(@id, ':requestedURL')]";
	@FindBy(xpath = requestedUrlXpath)
	private WebElement requestedUrl;
	
	@Before
	public void beforeEachTest() {
		
//		browser.manage().deleteAllCookies();
//		logger.log(Level.INFO, "browser.manage().deleteAllCookies() ...");
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES257PortletViewMode() throws Exception {
		
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		Thread.sleep(250);
		
		logger.log(Level.INFO, "portletDisplayName.isDisplayed() = " + portletDisplayName.isDisplayed());
		
		logger.log(Level.INFO, "anchor1.isDisplayed() = " + anchor1.isDisplayed());
		logger.log(Level.INFO, "assert1.getText() = " + assert1.getText());
		
		logger.log(Level.INFO, "anchor2.isDisplayed() = " + anchor2.isDisplayed());
		logger.log(Level.INFO, "assert2.getText() = " + assert2.getText());
		
		logger.log(Level.INFO, "button1.isDisplayed() = " + button1.isDisplayed());
		logger.log(Level.INFO, "assert3.getText() = " + assert3.getText());
		
		logger.log(Level.INFO, "button2.isDisplayed() = " + button2.isDisplayed());
		logger.log(Level.INFO, "assert4.getText() = " + assert4.getText());
		
		logger.log(Level.INFO, "isThere(alphaXpath) = " + isThere(alphaXpath));
		logger.log(Level.INFO, "isThere(betaXpath) = " + isThere(betaXpath));
		logger.log(Level.INFO, "isThere(gammaXpath) = " + isThere(gammaXpath));
		logger.log(Level.INFO, "requestedUrl.isDisplayed() = " + requestedUrl.isDisplayed());
		
		assertTrue("The portlet should be displayed by this point, but it is not ... portletDisplayName.isDisplayed() = " + portletDisplayName.isDisplayed(), portletDisplayName.isDisplayed());
		assertTrue("The requestedUrl should be displayed by this point, but it is not ... requestedUrl.isDisplayed() = " + requestedUrl.isDisplayed(), requestedUrl.isDisplayed());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1100)
	public void step1() throws Exception {
		anchor1.click();
		Thread.sleep(250);
		
		logger.log(Level.INFO, "alpha.getText() = " + alpha.getText());
		logger.log(Level.INFO, "beta.getText() = " + beta.getText());
		logger.log(Level.INFO, "gamma.getText() = " + gamma.getText());
		logger.log(Level.INFO, "requestedUrl.getText() = " + requestedUrl.getText());
		logger.log(Level.INFO, "requestedUrl.getText().contains(assert1.getText()) = " + requestedUrl.getText().contains(assert1.getText()));
		assertTrue("The requestedUrl should contain '"+assert1.getText()+"', but it is '"+requestedUrl.getText()+"'", requestedUrl.getText().contains(assert1.getText()));
		
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "browser.getCurrentUrl().contains(assert1.getText()) = " + browser.getCurrentUrl().contains(assert1.getText()));
		assertTrue("The browser.getCurrentUrl() should contain '"+assert1.getText()+"', but it is '"+browser.getCurrentUrl()+"'", browser.getCurrentUrl().contains(assert1.getText()));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1200)
	public void step2() throws Exception {
		anchor2.click();
		Thread.sleep(250);
		
		logger.log(Level.INFO, "alpha.getText() = " + alpha.getText());
		logger.log(Level.INFO, "beta.getText() = " + beta.getText());
		logger.log(Level.INFO, "gamma.getText() = " + gamma.getText());
		logger.log(Level.INFO, "requestedUrl.getText() = " + requestedUrl.getText());
		logger.log(Level.INFO, "requestedUrl.getText().contains(assert2.getText()) = " + requestedUrl.getText().contains(assert2.getText()));
		assertTrue("The requestedUrl should contain '"+assert2.getText()+"', but it is '"+requestedUrl.getText()+"'", requestedUrl.getText().contains(assert2.getText()));
		
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "browser.getCurrentUrl().contains(assert2.getText()) = " + browser.getCurrentUrl().contains(assert2.getText()));
		assertTrue("The browser.getCurrentUrl() should contain '"+assert2.getText()+"', but it is '"+browser.getCurrentUrl()+"'", browser.getCurrentUrl().contains(assert2.getText()));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1300)
	public void step3() throws Exception {
		button1.click();
		Thread.sleep(250);
		
		logger.log(Level.INFO, "alpha.getText() = " + alpha.getText());
		logger.log(Level.INFO, "beta.getText() = " + beta.getText());
		logger.log(Level.INFO, "gamma.getText() = " + gamma.getText());
		logger.log(Level.INFO, "requestedUrl.getText() = " + requestedUrl.getText());
		logger.log(Level.INFO, "requestedUrl.getText().contains(assert3.getText()) = " + requestedUrl.getText().contains(assert3.getText()));
		assertTrue("The requestedUrl should contain '"+assert3.getText()+"', but it is '"+requestedUrl.getText()+"'", requestedUrl.getText().contains(assert3.getText()));
		
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "browser.getCurrentUrl().contains(assert3.getText()) = " + browser.getCurrentUrl().contains(assert3.getText()));
		assertTrue("The browser.getCurrentUrl() should contain '"+assert3.getText()+"', but it is '"+browser.getCurrentUrl()+"'", browser.getCurrentUrl().contains(assert3.getText()));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1400)
	public void step4() throws Exception {
		button2.click();
		Thread.sleep(250);
		
		logger.log(Level.INFO, "alpha.getText() = " + alpha.getText());
		logger.log(Level.INFO, "beta.getText() = " + beta.getText());
		logger.log(Level.INFO, "gamma.getText() = " + gamma.getText());
		logger.log(Level.INFO, "requestedUrl.getText() = " + requestedUrl.getText());
		logger.log(Level.INFO, "requestedUrl.getText().contains(assert4.getText()) = " + requestedUrl.getText().contains(assert4.getText()));
		assertTrue("The requestedUrl should contain '"+assert4.getText()+"', but it is '"+requestedUrl.getText()+"'", requestedUrl.getText().contains(assert4.getText()));
		
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "browser.getCurrentUrl().contains(assert4.getText()) = " + browser.getCurrentUrl().contains(assert4.getText()));
		assertTrue("The browser.getCurrentUrl() should contain '"+assert4.getText()+"', but it is '"+browser.getCurrentUrl()+"'", browser.getCurrentUrl().contains(assert4.getText()));
		
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
