/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

import java.util.Set;
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
public class Jsf2ExportPdfPortletTest extends TesterBase {

	// portlet topper
	// <a href="http://localhost:8080/group/bridge-demos/jsf2-pdf?p_p_id=1_WAR_jsf2e...let_customerId=1" target="_blank" id="yui_patched_dev_3_x_1_1383368924455_492">Export</a>
	private static final String brianExportXpath =
		"//a[contains(text(),'Export')]/../following-sibling::td[1][contains(text(),'Green')]/preceding-sibling::td[1]/a";
	private static final String LizExportXpath =
			"//a[contains(text(),'Export')]/../following-sibling::td[1][contains(text(),'Kessler')]/preceding-sibling::td[1]/a";
	private static final String richExportXpath =
			"//a[contains(text(),'Export')]/../following-sibling::td[1][contains(text(),'Shearer')]/preceding-sibling::td[1]/a";

	static final String url = baseUrl + webContext + "/jsf2-pdf";

	@FindBy(xpath = brianExportXpath)
	private WebElement brianExport;
	@FindBy(xpath = LizExportXpath)
	private WebElement LizExport;
	@FindBy(xpath = richExportXpath)
	private WebElement richExport;
	
	@Drone
	WebDriver browser;

	@Test
	@RunAsClient
	@InSequence(1000)
	public void renderViewMode() throws Exception {

		signIn(browser);
		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		getPortletDisplayName();
		logger.log(Level.INFO, "displayName.getText() = " + displayName.getText());

		assertTrue("customerPortletDisplayName displayName.isDisplayed()", displayName.isDisplayed());
		
		if (isThere(browser, brianExportXpath)) {
			logger.log(Level.INFO, "isThere(brianExportXpath) = " + isThere(browser, brianExportXpath));
			richExport.click();
			Thread.sleep(1000);
			
			Set<String> handles = browser.getWindowHandles();
			for (String window : handles) {
                browser.switchTo().window(window);
                logger.log(Level.INFO, "window = " + window);
                logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
    			logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
    			logger.log(Level.INFO, "browser.getPageSource() = " + browser.getPageSource());
    			logger.log(Level.INFO, " -------------------------------------------------- ");
            }
			
			logger.log(Level.INFO, "browser.getPageSource().contains('Shearer') = " + browser.getPageSource().contains("Shearer"));
			logger.log(Level.INFO, " -------------------------------------------------- ");
			
			assertTrue("The pdf document should contain 'Shearer', but instead it contains: " + browser.getPageSource(),
				browser.getPageSource().contains("Shearer")
			);
			
		}

	}

}
