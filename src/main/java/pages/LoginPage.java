package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    private final SelenideElement emailInput = $(byName("email"));
    private final SelenideElement passwordInput = $(byName("password"));
    private final SelenideElement signInButton = $(byTagAndText("button", "GO"));

    public void openPage() {
        open("");
    }

    public void login(String email, String password) {
        emailInput.setValue(email);
        passwordInput.setValue(password);
        signInButton.click();
    }
}
