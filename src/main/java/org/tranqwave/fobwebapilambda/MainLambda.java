package org.tranqwave.fobwebapilambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dao.UserDao;
import dao.UserProfileDao;
import model.*;
import org.tranqwave.fobwebapilambda.user.CreateUserLambda;
import org.tranqwave.fobwebapilambda.user.DeleteUserLambda;
import org.tranqwave.fobwebapilambda.user.LoginUserLambda;

import static utils.ConstantUtils.RequestTypes.*;

public class MainLambda implements RequestHandler<Request, UserResponse> {
    private final UserDao userDao;
    private final UserProfileDao userProfileDao;

    private final LoginUserLambda loginUserLambda;
    private final DeleteUserLambda deleteUserLambda;
    private final CreateUserLambda createUserLambda;
    public MainLambda() {
        userDao = new UserDao();
        userProfileDao = new UserProfileDao();
        loginUserLambda = new LoginUserLambda(userDao);
        deleteUserLambda = new DeleteUserLambda(userDao, userProfileDao);
        createUserLambda = new CreateUserLambda(userDao, userProfileDao);
    }

    /*
    Handles the request sent to the lambda function and routes to the corresponding lambda function code
    for that request.
     */

    @Override
    public UserResponse handleRequest(Request request, Context context){
        switch (request.getRequestType()){
            case LOGIN_USER_REQUEST:
                return loginUserLambda.loginUser(LoginUserRequest.fromMap(request.getRequestBody()), context);
            case DELETE_USER_REQUEST:
                return deleteUserLambda.deleteUser(DeleteUserRequest.fromMap(request.getRequestBody()), context);
            case CREATE_USER_REQUEST:
                return createUserLambda.createUser(CreateUserRequest.fromMap(request.getRequestBody()), context);
        }

        return new UserResponse();
    }
}
