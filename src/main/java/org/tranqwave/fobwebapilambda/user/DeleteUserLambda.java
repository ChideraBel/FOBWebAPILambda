package org.tranqwave.fobwebapilambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dao.UserDao;
import dao.dbModels.DynamoDBUser;
import model.DeleteUserRequest;
import model.UserResponse;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class DeleteUserLambda implements RequestHandler<DeleteUserRequest, UserResponse> {

    private static UserDao userDao;
    public DeleteUserLambda() {
        userDao = new UserDao();
    }

    @Override
    public UserResponse handleRequest(DeleteUserRequest request, Context context){
        final DynamoDBUser user = userDao.getUser(request.getEmail());

        if(user == null)
            return new UserResponse(ERROR, "Account with email does not exist");


        userDao.delete(user);

        return new UserResponse(SUCCESS, String.format("Account with email %s has been successfully deleted", user.getUser_id()));
    }
}
