package tests.jdbc;

import adapters.UserAdapter;
import adapters.LoginAdapter;
import db.UserDBConnection;
import dto.AddMoneyData;
import models.positive.UserRequest;
import models.positive.UserResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.BaseTest;

public class AllPostJDBCTest extends BaseTest {

    private String apiToken;

    @BeforeMethod(description = "Получение токена перед тестом через API логин")
    public void setUpToken() {
        apiToken = LoginAdapter.loginApi().getAccessToken();
    }

    @Test( description = "Проверка: создание пользователя через API, добавление денег через AllPost UI, " +
                    "проверка в БД и удаление через API",
            testName = "AllPost + API + DB полный сценарий"
    )
    public void checkAddMoneyAndDeleteUser() {
        loginPage.openPage()
                .login(testUsername, testPassword);

        // Создаем пользоателя через API
        double initialMoney = 1000;

        UserRequest userRequest = UserRequest.builder()
                .firstName("Alex")
                .secondName("Ivanov")
                .age(25)
                .sex("MALE")
                .money(initialMoney)
                .build();

        UserResponse createdUser = UserAdapter.createUser(userRequest, apiToken);
        int userId = createdUser.id;

        // Добавляем деньги через UI (AllPost)
        double addedMoney = 500;

        AddMoneyData addMoney = AddMoneyData.builder()
                .userId(userId)
                .money(addedMoney)
                .build();

        allPostPage.openPage()
                .checkPageOpened()
                .addMoney(addMoney);

        allPostPage.getAddMoneyStatusMessage();

        // Проверяем баланс в БД
        UserDBConnection db = new UserDBConnection();
        db.connect();

        double expectedBalance = initialMoney + addedMoney;
        double actualBalance = db.getUserBalanceFromDB(userId);

        Assert.assertEquals(actualBalance, expectedBalance,"Баланс в БД не совпадает с ожидаемым");

        // Удаляем пользователя через API
        UserAdapter.deleteUser(userId, apiToken);

        // Проверяем что удален в бд
        Assert.assertEquals(db.deleted(userId),0,"Пользователь не удалён из БД");

        db.close();
    }
}