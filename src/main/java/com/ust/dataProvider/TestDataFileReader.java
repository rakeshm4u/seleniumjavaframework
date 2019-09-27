package com.ust.dataProvider;

import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * TestDataFileReader.class provides methods to read test data from the .dat
 * file into a Set
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class TestDataFileReader {

	/**
	 * This method to read test data from the .dat file into a Set
	 * 
	 * @param fullyQualifiedClassName
	 *            : fully qualified class name of the test class
	 * @return Set : set of test data read from the .dat file
	 */
	public static Set<String[]> getTestDataSet(String fullyQualifiedClassName) {
		Set<String[]> testDataSet = new HashSet<String[]>();
		String packageName = "";
		String className = "";

		try {
			packageName = fullyQualifiedClassName.split("\\.")[0];
			className = fullyQualifiedClassName.split("\\.")[1];
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			aioobe.printStackTrace();
		}

		String dataFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "java" + File.separator + packageName + File.separator + "data" + File.separator
				+ className + ".dat";

		BufferedReader bufferedReader = null;
		FileReader fileReader = null;

		try {
			File dataFile = new File(dataFilePath);
			fileReader = new FileReader(dataFile);
			bufferedReader = new BufferedReader(fileReader);

			String fileLine;

			while ((fileLine = bufferedReader.readLine()) != null) {
				if (!testDataSet.add(fileLine.trim().split("\\|"))) {
					Assert.assertTrue(false, "DATA_ERROR: duplicate test data found");
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}

				if (fileReader != null) {
					fileReader.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return testDataSet;
	}

}
