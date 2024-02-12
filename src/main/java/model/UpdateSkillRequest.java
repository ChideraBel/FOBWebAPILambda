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
public class UpdateSkillRequest {
    @NonNull
    String email;
    @NonNull
    int skillId;
    String skillName;

    public static UpdateSkillRequest fromMap(Map<String, String> map) {
        return UpdateSkillRequest.builder()
                .email(map.get("email"))
                .skillId(Integer.parseInt(map.get("skillId")))
                .skillName(map.get("skillName"))
                .build();
    }
}