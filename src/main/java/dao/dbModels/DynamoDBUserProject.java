package dao.dbModels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "FOBUserProjectsTable")
public class DynamoDBUserProject {

    @DynamoDBHashKey(attributeName = "user_id")
    private String user_id;

    @DynamoDBRangeKey(attributeName = "project_id")
    private int project_id;

    @DynamoDBAttribute(attributeName = "project_title")
    private String project_title;

    @DynamoDBAttribute(attributeName = "start_date")
    private String start_date;

    @DynamoDBAttribute(attributeName = "end_date")
    private String end_date;

    @DynamoDBAttribute(attributeName = "description")
    private String description;

    @DynamoDBAttribute(attributeName = "role")
    private String role;

    @DynamoDBAttribute(attributeName = "technologies_used")
    private String technologies_used;
}