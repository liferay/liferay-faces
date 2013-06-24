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

import java.util.logging.Level;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.openqa.selenium.WebElement;

import com.liferay.faces.test.util.TesterBase;


/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class FACES257PortletTest extends TesterBase {

	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";

	private static final String anchor1Xpath = "//a[contains(text(), 'alpha=1 beta=2 gamma=0')]";

	private static final String assert1Xpath = anchor1Xpath +
		"/following-sibling::*[1]/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";

	// alpha=1 beta=2 gamma=3
	private static final String anchor2Xpath = "//a[contains(text(), 'alpha=1 beta=2 gamma=3')]";

	private static final String assert2Xpath = anchor2Xpath +
		"/following-sibling::*[1]/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";

	// alpha=1 beta=2 gamma=3
	private static final String button1Xpath = "//input[@type='button' and contains(@value, 'alpha=4 beta=5 gamma=0')]";

	private static final String assert3Xpath = button1Xpath +
		"/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";

	private static final String button2Xpath = "//input[@type='button' and contains(@value, 'alpha=4 beta=5 gamma=6')]";

	private static final String assert4Xpath = button2Xpath +
		"/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";

	private static final String alphaXpath = "//span[contains(@id, ':alpha')]";

	private static final String betaXpath = "//span[contains(@id, ':beta')]";

	private static final String gammaXpath = "//span[contains(@id, ':gamma')]";

	private static final String requestedUrlXpath = "//span[contains(@id, ':requestedURL')]";

	static final String url = baseUrl + "/web/portal-issues/faces-257";

	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	@FindBy(xpath = anchor1Xpath)
	private WebElement anchor1;
	@FindBy(xpath = assert1Xpath)
	private WebElement assert1;
	@FindBy(xpath = anchor2Xpath)
	private WebElement anchor2;
	@FindBy(xpath = assert2Xpath)
	private WebElement assert2;
	@FindBy(xpath = button1Xpath)
	private WebElement button1;
	@FindBy(xpath = assert3Xpath)
	private WebElement assert3;
	@FindBy(xpath = button2Xpath)
	private WebElement button2;
	@FindBy(xpath = assert4Xpath)
	private WebElement assert4;
	@FindBy(xpath = alphaXpath)
	private WebElement alpha;
	@FindBy(xpath = betaXpath)
	private WebElement beta;
	@FindBy(xpath = gammaXpath)
	private WebElement gamma;
	@FindBy(xpath = requestedUrlXpath)
	private WebElement requestedUrl;

	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES257PortletViewMode() throws Exception {

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
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

		assertTrue(
			"The portlet should be displayed by this point, but it is not ... portletDisplayName.isDisplayed() = " +
			portletDisplayName.isDisplayed(), portletDisplayName.isDisplayed());
		assertTrue(
			"The requestedUrl should be displayed by this point, but it is not ... requestedUrl.isDisplayed() = " +
			requestedUrl.isDisplayed(), requestedUrl.isDisplayed());

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
		logger.log(Level.INFO,
			"requestedUrl.getText().contains(assert1.getText()) = " +
			requestedUrl.getText().contains(assert1.getText()));
		assertTrue("The requestedUrl should contain '" + assert1.getText() + "', but it is '" + requestedUrl.getText() +
			"'", requestedUrl.getText().contains(assert1.getText()));

		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO,
			"browser.getCurrentUrl().contains(assert1.getText()) = " +
			browser.getCurrentUrl().contains(assert1.getText()));
		assertTrue("The browser.getCurrentUrl() should contain '" + assert1.getText() + "', but it is '" +
			browser.getCurrentUrl() + "'", browser.getCurrentUrl().contains(assert1.getText()));

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
		logger.log(Level.INFO,
			"requestedUrl.getText().contains(assert2.getText()) = " +
			requestedUrl.getText().contains(assert2.getText()));
		assertTrue("The requestedUrl should contain '" + assert2.getText() + "', but it is '" + requestedUrl.getText() +
			"'", requestedUrl.getText().contains(assert2.getText()));

		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO,
			"browser.getCurrentUrl().contains(assert2.getText()) = " +
			browser.getCurrentUrl().contains(assert2.getText()));
		assertTrue("The browser.getCurrentUrl() should contain '" + assert2.getText() + "', but it is '" +
			browser.getCurrentUrl() + "'", browser.getCurrentUrl().contains(assert2.getText()));

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
		logger.log(Level.INFO,
			"requestedUrl.getText().contains(assert3.getText()) = " +
			requestedUrl.getText().contains(assert3.getText()));
		assertTrue("The requestedUrl should contain '" + assert3.getText() + "', but it is '" + requestedUrl.getText() +
			"'", requestedUrl.getText().contains(assert3.getText()));

		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO,
			"browser.getCurrentUrl().contains(assert3.getText()) = " +
			browser.getCurrentUrl().contains(assert3.getText()));
		assertTrue("The browser.getCurrentUrl() should contain '" + assert3.getText() + "', but it is '" +
			browser.getCurrentUrl() + "'", browser.getCurrentUrl().contains(assert3.getText()));

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
		logger.log(Level.INFO,
			"requestedUrl.getText().contains(assert4.getText()) = " +
			requestedUrl.getText().contains(assert4.getText()));
		assertTrue("The requestedUrl should contain '" + assert4.getText() + "', but it is '" + requestedUrl.getText() +
			"'", requestedUrl.getText().contains(assert4.getText()));

		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO,
			"browser.getCurrentUrl().contains(assert4.getText()) = " +
			browser.getCurrentUrl().contains(assert4.getText()));
		assertTrue("The browser.getCurrentUrl() should contain '" + assert4.getText() + "', but it is '" +
			browser.getCurrentUrl() + "'", browser.getCurrentUrl().contains(assert4.getText()));

	}

}
