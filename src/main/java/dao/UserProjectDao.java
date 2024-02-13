package dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import dao.dbModels.DynamoDBUserProject;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserProjectDao {
    private DynamoDBMapper mapper;

    /*
    Gets all the project entities for the specified userId
    */
    public List<DynamoDBUserProject> getAllUserProjects(@NonNull final String userId) {
        DynamoDBUserProject partitionKeyItem = new DynamoDBUserProject();
        partitionKeyItem.setUser_id(userId);

        DynamoDBQueryExpression<DynamoDBUserProject> queryExpression = new DynamoDBQueryExpression<DynamoDBUserProject>()
                .withHashKeyValues(partitionKeyItem);

        return mapper.query(DynamoDBUserProject.class, queryExpression);
    }

    /*
   Gets the next sequence number for the project ID under the specified userId
    */
    public int getNextSequence(@NonNull final String userId) {

        return getAllUserProjects(userId).size() + 1;
    }

    /*
    Gets a single project entity with the given educationId and userId
    */
    public Optional<DynamoDBUserProject> getProjectEntityForUser(@NonNull final String userId,
                                                                 @NonNull final int educationId) {
        return Optional.ofNullable(mapper.load(DynamoDBUserProject.class, userId, educationId));
    }

    public void save(@NonNull final DynamoDBUserProject dynamoDBUserProject) {
        mapper.save(dynamoDBUserProject);
    }

    public void deleteProjectEntityForUser(@NonNull final DynamoDBUserProject dynamoDBUserProject) {
        mapper.delete(dynamoDBUserProject);
    }
}