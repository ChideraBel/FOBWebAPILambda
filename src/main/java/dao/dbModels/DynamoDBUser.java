package dao.dbModels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "FOBUserTable")
public class DynamoDBUser {

    @DynamoDBHashKey(attributeName = "user_id")
    public String user_id;

    @DynamoDBAttribute(attributeName = "password")
    public String password;

    @DynamoDBAttribute(attributeName = "full_name")
    public String fullname;

    @DynamoDBAttribute(attributeName = "last_login")
    public String last_login;

    @DynamoDBAttribute(attributeName = "date_registered")
    public String date_registered;
}
