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
public class UpdateEducationRequest {
    @NonNull
    String email;
    @NonNull
    int educationId;
    String institution;
    String degreeType;
    String major;
    String startDate;
    String endDate;
    Double gpa;

    public static UpdateEducationRequest fromMap(Map<String, String> map) {
        return UpdateEducationRequest.builder()
                .email(map.get("email"))
                .educationId(Integer.parseInt(map.get("educationId")))
                .institution(map.get("institution"))
                .degreeType(map.get("degreeType"))
                .major(map.get("major"))
                .startDate(map.get("startDate"))
                .endDate(map.get("endDate"))
                .gpa(Double.valueOf(map.get("gpa")))
                .build();
    }
}