package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class UserDetails {
    @NonNull
    String emailString;
    String address;
    String dob;
    String employmentStatus;
    String industry;
    String nationality;
    String profilePic;
    String visaExpiration;
}