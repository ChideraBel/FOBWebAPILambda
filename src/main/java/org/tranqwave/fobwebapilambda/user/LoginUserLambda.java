package org.tranqwave.fobwebapilambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dao.UserDao;
import dao.dbModels.DynamoDBUser;
import model.LoginUserRequest;
import model.UserResponse;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static utils.ConstantUtils.*;

public class LoginUserLambda implements RequestHandler<LoginUserRequest, UserResponse> {
    private static UserDao userDao;
    public LoginUserLambda() {
        userDao = new UserDao();
    }

    @Override
    public UserResponse handleRequest(LoginUserRequest request, Context context) {
        Instant dateInstant = new Date().toInstant();
        String currentDate = DateTimeFormatter.ISO_INSTANT.format(dateInstant);

        final DynamoDBUser user = userDao.getUser(request.getEmail());

        if(user == null)
            return new UserResponse(ERROR, "Account with email does not exist");

        final String userPass = user.getPassword();

        if(!userPass.equals(request.getPassword()))
            return new UserResponse(ERROR, "Incorrect password");

        user.setLast_login(currentDate);
        userDao.save(user);

        return new UserResponse(SUCCESS, "Login successful");
    }
}
