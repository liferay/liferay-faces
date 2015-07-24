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
//J-

import static org.junit.Assert.assertTrue;

import java.util.logging.Level;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.liferay.faces.test.util.TesterBase;

/**
 * @author	Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class Jsf2EventsTest extends TesterBase {

	// portlet topper for customer
//	private static final String customerPortletDisplayNameXpath = "(//header[@class='portlet-topper']/h1/span)[1]";
	private static final String briansInputXpath =
		"//input[@type='image']/../following-sibling::td[1][contains(text(),'1')]/../td[1]/input";
	private static final String briansFirstNameXpath =
		"//input[@type='image']/../following-sibling::td[1][contains(text(),'1')]/following-sibling::*[1]";
	private static final String briansLastNameXpath =
		"//input[@type='image']/../following-sibling::td[1][contains(text(),'1')]/following-sibling::*[1]/following-sibling::*[1]";
	private static final String lizsInputXpath =
		"//input[@type='image']/../following-sibling::td[1][contains(text(),'2')]/../td[1]/input";
	private static final String lizsFirstNameXpath =
		"//input[@type='image']/../following-sibling::td[1][contains(text(),'2')]/following-sibling::*[1]";
	private static final String lizsLastNameXpath =
		"//input[@type='image']/../following-sibling::td[1][contains(text(),'2')]/following-sibling::*[1]/following-sibling::*[1]";

	// portlet topper for bookings
	private static final String firstNameXpath = "//input[contains(@id,':firstName')]";
	// <input id="A8622:f1:firstName" type="text" name="A8622:f1:firstName" value="Brian" class="focus">
	private static final String lastNameXpath = "//input[contains(@id,':lastName')]";
	private static final String bookingTypeIdXpath = "(//select[contains(@id,':bookingTypeId')])[1]";
	private static final String startDateXpath = "(//input[contains(@id,':startDate')])[1]";
	private static final String finishDateXpath = "(//input[contains(@id,':finishDate')])[1]";
	// <input type="submit" name="A8622:f1:j_idt28" value="Submit" id="aui_3_4_0_1_2331">
	private static final String submitXpath = "//input[@type='submit' and @value='Submit']";

	static final String url = baseUrl + webContext + "/jsf2-events";

	@FindBy(xpath = briansInputXpath)
	private WebElement briansInput;
	@FindBy(xpath = briansFirstNameXpath)
	private WebElement briansFirstName;
	@FindBy(xpath = briansLastNameXpath)
	private WebElement briansLastName;
	@FindBy(xpath = lizsInputXpath)
	private WebElement lizsInput;
	@FindBy(xpath = lizsFirstNameXpath)
	private WebElement lizsFirstName;
	@FindBy(xpath = lizsLastNameXpath)
	private WebElement lizsLastName;
	@FindBy(xpath = firstNameXpath)
	private WebElement firstName;
	@FindBy(xpath = lastNameXpath)
	private WebElement lastName;
	@FindBy(xpath = bookingTypeIdXpath)
	private WebElement bookingTypeId;
	@FindBy(xpath = startDateXpath)
	private WebElement startDate;
	@FindBy(xpath = finishDateXpath)
	private WebElement finishDate;
	@FindBy(xpath = submitXpath)
	private WebElement submit;

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
		logger.log(Level.INFO, "customerPortletDisplayName displayName = " + displayName.getText());

		assertTrue("customerPortletDisplayName displayName.isDisplayed()", displayName.isDisplayed());

		logger.log(Level.INFO,
			"browser.findElements(By.xpath(portletDisplayNameXpath)).size() = " +
			browser.findElements(By.xpath(displayNameXpath)).size());
		logger.log(Level.INFO,
			"browser.findElements(By.xpath(briansInputXpath)).size() = " +
			browser.findElements(By.xpath(briansInputXpath)).size());
		logger.log(Level.INFO,
			"browser.findElements(By.xpath(lizsInputXpath)).size() = " +
			browser.findElements(By.xpath(lizsInputXpath)).size());

		logger.log(Level.INFO, "briansFirstName.getText() = " + briansFirstName.getText());
		logger.log(Level.INFO, "briansLastName.getText() = " + briansLastName.getText());

		logger.log(Level.INFO, "lizsFirstName.getText() = " + lizsFirstName.getText());
		logger.log(Level.INFO, "lizsLastName.getText() = " + lizsLastName.getText());

	}

	@Test
	@RunAsClient
	@InSequence(2000)
	public void checkBriansBookings() throws Exception {

		briansInput.click();

//		Thread.sleep(500);
		try {
			waitForElement(browser, firstNameXpath);
		}
		catch (Exception e) { 
			logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
			assertTrue("firstField should be visible, " +
			"but " + firstNameXpath + " is not visible.", false);
		}

		logger.log(Level.INFO, "firstName.getAttribute(value) = " + firstName.getAttribute("value"));
		logger.log(Level.INFO, "lastName.getAttribute(value) = " + lastName.getAttribute("value"));
		logger.log(Level.INFO, "startDate.getAttribute(value) = " + startDate.getAttribute("value"));
		logger.log(Level.INFO, "finishDate.getAttribute(value) = " + finishDate.getAttribute("value"));

		assertTrue("customer first name should be the same in the bookings but it is '" + briansFirstName.getText() +
			"' in the customer module," + " and '" + firstName.getAttribute("value") + "' in bookings",
			firstName.getAttribute("value").contains(briansFirstName.getText()));
		assertTrue("customer last name should be the same in the bookings but it is '" + briansLastName.getText() +
			"' in the customer module," + " and '" + lastName.getAttribute("value") + "' in bookings",
			lastName.getAttribute("value").contains(briansLastName.getText()));

	}

	@Test
	@RunAsClient
	@InSequence(3000)
	public void changeBriansBookings() throws Exception {

		logger.log(Level.INFO, "lastName.clear() ...");
		lastName.clear();
		logger.log(Level.INFO, "lastName.sendKeys('Greeny') ...");
		lastName.sendKeys("Greeny");
		logger.log(Level.INFO, "finishDate.clear() ...");
		finishDate.clear();
		logger.log(Level.INFO, "finishDate.sendKeys('04/20/2099') ...");
		finishDate.sendKeys("04/20/2099");
		logger.log(Level.INFO, "submit.click() ...");
		submit.click();

		waitForElement(browser, briansLastNameXpath);

		logger.log(Level.INFO, "briansLastName.getText() = " + briansLastName.getText());
		logger.log(Level.INFO, "lastName.getAttribute(value) = " + lastName.getAttribute("value"));
		logger.log(Level.INFO, "finishDate.getAttribute(value) = " + finishDate.getAttribute("value"));

		assertTrue("customer first name should be the same in the bookings but it is '" + briansLastName.getText() +
			"' in the customer module," + " and '" + lastName.getAttribute("value") + "' in bookings",
			lastName.getAttribute("value").contains(briansLastName.getText()));

		assertTrue("Customer last name should contain 'Greeny', but it contains '" + briansLastName.getText() + "'",
			briansLastName.getText().contains("Greeny"));
		assertTrue("Bookings last name should contain 'Greeny', but it contains '" + lastName.getAttribute("value") +
			"'", lastName.getAttribute("value").contains("Greeny"));
	}

	@Test
	@RunAsClient
	@InSequence(4000)
	public void checkLizsBookings() throws Exception {

		lizsInput.click();

		waitForElement(browser, lizsFirstNameXpath);

		logger.log(Level.INFO, "firstName.getAttribute(value) = " + firstName.getAttribute("value"));
		logger.log(Level.INFO, "lastName.getAttribute(value) = " + lastName.getAttribute("value"));
		logger.log(Level.INFO, "startDate.getAttribute(value) = " + startDate.getAttribute("value"));
		logger.log(Level.INFO, "finishDate.getAttribute(value) = " + finishDate.getAttribute("value"));

		assertTrue("customer first name should be the same in the bookings but it is '" + lizsFirstName.getText() +
			"' in the customer module," + " and '" + firstName.getAttribute("value") + "' in bookings",
			firstName.getAttribute("value").contains(lizsFirstName.getText()));
		assertTrue("customer last name should be the same in the bookings but it is '" + lizsLastName.getText() +
			"' in the customer module," + " and '" + lastName.getAttribute("value") + "' in bookings",
			lastName.getAttribute("value").contains(lizsLastName.getText()));

	}

	@Test
	@RunAsClient
	@InSequence(5000)
	public void changeLizsBookings() throws Exception {

		logger.log(Level.INFO, "firstName.clear() ...");
		firstName.clear();
		logger.log(Level.INFO, "firstName.sendKeys('Lizzy') ...");
		firstName.sendKeys("Lizzy");
		finishDate.clear();
		finishDate.sendKeys("12/25/2999");
		submit.click();

		waitForElement(browser, lizsFirstNameXpath);

		logger.log(Level.INFO, "lizsFirstName.getText() = " + lizsFirstName.getText());
		logger.log(Level.INFO, "firstName.getAttribute(value) = " + firstName.getAttribute("value"));
		logger.log(Level.INFO, "finishDate.getAttribute(value) = " + finishDate.getAttribute("value"));

		assertTrue("customer first name should be the same in the bookings but it is '" + lizsFirstName.getText() +
			"' in the customer module," + " and '" + firstName.getAttribute("value") + "' in bookings",
			firstName.getAttribute("value").contains(lizsFirstName.getText()));

		assertTrue("Customer first name should contain 'Lizzy', but it contains '" + lizsFirstName.getText() + "'",
			lizsFirstName.getText().contains("Lizzy"));
		assertTrue("Bookings first name should contain 'Lizzy', but it contains '" + firstName.getAttribute("value") +
			"'", firstName.getAttribute("value").contains("Lizzy"));
	}

	@Test
	@RunAsClient
	@InSequence(6000)
	public void checkBriansBookingsAgain() throws Exception {

		briansInput.click();

		waitForElement(browser, firstNameXpath);

		logger.log(Level.INFO, "firstName.getAttribute(value) = " + firstName.getAttribute("value"));
		logger.log(Level.INFO, "lastName.getAttribute(value) = " + lastName.getAttribute("value"));
		logger.log(Level.INFO, "startDate.getAttribute(value) = " + startDate.getAttribute("value"));
		logger.log(Level.INFO, "finishDate.getAttribute(value) = " + finishDate.getAttribute("value"));

		assertTrue("customer first name should be the same in the bookings but it is '" + briansFirstName.getText() +
			"' in the customer module," + " and '" + firstName.getAttribute("value") + "' in bookings",
			firstName.getAttribute("value").contains(briansFirstName.getText()));
		assertTrue("customer last name should be the same in the bookings but it is '" + briansLastName.getText() +
			"' in the customer module," + " and '" + lastName.getAttribute("value") + "' in bookings",
			lastName.getAttribute("value").contains(briansLastName.getText()));

		assertTrue("Brian's first finishDate should be '04/20/2099', but it is '" + finishDate.getAttribute("value") +
			"'", finishDate.getAttribute("value").contains("04/20/2099"));

	}

}
//J+
