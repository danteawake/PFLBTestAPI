package adapters;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import models.positive.CarRequest;
import models.positive.CarRequestUpdate;
import models.positive.CarResponse;

import static io.restassured.RestAssured.given;

public class CarAdapter extends BaseAdapter {
    static Gson gson = new Gson();
    private static String token = "Bearer " + LoginAdapter.getAccessToken();

    public static CarResponse createCar(CarRequest carRq) {
        return given()
                .spec(spec)
                .header("Authorization", token)
                .body(gson.toJson(carRq))
                .when()
                .post("/car")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CarSchema.json"))
                .spec(ok201)
                .extract()
                .as(CarResponse.class);
    }
    public static void createCarWithIncorrectEngineType(CarRequest carRq) {
        given()
                .spec(spec)
                .header("Authorization", token)
                .body(gson.toJson(carRq))
                .when()
                .post("/car")
                .then()
                .spec(badRequest400);

    }

    public static CarResponse getCar(int carId) {
        return given()
                .spec(spec)
                .header("Authorization", token)
                .pathParam("carId", carId)
                .when()
                .get("/car/{carId}")
                .then()
                .spec(ok200)
                .extract()
                .as(CarResponse.class);
    }

    public static CarResponse updateCar(int carId, CarRequestUpdate carRequestUpdate) {
        return given()
                .spec(spec)
                .header("Authorization", token)
                .body(gson.toJson(carRequestUpdate))
                .pathParam("carId", carId)
                .body(gson.toJson(carRequestUpdate))
                .when()
                .put("/car/{carId}")
                .then()
                .spec(ok202)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CarSchema.json"))
                .extract()
                .as(CarResponse.class);
    }

    public static void deleteCar(int carId) {
        given()
                .spec(spec)
                .header("Authorization", token)
                .pathParam("carId", carId)
                .when()
                .delete("/car/{carId}")
                .then()
                .spec(ok204);
    }
}
