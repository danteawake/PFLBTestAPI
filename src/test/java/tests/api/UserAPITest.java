package tests.api;

import adapters.BaseAdapter;
import adapters.CarAdapter;
import adapters.HouseAdapter;
import adapters.UserAdapter;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import models.positive.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;

public class UserAPITest extends BaseAPITest {

    @Test(priority = 1,
            description = "10. POST /user/{userId}/money — добавление денег, статус 200")
    @Description("Проверка успешного добавления денег пользователю через API")
    @Feature("Users API")
    @Story("Добавление денег")
    @Owner("Якушин Андрей")
    public void checkAddMoney() {
        double initialMoney = 500.0;
        double addedMoney = 300.0;

        // 1. Создаём пользователя со случайными данными и балансом 500
        UserResponse createdUser = UserAdapter.createRandomUser(initialMoney, token);
        int userId = createdUser.id;

        // 2. Отправляем запрос и проверяем JSON Schema
        UserResponse updatedUser = given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", userId)
                .pathParam("amount", addedMoney)
                .when()
                .post("/user/{userId}/money/{amount}")
                .then()
                .spec(BaseAdapter.ok200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/UserSchema.json"))
                .extract()
                .as(UserResponse.class);

        double expectedBalance = initialMoney + addedMoney;
        Assert.assertEquals(updatedUser.money, expectedBalance, 0.01,
                "Баланс пользователя не совпадает с ожидаемым!");
    }

    @Test(priority = 2,
            description = "11. POST /user/{userId}/sellCar/{carId} — продажа машины, статус 200",
            groups = {"bug"})
    @Description("Проверка успешной продажи машины через API")
    @Feature("Users API")
    @Story("Продажа машины")
    @Owner("Якушин Андрей")
    @Issue("BUG. GET /user/{userId}/cars возвращает 204 вместо 200")
    public void checkSellCar() {
        // 1. Создаём пользователя со случайными данными и балансом 10000
        UserResponse createdUser = UserAdapter.createRandomUser(10000.0, token);
        int userId = createdUser.id;

        // 2. Создаём машину
        CarRequest carRequest = CarRequest.builder()
                .engineType("Gasoline")
                .mark("Tesla")
                .model("Model S")
                .price(5000)
                .build();

        CarResponse createdCar = CarAdapter.createCar(carRequest, token);
        int carId = createdCar.id;

        // 3. Покупаем машину (предусловие)
        CarAdapter.buyCar(userId, carId, token);

        // 4. Продаём машину (проверяем эндпоинт)
        // Проверяем JSON Schema в ответе
        UserResponse updatedUser = given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .when()
                .post("/user/{userId}/sellCar/{carId}")
                .then()
                .spec(BaseAdapter.ok200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/UserSchema.json"))
                .extract()
                .as(UserResponse.class);

        // 5. Проверяем, что машина больше не привязана к пользователю
        // БАГ! GET /user/{userId}/cars возвращает 204 вместо 200
        List<CarResponse> userCars = CarAdapter.getUserCars(userId, token);

        boolean carFound = userCars.stream()
                .anyMatch(car -> car.id == carId);

        Assert.assertFalse(carFound, "Машина " + carId + " всё ещё привязана к пользователю " + userId);
    }

    @Test(priority = 3,
            description = "12. POST /house/{houseId}/settle/{userId} — заселение в дом, статус 200")
    @Description("Проверка успешного заселения пользователя в дом через API")
    @Feature("Users API")
    @Story("Заселение в дом")
    @Owner("Якушин Андрей")
    public void checkSettleUser() {
        // 1. Создаём пользователя со случайными данными и балансом 20000
        UserResponse createdUser = UserAdapter.createRandomUser(20000.0, token);
        int userId = createdUser.id;

        // 2. Создаём дом
        int floorCount = 2;
        double price = 10000.0;

        HouseRequest request = HouseRequest.builder()
                .floorCount(floorCount)
                .price(price)
                .parkingPlaces(Collections.emptyList())
                .lodgers(Collections.emptyList())
                .build();

        HouseResponse createdHouse = HouseAdapter.createHouse(request, token);
        int houseId = createdHouse.id;

        // 3. Заселяем пользователя в дом (ПРОВЕРЯЕМ ЭНДПОИНТ)
        // Проверяем JSON Schema в ответе
        HouseResponse updatedHouse = given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("houseId", houseId)
                .pathParam("userId", userId)
                .when()
                .post("/house/{houseId}/settle/{userId}")
                .then()
                .spec(BaseAdapter.ok200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/HouseSchema.json"))
                .extract()
                .as(HouseResponse.class);
    }
}