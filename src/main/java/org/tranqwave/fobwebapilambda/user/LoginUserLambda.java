package org.tranqwave.fobwebapilambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserDao;
import dao.dbModels.DynamoDBUser;
import model.LoginUserRequest;
import model.ResponseMessage;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class LoginUserLambda {
    private final UserDao userDao;

    public LoginUserLambda(UserDao userDao) {
        this.userDao = userDao;
    }
    public ResponseMessage loginUser(LoginUserRequest request, Context context) {
        Instant dateInstant = new Date().toInstant();
        String currentDate = DateTimeFormatter.ISO_INSTANT.format(dateInstant);

        final DynamoDBUser user = userDao.getUser(request.getEmail());

        if(user == null)
            return new ResponseMessage(ERROR, "Account with email does not exist");

        final String userPass = user.getPassword();

        if(!userPass.equals(request.getPassword()))
            return new ResponseMessage(ERROR, "Incorrect password");

        user.setLast_login(currentDate);
        userDao.save(user);
        return new ResponseMessage(SUCCESS, "Login successful");
    }
}
