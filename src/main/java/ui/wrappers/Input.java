package ui.wrappers;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import io.qameta.allure.Step;

@Log4j2
public class Input {

    private final SelenideElement element;
    private final String label;

    public Input(SelenideElement element, String label) {
        this.element = element;
        this.label = label;
    }

    @Step("Вводим значение '{value}' в поле '{label}'")
    public void setValue(String value) {
        log.info("Вводим значение '{}' в поле '{}'", value, label);
        element.setValue(value);
    }
}