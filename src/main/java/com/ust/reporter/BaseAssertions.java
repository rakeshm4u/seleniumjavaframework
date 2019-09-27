package com.ust.reporter;

import com.aventstack.extentreports.Status;
import com.ust.webDriverManager.TestDriver;

import org.testng.asserts.SoftAssert;

/**
 * BaseAssertions.class provides a way to report on assertions that 'Pass' in
 * addition to assertions that 'Fail'
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class BaseAssertions {

	public TestDriver testDriver;
	private SoftAssert softAssert = new SoftAssert();

	public BaseAssertions(TestDriver tDriver) {
		testDriver = tDriver;
	}

	/**
	 * This method reports on the status of assertion (verification point)
	 * 
	 * @param bool
	 *            : boolean value of assertion condition
	 * @param passMessage
	 *            : message that should be reported when the assertion is a pass
	 * @param failMessage
	 *            : message that should be reported when the assertion is a fail
	 */
	public void booleanAssert(boolean bool, String passMessage, String failMessage) {
		softAssert.assertTrue(bool, failMessage);
		if (bool) {
			testDriver.getExtentTest().log(Status.PASS, "passed: " + passMessage);
		} else {
			testDriver.getExtentTest().log(Status.FAIL, "failed: " + failMessage);
		}
		softAssert.assertAll();
	}
}
