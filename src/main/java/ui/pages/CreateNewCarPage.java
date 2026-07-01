package ui.pages;

import ui.dto.Car;
import ui.dto.CarNotFull;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import ui.wrappers.InputCar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class CreateNewCarPage extends BasePage {

    protected final String STATUS_BUTTON = "//button[@class='status btn btn-secondary']";
    protected final String PUSH_TO_API_BUTTON = "//button[@class='tableButton btn btn-primary']";
    protected final String RESULT_BUTTON = "//button[@class='newId btn btn-secondary']";

    @Step("Открываем страницу создания автомобиля")
    public CreateNewCarPage openCreateNewCarPage() {
        open("http://82.142.167.37:4881/#/create/cars");
        $(byText("ID will be generated")).should(visible);
        $x(PUSH_TO_API_BUTTON).should(clickable);//Кнопка --PUSH TO API
        String status = $x(STATUS_BUTTON).getText(); //плашка Status not pushed
        Assert.assertEquals(status, "Status: not pushed");
        log.info("Открыта страница создания автомобиля");
        return this;
    }

    @Step("Создаем автомобиль")
    public int addNewCar(Car car) {
        sleep(1000);
        new InputCar("Engine", 2).write(car.getEngine_Type(), 2);
        sleep(1000);
        new InputCar("Mark", 3).write(car.getMark(), 3);
        sleep(1000);
        new InputCar("Model", 4).write(car.getModel(), 4);
        sleep(1000);
        new InputCar("Price", 5).writePrice(car.getPrice());
        sleep(1000);
        $x(PUSH_TO_API_BUTTON).click();
        sleep(3000);
        String status = $x(STATUS_BUTTON).getText(); //плашка Status not pushed
        Assert.assertEquals(status, "Status: Successfully pushed, code: 201");
        String resultStr = $x(RESULT_BUTTON).getText();
        //Достаем значение ID из полученной строки
        int id = 0;
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(resultStr);
        if (matcher.find()) {
            id = Integer.parseInt(matcher.group());
        }
        log.info("Создан автомобиль с id {}", id);
        return id;
    }

    @Step("Создаем автомобиль не со всеми полями")
    public void createCarWithNotAllFields(CarNotFull carNotFull) {
        sleep(1000);
        new InputCar("Engine", 2).write(carNotFull.getEngine_Type(), 2);
        sleep(1000);
        new InputCar("Mark", 3).write(carNotFull.getMark(), 3);
        sleep(1000);
        new InputCar("Model", 4).write(carNotFull.getModel(), 4);
        sleep(1000);
        $x(PUSH_TO_API_BUTTON).click();
        sleep(3000);
        String status = $x(STATUS_BUTTON).getText(); //плашка Status not pushed
        Assert.assertEquals(status, "Status: Invalid request data");
    }
}
