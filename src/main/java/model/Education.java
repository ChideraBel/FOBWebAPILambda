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
    public String institution_name;
    public String degree_type;
    public String major;
    public String start_date;
    public String end_date;
    public double gpa;
}
