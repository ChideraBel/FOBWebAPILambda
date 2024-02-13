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
public class GetAllUserProfileDetailsRequest {
    @NonNull
    String email;

    public static GetAllUserProfileDetailsRequest fromMap(Map<String, String> map){
        return GetAllUserProfileDetailsRequest.builder()
                .email(map.get("email"))
                .build();
    }

    
}
