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
    private String apiToken;

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
        int randomBalance = faker.number().numberBetween(1, 1000000);
        int userId = createUserPage.createUser(randomBalance);

        // 3. Получаем баланс из API (GET /user/{userId})
        UserResponse userFromApi = UserAdapter.getUser(userId, apiToken);
        double balanceFromApi = userFromApi.money;

        // 4. Получаем баланс из БД
        UserDBConnection userDB = new UserDBConnection();
        userDB.connect();
        double balanceFromDB = userDB.getUserBalanceFromDB(userId);
        userDB.close();

        // 5. Сравниваем балансы (API и БД должны совпадать)
        Assert.assertEquals(balanceFromApi, balanceFromDB,
                "Баланс в API и БД не совпадают!");
    }
}