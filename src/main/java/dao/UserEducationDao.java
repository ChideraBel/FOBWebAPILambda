package dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import dao.dbModels.DynamoDBUserEducation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
public class UserEducationDao {
    private DynamoDBMapper mapper;

    public DynamoDBUserEducation getUserEducation(@NonNull final String userId) {
        return mapper.load(DynamoDBUserEducation.class, userId);
    }

    /*
    Gets the next sequence number for the education ID under the specified userId
     */
    public int getNextSequence(@NonNull final String userId){
        DynamoDBUserEducation education = new DynamoDBUserEducation();
        education.setUser_id(userId);

        DynamoDBQueryExpression<DynamoDBUserEducation> queryExpression = new DynamoDBQueryExpression<DynamoDBUserEducation>()
                .withHashKeyValues(education);

        final List<DynamoDBUserEducation> userEducationList = mapper.query(DynamoDBUserEducation.class, queryExpression);
        return userEducationList.size() + 1;
    }

    public void save(DynamoDBUserEducation dynamoDBUserEducation) {
        mapper.save(dynamoDBUserEducation);
    }

    public void delete(DynamoDBUserEducation dynamoDBUserEducation) {
        mapper.delete(dynamoDBUserEducation);
    }

}
