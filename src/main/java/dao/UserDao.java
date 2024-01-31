package dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dao.dbModels.DynamoDBUser;
import lombok.NonNull;

public class UserDao {
    private static final AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
    private final DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);

    public DynamoDBUser getUser(@NonNull final String userId) {
        return mapper.load(DynamoDBUser.class, userId);
    }

    public void save(DynamoDBUser dynamoDBUser) {
        mapper.save(dynamoDBUser);
    }

    public void delete(DynamoDBUser dynamoDBUser) {
        mapper.delete(dynamoDBUser);
    }

}
