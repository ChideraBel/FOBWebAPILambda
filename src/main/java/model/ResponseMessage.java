package model;

import lombok.*;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class ResponseMessage {
    String responseType;
    String message;
}
