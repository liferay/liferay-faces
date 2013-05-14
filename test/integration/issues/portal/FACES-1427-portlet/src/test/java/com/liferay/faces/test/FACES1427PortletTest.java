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

import java.util.logging.Level;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.graphene.javascript.JSInterfaceFactory;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.liferay.faces.test.util.TesterBase;


/**
 * @author  Liferay Faces Team
 */
@RunWith(Arquillian.class)
public class FACES1427PortletTest extends TesterBase {
	
	// Error Message
	private static final String errorMessageXpath = "//td[text()=' This test is only valid on Liferay 6.0.12+ ']";

	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";

	// <input id="A2399:f1:fileEntryComp::vgfq83x2" name="A2399:f1:fileEntryComp::vgfq83x2" tabindex="0" type="file">
	private static final String fileEntryComponentXpath = "//input[@type='file']";

	// <input id="A2399:f1:_t12" name="A2399:f1:_t12" type="submit" value="Add Attachment">
	private static final String addAttachmentXpath = "//input[@type='submit' and @value='Add Attachment']";

	// <span id="A2399:f1:_t8:0:_t11">liferay-jsf-jersey.png</span>
	private static final String attachmentXpath = "//span[contains(text(),'jersey')]";

	private static final String textarea1Xpath = "//textarea[contains(@id,':comments1:inputText')]";

	// <img alt="Bold" buttonid="b" src="http://localhost:8080/html/themes/classic/images/message_boards/bold.png">
	private static final String bold1Xpath = "//img[@alt='Bold' and @buttonid='b' and contains(@src, 'bold.png')]";

	private static final String iframe1Xpath = "//td[contains(@id,':comments2')]/iframe";

	private static final String bold2Xpath = "//a[contains(@class, 'cke_button_bold')]";

	// <input id="A2399:f1:_t25" name="A2399:f1:_t25" type="submit" value="Submit">
	private static final String submitXpath = "//input[@type='submit' and @value='Submit']";

	// <span id="A2399:f1:comments1Output">comments1-initial-value</span>
	private static final String comments1OutputXpath = "//span[contains(@id,':comments1Output')]";

	// <span id="A2399:f1:comments2Output">comments2-initial-value</span>
	private static final String comments2OutputXpath = "//span[contains(@id,':comments2Output')]";

	static final String url = "http://localhost:8080/web/portal-issues/faces-1427";
	
	@FindBy(xpath = errorMessageXpath)
	private static WebElement errorMessage;
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	@FindBy(xpath = fileEntryComponentXpath)
	private WebElement fileEntryComponent;
	@FindBy(xpath = addAttachmentXpath)
	private WebElement addAttachment;
	@FindBy(xpath = attachmentXpath)
	private WebElement attachment;
	@FindBy(xpath = textarea1Xpath)
	private WebElement textarea1;
	@FindBy(xpath = bold1Xpath)
	private WebElement bold1;
	@FindBy(xpath = iframe1Xpath)
	private WebElement iframe1;
	@FindBy(xpath = bold2Xpath)
	private WebElement bold2;
	@FindBy(xpath = submitXpath)
	private WebElement submit;
	@FindBy(xpath = comments1OutputXpath)
	private WebElement comments1Output;
	@FindBy(xpath = comments2OutputXpath)
	private WebElement comments2Output;
	
	@Before
	public void beforeEachTest() {

		// Aparently this is necessary _sometimes_ for this test to pass ... not sure why.
		browser.manage().deleteAllCookies();
		logger.log(Level.INFO, "browser.manage().deleteAllCookies() ...");

	}

	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES1427PortletViewMode() throws Exception {

		logger.log(Level.INFO, "browser.navigate().to(" + url + ")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		Thread.sleep(250);
		
		if(isThere(errorMessageXpath) && errorMessage.isDisplayed()) {
			return;
		}

		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());

		logger.log(Level.INFO, "fileEntryComponent.isDisplayed() = " + fileEntryComponent.isDisplayed());
		logger.log(Level.INFO, "addAttachment.isDisplayed() = " + addAttachment.isDisplayed());

		logger.log(Level.INFO, "isThere(attachmentXpath) = " + isThere(attachmentXpath));

		logger.log(Level.INFO, "textarea1.isDisplayed() = " + textarea1.isDisplayed());
		logger.log(Level.INFO, "isThere(bold1Xpath) = " + isThere(bold1Xpath));
		logger.log(Level.INFO, "bold1.isDisplayed() = " + bold1.isDisplayed());
		assertTrue("the first editor should be rendered by now, but it is not displayed", textarea1.isDisplayed());

		logger.log(Level.INFO, "iframe1.isDisplayed() = " + iframe1.isDisplayed());
		logger.log(Level.INFO, "iframe1.getText() = " + iframe1.getText());
		logger.log(Level.INFO, "bold2.isDisplayed() = " + bold2.isDisplayed());
		assertTrue("the second editor should be rendered by now, but it is not displayed", iframe1.isDisplayed());

		logger.log(Level.INFO, "submit.isDisplayed() = " + submit.isDisplayed());
		logger.log(Level.INFO, "comments1Output.isDisplayed() = " + comments1Output.isDisplayed());
		logger.log(Level.INFO, "comments2Output.isDisplayed() = " + comments2Output.isDisplayed());

		assertTrue("the submit button should be rendered by now, but it is not displayed", submit.isDisplayed());

	}

	// 1. Upload a file by choosing a file with the ace:fileEntry control and click on the Add Attachment button.
	// 2. Verify that the file name appears in the list of attachments just above the ace:fileEntry control.

	@Test
	@RunAsClient
	@InSequence(1100)
	public void steps1and2() throws Exception {
		
		if(isThere(errorMessageXpath) && errorMessage.isDisplayed()) {
			return;
		}

		fileEntryComponent.sendKeys(getPathToJerseyFile());
		Thread.sleep(50);
		addAttachment.click();
		Thread.sleep(500);

		logger.log(Level.INFO, "attachment.isDisplayed() = " + attachment.isDisplayed());
		logger.log(Level.INFO, "attachment.getText() = " + attachment.getText());

		assertTrue("attachment should be listed now after clicking 'Add Attachment', but it is not there",
			isThere(attachmentXpath));

	}

	// 3. Verify that the FIRST editor is rendered and that the initial value of the text is: comments1-initial-value
	// 4. Verify that the SECOND editor is rendered and that the initial value of the text is: comments2-initial-value
	// 5. Click on the submit button.
	// 6. Verify that the submitted value for FIRST editor is: comments1-initial-value
	// 7. Verify that the submitted value for SECOND editor is: comments2-initial-value

	@Test
	@RunAsClient
	@InSequence(1200)
	public void steps34567() throws Exception {
		
		if(isThere(errorMessageXpath) && errorMessage.isDisplayed()) {
			return;
		}

		logger.log(Level.INFO, "textarea1.getAttribute('value') = " + textarea1.getAttribute("value"));

		logger.log(Level.INFO, "isThere(iframe1Xpath) = " + isThere(iframe1Xpath));
		Thread.sleep(250);
		submit.click();
		Thread.sleep(250);
		logger.log(Level.INFO, "isThere(iframe1Xpath) = " + isThere(iframe1Xpath));

		logger.log(Level.INFO, "comments1Output.getText() = '" + comments1Output.getText() + "'");
		logger.log(Level.INFO, "comments2Output.getText() = '" + comments2Output.getText() + "'");

		assertTrue("the submitted value for the FIRST editor should be 'comments1-initial-value', but " + "it is '" +
			comments1Output.getText() + "'", comments1Output.getText().equals("comments1-initial-value"));
		assertTrue("the submitted value for the SECOND editor should be 'comments2-initial-value', but " + "it is '" +
			comments2Output.getText() + "'", comments2Output.getText().equals("comments2-initial-value"));

	}

	// 8. Change the word "initial" to "subsequent" in the FIRST editor and click on the bold button in order to make
	// the word subsequent bold. 9. Change the word "initial" to "subsequent" in the SECOND editor and click on the bold
	// button in order to make the word subsequent bold. 10. Click on the submit button. 11. Verify that the submitted
	// value for FIRST editor is: comments1-[b]subsequent[/b]-value 12. Verify that the submitted value for SECOND
	// editor is: comments1-[b]subsequent[/b]-value

	@Test
	@RunAsClient
	@InSequence(1300)
	public void steps89012() throws Exception {
		
		if(isThere(errorMessageXpath) && errorMessage.isDisplayed()) {
			return;
		}

		logger.log(Level.INFO, "textarea1.getAttribute('value') = " + textarea1.getAttribute("value"));

		// click into textarea1
		textarea1.click();
		Thread.sleep(250);

		// move to the beginning of textarea1
		textarea1.sendKeys(Keys.HOME); // firefox cursor is on the left after the last click, in chromium cursor is on
									   // the right of the text ... grr
		Thread.sleep(250);

		// arrow over to the word 'initial'
		textarea1.sendKeys(Keys.ARROW_RIGHT);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.ARROW_RIGHT);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.ARROW_RIGHT);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.ARROW_RIGHT);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.ARROW_RIGHT);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.ARROW_RIGHT);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.ARROW_RIGHT);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.ARROW_RIGHT);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.ARROW_RIGHT);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.ARROW_RIGHT);
		Thread.sleep(250);

		// delete the word 'initial'
		textarea1.sendKeys(Keys.DELETE);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.DELETE);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.DELETE);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.DELETE);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.DELETE);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.DELETE);
		Thread.sleep(250);
		textarea1.sendKeys(Keys.DELETE);
		Thread.sleep(250);

		// type the word 'subsequent'
		textarea1.sendKeys("subsequent");
		Thread.sleep(250);

		// select the word 'subsequent' ... easier said than done
		logger.log(Level.INFO, "textarea1.getAttribute('id') = '" + textarea1.getAttribute("id") + "'");

		SelectText selectText = JSInterfaceFactory.create(SelectText.class);

		logger.log(Level.INFO,
			"before selecting ... selectText.getSelection(id) = " +
			selectText.getSelection(textarea1.getAttribute("id")));
		Thread.sleep(500);

		logger.log(Level.INFO, "selectText.getSelection('id', 10, 20) ... ");
		selectText.setSelection(textarea1.getAttribute("id"), 10, 20);
		Thread.sleep(1000);

		logger.log(Level.INFO,
			"after selecting ... selectText.getSelection(id) = " +
			selectText.getSelection(textarea1.getAttribute("id")));
		Thread.sleep(500);

		logger.log(Level.INFO, "isThere(bold1Xpath) = " + isThere(bold1Xpath));
		logger.log(Level.INFO, "bold1.isDisplayed() = " + bold1.isDisplayed());
		logger.log(Level.INFO, "bold1.getAttribute('src') = " + bold1.getAttribute("src"));
		logger.log(Level.INFO, "bold1.getLocation() = " + bold1.getLocation());
		logger.log(Level.INFO, "clicking the bold1 button ...");

		// click the bold1 button to make the word 'subsequent' bold
		try {
			bold1.click();
		}
		catch (Exception e) { // apparently things are different in chromium
			logger.log(Level.INFO, "e.getMessage() = " + e.getMessage());
			(new Actions(browser)).moveToElement(bold1, 3, 3).click(bold1).build().perform();
			Thread.sleep(2500);
			(new Actions(browser)).moveToElement(bold1, 3, 3).click().build().perform();
			Thread.sleep(2500);
			(new Actions(browser)).moveToElement(bold1).doubleClick().build().perform();
			Thread.sleep(2500);
		}

		Thread.sleep(250);

		// move into iframe1
		logger.log(Level.INFO, "clicking into iframe1 ...");
		iframe1.click();
		Thread.sleep(250);

		// arrow over to the word 'initial'
		(new Actions(browser)).sendKeys(Keys.ARROW_LEFT).perform();
		Thread.sleep(250);
		(new Actions(browser)).sendKeys(Keys.ARROW_LEFT).perform();
		Thread.sleep(250);
		(new Actions(browser)).sendKeys(Keys.ARROW_LEFT).perform();
		Thread.sleep(250);
		(new Actions(browser)).sendKeys(Keys.ARROW_LEFT).perform();
		Thread.sleep(250);
		(new Actions(browser)).sendKeys(Keys.ARROW_LEFT).perform();
		Thread.sleep(250);
		(new Actions(browser)).sendKeys(Keys.ARROW_LEFT).perform();
		Thread.sleep(250);

		// delete the word 'initial'
		(new Actions(browser)).sendKeys(Keys.BACK_SPACE).perform();
		Thread.sleep(250);
		(new Actions(browser)).sendKeys(Keys.BACK_SPACE).perform();
		Thread.sleep(250);
		(new Actions(browser)).sendKeys(Keys.BACK_SPACE).perform();
		Thread.sleep(250);
		(new Actions(browser)).sendKeys(Keys.BACK_SPACE).perform();
		Thread.sleep(250);
		(new Actions(browser)).sendKeys(Keys.BACK_SPACE).perform();
		Thread.sleep(250);
		(new Actions(browser)).sendKeys(Keys.BACK_SPACE).perform();
		Thread.sleep(250);
		(new Actions(browser)).sendKeys(Keys.BACK_SPACE).perform();
		Thread.sleep(250);

		// type the word 'subsequent'
		(new Actions(browser)).sendKeys("subsequent").perform();
		Thread.sleep(250);

		// select the word 'subsequent' ... easier said than done
		(new Actions(browser)).keyDown(iframe1, Keys.SHIFT).sendKeys(Keys.LEFT).sendKeys(Keys.LEFT).sendKeys(Keys.LEFT)
			.sendKeys(Keys.LEFT).sendKeys(Keys.LEFT).sendKeys(Keys.LEFT).sendKeys(Keys.LEFT).sendKeys(Keys.LEFT)
			.sendKeys(Keys.LEFT).sendKeys(Keys.LEFT).keyUp(iframe1, Keys.SHIFT).build().perform();
		Thread.sleep(250);

		// click the bold2 button to make the word 'subsequent' bold
		bold2.click();
		Thread.sleep(250);

		iframe1.click();
		Thread.sleep(250);

		// submit the form
		submit.click();
		Thread.sleep(250);

		// log some elements
		logger.log(Level.INFO, "isThere(iframe1Xpath) = " + isThere(iframe1Xpath));
		logger.log(Level.INFO, "comments1Output.getText() = " + comments1Output.getText());
		logger.log(Level.INFO, "comments2Output.getText() = " + comments2Output.getText());

		// assert to test
		assertTrue("the submitted value for the FIRST editor should be 'comments1-[b]subsequent[/b]-value', but " +
			"it is '" + comments1Output.getText() + "'",
			comments1Output.getText().equals("comments1-[b]subsequent[/b]-value"));
		assertTrue("the submitted value for the SECOND editor should be 'comments2-[b]subsequent[/b]-value', but " +
			"it is '" + comments2Output.getText() + "'",
			comments2Output.getText().equals("comments2-[b]subsequent[/b]-value"));

	}

}
