package tests.jdbc;

import adapters.LoginAdapter;
import adapters.UserAdapter;
import com.github.javafaker.Faker;
import db.UserDBConnection;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import models.positive.UserResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.BaseTest;

public class UserJDBCTest extends BaseTest {

    private static final Faker faker = new Faker();

    private String apiToken; // Поле для хранения токена внутри класса

    @BeforeMethod(description = "Получение токена авторизации для предварительных API-шагов")
    public void setUpApiToken() {
        // Запрашиваем токен перед запуском теста
        apiToken = LoginAdapter.loginApi().getAccessToken();
    }

    @Test(priority = 1,
            description = "13. Проверка баланса пользователя в БД")
    @Description("Баланс пользователя в БД соответствует API")
    @Feature("Users DB")
    @Story("Проверка баланса в БД")
    @Owner("Якушин Андрей")
    public void checkUserBalanceInDB() {
        // 1. Авторизация
        loginPage.openPage().login(testUsername, testPassword);

        // 2. Создаём пользователя со случайным балансом через UI
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName() + "_" + System.currentTimeMillis();
        //Генерируем случайный баланс от 1 до 1 000 000
        int randomBalance = faker.number().numberBetween(1, 1000000);
        logger.info("Случайный баланс: {}", randomBalance);

        int userId = createUserPage.createUser(firstName, lastName, 30, "MALE", randomBalance);
        logger.info("Создан пользователь ID: {}", userId);

        // 3. Получаем баланс из API (GET /user/{userId})
        UserResponse userFromApi = UserAdapter.getUser(userId, apiToken);
        double balanceFromApi = userFromApi.money;
        logger.info("Баланс из API: {}", balanceFromApi);

        // 4. Получаем баланс из БД
        UserDBConnection userDB = new UserDBConnection();
        userDB.connect();
        double balanceFromDB = userDB.getUserBalanceFromDB(userId);
        userDB.close();
        logger.info("Баланс из БД: {}", balanceFromDB);

        // 5. Сравниваем балансы (API и БД должны совпадать)
        Assert.assertEquals(balanceFromApi, balanceFromDB,
                "Баланс в API и БД не совпадают!");

        logger.info("Тест 13 пройден: баланс в API и БД совпадают");
    }
}