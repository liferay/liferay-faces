/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.test;

import static org.junit.Assert.assertTrue;

import java.util.logging.Level;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.liferay.faces.test.util.TesterBase;


/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class FACES1470PortletTest extends TesterBase {

	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";

	private static final String formTagXpath = "//form[@method='post']";

	// <input id="A5773:f1:j_idt18" type="submit" name="A5773:f1:j_idt18" value="Click me to navigate to view2.xhtml
	// via Ajax" onclick="mojarra.ab(this,event,'action','@form',0);return false">
	private static final String ajaxButtonXpath =
		"//input[contains(@value,'Click me to navigate to view2.xhtml via Ajax')]";

	// <input type="submit" name="A5773:f1:j_idt19" value="Click me to navigate to view2.xhtml via non-Ajax (full
	// postback)" id="aui_3_4_0_1_533">
	private static final String fullPostBackButtonXpath =
		"//input[contains(@value,'Click me to navigate to view2.xhtml via non-Ajax')]";

	private static final String ajaxButton2Xpath =
		"//input[contains(@value,'Click me to navigate to view1.xhtml via Ajax')]";

	private static final String fullPostBackButton2Xpath =
		"//input[contains(@value,'Click me to navigate to view1.xhtml via non-Ajax')]";

	static final String url = baseUrl + "/web/bridge-issues/faces-1470";

	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	@FindBy(xpath = ajaxButtonXpath)
	private WebElement ajaxButton;
	@FindBy(xpath = fullPostBackButtonXpath)
	private WebElement fullPostBackButton;
	@FindBy(xpath = ajaxButton2Xpath)
	private WebElement ajaxButton2;
	@FindBy(xpath = fullPostBackButton2Xpath)
	private WebElement fullPostBackButton2;
	
	@Drone
	WebDriver browser;

	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES1470PortletViewMode() throws Exception {

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());

		logger.log(Level.INFO, "ajaxButton.isDisplayed() = " + ajaxButton.isDisplayed());
		logger.log(Level.INFO, "fullPostBackButton.isDisplayed() = " + fullPostBackButton.isDisplayed());

		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue("ajaxButton should be displayed, but it is not", ajaxButton.isDisplayed());
		assertTrue("fullPostBackButton should be displayed, but it is not", fullPostBackButton.isDisplayed());

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

}
