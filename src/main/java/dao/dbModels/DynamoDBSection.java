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
    private int section_id;

    @DynamoDBAttribute(attributeName = "section_name")
    private String section_name;

    @DynamoDBAttribute(attributeName = "section_content")
    private String section_content;

    @DynamoDBAttribute(attributeName = "section_description")
    private String section_description;
}
