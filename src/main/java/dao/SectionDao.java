package dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import dao.dbModels.DynamoDBSection;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
@AllArgsConstructor
public class SectionDao {


    private final DynamoDBMapper mapper;

    public DynamoDBSection getSection(@NonNull final String sectionId) { return mapper.load(DynamoDBSection.class, sectionId);}

    public void save(DynamoDBSection dynamoDBSection) { mapper.save(dynamoDBSection);}

    public void delete(DynamoDBSection dynamoDBSection) { mapper.delete(dynamoDBSection);}

    public List<DynamoDBSection> getAllSections() {
        return mapper.scan(DynamoDBSection.class, new DynamoDBScanExpression());
    }

    public int getNextSequence() {

        return getAllSections().size() + 1;
    }
}
