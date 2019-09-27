package com.ust.webDriverManager;

/**
 * DriverManagerFactory.class provides the corresponding driver manager for the browser name
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class DriverManagerFactory {

    /**
     * This method provides the corresponding driver manager for the browser name
     * @param sBrowser : String browser-name
     * @return DriverManager
     */
    public static DriverManager getDriverManager(String sBrowser){
        DriverManager driverManager = null;

        if(sBrowser.equalsIgnoreCase("CHROME")){
            driverManager = new ChromeDriverManager();
        }
        else if(sBrowser.equalsIgnoreCase("FIREFOX")){
            driverManager = new FirefoxDriverManager();
        }
        else if(sBrowser.equalsIgnoreCase("SAFARI")){
            driverManager = new SafariDriverManager();
        }
        else if(sBrowser.equalsIgnoreCase("EDGE")){
            driverManager = new EdgeDriverManager();
        }
        else if(sBrowser.equalsIgnoreCase("IE")){
            driverManager = new IEDriverManager();
        }
        else if(sBrowser.equalsIgnoreCase("REMOTE_WEB_DRIVER")){

        }

        return driverManager;
    }
}
