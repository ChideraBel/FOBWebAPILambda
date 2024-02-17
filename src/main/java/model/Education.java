package model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class Education {
    @NonNull
    String institution;
    String location;
    String degreeType;
    String major;
    String startDate;
    String endDate;
    double gpa;
}
