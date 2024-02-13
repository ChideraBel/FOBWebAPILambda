package dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import dao.dbModels.DynamoDBUserEducation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserEducationDao {
    private DynamoDBMapper mapper;

    /*
   Gets all the education entities for the specified userId
    */
    public List<DynamoDBUserEducation> getAllUserEducation(@NonNull final String userId) {
        DynamoDBUserEducation partitionKeyItem = new DynamoDBUserEducation();
        partitionKeyItem.setUser_id(userId);

        DynamoDBQueryExpression<DynamoDBUserEducation> queryExpression = new DynamoDBQueryExpression<DynamoDBUserEducation>()
                .withHashKeyValues(partitionKeyItem);

        return mapper.query(DynamoDBUserEducation.class, queryExpression);
    }

    /*
    Gets the next sequence number for the education ID under the specified userId
     */
    public int getNextSequence(@NonNull final String userId) {

        return getAllUserEducation(userId).size() + 1;
    }

    /*
    Gets a single education entity with the given educationId and userId
     */
    public Optional<DynamoDBUserEducation> getEducationEntityForUser(@NonNull final String userId,
                                                                     @NonNull final int educationId) {
        return Optional.ofNullable(mapper.load(DynamoDBUserEducation.class, userId, educationId));
    }

    public void save(@NonNull final DynamoDBUserEducation dynamoDBUserEducation) {
        mapper.save(dynamoDBUserEducation);
    }

    public void deleteEducationEntityForUser(@NonNull final DynamoDBUserEducation dynamoDBUserEducation) {
        mapper.delete(dynamoDBUserEducation);
    }
}