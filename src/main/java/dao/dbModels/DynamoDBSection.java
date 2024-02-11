package dao.dbModels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "FOBSectionTable")
public class DynamoDBSection {

    @DynamoDBHashKey(attributeName = "section_id")
    public int section_id;

    @DynamoDBAttribute(attributeName = "section_name")
    public String section_name;

    @DynamoDBAttribute(attributeName = "section_content")
    public String section_content;

    @DynamoDBAttribute(attributeName = "section_description")
    public String section_description;
}
