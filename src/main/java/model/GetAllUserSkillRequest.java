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
public class GetAllUserSkillRequest {
    @NonNull
    String email;

    public static GetAllUserSkillRequest fromMap(Map<String, String> map) {
        return GetAllUserSkillRequest.builder()
                .email(map.get("email"))
                .build();
    }
}
