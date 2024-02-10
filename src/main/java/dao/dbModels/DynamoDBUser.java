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
    private String user_id;

    @DynamoDBAttribute(attributeName = "password")
    private String password;

    @DynamoDBAttribute(attributeName = "full_name")
    private String fullname;

    @DynamoDBAttribute(attributeName = "last_login")
    private String last_login;

    @DynamoDBAttribute(attributeName = "date_registered")
    private String date_registered;
}
