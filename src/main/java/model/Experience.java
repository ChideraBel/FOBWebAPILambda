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
public class Experience {
    @NonNull
    String company;
    String role;
    String startDate;
    String endDate;
    String jobDescription;
}