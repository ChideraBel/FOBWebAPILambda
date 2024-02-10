package model;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class CreateSectionRequest {
    @NonNull
    String name;
    @NonNull
    String content;
    String description;

    public static CreateSectionRequest fromMap(Map<String, String> map) {
        return CreateSectionRequest.builder()
                .name(map.get("name"))
                .description(map.get("description"))
                .content(map.get("content"))
                .build();
    }
}
