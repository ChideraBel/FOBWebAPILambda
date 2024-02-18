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
public class ChatBotRequest {
    @NonNull
    private String email;
    @NonNull
    private String userPrompt;

    public static ChatBotRequest fromMap(Map<String, String> map) {
        return ChatBotRequest.builder()
                .email(map.get("email"))
                .userPrompt(map.get("userPrompt"))
                .build();
    }
}