package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;
import pages.UsersReadAllPage;

public class BaseTest {

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    protected LoginPage loginPage;
    protected UsersReadAllPage usersReadAllPage;

    @BeforeAll
    public static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.baseUrl = "http://82.142.167.37:4881/";
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.clickViaJs = true;
        Configuration.timeout = 30000;
        Configuration.browserSize = "1920x1080";
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        Configuration.browserCapabilities = options;
    }

    @BeforeEach
    public void initPages() {
        logger.info("Инициализация объектов страниц перед тестом.");
        loginPage = new LoginPage();
        usersReadAllPage = new UsersReadAllPage();
    }
}
