package tests.ui;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

public class UserHouseTest extends BaseTest {

    private static final Faker faker = new Faker();

    @Test(priority = 1,
            description = "8. Заселение пользователя в свободный дом")
    @Description("Пользователь заселяется в свободный дом")
    @Feature("Users UI")
    @Story("Заселение в дом")
    @Owner("Якушин Андрей")
    public void settleUserToFreeHouse() {
        loginPage.openPage().login(testUsername, testPassword);

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName() + "_" + System.currentTimeMillis();

        int userId = createUserPage.createUser(firstName, lastName, 30, "MALE", 10000);
        int houseId = createHousePage.createHouse(2, 5000.0);

        updateUserHousePage.openPage()
                .settleUser(userId, houseId)
                .checkStatus("code: 200");
        logger.info("Пользователь {} заселён в дом {}", userId, houseId);

        logger.info("✅ Тест 8 пройден: пользователь {} заселён в дом {}", userId, houseId);
    }

    @Test(priority = 2,
            description = "9. Выселение пользователя из дома")
    @Description("Пользователь выселяется из дома")
    @Feature("Users UI")
    @Story("Выселение из дома")
    @Owner("Якушин Андрей")
    public void evictUserFromHouse() {
        loginPage.openPage().login(testUsername, testPassword);

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName() + "_" + System.currentTimeMillis();

        int userId = createUserPage.createUser(firstName, lastName, 30, "MALE", 10000);
        int houseId = createHousePage.createHouse(2, 5000.0);

        updateUserHousePage.openPage()
                .settleUser(userId, houseId)
                .checkStatus("code: 200");
        logger.info("Пользователь {} заселён в дом {}", userId, houseId);

        updateUserHousePage.openPage()
                .evictUser(userId, houseId)
                .checkStatus("code: 200");

        logger.info("✅ Тест 9 пройден: пользователь {} выселен из дома {}", userId, houseId);
    }
}