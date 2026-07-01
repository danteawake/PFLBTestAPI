package tests.api;

import api.adapters.LoginAdapter;
import org.testng.annotations.BeforeClass;

public class BaseAPITest {
    // Этот токен будет доступен всем наследникам (классам тестов)
    protected String token;

    @BeforeClass(description = "Авторизация перед запуском тестов в классе")
    public void setUpAuth() {
        // Запрос на логин происходит ОДИН раз перед запуском тестов в конкретном классе
        token = LoginAdapter.loginApi().getAccessToken();
    }
}