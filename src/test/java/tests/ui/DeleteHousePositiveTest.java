package tests.ui;

import api.adapters.HouseAdapter;
import api.adapters.LoginAdapter;
import db.HouseDBConnection;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import api.models.house.HouseRequest;
import api.models.house.HouseResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.AssertJUnit.assertEquals;

@Slf4j
public class DeleteHousePositiveTest extends BaseTest {

    private String apiToken; // Переменная для хранения токена API

    @BeforeMethod(description = "Получение токена авторизации для API-предварительных шагов")
    public void setUpApiToken() {
        // Получаем чистый токен из LoginAdapter один раз перед тестом
        apiToken = LoginAdapter.loginApi().getAccessToken();
    }

    int floorCount = 1;
    double price = 100.0;

    HouseRequest request = HouseRequest.builder()
            .floorCount(floorCount)
            .price(price)
            .parkingPlaces(Collections.emptyList())
            .lodgers(Collections.emptyList())
            .build();

    @SneakyThrows
    @Test(description = "Проверка удаления дома",
            testName = "Проверка удаления дома")
    @Owner("Konstantin")
    @Description("Проверка удаления дома")
    public void checkDeleteHouse() {
        //Создаем дом через api
        HouseResponse houseResponse = HouseAdapter.createHouse(request);
        int houseId = houseResponse.id;
        log.info("Дом создан. ID = {}", houseId);

        //удаляем дом через ui
        loginPage.openPage().login(testUsername, testPassword);
        allDeletePage.openPage()
                .isPageOpened()
                .input("house", houseId)
                .clickDelete("house");

        Thread.sleep(3000);
        assertEquals("Status: 204", allDeletePage.getStatus("house"));
        log.info("Дом удален. ID = {}", houseId);

        //проверяем через БД, что дома действительно нет
        HouseDBConnection connection = new HouseDBConnection();
        connection.connect();
        assertEquals(0, connection.deleted(houseId));
        log.info("Дом с id {} отсутствует в БД", houseId);
        connection.close();
    }
}
