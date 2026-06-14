package tests.ui;

import dto.User;
import org.testng.annotations.Test;

@Test
public class CarTest extends BaseTest {
    public void checkOpenedPage() {
        loginPage.openPage().login(
                User.userStandard().getUsername(),
                User.userStandard().getPassword());
        carReaAll.openPage();
        carReaAll.checkOpenedPage();
        carReaAll.checkElementsOnPage();
       // carReaAll.checkSortingById();
 //carReaAll.checkSortingEngineType2Mark32Model4(2,"Engine Type");
        carReaAll.checkSortingByPrice();
    }
}
