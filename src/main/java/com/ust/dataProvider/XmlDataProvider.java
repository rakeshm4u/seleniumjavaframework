package com.ust.dataProvider;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class XmlDataProvider extends DataProviderXmlUtils {

	/**
	 * @author Rakesh M	 
	 */

	/**
	 * Method to get the data from xml file
	 * 
	 * @param m
	 * @return
	 */
	@DataProvider(name = "Framework", parallel = true)
	public static Object[][] getXmlTestData(Method m) {
		return getTestData(getTestDataXmlFileName(m.getAnnotation(Test.class)), m.getName(), DataObjectInfo.class);
	}

	/**
	 * Method to get the xml file name as per environment on which test will be
	 * executed
	 * 
	 * @param test
	 * @return xml file name
	 */
	private static String getTestDataXmlFileName(Test test) {
		String dataprovider = test.dataProvider();
		return dataprovider + ".xml";

	}

}
