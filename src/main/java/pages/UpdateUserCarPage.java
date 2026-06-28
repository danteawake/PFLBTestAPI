package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

@Log4j2
public class UpdateUserCarPage extends BasePage {

    private final SelenideElement userIdInput = $x("//input[@id='id_send']");
    private final SelenideElement carIdInput = $x("//input[@id='car_send']");
    private final SelenideElement buyRadio = $x("//input[@type='radio' and @value='buyCar']");
    private final SelenideElement sellRadio = $x("//input[@type='radio' and @value='sellCar']");
    private final SelenideElement pushButton = $x("//button[contains(@class,'tableButton')]");
    private final SelenideElement statusButton = $x("//button[contains(@class,'status')]");

    @Step("Открыть страницу Users -> Buy or sell car")
    public UpdateUserCarPage openPage() {
        log.info("Открытие страницы Users -> Buy or sell car");
        usersBuyOrSellCarTab.selectItem();
        userIdInput.shouldBe(visible);
        return this;
    }

    @Step("Купить машину с ID {carId} для пользователя {userId}")
    public UpdateUserCarPage buyCar(int userId, int carId) {
        log.info("Покупка машины (ID: {}) для пользователя {}", carId, userId);

        // Вводим ID пользователя и машины
        userIdInput.setValue(String.valueOf(userId));
        carIdInput.setValue(String.valueOf(carId));

        // Выбираем radio-кнопку BUY
        buyRadio.shouldBe(visible).click();
        log.info("Выбран режим: BUY (radio)");
        sleep(500); // небольшая задержка для активации

        // Нажимаем PUSH TO API
        pushButton.shouldBe(visible).click();
        log.info("Кнопка PUSH TO API нажата (покупка)");

        return this;
    }

    @Step("Продать машину с ID {carId} у пользователя {userId}")
    public UpdateUserCarPage sellCar(int userId, int carId) {
        log.info("Продажа машины (ID: {}) у пользователя {}", carId, userId);

        // Вводим ID пользователя и машины
        userIdInput.setValue(String.valueOf(userId));
        carIdInput.setValue(String.valueOf(carId));

        // Выбираем radio-кнопку SELL
        sellRadio.shouldBe(visible).click();
        log.info("Выбран режим: SELL (radio)");
        sleep(500); // небольшая задержка для активации

        // Нажимаем PUSH TO API
        pushButton.shouldBe(visible).click();
        log.info("Кнопка PUSH TO API нажата (продажа)");

        return this;
    }

    @Step("Проверить статус: {expectedText}")
    public UpdateUserCarPage checkStatus(String expectedText) {
        statusButton.shouldBe(visible);
        statusButton.shouldHave(com.codeborne.selenide.Condition.text(expectedText));
        log.info("Статус проверен: {}", statusButton.getText());
        return this;
    }

    @Step("Получить текст статуса")
    public String getStatusText() {
        String status = statusButton.getText();
        log.info("Получен статус: {}", status);
        return status;
    }
}