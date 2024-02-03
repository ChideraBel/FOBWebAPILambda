package model;

import lombok.*;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class UserResponse {
    String responseType;
    String message;
}
