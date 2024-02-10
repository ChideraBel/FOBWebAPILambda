package dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dao.dbModels.DynamoDBUserProfile;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class UserProfileDao {
    private DynamoDBMapper mapper;

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
