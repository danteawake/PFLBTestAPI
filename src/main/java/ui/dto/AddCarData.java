package ui.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AddCarData {

    int userId;
    int carId;
    String action; // BUY/SELL
}