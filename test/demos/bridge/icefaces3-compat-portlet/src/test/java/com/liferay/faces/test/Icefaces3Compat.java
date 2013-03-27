package com.liferay.faces.test;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;

import java.io.File;

import org.apache.commons.io.FileUtils;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;
// import java.net.URL;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.jboss.arquillian.graphene.Graphene.waitGui;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

public class Icefaces3Compat {
	
	private final static Logger logger = Logger.getLogger(Icefaces3Compat.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/signin";
	String url = "http://localhost:8080/group/bridge-demos/ice3-compat";
	
	WebDriver browser;
	
	// elements for logging in
	private static final String emailFieldXpath = "//input[contains(@id,':handle')]";
	@FindBy(xpath = emailFieldXpath)
	private WebElement emailField;
	private static final String passwordFieldXpath = "//input[contains(@id,':password')]";
	@FindBy(xpath = passwordFieldXpath)
	private WebElement passwordField;
	private static final String signInButtonXpath = "//input[@type='submit' and @value='Sign In']";
	@FindBy(xpath = signInButtonXpath)
	private WebElement signInButton;
	private static final String signedInTextXpath = "//div[contains(text(),'You are signed in as')]";
	@FindBy(xpath = signedInTextXpath)
	private WebElement signedInText;
	
	// form tag found after submitting
	private static final String formTagXpath = "//form[@method='post']";
	@FindBy(xpath = formTagXpath)
	private WebElement formTag;
	
	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	private static final String menuButtonXpath = "//a[contains(@id,'menuButton')]";
	@FindBy(xpath = menuButtonXpath)
	private WebElement menuButton;
	private static final String menuPreferencesXpath = "//a[contains(@id,'menu_preferences')]";
	@FindBy(xpath = menuPreferencesXpath)
	private WebElement menuPreferences;
	
	// preferences elements
	private static final String datePatternFieldXpath = "//input[contains(@id,':datePattern')]";
	@FindBy(xpath = datePatternFieldXpath)
	private WebElement datePatternField;
	private static final String resetButtonXpath = "//input[@type='submit' and @value='Reset']";
	@FindBy(xpath = resetButtonXpath)
	private WebElement resetButton;
	
	// elements for Job Applicants
	private static final String logoXpath = "//img[contains(@src,'liferay-logo.png')]";
	@FindBy(xpath = logoXpath)
	private WebElement logo;
	
	private static final String firstNameFieldXpath = "//input[contains(@id,':firstName')]";
	@FindBy(xpath = firstNameFieldXpath)
	private WebElement firstNameField;
	private static final String firstNameFieldErrorXpath = "//input[contains(@id,':firstName')]/following-sibling::*[1]";
	@FindBy(xpath = firstNameFieldErrorXpath)
	private WebElement firstNameFieldError;
	
	private static final String lastNameFieldXpath = "//input[contains(@id,':lastName')]";
	@FindBy(xpath = lastNameFieldXpath)
	private WebElement lastNameField;
	private static final String lastNameFieldErrorXpath = "//input[contains(@id,':lastName')]/following-sibling::*[1]";
	@FindBy(xpath = lastNameFieldErrorXpath)
	private WebElement lastNameFieldError;
	
	private static final String emailAddressFieldXpath = "//input[contains(@id,':emailAddress')]";
	@FindBy(xpath = emailAddressFieldXpath)
	private WebElement emailAddressField;
	private static final String emailAddressFieldErrorXpath = "//input[contains(@id,':emailAddress')]/following-sibling::*[1]/child::node()[1]";
	@FindBy(xpath = emailAddressFieldErrorXpath)
	private WebElement emailAddressFieldError;
	
	private static final String phoneNumberFieldXpath = "//input[contains(@id,':phoneNumber')]";
	@FindBy(xpath = phoneNumberFieldXpath)
	private WebElement phoneNumberField;
	private static final String phoneNumberFieldErrorXpath = "//input[contains(@id,':phoneNumber')]/following-sibling::*[1]";
	@FindBy(xpath = phoneNumberFieldErrorXpath)
	private WebElement phoneNumberFieldError;
	
	private static final String dateOfBirthFieldXpath = "//input[contains(@id,':dateOfBirth')]";
	@FindBy(xpath = dateOfBirthFieldXpath)
	private WebElement dateOfBirthField;
	private static final String dateOfBirthFieldErrorXpath = "//input[contains(@id,':dateOfBirth')]/../following-sibling::*[1]/child::node()[1]";
	@FindBy(xpath = dateOfBirthFieldErrorXpath)
	private WebElement dateOfBirthFieldError;
	
	private static final String cityFieldXpath = "//input[contains(@id,':city')]";
	@FindBy(xpath = cityFieldXpath)
	private WebElement cityField;
	private static final String cityFieldErrorXpath = "//input[contains(@id,':city')]/following-sibling::*[1]";
	@FindBy(xpath = cityFieldErrorXpath)
	private WebElement cityFieldError;
	
	private static final String provinceIdFieldXpath = "//select[contains(@id,':provinceId')]";
	@FindBy(xpath = provinceIdFieldXpath)
	private WebElement provinceIdField;
	private static final String provinceIdFieldErrorXpath = "//select[contains(@id,':provinceId')]/following-sibling::*[1]";
	@FindBy(xpath = provinceIdFieldErrorXpath)
	private WebElement provinceIdFieldError;
	
	private static final String postalCodeFieldXpath = "//input[contains(@id,':postalCode')]";
	@FindBy(xpath = postalCodeFieldXpath)
	private WebElement postalCodeField;
	private static final String postalCodeFieldErrorXpath = "//input[contains(@id,':postalCode')]/following-sibling::*[1]/following-sibling::*[1]";
	@FindBy(xpath = postalCodeFieldErrorXpath)
	private WebElement postalCodeFieldError;
	
	private static final String postalCodeToolTipXpath = "//img[contains(@title,'Type any of these ZIP codes')]";
	@FindBy(xpath = postalCodeToolTipXpath)
	private WebElement postalCodeToolTip;
	
	private static final String showCommentsLinkXpath = "//a[contains(text(),'Show Comments')]";
	@FindBy(xpath = showCommentsLinkXpath)
	private WebElement showCommentsLink;
	private static final String hideCommentsLinkXpath = "//a[contains(text(),'Hide Comments')]";
	@FindBy(xpath = hideCommentsLinkXpath)
	private WebElement hideCommentsLink;
	private static final String commentsXpath = "//textarea[contains(@id,':comments')]";
	@FindBy(xpath = commentsXpath)
	private WebElement comments;
	
	private static final String fileUploadChooserXpath = "//input[@type='file' and contains(@id,':fileEntryComp')]";
	@FindBy(xpath = fileUploadChooserXpath)
	private WebElement fileUploadChooser;
	private static final String submitFileXpath = "//input[@type='submit' and @value='Add Attachment']";
	@FindBy(xpath = submitFileXpath)
	private WebElement submitFile;
	private static final String uploadedFileXpath = "//tr[contains(@class,'portlet-section-body results-row')]/td[2]";
	@FindBy(xpath = uploadedFileXpath)
	private WebElement uploadedFile;
	
	private static final String submitButtonXpath = "//input[@type='submit' and @value='Submit']";
	@FindBy(xpath = submitButtonXpath)
	private WebElement submitButton;
	private static final String preferencesSubmitButtonXpath = "//input[@type='submit' and @value='Submit']";
	@FindBy(xpath = preferencesSubmitButtonXpath)
	private WebElement preferencesSubmitButton;
	private static final String editPreferencesButtonXpath = "//input[@type='submit' and @value='Edit Preferences']";
	@FindBy(xpath = editPreferencesButtonXpath)
	private WebElement editPreferencesButton;
	private static final String returnLinkXpath = "//a[contains(text(),'Return to Full Page')]";
	@FindBy(xpath = returnLinkXpath)
	private WebElement returnLink;
	
	private static final String mojarraVersionXpath = "//*[contains(text(),'Mojarra')]";
	@FindBy(xpath = mojarraVersionXpath)
	private WebElement mojarraVersion;
	private static final String componentLibraryVersionXpath = "//*[contains(text(),'ICEfaces ')]";
	@FindBy(xpath = componentLibraryVersionXpath)
	private WebElement componentLibraryVersion;
	private static final String alloyVersionXpath = "//*[contains(text(),'Liferay Faces Alloy')]";
	@FindBy(xpath = alloyVersionXpath)
	private WebElement alloyVersion;
	private static final String bridgeVersionXpath = "//*[contains(text(),'Liferay Faces Bridge')]";
	@FindBy(xpath = bridgeVersionXpath)
	private WebElement bridgeVersion;
	
	// xpath for specific tests
	private static final String dateValidationXpath = "//input[contains(@id,':dateOfBirth')]/../following-sibling::*[1]/child::node()";
	int dateValidationXpathModifier = 0;
	
}
