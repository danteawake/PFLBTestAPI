package ui.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AddMoneyData {

    int userId;
    double money;
}