package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Log4j2
public class CreateUserPage extends BasePage {

    // Локаторы
    private final SelenideElement firstNameInput = $("#first_name_send");
    private final SelenideElement lastNameInput = $("#last_name_send");
    private final SelenideElement ageInput = $("#age_send");
    private final SelenideElement maleRadio = $("input[name='sex_send'][value='MALE']");
    private final SelenideElement femaleRadio = $("input[name='sex_send'][value='FEMALE']");
    private final SelenideElement moneyInput = $("#money_send");
    private final SelenideElement pushButton = $("button.tableButton");
    private final SelenideElement statusButton = $("button.status");
    private final SelenideElement userIdButton = $("button.newId");

    @Step("Открыть страницу создания пользователя")
    public CreateUserPage openPage() {
        log.info("Открытие страницы создания пользователя");
        open("#/create/user");
        firstNameInput.shouldBe(visible);
        return this;
    }

    @Step("Создать пользователя: {firstName} {lastName}, возраст {age}, пол {sex}, баланс {money}")
    public CreateUserPage createUser(String firstName, String lastName, int age, String sex, int money) {
        log.info("Создание пользователя: {} {}, возраст {}, пол {}, баланс {}",
                firstName, lastName, age, sex, money);

        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName + "_" + System.currentTimeMillis());
        ageInput.setValue(String.valueOf(age));

        if ("MALE".equalsIgnoreCase(sex)) {
            maleRadio.shouldBe(visible).click();
            log.info("Выбран пол: MALE");
        } else if ("FEMALE".equalsIgnoreCase(sex)) {
            femaleRadio.shouldBe(visible).click();
            log.info("Выбран пол: FEMALE");
        } else {
            throw new IllegalArgumentException("Неизвестный пол: " + sex + ". Используйте MALE или FEMALE");
        }

        moneyInput.setValue(String.valueOf(money));
        pushButton.shouldBe(visible).click();
        log.info("Кнопка PUSH TO API нажата");

        return this;
    }

    @Step("Проверить статус создания пользователя: {expectedText}")
    public CreateUserPage checkStatus(String expectedText) {
        statusButton.shouldHave(com.codeborne.selenide.Condition.text(expectedText));
        log.info("Статус создания проверен: {}", statusButton.getText());
        return this;
    }

    @Step("Получить ID созданного пользователя")
    public int getCreatedUserId() {
        userIdButton.shouldBe(visible);
        String text = userIdButton.getText();
        int userId = Integer.parseInt(text.replaceAll("[^\\d]", ""));
        log.info("Получен ID созданного пользователя: {}", userId);
        return userId;
    }
}