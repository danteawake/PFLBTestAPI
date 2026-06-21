package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreateData {
    String firstName;
    String lastName;
    int age;
    String sex;
    double money;
}
