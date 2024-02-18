package dao.dbModels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
@DynamoDBTable(tableName = "FOBUserEducationTable")
public class DynamoDBUserEducation {

    @DynamoDBHashKey(attributeName = "user_id")
    private String user_id;

    @DynamoDBRangeKey(attributeName = "education_id")
    private int education_id;

    @DynamoDBAttribute(attributeName = "institution_name")
    private String institution_name;

    @DynamoDBAttribute(attributeName = "degree_type")
    private String degree_type;

    @DynamoDBAttribute(attributeName = "major")
    private String major;

    @DynamoDBAttribute(attributeName = "start_date")
    private String start_date;

    @DynamoDBAttribute(attributeName = "end_date")
    private String end_date;

    @DynamoDBAttribute(attributeName = "gpa")
    private double gpa;

    @DynamoDBAttribute(attributeName = "institution_location")
    private String location;
}
