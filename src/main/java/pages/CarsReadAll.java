package pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class CarsReadAll extends BasePage {
    protected final static String SORT_BUTTON_ID = "table tbody tr td:nth-child(1)";
    protected final static String SORT_BUTTON_STRING = "table tbody tr td:nth-child(%s)";
    protected final static String SORT_BUTTON_PRICE = "table tbody tr td:nth-child(5)";

    @Step("Открытие страницы Cars -> Read all")
    public CarsReadAll openPage() {
        log.info("Открытие страницы Cars -> Read all");
        open("#/read/cars");
        sleep(2000);
        return this;
    }

    @Step("Проверяем, что открыта страница cars")
    public CarsReadAll checkOpenedPage() {
        $(byText("Engine Type"));
        return this;
    }

    @Step("Проверяем элементы на странице")
    public CarsReadAll checkElementsOnPage() {
        $(byText("Reload")).shouldBe(visible, enabled);
        $(byText("Sort by:")).shouldNotBe(enabled);
        return this;
    }

    @Step("Проверяем сортировку по ID")
    public CarsReadAll checkSortingById() {
        //Проверяем сортировку ASC
        log.info("Начинаем проверку сортировки ASC по ID");
        ElementsCollection initCollectionString = $$(SORT_BUTTON_ID); //Получили текстовую коллекцию
        List<Integer> initCollectionInt = initCollectionString.texts().stream()//Перевели стринговую  в числовую коллекцию
                .map(Integer::parseInt)
                .toList();
        List<Integer> initCollectionIntSorted = new ArrayList<>(initCollectionInt);
        Collections.sort(initCollectionIntSorted);
        $("[aria-label='sort']").$(byText("ID")).click();

        ElementsCollection gotCollectionString = $$(SORT_BUTTON_ID); //Получили текстовую коллекцию
        List<Integer> gotCollectionInt = gotCollectionString.texts().stream()//Перевели стринговую  в числовую коллекцию
                .map(Integer::parseInt)
                .toList();
        Assert.assertEquals(gotCollectionInt, initCollectionIntSorted);
        log.info("Проверка на идентичность массивов ASC пройдена успешно");
//Проверяем сортировку DESC
        log.info("Начинаем проверку сортировки DESC");
        Collections.reverse(initCollectionIntSorted);
        $("[aria-label='sort']").$(byText("ID")).click();
        sleep(3000);
        ElementsCollection gotCollectionStringRev = $$(SORT_BUTTON_ID); //Получили текстовую коллекцию
        List<Integer> gotCollectionIntRev = gotCollectionStringRev.texts().stream()//Перевели стринговую  в числовую коллекцию
                .map(Integer::parseInt)
                .toList();
        Assert.assertEquals(gotCollectionIntRev, initCollectionIntSorted);
        log.info("Проверка на идентичность массивов DESC пройдена успешно");
        return this;
    }

    @Step("Проверяем сортировку по Типу двигателя/Марке/Модели")
    public CarsReadAll checkSortingEngineType2Mark32Model4(int columNum, String columName) {
        log.info("Начинаем проверку сортировки ASC по {}", columName);
        ElementsCollection initCollection = $$(String.format(SORT_BUTTON_STRING, columNum)); //Получили текстовую коллекцию
        List<String> initCollectionString = initCollection.texts();//Получили коллекцию стринговых значений

        List<String> initCollectionStringSorted = new ArrayList<>(initCollectionString);
        Collections.sort(initCollectionStringSorted);
        $("[aria-label='sort']").$(byText(columName)).click();

        ElementsCollection gotCollection = $$(String.format(SORT_BUTTON_STRING, columNum)); //Получили текстовую коллекцию
        List<String> gotCollectionString = gotCollection.texts();//Получили коллекцию стринговых значений

        Assert.assertEquals(gotCollectionString, initCollectionStringSorted);
        log.info("Проверка на идентичность массивов ASC пройдена успешно");
//Проверяем сортировку DESC
        Collections.reverse(initCollectionStringSorted);

        $("[aria-label='sort']").$(byText(columName)).click();
        ElementsCollection gotCollectionRev = $$(String.format(SORT_BUTTON_STRING, columNum)); //Получили текстовую коллекцию
        List<String> gotCollectionStringRev = gotCollectionRev.texts();//Получили коллекцию стринговых значений
        Assert.assertEquals(gotCollectionStringRev, initCollectionStringSorted);
        log.info("Проверка на идентичность массивов DESC пройдена успешно");
        return this;
    }

    @Step("Проверяем сортировку по Цене")
    public CarsReadAll checkSortingByPrice() {
        log.info("Начинаем проверку сортировки ASC по Price");
        ElementsCollection initCollectionString = $$(SORT_BUTTON_PRICE); //Получили текстовую коллекцию
        System.out.println(initCollectionString.texts());
        List<BigDecimal> initCollectionInt = initCollectionString.texts().stream()//Перевели стринговую  в числовую коллекцию
                .map(s -> s.replaceAll("[^\\d.-]", "")) // Чистим всё лишнее
                .filter(s -> !s.isBlank())
                .map(BigDecimal::new)
                .toList();
        List<BigDecimal> initCollectionIntSorted = new ArrayList<>(initCollectionInt);
        Collections.sort(initCollectionIntSorted);
        $("[aria-label='sort']").$(byText("Price")).click();
        ElementsCollection gotCollectionString = $$(SORT_BUTTON_PRICE); //Получили текстовую коллекцию
        List<BigDecimal> gotCollectionInt = gotCollectionString.texts().stream()//Перевели стринговую  в числовую коллекцию
                .map(s -> s.replaceAll("[^\\d.-]", "")) // Чистим всё лишнее
                .filter(s -> !s.isBlank())
                .map(BigDecimal::new)
                .toList();
        Assert.assertEquals(gotCollectionInt, initCollectionIntSorted);
        log.info("Проверка на идентичность массивов ASC пройдена успешно");
        //Проверяем сортировку DESC
        Collections.reverse(initCollectionIntSorted);
        $("[aria-label='sort']").$(byText("Price")).click();
        ElementsCollection gotCollectionStringRev = $$(SORT_BUTTON_PRICE); //Получили текстовую коллекцию
        List<BigDecimal> gotCollectionIntRev = gotCollectionStringRev.texts().stream()//Перевели стринговую  в числовую коллекцию
                .map(s -> s.replaceAll("[^\\d.-]", "")) // Чистим всё лишнее
                .filter(s -> !s.isBlank())
                .map(BigDecimal::new)
                .toList();
        Assert.assertEquals(gotCollectionIntRev, initCollectionIntSorted);
        log.info("Проверка на идентичность массивов DESC пройдена успешно");
        return this;
    }
}
