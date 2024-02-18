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
public class GenerateResumeRequest {
    @NonNull
    String email;

    public static GenerateResumeRequest fromMap(Map<String, String> map){
        return GenerateResumeRequest.builder()
                .email(map.get("email"))
                .build();
    }
}