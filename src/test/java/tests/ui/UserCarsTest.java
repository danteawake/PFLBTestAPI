package tests.ui;

import com.codeborne.selenide.Selenide;
import dto.Car;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

public class UserCarsTest extends BaseTest {

    // Создание тестового пользователя
    private int createTestUser(String firstName, String lastName, int age, String sex, int money) {
        createUserPage.openPage();
        Selenide.sleep(500);
        return createUserPage.openPage()
                .createUser(firstName + "_" + System.currentTimeMillis(),
                        lastName + "_" + System.currentTimeMillis(),
                        age, sex, money)
                .checkStatus("Successfully pushed")
                .getCreatedUserId();
    }

    // Создание тестового автомобиля
    private int createTestCar(String engineType, String mark, String model, double price) {
        Car car = new Car(engineType, mark, model, price);
        return createNewCarPage.openCreateNewCarPage()
                .addNewCar(car);
    }

    @Test(priority = 1,
            description = "4. Покупка машины с достаточным балансом")
    @Description("Пользователь с достаточным балансом покупает машину")
    @Feature("Users")
    @Story("Покупка машины")
    @Owner("Якушин Андрей")
    public void buyCarWithEnoughMoney() {
        loginPage.openPage().login(testUsername, testPassword);

        int userId = createTestUser("Test", "CarBuyer", 30, "MALE", 10000);
        int carId = createTestCar("Gasoline", "Tesla", "Model S", 5000.00);

        userCarsPage.openPage()
                .buyCar(userId, carId)
                .checkStatus("code: 200");
    }

    @Test(priority = 2,
            description = "5. Покупка машины с недостаточным балансом")
    @Description("Пользователь с недостаточным балансом не может купить машину")
    @Feature("Users")
    @Story("Покупка машины")
    @Owner("Якушин Андрей")
    public void buyCarWithoutMoney() {
        loginPage.openPage().login(testUsername, testPassword);

        int userId = createTestUser("Test", "PoorUser", 30, "MALE", 100);
        int carId = createTestCar("Electric", "Bugatti", "Veyron", 50000.00);

        userCarsPage.openPage()
                .buyCar(userId, carId)
                .checkStatus("406");
    }

    @Test(priority = 3,
            description = "6. Продажа машины, которая есть у пользователя")
    @Description("Пользователь продаёт машину, баланс увеличивается")
    @Feature("Users")
    @Story("Продажа машины")
    @Owner("Якушин Андрей")
    public void sellExistingCar() {
        loginPage.openPage().login(testUsername, testPassword);

        int userId = createTestUser("Test", "CarSeller", 30, "MALE", 10000);
        int carId = createTestCar("Diesel", "BMW", "X5", 8000.00);

        userCarsPage.openPage()
                .buyCar(userId, carId)
                .checkStatus("code: 200");

        userCarsPage.openPage()
                .sellCar(userId, carId)
                .checkStatus("code: 200");
    }

    @Test(priority = 4,
            description = "7. Продажа машины, которой нет у пользователя")
    @Description("Пользователь пытается продать машину, которой у него нет")
    @Feature("Users")
    @Story("Продажа машины")
    @Owner("Якушин Андрей")
    public void sellNonExistingCar() {
        loginPage.openPage().login(testUsername, testPassword);

        int userId = createTestUser("Test", "NoCarUser", 30, "MALE", 10000);
        int carId = createTestCar("CNG", "Audi", "A8", 10000.00);

        userCarsPage.openPage()
                .sellCar(userId, carId)
                // Ожидаем 404. Если тест упадет с 200 — это БАГ!
                .checkStatus("404");
    }
}