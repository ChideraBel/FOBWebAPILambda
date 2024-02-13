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
public class UpdateProjectRequest {
    @NonNull
    String email;
    @NonNull
    int projectId;
    String projectName;
    String startDate;
    String endDate;
    String projectDescription;
    String role;
    String techUsed;

    public static UpdateProjectRequest fromMap(Map<String, String> map) {
        return UpdateProjectRequest.builder()
                .email(map.get("email"))
                .projectId(Integer.parseInt("projectId"))
                .projectName(map.get("projectName"))
                .startDate(map.get("startDate"))
                .endDate(map.get("endDate"))
                .projectDescription(map.get("projectDescription"))
                .role(map.get("role"))
                .techUsed(map.get("techUsed"))
                .build();
    }
}