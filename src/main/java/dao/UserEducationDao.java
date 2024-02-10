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
    public List<DynamoDBUserEducation> getAllEducationEntitiesForUser(@NonNull final String userId) {
        DynamoDBUserEducation education = new DynamoDBUserEducation();
        education.setUser_id(userId);

        DynamoDBQueryExpression<DynamoDBUserEducation> queryExpression = new DynamoDBQueryExpression<DynamoDBUserEducation>()
                .withHashKeyValues(education);

        final List<DynamoDBUserEducation> userEducationList = mapper.query(DynamoDBUserEducation.class, queryExpression);
        return userEducationList;
    }

    /*
    Gets the next sequence number for the education ID under the specified userId
     */
    public int getNextSequence(@NonNull final String userId) {

        return getAllEducationEntitiesForUser(userId).size() + 1;
    }

    public Optional<DynamoDBUserEducation> getEducationEntityForUser(@NonNull final String userId,
                                                                     @NonNull final int educationId) {
        return Optional.ofNullable(mapper.load(DynamoDBUserEducation.class, userId, educationId));
    }

    public void save(DynamoDBUserEducation dynamoDBUserEducation) {
        mapper.save(dynamoDBUserEducation);
    }

    public void deleteEducationEntityForUser(@NonNull final DynamoDBUserEducation dynamoDBUserEducation) {
        mapper.delete(dynamoDBUserEducation);
    }

}