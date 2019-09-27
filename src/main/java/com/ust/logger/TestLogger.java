package com.ust.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * TestLogger.class initializes a log4j logger for logging purposes.
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class TestLogger {

    public static Logger logger = LogManager.getLogger(TestLogger.class.getName());

}
