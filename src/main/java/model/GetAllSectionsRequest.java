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
public class GetAllSectionsRequest {
    @NonNull
    String section_id;

    public static GetAllSectionsRequest fromMap() {
        return GetAllSectionsRequest.builder()
                .build();
    }

}
