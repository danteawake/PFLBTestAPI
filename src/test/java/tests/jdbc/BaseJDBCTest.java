package tests.jdbc;

import api.adapters.LoginAdapter;
import org.testng.annotations.BeforeClass;

public class BaseJDBCTest {

    protected String apiToken;

    @BeforeClass(description = "Получение токена авторизации для API-шагов")
    public void setUpApiToken() {
        apiToken = LoginAdapter.loginApi().getAccessToken();
    }
}
