package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.AllPostPage;
import pages.LoginPage;
import pages.UsersReadAllPage;
import pages.UsersReadUserWithCar;

public class BaseTest {

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    protected LoginPage loginPage;
    protected UsersReadAllPage usersReadAllPage;
    protected UsersReadUserWithCar usersReadUserWithCar;
    protected AllPostPage allPostPage;

    @BeforeClass
    public static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.baseUrl = "http://82.142.167.37:4881/";
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.clickViaJs = true;
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        Configuration.browserCapabilities = options;
    }

    @BeforeMethod
    public void initPages() {
        logger.info("Инициализация объектов страниц перед тестом.");
        loginPage = new LoginPage();
        usersReadAllPage = new UsersReadAllPage();
        usersReadUserWithCar = new UsersReadUserWithCar();
        allPostPage = new AllPostPage();
    }
}