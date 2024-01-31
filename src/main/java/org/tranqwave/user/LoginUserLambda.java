package org.tranqwave.user;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import model.UserLoginRequest;
import model.UserResponse;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static utils.ConstantUtils.*;

public class LoginUserLambda implements RequestHandler<UserLoginRequest, UserResponse> {
    private final AmazonDynamoDB dynamoDBClient;

    public LoginUserLambda() {
        dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
    }

    @Override
    public UserResponse handleRequest(UserLoginRequest request, Context context) {
        Instant dateInstant = new Date().toInstant();
        String currentDate = DateTimeFormatter.ISO_INSTANT.format(dateInstant);

        GetItemRequest user = new GetItemRequest()
                .withTableName(FOB_USER_TABLE)
                .addKeyEntry(USER_ID, new AttributeValue(request.getEmail()));

        if(user == null)
            return new UserResponse(ERROR, "Account with email does not exist");

        final String userPass = user.getKey().get(PASSWORD).toString();

        if(!userPass.equals(request.getPassword()))
            return new UserResponse(ERROR, "Incorrect password");



        return new UserResponse(SUCCESS, "Login successful");
    }
}
