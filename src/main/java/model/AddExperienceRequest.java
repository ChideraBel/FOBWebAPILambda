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
public class AddExperienceRequest {
    @NonNull
    String email;
    String company;
    String role;
    String startDate;
    String endDate;
    String jobDescription;

    public static AddExperienceRequest fromMap(Map<String, String> map) {
        return AddExperienceRequest.builder()
                .email(map.get("email"))
                .company(map.get("company"))
                .role(map.get("role"))
                .startDate(map.get("startDate"))
                .endDate(map.get("endDate"))
                .jobDescription(map.get("jobDescription"))
                .build();
    }
}