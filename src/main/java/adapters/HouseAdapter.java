package adapters;

import com.google.gson.Gson;
import models.positive.HouseRequest;
import models.positive.HouseResponse;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class HouseAdapter {

    static Gson gson = new Gson();

    public static HouseResponse createHouse(int floorCount, double price, String token) {
        HouseRequest request = HouseRequest.builder()
                .floorCount(floorCount)
                .price(price)
                .parkingPlaces(Collections.emptyList())
                .lodgers(Collections.emptyList())
                .build();

        return given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(request))          // ← Gson, как у всех
                .when()
                .post("/house")
                .then()
                .spec(BaseAdapter.ok201)
                .extract()
                .as(HouseResponse.class);
    }

    public static void settleUser(int houseId, int userId, String token) {
        given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("houseId", houseId)
                .pathParam("userId", userId)
                // НЕТ .body() — только path-параметры!
                .when()
                .post("/house/{houseId}/settle/{userId}")
                .then()
                .spec(BaseAdapter.ok200);
    }
}