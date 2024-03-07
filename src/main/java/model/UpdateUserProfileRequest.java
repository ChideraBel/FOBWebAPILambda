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

public class UpdateUserProfileRequest {
    @NonNull
    String emailString;
    String address;
    String dob;
    String employmentStatus;
    String industry;
    String nationality;
    String profilePic;
    String visaExpiration;

    public static UpdateUserProfileRequest fromMap(Map<String, String> map){
        return UpdateUserProfileRequest.builder()
                .emailString(map.get("emailString"))
                .address(map.get("address"))
                .dob(map.get("dob"))
                .employmentStatus(map.get("employmentStatus"))
                .industry(map.get("industry"))
                .nationality(map.get("nationality"))
                .profilePic(map.get("profilePic"))
                .visaExpiration(map.get("visaExpiration"))
                .build();
    }
}
