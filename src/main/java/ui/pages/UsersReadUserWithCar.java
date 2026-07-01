package ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class UsersReadUserWithCar extends BasePage {

    // Локаторы для первой таблицы (Пользователь)
    private final ElementsCollection userTableHeaders = $$("table.tableUser thead th");
    private final ElementsCollection userTableRows = $$("table.tableUser tbody tr");

    // Локаторы для второй таблицы (Автомобили)
    private final ElementsCollection carTableHeaders = $$("table.tableCars thead th");
    private final ElementsCollection carTableRows = $$("table.tableCars tbody tr");

    private final SelenideElement userIdInput = $("#user_input");
    private final SelenideElement readButton = $(byText("Read"));
    private final SelenideElement statusButton = $("button.status");

    @Step("Открытие страницы Users -> Read user with car")
    public UsersReadUserWithCar openPage() {
        log.info("Открытие страницы Users -> Read user with car");
        open("#/read/userInfo");
        sleep(2000);
        return this;
    }

    @Step("Вводим ID пользователя '{0}' и кликаем 'Read'")
    public UsersReadUserWithCar enterAndReadUserId(int userId) {
        log.info("Вводим значение в поле ID: {}. Кликаем на кнопку 'Read'", userId);
        userIdInput.setValue(String.valueOf(userId));
        sleep(500);
        readButton.click();
        sleep(2000);
        return this;
    }

    @Step("Проверяем таблицу пользователя: колонка '{columnName}', строка №{rowNumber}")
    public UsersReadUserWithCar checkUserTableText(String columnName, int rowNumber, String expectedText) {
        return tableStep.checkTableElementText(this, userTableHeaders, userTableRows, columnName, rowNumber, expectedText);
    }

    @Step("Проверяем таблицу автомобилей: колонка '{columnName}', строка №{rowNumber}")
    public UsersReadUserWithCar checkCarTableText(String columnName, int rowNumber, String expectedText) {
        return tableStep.checkTableElementText(this, carTableHeaders, carTableRows, columnName, rowNumber, expectedText);
    }

    @Step("Проверяем сообщение о статусе ответа: '{expectedText}'")
    public UsersReadUserWithCar checkStatusMessage(String expectedText) {
        return tableStep.checkStatusButtonText(this, statusButton, expectedText);
    }
}
