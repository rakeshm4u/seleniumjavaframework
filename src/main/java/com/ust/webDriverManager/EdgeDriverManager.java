package com.ust.webDriverManager;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;

/**
 * EdgeDriverManager.class provides method to get EdgeDriver instance
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class EdgeDriverManager extends DriverManager {

    private EdgeDriverService edgeDriverService;

    @Override
    public void startService() {
    }

    @Override
    public void stopService() {
    }

    @Override
    public void createDriver() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
    }

}
