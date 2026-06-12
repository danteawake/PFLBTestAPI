package step;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;

@Log4j2
public class TableStep {

    @Step("Проверяем, что текст ячейки в колонке '{columnName}', строка №{rowNumber} равен '{expectedText}'")
    public <T> T checkTableElementText(T page, ElementsCollection tableHeaders, ElementsCollection tableRows,
                                       String columnName, int rowNumber, String expectedText) {
        int columnIndex = -1;
        for (int i = 0; i < tableHeaders.size(); i++) {
            String headerText = tableHeaders.get(i).getText().replace("\u00A0", " ").trim();
            if (headerText.equalsIgnoreCase(columnName.trim())) {
                columnIndex = i;
                break;
            }
        }
        if (columnIndex == -1) {
            throw new IllegalArgumentException("Колонка с названием '" + columnName + "' не найдена в таблице!");
        }

        String cellText = tableRows.get(rowNumber - 1).$$("td").get(columnIndex).getText();
        log.info("Значение в ячейке [колонка: {}, строка: {}]: {}. Ожидаемое: {}",
                columnName, rowNumber, cellText, expectedText);

        Assert.assertEquals(cellText, expectedText, "Текст ячейки не совпадает!");
        return page;
    }

    @Step("Проверяем, что на кнопке статуса отображается текст '{expectedText}'")
    public <T> T checkStatusButtonText(T page, SelenideElement statusButton, String expectedText) {
        String actualText = statusButton.getText().trim();
        log.info("Текст на кнопке статуса: '{}'. Ожидаемый: '{}'", actualText, expectedText);
        Assert.assertEquals(actualText, expectedText, "Текст на кнопке статуса не совпадает!");
        return page;
    }
}