package tests.api;

import api.adapters.LoginAdapter;
import api.models.login.LoginResponse;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

public class LoginAPITest extends BaseAPITest {

    @Test
    public void LoginAPIWithValidCredentials() {
        LoginResponse loginResponse = LoginAdapter.loginApi();
        assertNotNull(loginResponse.getAccessToken(), "Токен авторизации пустой!");
    }

    @Test(description = "Проверка получения токена из базового класса")
    public void loginAPIGetTokenTest() {
        // Используем токен, который автоматически прилетел из @BeforeClass
        assertNotNull(token, "Токен в BaseAPITest пустой!");
        System.out.println("Наш полученный токен: " + token);
    }
}