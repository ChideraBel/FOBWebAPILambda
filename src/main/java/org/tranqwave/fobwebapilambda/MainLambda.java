package org.tranqwave.fobwebapilambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dao.SectionDao;
import dao.UserDao;
import dao.UserEducationDao;
import dao.UserExperienceDao;
import dao.UserProfileDao;

import dao.UserSkillDao;
import model.AddEducationRequest;
import model.AddSkillRequest;
import model.CreateSectionRequest;
import model.AddExperienceRequest;
import model.CreateUserRequest;
import model.DeleteEducationRequest;
import model.DeleteExperienceRequest;
import model.DeleteSkillRequest;
import model.DeleteUserRequest;
import model.GetAllUserEducationRequest;
import model.GetAllUserExperienceRequest;
import model.GetAllUserProfileDetailsRequest;
import model.GetAllUserSkillRequest;
import model.LoginUserRequest;
import model.Request;
import model.UpdateEducationRequest;
import model.UpdateExperienceRequest;
import model.UpdateSkillRequest;
import org.tranqwave.fobwebapilambda.resume.AddUserEducationLambda;
import org.tranqwave.fobwebapilambda.resume.AddUserExperienceLambda;
import org.tranqwave.fobwebapilambda.resume.AddUserSkillLambda;
import org.tranqwave.fobwebapilambda.resume.DeleteUserEducationLambda;
import org.tranqwave.fobwebapilambda.resume.DeleteUserExperienceLambda;
import org.tranqwave.fobwebapilambda.resume.DeleteUserSkillLambda;
import org.tranqwave.fobwebapilambda.resume.GetAllUserEducationLambda;
import org.tranqwave.fobwebapilambda.resume.GetAllUserExperienceLambda;
import org.tranqwave.fobwebapilambda.resume.GetAllUserSkillLambda;
import org.tranqwave.fobwebapilambda.resume.UpdateUserEducationLambda;
import org.tranqwave.fobwebapilambda.resume.UpdateUserSkillLambda;
import org.tranqwave.fobwebapilambda.section.CreateSectionLambda;
import org.tranqwave.fobwebapilambda.resume.UpdateUserExperienceLambda;
import org.tranqwave.fobwebapilambda.user.CreateUserLambda;
import org.tranqwave.fobwebapilambda.user.DeleteUserLambda;
import org.tranqwave.fobwebapilambda.user.GetUserProfileLambda;
import org.tranqwave.fobwebapilambda.user.LoginUserLambda;

import static utils.ConstantUtils.RequestTypes.ADD_USER_EDUCATION;
import static utils.ConstantUtils.RequestTypes.ADD_USER_SKILL;
import static utils.ConstantUtils.RequestTypes.CREATE_SECTION_REQUEST;
import static utils.ConstantUtils.RequestTypes.ADD_USER_EXPERIENCE;
import static utils.ConstantUtils.RequestTypes.CREATE_USER_REQUEST;
import static utils.ConstantUtils.RequestTypes.DELETE_USER_EDUCATION;
import static utils.ConstantUtils.RequestTypes.DELETE_USER_EXPERIENCE;
import static utils.ConstantUtils.RequestTypes.DELETE_USER_REQUEST;
import static utils.ConstantUtils.RequestTypes.DELETE_USER_SKILL;
import static utils.ConstantUtils.RequestTypes.GET_ALL_USER_EDUCATION;
import static utils.ConstantUtils.RequestTypes.GET_ALL_USER_EXPERIENCE;
import static utils.ConstantUtils.RequestTypes.GET_ALL_USER_SKILL;
import static utils.ConstantUtils.RequestTypes.GET_USER_PROFILE;
import static utils.ConstantUtils.RequestTypes.LOGIN_USER_REQUEST;
import static utils.ConstantUtils.RequestTypes.UPDATE_USER_EDUCATION;
import static utils.ConstantUtils.RequestTypes.UPDATE_USER_EXPERIENCE;
import static utils.ConstantUtils.RequestTypes.UPDATE_USER_SKILL;


public class MainLambda implements RequestHandler<Request, Object> {
    private final LoginUserLambda loginUserLambda;
    private final DeleteUserLambda deleteUserLambda;
    private final CreateUserLambda createUserLambda;
    private final AddUserEducationLambda addUserEducationLambda;
    private final UpdateUserEducationLambda updateUserEducationLambda;
    private final DeleteUserEducationLambda deleteUserEducationLambda;
    private final GetAllUserEducationLambda getAllUserEducationLambda;
    private final AddUserExperienceLambda addUserExperienceLambda;
    private final UpdateUserExperienceLambda updateUserExperienceLambda;
    private final DeleteUserExperienceLambda deleteUserExperienceLambda;
    private final GetAllUserExperienceLambda getAllUserExperienceLambda;
    private final CreateSectionLambda createSectionLambda;
    private final AddUserSkillLambda addUserSkillLambda;
    private final DeleteUserSkillLambda deleteUserSkillLambda;
    private final GetAllUserSkillLambda getAllUserSkillLambda;
    private final UpdateUserSkillLambda updateUserSkillLambda;
    private final GetUserProfileLambda getUserProfileLambda;

    public MainLambda() {
        final AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
        final DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);

        final UserDao userDao = new UserDao(mapper);
        final UserProfileDao userProfileDao = new UserProfileDao(mapper);
        final UserEducationDao userEducationDao = new UserEducationDao(mapper);
        final SectionDao sectionDao = new SectionDao(mapper);
        final UserExperienceDao userExperienceDao = new UserExperienceDao(mapper);
        final UserSkillDao userSkillDao = new UserSkillDao(mapper);

        loginUserLambda = new LoginUserLambda(userDao);
        deleteUserLambda = new DeleteUserLambda(userDao, userProfileDao);
        createUserLambda = new CreateUserLambda(userDao, userProfileDao);
        addUserEducationLambda = new AddUserEducationLambda(userEducationDao, userDao);
        updateUserEducationLambda = new UpdateUserEducationLambda(userEducationDao);
        deleteUserEducationLambda = new DeleteUserEducationLambda(userEducationDao);
        getAllUserEducationLambda = new GetAllUserEducationLambda(userEducationDao);
        createSectionLambda = new CreateSectionLambda(sectionDao);
        addUserExperienceLambda = new AddUserExperienceLambda(userExperienceDao, userDao);
        updateUserExperienceLambda = new UpdateUserExperienceLambda(userExperienceDao);
        deleteUserExperienceLambda = new DeleteUserExperienceLambda(userExperienceDao);
        getAllUserExperienceLambda = new GetAllUserExperienceLambda(userExperienceDao);
        addUserSkillLambda = new AddUserSkillLambda(userSkillDao, userDao);
        deleteUserSkillLambda = new DeleteUserSkillLambda(userSkillDao);
        getAllUserSkillLambda = new GetAllUserSkillLambda(userSkillDao);
        updateUserSkillLambda = new UpdateUserSkillLambda(userSkillDao);
        getUserProfileLambda = new GetUserProfileLambda(userProfileDao);
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
            case ADD_USER_EXPERIENCE:
                return addUserExperienceLambda.addExperience(AddExperienceRequest.fromMap(request.getRequestBody()), context);
            case GET_ALL_USER_EXPERIENCE:
                return getAllUserExperienceLambda.getAllUserExperience(GetAllUserExperienceRequest.fromMap(request.getRequestBody()), context);
            case UPDATE_USER_EXPERIENCE:
                return updateUserExperienceLambda.updateExperience(UpdateExperienceRequest.fromMap(request.getRequestBody()), context);
            case DELETE_USER_EXPERIENCE:
                return deleteUserExperienceLambda.deleteExperience(DeleteExperienceRequest.fromMap(request.getRequestBody()), context);
            case ADD_USER_SKILL:
                return addUserSkillLambda.addSkill(AddSkillRequest.fromMap(request.getRequestBody()), context);
            case DELETE_USER_SKILL:
                return deleteUserSkillLambda.deleteSkill(DeleteSkillRequest.fromMap(request.getRequestBody()), context);
            case UPDATE_USER_SKILL:
                return updateUserSkillLambda.updateSkill(UpdateSkillRequest.fromMap(request.getRequestBody()), context);
            case GET_ALL_USER_SKILL:
                return getAllUserSkillLambda.getAllUserSkill(GetAllUserSkillRequest.fromMap(request.getRequestBody()), context);
            case CREATE_SECTION_REQUEST:
                return createSectionLambda.createSection(CreateSectionRequest.fromMap(request.getRequestBody()));
            case GET_USER_PROFILE:
                return getUserProfileLambda.getAllUserProfileDetails(GetAllUserProfileDetailsRequest.fromMap(request.getRequestBody()), context);
        }
        throw new IllegalArgumentException(String.format("Bad request input request type: %s", request.getRequestType()));
    }
}