package com.ust.pagesObjectsDesign;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ust.webDriverManager.TestDriver;

/**
 * HomePage.class
 *
 * @author sbhattathiri
 * @version 1.0
 * @since 13-11-2018
 */
public class HomePage extends BasePage {

    @FindBy(xpath="//img[contains(@src,'https://static.uat.impress.ai/static/ymmb/img/transparent_landscape_logo.png')]")
    private WebElement img_logo;

    @FindBy(xpath="//a[@class='dropdown-toggle']")
    private WebElement link_userName;

    @FindBy(css=".dropdown-menu")
    private WebElement list_dropdownMenu;

    @FindBy(xpath="//a[text()='Profile']")
    private WebElement link_profile;

    @FindBy(xpath="//a[text()='Log out']")
    private WebElement link_logOut;

    @FindBy(id="menu-toggle")
    private WebElement link_hamburgerMenu;

    @FindBy(id="wrapper")
    private WebElement div_Wrapper;

    public HomePage(TestDriver testDriver){
        super(testDriver);
        PageFactory.initElements(testDriver, this);
    }

    /**
     * @return boolean : returns true if the user name matches with the passed username string
     */
    public boolean validateHomePage(String userName){
        boolean flag = false;
        String s_url = getURL();
        if(s_url.endsWith("/home/")){
            String sUserName = link_userName.getText().trim();
            if(sUserName.equalsIgnoreCase(userName)){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * This method clicks on 'Username' toggle
     */
    public void clickUserName(){
        click(link_userName);
    }

    /**
     * This method clicks on 'Profile' option
     */
    public void clickProfile(){
        click(link_profile);
    }

    /**
     * This method clicks on 'Logout' option
     */
    public void clickLogout(){
        click(link_logOut);
    }

    /**
     * This method clicks on  hamburger menu
     */
    public void clickHamburgerMenu(){
        click(link_hamburgerMenu);
    }

    /**
     * @return boolean : true if link_userName is displayed ; else false
     */
    public boolean isUserNameToggleDisplayed(){
        return link_userName.isDisplayed();
    }

    /**
     * @return boolean : true if link_profile is displayed ; else false
     */
    public boolean isProfileOptionDisplayed(){
        return link_profile.isDisplayed();
    }

    /**
     * @return boolean : true if link_logOut is displayed ; else false
     */
    public boolean isLogOutOptionDisplayed(){
        return link_logOut.isDisplayed();
    }

    /**
     * @return String : user name
     */
    public String getUserName(){
        return link_userName.getText().trim();
    }

    /**
     * @return boolean : true if link_userName is displayed ; else false
     */
    public boolean isUserNameToggleMenuExpanded(){
        return link_userName.getAttribute("aria-expanded").equalsIgnoreCase("true");
    }

    /**
     * @return boolean : true if Hamburger Menu is displayed ; else false
     */
    public boolean isHamburgerMenuPresent(){
        return link_hamburgerMenu.findElement(By.xpath("./i")).getAttribute("class").contains("fa-bars");
    }

    /**
     * @return boolean : true if hamburger menu bar is collapsed; else false
     */
    public boolean isHamburgerMenuCollapsed(){
        return div_Wrapper.getAttribute("class").length()==0;
    }

    /**
     * @return boolean : true if hamburger menu bar is expanded; else false
     */
    public boolean isHamburgerMenuExpanded(){
        return div_Wrapper.getAttribute("class").equalsIgnoreCase("active");
    }


}
