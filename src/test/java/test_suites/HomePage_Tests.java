package test_suites;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ust.configManager.ExecutionConfig;
import com.ust.dataProvider.TestDataProvider;
import com.ust.initializer.BaseTest;
import com.ust.pagesObjectsDesign.HomePage;
import com.ust.pagesObjectsDesign.PageInstanceFactory;

public class HomePage_Tests extends BaseTest {

	private static String s_admin_userid;
	private static String s_admin_password;
	private static String s_default_profile_name;

	@BeforeClass
	public void init() {
		s_admin_userid = ExecutionConfig.getInstance().getAdminUserId();
		s_admin_password = ExecutionConfig.getInstance().getAdminUserPassword();
		s_default_profile_name = ExecutionConfig.getInstance().getDefaultProfileName();
	}

	@Test(dataProvider = "TestDataProvider", dataProviderClass = TestDataProvider.class, description = "Check whether right side of the title bar has user name displayed with a dropdown ")
	public void test_UserNameWithDropdown(String... data) {

		Map<String, String> dataMap = TestDataProvider.constructMapOfTestData(data);
		String expected_username = dataMap.get("username");

		pageInstanceFactory = new PageInstanceFactory(testDriverThread.get());
		// pageInstanceFactory.getPageInstance(LoginPage.class).launch();
		// pageInstanceFactory.getPageInstance(LoginPage.class).login(s_admin_userid
		// ,s_admin_password);

		HomePage homePage = pageInstanceFactory.getPageInstance(HomePage.class);

		boolean isUserNameDisplayed = homePage.isUserNameToggleDisplayed();
		String sUserName = homePage.getUserName();
		boolean isUserNameCorrect = sUserName.equalsIgnoreCase(expected_username);

		baseAssertions.booleanAssert(isUserNameDisplayed, "User Name is displayed in the title bar",
				"User Name is not displayed in the title bar");
		baseAssertions.booleanAssert(isUserNameCorrect, "User Name is correct",
				"User Name is not displayed in the title bar");
	}

}
