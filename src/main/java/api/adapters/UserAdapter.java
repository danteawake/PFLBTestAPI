package api.adapters;

import com.github.javafaker.Faker;
import api.models.user.UserRequest;
import api.models.user.UserResponse;

import static io.restassured.RestAssured.given;

public class UserAdapter extends BaseAdapter {

    public static UserResponse createUser(UserRequest userRq) {

        return given()
                .spec(getAuthenticatedSpec())
                .body(gson.toJson(userRq))
                .when()
                .post("/user")
                .then()
                .spec(ok201)
                .extract()
                .as(UserResponse.class);
    }

    public static UserResponse getUser(int userId) {

        return given()
                .spec(getAuthenticatedSpec())
                .pathParam("userId", userId)
                .when()
                .get("/user/{userId}")
                .then()
                .spec(ok200)
                .extract()
                .as(UserResponse.class);
    }

    public static UserResponse addMoney(int userId, double amount) {
        return given()
                .spec(BaseAdapter.getAuthenticatedSpec())
                .pathParam("userId", userId)
                .pathParam("amount", amount)
                .when()
                .post("/user/{userId}/money/{amount}")
                .then()
                .spec(BaseAdapter.ok200)
                .extract()
                .as(UserResponse.class);
    }

    // Создаёт пользователя со случайными данными (имя, фамилия, возраст, пол) и заданным балансом. Использует Faker для генерации реалистичных данных.
    public static UserResponse createRandomUser(double money) {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String secondName = faker.name().lastName() + "_" + System.currentTimeMillis();
        int age = faker.number().numberBetween(18, 65);
        String sex = faker.options().option("MALE", "FEMALE");

        UserRequest userRequest = UserRequest.builder()
                .firstName(firstName)
                .secondName(secondName)
                .age(age)
                .sex(sex)
                .money(money)
                .build();

        return createUser(userRequest);
    }
    public static void deleteUser(int userId) {

        given()
                .spec(getAuthenticatedSpec())
                .pathParam("userId", userId)
                .when()
                .delete("/user/{userId}")
                .then()
                .spec(ok204);
    }
}