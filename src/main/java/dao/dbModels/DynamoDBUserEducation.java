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
    public String user_id;

    @DynamoDBRangeKey(attributeName = "education_id")
    public int education_id;

    @DynamoDBAttribute(attributeName = "institution_name")
    public String institution_name;

    @DynamoDBAttribute(attributeName = "degree_type")
    public String degree_type;

    @DynamoDBAttribute(attributeName = "major")
    public String major;

    @DynamoDBAttribute(attributeName = "start_date")
    public String start_date;

    @DynamoDBAttribute(attributeName = "end_date")
    public String end_date;

    @DynamoDBAttribute(attributeName = "gpa")
    public double gpa;
}
