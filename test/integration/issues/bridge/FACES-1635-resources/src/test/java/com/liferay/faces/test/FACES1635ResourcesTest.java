/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
import java.util.List;
import java.util.logging.Level;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.liferay.faces.bridge.renderkit.html_basic.internal.HeadResource;
import com.liferay.faces.test.util.TesterBase;


/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class FACES1635ResourcesTest extends TesterBase {

	private static final String textarea1Xpath = "//textarea[contains(@id,':comments1:inputText')]";

	static final String url = baseUrl + webContext + "/faces-1635?p_p_parallel=0";

	@FindBy(xpath = textarea1Xpath)
	private WebElement textarea1;

	@Drone
	WebDriver browser;

	public void checkResourcesForDuplicates(ArrayList<HeadResource> resources, String whichResources) {
		int numberOfResources = resources.size();

		for (int i = 0; i < numberOfResources; i++) {
			HeadResource resource1 = resources.get(i);

			for (int j = i + 1; j < numberOfResources; j++) {
				HeadResource resource2 = resources.get(j);

				if (!resource2.isDuplicate() && resource1.equals(resource2)) {
					logger.log(Level.INFO,
						"checkResourcesForDuplicates: " + whichResources + " occur more than once: type = " +
						resource1.getType() + " url = " + resource1.getURL());
					resource2.setDuplicate(true);
					assertTrue(whichResources + " occur more than once: type = " + resource1.getType() + " url = " +
						resource1.getURL(), false);

					break;
				}
			}
		}
	}

	public ArrayList<HeadResource> convertToHeadResources(List<WebElement> webElements, String attribute) {
		ArrayList<HeadResource> resources = new ArrayList<HeadResource>();

		for (WebElement webElement : webElements) {
			String url = webElement.getAttribute(attribute);
			String type = ("src".equals(attribute)) ? "script" : "link";

			if (url != null) {

				if ("".equals(url)) {
					// logger.log(Level.INFO, "convertToHeadResources: ignoring inline " + type + " ...");
				}
				else {

					if ("link".equals(type)) {
						String rel = webElement.getAttribute("rel");

						if (rel == null) {
							logger.log(Level.INFO, "rel == null, type = " + type);
						}
						else {

							if ("stylesheet".equals(rel)) {
//                              logger.log(Level.INFO, "convertToHeadResources: url = " + url);
								resources.add(new HeadResource(type, url));
							}
						}
					}
					else { // SCRIPT.equals(type)

						if (url.contains("jsf.js")) {
							logger.log(Level.INFO, "convertToHeadResources: type = " + type + " url = " + url);
						}

						resources.add(new HeadResource(type, url));
					}
				}
			}
		}

		return resources;
	}

	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES1635ResourcesTest1() throws Exception {

		if ("pluto".equals(portal)) {
			signIn(browser);
		}

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());

		getPortletDisplayName();

		if (isThere(browser, displayNameXpath)) {

			logger.log(Level.INFO, "displayName.getText() = " + displayName.getText());
			waitForElement(browser, displayNameXpath);

			// check that scriptsInHead only occur once
			List<WebElement> scriptsInHead = browser.findElements(By.xpath("//head/script"));
			ArrayList<HeadResource> scriptResourcesInHead = convertToHeadResources(scriptsInHead, "src");
			logger.log(Level.INFO, "scriptResourcesInHead.size() = " + scriptResourcesInHead.size());
			checkResourcesForDuplicates(scriptResourcesInHead, "scriptResourcesInHead");

			// check that scriptsInBody only occur once
			List<WebElement> scriptsInBody = browser.findElements(By.xpath("//body/script"));
			ArrayList<HeadResource> scriptResourcesInBody = convertToHeadResources(scriptsInBody, "src");
			logger.log(Level.INFO, "scriptResourcesInBody.size() = " + scriptResourcesInBody.size());
			checkResourcesForDuplicates(scriptResourcesInBody, "scriptResourcesInBody");

			// check that styleSheetsInHead only occur once
			List<WebElement> styleSheetsInHead = browser.findElements(By.xpath("//head/link"));
			ArrayList<HeadResource> styleSheetResourcesInHead = convertToHeadResources(styleSheetsInHead, "href");
			logger.log(Level.INFO, "styleSheetResourcesInHead.size() = " + styleSheetResourcesInHead.size());
			checkResourcesForDuplicates(styleSheetResourcesInHead, "styleSheetResourcesInHead");

			// check that styleSheetsInBody only occur once
			List<WebElement> styleSheetsInBody = browser.findElements(By.xpath("//body/link"));
			ArrayList<HeadResource> styleSheetResourcesInBody = convertToHeadResources(styleSheetsInBody, "href");
			logger.log(Level.INFO, "styleSheetResourcesInBody.size() = " + styleSheetResourcesInBody.size());
			checkResourcesForDuplicates(styleSheetResourcesInBody, "styleSheetResourcesInBody");

			// check that scripts only occur once
			List<WebElement> scripts = browser.findElements(By.xpath("//script"));
			ArrayList<HeadResource> scriptResources = convertToHeadResources(scripts, "src");
			logger.log(Level.INFO, "scriptResources.size() = " + scriptResources.size());
			checkResourcesForDuplicates(scriptResources, "scriptResources");

			// check that styleSheets only occur once
			List<WebElement> styleSheets = browser.findElements(By.xpath("//link"));
			ArrayList<HeadResource> styleSheetResources = convertToHeadResources(styleSheets, "href");
			logger.log(Level.INFO, "styleSheetResources.size() = " + styleSheetResources.size());
			checkResourcesForDuplicates(styleSheetResources, "styleSheetResources");

		}
		else {
			logger.log(Level.SEVERE, "ERROR: Perhaps the portlets for this tester were not deployed ...");
			assertTrue("No portlets found on the page.  Perhaps the portlets for this tester were not deployed.",
				false);
		}
	}
}
