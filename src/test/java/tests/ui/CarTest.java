package tests.ui;

import adapters.CarAdapter;
import adapters.LoginAdapter;
import db.CarDBConnection;
import dto.Car;
import dto.CarNotFull;
import dto.User;
import jdk.jfr.Description;
import jdk.jfr.Enabled;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

@Log4j2
@Test(description = "Открытие страницы логина с корректными кредами",
testName = "Открытие страницы логина")
@Description("Открытие страницы логина с корректными кредами")
public class CarTest extends BaseTest {

    private String apiToken; // Поле для хранения токена внутри класса

    @BeforeMethod(description = "Получение токена авторизации для предварительных API-шагов")
    public void setUpApiToken() {
        // Запрашиваем токен перед запуском теста
        apiToken = LoginAdapter.loginApi().getAccessToken();
    }

    public void checkOpenedPage() {
        loginPage.openPage().login(
                User.userStandard().getUsername(),
                User.userStandard().getPassword());
    }
@Description("Сортировка автомобилей")
    public void checkCarsSorting() {
        carReaAll.openPage()
                .checkOpenedPage()
                .checkElementsOnPage()
                .checkSortingById()
                .checkSortingEngineType2Mark32Model4(2, "Engine Type")
                .checkSortingEngineType2Mark32Model4(3, "Mark")
                .checkSortingEngineType2Mark32Model4(4, "Model")
                .checkSortingByPrice();
    }

    @Description("Создание автомобиля с корректными данными")
    public void checkCreatingCar() {
        loginPage.openPage()
                .login(User.userStandard().getUsername(),
                        User.userStandard().getPassword());
        createNewCarPage.openCreateNewCarPage();
        Car car = new Car("CNG", "Volvo", "S14", 50000.00);
        int carId = createNewCarPage.addNewCar(car);
        CarDBConnection connection = new CarDBConnection();
        connection.connect();
        Assert.assertEquals(connection.selectCreatedCar(carId), 1);
        log.info("Id созданного автомобиля найден в таблице БД car");
        CarAdapter.deleteCar(carId, apiToken);                                   //Удаляем созданный автомобиль
        assertEquals(0, connection.deleted(carId));
        log.info("Автомобиль с id {} успешно удален", carId);
        connection.close();
    }

    @Description("Создание автомобиля не со всеми полями")
    public void checkCreatingCarWithNotAllFields() {
        loginPage.openPage()
                .login(User.userStandard().getUsername(),
                        User.userStandard().getPassword());
        createNewCarPage.openCreateNewCarPage();
        CarNotFull carNotFull = new CarNotFull("CNG", "Volvo", "S14");
        createNewCarPage.createCarWithNotAllFields(carNotFull);
    }
}
