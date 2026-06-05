package tests;

import org.testng.annotations.Test;

public class AuthorizationTest extends BaseTest {

    @Test
    public void checkAuthorizationWithValidCredentials() {
        loginPage.openPage()
                .login("user@pflb.ru", "user");
    }
}