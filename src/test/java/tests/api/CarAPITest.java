package tests.api;

import adapters.CarAdapter;
import db.CarDBConnection;
import lombok.extern.slf4j.Slf4j;
import models.positive.CarRequest;
import models.positive.CarRequestUpdate;
import models.positive.CarResponse;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

@Slf4j
public class CarAPITest extends BaseAPITest {

    CarRequest carRq = CarRequest.builder()
            .engineType("Gasoline")
            .mark("Volvo")
            .model("Fantom")
            .price(2000)
            .build();

    CarRequest carRqWithWrongEngineType = CarRequest.builder()
            .engineType("gasoline")
            .mark("Volvo")
            .model("Fantom")
            .price(14000)
            .build();

    @Test(description = "Создание автомобиля с корректными параметрами",
            testName = "Создание автомобиля")
    public void checkCreatingCar() {
        CarDBConnection connection = new CarDBConnection();
        connection.connect();
        CarResponse carRs = CarAdapter.createCar(carRq, token);//Создаем автомобиль
        int carId = carRs.id;
        assertEquals(
                connection.select(carId), carId);
        log.info("Автомобиль с Id {} успешно создан", carId);
        CarResponse carResponse = CarAdapter.getCar(carId, token); //Получаем созданный автомобиль
        assertEquals(carResponse.mark, carRq.mark);
        assertEquals(carResponse.engineType, carRq.engineType);
        assertEquals(carResponse.model, carRq.model);
        assertEquals(carResponse.price, carRq.price);
        CarRequestUpdate carRqUpdate = CarRequestUpdate.builder()//Изменяем тип двигателя у созданного автомобиля
                .engineType("Electric")
                .id(carId)
                .mark("Volvo")
                .model("Fantom")
                .price(5000)
                .build();
        CarResponse carRsUpdated = CarAdapter.updateCar(carId, carRqUpdate, token);
        int carIdUpdated = carRsUpdated.id;
        String engineType = carRsUpdated.engineType;
        assertEquals(connection.selectUpdated(carIdUpdated), engineType);
        log.info("Автомобиль с id {} успешно изменен", carIdUpdated);

        CarAdapter.deleteCar(carId, token);                                   //Удаляем созданный автомобиль
        assertEquals(0, connection.deleted(carIdUpdated));
        log.info("Автомобиль с id {} успешно удален", carId);
        connection.close();
    }
    @Test(description = "Создание автомобиля с некорректными параметрами",
            testName = "Создание автомобиля с некорректным Engine_Type")
    public void checkCreatingWithNotAllFields(){
        CarDBConnection connection = new CarDBConnection();
        connection.connect();
        CarAdapter.createCarWithIncorrectEngineType(carRqWithWrongEngineType,token);
    }
}
