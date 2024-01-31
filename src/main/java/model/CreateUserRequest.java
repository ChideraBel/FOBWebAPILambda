package model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CreateUserRequest {
    @NonNull
    String email;
    @NonNull
    String password;
    String fullName;
}
