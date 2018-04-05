package JenkinsTests;

import driver.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CreateUserPage;
import pages.LoginPage;
import pages.MainPage;
import pages.UsersPage;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class AllTests {


    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private UsersPage usersPage;
    private CreateUserPage createUserPage;
    private static final Logger logger = LogManager.getRootLogger();

    private StringBuilder errors_msg = new StringBuilder();
    private List<WebElement> typeTextElements;
    private List<WebElement> typePasswordElements;

    //for checking during tests that all buttons have this bg_color
    private final String BG_COLOR_BUTTON = "#4b758b";

    private String userNameLogIn = "admin";
    private String userPasswordLogIn = "admin";
    private String newUserName = "someuser";
    private String newPassword = "somepassword";
    private String newPasswordConfirm = "somepassword";
    private String newFullName = "Some JenkinsTests.Full Name";


    @BeforeMethod
    public void beforeClass() {
        this.driver = Driver.getDriver();
        loginPage = new LoginPage(driver);
        loginPage.login(userNameLogIn, userPasswordLogIn);
    }


    @Test
    public void allSteps() {

        logger.info("Starting main tests by task HT1");

        Assert.assertEquals(loginPage.getButtonBG(), BG_COLOR_BUTTON);
        loginPage.clickButtonLogin();
        logger.info("Login confirm");


        mainPage = new MainPage(driver);
        mainPage.clickManageJenkins();

        logger.info("Open manage page URL-" + mainPage.getBASE_URL());
        //check elements with text "Manage Users" and "Create/delete/modify users that can log in to this Jenkins"
        try {
            Assert.assertEquals(mainPage.getHrefManageUsers(), "Manage Users");
        } catch (Error e) {
            logger.error("Elements with text 'Manage Users' aren't on the page");
        }

        try {
            Assert.assertEquals(mainPage.getHrefManageUsersDescriptionText(),
                    "Create/delete/modify users that can log in to this Jenkins");
        } catch (Error e) {
            logger.error("Elements with text 'Create/delete/modify users that can log in to this Jenkins' aren't on the page");
        }


        mainPage.clickManageUsers();
        mainPage.clickCreateUser();


        createUserPage = new CreateUserPage(driver);
        logger.info("Open create user page page URL-" + createUserPage.getBASE_URL());

        //init typeTextElements with elements that have type = text
        typeTextElements = createUserPage.getFieldsTypeText();
        //init typeTextElements with elements that have type = password
        typePasswordElements = createUserPage.getFieldsTypePassword();

        //check that three elements have "text" type
        try {
            Assert.assertEquals(typeTextElements.size(), 3);
            logger.info("There are three elements with type 'text' on page");
        } catch (Error e) {
            errors_msg.append("Only " + typeTextElements.size() + " elements type 'text' exist, instead three.\nElements that exist: ");
            for (WebElement i : typeTextElements)
                errors_msg.append("[" + i.getAttribute("name") + "] ");
            errors_msg.append("\nElements that must be: [username] [fullname] [e-mailaddres]");
            logger.error(errors_msg);
        }
        //check that two elements have "password" type
        try {
            Assert.assertEquals(typePasswordElements.size(), 2);
            logger.info("There are three elements with type 'password' on page.");
        } catch (Error e) {
            errors_msg.append("Only " + typePasswordElements.size() + " elements type 'text' exist, instead two.\nElements that exist: ");
            for (WebElement i : typePasswordElements)
                errors_msg.append("[" + i.getAttribute("name") + "] ");
            errors_msg.append("\nElements that must be: [password1] [password2]");
            logger.error(errors_msg);
        }


        //check that all field values were empty(clear)
        for (WebElement i : typeTextElements) {
            try {
                Assert.assertEquals(i.getAttribute("value"), "");
                logger.info("Field [" + i.getAttribute("text") + "] is clear(empty).");
            } catch (Error e) {
                logger.error("Field [" + i.getAttribute("text") + "] isn't clear(empty).");
            }
        }
        for (WebElement i : typePasswordElements) {
            try {
                Assert.assertEquals(i.getAttribute("value"), "");
                logger.info("Field [" + i.getAttribute("text") + "] is clear(empty).");
            } catch (Error e) {
                logger.error("Field [" + i.getAttribute("text") + "] isn't clear(empty).");
            }
        }


        createUserPage.createNewUser(newUserName, newPassword, newPasswordConfirm, newFullName);

        //check that all field filled with the correct values
        try {
            Assert.assertEquals(createUserPage.getUsernameValue(), newUserName);
            logger.info("Field UserName correct filled.");
        } catch (Error e) {
            logger.error("Field UserName isn't correct filled. Value must be [" + newUserName + "].");
        }
        try {
            Assert.assertEquals(createUserPage.getPasswordValue(), newPassword);
            logger.info("Field Password correct filled.");
        } catch (Error e) {
            logger.error("Field Password isn't correct filled. Value must be [" + newPassword + "].");
        }
        try {
            Assert.assertEquals(createUserPage.getPasswordConfirmValue(), newPasswordConfirm);
            logger.info("Field Password Confirm correct filled.");
        } catch (Error e) {
            logger.error("Field Password Confirm isn't correct filled. Value must be [" + newPasswordConfirm + "].");
        }
        try {
            Assert.assertEquals(createUserPage.getFullNameValue(), newFullName);
            logger.info("Field Full Name Confirm correct filled.");
        } catch (Error e) {
            logger.error("Field Full Name Confirm isn't correct filled. Value must be [" + newFullName + "].");
        }


        Assert.assertEquals(createUserPage.getButtonBG(), BG_COLOR_BUTTON);
        createUserPage.clickCreateUserButton();

        usersPage = new UsersPage(driver, newUserName);

        logger.info("Open security realm page URL-" + usersPage.getBASE_URL());

        //check that new user was create with name= 'newUserName'
        try {
            Assert.assertEquals(usersPage.findNewUser(), newUserName);
            logger.info("User [" + newUserName + "] is created");
        } catch (Error e) {
            logger.error("Cant find created user with name [" + newUserName + "].");
        }


        //check correct message when a user will try to be delete entry
        try {
            Assert.assertEquals(usersPage.deleteUser(), "Are you sure about deleting the user from Jenkins?");
            logger.info("Message ,when user want to delete entry, is correct.");
        } catch (Error e) {
            logger.error("Message when user want to delete entry is[" + usersPage.deleteUser() + "]. Message must be - 'Are you sure about deleting the user from Jenkins?'");
        }

        Assert.assertEquals(usersPage.getButtonBG(), BG_COLOR_BUTTON);
        usersPage.clickDeleteButton();

        logger.info("Delete entry.");

        //check user deleted and link 'user/" + username + "/delete' deleted too
        try {
            Assert.assertFalse(usersPage.checkUserNameInTable());
            logger.info("Record deleted.");
        } catch (Error e) {
            logger.error("Record not deleted.");
        }
        //check link 'user/" + username + "/delete' deleted
        try {
            Assert.assertFalse(usersPage.checkDeleteHref());
            logger.info("Link 'user/" + newUserName + "/delete' deleted.");
        } catch (Error e) {
            logger.error("Link 'user/" + newUserName + "/delete' not deleted.");
        }

        //check that without performing any actions there is no 'user/admin/delete' link
        try {
            Assert.assertFalse(usersPage.checkDeleteLinkIfThereWereNoActions());
            logger.info("Without performing any actions there is no 'user/admin/delete' link");
        } catch (Error e) {
            logger.error("Without performing any actions there is 'user/admin/delete' link");
        }

        logger.info("Main tests by task HT1 performed.");
    }


    @Test
    public void createWithEmptyName() {

        loginPage.clickButtonLogin();
        logger.info("Starting test 'Check error msq when try to create user with empty name' by task HT1");

        createUserPage = new CreateUserPage(driver);
        driver.navigate().to(createUserPage.getBASE_URL());

        createUserPage.createNewUser("", newPassword, newPasswordConfirm, newFullName);

        createUserPage.clickCreateUserButton();

        //check text error when try to create user with empty username
        try {
            Assert.assertEquals(createUserPage.getErrorMSG(), "\"\" is prohibited as a username for security reasons.");
            logger.info("Error msg is correct");
        } catch (Error e) {
            logger.error("Error msq is incorrect.");
        }

        logger.info("Test 'Check error' performed.");

    }


    @Test
    public void checkAutoRefresh() {

        loginPage.clickButtonLogin();

        logger.info("Starting test 'Links change each other'");
        mainPage = new MainPage(driver);

        //check links change each other
        for (int i = 0; i < 6; i++) {
            try {
                Assert.assertEquals(mainPage.getHrefAutoRefreshText(), "ENABLE AUTO REFRESH");
                mainPage.clickAutoRefresh();
                driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
                logger.info("Link 'ENABLE AUTO REFRESH' after click changed to 'DISABLE AUTO REFRESH' link.");
            } catch (Error e) {
                logger.error("Link 'ENABLE AUTO REFRESH' after click didn't change to 'DISABLE AUTO REFRESH' link.");
                break;
            }
            try {
                Assert.assertEquals(mainPage.getHrefAutoRefreshText(), "DISABLE AUTO REFRESH");
                mainPage.clickAutoRefresh();
                driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
                logger.info("Link 'DISABLE AUTO REFRESH' after click changed to 'ENABLE AUTO REFRESH' link.");
            } catch (Error e) {
                logger.error("Link 'DISABLE AUTO REFRESH' after click didn't change to 'ENABLE AUTO REFRESH' link.");
                break;
            }
        }

        logger.info("Test 'Links change each other' performed.");
    }


    @AfterMethod
    public void stopBrowser() {
        Driver.closeDriver();
    }

}
