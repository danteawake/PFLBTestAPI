package ui.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    String username;
    String password;

    public static User userStandard() {
        return User.builder()
                .username("user@pflb.ru")
                .password("user")
                .build();
    }
}