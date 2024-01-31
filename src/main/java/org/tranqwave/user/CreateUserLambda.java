package org.tranqwave.user;

import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dao.UserDao;
import dao.dbModels.DynamoDBUser;
import model.CreateUserRequest;
import model.UserResponse;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import static utils.ConstantUtils.*;

public class CreateUserLambda implements RequestHandler<CreateUserRequest, UserResponse> {
    private static UserDao userDao;
    public CreateUserLambda() {
        userDao = new UserDao();
    }

    @Override
    public UserResponse handleRequest(CreateUserRequest request, Context context) {
        Instant dateInstant = new Date().toInstant();
        String currentDate = DateTimeFormatter.ISO_INSTANT.format(dateInstant);

        GetItemRequest user = new GetItemRequest()
                .withTableName(FOB_USER_TABLE)
                .addKeyEntry(USER_ID, new AttributeValue(request.getEmail()));

        if (user != null)
            return new UserResponse(ERROR, String.format("Account with email %s already exists", request.getEmail()));

        final DynamoDBUser newUser = new DynamoDBUser(request.getEmail(), request.getPassword(), request.getFullName(), currentDate, currentDate);
        userDao.save(newUser);

        return new UserResponse(SUCCESS, "Account registered successfully");
    }
}
