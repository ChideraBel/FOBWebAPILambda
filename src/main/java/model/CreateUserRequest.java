package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@Getter
@Setter
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
}
