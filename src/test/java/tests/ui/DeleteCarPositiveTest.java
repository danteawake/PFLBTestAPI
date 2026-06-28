package tests.ui;

import adapters.CarAdapter;
import adapters.LoginAdapter;
import db.CarDBConnection;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import models.positive.CarRequest;
import models.positive.CarResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

@Slf4j
public class DeleteCarPositiveTest extends BaseTest {

    private final CarRequest carRq = CarRequest.builder()
            .engineType("Gasoline")
            .mark("Volvo")
            .model("Fantom")
            .price(2000)
            .build();

    private String apiToken; // Поле для хранения токена внутри класса

    @BeforeMethod(description = "Получение токена авторизации для предварительных API-шагов")
    public void setUpApiToken() {
        // Запрашиваем токен перед запуском теста
        apiToken = LoginAdapter.loginApi().getAccessToken();
    }

    @SneakyThrows
    @Test(description = "Проверка удаления автомобиля",
            testName = "Проверка удаления автомобиля")
    @Owner("Konstantin")
    @Description("Проверка удаления автомобиля")
    public void checkDeleteCar() {

        //создаем автомобиль через api
        CarResponse carRs = CarAdapter.createCar(carRq, apiToken);
        int carId = carRs.id;
        log.info("Машина создана. ID = {}", carId);

        //удаляем автомобиль через ui
        loginPage.openPage().login(testUsername, testPassword);
        allDeletePage.openPage()
                .isPageOpened()
                .input("car", carId)
                .clickDelete("car");

        Thread.sleep(3000);
        assertEquals("Status: 204", allDeletePage.getStatus("car"));
        log.info("Машина удалена. ID = {}", carId);

        //проверяем через БД, что автомобиля действительно нет
        CarDBConnection connection = new CarDBConnection();
        connection.connect();
        assertEquals(0, connection.deleted(carId));
        log.info("Машина с id {} отсутствует в БД", carId);
        connection.close();
    }
}
