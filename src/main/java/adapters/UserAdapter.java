package adapters;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import models.positive.UserRequest;
import models.positive.UserResponse;

import static io.restassured.RestAssured.given;

public class UserAdapter extends BaseAdapter {
    static Gson gson = new Gson();

    public static UserResponse createUser(UserRequest userRq, String token) {

        return given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(userRq))
                .when()
                .post("/user")
                .then()
                .spec(ok201)
                .extract()
                .as(UserResponse.class);
    }

    @Step("Получить пользователя с ID {userId}")
    public static UserResponse getUser(int userId, String token) {

        return given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", userId)
                .when()
                .get("/user/{userId}")
                .then()
                .spec(ok200)
                .extract()
                .as(UserResponse.class);
    }

    @Step("Добавить {amount} денег пользователю {userId}")
    public static UserResponse addMoney(int userId, double amount, String token) {
        return given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", userId)
                .pathParam("amount", amount)
                .when()
                .post("/user/{userId}/money/{amount}")
                .then()
                .spec(BaseAdapter.ok200)
                .extract()
                .as(UserResponse.class);
    }
}