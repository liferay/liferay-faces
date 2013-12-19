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
public class FACES1638PortletTest extends TesterBase {

	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";

	private static final String formTagXpath = "//form[@method='post']";

	private static final String firstByItemIdXpath = "//a[contains(@href,'portlet_itemId') and text()='First-Item']";
	private static final String secondByItemIdXpath = "//a[contains(@href,'portlet_itemId') and text()='Second-Item']";
	private static final String thirdByItemIdXpath = "//a[contains(@href,'portlet_itemId') and text()='Third-Item']";
	
	private static final String firstByItemNameXpath = "//a[contains(@href,'portlet_First-Item') and text()='First-Item']";
	private static final String secondByItemNameXpath = "//a[contains(@href,'portlet_Second-Item') and text()='Second-Item']";
	private static final String thirdByItemNameXpath = "//a[contains(@href,'portlet_Third-Item') and text()='Third-Item']";
	
	// http://localhost:8080/web/bridge-issues/faces-1638?p_p_id=1_WAR_FACES1638portlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_1_WAR_FACES1638portlet__facesViewIdRender=%2Fviews%2Fview1.xhtml&_1_WAR_FACES1638portlet_itemId=3
	// http://localhost:8080/web/bridge-issues/faces-1638?p_p_id=1_WAR_FACES1638portlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_1_WAR_FACES1638portlet__facesViewIdRender=%2Fviews%2Fview1.xhtml&_1_WAR_FACES1638portlet_First-Item=1	

	static final String url = baseUrl + "/web/bridge-issues/faces-1638";
	static final String ITEM_ID = "itemId=";
	static final String ITEM_NAME = "-Item=";

	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	
	@FindBy(xpath = firstByItemIdXpath)
	private WebElement firstByItemId;
	@FindBy(xpath = secondByItemIdXpath)
	private WebElement secondByItemId;
	@FindBy(xpath = thirdByItemIdXpath)
	private WebElement thirdByItemId;
	
	@FindBy(xpath = firstByItemNameXpath)
	private WebElement firstByItemName;
	@FindBy(xpath = secondByItemNameXpath)
	private WebElement secondByItemName;
	@FindBy(xpath = thirdByItemNameXpath)
	private WebElement thirdByItemName;
	
	@Drone
	WebDriver browser;

	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES1638Test() throws Exception {

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		logger.log(Level.INFO, "formTag.getText() = " + formTag.getText());

		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue("formTag.isDisplayed()", formTag.isDisplayed());
		
		
		int firstItemIndex = firstByItemId.getAttribute("href").indexOf(ITEM_ID) + ITEM_ID.length();
		String firstItemID = firstByItemId.getAttribute("href").substring(firstItemIndex);
		logger.log(Level.INFO, "firstItemID = " + firstItemID);
		assertTrue("firstItemID should equal 1, but it does not.", firstItemID.equals("1"));
		
		int secondItemIndex = secondByItemId.getAttribute("href").indexOf(ITEM_ID) + ITEM_ID.length();
		String secondItemID = secondByItemId.getAttribute("href").substring(secondItemIndex);
		logger.log(Level.INFO, "secondItemID = " + secondItemID);
		assertTrue("secondItemID should equal 2, but it does not", secondItemID.equals("2"));
		
		int thirdItemIndex = thirdByItemId.getAttribute("href").indexOf(ITEM_ID) + ITEM_ID.length();
		String thirdItemID = thirdByItemId.getAttribute("href").substring(thirdItemIndex);
		logger.log(Level.INFO, "thirdItemID = " + thirdItemID);
		assertTrue("thirdItemID should equal 3, but it does not", thirdItemID.equals("3"));
		
		
		firstItemIndex = firstByItemName.getAttribute("href").indexOf(ITEM_NAME) + ITEM_NAME.length();
		String firstItemName = firstByItemName.getAttribute("href").substring(firstItemIndex);
		logger.log(Level.INFO, "firstItemName = " + firstItemName);
		assertTrue("firstItemName should equal 1, but it does not.", firstItemName.equals("1"));
		
		secondItemIndex = secondByItemName.getAttribute("href").indexOf(ITEM_NAME) + ITEM_NAME.length();
		String secondItemName = secondByItemName.getAttribute("href").substring(secondItemIndex);
		logger.log(Level.INFO, "secondItemName = " + secondItemName);
		assertTrue("firstItemName should equal 2, but it does not", secondItemName.equals("2"));
		
		thirdItemIndex = thirdByItemName.getAttribute("href").indexOf(ITEM_NAME) + ITEM_NAME.length();
		String thirdItemName = thirdByItemName.getAttribute("href").substring(thirdItemIndex);
		logger.log(Level.INFO, "thirdItemName = " + thirdItemName);
		assertTrue("firstItemName should equal 3, but it does not", thirdItemName.equals("3"));

	}

}
