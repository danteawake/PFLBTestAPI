package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class UsersReadAllPage extends BasePage {

    private final ElementsCollection sortFiltersButtons = $(byAttribute("aria-label", "sort"))
            .$$("button");
    private final ElementsCollection tableRows = $$("table.table tbody tr");
    private final ElementsCollection tableHeaders = $$("table.table thead th");

    @Step("Проверяем общую таблицу пользователей: колонка '{columnName}', строка №{rowNumber}")
    public UsersReadAllPage checkTableElementText(String columnName, int rowNumber, String expectedText) {
        return tableStep.checkTableElementText(this, tableHeaders, tableRows, columnName, rowNumber, expectedText);
    }

    @Step("Открытие страницы Users -> Read all")
    public UsersReadAllPage openPage() {
        log.info("Открытие страницы Users -> Read all");
        open("#/read/users");
        sleep(2000);
        return this;
    }

    @Step("Кликаем на кнопку сортировки {0}")
    public UsersReadAllPage sortFilterButtonClick(String buttonName) {
        log.info("Кликаем на кнопку сортировки {}", buttonName);
        sortFiltersButtons.findBy(Condition.text(buttonName)).click();
        sleep(2000);
        return this;
    }

    @Step("Проверяем, что количество строк с данными в таблице равно {expectedCount}")
    public UsersReadAllPage checkTableRowsCount(int expectedCount) {
        int rowsCount = tableRows.size();
        log.info("Количество найденных строк в таблице: {}. Ожидаемое: {}", rowsCount, expectedCount);
        Assert.assertEquals(rowsCount, expectedCount, "Количество строк в таблице не совпадает с ожидаемым!");
        return this;
    }

    @Step("Проверяем, что для кнопки '{buttonName}' отображается знак сортировки '{expectedSign}'")
    public UsersReadAllPage checkSortDirectionSign(String buttonName, String expectedSign) {
        String fullButtonText = sortFiltersButtons.findBy(Condition.text(buttonName)).getText();
        String actualSign = fullButtonText.replaceAll("[^↑↓]", "").trim();
        log.info("Для кнопки '{}' найден знак сортировки: '{}'. Ожидаемый: '{}'",
                buttonName, actualSign, expectedSign);
        Assert.assertEquals(actualSign, expectedSign, "Знак сортировки на кнопке не совпадает с ожидаемым!");
        return this;
    }
}