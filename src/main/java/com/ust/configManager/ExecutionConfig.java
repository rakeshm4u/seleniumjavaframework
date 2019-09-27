package com.ust.configManager;

import org.apache.logging.log4j.Level;

import com.ust.logger.TestLogger;

import java.io.*;
import java.util.Properties;

/**
 * ExecutionConfig.class sets the test execution variables from the
 * config/properties file
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class ExecutionConfig {

	private Properties testExecutionConfigs;

	// from execution parameters
	private static String s_environmentProperties = System.getProperty("env");
	private static String s_executionXML = System.getProperty("xml");
	private static String s_browser = System.getProperty("browser");
	private static int i_wait = Integer.parseInt(System.getProperty("wait", "300"));
	private static boolean b_isCleanUpRequired = Boolean.parseBoolean(System.getProperty("cleanUp", "false"));

	// from properties file
	private static String s_loginURL;
	private static String s_admin_userid;
	private static String s_admin_password;
	private static String s_default_profile_name;

	// singleton instance
	private static ExecutionConfig executionConfig;

	public static ExecutionConfig getInstance() {
		if (executionConfig == null) {
			executionConfig = new ExecutionConfig();
		}
		return executionConfig;
	}

	private ExecutionConfig() {
		String s_propertyFilePath;
		File f_propertyFile;
		testExecutionConfigs = new Properties();

		if ((s_environmentProperties == null) || (s_environmentProperties.length() == 0)) {
			TestLogger.logger.log(Level.INFO,
					"no environment properties defined in maven commandline, defaulting to impress.properties");
			s_environmentProperties = "Application";
		}

		// changing the folder location to src/main/java/resources
		s_propertyFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources" + File.separator + s_environmentProperties + ".properties";

		if ((s_browser == null) || (s_browser.length() == 0)) {
			s_browser = "chrome";
			TestLogger.logger.log(Level.INFO, "defaulting to chrome");
		}

		InputStream inputStream = null;
		try {
			f_propertyFile = new File(s_propertyFilePath);
			if (f_propertyFile.exists()) {
				inputStream = new FileInputStream(f_propertyFile);
				if (inputStream != null) {
					testExecutionConfigs.load(inputStream);
				}

				s_loginURL = testExecutionConfigs.getProperty("app_url");
				s_admin_userid = testExecutionConfigs.getProperty("admin_userid");
				s_admin_password = testExecutionConfigs.getProperty("admin_password");
				s_default_profile_name = testExecutionConfigs.getProperty("default_profile_name");

			} else {
				throw new RuntimeException("property file");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * @return String : browser name
	 */
	public String getBrowser() {
		return s_browser;
	}

	/**
	 * @return String : test-ng suite xml
	 */
	public String getExecutionXML() {
		return s_executionXML;
	}

	/**
	 * @return String : name of the properties file to refer to
	 */
	public String getEnvironmentProperties() {
		return s_environmentProperties;
	}

	/**
	 * @return int : implicit wait time
	 */
	public int getImplicitWait() {
		return i_wait;
	}

	/**
	 * @return boolean : if true - the contents of the {baseDir}/reports will be
	 *         deleted
	 */
	public boolean getIsCleanUpRequired() {
		return b_isCleanUpRequired;
	}

	/**
	 * @return String : login url read from properties file
	 */
	public String getLoginURL() {
		return s_loginURL;
	}

	/**
	 * @return String : admin user id read from properties file
	 */
	public String getAdminUserId() {
		return s_admin_userid;
	}

	/**
	 * @return String : admin password read from properties file
	 */
	public String getAdminUserPassword() {
		return s_admin_password;
	}

	/**
	 * @return String : default profile name read from properties file
	 */
	public String getDefaultProfileName() {
		return s_default_profile_name;
	}

}
