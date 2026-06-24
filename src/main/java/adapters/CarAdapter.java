package adapters;

import com.google.gson.Gson;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import models.positive.CarRequest;
import models.positive.CarRequestUpdate;
import models.positive.CarResponse;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CarAdapter extends BaseAdapter {
    static Gson gson = new Gson();
    private static String token = "Bearer " + LoginAdapter.getAccessToken();

    public static CarResponse createCar(CarRequest carRq) {

        System.out.println(token);
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

    public static void buyCar(int userId, int carId) {
        given()
                .spec(spec)
                .header("Authorization", token)
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .when()
                .post("/user/{userId}/buyCar/{carId}")
                .then()
                .spec(ok200);
    }

    public static void sellCar(int userId, int carId) {
        given()
                .spec(spec)
                .header("Authorization", token)
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .when()
                .post("/user/{userId}/sellCar/{carId}")
                .then()
                .spec(ok200);
    }

    public static List<CarResponse> getUserCars(int userId) {
        Response response = given()
                .spec(spec)
                .header("Authorization", token)
                .pathParam("userId", userId)
                .when()
                .get("/user/{userId}/cars");

        int statusCode = response.statusCode();

        // Проверяем, что статус 200, любой другой статус — это баг!
        Assert.assertEquals(
                statusCode,
                200,
                "БАГ! GET /user/{userId}/cars вернул статус " + statusCode +
                        ", хотя должен быть 200 OK"
        );

        return Arrays.asList(response.as(CarResponse[].class));
    }
}
