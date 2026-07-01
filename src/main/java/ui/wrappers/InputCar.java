package ui.wrappers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;

import static com.codeborne.selenide.Selenide.$x;
@Log4j2
public class InputCar {
    private final String columnName;
    private final int columnNum;


    public InputCar(String columnName, int columnNum) {
        this.columnName = columnName;
        this.columnNum = columnNum;
    }

    public void write(String text,int columnNum){
        log.info("Вводим значение {} в поле {}",text,columnName);

        SelenideElement input= $x(String.format("//th[contains(., '%s')]/ancestor::table//td[%s]//input",columnName,columnNum));
        input.shouldBe(Condition.visible,Condition.enabled,Condition.editable).setValue(text);

    }
    public void writePrice(Double price){
        log.info("Вводим значение {} в поле {}",price,columnName);
        $x("//th[contains(., 'Price')]/ancestor::table//td[5]//input").setValue(price.toString());
    }
}
