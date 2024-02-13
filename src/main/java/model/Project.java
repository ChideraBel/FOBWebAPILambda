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
public class Project {
    @NonNull
    String projectName;
    String startDate;
    String endDate;
    String projectDescription;
    String role;
    String techUsed;
}
