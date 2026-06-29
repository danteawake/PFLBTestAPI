package dto;

import lombok.Data;

@Data
public class CarNotFull {
    String Engine_Type;
    String Model;
    String Mark;

    public CarNotFull(String engine_Type, String model, String mark) {
        Engine_Type = engine_Type;
        Model = model;
        Mark = mark;
    }
}
