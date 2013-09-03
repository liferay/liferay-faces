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

import java.util.HashMap;
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
public class FACES1618PortletTest extends TesterBase {

	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";

	private static final String formTagXpath = "//form[@method='post']";
	
	// <span id="A3981:j_idt3:headResourceIds">
	private static final String headResourceIdsSpanXpath = "//span[contains(@id,':headResourceIds')]";
	
	// <input id="A3981:j_idt3:_t11" name="A3981:j_idt3:_t11" type="submit" value="go to next view">
	private static final String submitButtonXpath =	"//input[contains(@value,'go to ')]";

	static final String url =  baseUrl + "/web/bridge-issues/faces-1618";

	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	@FindBy(xpath = headResourceIdsSpanXpath)
	private WebElement headResourceIdsSpan;
	@FindBy(xpath = submitButtonXpath)
	private WebElement submitButton;
	
	protected StringBuilder headResourceIds;
	protected String headResourceIdsString;
	protected static HashMap<String, String> headResourceIdsMap = new HashMap<String, String>();

	@Test
	@RunAsClient
	@InSequence(1000)
	public void portletViewMode() throws Exception {

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		
		logger.log(Level.INFO, "headResourceIdsSpan.getText() = " + headResourceIdsSpan.getText());
		headResourceIds = new StringBuilder();
		headResourceIds.append(headResourceIdsSpan.getText());
		headResourceIdsString = headResourceIds.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ","");
		logger.log(Level.INFO, "headResourceIdsString = " + headResourceIdsString);
		
		String[] resourceIds = headResourceIdsString.split(",");
		logger.log(Level.INFO, "resourceIds.length = " + resourceIds.length);
		
		for (int i=0; i < resourceIds.length; i++) {
			headResourceIdsMap.put(resourceIds[i], "1");
		}

		logger.log(Level.INFO, "submitButton.isDisplayed() = " + submitButton.isDisplayed());

		assertTrue("portletDisplayName.isDisplayed()", portletDisplayName.isDisplayed());
		assertTrue(
			"There should be more than 1 headResourceIds, but resourceIds.length == " + resourceIds.length, 
			(resourceIds.length > 1)
		);
		assertTrue("submitButton should be displayed, but it is not", submitButton.isDisplayed());

	}

	@Test
	@RunAsClient
	@InSequence(2000)
	public void View2ViaAjax() throws Exception {
		
		boolean resourceIdsAreTheSame = false;

		submitButton.click();
		Thread.sleep(250);

		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		
		logger.log(Level.INFO, "headResourceIdsSpan.getText() = " + headResourceIdsSpan.getText());
		headResourceIds = new StringBuilder();
		headResourceIds.append(headResourceIdsSpan.getText());
		headResourceIdsString = headResourceIds.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ","");
		logger.log(Level.INFO, "headResourceIdsString = " + headResourceIdsString);
		
		String[] resourceIds = headResourceIdsString.split(",");
		logger.log(Level.INFO, "resourceIds.length = " + resourceIds.length);
		
		// first check that the lengths are the same
		assertTrue(
			"previous number of resources ids and currnet number of resource ids should match, " +
				"but the current headResourceIdsMap.size() = " + headResourceIdsMap.size() +
				", while the previous resourceIds.length = " + resourceIds.length,
			(headResourceIdsMap.size() == resourceIds.length)
		);
		
		// check that none have changed
		for (int i=0; i < resourceIds.length; i++) {
			// TODO check them
			if (headResourceIdsMap.get(resourceIds[i]) == null) {
				logger.log(Level.INFO, "never seen this resourceId before resourceIds[i] = " + resourceIds[i]);
				resourceIdsAreTheSame = false;
				break;
			} else {
				logger.log(Level.INFO, "headResourceIdsMap.get("+resourceIds[i]+") == " + headResourceIdsMap.get(resourceIds[i]));
				resourceIdsAreTheSame = true;
			}
		}
		
		logger.log(Level.INFO, "resourceIdsAreTheSame = " + resourceIdsAreTheSame);
		
		assertTrue("submitButton should be displayed, but it is not", submitButton.isDisplayed());

	}

}
