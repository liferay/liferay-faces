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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.liferay.faces.test.util.TesterBase;

/**
 * @author	Vernon Singleton
 */
@RunWith(Arquillian.class)
public class Jsf2FlowsPortletTest extends TesterBase {

	// xpath
	private static final String weldServletVersionXpath = "//em[contains(text(),'Weld Servlet')]";
	private static final String enterBookingFlowButtonXpath = "//input[@value='Enter Booking Flow']";
	private static final String bookingTypeSelectXpath = "//select[contains(@id,':bookingTypeId')]";
	private static final String bookingTypeSelectFlightXpath = "//select[contains(@id,':bookingTypeId')]/option[contains(text(),'Flight')]";
	private static final String bookingTypeSelectCruiseXpath = "//select[contains(@id,':bookingTypeId')]/option[contains(text(),'Cruise')]";
	private static final String departureSelectXpath = "//select[contains(@id,':departureId')]";
	private static final String departureLaxXpath = "//select[contains(@id,':departureId')]/option[@value='7755']";
	private static final String arrivalSelectXpath = "//select[contains(@id,':arrivalId')]";
	private static final String arrivalSdfXpath = "//select[contains(@id,':arrivalId')]/option[@value='4014']";
	private static final String departureSdfXpath = "//select[contains(@id,':departureId')]/option[@value='4014']";
	private static final String arrivalMcoXpath = "//select[contains(@id,':arrivalId')]/option[@value='3878']";
	private static final String departureDateXpath = "//input[contains(@id,':departureDate')]";
	private static final String searchFlightsButtonXpath = "//input[@value='Search Flights']";
	private static final String addToCartButtonXpath = "(//input[@value='Add To Cart'])[1]";
	private static final String removeButtonXpath = "(//input[@value='Remove'])[1]";
	private static final String bookAdditionalTravelButtonXpath = "//input[@value='Book Additional Travel']";
	private static final String onlyFlightBookingXpath = "//p[contains(text(),'the only type of booking')]";	
	private static final String checkoutButtonXpath = "//input[@value='Checkout']";
	private static final String purchaseButtonXpath = "//input[@value='Purchase']";
	private static final String firstNameXpath = "//input[contains(@id,':firstName')]";
	private static final String callSurveyFlowButtonXpath = "//input[@value='Call Survey Flow']";
	private static final String thankYouFormPurchaseXpath = "//form[contains(.,'Thank you Gilbert for your purchase')]";
	private static final String finishButtonXpath = "//input[@value='Finish']";
	private static final String question1Xpath = "//div[contains(@id,':question1')]/input";
	private static final String question2Xpath = "//div[contains(@id,':question2')]/input";
	private static final String thankYouFormSurveyXpath = "//form[contains(.,'Thank you Gilbert for participating in our survey')]";
	private static final String returnFromSurveyFlowButtonXpath = "//input[@value='Return From Survey Flow']";
	private static final String exitBookingFlowButtonXpath = "//input[@value='Exit Booking Flow']";
	
	static final String url = baseUrl + webContext + "/jsf2-flows";

	@FindBy(xpath = weldServletVersionXpath)
	private WebElement weldServletVersion;
	@FindBy(xpath = enterBookingFlowButtonXpath)
	private WebElement enterBookingFlowButton;
	@FindBy(xpath = bookingTypeSelectXpath)
	private WebElement bookingTypeSelect;
	@FindBy(xpath = bookingTypeSelectFlightXpath)
	private WebElement bookingTypeSelectFlight;
	@FindBy(xpath = bookingTypeSelectCruiseXpath)
	private WebElement bookingTypeSelectCruise;
	@FindBy(xpath = departureSelectXpath)
	private WebElement departureSelect;
	@FindBy(xpath = departureLaxXpath)
	private WebElement departureLax;
	@FindBy(xpath = arrivalSelectXpath)
	private WebElement arrivalSelect;
	@FindBy(xpath = arrivalSdfXpath)
	private WebElement arrivalSdf;
	@FindBy(xpath = departureSdfXpath)
	private WebElement departureSdf;
	@FindBy(xpath = arrivalMcoXpath)
	private WebElement arrivalMco;
	@FindBy(xpath = departureDateXpath)
	private WebElement departureDate;
	@FindBy(xpath = searchFlightsButtonXpath)
	private WebElement searchFlightsButton;
	@FindBy(xpath = addToCartButtonXpath)
	private WebElement addToCartButton;
	@FindBy(xpath = removeButtonXpath)
	private WebElement removeButton;
	@FindBy(xpath = bookAdditionalTravelButtonXpath)
	private WebElement bookAdditionalTravelButton;
	@FindBy(xpath = onlyFlightBookingXpath)
	private WebElement onlyFlightBooking;
	@FindBy(xpath = checkoutButtonXpath)
	private WebElement checkoutButton;
	@FindBy(xpath = purchaseButtonXpath)
	private WebElement purchaseButton;
	@FindBy(xpath = firstNameXpath)
	private WebElement firstName;
	@FindBy(xpath = callSurveyFlowButtonXpath)
	private WebElement callSurveyFlowButton;
	@FindBy(xpath = thankYouFormPurchaseXpath)
	private WebElement thankYouFormPurchase;
	@FindBy(xpath = finishButtonXpath)
	private WebElement finishButton;
	@FindBy(xpath = question1Xpath)
	private WebElement question1;
	@FindBy(xpath = question2Xpath)
	private WebElement question2;
	@FindBy(xpath = thankYouFormSurveyXpath)
	private WebElement thankYouFormSurvey;
	@FindBy(xpath = returnFromSurveyFlowButtonXpath)
	private WebElement returnFromSurveyFlowButton;
	@FindBy(xpath = exitBookingFlowButtonXpath)
	private WebElement exitBookingFlowButton;
	
	@Drone
	WebDriver browser;

	@Test
	@RunAsClient
	@InSequence(1000)
	public void flowsPortlet() throws Exception {

		signIn(browser);
		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		getPortletDisplayName();
		logger.log(Level.INFO, "displayName.getText() = " + displayName.getText());
		
		if (isThere(browser, weldServletVersionXpath)) {
			logger.log(Level.INFO, "weldServletVersion.isDisplayed() = " + weldServletVersion.isDisplayed());
			logger.log(Level.INFO, "weldServletVersion.getText() = " + weldServletVersion.getText());
		}

		assertTrue("portletDisplayName displayName.isDisplayed()", displayName.isDisplayed());
		
		if (isThere(browser, enterBookingFlowButtonXpath)) {

			enterBookingFlowButton.click();

			try {
				waitForElement(browser, bookingTypeSelectXpath);
			}
			catch (Exception e) {
				logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
				assertTrue("bookingTypeSelect should be visible, but "+ bookingTypeSelectXpath +" is not there.", e == null);
			}
			
			exitBookingFlowButton.click();
			
			try {
				waitForElement(browser, enterBookingFlowButtonXpath);
				logger.log(Level.INFO, "We exited the flow.");
			}
			catch (Exception e) {
				logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
				assertTrue("enterBookingFlowButton should be visible, but "+ enterBookingFlowButtonXpath +" is not there.", e == null);
			}
			
			enterBookingFlowButton.click();

			try {
				waitForElement(browser, bookingTypeSelectXpath);
			}
			catch (Exception e) {
				logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
				assertTrue("bookingTypeSelect should be visible, but "+ bookingTypeSelectXpath +" is not there.", e == null);
			}
			
			if (isThere(browser, bookingTypeSelectFlightXpath)) {
				bookingTypeSelectFlight.click();
				
				waitForElement(browser, departureLaxXpath);
				departureLax.click();

				waitForElement(browser, arrivalSdfXpath);
				arrivalSdf.click();

				waitForElement(browser, departureDateXpath);
				departureDate.sendKeys("2015-08-12T00:00:01Z");

				if (isThere(browser, searchFlightsButtonXpath)) {
					searchFlightsButton.click();

					try {
						waitForElement(browser, addToCartButtonXpath);
					}
					catch (Exception e) {
						logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
						assertTrue("addToCartButton should be visible, but "+ addToCartButtonXpath +" is not there.", e == null);
					}
					addToCartButton.click();

					try {
						waitForElement(browser, bookAdditionalTravelButtonXpath);
					}
					catch (Exception e) {
						logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
						assertTrue("bookAdditionalTravelButton should be visible, but "+ bookAdditionalTravelButtonXpath +" is not there.", e == null);
					}
					bookAdditionalTravelButton.click();

					waitForElement(browser, bookingTypeSelectXpath);
					assertTrue("The selector for the booking type should be showing now, but it is not visible.", isThere(browser, bookingTypeSelectXpath));
					
					if (isThere(browser, bookingTypeSelectFlightXpath)) {
						bookingTypeSelectCruise.click();
						
						try {
							waitForElement(browser, onlyFlightBookingXpath);
						}
						catch (Exception e) {
							logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
							assertTrue("onlyFlightBooking message should be visible, but "+ onlyFlightBookingXpath +" is not there.", e == null);
						}
						
						bookingTypeSelectFlight.click();
						
						waitForElement(browser, departureSdfXpath);
						departureSdf.click();

						waitForElement(browser, arrivalMcoXpath);
						arrivalMco.click();

						waitForElement(browser, departureDateXpath);
						departureDate.sendKeys("2015-08-12T00:00:01Z");
						
						if (isThere(browser, searchFlightsButtonXpath)) {
							searchFlightsButton.click();

							try {
								waitForElement(browser, addToCartButtonXpath);
							}
							catch (Exception e) {
								logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
								assertTrue("addToCartButton should be visible, but "+ addToCartButtonXpath +" is not there.", e == null);
							}
							addToCartButton.click();
														
							waitForElement(browser, checkoutButtonXpath);
							checkoutButton.click();
							
							try {
								waitForElement(browser, purchaseButtonXpath);
							}
							catch (Exception e) {
								logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
								assertTrue("purchaseButton should be visible, but "+ purchaseButtonXpath +" is not there.", e == null);
							}

							assertTrue("The firstName field should be vissible, but it is not.", isThere(browser, firstNameXpath));
							if (isThere(browser, firstNameXpath)) {
								firstName.sendKeys("Gilbert");
								firstName.sendKeys(Keys.TAB);
							}
							
							purchaseButton.click();
							
							try {
								waitForElement(browser, thankYouFormPurchaseXpath);
								logger.log(Level.INFO, "We booked a flight.");
							}
							catch (Exception e) {
								logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
								assertTrue("thankYouForm for the purchase should be visible, but "+ thankYouFormPurchaseXpath +" is not there.", e == null);
							}
							
							if (isThere(browser, callSurveyFlowButtonXpath)) {
								callSurveyFlowButton.click();
								
								try {
									waitForElement(browser, finishButtonXpath);
								}
								catch (Exception e) {
									logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
									assertTrue("finishButton should be visible, but "+ finishButtonXpath +" is not there.", e == null);
								}
								question1.sendKeys("Liferay");
								question2.sendKeys("cockpit");
								finishButton.click();
								
								try {
									waitForElement(browser, returnFromSurveyFlowButtonXpath);
								}
								catch (Exception e) {
									logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
									assertTrue("returnFromSurveyFlowButton should be visible, but "+ returnFromSurveyFlowButtonXpath +" is not there.", e == null);
								}
								returnFromSurveyFlowButton.click();
								
								try {
									waitForElement(browser, enterBookingFlowButtonXpath);
									logger.log(Level.INFO, "We completed the survey flow.  Yay!");
								}
								catch (Exception e) {
									logger.log(Level.INFO, "Exception e.getMessage() = " + e.getMessage());
									assertTrue("enterBookingFlowButton should be visible, but "+ enterBookingFlowButtonXpath +" is not there.", e == null);
								}
							}
						}
					}
				}
			}
		}
	}
}
//J+
