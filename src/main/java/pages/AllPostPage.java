package pages;

import com.codeborne.selenide.SelenideElement;
import dto.AddMoneyData;
import dto.UserCreateData;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import wrappers.Input;
import wrappers.RadioButton;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Log4j2

public class AllPostPage extends BasePage {
    private final SelenideElement workspace = $("section.workspace");
    private final Input firstNameInput =
            new Input($("#first_name_send"), "First Name");
    private final Input lastNameInput =
            new Input($("#last_name_send"), "Last Name");
    private final Input ageInput =
            new Input($("#age_send"), "Age");
    private final RadioButton sexRadioButton =
            new RadioButton($$("input[name='sex_send']"), "Sex");
    private final Input moneyInput =
            new Input($("table.table tbody tr td:nth-child(6) input"), "Money");
    private final SelenideElement pushToApiButton = $x("//input[@id='first_name_send']" +
            "/ancestor::table/../div//button[contains(@class,'tableButton')]");
    private final SelenideElement statusMessage = $x("//input[@id='first_name_send']" +
            "/ancestor::table/following-sibling::div//button[contains(@class,'status')]");
    private final SelenideElement newUserIdMessage = $x("//input[@id='first_name_send']" +
            "/ancestor::table/following-sibling::div//button[contains(@class,'newId')]");
    private final Input addMoneyUserIdInput =
            new Input($x("(//input[@id='id_send'])[1]"),"User ID");
    private final Input addMoneyInput = new Input($x("(//input[@id='money_send'])[2]"),
                    "Money");
    private final SelenideElement addMoneyPushButton =
            $x("(//button[contains(@class,'tableButton')])[2]");
    private final SelenideElement addMoneyStatusMessage =
            $x("(//button[contains(@class,'status')])[2]");
    private final SelenideElement newBalanceMessage =
            $x("(//button[contains(@class,'money')])[1]");

    @Step("Открытие страницы All Post")
    public AllPostPage openPage() {
        log.info("Открытие страницы All Post");
        open("#/create/all");
        return this;
    }

    @Step("Проверяем открытие страницы All Post")
    public AllPostPage checkPageOpened() {
        workspace.shouldBe(visible);

        log.info("Страница All Post успешно открыта");
        return this;
    }

    @Step("Создаём пользователя")
    public AllPostPage createUser(UserCreateData userCreateData) {

        log.info("Создание пользователя: {}", userCreateData);
        firstNameInput.setValue(userCreateData.getFirstName());
        lastNameInput.setValue(userCreateData.getLastName());
        ageInput.setValue(String.valueOf(userCreateData.getAge()));
        sexRadioButton.select(userCreateData.getSex());
        moneyInput.setValue(String.valueOf(userCreateData.getMoney()));
        pushToApiButton.click();
        log.info("Кнопка PUSH TO API нажата");

        return this;
    }

    @Step("Получаем статус создания пользователя")
    public String getStatusMessage() {
        statusMessage.shouldHave(text("Successfully pushed"));
        String status = statusMessage.getText();
        log.info("Получен статус создания пользователя: {}", status);
        return status;
    }

    @Step("Получаем ID созданного пользователя")
    public int getCreatedUserId() {
        newUserIdMessage.shouldNotBe(empty);
        String text = newUserIdMessage.getText();
        int userId = Integer.parseInt(text.replace("New user ID:", "").trim());
        log.info("Получен ID созданного пользователя: {}", userId);
        return userId;
    }
    @Step("Добавляем деньги пользователю")
    public AllPostPage addMoney(AddMoneyData addMoneyData) {
        log.info("Добавляем деньги пользователю: {}", addMoneyData);
        addMoneyUserIdInput.setValue(
                String.valueOf(addMoneyData.getUserId()));
        addMoneyInput.setValue(
                String.valueOf(addMoneyData.getMoney()));
        addMoneyPushButton.click();
        log.info("Кнопка PUSH TO API нажата");
        return this;
    }
    @Step("Получаем статус добавления денег")
    public String getAddMoneyStatusMessage() {
        addMoneyStatusMessage.shouldNotHave(text("not pushed"));
        String status = addMoneyStatusMessage.getText();
        log.info("Получен статус: {}", status);
        return status;
    }
    @Step("Получаем текущий баланс пользователя")
    public double getCurrentMoney() {
        String text = newBalanceMessage.shouldBe(visible).getText();
        log.info("Получен баланс пользователя: {}", text);
        return Double.parseDouble(text.trim());
    }
    @Step("Получаем новый баланс пользователя")
    public String getNewBalanceMessage() {
        String balance = newBalanceMessage.getText();
        log.info("Получен новый баланс: {}", balance);
        return balance;
    }
}