package tests;

import org.junit.jupiter.api.Test;

public class AuthorizationTest extends BaseTest {

    @Test
    public void checkAuthorizationWithValidCredentials() {
        loginPage.openPage()
                .login("user@pflb.ru", "user");
    }
}
