package dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dao.dbModels.DynamoDBUser;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class UserDao {
    private DynamoDBMapper mapper;

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
