package dao.dbModels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
@DynamoDBTable(tableName = "FOBUserProfileTable")
public class DynamoDBUserProfile {

    @DynamoDBHashKey(attributeName = "user_id")
    private String user_id;

    @DynamoDBAttribute(attributeName = "address")
    private String address;

    @DynamoDBAttribute(attributeName = "date_of_birth")
    private String date_of_birth;

    @DynamoDBAttribute(attributeName = "employment")
    private String employment;

    @DynamoDBAttribute(attributeName = "industry")
    private String industry;

    @DynamoDBAttribute(attributeName = "nationality")
    private String nationality;

    @DynamoDBAttribute(attributeName = "profile_picture")
    private String profile_picture;

    @DynamoDBAttribute(attributeName = "visa_end_date")
    private String visa_end_date;

    @DynamoDBAttribute(attributeName = "phone_number")
    private String phone_number;
}
