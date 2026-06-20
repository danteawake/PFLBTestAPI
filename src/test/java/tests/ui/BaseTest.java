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
    public void setUp() {  // Убрали static
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.baseUrl = "http://82.142.167.37:4881/";
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        Configuration.clickViaJs = true;

        // Управляем headless через системное свойство из Jenkins
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        Configuration.headless = isHeadless;

        ChromeOptions options = new ChromeOptions();

        if (isHeadless) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-setuid-sandbox");
        options.addArguments("--disable-crash-reporter");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--window-size=1920,1080");

        // Дополнительные полезные настройки
        options.setAcceptInsecureCerts(true);

        Configuration.browserCapabilities = options;
        Configuration.browserSize = "1920x1080"; // оставляем для совместимости

        logger.info("Selenide настроен. Headless = " + isHeadless);
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