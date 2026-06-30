package pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertFalse;

@Log4j2
public class ReadHousePage extends BasePage {

    private static final SelenideElement pageHouse = $x("//a[text()='Houses']");
    private static final SelenideElement pageReadAll = $x("//a[@href='#/read/houses']");
    private static final SelenideElement pageReadOneId = $x("//a[@href='#/read/house']");
    private static final SelenideElement buttonReload = $x("//button[text()='Reload']");
    private static final String lodgersIdRealAll = "//*[@id='root']/div/section/div/table/tbody/tr[1]/td[5]/table/tbody/tr/td";
    private static final String lodgersOneId = "//*[@id='root']/div/section/div/table[2]/tbody/tr/td";
    private static final SelenideElement sendId = $x("//input[@type ='number']");
    private static final SelenideElement buttonRead = $x("//button[@class='tableButton btn btn-primary']");

    @Step("Открытие страницы Read All House")
    public void openPageAll() {
        log.info("Открытие страницы Read All House");
        pageHouse.shouldBe(visible).click();
        pageReadAll.shouldBe(visible).click();
        buttonReload.shouldBe(visible);
    }

    @Step("Открытие страницы Read One By Id House")
    public ReadHousePage openPageOne() {
        log.info("Открытие страницы Read One By Id House");
        pageHouse.shouldBe(visible).click();
        pageReadOneId.shouldBe(visible).click();
        buttonRead.shouldBe(visible);
        return this;
    }

    @Step("Поиск пустых полей на странице ReadAllHousesPage")
    public static Boolean hasEmptyFields() {
        log.info("Ищем пустые поля на странице ReadAllHousesPage");

        List<String> texts = $$x("//td").texts();
        List<String> emptyFields = texts.stream()
                .filter(String::isEmpty)
                .collect(Collectors.toList());

        if (!emptyFields.isEmpty()) {
            log.info("Найдено {} пустых полей", emptyFields.size());
            log.info("Пустые поля: {}", emptyFields);
            return true;
        } else {
            log.info("Пустые поля не найдены");
            return false;
        }
    }

    @Step("Проверка кнопки Reload на странице ReadAllHousesPage")
    public Boolean buttonIsWorked() {
        log.info("Ожидаем загрузку таблицы перед первым сбором данных");
        $$x("//td").shouldHave(CollectionCondition.sizeGreaterThan(0));
        log.info("Проверяем все элементы до обновления");
        ElementsCollection allFieldFirstEqual = $$x("//td"); //находим все элементы до обновления
        List<String> stringFieldFirst = allFieldFirstEqual.texts(); //и помещаем в список stringFieldFirst
        log.info("Нажимаем на УЭ RELOAD");
        buttonReload.shouldBe(visible).click();
        log.info("Проверяем все элементы после обновления");
        ElementsCollection allFieldSecondEqual = $$x("//td");
        List<String> stringFieldSecond = allFieldSecondEqual.texts(); //снова собираем элементы и помещаем в список2
        boolean isEqual = stringFieldFirst.equals(stringFieldSecond);
        assertFalse(isEqual, "Сортировка не изменилась после нажатия кнопки Reload");
        return isEqual;
    }

    @Step("Поиск дома по ID: {ID} и нажатие кнопки Read")
    public void clickRead(String ID) {
        log.info("Поиск дома по ID: {}", ID);
        sendId.setValue(String.valueOf(ID));
        log.info("Клик на УЭ Read");
        buttonRead.click();
    }

    @Step("Получение информации по жильцам на странице {pageName}")
    private static List<String> getLodgersList(String xpath, String pageName) {
        log.info("Сбор информации по жильцам на странице {}", pageName);
        List<String> lodgersList = $$x(xpath)
                .shouldHave(CollectionCondition.sizeGreaterThan(0)) //ждем появления элемента в коллекции
                .texts()
                .stream()
                .sorted()
                .collect(Collectors.toList());
        log.info("Найдено всего {} элементов о жильцах", lodgersList.size());
        return lodgersList;
    }

    @Step("Получение информации по жильцам на странице ReadAllHousesPage")
    public static List<String> lodgersReadAllHousePage() {
        return getLodgersList(lodgersIdRealAll, "ReadAllHousesPage");
    }


    @Step("Получение информации по жильцам на странице ReadOneByIDPage")
    public static List<String> lodgersReadOneByIDPage() {
        return getLodgersList(lodgersOneId, "ReadOneByIDPage");
    }

}