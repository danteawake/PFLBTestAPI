package ui.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AddHouseData {

    int userId;
    int houseId;
    String action;//SETTLE/EVICT
}