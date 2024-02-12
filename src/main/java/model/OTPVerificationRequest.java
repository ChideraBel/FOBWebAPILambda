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
public class OTPVerificationRequest {
    @NonNull
    String email;
    @NonNull
    String enteredOTP;

    public static OTPVerificationRequest fromMap(Map<String, String> map) {
        return OTPVerificationRequest.builder()
                .email(map.get("email"))
                .enteredOTP(map.get("enteredOPT"))
                .build();
    }
}