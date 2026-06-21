package dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Car {
    String Engine_Type;
    String Model;
    String Mark;
    Double Price;

    public Car(String engine_Type, String model, String mark, Double price) {
        Engine_Type = engine_Type;
        Model = model;
        Mark = mark;
        Price = price;
    }
}
