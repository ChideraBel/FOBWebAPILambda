package model;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class CreateUserRequest {
    @NonNull
    String email;
    @NonNull
    String password;
    String fullName;
    String address;
    String dob;
    String employment;
    String industry;
    String nationality;
    String profilePic;
    String visaExpDate;

    public static CreateUserRequest fromMap(Map<String, String> map){
        return CreateUserRequest.builder()
                .email(map.get("email"))
                .password(map.get("password"))
                .fullName(map.get("fullName"))
                .address(map.get("address"))
                .dob(map.get("dob"))
                .employment(map.get("employment"))
                .industry(map.get("industry"))
                .nationality(map.get("nationality"))
                .profilePic(map.get("profilePic"))
                .visaExpDate(map.get("visaExpDate"))
                .build();
    }
}
