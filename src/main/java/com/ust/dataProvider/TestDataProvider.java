package com.ust.dataProvider;

import org.testng.Assert;
import org.testng.annotations.DataProvider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * TestDataProvider.class provides methods to feed the test data to the calling
 * test method
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class TestDataProvider {

	/**
	 * This method to feed the test data to the calling test method
	 * 
	 * @param callingMethod
	 *            : calling method signature
	 * @return Object[][]
	 */
	@DataProvider(name = "TestDataProvider")
	public Object[][] testDataProvider(java.lang.reflect.Method callingMethod) {
		boolean isDataPresentinDataFile = false;

		String methodName = callingMethod.getName();
		String fullyQualifiedClassName = callingMethod.getDeclaringClass().getName();

		Set<String[]> testDataSet = TestDataFileReader.getTestDataSet(fullyQualifiedClassName);

		Object[][] testData = new Object[testDataSet.size()][];
		int testDataCount = 0;
		Iterator<String[]> testDataSetIterator = testDataSet.iterator();
		while (testDataSetIterator.hasNext()) {
			String[] data = testDataSetIterator.next();
			String testMethodNameInDataFile = data[0].trim();
			if (testMethodNameInDataFile == null || (testMethodNameInDataFile.length() == 0)) {
				continue;
			}
			if (methodName.equalsIgnoreCase(testMethodNameInDataFile)) {
				testData[testDataCount] = data[1].split(",");
				testDataCount++;
				isDataPresentinDataFile = true;
			}
		}
		if (!isDataPresentinDataFile) {
			Assert.assertTrue(false, "DATA_ERROR: no test data found");
		}

		return testData;
	}

	/**
	 * This method constructs a map of test data fed to the method as object
	 * array
	 * 
	 * @param dataSet
	 *            : data passed to the test method
	 * @return HashMap : map of test data
	 */
	public static Map<String, String> constructMapOfTestData(String[] dataSet) {
		Map<String, String> mapOfTestData = new HashMap<String, String>();
		for (String data : dataSet) {
			String[] keyValuePairs = data.split(",");
			for (String keyValuePair : keyValuePairs) {
				String dataKey = keyValuePair.split("=")[0];
				String dataValue = keyValuePair.split("=")[1];
				if (dataValue.contains(":eq:")) {
					dataValue = dataValue.replaceAll(":eq:", "=");
				}
				if (dataValue.contains(":cma:")) {
					dataValue = dataValue.replaceAll(":cma:", ",");
				}
				mapOfTestData.put(dataKey, dataValue);
			}
		}
		return mapOfTestData;
	}

}
