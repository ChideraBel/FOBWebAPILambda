package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserLoginRequest {
    @NonNull
    String email;
    @NonNull
    String password;
}