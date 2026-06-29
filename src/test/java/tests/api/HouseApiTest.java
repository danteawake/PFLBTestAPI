package tests.api;

import adapters.HouseAdapter;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import models.positive.HouseRequest;
import models.positive.HouseResponse;
import org.testng.annotations.Test;
import java.util.Collections;

import static adapters.HouseAdapter.*;
import static org.testng.Assert.assertEquals;

@Slf4j
public class HouseApiTest extends BaseAPITest {

    private static final Faker faker = new Faker();
    private final int floorCount = faker.number().numberBetween(1, 30);
    private final double price = faker.number().randomDouble(1, 10, 100000);
    private final int parkingCount = faker.number().numberBetween(1, 10);

    HouseRequest.HouseParkingPlace parkingPlace = HouseRequest.HouseParkingPlace.builder()
            .isWarm(true)
            .isCovered(true)
            .placesCount(parkingCount)
            .build();

    HouseRequest houseRequest = HouseRequest.builder()
            .floorCount(floorCount)
            .price(price)
            .parkingPlaces(Collections.singletonList(parkingPlace))
            .lodgers(Collections.emptyList())
            .build();

    private final int newFloorCount = faker.number().numberBetween(1, 30);
    private final double newPrice = faker.number().randomDouble(1, 10, 100000);
    private final int newParkingCount = faker.number().numberBetween(1, 10);

    HouseRequest.HouseParkingPlace newParkingPlace = HouseRequest.HouseParkingPlace.builder()
            .isWarm(false)
            .isCovered(true)
            .placesCount(newParkingCount)
            .build();

    HouseRequest newHouse = HouseRequest.builder()
            .floorCount(newFloorCount)
            .price(newPrice)
            .parkingPlaces(Collections.singletonList(newParkingPlace))
            .lodgers(Collections.emptyList())
            .build();

    @Test(description = "CRUD тест для объекта House")
    public void checkCRUDHouse() {
        log.info("=== Начало CRUD теста для дома ===");
        log.info("=== Создание дома ===");
        HouseResponse rs = HouseAdapter.createHouse(houseRequest, token);
        Integer id = rs.getId();
        assertEquals(rs.getFloorCount(), houseRequest.getFloorCount(), "Количество этажей не совпадает после создания");
        assertEquals(rs.getPrice(), houseRequest.getPrice(), "Цена не совпадает после создания");
        log.info("Создан дом с ID: {}, этажей: {}, цена: {}", id, rs.getFloorCount(), rs.getPrice());
        getHouse200(id, token);
        log.info("=== Обновление дома ===");
        HouseResponse updateRs = HouseAdapter.updateHouse(id, newHouse, token);
        assertEquals(updateRs.getFloorCount(), newFloorCount, "Количество этажей не совпадает после обновления");
        assertEquals(updateRs.getPrice(), newPrice, "Цена не совпадает после обновления");
        log.info("Обновлен дом с ID: {}, этажей: {}, цена: {}", id, rs.getFloorCount(), rs.getPrice());
        log.info("Удаление дома");
        deleteHouse(id, token);
        deleteHouse404(id, token);
        log.info("GET запрос для удаленного дома вернул 204 (ожидаемо)");
        getHouse204(id, token);
    }
}
