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
public class GetSectionRequest {
    @NonNull
    int sectionId;

    public static GetSectionRequest fromMap(Map<String, String> map) {
        return GetSectionRequest.builder()
                .sectionId(Integer.parseInt(map.get("sectionId")))
                .build();
    }
}
