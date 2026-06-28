package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

@Log4j2
public class CreateHousePage extends BasePage {

    private final SelenideElement floorCountInput = $x("//input[@id='floor_send']");
    private final SelenideElement priceInput = $x("//input[@id='price_send']");
    private final SelenideElement pushButton = $x("//button[contains(@class,'tableButton')]");
    private final SelenideElement statusButton = $x("//button[contains(@class,'status')]");
    private final SelenideElement resultButton = $x("//button[contains(@class,'newId')]");

    @Step("Открыть страницу создания дома")
    public CreateHousePage openPage() {
        log.info("Открытие страницы создания дома");
        housesCreateNewTab.selectItem();
        floorCountInput.shouldBe(visible);
        return this;
    }

    @Step("Создать дом с этажностью {floorCount} и ценой {price}")
    public int createHouse(int floorCount, double price) {
        log.info("Создание дома: этажность {}, цена {}", floorCount, price);

        openPage();

        floorCountInput.setValue(String.valueOf(floorCount));
        priceInput.setValue(String.valueOf(price));
        pushButton.click();
        sleep(1000);

        statusButton.shouldHave(com.codeborne.selenide.Condition.text("Successfully pushed"));
        log.info("Статус создания проверен: {}", statusButton.getText());

        String text = resultButton.getText();
        int houseId = Integer.parseInt(text.replaceAll("[^\\d]", ""));
        log.info("Получен ID созданного дома: {}", houseId);
        return houseId;
    }

    @Step("Проверить статус: {expectedText}")
    public CreateHousePage checkStatus(String expectedText) {
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