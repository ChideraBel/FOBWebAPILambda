package model;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class LoginUserRequest {
    @NonNull
    String email;
    @NonNull
    String password;

    public static LoginUserRequest fromMap(Map<String, String> map){
        return LoginUserRequest.builder()
                .email(map.get("email"))
                .password(map.get("password"))
                .build();
    }
}