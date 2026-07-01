package tests.ui;

import org.testng.annotations.Test;

public class AuthorizationTest extends BaseTest {

    @Test
    public void checkAuthorizationWithValidCredentials() {
        loginPage.openPage()
                .login(testUsername,testPassword);
    }
}