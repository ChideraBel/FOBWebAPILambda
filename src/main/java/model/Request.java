package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;

@NoArgsConstructor
@Data
public class Request {
    @NonNull
    String requestType;

    @NonNull
    Map<String, String> requestBody;
}
