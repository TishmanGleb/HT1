package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CreateUserPage extends AbstractPage {

    private final String BASE_URL = "http://localhost:8080/securityRealm/addUser";

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(name = "password1")
    private WebElement passwordInput;

    @FindBy(name = "password2")
    private WebElement passwordInputConfirm;

    @FindBy(name = "fullname")
    private WebElement fullNameInput;

    @FindBy(xpath = "//*[@id=\"yui-gen1-button\"]")
    private WebElement buttonCreateUser;

    @FindBy(xpath = "//*[@id=\"main-panel\"]/form[1]")
    private WebElement formWithFields;


    public CreateUserPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getErrorMSG() {
        try {
            return driver.findElement(By.xpath("//*[@id=\"main-panel\"]/form/div/div[@class='error']")).getText();
        } catch (Exception e) {
            return "There's no error message.";
        }
    }


    public List<WebElement> getFieldsTypeText() {

        return driver.findElements(By.xpath("//*[@type='text']"));
    }

    public List<WebElement> getFieldsTypePassword() {

        return driver.findElements(By.xpath("//*[@type='text']"));
    }


    public String getUsernameValue() {
        return usernameInput.getAttribute("value");
    }


    public String getPasswordValue() {
        return passwordInput.getAttribute("value");
    }


    public String getPasswordConfirmValue() {
        return passwordInputConfirm.getAttribute("value");
    }


    public String getFullNameValue() {
        return fullNameInput.getAttribute("value");
    }


    public void createNewUser(String userName, String password, String confirmPassword, String fullName) {

        usernameInput.sendKeys(userName);
        passwordInput.sendKeys(password);
        passwordInputConfirm.sendKeys(confirmPassword);
        fullNameInput.sendKeys(fullName);
    }

    public void clickCreateUserButton() {
        buttonCreateUser.click();
    }

    public String getButtonBG() {
        return Color.fromString(buttonCreateUser.getCssValue("background-color")).asHex();
    }

}
