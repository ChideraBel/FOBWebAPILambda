package dao.dbModels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
@DynamoDBTable(tableName = "FOBUserTable")
public class DynamoDBUserProfile {

    @DynamoDBHashKey(attributeName = "user_id")
    public String user_id;

    @DynamoDBAttribute(attributeName = "address")
    public String address;

    @DynamoDBAttribute(attributeName = "date_of_birth")
    public String date_of_birth;

    @DynamoDBAttribute(attributeName = "employment")
    public String employment;

    @DynamoDBAttribute(attributeName = "industry")
    public String industry;

    @DynamoDBAttribute(attributeName = "nationality")
    public String nationality;

    @DynamoDBAttribute(attributeName = "profile_picture")
    public String profile_picture;

    @DynamoDBAttribute(attributeName = "visa_end_date")
    public String visa_end_date;
}
