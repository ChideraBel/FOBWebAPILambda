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
public class DeleteEducationRequest {
    @NonNull
    String email;
    @NonNull
    int educationId;

    public static DeleteEducationRequest fromMap(Map<String, String> map){
        return DeleteEducationRequest.builder()
                .educationId(Integer.parseInt(map.get("educationId")))
                .email(map.get("email"))
                .build();
    }
}