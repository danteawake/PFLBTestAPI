package ui.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HouseCreateData {

    int floors;
    double price;
    int warmCoveredParking;
    int warmNotCoveredParking;
    int coldCoveredParking;
    int coldNotCoveredParking;
}