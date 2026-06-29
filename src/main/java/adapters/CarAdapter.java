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

    public static CarResponse createCar(CarRequest carRq, String token) {
        System.out.println(token);
        return given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
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

    public static CarResponse getCar(int carId, String token) {
        return given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("carId", carId)
                .when()
                .get("/car/{carId}")
                .then()
                .spec(ok200)
                .extract()
                .as(CarResponse.class);
    }

    public static CarResponse updateCar(int carId, CarRequestUpdate carRequestUpdate, String token) {
        return given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
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

    public static void deleteCar(int carId, String token) {
        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("carId", carId)
                .when()
                .delete("/car/{carId}")
                .then()
                .spec(ok204);
    }

    public static void buyCar(int userId, int carId, String token) {
        given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .when()
                .post("/user/{userId}/buyCar/{carId}")
                .then()
                .spec(BaseAdapter.ok200);
    }

    public static void sellCar(int userId, int carId, String token) {
        given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .when()
                .post("/user/{userId}/sellCar/{carId}")
                .then()
                .spec(BaseAdapter.ok200);
    }

    public static List<CarResponse> getUserCars(int userId, String token) {
        Response response = given()
                .spec(BaseAdapter.spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", userId)
                .when()
                .get("/user/{userId}/cars");

        int statusCode = response.statusCode();

        Assert.assertEquals(
                statusCode,
                200,
                "БАГ! GET /user/{userId}/cars вернул статус " + statusCode +
                        ", хотя должен быть 200 OK"
        );

        return Arrays.asList(response.as(CarResponse[].class));
    }
}