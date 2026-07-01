package tests.ui;

import api.adapters.UserAdapter;
import db.UserDBConnection;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import api.models.user.UserRequest;
import api.models.user.UserResponse;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

@Slf4j
public class DeleteUserPositiveTest extends BaseTest {

    UserRequest userRequest = UserRequest.builder()
            .firstName("Test")
            .secondName("Test")
            .age(18)
            .sex("MALE")
            .money(100.0)
            .build();

//    private String apiToken; // Переменная для хранения токена API

//    @BeforeMethod(description = "Получение токена авторизации для API-предварительных шагов")
//    public void setUpApiToken() {
        // Получаем чистый токен из LoginAdapter один раз перед тестом
//        apiToken = LoginAdapter.loginApi().getAccessToken();
//    }

    @SneakyThrows
    @Test(description = "Проверка удаления пользователя",
            testName = "Проверка удаления пользователя")
    @Owner("Konstantin")
    @Description("Проверка удаления пользователя")
    public void checkDeleteUser() {

        UserResponse createdUser = UserAdapter.createUser(userRequest);
        int userId = createdUser.id;
        log.info("Пользователь создан. ID = {}", userId);
        //удаляем автомобиль через ui
        loginPage.openPage().login(testUsername, testPassword);
        allDeletePage.openPage()
                .isPageOpened()
                .input("user", userId)
                .clickDelete("user");
        Thread.sleep(3000);
        assertEquals("Status: 204", allDeletePage.getStatus("user"));
        log.info("Пользователь удален. ID = {}", userId);
        //проверяем через БД, что пользователя действительно нет
        UserDBConnection connection = new UserDBConnection();
        connection.connect();
        assertEquals(0, connection.deleted(userId));
        log.info("Пользователь с id {} отсутствует в БД", userId);
        connection.close();
    }
}