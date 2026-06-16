package tests.ui;

import dto.User;
import dto.UserCreateData;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

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
}