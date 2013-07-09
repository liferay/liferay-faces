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
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

import com.liferay.faces.test.util.TesterBase;


/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class FACES1638PortletTest extends TesterBase {

	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";

	private static final String formTagXpath = "//form[@method='post']";

	private static final String firstItemURLXpath = "//a[text()='First Item']";
	private static final String secondItemURLXpath = "//a[text()='Second Item']";
	private static final String thirdItemURLXpath = "//a[text()='Third Item']";

	static final String url = baseUrl + "/web/bridge-issues/faces-1638";
	static final String ITEM_ID = "itemId=";

	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	
	@FindBy(xpath = firstItemURLXpath)
	private WebElement firstItemURL;
	@FindBy(xpath = secondItemURLXpath)
	private WebElement secondItemURL;
	@FindBy(xpath = thirdItemURLXpath)
	private WebElement thirdItemURL;

	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES1638ItemURLIDsTest() throws Exception {

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		logger.log(Level.INFO, "formTag.getText() = " + formTag.getText());

		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue("formTag.isDisplayed()", formTag.isDisplayed());

		int firstItemIndex = firstItemURL.getAttribute("href").indexOf(ITEM_ID) + ITEM_ID.length();
		String firstItemID = firstItemURL.getAttribute("href").substring(firstItemIndex);
		assertTrue("firstItemID should equal 1, but it does not.", firstItemID.equals("1"));
		logger.log(Level.INFO, "firstItemID = " + firstItemID);
		int secondItemIndex = secondItemURL.getAttribute("href").indexOf(ITEM_ID) + ITEM_ID.length();
		String secondItemID = secondItemURL.getAttribute("href").substring(secondItemIndex);
		assertTrue("secondItemID should equal 2, but it does not", secondItemID.equals("2"));
		logger.log(Level.INFO, "secondItemID = " + secondItemID);
		int thirdItemIndex = thirdItemURL.getAttribute("href").indexOf(ITEM_ID) + ITEM_ID.length();
		String thirdItemID = thirdItemURL.getAttribute("href").substring(thirdItemIndex);
		assertTrue("thirdItemID should equal 3, but it does not", thirdItemID.equals("3"));
		logger.log(Level.INFO, "thirdItemID = " + thirdItemID);
        
	}

}
