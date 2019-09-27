package com.ust.utils;

import java.io.File;

/**
 * FileUtils.class provides various methods for file manipulations
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class FileUtils {

	/**
	 * This method deletes the contents of the directory (path specified)
	 */
	public static void delete_directory_contents(String dir_path) {
		File directory = new File(dir_path);
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					delete_directory_contents(file.getAbsolutePath());
				} else {
					file.delete();
				}
			}
		}
	}
}
