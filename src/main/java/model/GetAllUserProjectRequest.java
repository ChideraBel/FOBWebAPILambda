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
public class GetAllUserProjectRequest {
    @NonNull
    String email;

    public static GetAllUserProjectRequest fromMap(Map<String, String> map) {
        return GetAllUserProjectRequest.builder()
                .email(map.get("email"))
                .build();
    }
}