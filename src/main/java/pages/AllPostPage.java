package pages;

import com.codeborne.selenide.SelenideElement;
import dto.*;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import wrappers.Input;
import wrappers.RadioButton;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Log4j2

public class AllPostPage extends BasePage {
    private final SelenideElement workspace = $("section.workspace");
    private final Input firstNameInput =
            new Input($("#first_name_send"), "First Name");
    private final Input lastNameInput =
            new Input($("#last_name_send"), "Last Name");
    private final Input ageInput =
            new Input($("#age_send"), "Age");
    private final RadioButton sexRadioButton =
            new RadioButton($$("input[name='sex_send']"), "Sex");
    private final Input moneyInput =
            new Input($("table.table tbody tr td:nth-child(6) input"), "Money");
    private final SelenideElement pushToApiButton = $x("//input[@id='first_name_send']" +
            "/ancestor::table/../div//button[contains(@class,'tableButton')]");
    private final SelenideElement statusMessage = $x("//input[@id='first_name_send']" +
            "/ancestor::table/following-sibling::div//button[contains(@class,'status')]");
    private final SelenideElement newUserIdMessage = $x("//input[@id='first_name_send']" +
            "/ancestor::table/following-sibling::div//button[contains(@class,'newId')]");
    private final Input addMoneyUserIdInput =
            new Input($x("(//input[@id='id_send'])[1]"),"User ID");
    private final Input addMoneyInput = new Input($x("(//input[@id='money_send'])[2]"),
                    "Money");
    private final SelenideElement addMoneyPushButton =
            $x("(//button[contains(@class,'tableButton')])[2]");
    private final SelenideElement addMoneyStatusMessage =
            $x("(//button[contains(@class,'status')])[2]");
    private final SelenideElement newBalanceMessage =
            $x("(//button[contains(@class,'money')])[1]");

    // Создание дома
    private final Input floorsInput =
            new Input($("#floor_send"), "Floors");
    private final Input priceInput = new Input($("#price_send"), "Price");

    private final Input warmCoveredParkingInput = new Input($("#parking_first_send"),
            "Warm covered parking");

    private final Input warmNotCoveredParkingInput = new Input($("#parking_second_send"),
                    "Warm not covered parking");

    private final Input coldCoveredParkingInput = new Input($("#parking_third_send"),
                    "Cold covered parking");

    private final Input coldNotCoveredParkingInput = new Input($("#parking_fourth_send"),
                    "Cold not covered parking");
    private final SelenideElement createHousePushButton =
            $x("(//button[contains(@class,'tableButton')])[6]");
    private final SelenideElement createHouseStatusMessage =
            $x("(//button[contains(@class,'status')])[6]");
    private final SelenideElement newHouseIdMessage =
            $x("(//button[contains(@class,'newId')])[3]");

// добавление дома пользователю

    private final Input addHouseUserIdInput =
            new Input($x("(//input[@id='id_send'])[2]"),"User ID");
    private final Input houseIdInput =
            new Input($("#house_send"),"House ID");
    private final RadioButton settleOrEvictRadioButton =
            new RadioButton($$("input[name='settleOrEvict']"),"Action");
    private final SelenideElement addHousePushButton =
            $x("(//button[contains(@class,'tableButton')])[3]");

    private final SelenideElement addHouseStatusMessage =
            $x("(//button[contains(@class,'status')])[3]");

    // создание автомобиля
    private final Input carEngineTypeInput = new Input($("#car_engine_type_send"),
                    "Car engine type");
    private final Input carMarkInput = new Input($("#car_mark_send"),
                    "Car mark");
    private final Input carModelInput = new Input($("#car_model_send"),
                    "Car model");
    private final Input carPriceInput = new Input($("#car_price_send"),
                    "Car price");
    private final SelenideElement createCarPushButton = $x("//input[@id='car_engine_type_send']" +
                            "/ancestor::table/../div//button[contains(@class,'tableButton')]");

    private final SelenideElement createCarStatusMessage = $x("//input[@id='car_engine_type_send']" +
                            "/ancestor::table/following-sibling::div//button[contains(@class,'status')]");

    private final SelenideElement newCarIdMessage = $x("//input[@id='car_engine_type_send']" +
                            "/ancestor::table/following-sibling::div//button[contains(@class,'newId')]");

    //добавление автомобиля пользователю
    private final Input addCarUserIdInput = new Input($x("//input[@id='car_send']/ancestor::tr//input[@id='id_send']"),
                    "User ID");
    private final Input addCarIdInput = new Input($("#car_send"),"Car ID");
    private final SelenideElement buyCarRadioButton =
            $x("//input[@id='car_send']/ancestor::tr//input[@value='buyCar']");

    private final SelenideElement sellCarRadioButton =
            $x("//input[@id='car_send']/ancestor::tr//input[@value='sellCar']");
    private final SelenideElement addCarPushButton = $x("//input[@id='car_send']" +
                    "/ancestor::table/following-sibling::div//button[contains(@class,'tableButton')]");
    private final SelenideElement addCarStatusMessage = $x("//input[@id='car_send']" +
                    "/ancestor::table/following-sibling::div//button[contains(@class,'status')]");


    @Step("Открытие страницы All Post")
    public AllPostPage openPage() {
        log.info("Открытие страницы All Post");
        open("#/create/all");
        return this;
    }

    @Step("Проверяем открытие страницы All Post")
    public AllPostPage checkPageOpened() {
        workspace.shouldBe(visible);

        log.info("Страница All Post успешно открыта");
        return this;
    }

    @Step("Создаём пользователя")
    public AllPostPage createUser(UserCreateData userCreateData) {

        log.info("Создание пользователя: {}", userCreateData);
        firstNameInput.setValue(userCreateData.getFirstName());
        lastNameInput.setValue(userCreateData.getLastName());
        ageInput.setValue(String.valueOf(userCreateData.getAge()));
        sexRadioButton.select(userCreateData.getSex());
        moneyInput.setValue(String.valueOf(userCreateData.getMoney()));
        pushToApiButton.click();
        log.info("Кнопка PUSH TO API нажата");

        return this;
    }

    @Step("Получаем статус создания пользователя")
    public String getStatusMessage() {
        statusMessage.shouldHave(text("Successfully pushed"));
        String status = statusMessage.getText();
        log.info("Получен статус создания пользователя: {}", status);
        return status;
    }

    @Step("Получаем ID созданного пользователя")
    public int getCreatedUserId() {
        newUserIdMessage.shouldNotBe(empty);
        String text = newUserIdMessage.getText();
        int userId = Integer.parseInt(text.replace("New user ID:", "").trim());
        log.info("Получен ID созданного пользователя: {}", userId);
        return userId;
    }
    @Step("Добавляем деньги пользователю")
    public AllPostPage addMoney(AddMoneyData addMoneyData) {
        log.info("Добавляем деньги пользователю: {}", addMoneyData);
        addMoneyUserIdInput.setValue(
                String.valueOf(addMoneyData.getUserId()));
        addMoneyInput.setValue(
                String.valueOf(addMoneyData.getMoney()));
        addMoneyPushButton.click();
        log.info("Кнопка PUSH TO API нажата");
        return this;
    }
    @Step("Получаем статус добавления денег")
    public String getAddMoneyStatusMessage() {
        addMoneyStatusMessage.shouldNotHave(text("not pushed"));
        String status = addMoneyStatusMessage.getText();
        log.info("Получен статус: {}", status);
        return status;
    }
    @Step("Получаем текущий баланс пользователя")
    public double getCurrentMoney() {
        String text = newBalanceMessage.shouldBe(visible).getText();
        log.info("Получен баланс пользователя: {}", text);
        return Double.parseDouble(text.trim());
    }
    @Step("Получаем новый баланс пользователя")
    public String getNewBalanceMessage() {
        String balance = newBalanceMessage.getText();
        log.info("Получен новый баланс: {}", balance);
        return balance;
    }
    @Step("Создаём дом")
    public AllPostPage createHouse(HouseCreateData houseData) {

        log.info("Создание дома: {}", houseData);
        floorsInput.setValue(String.valueOf(houseData.getFloors()));
        priceInput.setValue(String.valueOf(houseData.getPrice()));
        warmCoveredParkingInput.setValue(String.valueOf(houseData.getWarmCoveredParking()));
        warmNotCoveredParkingInput.setValue(String.valueOf(houseData.getWarmNotCoveredParking()));
        coldCoveredParkingInput.setValue(String.valueOf(houseData.getColdCoveredParking()));
        coldNotCoveredParkingInput.setValue(String.valueOf(houseData.getColdNotCoveredParking()));
        createHousePushButton.click();
        log.info("Кнопка PUSH TO API нажата");

        return this;
    }
    @Step("Получаем статус создания дома")
    public String getCreateHouseStatusMessage() {
        createHouseStatusMessage.shouldHave(text("Successfully pushed"));
        String status = createHouseStatusMessage.getText();
        log.info("Получен статус создания дома: {}", status);

        return status;
    }
    @Step("Получаем ID созданного дома")
    public int getCreatedHouseId() {
        newHouseIdMessage.shouldHave(text("New house ID:"));
        String text = newHouseIdMessage.getText();
        int houseId = Integer.parseInt(text.replace("New house ID:", "").trim());
        log.info("Получен ID дома: {}", houseId);
        return houseId;
    }

    @Step("Добавляем дом пользователю")
    public AllPostPage addHouse(AddHouseData data) {

        log.info("Добавляем дом пользователю: {}", data);
        addHouseUserIdInput.setValue(String.valueOf(data.getUserId()));
        houseIdInput.setValue(String.valueOf(data.getHouseId()));
        settleOrEvictRadioButton.select(data.getAction());
        addHousePushButton.click();
        log.info("Кнопка PUSH TO API нажата");

        return this;
    }
    @Step("Получаем статус добавления дома")
    public String getAddHouseStatusMessage() {
        addHouseStatusMessage.shouldNotHave(text("not pushed"));
        String status = addHouseStatusMessage.getText();
        log.info("Получен статус: {}", status);
        return status;
    }
    @Step("Создаём машину")
    public AllPostPage createCar(CarCreateData carData) {
        log.info("Создание машины: {}", carData);
        carEngineTypeInput.setValue(carData.getEngineType());
        carModelInput.setValue(carData.getModel());
        carMarkInput.setValue(carData.getMark());
        carPriceInput.setValue(String.valueOf(carData.getPrice()));
        createCarPushButton.click();
        log.info("Кнопка PUSH TO API нажата");

        return this;
    }
    @Step("Получаем статус создания машины")
    public String getCreateCarStatusMessage() {
        createCarStatusMessage.shouldHave(text("Successfully pushed"));
        String status = createCarStatusMessage.getText();
        log.info("Получен статус создания машины: {}", status);
        return status;
    }
    @Step("Получаем ID созданной машины")
    public int getCreatedCarId() {

        newCarIdMessage.shouldHave(text("New car ID:"));
        String text = newCarIdMessage.getText();
        int carId = Integer.parseInt(text.replace("New car ID:", "").trim());
        log.info("Получен ID машины: {}", carId);
        return carId;
    }
    @Step("Добавляем машину пользователю")
    public AllPostPage addCarToUser(AddCarData data) {
        log.info("Добавляем машину пользователю: {}", data);
        addCarUserIdInput.setValue(String.valueOf(data.getUserId()));
        addCarIdInput.setValue(String.valueOf(data.getCarId()));
        settleOrEvictRadioButton.select(data.getAction());
        addCarPushButton.click();
        log.info("Кнопка PUSH TO API нажата");

        return this;
    }
    @Step("Получаем статус добавления машины пользователю")
    public String getAddCarStatusMessage() {
        addCarStatusMessage.shouldNotHave(text("not pushed"));
        String status = addCarStatusMessage.getText();
        log.info("Получен статус: {}", status);
        return status;
    }
}