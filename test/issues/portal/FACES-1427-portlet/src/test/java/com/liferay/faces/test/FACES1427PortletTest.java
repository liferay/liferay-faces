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
import org.openqa.selenium.JavascriptExecutor;

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

@RunWith(Arquillian.class)
public class FACES1427PortletTest {
	
	private final static Logger logger = Logger.getLogger(FACES1427PortletTest.class.getName());
	
	// @ArquillianResource
	// URL portalURL;
	String signInUrl = "http://localhost:8080/web/guest/signin";
	String url = "http://localhost:8080/web/guest/faces-1427";

	@Drone
	WebDriver browser;
	
	// portlet topper and menu elements
	private static final String portletDisplayNameXpath = "//header[@class='portlet-topper']/h1/span";
	@FindBy(xpath = portletDisplayNameXpath)
	private WebElement portletDisplayName;
	
	// <input id="A2399:f1:fileEntryComp::vgfq83x2" name="A2399:f1:fileEntryComp::vgfq83x2" tabindex="0" type="file">
	private static final String fileEntryComponentXpath = "//input[@type='file']";
	@FindBy(xpath = fileEntryComponentXpath)
	private WebElement fileEntryComponent;
	
	// <input id="A2399:f1:_t12" name="A2399:f1:_t12" type="submit" value="Add Attachment">
	private static final String addAttachmentXpath = "//input[@type='submit' and @value='Add Attachment']";
	@FindBy(xpath = addAttachmentXpath)
	private WebElement addAttachment;
	
	// <span id="A2399:f1:_t8:0:_t11">kitten.png</span>
	private static final String attachmentXpath = "//span[contains(text(),'kitten')]";
	@FindBy(xpath = attachmentXpath)
	private WebElement attachment;
	
	// <textarea cols="100" id="A2399:f1:comments1:inputText" name="A2399:f1:comments1:inputText" class="focus">comments1-initial-value</textarea>
	private static final String textarea1Xpath = "//textarea[contains(@id,':comments1:inputText')]";
	@FindBy(xpath = textarea1Xpath)
	private WebElement textarea1;
	
	// <img alt="Bold" buttonid="b" src="http://localhost:8080/html/themes/classic/images/message_boards/bold.png">
	private static final String bold1Xpath = "//img[@alt='Bold' and @buttonid='b' and contains(@src, 'bold.png')]";
	@FindBy(xpath = bold1Xpath)
	private WebElement bold1;
	
	// <td id="cke_contents__1_WAR_FACES1427portlet_A2399:f1:comments2" class="cke_contents" style="height:265px" role="presentation"><iframe style="width:100%;height:100%" frameborder="0" title="Rich text editor, _1_WAR_FACES1427portlet_A2399:f1:comments2, press ALT 0 for help." src="" tabindex="-1" allowtransparency="true"></iframe></td>
	// <iframe style="width:100%;height:100%" frameborder="0" title="Rich text editor, _1_WAR_FACES1427portlet_A2399:f1:comments2, press ALT 0 for help." src="" tabindex="-1" allowtransparency="true"></iframe>
	private static final String iframe1Xpath = "//td[contains(@id,':comments2')]/iframe";
	@FindBy(xpath = iframe1Xpath)
	private WebElement iframe1;
	
	// <a id="cke_7" class="cke_off cke_button_bold" "="" href="javascript:void('Bold')" title="Bold" tabindex="-1" hidefocus="true" role="button" aria-labelledby="cke_7_label" onkeydown="return CKEDITOR.tools.callFunction(0, 0, event);" onfocus="return CKEDITOR.tools.callFunction(1, 0, event);" onclick="CKEDITOR.tools.callFunction(4, this); return false;"><span class="cke_icon">&nbsp;</span><span id="cke_7_label" class="cke_label">Bold</span></a>
	private static final String bold2Xpath = "//a[contains(@class, 'cke_button_bold')]";
	@FindBy(xpath = bold2Xpath)
	private WebElement bold2;
	
	// <input id="A2399:f1:_t25" name="A2399:f1:_t25" type="submit" value="Submit">
	private static final String submitXpath = "//input[@type='submit' and @value='Submit']";
	@FindBy(xpath = submitXpath)
	private WebElement submit;
	
	// <span id="A2399:f1:comments1Output">comments1-initial-value</span>
	private static final String comments1OutputXpath = "//span[contains(@id,':comments1Output')]";
	@FindBy(xpath = comments1OutputXpath)
	private WebElement comments1Output;
	
	// <span id="A2399:f1:comments2Output">comments2-initial-value</span>
	private static final String comments2OutputXpath = "//span[contains(@id,':comments2Output')]";
	@FindBy(xpath = comments2OutputXpath)
	private WebElement comments2Output;
	
	@Before
	public void beforeEachTest() {
		
//		browser.manage().deleteAllCookies();
//		logger.log(Level.INFO, "browser.manage().deleteAllCookies() ...");
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1000)
	public void FACES1427PortletViewMode() throws Exception {
		
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		logger.log(Level.INFO, "browser.getTitle() = " + browser.getTitle());
		logger.log(Level.INFO, "browser.getCurrentUrl() = " + browser.getCurrentUrl());
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		Thread.sleep(250);
		
		logger.log(Level.INFO, "portletDisplayName.getText() = " + portletDisplayName.getText());
		
		logger.log(Level.INFO, "fileEntryComponent.isDisplayed() = " + fileEntryComponent.isDisplayed());
		logger.log(Level.INFO, "addAttachment.isDisplayed() = " + addAttachment.isDisplayed());
		
		logger.log(Level.INFO, "isThere(attachmentXpath) = " + isThere(attachmentXpath));
		// logger.log(Level.INFO, "attachment.isDisplayed() = " + attachment.isDisplayed());
		
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
		
		fileEntryComponent.sendKeys("/tmp/kitten.png");
		Thread.sleep(50);
		addAttachment.click();
		Thread.sleep(250);
		
		logger.log(Level.INFO, "attachment.isDisplayed() = " + attachment.isDisplayed());
		logger.log(Level.INFO, "attachment.getText() = " + attachment.getText());
		
		assertTrue("attachment should be listed now after clicking 'Add Attachment', but it is not there", isThere(attachmentXpath));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1100)
	public void reset() throws Exception {
		
		logger.log(Level.INFO, "browser.navigate().to("+url+")");
		browser.navigate().to(url);
		// TODO We should not have to have this reset function
		// assertTrue("We should not have to have this reset function", false);
		
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
		
		logger.log(Level.INFO, "textarea1.getAttribute('value') = " + textarea1.getAttribute("value"));
		// logger.log(Level.INFO, "iframe1.getText() = " + iframe1.getText());
		logger.log(Level.INFO, "isThere(iframe1Xpath) = " + isThere(iframe1Xpath));
		Thread.sleep(250);
		submit.click();
		Thread.sleep(250);
		logger.log(Level.INFO, "isThere(iframe1Xpath) = " + isThere(iframe1Xpath));
		
		logger.log(Level.INFO, "comments1Output.getText() = '" + comments1Output.getText() + "'");
		logger.log(Level.INFO, "comments2Output.getText() = '" + comments2Output.getText() + "'");
		
		assertTrue("the submitted value for the FIRST editor should be 'comments1-initial-value', but " +
				"it is '"+ comments1Output.getText() +"'", comments1Output.getText().equals("comments1-initial-value"));
		assertTrue("the submitted value for the SECOND editor should be 'comments2-initial-value', but " +
				"it is '"+ comments2Output.getText() +"'", comments2Output.getText().equals("comments2-initial-value"));
		
		
	}
	
	//	8. Change the word "initial" to "subsequent" in the FIRST editor and click on the bold button in order to make the word subsequent bold.
	//	9. Change the word "initial" to "subsequent" in the SECOND editor and click on the bold button in order to make the word subsequent bold.
	//	10. Click on the submit button.
	//	11. Verify that the submitted value for FIRST editor is: comments1-[b]subsequent[/b]-value
	//	12. Verify that the submitted value for SECOND editor is: comments1-[b]subsequent[/b]-value
	
	@Test
	@RunAsClient
	@InSequence(1300)
	public void steps89012() throws Exception {
		
		logger.log(Level.INFO, "textarea1.getAttribute('value') = " + textarea1.getAttribute("value"));
		textarea1.click();
		Thread.sleep(250);
		
		textarea1.sendKeys(Keys.HOME); // firefox cursor is on the left after the last click, in chromium cursor is on the right of the text ... grr
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
		textarea1.sendKeys(Keys.ARROW_RIGHT);
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
		textarea1.sendKeys(Keys.DELETE);
		Thread.sleep(250);
		textarea1.sendKeys("subsequent");
		Thread.sleep(250);
		
		logger.log(Level.INFO, "textarea1.getAttribute('id') = '" + textarea1.getAttribute("id") + "'");
		
//		((JavascriptExecutor) browser).executeScript(
//			"document.getElementById(\"" + textarea1.getAttribute("id") + "\").select();"
//		);
		
		((JavascriptExecutor) browser).executeScript(	
			"function selectText(field, start, end) { " +
				"if ( field.createTextRange ) { " +
					"var selRange = field.createTextRange(); " +
					"selRange.collapse(true); " +
					"selRange.moveStart('character', start); " +
					"selRange.moveEnd('character', end); " +
					"selRange.select(); " +
					"field.focus(); " +
				"} else if ( field.setSelectionRange ) { " +
					"field.focus(); " +
					"field.setSelectionRange(start, end); " +
				"} else if ( typeof field.selectionStart != 'undefined' ) { " +
					"field.selectionStart = start; " +
					"field.selectionEnd = end; " +
					"field.focus(); " +
				"} " +
			"}; " +
			"ta = document.getElementById('" + textarea1.getAttribute("id") + "');" +
			"selectText(ta, 10, 20);"
		);
		Thread.sleep(250);
		
		logger.log(Level.INFO, "isThere(bold1Xpath) = " + isThere(bold1Xpath));
		logger.log(Level.INFO, "bold1.isDisplayed() = " + bold1.isDisplayed());
		logger.log(Level.INFO, "bold1.getAttribute('src') = " + bold1.getAttribute("src"));
		logger.log(Level.INFO, "bold1.getLocation() = " + bold1.getLocation());
		logger.log(Level.INFO, "clicking the bold1 button ...");
		try {
			bold1.click();
		} catch (Exception e) {
			logger.log(Level.INFO, "e.getMessage() = " + e.getMessage());
			(new Actions(browser)).moveToElement(bold1, 3, 3).click(bold1).build().perform();
			Thread.sleep(2500);
			(new Actions(browser)).moveToElement(bold1, 3, 3).click().build().perform();
			Thread.sleep(2500);
			(new Actions(browser)).moveToElement(bold1).doubleClick().build().perform();
			Thread.sleep(2500);
		}
		Thread.sleep(250);
		
		logger.log(Level.INFO, "clicking into iframe1 ...");
		iframe1.click();
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
		(new Actions(browser)).sendKeys(Keys.ARROW_LEFT).perform();
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
		(new Actions(browser)).sendKeys(Keys.BACK_SPACE).perform();
		Thread.sleep(250);
		(new Actions(browser)).sendKeys("subsequent").perform();
		Thread.sleep(250);
		(new Actions(browser)).keyDown(iframe1, Keys.SHIFT)
			.sendKeys(Keys.LEFT).sendKeys(Keys.LEFT)
			.sendKeys(Keys.LEFT).sendKeys(Keys.LEFT)
			.sendKeys(Keys.LEFT).sendKeys(Keys.LEFT)
			.sendKeys(Keys.LEFT).sendKeys(Keys.LEFT)
			.sendKeys(Keys.LEFT).sendKeys(Keys.LEFT)
			.keyUp(iframe1, Keys.SHIFT).build().perform();
		Thread.sleep(250);
		bold2.click();
		Thread.sleep(250);
		iframe1.click();
		
		submit.click();
		Thread.sleep(250);
		logger.log(Level.INFO, "isThere(iframe1Xpath) = " + isThere(iframe1Xpath));
		
		logger.log(Level.INFO, "comments1Output.getText() = " + comments1Output.getText());
		logger.log(Level.INFO, "comments2Output.getText() = " + comments2Output.getText());
		
		assertTrue("the submitted value for the FIRST editor should be 'comments1-[b]subsequent[/b]-value', but " +
			"it is '"+ comments1Output.getText() +"'", comments1Output.getText().equals("comments1-[b]subsequent[/b]-value"));
		assertTrue("the submitted value for the SECOND editor should be 'comments2-[b]subsequent[/b]-value', but " +
				"it is '"+ comments2Output.getText() +"'", comments2Output.getText().equals("comments2-[b]subsequent[/b]-value"));
				
	}
	
	public boolean isThere(String xpath) {
		boolean isThere = false;
		int count = 0;
		count = browser.findElements(By.xpath(xpath)).size();
		if (count == 0) { isThere = false; }
		if (count > 0) { isThere = true; }
		if (count > 1) {
			logger.log(Level.WARNING, "The method 'isThere(xpath)' found "+count+" matches using xpath = " + xpath + 
					" ... the word 'is' implies singluar, or 1, not " + count
				);
		}
		return isThere;
	}

}
