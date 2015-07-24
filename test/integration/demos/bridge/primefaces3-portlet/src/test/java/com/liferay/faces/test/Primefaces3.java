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

import java.util.logging.Logger;

// import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;

import com.liferay.faces.test.util.TesterBase;

/**
 * @author	Liferay Faces Team
 */
public class Primefaces3 extends TesterBase {

	protected static final Logger logger = Logger.getLogger(Primefaces3.class.getName());

	// form tag found after submitting
	private static final String formTagXpath = "//form[@method='post']";

	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";
	private static final String menuButtonXpath = "//*[contains(text(),'Options')]/..";
	private static final String menuPreferencesXpath = "//img[contains(@src,'/edit.png')]";

	// preferences elements
	private static final String datePatternFieldXpath = "//input[contains(@id,':datePattern')]";
	private static final String resetButtonXpath = "//input[@type='submit' and @value='Reset']";

	// elements for Job Applicants
	private static final String logoXpath = "//img[contains(@src,'liferay-logo.png')]";

	private static final String firstNameFieldXpath = "//input[contains(@id,':firstName')]";
	private static final String firstNameFieldErrorXpath = "//input[contains(@id,':firstName')]/following-sibling::*[1]";

	private static final String lastNameFieldXpath = "//input[contains(@id,':lastName')]";
	private static final String lastNameFieldErrorXpath = "//input[contains(@id,':lastName')]/following-sibling::*[1]";

	private static final String emailAddressFieldXpath = "//input[contains(@id,':emailAddress')]";
	private static final String emailAddressFieldErrorXpath = "//input[contains(@id,':emailAddress')]/following-sibling::*[1]";

	private static final String phoneNumberFieldXpath = "//input[contains(@id,':phoneNumber')]";
	private static final String phoneNumberFieldErrorXpath = "//input[contains(@id,':phoneNumber')]/following-sibling::*[1]";

	private static final String dateOfBirthFieldXpath = "//input[contains(@id,':dateOfBirth')]";
	private static final String dateOfBirthFieldErrorXpath = "//input[contains(@id,':dateOfBirth')]/../following-sibling::*[1]";

	private static final String cityFieldXpath = "//input[contains(@id,':city')]";
	private static final String cityFieldErrorXpath = "//input[contains(@id,':city')]/following-sibling::*[1]";

	private static final String provinceIdFieldXpath = "//select[contains(@id,':provinceId')]";
	private static final String provinceIdFieldErrorXpath = "//select[contains(@id,':provinceId')]/following-sibling::*[1]";

	private static final String provinceIdSelectorXpath = "";

	private static final String postalCodeFieldXpath = "//input[contains(@id,':postalCode')]";
	private static final String postalCodeFieldErrorXpath = "//input[contains(@id,':postalCode')]/following-sibling::*[1]/following-sibling::*[1]";

	private static final String postalCodeToolTipXpath = "//img[contains(@title,'Type any of these ZIP codes')]";

	private static final String showCommentsLinkXpath = "//a[contains(text(),'Show Comments')]";
	private static final String hideCommentsLinkXpath = "//a[contains(text(),'Hide Comments')]";
	private static final String commentsXpath = "//textarea[contains(@id,':comments')]";

	private static final String fileUploadChooserXpath = "//input[@type='file' and @multiple='multiple']";
	private static final String submitFileXpath = "//span[contains(text(),'Upload')]";
	private static final String uploadedFileXpath = "//tr[@class='ui-widget-content ui-datatable-even']/td[2]";

	private static final String submitButtonXpath = "//span[contains(text(),'Submit')]/..";
	private static final String preferencesSubmitButtonXpath = "//input[@type='submit' and @value='Submit']";
	private static final String editPreferencesButtonXpath = "//span[contains(text(),'Edit Preferences')]/..";
	private static final String returnLinkXpath = "//a[contains(text(),'Return to Full Page')]";

	private static final String mojarraVersionXpath = "//*[contains(text(),'Mojarra')]";
	private static final String componentLibraryVersionXpath = "//*[contains(text(),'PrimeFaces ')]";
	private static final String alloyVersionXpath = "//*[contains(text(),'Liferay Faces Alloy')]";
	private static final String bridgeVersionXpath = "//*[contains(text(),'Liferay Faces Bridge')]";

	private static final String versionUlXpath = "//*[contains(text(),'Liferay Faces Bridge')]/../../../ul";
	private static final String windowInnerHeightXpath = "//em[@id='window.innerHeight']";
	private static final String windowInnerWidthXpath = "//em[@id='window.innerWidth']";

	// xpath for specific tests
	protected static final String dateValidationXpath = "//input[contains(@id,':dateOfBirth')]/../following-sibling::*[1]/child::node()";

	static final String url = baseUrl + webContext + "/prime3";

	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	@FindBy(xpath = menuButtonXpath)
	private WebElement menuButton;
	@FindBy(xpath = menuPreferencesXpath)
	private WebElement menuPreferences;
	@FindBy(xpath = datePatternFieldXpath)
	private WebElement datePatternField;
	@FindBy(xpath = resetButtonXpath)
	private WebElement resetButton;
	@FindBy(xpath = logoXpath)
	private WebElement logo;
	@FindBy(xpath = firstNameFieldXpath)
	private WebElement firstNameField;
	@FindBy(xpath = firstNameFieldErrorXpath)
	private WebElement firstNameFieldError;
	@FindBy(xpath = lastNameFieldXpath)
	private WebElement lastNameField;
	@FindBy(xpath = lastNameFieldErrorXpath)
	private WebElement lastNameFieldError;
	@FindBy(xpath = emailAddressFieldXpath)
	private WebElement emailAddressField;
	@FindBy(xpath = emailAddressFieldErrorXpath)
	private WebElement emailAddressFieldError;
	@FindBy(xpath = phoneNumberFieldXpath)
	private WebElement phoneNumberField;
	@FindBy(xpath = phoneNumberFieldErrorXpath)
	private WebElement phoneNumberFieldError;
	@FindBy(xpath = dateOfBirthFieldXpath)
	private WebElement dateOfBirthField;
	@FindBy(xpath = dateOfBirthFieldErrorXpath)
	private WebElement dateOfBirthFieldError;
	@FindBy(xpath = cityFieldXpath)
	private WebElement cityField;
	@FindBy(xpath = cityFieldErrorXpath)
	private WebElement cityFieldError;
	@FindBy(xpath = provinceIdFieldXpath)
	private WebElement provinceIdField;
	@FindBy(xpath = provinceIdFieldErrorXpath)
	private WebElement provinceIdFieldError;
	@FindBy(xpath = postalCodeFieldXpath)
	private WebElement postalCodeField;
	@FindBy(xpath = postalCodeFieldErrorXpath)
	private WebElement postalCodeFieldError;
	@FindBy(xpath = postalCodeToolTipXpath)
	private WebElement postalCodeToolTip;
	@FindBy(xpath = showCommentsLinkXpath)
	private WebElement showCommentsLink;
	@FindBy(xpath = hideCommentsLinkXpath)
	private WebElement hideCommentsLink;
	@FindBy(xpath = commentsXpath)
	private WebElement comments;
	@FindBy(xpath = fileUploadChooserXpath)
	private WebElement fileUploadChooser;
	@FindBy(xpath = submitFileXpath)
	private WebElement submitFile;
	@FindBy(xpath = uploadedFileXpath)
	private WebElement uploadedFile;
	@FindBy(xpath = submitButtonXpath)
	private WebElement submitButton;
	@FindBy(xpath = preferencesSubmitButtonXpath)
	private WebElement preferencesSubmitButton;
	@FindBy(xpath = editPreferencesButtonXpath)
	private WebElement editPreferencesButton;
	@FindBy(xpath = returnLinkXpath)
	private WebElement returnLink;
	@FindBy(xpath = mojarraVersionXpath)
	private WebElement mojarraVersion;
	@FindBy(xpath = componentLibraryVersionXpath)
	private WebElement componentLibraryVersion;
	@FindBy(xpath = alloyVersionXpath)
	private WebElement alloyVersion;
	@FindBy(xpath = bridgeVersionXpath)
	private WebElement bridgeVersion;
	@FindBy(xpath = versionUlXpath)
	private WebElement versionUl;
	@FindBy(xpath = windowInnerHeightXpath)
	private WebElement windowInnerHeight;
	@FindBy(xpath = windowInnerWidthXpath)
	private WebElement windowInnerWidth;

	protected int dateValidationXpathModifier = 0;

}
//J+
