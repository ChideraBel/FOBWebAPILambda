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
public class UpdateExperienceRequest {
    @NonNull
    String email;
    @NonNull
    int experienceId;
    String company;
    String role;
    String startDate;
    String endDate;
    String jobDescription;

    public static UpdateExperienceRequest fromMap(Map<String, String> map) {
        return UpdateExperienceRequest.builder()
                .email(map.get("email"))
                .experienceId(Integer.parseInt(map.get("experienceId")))
                .company(map.get("company"))
                .role(map.get("role"))
                .startDate(map.get("startDate"))
                .endDate(map.get("endDate"))
                .jobDescription(map.get("jobDescription"))
                .build();
    }
}