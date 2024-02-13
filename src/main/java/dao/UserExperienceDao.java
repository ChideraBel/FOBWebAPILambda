package dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import dao.dbModels.DynamoDBUserExperience;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserExperienceDao {
    private DynamoDBMapper mapper;

    /*
    Get the user experience for specified userId and experienceId
     */
    public Optional<DynamoDBUserExperience> getExperienceEntityForUser(@NonNull final String userId,
                                                                       @NonNull final int experienceId) {
        return Optional.ofNullable(mapper.load(DynamoDBUserExperience.class, userId, experienceId));
    }

    /*
    Gets all the user experience for the specified userId
     */
    public List<DynamoDBUserExperience> getAllUserExperience(@NonNull final String userId) {
        DynamoDBUserExperience partitionKeyItem = new DynamoDBUserExperience();
        partitionKeyItem.setUser_id(userId);

        DynamoDBQueryExpression<DynamoDBUserExperience> queryExpression = new DynamoDBQueryExpression<DynamoDBUserExperience>()
                .withHashKeyValues(partitionKeyItem);

        return mapper.query(DynamoDBUserExperience.class, queryExpression);
    }

    /*
    Gets the next sequence number for the education ID under the specified userId
     */
    public int getNextSequence(@NonNull final String userId) {

        return getAllUserExperience(userId).size() + 1;
    }

    public void save(@NonNull final DynamoDBUserExperience dynamoDBUserExperience) {
        mapper.save(dynamoDBUserExperience);
    }

    public void deleteEducationEntityForUser(@NonNull final DynamoDBUserExperience dynamoDBUserExperience) {
        mapper.delete(dynamoDBUserExperience);
    }

}