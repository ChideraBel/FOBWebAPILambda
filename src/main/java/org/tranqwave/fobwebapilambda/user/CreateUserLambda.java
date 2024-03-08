package org.tranqwave.fobwebapilambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserDao;
import dao.UserProfileDao;
import dao.dbModels.DynamoDBUser;
import dao.dbModels.DynamoDBUserProfile;
import model.CreateUserRequest;
import model.ResponseMessage;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class CreateUserLambda {
    private final UserDao userDao;
    private final UserProfileDao userProfileDao;
    public CreateUserLambda(UserDao userDao, UserProfileDao userProfileDao) {
        this.userDao = userDao;
        this.userProfileDao = userProfileDao;
    }

    /*
    Creates a new user and user profile.
     */
    public ResponseMessage createUser(CreateUserRequest request, Context context) {
        Instant dateInstant = new Date().toInstant();
        String currentDate = DateTimeFormatter.ISO_INSTANT.format(dateInstant);

        final DynamoDBUser user = userDao.getUser(request.getEmail());

        if (user != null)
            return new ResponseMessage(ERROR, String.format("Account with email %s already exists", request.getEmail()));

        final DynamoDBUser newUser = new DynamoDBUser(request.getEmail(), request.getPassword(), request.getFullName(), currentDate, currentDate);
        userDao.save(newUser);

        //Trying to simplify register process, avoiding overwhelming set up.
        final DynamoDBUserProfile newUserProfile = DynamoDBUserProfile.builder()
                .user_id(request.getEmail())
                .build();

        userProfileDao.save(newUserProfile);

        return new ResponseMessage(SUCCESS, "Account registered successfully");
    }
}