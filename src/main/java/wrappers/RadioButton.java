package wrappers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import lombok.extern.log4j.Log4j2;
import io.qameta.allure.Step;

@Log4j2
public class RadioButton {

    private final ElementsCollection radioButtons;
    private final String label;

    public RadioButton(ElementsCollection radioButtons, String label) {
        this.radioButtons = radioButtons;
        this.label = label;
    }

    @Step("Выбираем значение '{value}' для поля '{label}'")
    public void select(String value) {
        log.info("Выбираем значение '{}' для поля '{}'", value, label);

        radioButtons.findBy(Condition.value(value))
                .click();
    }
}