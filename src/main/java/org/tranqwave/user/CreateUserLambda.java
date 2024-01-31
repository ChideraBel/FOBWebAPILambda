package org.tranqwave.user;

import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import model.CreateUserRequest;
import model.CreateUserResponse;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static utils.ConstantUtils.*;

public class CreateUserLambda implements RequestHandler<CreateUserRequest, CreateUserResponse> {
    private final AmazonDynamoDB dynamoDBClient;

    public CreateUserLambda() {
        dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
    }

    @Override
    public CreateUserResponse handleRequest(CreateUserRequest request, Context context) {
        Instant dateInstant = new Date().toInstant();
        String currentDate = DateTimeFormatter.ISO_INSTANT.format(dateInstant);

        GetItemRequest user = new GetItemRequest()
                .withTableName(FOB_USER_TABLE)
                .addKeyEntry(USER_ID, new AttributeValue(request.getEmail()));

        if(user != null)
            throw new IllegalArgumentException("Account with email already exists.");

        PutItemRequest putItemRequest = new PutItemRequest()
                .withTableName(FOB_USER_TABLE)
                .withItem(Map.of(
                        USER_ID, new AttributeValue(request.getEmail()),
                        PASSWORD, new AttributeValue(request.getPassword()),
                        FULL_NAME, new AttributeValue(request.getFullName()),
                        LAST_LOGIN, new AttributeValue(currentDate),
                        DATE_REGISTERED, new AttributeValue(currentDate)
                ));

        dynamoDBClient.putItem(putItemRequest);

        return new CreateUserResponse();
    }
}
