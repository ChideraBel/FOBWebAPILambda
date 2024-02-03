package model;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class DeleteUserRequest {
    @NonNull
    String email;

    public static DeleteUserRequest fromMap(Map<String, String> map){
        return DeleteUserRequest.builder()
                .email(map.get("email"))
                .build();
    }
}
