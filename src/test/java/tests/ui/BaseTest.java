package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import dto.User;
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

    // === Добавили эти две статические переменные ===
    protected static String testUsername;
    protected static String testPassword;

    protected LoginPage loginPage;
    protected UsersReadAllPage usersReadAllPage;
    protected UsersReadUserWithCar usersReadUserWithCar;
    protected AllPostPage allPostPage;

    @BeforeClass
    public void setUp() {  // Убрали static
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.baseUrl = "http://82.142.167.37:4881/";

        // Читаем браузер из Jenkins, если не передан — используем chrome по умолчанию
        String browserFromJenkins = System.getProperty("browser", "chrome");
        Configuration.browser = browserFromJenkins;

        Configuration.timeout = 10000;
        Configuration.clickViaJs = true;

        // Управляем headless через системное свойство из Jenkins
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        Configuration.headless = isHeadless;

        // === Чтение логина и пароля из параметров (Jenkins / локально) ===
        String username = System.getProperty("test.user");
        String password = System.getProperty("test.password");

        // Если параметры не переданы — используем дефолт из DTO
        if (username == null || username.isEmpty()) {
            username = User.userStandard().getUsername();
        }
        if (password == null || password.isEmpty()) {
            password = User.userStandard().getPassword();
        }

        testUsername = username;
        testPassword = password;

        logger.info("Используется пользователь для тестов: " + testUsername);

        // === ИСПРАВЛЕНИЕ: Динамическая настройка опций браузера ===
        org.openqa.selenium.MutableCapabilities options;

        if ("firefox".equalsIgnoreCase(Configuration.browser)) {
            org.openqa.selenium.firefox.FirefoxOptions fxOptions = new org.openqa.selenium.firefox.FirefoxOptions();
            if (isHeadless) {
                fxOptions.addArguments("-headless");
            }
            fxOptions.addArguments("--window-size=1920,1080");
            options = fxOptions;
        } else {
            // Для chrome, edge и других по умолчанию используем ChromeOptions
            ChromeOptions chOptions = new ChromeOptions();
            if (isHeadless) {
                chOptions.addArguments("--headless=new");
            }
            chOptions.addArguments("--no-sandbox");
            chOptions.addArguments("--disable-dev-shm-usage");
            chOptions.addArguments("--disable-gpu");
            chOptions.addArguments("--disable-extensions");
            chOptions.addArguments("--disable-setuid-sandbox");
            chOptions.addArguments("--disable-crash-reporter");
            chOptions.addArguments("--disable-notifications");
            chOptions.addArguments("--disable-blink-features=AutomationControlled");
            chOptions.addArguments("--window-size=1920,1080");
            options = chOptions;
        }

        // Общие настройки для всех браузеров
        options.setCapability("acceptInsecureCerts", true);

        Configuration.browserCapabilities = options;
        Configuration.browserSize = "1920x1080";

        logger.info("Selenide настроен. Браузер = " + Configuration.browser + ", Headless = " + isHeadless);
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