package org.tranqwave.fobwebapilambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dao.UserDao;
import dao.UserEducationDao;
import dao.UserProfileDao;
import model.AddEducationRequest;
import model.CreateUserRequest;
import model.DeleteEducationRequest;
import model.DeleteUserRequest;
import model.GetAllUserEducationRequest;
import model.LoginUserRequest;
import model.Request;
import model.UpdateEducationRequest;
import org.tranqwave.fobwebapilambda.resume.AddUserEducationLambda;
import org.tranqwave.fobwebapilambda.resume.DeleteUserEducationLambda;
import org.tranqwave.fobwebapilambda.resume.GetAllUserEducationLambda;
import org.tranqwave.fobwebapilambda.resume.UpdateUserEducationLambda;
import org.tranqwave.fobwebapilambda.user.CreateUserLambda;
import org.tranqwave.fobwebapilambda.user.DeleteUserLambda;
import org.tranqwave.fobwebapilambda.user.LoginUserLambda;

import static utils.ConstantUtils.RequestTypes.ADD_USER_EDUCATION;
import static utils.ConstantUtils.RequestTypes.CREATE_USER_REQUEST;
import static utils.ConstantUtils.RequestTypes.DELETE_USER_EDUCATION;
import static utils.ConstantUtils.RequestTypes.DELETE_USER_REQUEST;
import static utils.ConstantUtils.RequestTypes.GET_ALL_USER_EDUCATION;
import static utils.ConstantUtils.RequestTypes.LOGIN_USER_REQUEST;
import static utils.ConstantUtils.RequestTypes.UPDATE_USER_EDUCATION;

public class MainLambda implements RequestHandler<Request, Object> {
    private final UserDao userDao;
    private final UserProfileDao userProfileDao;
    private final UserEducationDao userEducationDao;

    private final LoginUserLambda loginUserLambda;
    private final DeleteUserLambda deleteUserLambda;
    private final CreateUserLambda createUserLambda;
    private final AddUserEducationLambda addUserEducationLambda;
    private final UpdateUserEducationLambda updateUserEducationLambda;
    private final DeleteUserEducationLambda deleteUserEducationLambda;
    private final GetAllUserEducationLambda getAllUserEducationLambda;

    public MainLambda() {
        final AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
        final DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);

        userDao = new UserDao(mapper);
        userProfileDao = new UserProfileDao(mapper);
        userEducationDao = new UserEducationDao(mapper);
        loginUserLambda = new LoginUserLambda(userDao);
        deleteUserLambda = new DeleteUserLambda(userDao, userProfileDao);
        createUserLambda = new CreateUserLambda(userDao, userProfileDao);
        addUserEducationLambda = new AddUserEducationLambda(userEducationDao, userDao);
        updateUserEducationLambda = new UpdateUserEducationLambda(userEducationDao);
        deleteUserEducationLambda = new DeleteUserEducationLambda(userEducationDao);
        getAllUserEducationLambda = new GetAllUserEducationLambda(userEducationDao);
    }

    /*
    Handles the request sent to the lambda function and routes to the corresponding lambda function code
    for that request.
     */
    @Override
    public Object handleRequest(Request request, Context context) {
        switch (request.getRequestType()) {
            case LOGIN_USER_REQUEST:
                return loginUserLambda.loginUser(LoginUserRequest.fromMap(request.getRequestBody()), context);
            case DELETE_USER_REQUEST:
                return deleteUserLambda.deleteUser(DeleteUserRequest.fromMap(request.getRequestBody()), context);
            case CREATE_USER_REQUEST:
                return createUserLambda.createUser(CreateUserRequest.fromMap(request.getRequestBody()), context);
            case ADD_USER_EDUCATION:
                return addUserEducationLambda.addEducation(AddEducationRequest.fromMap(request.getRequestBody()), context);
            case UPDATE_USER_EDUCATION:
                return updateUserEducationLambda.updateEducation(UpdateEducationRequest.fromMap(request.getRequestBody()), context);
            case DELETE_USER_EDUCATION:
                return deleteUserEducationLambda.deleteEducation(DeleteEducationRequest.fromMap(request.getRequestBody()), context);
            case GET_ALL_USER_EDUCATION:
                return getAllUserEducationLambda.getAllUserEducation(GetAllUserEducationRequest.fromMap(request.getRequestBody()), context);
        }
        throw new IllegalArgumentException(String.format("Bad request input request type: %s", request.getRequestType()));
    }
}