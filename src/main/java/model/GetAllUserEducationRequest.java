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
public class GetAllUserEducationRequest {
    @NonNull
    String email;

    public static GetAllUserEducationRequest fromMap(Map<String, String> map) {
        return GetAllUserEducationRequest.builder()
                .email(map.get("email"))
                .build();
    }
}