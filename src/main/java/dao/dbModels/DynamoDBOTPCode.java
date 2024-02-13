package dao.dbModels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "FOBOTPCodesTable")
public class DynamoDBOTPCode {
    @DynamoDBHashKey(attributeName = "user_id")
    private String user_id;

    @DynamoDBRangeKey(attributeName = "timestamp")
    private String timestamp;

    @DynamoDBAttribute(attributeName = "otp_code")
    private String otp_code;
}