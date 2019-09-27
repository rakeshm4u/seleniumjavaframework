package com.ust.pagesObjectsDesign;

import com.aventstack.extentreports.Status;
import com.ust.webDriverManager.TestDriver;

/**
 * PageInstanceFactory.class provides method to instantiate a page class
 *
 * @author Rakesh M
 * @version 1.0
 * @since 13-11-2018
 */
public class PageInstanceFactory {

	public TestDriver testDriver;

	public PageInstanceFactory(TestDriver tDriver) {
		this.testDriver = tDriver;
	}

	/**
	 * This method instantiates the requested page class
	 * 
	 * @param pageClass
	 *            : web element to be clicked
	 * @return pageInstance : returns null if page could not be instantiated
	 */
	public <TPage extends BasePage> TPage getPageInstance(Class<TPage> pageClass) {
		try {
			testDriver.getExtentTest().log(Status.INFO, "initializing " + pageClass.getName());
			return pageClass.getDeclaredConstructor(TestDriver.class).newInstance(this.testDriver);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
