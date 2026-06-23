package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddMoneyData {

    private Integer userId;
    private Double money;
}