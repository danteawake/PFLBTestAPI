package wrappers;

import com.codeborne.selenide.Selenide;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selectors.byText;

@Log4j2
public class DropDown {
    private final String tab;
    private final String choice;

    public DropDown(String tab, String choice) {
        this.tab = tab;
        this.choice = choice;
    }

    public void selectItem() {
        log.info("Раскрываем меню... {}", tab);
        var currentDropdown = Selenide.$$(".nav-item.dropdown")
                .findBy(com.codeborne.selenide.Condition.text(tab));

        currentDropdown.$(byText(tab)).click();

        log.info("Выбираем пункт меню... {}", choice);
        currentDropdown.$(byText(choice)).click();
    }
}