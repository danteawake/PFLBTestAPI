package adapters;


import dto.User;
import models.positive.LoginResponse;

import static io.restassured.RestAssured.given;

public class LoginAdapter extends BaseAdapter {

    public static LoginResponse loginApi() {
        return given()
                .spec(spec)
                .body(User.userStandard())
                .when()
                .post("/login")
                .then()
                .spec(access202)
                .extract()
                .as(LoginResponse.class);
    }

    public static String getAccessToken() {
        return given()
                .spec(spec)
                .body(User.userStandard())
                .when()
                .post("/login")
                .then()
                .spec(access202)
                .extract()
                .path("access_token");
    }
}