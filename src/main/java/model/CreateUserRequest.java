package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class CreateUserRequest {
    @NonNull
    String email;
    @NonNull
    String password;
    String fullName;
    String industry;

    public static CreateUserRequest fromMap(Map<String, String> map) {
        return CreateUserRequest.builder()
                .email(map.get("email"))
                .password(map.get("password"))
                .fullName(map.get("fullName"))
                .industry(map.get("industry"))
                .build();
    }
}