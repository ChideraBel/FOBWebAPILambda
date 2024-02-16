package dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import dao.dbModels.DynamoDBUserSkill;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserSkillDao {
    private DynamoDBMapper mapper;

    /*
   Gets all the skill entities for the specified userId
    */
    public List<DynamoDBUserSkill> getAllUserSkill(@NonNull final String userId) {
        DynamoDBUserSkill partitionKeyItem = new DynamoDBUserSkill();
        partitionKeyItem.setUser_id(userId);

        DynamoDBQueryExpression<DynamoDBUserSkill> queryExpression = new DynamoDBQueryExpression<DynamoDBUserSkill>()
                .withHashKeyValues(partitionKeyItem);

        return mapper.query(DynamoDBUserSkill.class, queryExpression);
    }

    /*
    Gets the next sequence number for the skill ID under the specified userId
    */
    public int getNextSequence(@NonNull final String userId) {
        DynamoDBUserSkill partitionKeyItem = new DynamoDBUserSkill();
        partitionKeyItem.setUser_id(userId);

        DynamoDBQueryExpression<DynamoDBUserSkill> queryExpression = new DynamoDBQueryExpression<DynamoDBUserSkill>()
                .withHashKeyValues(partitionKeyItem)
                .withScanIndexForward(false)
                .withLimit(1);
        return mapper.query(DynamoDBUserSkill.class, queryExpression).get(0).getSkill_id() + 1;
    }

    public Optional<DynamoDBUserSkill> getSkillEntityForUser(@NonNull final String userId,
                                                             @NonNull final int skillId) {
        return Optional.ofNullable(mapper.load(DynamoDBUserSkill.class, userId, skillId));
    }

    public void save(@NonNull final DynamoDBUserSkill dynamoDBUserSkill) {
        mapper.save(dynamoDBUserSkill);
    }

    public void deleteSkillForUser(@NonNull final DynamoDBUserSkill dynamoDBUserSkill) {
        mapper.delete(dynamoDBUserSkill);
    }
}