package dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dao.dbModels.DynamoDBUserProfile;
import lombok.NonNull;

public class UserProfileDao {
    private static final AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
    private final DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);

    public DynamoDBUserProfile getUserProfile(@NonNull final String userId) {
        return mapper.load(DynamoDBUserProfile.class, userId);
    }

    public void save(DynamoDBUserProfile dynamoDBUserProfile) {
        mapper.save(dynamoDBUserProfile);
    }

    public void delete(DynamoDBUserProfile dynamoDBUserProfile) {
        mapper.delete(dynamoDBUserProfile);
    }

}
