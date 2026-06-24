package tests.ui;

import adapters.CarAdapter;
import db.CarDBConnection;
import dto.Car;
import dto.User;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

@Log4j2
@Test
public class CarTest extends BaseTest {
    public void checkOpenedPage() {
        loginPage.openPage().login(
                User.userStandard().getUsername(),
                User.userStandard().getPassword());
    }

    public void checkCarsSorting() {
        carReaAll.openPage();
        carReaAll.checkOpenedPage();
        carReaAll.checkElementsOnPage();
        carReaAll.checkSortingById();
        carReaAll.checkSortingEngineType2Mark32Model4(2, "Engine Type");
        carReaAll.checkSortingEngineType2Mark32Model4(3, "Mark");
        carReaAll.checkSortingEngineType2Mark32Model4(4, "Model");
        carReaAll.checkSortingByPrice();
    }

    public void checkCreatingCar() {
        loginPage.openPage();
        loginPage.login(User.userStandard().getUsername(),
                User.userStandard().getPassword());
        createNewCarPage.openCreateNewCarPage();
        Car car = new Car("CNG", "Volvo", "S14", 50000.00);
        int carId = createNewCarPage.addNewCar(car);
        CarDBConnection connection = new CarDBConnection();
        connection.connect();
        Assert.assertEquals(connection.selectCreatedCar(carId), 1);
        log.info("Id созданного автомобиля найден в таблице БД car");
        CarAdapter.deleteCar(carId);                                   //Удаляем созданный автомобиль
        assertEquals(0, connection.deleted(carId));
        log.info("Автомобиль с id {} успешно удален", carId);
        connection.close();
    }
}
