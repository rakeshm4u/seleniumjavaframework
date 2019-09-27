package com.ust.initializer;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ust.configManager.ExecutionConfig;
import com.ust.logger.TestLogger;
import com.ust.pagesObjectsDesign.PageInstanceFactory;
import com.ust.reporter.BaseAssertions;
import com.ust.reporter.ReportManager;
import com.ust.utils.FileUtils;
import com.ust.webDriverManager.TestDriver;

import org.apache.logging.log4j.Level;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * BaseTest.class provides implementations for test-ng annotated methods
 *
 * @author Rakesh M
 * @version 1.1
 * @since 13-11-2018
 */
public class BaseTest {

	//for report file name
	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		String s_timestamp = dateFormat.format(new Date()) + "";
		System.setProperty("automationFrameworkLogFileName", s_timestamp);
	}

	public BaseAssertions baseAssertions;
	public PageInstanceFactory pageInstanceFactory;

	public static ThreadLocal<TestDriver> testDriverThread = new ThreadLocal<TestDriver>();
	public TestDriver test_driver;

	public static ExtentReports extentReports = ReportManager.createReportInstance();
	public ExtentTest extentTest;

	public static ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<ExtentTest>();


	/**
	 * The annotated method will be run before all tests in this suite have run
	 * @param iTestContext
	 */
	@BeforeSuite
	public void beforeSuite(ITestContext iTestContext) {
		if (ExecutionConfig.getInstance().getIsCleanUpRequired()) {
			FileUtils.delete_directory_contents(System.getProperty("user.dir") + File.separator + "reports");
		}
		// for logs
		String s_executionXML = ExecutionConfig.getInstance().getExecutionXML();
		if (s_executionXML == null) {
			s_executionXML = iTestContext.getCurrentXmlTest().getSuite().getFileName();
		}

		TestLogger.logger.log(Level.INFO, "initialized extent reports for test name : " + s_executionXML);
	}

	/**
	 * The annotated method will be run before each test method
	 * @param method
	 */
	@BeforeMethod
	public void beforeMethod(Method method) {
		Test test = method.getDeclaredAnnotation(Test.class);

		extentTest = extentReports.createTest(method.getName(), test.description());
		extentTestThread.set(extentTest);

		test_driver = new TestDriver(extentTestThread.get());
		testDriverThread.set(test_driver);
		baseAssertions = new BaseAssertions(testDriverThread.get());
	}

	/**
	 * The annotated method will be run after each test method. This does the reporting (extent reports) for the status of the test case
	 * @param iTestResult
	 */
	@AfterMethod
	public void afterMethod(ITestResult iTestResult) {

		if (iTestResult.getStatus() == ITestResult.SUCCESS) {
			extentTestThread.get().pass(iTestResult.getMethod().getMethodName() + " is a pass");
		} else if (iTestResult.getStatus() == ITestResult.FAILURE) {
			extentTestThread.get().fail(iTestResult.getThrowable());
			String screenshotFile = testDriverThread.get().takeScreenShot(iTestResult.getMethod().getMethodName());
			try {
				System.out.println(screenshotFile);
				extentTestThread.get().addScreenCaptureFromPath(screenshotFile,
						iTestResult.getMethod().getMethodName());

			} catch (IOException ioe) {
				extentTestThread.get().log(Status.INFO, "unable to attach screenshot");
			}
		} else if (iTestResult.getStatus() == ITestResult.SKIP) {
			extentTestThread.get().skip(iTestResult.getThrowable());
		}

		testDriverThread.get().quit();
	}

	/**
	 * The annotated method will be run after all tests in this suite have run.
	 * @param iTestContext
	 */
	@AfterSuite(alwaysRun = true)
	public void tearDown(ITestContext iTestContext) {
		extentReports.flush();
		TestLogger.logger.log(Level.INFO, "flushing extent reports");
		createSummaryReport(iTestContext);
	}

	/**
	 * This method used to create summary report.
	 * @param iTestContext
	 */
	public void createSummaryReport(final org.testng.ITestContext iTestContext) {
		try {
			final StringBuilder htmlStringBuilder = new StringBuilder();
			final String browser = "chrome";//System.getProperty("browser");
			final String environment = "uat";//System.getProperty("env");
			htmlStringBuilder.append("<html><head><title>Automation Summary Report</title></head>");
			htmlStringBuilder.append("<body bgcolor=\"#DFE6E6\">");
			htmlStringBuilder.append("<h4>Suite Execution Summary:-</h4>");
			htmlStringBuilder.append("<table border=\"1\" bordercolor=\"#000000\">");
			htmlStringBuilder.append(
					"<tr bgcolor=\"#0d6c73\"><td align='center'><font color=\"#FFFFFF\">Suite</font></td><td align='center'><font color=\"#FFFFFF\">Start Time</font></td><td align='center'><font color=\"#FFFFFF\">End Time</font></td><td align='center'><font color=\"#FFFFFF\">Duration</font></td><td align='center'><font color=\"#FFFFFF\">#Executed</font></td><td align='center'><font color=\"#FFFFFF\">#Passed</font></td><td align='center'><font color=\"#FFFFFF\">#Failed</font></td><td align='center'><font color=\"#FFFFFF\">#Skipped</font></td><td align='center'><font color=\"#FFFFFF\">Browser</font></td><td align='center'><font color=\"#FFFFFF\">Environment</font></td></tr>");
			htmlStringBuilder.append("<tr><td align='center'>" + iTestContext.getCurrentXmlTest().getSuite().getName()
					+ "</td><td align='center'>" + iTestContext.getStartDate() + "</td><td align='center'>"
					+ iTestContext.getEndDate() + "</td><td align='center'>"
					+ getTotalExecutionTime(iTestContext.getStartDate(), iTestContext.getEndDate())
					+ "</td><td align='center'>" + String.valueOf(iTestContext.getAllTestMethods().length)
					+ "</td><td align='center'>" + String.valueOf(iTestContext.getPassedTests().size())
					+ "</td><td align='center'>" + String.valueOf(iTestContext.getFailedTests().size())
					+ "</td><td align='center'>" + String.valueOf(iTestContext.getSkippedTests().size())
					+ "</td><td align='center'>" + browser + "</td><td align='center'>" + environment + "</td></tr>");
			htmlStringBuilder.append("</table></body></html>");
			// write html string content to a file
			writeToFile(htmlStringBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to get difference between two date.
	 *
	 * @param startDate
	 *            Start date
	 * @param endDate
	 *            End date
	 * @return String have time difference details
	 */
	public String getTotalExecutionTime(final Date startDate, final Date endDate) {
		final long timeDiff = Math.abs(startDate.getTime() - endDate.getTime());
		return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeDiff),
				TimeUnit.MILLISECONDS.toMinutes(timeDiff)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)),
				TimeUnit.MILLISECONDS.toSeconds(timeDiff)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDiff)));
	}

	/**
	 * This method used to write file content to html file.
	 *
	 * @param fileContent
	 *            fileContent
	 * @throws IOException
	 *             IOException
	 */
	public static void writeToFile(final String fileContent) throws IOException {
		final File file = new File("reports", "Impress_Automation_Summary_Report.html");
		final OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
		final Writer writer = new OutputStreamWriter(outputStream);
		writer.write(fileContent);
		writer.close();
	}

}
