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
public class FACES224PortletTest {
	
	private final static Logger logger = Logger.getLogger(FACES224PortletTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/signin";
	String url = "http://localhost:8080/web/bridge-issues/faces-224";

	@Drone
	WebDriver browser;
	
	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	
	private static final String formTagXpath = "//form[@method='post']";
	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	
	// <input id="A2535:httpGetButton" type="button" value="Click me to render view2.xhtml via HTTP GET">
	private static final String buttonXpath = "//input[contains(@value,'Click me to render view2.xhtml')]";
	@FindBy(xpath = buttonXpath)
	private WebElement button;
	
	// <div class="portlet-body" id="aui_3_4_0_1_500"> <div id="A2535" class="liferay-faces-bridge-body">This is view2.xhtml <br>viewParam1='' (if the issue is fixed, the value should be equal to 'abc') <br>viewParam2='' (if the issue is fixed, the value should be equal to 'xyz')</div> </div>
	private static final String view2DivXpath = "//div[@class='portlet-body']/div[1]";
	@FindBy(xpath = view2DivXpath)
	private WebElement view2Div;
	
	@Before
	public void beforeEachTest() {
		
//		browser.manage().deleteAllCookies();
//		logger.log(Level.INFO, "browser.manage().deleteAllCookies() ...");
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES224PortletViewMode() throws Exception {
		
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		
		logger.log(Level.INFO, "button.isDisplayed() = " + button.isDisplayed());
		
		assertTrue("button should be displayed but it is not, nothing found with buttonXpath = " + buttonXpath, button.isDisplayed());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(2000)
	public void View2() throws Exception {
		
		button.click();
		Thread.sleep(500);
		
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		
		logger.log(Level.INFO, "view2Div.isDisplayed() = " + view2Div.isDisplayed());
		logger.log(Level.INFO, "view2Div.getText() = " + view2Div.getText());
		
		logger.log(Level.INFO, "view2Div.getText().contains('viewParam1') = " + view2Div.getText().contains("viewParam1"));
		logger.log(Level.INFO, "view2Div.getText().contains('viewParam2') = " + view2Div.getText().contains("viewParam2"));
		Thread.sleep(500);
		
		assertTrue("view2Div.getText() should contain viewParam1='abc', but instead it contains '"+view2Div.getText()+"'", view2Div.getText().contains("viewParam1='abc'"));
		assertTrue("view2Div.getText() should contain viewParam2='xyz', but instead it contains '"+view2Div.getText()+"'", view2Div.getText().contains("viewParam2='xyz'"));
		
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
