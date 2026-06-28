package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarCreateData {
    String engineType;
    String model;
    String mark;
    Double price;
}