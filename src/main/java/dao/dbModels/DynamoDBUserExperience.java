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
@DynamoDBTable(tableName = "FOBUserExperienceTable")
public class DynamoDBUserExperience {

    @DynamoDBHashKey(attributeName = "user_id")
    private String user_id;

    @DynamoDBRangeKey(attributeName = "experience_id")
    private int experience_id;

    @DynamoDBAttribute(attributeName = "company_name")
    private String company_name;

    @DynamoDBAttribute(attributeName = "role")
    private String role;

    @DynamoDBAttribute(attributeName = "start_date")
    private String start_date;

    @DynamoDBAttribute(attributeName = "end_date")
    private String end_date;

    @DynamoDBAttribute(attributeName = "description")
    private String description;
}
