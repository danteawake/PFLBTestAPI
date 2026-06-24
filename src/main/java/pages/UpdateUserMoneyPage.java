package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Log4j2
public class UpdateUserMoneyPage extends BasePage {

    private final SelenideElement userIdInput = $("#id_send");
    private final SelenideElement moneyInput = $("#money_send");
    private final SelenideElement pushButton = $("button.tableButton");
    private final SelenideElement statusButton = $("button.status");

    @Step("Открыть страницу добавления денег")
    public UpdateUserMoneyPage openPage() {
        log.info("Открытие страницы добавления денег");
        open("#/update/users/plusMoney");
        userIdInput.shouldBe(visible);
        return this;
    }

    @Step("Добавить пользователю {userId} сумму {amount}")
    public UpdateUserMoneyPage addMoney(int userId, int amount) {
        log.info("Добавление {} денег пользователю {}", amount, userId);
        userIdInput.setValue(String.valueOf(userId));
        moneyInput.setValue(String.valueOf(amount));
        pushButton.shouldBe(visible).click();
        log.info("Кнопка PUSH TO API нажата");
        return this;
    }

    @Step("Проверить статус добавления денег: {expectedText}")
    public UpdateUserMoneyPage checkStatus(String expectedText) {
        statusButton.shouldBe(visible);
        statusButton.shouldHave(com.codeborne.selenide.Condition.text(expectedText));
        log.info("Статус добавления проверен: {}", statusButton.getText());
        return this;
    }
}