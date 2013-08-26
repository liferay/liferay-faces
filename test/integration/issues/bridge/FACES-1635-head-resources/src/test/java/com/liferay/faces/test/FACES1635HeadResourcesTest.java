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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.liferay.faces.test.util.TesterBase;

import static org.junit.Assert.assertTrue;

import com.liferay.faces.bridge.renderkit.html_basic.HeadResource;

/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class FACES1635HeadResourcesTest extends TesterBase {

	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";

	private static final String textarea1Xpath = "//textarea[contains(@id,':comments1:inputText')]";

	static final String url = baseUrl + "/web/bridge-issues/faces-1635";

	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	
	@FindBy(xpath = textarea1Xpath)
	private WebElement textarea1;
	
	private HashMap<HeadResource, Integer> mapOfScriptsInHead = new HashMap<HeadResource, Integer>();
	private HashMap<HeadResource, Integer> mapOfScriptsInBody = new HashMap<HeadResource, Integer>();
	
	private HashMap<HeadResource, Integer> mapOfStyleSheetsInHead = new HashMap<HeadResource, Integer>();
	private HashMap<HeadResource, Integer> mapOfStyleSheetsInBody = new HashMap<HeadResource, Integer>();

	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES1635HeadResourcesTest1() throws Exception {

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		
		waitForElement(portletDisplayNameXpath);
		
		// gather information
		List<WebElement> scriptsInHead = browser.findElements(By.xpath("//head/script"));
		ArrayList<HeadResource> scriptResourcesInHead = convertToHeadResources(scriptsInHead);
		logger.log(Level.INFO, "scriptResourcesInHead.size() = " + scriptResourcesInHead.size());
		checkResourcesForDuplicates(scriptResourcesInHead, "scriptResourcesInHead");
		
		List<WebElement> scriptsInBody = browser.findElements(By.xpath("//body/script"));
		ArrayList<HeadResource> scriptResourcesInBody = convertToHeadResources(scriptsInBody);
		logger.log(Level.INFO, "scriptResourcesInBody.size() = " + scriptResourcesInBody.size());
		checkResourcesForDuplicates(scriptResourcesInBody, "scriptResourcesInBody");
		
		List<WebElement> styleSheetsInHead = browser.findElements(By.xpath("//head/link"));
		ArrayList<HeadResource> styleSheetResourcesInHead = convertToHeadResources(styleSheetsInHead);
		logger.log(Level.INFO, "styleSheetResourcesInHead.size() = " + styleSheetResourcesInHead.size());
		checkResourcesForDuplicates(styleSheetResourcesInHead, "styleSheetResourcesInHead");
		
		List<WebElement> styleSheetsInBody = browser.findElements(By.xpath("//body/link"));
		ArrayList<HeadResource> styleSheetResourcesInBody = convertToHeadResources(styleSheetsInBody);
		logger.log(Level.INFO, "styleSheetResourcesInBody.size() = " + styleSheetResourcesInBody.size());
		checkResourcesForDuplicates(styleSheetResourcesInBody, "styleSheetResourcesInBody");
		
		List<WebElement> scripts = browser.findElements(By.xpath("//script"));
		ArrayList<HeadResource> scriptResources = convertToHeadResources(scripts);
		logger.log(Level.INFO, "scriptResources.size() = " + scriptResources.size());
		checkResourcesForDuplicates(scriptResources, "scriptResources");
		
		List<WebElement> styleSheets = browser.findElements(By.xpath("//link"));
		ArrayList<HeadResource> styleSheetResources = convertToHeadResources(styleSheets);
		logger.log(Level.INFO, "styleSheetResources.size() = " + styleSheetResources.size());
		checkResourcesForDuplicates(styleSheetResources, "styleSheetResources");
	}
	
	public ArrayList<HeadResource> convertToHeadResources(List<WebElement> resources) {
		ArrayList<HeadResource> list = new ArrayList<HeadResource>();
		for(WebElement webElement : resources) {
			String url = webElement.getAttribute("src");
			if (url == null) {
				logger.log(Level.INFO, "ignoring inline script");
			} else {
				list.add(new HeadResource("script", url));
			}	
		}
		return list;
	}
	
	public void checkResourcesForDuplicates(ArrayList<HeadResource> resources, String whichResources) {
		int totalHeadResources = resources.size();
		for (int i = 0; i < totalHeadResources; i++) {
			HeadResource resource1 = resources.get(i);
			for (int j = i + 1; j < totalHeadResources; j++) {
				HeadResource resource2 = resources.get(j);
				if (!resource2.isDuplicate() && resource1.equals(resource2)) {
					logger.log(Level.INFO, "checkResourcesForDuplicates: " + whichResources + " occur more than once");
					resource2.setDuplicate(true);
					break;
				}
			}
		}
	}
}
