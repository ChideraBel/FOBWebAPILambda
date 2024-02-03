package org.tranqwave.fobwebapilambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dao.UserDao;
import dao.UserProfileDao;
import dao.dbModels.DynamoDBUser;
import dao.dbModels.DynamoDBUserProfile;
import model.DeleteUserRequest;
import model.UserResponse;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class DeleteUserLambda {

    private final UserDao userDao;
    private final UserProfileDao userProfileDao;
    public DeleteUserLambda(UserDao userDao, UserProfileDao userProfileDao) {
        this.userDao = userDao;
        this.userProfileDao = userProfileDao;
    }

    public UserResponse deleteUser(DeleteUserRequest request, Context context){
        String userId = request.getEmail();

        final DynamoDBUser user = userDao.getUser(userId);

        if(user == null)
            return new UserResponse(ERROR, "Account with email does not exist");

        final DynamoDBUserProfile userProfile = userProfileDao.getUserProfile(userId);
        if(userProfile != null)
            userProfileDao.delete(userProfile);

        userDao.delete(user);

        return new UserResponse(SUCCESS, String.format("Account with email %s has been successfully deleted", user.getUser_id()));
    }
}
