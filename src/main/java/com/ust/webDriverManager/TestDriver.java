package com.ust.webDriverManager;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ust.configManager.ExecutionConfig;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * TestDriver.class provides a custom implementation of WebDriver interface
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class TestDriver implements WebDriver {

    //from execution config
    private String sBrowser;
    private int iWait;

    private WebDriver driver;

    private ExtentTest extentTest ;

    public TestDriver(ExtentTest extentTest){
        this.extentTest = extentTest;

        sBrowser = ExecutionConfig.getInstance().getBrowser();
        DriverManager driverManager = DriverManagerFactory.getDriverManager(sBrowser);
        driver = driverManager.getDriver();

        iWait = ExecutionConfig.getInstance().getImplicitWait();
        driver.manage().timeouts().implicitlyWait(iWait, TimeUnit.SECONDS);

        driver.manage().window().maximize();
    }

    /**
     * @return ExtentTest : extentTest
     */
    public ExtentTest getExtentTest() {
        return extentTest;
    }

    /**
     * wrapper on webdriver.get()
     */
    public void get(String s) {
        extentTest.log(Status.INFO,"hitting url : "+s);
        driver.get(s);
    }

    /**
     * @return WebDriver : driver instance
     */
    public WebDriver getDriver(){
        return driver;
    }

    /**
     * @return String: get current browser url
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * @return String: get current browser page title
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * @return List<WebElement> : wrapper on findElements(by)
     */
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    /**
     * @return WebElement : wrapper on findElement(by)
     */
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    /**
     * @return String: page source of the current browser page
     */
    public String getPageSource() {
        return driver.getPageSource();
    }

    /**
     *  wrapper on webdriver.close()
     */
    public void close() {
        extentTest.log(Status.INFO,"testDriver close");
        driver.close();
    }

    /**
     *  wrapper on webdriver.quit()
     */
    public void quit() {
        extentTest.log(Status.INFO,"testDriver quit");
        driver.quit();
    }

    /**
     * @return Set<String> : wrapper on webdriver.getWindowHandles();
     */
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    /**
     * @return String : wrapper on webdriver.getWindowHandle();
     */
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    /**
     * @return TargetLocator : wrapper on webdriver.switchTo();
     */
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    /**
     * @return Navigation : wrapper on webdriver.navigate();
     */
    public Navigation navigate() {
        return driver.navigate();
    }

    /**
     * @return Options : wrapper on webdriver.manage();
     */
    public Options manage() {
        return driver.manage();
    }

    /**
     * This method takes screenshot of the current browser window
     * @param testName : String, name for screenshot file
     * @return String : wrapper on webdriver.manage();
     */
    public String takeScreenShot(String testName) {
        testName = testName.replaceAll("\\.", "_");
        String filePath = System.getProperty("user.dir")+ File.separator +"logs"+ File.separator +"screenshots"+ File.separator;
        String fileName = filePath+System.currentTimeMillis()+"_"+testName+".png";
        File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, new File(fileName));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        extentTest.log(Status.INFO,"captured screenshot");
        return fileName;
    }




}
