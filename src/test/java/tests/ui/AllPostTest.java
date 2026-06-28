package tests.ui;

import dto.*;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class AllPostTest extends BaseTest {

    @Test(description = "Проверка открытия страницы All Post",
          testName = "Открытие страницы All Post")
    @Description("Проверка успешного открытия страницы All Post после авторизации")
    @Owner("Шемякина Юлия")
    public void checkOpenAllPostPage() {

        loginPage.openPage()
                .login(testUsername, testPassword);

        allPostPage.openPage()
                .checkPageOpened();
    }

    @Test(description = "Проверка создания пользователя через All Post",
          testName = "Создание пользователя")
    @Description("Проверка успешного создания пользователя через форму All Post")
    @Owner("Шемякина Юлия")
    public void checkCreateUserWithFirstName() {

        UserCreateData userCreateData = UserCreateData.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .age(25)
                .sex("MALE")
                .money(1000)
                .build();

        loginPage.openPage()
                .login(testUsername, testPassword);

        allPostPage.openPage()
                .checkPageOpened()
                .createUser(userCreateData);
        Assert.assertEquals(allPostPage.getStatusMessage(),
                "Status: Successfully pushed, code: 201"
        );
        Assert.assertTrue(allPostPage.getCreatedUserId() > 0,
                "ID пользователя не был сгенерирован");
    }

    @Test(description = "Проверка добавления денег пользователю",
          testName = "Добавление денег пользователю")
    @Description("Проверка успешного пополнения баланса пользователя")
    @Owner("Шемякина Юлия")
    public void checkAddMoneyToUser() {

        double initialMoney = 500;
        double addedMoney = 500;

        log.info("Создаём пользователя с балансом {}", initialMoney);
        UserCreateData userCreateData = UserCreateData.builder()//сначала создаем пользователя
                .firstName("Max")
                .lastName("Ivanov")
                .age(20)
                .sex("MALE")
                .money(initialMoney)
                .build();

        loginPage.openPage()
                .login(testUsername, testPassword);

        allPostPage.openPage()
                .createUser(userCreateData);

        int userId = allPostPage.getCreatedUserId();
        log.info("Пользователь создан, ID = {}", userId);

        AddMoneyData addMoneyData = AddMoneyData.builder()
                .userId(userId)
                .money(addedMoney)
                .build();
        log.info("Добавляем деньги: {}", addedMoney);
        allPostPage.addMoney(addMoneyData);

        Assert.assertEquals(
                allPostPage.getAddMoneyStatusMessage(),
                "Status: Successfully pushed, code: 200"
        );
        double expectedBalance = initialMoney + addedMoney;
        double actualBalance = allPostPage.getCurrentMoney();

        log.info("Ожидаемый баланс: {}", expectedBalance);
        log.info("Фактический баланс: {}", actualBalance);

        Assert.assertEquals(
                actualBalance,
                expectedBalance,
                "Баланс пользователя рассчитан неверно");
    }
    @Test(description = "Проверка создания дома",
          testName = "Создание дома")
    @Description("Проверка успешного создания дома через форму All Post")
    @Owner("Шемякина Юлия")
    public void checkCreateHouse() {

        HouseCreateData houseData = HouseCreateData.builder()
                .floors(2)
                .price(350000)
                .warmCoveredParking(5)
                .warmNotCoveredParking(3)
                .coldCoveredParking(2)
                .coldNotCoveredParking(1)
                .build();

        loginPage.openPage()
                .login(testUsername, testPassword);

        allPostPage.openPage()
                .checkPageOpened()
                .createHouse(houseData);

        Assert.assertEquals(
                allPostPage.getCreateHouseStatusMessage(),
                "Status: Successfully pushed, code: 201"
        );
        Assert.assertTrue(
                allPostPage.getCreatedHouseId() > 0,
                "ID дома не был сгенерирован"
        );
    }
    @Test(description = "Проверка добавления дома пользователю",
          testName = "Добавление дома пользователю")
    @Description("Проверка успешного заселения пользователя в дом")
    @Owner("Шемякина Юлия")
    public void checkAddHouseToUser() {

        UserCreateData userCreateData = UserCreateData.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .age(25)
                .sex("MALE")
                .money(100000)
                .build();

        HouseCreateData houseCreateData = HouseCreateData.builder()
                .floors(5)
                .price(50000)
                .warmCoveredParking(2)
                .warmNotCoveredParking(5)
                .coldCoveredParking(3)
                .coldNotCoveredParking(2)
                .build();

        loginPage.openPage()
                .login(testUsername, testPassword);

        allPostPage.openPage()
                .checkPageOpened()
                .createUser(userCreateData);

        int userId = allPostPage.getCreatedUserId();

        allPostPage.createHouse(houseCreateData);

        int houseId = allPostPage.getCreatedHouseId();

        AddHouseData addHouseData = AddHouseData.builder()
                .userId(userId)
                .houseId(houseId)
                .action("settle")
                .build();

        allPostPage.addHouse(addHouseData);

        Assert.assertEquals(
                allPostPage.getAddHouseStatusMessage(),
                "Status: Successfully pushed, code: 200"
        );

    }
    @Test(description = "Проверка создания машины",
          testName = "Создание машины")
    @Description("Проверка успешного создания машины")
    @Owner("Шемякина Юлия")
    public void checkCreateCar() {

        CarCreateData carCreateData = CarCreateData.builder()
                .engineType("Gasoline")
                .model("BMW X5")
                .mark("BMW")
                .price(3500000.0)
                .build();

        loginPage.openPage()
                .login(testUsername, testPassword);

        allPostPage.openPage()
                .checkPageOpened()
                .createCar(carCreateData);

        Assert.assertEquals(
                allPostPage.getCreateCarStatusMessage(),
                "Status: Successfully pushed, code: 201"
        );

        Assert.assertTrue(
                allPostPage.getCreatedCarId() > 0,
                "ID машины не был сгенерирован"
        );
    }
    @Test(description = "Проверка добавления машины пользователю",
          testName = "Добавление машины пользователю"
    )
    @Description("Проверка успешной покупки машины пользователем")
    @Owner("Шемякина Юлия")
    public void checkAddCarToUser() {

        // создаем USER
        UserCreateData userCreateData = UserCreateData.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .age(25)
                .sex("MALE")
                .money(600000)
                .build();

        // создаем CAR
        CarCreateData carCreateData = CarCreateData.builder()
                .engineType("Gasoline")
                .model("BMW X5")
                .mark("BMW")
                .price(500000.00)
                .build();

        loginPage.openPage()
                .login(testUsername, testPassword);

        allPostPage.openPage()
                .checkPageOpened()
                .createUser(userCreateData);

        int userId = allPostPage.getCreatedUserId();

        allPostPage.createCar(carCreateData);

        int carId = allPostPage.getCreatedCarId();

        AddCarData addCarToUserData = AddCarData.builder()
                .userId(userId)
                .carId(carId)
                .action("buyCar")
                .build();

        allPostPage.addCarToUser(addCarToUserData);

        Assert.assertEquals(
                allPostPage.getAddCarStatusMessage(),
                "Status: Successfully pushed, code: 200"
        );
    }
}