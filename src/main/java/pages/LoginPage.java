package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Log4j2
public class LoginPage extends BasePage {

    private final SelenideElement emailInput = $(byName("email"));
    private final SelenideElement passwordInput = $(byName("password"));
    private final SelenideElement signInButton = $(byTagAndText("button", "GO"));

    public LoginPage openPage() {
        log.info("Открытие страницы авторизации.");
        open("");
        return this;
    }

    public LoginPage login(String email, String password) {
        log.info("Авторизация с почтой: {} и паролем: {}", email, password);
        emailInput.setValue(email);
        passwordInput.setValue(password);
        signInButton.click();
        Selenide.confirm("Successful authorization");
        return this;
    }
}
