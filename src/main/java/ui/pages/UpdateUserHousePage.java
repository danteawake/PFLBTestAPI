package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

@Log4j2
public class UpdateUserHousePage extends BasePage {

    private final SelenideElement userIdInput = $x("//input[@id='id_send']");
    private final SelenideElement houseIdInput = $x("//input[@id='house_send']");
    private final SelenideElement settleRadio = $x("//input[@type='radio' and @value='settle']");
    private final SelenideElement evictRadio = $x("//input[@type='radio' and @value='evict']");
    private final SelenideElement pushButton = $x("//button[contains(@class,'tableButton')]");
    private final SelenideElement statusButton = $x("//button[contains(@class,'status')]");

    @Step("Открыть страницу Houses -> Settle or evict user")
    public UpdateUserHousePage openPage() {
        log.info("Открытие страницы Houses -> Settle or evict user");
        housesSettleOrEvictUserTab.selectItem();
        userIdInput.shouldBe(visible);
        return this;
    }

    @Step("Заселить пользователя {userId} в дом {houseId}")
    public UpdateUserHousePage settleUser(int userId, int houseId) {
        log.info("Заселение пользователя {} в дом {}", userId, houseId);

        userIdInput.setValue(String.valueOf(userId));
        houseIdInput.setValue(String.valueOf(houseId));

        settleRadio.shouldBe(visible).click();
        log.info("Выбран режим: SETTLE (radio)");
        sleep(500);

        pushButton.shouldBe(visible).click();
        log.info("Кнопка PUSH TO API нажата (заселение)");

        return this;
    }

    @Step("Выселить пользователя {userId} из дома {houseId}")
    public UpdateUserHousePage evictUser(int userId, int houseId) {
        log.info("Выселение пользователя {} из дома {}", userId, houseId);

        userIdInput.setValue(String.valueOf(userId));
        houseIdInput.setValue(String.valueOf(houseId));

        evictRadio.shouldBe(visible).click();
        log.info("Выбран режим: EVICT (radio)");
        sleep(500);

        pushButton.shouldBe(visible).click();
        log.info("Кнопка PUSH TO API нажата (выселение)");

        return this;
    }

    @Step("Проверить статус: {expectedText}")
    public UpdateUserHousePage checkStatus(String expectedText) {
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
