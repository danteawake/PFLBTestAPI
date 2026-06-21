package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

public class UserAddMoneyTest extends BaseTest {

    @Test(description = "Проверка добавления денег пользователю (положительная сумма)")
    @Description("Пользователь добавляет положительную сумму, баланс увеличивается")
    @Feature("Users")
    @Story("Добавление денег")
    @Owner("Якушин Андрей")
    public void testAddPositiveMoney() {
        loginPage.openPage().login(testUsername, testPassword);

        int userId = createUserPage.openPage()
                .createUser("Test", "User", 25, "MALE", 100)
                .checkStatus("Successfully pushed")
                .getCreatedUserId();

        userAddMoneyPage.openPage()
                .addMoney(userId, 1000)
                .checkStatus("code: 200");
    }

    @Test(description = "Проверка добавления отрицательной суммы")
    @Description("Пользователь пытается добавить отрицательную сумму — ошибка")
    @Feature("Users")
    @Story("Добавление денег")
    @Owner("Якушин Андрей")
    public void testAddNegativeMoney() {
        loginPage.openPage().login(testUsername, testPassword);

        int userId = createUserPage.openPage()
                .createUser("Test", "User", 25, "MALE", 100)
                .checkStatus("Successfully pushed")
                .getCreatedUserId();

        userAddMoneyPage.openPage()
                .addMoney(userId, -500)
                .checkStatus("Incorrect input data");
    }

    @Test(description = "Проверка добавления нулевой суммы")
    @Description("Пользователь добавляет 0 — ошибка (сервер не принимает 0)")
    @Feature("Users")
    @Story("Добавление денег")
    @Owner("Якушин Андрей")
    public void testAddZeroMoney() {
        loginPage.openPage().login(testUsername, testPassword);

        int userId = createUserPage.openPage()
                .createUser("Test", "User", 25, "MALE", 100)
                .checkStatus("Successfully pushed")
                .getCreatedUserId();

        userAddMoneyPage.openPage()
                .addMoney(userId, 0)
                .checkStatus("Incorrect input data");
    }
}