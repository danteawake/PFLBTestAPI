package tests.api;

import adapters.LoginAdapter;
import models.positive.LoginResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

public class LoginAPITest {

    @Test
    public void LoginAPIWithValidCredentials() {
        LoginResponse loginResponse = LoginAdapter.loginApi();
        assertNotNull(loginResponse.getAccessToken(), "Токен авторизации пустой!");
    }

    @Test
    public void LoginAPIGetTokenTest() {
        String token = LoginAdapter.getAccessToken();
        Assert.assertNotNull(token, "Токен авторизации пустой!");
        System.out.println("Наш полученный токен: " + token);
    }
}