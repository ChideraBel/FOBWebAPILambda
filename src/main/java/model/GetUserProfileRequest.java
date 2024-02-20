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
public class GetUserProfileRequest {
    @NonNull
    String email;

    public static GetUserProfileRequest fromMap(Map<String, String> map){
        return GetUserProfileRequest.builder()
                .email(map.get("email"))
                .build();
    }

    
}
