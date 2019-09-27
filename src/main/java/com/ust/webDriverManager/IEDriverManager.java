package com.ust.webDriverManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * IEDriverManager.class provides method to get IE Driver instance
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class IEDriverManager extends DriverManager  {


    @Override
    public void startService() {
    }

    @Override
    public void stopService() {
    }

    @Override
    public void createDriver() {
        WebDriverManager.iedriver().setup();
        driver = new InternetExplorerDriver();
    }
}
