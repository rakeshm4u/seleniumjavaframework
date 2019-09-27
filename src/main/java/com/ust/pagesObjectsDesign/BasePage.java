package com.ust.pagesObjectsDesign;

import com.aventstack.extentreports.Status;
import com.ust.webDriverManager.TestDriver;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.util.concurrent.TimeUnit;

/**
 * BasePage.class provides generic methods for DOM interaction
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class BasePage extends PageInstanceFactory {

	public BasePage(TestDriver tDriver) {
		super(tDriver);
	}

	/**
	 * This method receives the web element and performs a click on it and then
	 * waits for the jquery to finish.
	 * 
	 * @param webElement
	 *            : web element to be clicked
	 */
	public void click(WebElement webElement) {
		testDriver.getExtentTest().log(Status.INFO, "clicking : " + (webElement).getText());

		Wait<WebDriver> wait = new FluentWait<>(testDriver.getDriver()).withTimeout(90000, TimeUnit.MILLISECONDS)
				.pollingEvery(5500, TimeUnit.MILLISECONDS);

		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				try {
					webElement.click();
					return true;
				} catch (StaleElementReferenceException e) {
					testDriver.getExtentTest().log(Status.INFO, "attempting click on element ");
					return false;
				}
			}
		});

		waitForPageLoad();
	}

	/**
	 * This method inputs the text into the web element. Any default value is
	 * cleared and overwritten.
	 * 
	 * @param webElement
	 *            : input web element to be written into
	 * @param sText
	 *            : text to written
	 */
	public void writeText(WebElement webElement, String sText) {
		testDriver.getExtentTest().log(Status.INFO, "entering text : " + sText);
		webElement.clear();
		webElement.sendKeys(sText);
	}

	/**
	 * This method returns the current URL
	 * 
	 * @return String : current browser URL
	 */
	public String getURL() {
		String sURL = testDriver.getCurrentUrl();
		testDriver.getExtentTest().log(Status.INFO, "current url: " + sURL);
		return sURL;
	}

	/**
	 * This method waits for any js/jquery to finish loading
	 * 
	 * @return boolean : true if pageLoad is complete
	 */
	public boolean waitForPageLoad() {
		WebDriverWait wait = new WebDriverWait(testDriver.getDriver(), 30);
		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					// no jQuery present
					return true;
				}
			}
		};
		// wait for javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

}
