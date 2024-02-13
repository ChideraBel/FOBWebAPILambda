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
public class SendOTPRequest {
    @NonNull
    String email;

    public static SendOTPRequest fromMap(Map<String, String> map){
        return SendOTPRequest.builder()
                .email(map.get("email"))
                .build();
    }
}