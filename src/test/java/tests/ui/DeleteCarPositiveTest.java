package tests.ui;

import adapters.CarAdapter;
import db.CarDBConnection;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import models.positive.CarRequest;
import models.positive.CarResponse;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

@Slf4j
public class DeleteCarPositiveTest extends BaseTest {

    CarRequest carRq = CarRequest.builder()
            .engineType("Gasoline")
            .mark("Volvo")
            .model("Fantom")
            .price(2000)
            .build();

    @SneakyThrows
    @Test(description = "Проверка удаления автомобиля",
            testName = "Проверка удаления автомобиля")
    @Owner("Konstantin")
    @Description("Проверка удаления автомобиля")
    public void checkDeleteCar() {
        //создаем автомобиль через api
        CarDBConnection connection = new CarDBConnection();
        connection.connect();
        CarResponse carRs = CarAdapter.createCar(carRq);//Создаем автомобиль
        int carId = carRs.id;
        //удаляем автомобиль через ui
        loginPage.openPage().login(testUsername, testPassword);
        allDeletePage.openPage()
                .isPageOpened()
                .input("car", carId)
                .clickDelete("car");
        Thread.sleep(3000);
        assertEquals("Status: 204", allDeletePage.getStatus("car"));
    }
}
