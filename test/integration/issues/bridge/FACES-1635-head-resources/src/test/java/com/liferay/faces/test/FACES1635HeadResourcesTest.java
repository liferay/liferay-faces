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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.liferay.faces.test.util.TesterBase;

import static org.junit.Assert.assertTrue;

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
	
	private HashMap<String, Integer> mapOfScriptsInHead = new HashMap<String, Integer>();
	private HashMap<String, Integer> mapOfScriptsInBody = new HashMap<String, Integer>();
	
	private HashMap<String, Integer> mapOfStyleSheetsInHead = new HashMap<String, Integer>();
	private HashMap<String, Integer> mapOfStyleSheetsInBody = new HashMap<String, Integer>();

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
		
		List<WebElement> scriptsInHead = browser.findElements(By.xpath("//head/script"));
		logger.log(Level.INFO, "scriptsInHead.size() = " + scriptsInHead.size());
		populateMap(scriptsInHead, mapOfScriptsInHead, "src");
		
		for (Map.Entry<String, Integer> entry : mapOfScriptsInHead.entrySet()) {
		    String key = entry.getKey();
		    int value = (entry.getValue()).intValue();
		    if (value > 1) {
		    	logger.log(Level.INFO, "scriptsInHead key = " + key + " occurs " + value + " times.");
		    	assertTrue("scriptsInHead should only occur once, but " + key + " occurs " + value + " times.", false);
		    }
		}
		
		List<WebElement> scriptsInBody = browser.findElements(By.xpath("//body/script"));
		logger.log(Level.INFO, "scriptsInBody.size() = " + scriptsInBody.size());
		populateMap(scriptsInBody, mapOfScriptsInBody, "src");
		
		for (Map.Entry<String, Integer> entry : mapOfScriptsInBody.entrySet()) {
		    String bodyKey = entry.getKey();
		    int value = (entry.getValue()).intValue();
		    if (value > 1) {
		    	logger.log(Level.INFO, "scriptsInBody " + bodyKey + " occurs " + value + " times.");
		    	assertTrue("scriptsInBody should only occur once, but " + bodyKey + " occurs " + value + " times.", false);
		    }
		    
		    Integer headScriptAlsoInBody = mapOfScriptsInHead.get(bodyKey);
		    if (headScriptAlsoInBody == null) {
		    	logger.log(Level.INFO, "headScriptInBody == null ... this is good.");
		    } else {
		    	logger.log(Level.INFO, "headScriptInBody != null ... this is bad. " + bodyKey + " occurs in both the head and the body");
		    	assertTrue("headScriptInBody != null ... this is bad. " + bodyKey + " occurs in both the head and the body", false);
		    }
		}
		
		List<WebElement> styleSheetsInHead = browser.findElements(By.xpath("//head/link"));
		logger.log(Level.INFO, "styleSheetsInHead.size() = " + styleSheetsInHead.size());
		populateMap(styleSheetsInHead, mapOfStyleSheetsInHead, "href");
		
		for (Map.Entry<String, Integer> entry : mapOfStyleSheetsInHead.entrySet()) {
		    String key = entry.getKey();
		    int value = (entry.getValue()).intValue();
		    if (value > 1) {
		    	logger.log(Level.INFO, "StyleSheetsInHead " + key + " occurs " + value + " times.");
		    	assertTrue("StyleSheetsInHead should only occur once, but " + key + " occurs " + value + " times.", false);
		    }
		}
		
		List<WebElement> styleSheetsInBody = browser.findElements(By.xpath("//body/link"));
		logger.log(Level.INFO, "styleSheetsInBody.size() = " + styleSheetsInBody.size());
		populateMap(styleSheetsInHead, mapOfStyleSheetsInBody, "href");
		
		for (Map.Entry<String, Integer> entry : mapOfStyleSheetsInBody.entrySet()) {
		    String bodyKey = entry.getKey();
		    int value = (entry.getValue()).intValue();
		    if (value > 1) {
		    	logger.log(Level.INFO, "scriptsInBody " + bodyKey + " occurs " + value + " times.");
		    	assertTrue("scriptsInBody should only occur once, but " + bodyKey + " occurs " + value + " times.", false);
		    }
		    
		    Integer styleSheetAlsoInBody = mapOfStyleSheetsInHead.get(bodyKey);
		    if (styleSheetAlsoInBody == null) {
		    	logger.log(Level.INFO, "styleSheetAlsoInBody == null ... this is good.");
		    } else {
		    	logger.log(Level.INFO, "styleSheetAlsoInBody != null ... this is bad. " + bodyKey + " occurs in both the head and the body");
		    	assertTrue("styleSheetAlsoInBody != null ... this is bad. " + bodyKey + " occurs in both the head and the body", false);
		    }
		}

	}
	
	public void populateMap(List<WebElement> webElements, Map<String, Integer> map, String attribute) {
		for (WebElement script: webElements) {
		    String url = script.getAttribute(attribute);
		    if (url == null) {
		    	logger.log(Level.INFO, "populateMap: " + attribute + " == null");
			} else {
				if ("".equals(url)) {
					// logger.log(Level.INFO, "populateMap: ignoring inline script tag");
				} else {
					// logger.log(Level.INFO, "src = " + src);
					String[] slashTokens = url.split("/");
					if (slashTokens.length > 0) {
						String token = slashTokens[(slashTokens.length - 1)];
						String[] questTokens = token.split("\\?");
						token = questTokens[0];

						if (questTokens.length > 1) {
							if (questTokens[1].contains("resource=")) {
								// logger.log(Level.INFO, "questTokens[1] = " + questTokens[1]);
								String[] resourceTokens = questTokens[1]
										.split("resource=");
								if (resourceTokens.length > 1) {
									String[] ampTokens = resourceTokens[1].split("\\&");
									token = ampTokens[0];
								} else {
									token = resourceTokens[0];
								}
								logger.log(Level.INFO, "token = " + token);
							}
						}

						int c = 0;
						Integer count = map.get(token);
						if (count == null) {
							count = new Integer(1);
						} else {
							c = count.intValue() + 1;
							count = new Integer(c);
						}
						map.put(token, count);
						// logger.log(Level.INFO, "populateMap: token = " + token);
					}
				}
			}
		}
	}
}
