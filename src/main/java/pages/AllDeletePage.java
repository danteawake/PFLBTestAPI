package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

@Log4j2

public class AllDeletePage extends BasePage {
    //локаторы
    private final String DELETE_BUTTON = "//button[@value = '%s']";//кнопка удаления
    private final String DELETE_INPUT = "//button[@value = '%s']/ancestor::div[@role='group']//input";// поле ввода ID
    private final String DELETE_STATUS = "//button[@value = '%s']/ancestor::div[@role='group']" +
            "//button[@class='status btn btn-secondary']";//текст статуса

    //шаги
    @Step("Открытие страницы ALL DELETE")
    public AllDeletePage openPage() {
        log.info("Открытие страницы ALL DELETE");
        open("#/delete/all");//открываем страницу по адресу
        return this;
    }

    @Step("Проверка открытия страницы ALL DELETE")
    public AllDeletePage isPageOpened() {
        try {
            $x(String.format(DELETE_BUTTON, "user")).shouldBe(visible);//пробуем найти кнопку
            log.info("AllDeletePage открыта");
        } catch (Exception e) {
            log.error("AllDeletePage не открыта: {}", e.getMessage());
            Assert.fail("AllDeletePage не открыта: " + e.getMessage());
        }
        return this;
    }

    @Step("Ввод в поле {buttonname}: {value}")
    public AllDeletePage input(String buttonname, int id) {
        $x(String.format(DELETE_INPUT, buttonname)).sendKeys(String.valueOf(id));
        log.info("Ввод в поле {} значения {}", buttonname, id);
        return this;
    }

    @Step("Нажатие кнопки 'DELETE' {buttonname}")
    public AllDeletePage clickDelete(String buttonname) {
        $x(String.format(DELETE_BUTTON, buttonname)).click();
        log.info("Нажатие Delete {}", buttonname);
        return this;
    }

    @Step("Получения статуса {buttonname}")
    public String getStatus(String buttonname) {
        String status = $x(String.format(DELETE_STATUS, buttonname)).text();
        log.info("Получение статуса {} : {}", buttonname, status);
        return status;
    }
}
