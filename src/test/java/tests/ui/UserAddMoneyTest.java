package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

public class UserAddMoneyTest extends BaseTest {

    @Test(priority = 1,
            description = "1. Проверка добавления денег пользователю (положительная сумма)")
    @Description("Пользователь добавляет положительную сумму, баланс увеличивается")
    @Feature("Users")
    @Story("Добавление денег")
    @Owner("Якушин Андрей")
    public void addPositiveMoney() {
        loginPage.openPage().login(testUsername, testPassword);

        int userId = createUserPage.openPage()
                .createUser("Test", "User", 25, "MALE", 100)
                .checkStatus("Successfully pushed")
                .getCreatedUserId();

        userAddMoneyPage.openPage()
                .addMoney(userId, 1000)
                .checkStatus("code: 200");
    }

    @Test(priority = 2,
            description = "2. Проверка добавления отрицательной суммы")
    @Description("Пользователь пытается добавить отрицательную сумму — ошибка")
    @Feature("Users")
    @Story("Добавление денег")
    @Owner("Якушин Андрей")
    public void addNegativeMoney() {
        loginPage.openPage().login(testUsername, testPassword);

        int userId = createUserPage.openPage()
                .createUser("Test", "User", 25, "MALE", 100)
                .checkStatus("Successfully pushed")
                .getCreatedUserId();

        userAddMoneyPage.openPage()
                .addMoney(userId, -500)
                .checkStatus("Incorrect input data");
    }

    @Test(priority = 3,
            description = "3. Проверка добавления нулевой суммы")
    @Description("Пользователь добавляет 0 — ошибка (сервер не принимает 0)")
    @Feature("Users")
    @Story("Добавление денег")
    @Owner("Якушин Андрей")
    public void addZeroMoney() {
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