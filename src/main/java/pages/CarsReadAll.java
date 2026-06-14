package pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class CarsReadAll extends BasePage {

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
        ElementsCollection initCollectionString = $$("table tbody tr td:nth-child(1)"); //Получили текстовую коллекцию
        List<Integer> initCollectionInt = initCollectionString.texts().stream()//Перевели стринговую  в числовую коллекцию
                .map(Integer::parseInt)
                .toList();
        List<Integer> initCollectionIntSorted = new ArrayList<>(initCollectionInt);
        Collections.sort(initCollectionIntSorted);
        System.out.println("Изначальный " + initCollectionInt);
        System.out.println("Отсортированный " + initCollectionIntSorted);
        $("[aria-label='sort']").$(byText("ID")).click();

        ElementsCollection gotCollectionString = $$("table tbody tr td:nth-child(1)"); //Получили текстовую коллекцию
        List<Integer> gotCollectionInt= initCollectionString.texts().stream()//Перевели стринговую  в числовую коллекцию
                .map(Integer::parseInt)
                .toList();
        Assert.assertEquals(gotCollectionInt,initCollectionIntSorted);
        log.info("Проверка на идентичность массивов пройдена успешно");
        return this;
    }

    @Step("Проверяем сортировку по Типу двигателя/Марке/Модели")
    public CarsReadAll checkSortingEngineType2Mark32Model4(int columNum,String columName) {
        ElementsCollection initCollection = $$(String.format("table tbody tr td:nth-child(%s)",columNum)); //Получили текстовую коллекцию
        List<String> initCollectionString = initCollection.texts();//Получили коллекцию стринговых значений

        List<String> initCollectionStringSorted = new ArrayList<>(initCollectionString);
        Collections.sort(initCollectionStringSorted);
        System.out.println("Изначальный " + initCollectionString);
        System.out.println("Отсортированный " + initCollectionStringSorted);
        $("[aria-label='sort']").$(byText(columName)).click();

        ElementsCollection gotCollection = $$(String.format("table tbody tr td:nth-child(%s)",columNum)); //Получили текстовую коллекцию
        List<String> gotCollectionString= gotCollection.texts();//Получили коллекцию стринговых значений

        Assert.assertEquals(gotCollectionString,initCollectionStringSorted);
        log.info("Проверка на идентичность массивов пройдена успешно");
        return this;
    }

    @Step("Проверяем сортировку по Цене")
    public CarsReadAll checkSortingByPrice() {
        ElementsCollection initCollectionString = $$("table tbody tr td:nth-child(5)"); //Получили текстовую коллекцию
        System.out.println(initCollectionString.texts());
        List<BigDecimal> initCollectionInt = initCollectionString.texts().stream()//Перевели стринговую  в числовую коллекцию
                .map(s -> s.replaceAll("[^\\d.-]", "")) // чистим всё лишнее
                .filter(s -> !s.isBlank())
                .map(BigDecimal::new)
                .toList();
        List<BigDecimal> initCollectionIntSorted = new ArrayList<>(initCollectionInt);
        Collections.sort(initCollectionIntSorted);
        System.out.println("Изначальный " + initCollectionInt);
        System.out.println("Отсортированный " + initCollectionIntSorted);
        $("[aria-label='sort']").$(byText("Price")).click();

        ElementsCollection gotCollectionString = $$("table tbody tr td:nth-child(5)"); //Получили текстовую коллекцию
        List<BigDecimal> gotCollectionInt= initCollectionString.texts().stream()//Перевели стринговую  в числовую коллекцию
                .map(s -> s.replaceAll("[^\\d.-]", "")) // чистим всё лишнее
                .filter(s -> !s.isBlank())
                .map(BigDecimal::new)
                .toList();
        Assert.assertEquals(gotCollectionInt,initCollectionIntSorted);
        log.info("Проверка на идентичность массивов пройдена успешно");
        return this;
    }
}
