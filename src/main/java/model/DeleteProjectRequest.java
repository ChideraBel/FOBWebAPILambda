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
public class DeleteProjectRequest {
    @NonNull
    String email;
    @NonNull
    int projectId;

    public static DeleteProjectRequest fromMap(Map<String, String> map){
        return DeleteProjectRequest.builder()
                .projectId(Integer.parseInt(map.get("projectId")))
                .email(map.get("email"))
                .build();
    }
}