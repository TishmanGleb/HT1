package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractPage {
    @FindBy(id = "j_username")
    private WebElement inputLogin;

    @FindBy(name = "j_password")
    private WebElement inputPassword;

    @FindBy(xpath = "//*[@id=\"yui-gen1-button\"]")
    private WebElement buttonLogin;


    public LoginPage(WebDriver driver) {

        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        inputLogin.sendKeys(username);
        inputPassword.sendKeys(password);
    }

    public void clickButtonLogin(){
    buttonLogin.click();
    }

    public String getButtonBG() {
        return Color.fromString(buttonLogin.getCssValue("background-color")).asHex();
    }
}
