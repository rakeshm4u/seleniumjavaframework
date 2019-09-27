package com.ust.webDriverManager;

import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariDriverService;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * SafariDriverManager.class provides method to get Safari Driver instance
 *
 * @author sbhattathiri
 * @version 1.0
 * @since 13-11-2018
 */
public class SafariDriverManager extends DriverManager {

    private SafariDriverService safariDriverService;

    @Override
    public void startService() {
        if (null == safariDriverService) {
            try {
                safariDriverService = new SafariDriverService.Builder()
                        .usingAnyFreePort()
                        .build();
                safariDriverService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stopService() {
        if (null != safariDriverService && safariDriverService.isRunning())
            safariDriverService.stop();
    }

    @Override
    public void createDriver() {
        driver = new SafariDriver();
    }
}
