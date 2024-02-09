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
public class DeleteUserRequest {
    @NonNull
    String email;

    public static DeleteUserRequest fromMap(Map<String, String> map){
        return DeleteUserRequest.builder()
                .email(map.get("email"))
                .build();
    }
}
