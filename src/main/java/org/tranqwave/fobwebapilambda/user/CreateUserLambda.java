package org.tranqwave.fobwebapilambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dao.UserDao;
import dao.UserProfileDao;
import dao.dbModels.DynamoDBUser;
import dao.dbModels.DynamoDBUserProfile;
import model.CreateUserRequest;
import model.UserResponse;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static utils.ConstantUtils.*;

public class CreateUserLambda {
    private final UserDao userDao;
    private final UserProfileDao userProfileDao;
    public CreateUserLambda(UserDao userDao, UserProfileDao userProfileDao) {
        this.userDao = userDao;
        this.userProfileDao = userProfileDao;
    }

    public UserResponse createUser(CreateUserRequest request, Context context) {
        Instant dateInstant = new Date().toInstant();
        String currentDate = DateTimeFormatter.ISO_INSTANT.format(dateInstant);

        final DynamoDBUser user = userDao.getUser(request.getEmail());

        if (user != null)
            return new UserResponse(ERROR, String.format("Account with email %s already exists", request.getEmail()));

        final DynamoDBUser newUser = new DynamoDBUser(request.getEmail(), request.getPassword(), request.getFullName(), currentDate, currentDate);
        userDao.save(newUser);

        final DynamoDBUserProfile newUserProfile = new DynamoDBUserProfile(request.getEmail(), request.getAddress(), request.getDob(),
                request.getEmployment(), request.getIndustry(), request.getNationality(), request.getProfilePic(), request.getVisaExpDate());

        userProfileDao.save(newUserProfile);

        return new UserResponse(SUCCESS, "Account registered successfully");
    }
}
