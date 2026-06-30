package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import pages.ReadHousePage;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Log4j2
@Feature("Houses UI")
@Story("Read House")
@Owner("Лямкина Татьяна")
public class HouseTest extends BaseTest{

    @Test(priority = 1)
    @Description("Поиск пустых элементов")
    public void checkFieldsIsNotEmpty() {
        loginPage.openPage().login(testUsername, testPassword);
        readHousePage.openPageAll();
        assertFalse(ReadHousePage.hasEmptyFields(), "Имеются пустые поля на странице");
    }

    @Test(priority = 2)
    @Description("Обновление страницы через УЭ Reload")
    public void checkReloadButton() {
        loginPage.openPage().login(testUsername, testPassword);
        readHousePage.openPageAll();
        assertFalse(readHousePage.buttonIsWorked(), "Управляющий элемент Reload не работает");
    }

    @Test(priority = 3)
    @Description("Сравнение данных о жильцах с ReadAllHousesPage и ReadOneByIDPage")
    public void checkingTheDataWithTheReadAllPage() {
        loginPage
                .openPage()
                .login(testUsername, testPassword);
        readHousePage
                .openPageOne()
                .clickRead("1");
        List<String> lodgersReadOneByIDPageSaved = ReadHousePage.lodgersReadOneByIDPage();
        readHousePage.openPageAll();
        assertEquals(lodgersReadOneByIDPageSaved,
                ReadHousePage.lodgersReadAllHousePage(),
                "Данные о жильцах не совпадают");
    }
}
