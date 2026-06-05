package tests;

import org.junit.jupiter.api.Test;
import pages.LoginPage;

public class AuthorizationTest extends BaseTest{

    LoginPage loginPage = new LoginPage();

    @Test
    public void checkAuthorizationWithValidCredentials(){
        loginPage.openPage();
        loginPage.login("user@pflb.ru","user");
    }
}
