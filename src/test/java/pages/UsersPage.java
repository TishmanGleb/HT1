package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UsersPage extends AbstractPage {


    private final String BASE_URL = "http://localhost:8080/securityRealm/";

    @FindBy(xpath = "//*[@id=\"yui-gen1-button\"]")
    private WebElement buttonDelete;

    private WebElement userName;
    private WebElement deleteHref;

    private WebDriver driver;


    public UsersPage(WebDriver driver, String username) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.driver = driver;
        try {
            this.userName = driver.findElement(By.xpath("//*[@id=\"people\"]/tbody/tr/td[2]/a[@href='user/" + username + "/']"));
        } catch (Exception e) {
            System.out.println("User [" + username + "] not found.");
        }
        try {
            this.deleteHref = driver.findElement(By.xpath("//*[@id=\"people\"]/tbody/tr/td/a[@href='user/" + username + "/delete']"));
        } catch (Exception e) {
            System.out.println("Link [user/" + username + "/delete] not found.");
        }
    }


    public String findNewUser() {
        return userName.getText();
    }


    public String deleteUser() {
        deleteHref.click();
        return driver.findElement(By.xpath("//*[@id=\"main-panel\"]/form[1]")).getText().split("\n")[0];
    }

    public void clickDeleteButton() {
        buttonDelete.click();
    }

    public String getButtonBG() {
        return Color.fromString(buttonDelete.getCssValue("background-color")).asHex();
    }

    public boolean checkUserNameInTable() {
        try {
            return userName.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkDeleteHref() {
        try {
            return deleteHref.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkDeleteLinkIfThereWereNoActions() {
        try {
            return driver.findElement(By.xpath("//*[@id=\"people\"]/tbody/tr/td/a[@href='user/admin/delete']")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getBASE_URL() {
        return BASE_URL;
    }
}
