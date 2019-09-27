package com.ust.webDriverManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * FirefoxDriverManager.class provides method to get Firefox Driver instance
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class FirefoxDriverManager extends DriverManager {

    @Override
    public void startService() {
    }

    @Override
    public void stopService() {
    }

    @Override
    public void createDriver() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }
}
