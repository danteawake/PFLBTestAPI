package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import dto.User;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.*;

public class BaseTest {

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    protected static String testUsername;
    protected static String testPassword;

    protected LoginPage loginPage;
    protected UsersReadAllPage usersReadAllPage;
    protected UsersReadUserWithCar usersReadUserWithCar;
    protected CarsReadAll carReaAll;
    protected CreateNewCarPage createNewCarPage;
    protected AllPostPage allPostPage;
    protected UpdateUserMoneyPage updateUserMoneyPage;
    protected CreateUserPage createUserPage;
    protected AllDeletePage allDeletePage;
    protected UpdateUserCarPage updateUserCarPage;
    protected CreateHousePage createHousePage;
    protected UpdateUserHousePage updateUserHousePage;

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
        String currentBrowser = Configuration.browser.toLowerCase();

        // Настраиваем capabilities в зависимости от выбранного браузера
        if (currentBrowser.contains("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) {
                options.addArguments("-headless");
            }
            options.addArguments("--window-size=1920,1080");
            options.setAcceptInsecureCerts(true);
            options.setCapability("unhandledPromptBehavior", "ignore");
            Configuration.browserCapabilities = options;

        } else if (currentBrowser.contains("edge")) {
            EdgeOptions options = new EdgeOptions();
            if (isHeadless) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.setAcceptInsecureCerts(true);
            options.setCapability("unhandledPromptBehavior", "ignore");
            Configuration.browserCapabilities = options;

        } else {
            // Для chrome и всех остальных по умолчанию используем ваши исходные настройки
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
            options.setAcceptInsecureCerts(true);
            options.setCapability("unhandledPromptBehavior", "ignore");
            Configuration.browserCapabilities = options;
        }

        Configuration.browserSize = "1920x1080"; // оставляем для совместимости
        logger.info("Selenide настроен. Headless = " + isHeadless);
    }

    @BeforeMethod
    public void initPages() {
        logger.info("Инициализация объектов страниц перед тестом.");
        loginPage = new LoginPage();
        usersReadAllPage = new UsersReadAllPage();
        usersReadUserWithCar = new UsersReadUserWithCar();
        carReaAll = new CarsReadAll();
        createNewCarPage = new CreateNewCarPage();
        allPostPage = new AllPostPage();
        updateUserMoneyPage = new UpdateUserMoneyPage();
        createUserPage = new CreateUserPage();
        allDeletePage = new AllDeletePage();
        updateUserCarPage = new UpdateUserCarPage();
        createHousePage = new CreateHousePage();
        updateUserHousePage = new UpdateUserHousePage();
    }
}