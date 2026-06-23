package tests.ui;

import dto.AddMoneyData;
import dto.User;
import dto.UserCreateData;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class AllPostTest extends BaseTest {

    @Test
    public void checkOpenAllPostPage() {

        loginPage.openPage()
                .login(
                        User.userStandard().getUsername(),
                        User.userStandard().getPassword());

        allPostPage.openPage()
                .checkPageOpened();
    }

    @Test(
            description = "Проверка создания пользователя через All Post",
            testName = "Создание пользователя")
    @Description("Проверка успешного создания пользователя через форму All Post")
    public void checkCreateUserWithFirstName() {

        UserCreateData userCreateData = UserCreateData.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .age(25)
                .sex("MALE")
                .money(1000)
                .build();

        loginPage.openPage()
                .login(
                        User.userStandard().getUsername(),
                        User.userStandard().getPassword());

        allPostPage.openPage()
                .checkPageOpened()
                .createUser(userCreateData);
        Assert.assertEquals(allPostPage.getStatusMessage(),
                "Status: Successfully pushed, code: 201"
        );
        Assert.assertTrue(allPostPage.getCreatedUserId() > 0,
                "ID пользователя не был сгенерирован");
    }

    @Test(
            description = "Проверка добавления денег пользователю",
            testName = "Добавление денег пользователю")
    @Description("Проверка успешного пополнения баланса пользователя")
    public void checkAddMoneyToUser() {

        double initialMoney = 500;
        double addedMoney = 500;

        log.info("Создаём пользователя с балансом {}", initialMoney);
        UserCreateData userCreateData = UserCreateData.builder()//сначала создаем пользователя
                .firstName("Max")
                .lastName("Ivanov")
                .age(20)
                .sex("MALE")
                .money(initialMoney)
                .build();

        loginPage.openPage()
                .login(User.userStandard().getUsername(),
                       User.userStandard().getPassword());

        allPostPage.openPage()
                .createUser(userCreateData);

        int userId = allPostPage.getCreatedUserId();
        log.info("Пользователь создан, ID = {}", userId);

        AddMoneyData addMoneyData = AddMoneyData.builder()
                .userId(userId)
                .money(addedMoney)
                .build();
        log.info("Добавляем деньги: {}", addedMoney);
        allPostPage.addMoney(addMoneyData);

        Assert.assertEquals(
                allPostPage.getAddMoneyStatusMessage(),
                "Status: Successfully pushed, code: 200"
        );
        double expectedBalance = initialMoney + addedMoney;
        double actualBalance = allPostPage.getCurrentMoney();

        log.info("Ожидаемый баланс: {}", expectedBalance);
        log.info("Фактический баланс: {}", actualBalance);

        Assert.assertEquals(
                actualBalance,
                expectedBalance,
                "Баланс пользователя рассчитан неверно");
    }
}