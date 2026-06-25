package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

public class UserHouseTest extends BaseTest {

    @Test(priority = 1,
            description = "8. Заселение пользователя в свободный дом")
    @Description("Пользователь заселяется в свободный дом")
    @Feature("Users UI")
    @Story("Заселение в дом")
    @Owner("Якушин Андрей")
    public void settleUserToFreeHouse() {
        loginPage.openPage().login(testUsername, testPassword);

        int userId = createUserPage.createUser("Test", "Settler", 30, "MALE", 10000);

        int houseId = createHousePage.createHouse(2, 5000.0);

        updateUserHousePage.openPage()
                .settleUser(userId, houseId)
                // Ожидаем 200. Если тест упадет с 406 — это БАГ!
                .checkStatus("code: 200");

        logger.info("✅ Тест 8 пройден: пользователь {} заселён в дом {}", userId, houseId);
    }

    @Test(priority = 2,
            enabled = false,// ← отключен временно
            description = "9. Выселение пользователя из дома")
    @Description("Пользователь выселяется из дома")
    @Feature("Users UI")
    @Story("Выселение из дома")
    @Owner("Якушин Андрей")
    public void evictUserFromHouse() {
        loginPage.openPage().login(testUsername, testPassword);

        int userId = createUserPage.createUser("Test", "Evictee", 30, "MALE", 10000);
        int houseId = createHousePage.createHouse(2, 100000.0);

        updateUserHousePage.openPage()
                .settleUser(userId, houseId)
                .checkStatus("code: 200");

        updateUserHousePage.openPage()
                .evictUser(userId, houseId)
                .checkStatus("code: 200");

        logger.info("✅ Тест 9 пройден: пользователь {} выселен из дома {}", userId, houseId);
    }
}