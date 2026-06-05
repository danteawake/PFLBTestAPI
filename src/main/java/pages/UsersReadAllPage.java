package pages;

import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.open;

@Log4j2
public class UsersReadAllPage extends BasePage {

    public void openPage() {
        log.info("Открытие страницы Users -> Read all");
        open("#/read/users");
    }
}