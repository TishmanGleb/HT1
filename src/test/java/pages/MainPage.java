package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends AbstractPage {


    private final String BASE_URL = "http://localhost:8080/manage";


    @FindBy(xpath = "//*[@id=\"tasks\"]/div/a[text()='Manage Jenkins']")
    private WebElement href_manage_jenkins;

    //@FindBy(xpath = "//*[@id=\"main-panel\"]/div[15]/a[1]/dl[1]/dt[text()='Manage Users']")
    @FindBy(xpath = "//*[@id=\"main-panel\"]/div[@class='manage-option']/a/dl/dt[text()='Manage Users']")
    private WebElement href_manage_users;

    @FindBy(xpath = "//*[@id=\"main-panel\"]/div[@class='manage-option']/a/dl/dd[text()='Create/delete/modify users that can log in to this Jenkins']")
    private WebElement href_manage_users_description_text;

    @FindBy(xpath = "//*[@id=\"tasks\"]/div/a[text()='Create User']")
    private WebElement create_user;

    @FindBy(xpath = "//*[@id=\"right-top-nav\"]/div[1]/a[1]")
    private WebElement auto_refresh;


    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);

    }

    public String getHrefManageUsers() {
        return href_manage_users.getText();
    }

    public String getHrefManageUsersDescriptionText() {
        return href_manage_users_description_text.getText();
    }


    public void clickManageJenkins() {
        href_manage_jenkins.click();
    }

    public void clickManageUsers() {
        href_manage_users.click();
    }

    public void clickCreateUser() {
        create_user.click();
    }

    public void clickAutoRefresh() {
        auto_refresh.click();
    }

    public String getHrefAutoRefreshText() {
        return auto_refresh.getText();
    }

    public String getBASE_URL() {
        return BASE_URL;
    }


}
