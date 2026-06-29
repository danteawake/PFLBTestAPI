package tests.jdbc;

import adapters.UserAdapter;
import com.github.javafaker.Faker;
import db.UserDBConnection;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import models.positive.UserResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserJDBCTest extends BaseJDBCTest {

    private static final Faker faker = new Faker();

    @Test(priority = 1,
            description = "13. Проверка баланса пользователя в БД")
    @Description("Баланс пользователя в БД соответствует API")
    @Feature("Users DB")
    @Story("Проверка баланса в БД")
    @Owner("Якушин Андрей")
    public void checkUserBalanceInDB() {
        // 1. Создаём пользователя через API (без UI)
        double randomBalance = faker.number().numberBetween(1, 1000000);
        UserResponse createdUser = UserAdapter.createRandomUser(randomBalance, apiToken);
        int userId = createdUser.id;

        // 2. Получаем баланс из API
        UserResponse userFromApi = UserAdapter.getUser(userId, apiToken);
        double balanceFromApi = userFromApi.money;

        // 3. Получаем баланс из БД
        UserDBConnection userDB = new UserDBConnection();
        userDB.connect();
        double balanceFromDB = userDB.getUserBalanceFromDB(userId);
        userDB.close();

        // 4. Сравниваем балансы (API и БД должны совпадать)
        Assert.assertEquals(balanceFromApi, balanceFromDB,
                "Баланс в API и БД не совпадают!");
    }
}