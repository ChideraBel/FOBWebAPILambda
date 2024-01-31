package dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import dao.dbModels.DynamoDBUser;

import java.util.Map;

import static utils.ConstantUtils.*;
import static utils.ConstantUtils.DATE_REGISTERED;

public class UserDao {
    private static final AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();

    public void save(DynamoDBUser dynamoDBUser) {
        PutItemRequest putItemRequest = new PutItemRequest()
                .withTableName(FOB_USER_TABLE)
                .withItem(Map.of(
                        USER_ID, new AttributeValue(dynamoDBUser.getUser_id()),
                        PASSWORD, new AttributeValue(dynamoDBUser.getPassword()),
                        FULL_NAME, new AttributeValue(dynamoDBUser.getFullname()),
                        LAST_LOGIN, new AttributeValue(dynamoDBUser.getLast_login()),
                        DATE_REGISTERED, new AttributeValue(dynamoDBUser.getDate_registered())
                ));

        dynamoDBClient.putItem(putItemRequest);
    }

}
