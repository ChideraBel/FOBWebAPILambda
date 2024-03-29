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
public class AddEducationRequest {
    @NonNull
    String email;
    String institution;
    String location;
    String degreeType;
    String major;
    String startDate;
    String endDate;
    double gpa;

    public static AddEducationRequest fromMap(Map<String, String> map) {
        return AddEducationRequest.builder()
                .email(map.get("email"))
                .institution(map.get("institution"))
                .location(map.get("location"))
                .degreeType(map.get("degreeType"))
                .major(map.get("major"))
                .startDate(map.get("startDate"))
                .endDate(map.get("endDate"))
                .gpa(Double.valueOf(map.get("gpa")))
                .build();
    }
}