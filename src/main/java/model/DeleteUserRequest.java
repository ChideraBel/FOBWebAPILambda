package model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class DeleteUserRequest {
    @NonNull
    String email;
}
