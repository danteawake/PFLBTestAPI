package adapters;

import io.restassured.module.jsv.JsonSchemaValidator;
import models.positive.HouseRequest;
import models.positive.HouseResponse;

import static io.restassured.RestAssured.given;

public class HouseAdapter extends BaseAdapter {

    public static HouseResponse createHouse(HouseRequest houseRequest, String token) {

        return given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(houseRequest))
                .when()
                .post("/house")
                .then()
                .spec(BaseAdapter.ok201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/HouseSchema.json"))
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

    public static void getHouse200(int houseId, String token) {
        given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("houseId", houseId)
                .when()
                .get("/house/{houseId}")
                .then()
                .spec(BaseAdapter.ok200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/HouseSchema.json"))
                .extract()
                .as(HouseResponse.class);
    }

    public static void getHouse204(int houseId, String token) {
        given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("houseId", houseId)
                .when()
                .get("/house/{houseId}")
                .then()
                .spec(BaseAdapter.ok204);
    }

    public static HouseResponse updateHouse(int houseId, HouseRequest houseRequest, String token) {
        return given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(houseRequest))
                .pathParam("id", houseId)
                .log().all()
                .when()
                .put("/house/{id}")
                .then()
                .log().all()
                .spec(BaseAdapter.ok202)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/HouseSchema.json"))
                .extract()
                .as(HouseResponse.class);
    }

    public static void deleteHouse(int houseId, String  token) {
        given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("houseId", houseId)
                .when()
                .delete("/house/{houseId}")
                .then()
                .spec(BaseAdapter.ok204);
    }

    public static void deleteHouse404(int houseId, String  token) {
        given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("houseId", houseId)
                .when()
                .delete("/house/{houseId}")
                .then()
                .spec(BaseAdapter.false404);
    }
}