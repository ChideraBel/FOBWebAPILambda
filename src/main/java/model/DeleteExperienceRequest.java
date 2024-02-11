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
public class DeleteExperienceRequest {
    @NonNull
    String email;
    @NonNull
    int experienceId;

    public static DeleteExperienceRequest fromMap(Map<String, String> map) {
        return DeleteExperienceRequest.builder()
                .experienceId(Integer.parseInt(map.get("experienceId")))
                .email(map.get("email"))
                .build();
    }
}