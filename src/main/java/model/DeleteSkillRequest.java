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
public class DeleteSkillRequest {
    @NonNull
    String email;
    @NonNull
    int skillId;

    public static DeleteSkillRequest fromMap(Map<String, String> map) {
        return DeleteSkillRequest.builder()
                .skillId(Integer.parseInt(map.get("skillId")))
                .email(map.get("email"))
                .build();
    }
}