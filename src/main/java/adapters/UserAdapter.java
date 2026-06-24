package adapters;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import models.positive.UserRequest;
import models.positive.UserResponse;

import static io.restassured.RestAssured.given;

public class UserAdapter extends BaseAdapter {
    static Gson gson = new Gson();
    private static final String token =
            "Bearer " + LoginAdapter.getAccessToken();

    public static UserResponse createUser(UserRequest userRq) {

        return given()
                .spec(spec)
                .header("Authorization", token)
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
                .spec(spec)
                .header("Authorization", token)
                .pathParam("userId", userId)
                .when()
                .get("/user/{userId}")
                .then()
                .spec(ok200)
                .extract()
                .as(UserResponse.class);
    }

    @Step("Добавить {amount} денег пользователю {userId}")
    public static UserResponse addMoney(int userId, double amount) {
        return given()
                .spec(spec)
                .header("Authorization", token)
                .pathParam("userId", userId)
                .pathParam("amount", amount)
                .when()
                .post("/user/{userId}/money/{amount}")
                .then()
                .spec(ok200)
                .extract()
                .as(UserResponse.class);
    }
}