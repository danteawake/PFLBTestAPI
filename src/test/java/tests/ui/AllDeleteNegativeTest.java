package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class AllDeleteNegativeTest extends BaseTest {

    @DataProvider(name = "DeleteTest")
    public Object[][] data() {
        return new Object[][]{
                {999999999, "user", "Status: not pushed"},
                {0, "user", "Status: Bad request"},
                {-232, "user", "Status: Bad request"},
                {999999999, "house", "Status: not pushed"},
                {0, "house", "Status: Bad request"},
                {-232, "house", "Status: Bad request"},
                {999999999, "car", "Status: not pushed"},
                {0, "car", "Status: Bad request"},
                {-232, "car", "Status: Bad request"},
        };
    }

    @Test(dataProvider = "DeleteTest",
            description = "Проверка ошибок при удалении",
            testName = "Проверка ошибок при удалении")
    @Owner("Konstantin")
    @Description("Проверка ошибок  при удалении")
    public void notValidDeleteData(int id, String button, String status) {
        loginPage.openPage().login(testUsername, testPassword);
        allDeletePage.openPage()
                .isPageOpened()
                .input(button, id)
                .clickDelete(button);
        assertEquals(status, allDeletePage.getStatus(button));
    }
}
