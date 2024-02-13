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
public class AddProjectRequest {
    @NonNull
    String email;
    String projectName;
    String startDate;
    String endDate;
    String projectDescription;
    String role;
    String techUsed; //could also be a list (contemplating)

    public static AddProjectRequest fromMap(Map<String, String> map) {
        return AddProjectRequest.builder()
                .email(map.get("email"))
                .projectName(map.get("projectName"))
                .startDate(map.get("startDate"))
                .endDate(map.get("endDate"))
                .projectDescription(map.get("projectDescription"))
                .role(map.get("role"))
                .techUsed(map.get("techUsed"))
                .build();
    }
}