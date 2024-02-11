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
public class GetAllUserExperienceRequest {
    @NonNull
    String email;

    public static GetAllUserExperienceRequest fromMap(Map<String, String> map) {
        return GetAllUserExperienceRequest.builder()
                .email(map.get("email"))
                .build();
    }
}