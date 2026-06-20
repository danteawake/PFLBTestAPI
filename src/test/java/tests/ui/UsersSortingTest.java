package tests.ui;

import dto.User;
import org.testng.annotations.Test;

public class UsersSortingTest extends BaseTest {

    @Test //для тестирования работы методов
    public void sortingTest() {
        loginPage.openPage().login(
                User.userStandard().getUsername(),
                User.userStandard().getPassword());
        usersReadAllPage.openPage()
                .sortFilterButtonClick("Age")
                .checkSortDirectionSign("Age", "↑")
                .checkTableElementText("Last name:", 1, "Lubowitz");
//                .checkTableRowsCount(9747);
        usersReadUserWithCar.openPage()
                .enterAndReadUserId(7338)
                .checkUserTableText("Last name:", 1, "Denesik")
                .checkCarTableText("Mark:", 1, "Tesla")
                .checkStatusMessage("Status: 200 ok");
    }
}
