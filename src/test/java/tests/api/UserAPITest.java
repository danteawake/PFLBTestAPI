package tests.api;

import adapters.CarAdapter;
import adapters.UserAdapter;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import models.positive.CarRequest;
import models.positive.CarResponse;
import models.positive.UserRequest;
import models.positive.UserResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UserAPITest {

    private static final Faker faker = new Faker();

    @Test(priority = 1,
            description = "10. POST /user/{userId}/money — добавление денег, статус 200")
    @Description("Проверка успешного добавления денег пользователю через API")
    @Feature("Users API")
    @Story("Добавление денег")
    @Owner("Якушин Андрей")
    public void checkAddMoney() {
        double initialMoney = 500.0;
        double addedMoney = 300.0;

        String firstName = faker.name().firstName();
        String secondName = faker.name().lastName() + "_" + System.currentTimeMillis();

        UserRequest userRequest = UserRequest.builder()
                .firstName(firstName)
                .secondName(secondName)
                .age(25)
                .sex("MALE")
                .money(initialMoney)
                .build();

        UserResponse createdUser = UserAdapter.createUser(userRequest);
        int userId = createdUser.id;
        System.out.println("Создан пользователь ID: " + userId + ", баланс: " + createdUser.money);

        UserResponse updatedUser = UserAdapter.addMoney(userId, addedMoney);
        System.out.println("Добавлено денег: " + addedMoney);
        System.out.println("Новый баланс: " + updatedUser.money);

        double expectedBalance = initialMoney + addedMoney;
        Assert.assertEquals(updatedUser.money, expectedBalance, 0.01,
                "Баланс пользователя не совпадает с ожидаемым!");
    }

    @Test(priority = 2,
            description = "11. POST /user/{userId}/sellCar/{carId} — продажа машины, статус 200")
    @Description("Проверка успешной продажи машины через API")
    @Feature("Users API")
    @Story("Продажа машины")
    @Owner("Якушин Андрей")
    public void checkSellCar() {
        // 1. Создаём пользователя
        String firstName = faker.name().firstName();
        String secondName = faker.name().lastName() + "_" + System.currentTimeMillis();

        UserRequest userRequest = UserRequest.builder()
                .firstName(firstName)
                .secondName(secondName)
                .age(30)
                .sex("MALE")
                .money(10000.0)
                .build();

        UserResponse createdUser = UserAdapter.createUser(userRequest);
        int userId = createdUser.id;
        System.out.println("✅ Создан пользователь ID: " + userId + ", баланс: " + createdUser.money);

        // 2. Создаём машину
        CarRequest carRequest = CarRequest.builder()
                .engineType("Gasoline")
                .mark("Tesla")
                .model("Model S")
                .price(5000)
                .build();

        CarResponse createdCar = CarAdapter.createCar(carRequest);
        int carId = createdCar.id;
        System.out.println("✅ Создана машина ID: " + carId + ", цена: " + createdCar.price);

        // 3. Покупаем машину (предусловие)
        CarAdapter.buyCar(userId, carId);
        System.out.println("✅ Машина куплена пользователем");

        // 4. Продаём машину (ПРОВЕРЯЕМ ЭНДПОИНТ)
        CarAdapter.sellCar(userId, carId);
        System.out.println("✅ Машина продана — статус 200 OK");

        // 5. Проверяем, что машина больше не привязана к пользователю
        List<CarResponse> userCars = CarAdapter.getUserCars(userId);

        boolean carFound = userCars.stream()
                .anyMatch(car -> car.id == carId);

        Assert.assertFalse(carFound, "Машина " + carId + " всё ещё привязана к пользователю " + userId);

        System.out.println("✅ Проверка пройдена: машина удалена из списка пользователя");
    }
}