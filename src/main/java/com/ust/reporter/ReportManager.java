package com.ust.reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.ust.configManager.ExecutionConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ReportManager.class provides method to configure the extent report file
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class ReportManager {
	private static ExtentReports extentReports;
	private static String reportFileName = new SimpleDateFormat("hh-mm-ss dd-MM-yyyy").format(new Date())
			+ "reports.html";
	private static String reportFilePath = System.getProperty("user.dir") + File.separator + "reports" + File.separator
			+ reportFileName;

	/**
	 * This method configures the appearance of the extent report
	 */
	public static ExtentReports createReportInstance() {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFilePath);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(false);
		htmlReporter.config().setTheme(Theme.DARK); // Theme.STANDARD
		htmlReporter.config().setDocumentTitle("automation run report");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("");

		extentReports = new ExtentReports();
		extentReports.attachReporter(htmlReporter);
		extentReports.setSystemInfo("Environment : ", ExecutionConfig.getInstance().getEnvironmentProperties());
		extentReports.setSystemInfo("Suite XML : ", ExecutionConfig.getInstance().getExecutionXML());

		return extentReports;
	}
}
