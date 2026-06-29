package tests.jdbc;

import adapters.UserAdapter;
import adapters.LoginAdapter;
import db.UserDBConnection;
import dto.AddMoneyData;
import dto.UserCreateData;
import io.qameta.allure.Owner;
import jdk.jfr.Description;
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

    @Test(description = "Проверка, что пользователь, созданный через All Post, сохраняется в БД",
            testName = "Проверка в БД созданого через AllPost пользователя")
    @Description("Пользователь создаётся через форму All Post, после чего проверяется его наличие в базе данных")
    @Owner("Шемякина Юлия")
    public void checkCreateUserFromAllPostInDB() {

        loginPage.openPage()
                .login(testUsername, testPassword);
        UserCreateData user = UserCreateData.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .age(25)
                .sex("MALE")
                .money(1000)
                .build();

        // Создаем user через UI (AllPost)
        allPostPage.openPage()
                .checkPageOpened()
                .createUser(user);

        String status = allPostPage.getStatusMessage();
        Assert.assertEquals(status, "Status: Successfully pushed, code: 201",
                "Неверный статус создания пользователя"
        );

        int userId = allPostPage.getCreatedUserId();

        // Проверяем в БД
        UserDBConnection db = new UserDBConnection();
        db.connect();

        int count = db.selectCreatedUser(userId);

        Assert.assertEquals(count, 1,
                "Пользователь не найден в БД после создания через AllPost");

        // удаляем через API
        UserAdapter.deleteUser(userId, apiToken);
        // Проверяем что удален в бд
        Assert.assertEquals(db.deleted(userId), 0, "Пользователь не удалился из БД");

        db.close();
    }

    @Test( description = "Проверка в БД добавления денег через AllPost UI" +
            "Проверка: создание пользователя через API, добавление денег через AllPost UI, " +
                    "проверка в БД и удаление через API",
            testName = "Проверка в БД добавления денег через AllPost")
    @Description("Пользователю сзданному через API добавляются деньги через форму All Post " +
            "и баланс сверяется с базой данных")
    @Owner("Шемякина Юлия")
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

        Assert.assertEquals(actualBalance, expectedBalance,
                "Баланс в БД не совпадает с ожидаемым");

        // Удаляем пользователя через API
        UserAdapter.deleteUser(userId, apiToken);

        // Проверяем что удален в бд
        Assert.assertEquals(db.deleted(userId),0,
                "Пользователь не удалён из БД");

        db.close();
    }
}