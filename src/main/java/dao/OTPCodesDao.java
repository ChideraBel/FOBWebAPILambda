package dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import dao.dbModels.DynamoDBOTPCode;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
public class OTPCodesDao {
    private final DynamoDBMapper mapper;

    public DynamoDBOTPCode getLatestOTPCode(@NonNull final String userId) {
        DynamoDBOTPCode partitionKeyItem = new DynamoDBOTPCode();
        partitionKeyItem.setUser_id(userId);

        DynamoDBQueryExpression<DynamoDBOTPCode> queryExpression = new DynamoDBQueryExpression<DynamoDBOTPCode>()
                .withHashKeyValues(partitionKeyItem)
                .withScanIndexForward(false)
                .withLimit(1);

        List<DynamoDBOTPCode> dynamoDBOTPCodes = mapper.query(DynamoDBOTPCode.class, queryExpression);
        return dynamoDBOTPCodes.isEmpty() ? null : dynamoDBOTPCodes.get(0);
    }

    public void save(DynamoDBOTPCode dynamoDBOTPCode) { mapper.save(dynamoDBOTPCode);}

    public void delete(DynamoDBOTPCode dynamoDBOTPCode) {mapper.delete(dynamoDBOTPCode);}
}