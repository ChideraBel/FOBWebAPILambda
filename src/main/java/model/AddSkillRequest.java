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
public class AddSkillRequest {
    @NonNull
    String email;
    String skillName;

    public static AddSkillRequest fromMap(Map<String, String> map) {
        return AddSkillRequest.builder()
                .email(map.get("email"))
                .skillName(map.get("skillName"))
                .build();
    }
}