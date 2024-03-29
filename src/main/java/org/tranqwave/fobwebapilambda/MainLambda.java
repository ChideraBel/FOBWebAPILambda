package org.tranqwave.fobwebapilambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dao.OTPCodesDao;
import dao.SectionDao;
import dao.UserDao;
import dao.UserEducationDao;
import dao.UserExperienceDao;
import dao.UserProfileDao;

import dao.UserProjectDao;
import dao.UserSkillDao;
import model.AddEducationRequest;
import model.AddProjectRequest;
import model.AddSkillRequest;
import model.ChatBotRequest;
import model.CreateSectionRequest;
import model.AddExperienceRequest;
import model.CreateUserRequest;
import model.DeleteEducationRequest;
import model.DeleteExperienceRequest;
import model.DeleteProjectRequest;
import model.DeleteSkillRequest;
import model.DeleteUserRequest;
import model.GenerateResumeRequest;
import model.GetAllUserEducationRequest;
import model.GetAllUserExperienceRequest;
import model.GetAllUserProjectRequest;
import model.GetAllUserSkillRequest;
import model.GetSectionRequest;
import model.GetUserProfileRequest;
import model.LoginUserRequest;
import model.OTPVerificationRequest;
import model.Request;
import model.SendOTPRequest;
import model.UpdateEducationRequest;
import model.UpdateExperienceRequest;
import model.UpdateProjectRequest;
import model.UpdateSkillRequest;
import model.UpdateUserProfileRequest;

import org.tranqwave.fobwebapilambda.chatBot.ChatBotLambda;
import org.tranqwave.fobwebapilambda.resume.AddUserEducationLambda;
import org.tranqwave.fobwebapilambda.resume.AddUserExperienceLambda;
import org.tranqwave.fobwebapilambda.resume.AddUserProjectLambda;
import org.tranqwave.fobwebapilambda.resume.AddUserSkillLambda;
import org.tranqwave.fobwebapilambda.resume.DeleteUserEducationLambda;
import org.tranqwave.fobwebapilambda.resume.DeleteUserExperienceLambda;
import org.tranqwave.fobwebapilambda.resume.DeleteUserProjectLambda;
import org.tranqwave.fobwebapilambda.resume.DeleteUserSkillLambda;
import org.tranqwave.fobwebapilambda.resume.GetAllUserEducationLambda;
import org.tranqwave.fobwebapilambda.resume.GetAllUserExperienceLambda;
import org.tranqwave.fobwebapilambda.resume.GetAllUserProjectLambda;
import org.tranqwave.fobwebapilambda.resume.GetAllUserSkillLambda;
import org.tranqwave.fobwebapilambda.resume.UpdateUserEducationLambda;
import org.tranqwave.fobwebapilambda.resume.UpdateUserProjectLambda;
import org.tranqwave.fobwebapilambda.resume.UpdateUserSkillLambda;
import org.tranqwave.fobwebapilambda.resumeGen.GenerateResumeLambda;
import org.tranqwave.fobwebapilambda.section.CreateSectionLambda;
import org.tranqwave.fobwebapilambda.resume.UpdateUserExperienceLambda;
import org.tranqwave.fobwebapilambda.section.GetAllSectionsLambda;
import org.tranqwave.fobwebapilambda.section.GetSectionLambda;
import org.tranqwave.fobwebapilambda.user.CreateUserLambda;
import org.tranqwave.fobwebapilambda.user.DeleteUserLambda;
import org.tranqwave.fobwebapilambda.user.GetUserProfileLambda;
import org.tranqwave.fobwebapilambda.user.LoginUserLambda;
import org.tranqwave.fobwebapilambda.user.OTPVerificationLambda;
import org.tranqwave.fobwebapilambda.user.SendOTPLambda;
import org.tranqwave.fobwebapilambda.user.UpdateUserProfileLambda;

import static utils.ConstantUtils.RequestTypes.ADD_USER_EDUCATION;
import static utils.ConstantUtils.RequestTypes.ADD_USER_PROJECT;
import static utils.ConstantUtils.RequestTypes.ADD_USER_SKILL;
import static utils.ConstantUtils.RequestTypes.CREATE_SECTION_REQUEST;
import static utils.ConstantUtils.RequestTypes.ADD_USER_EXPERIENCE;
import static utils.ConstantUtils.RequestTypes.CREATE_USER_REQUEST;
import static utils.ConstantUtils.RequestTypes.DELETE_USER_EDUCATION;
import static utils.ConstantUtils.RequestTypes.DELETE_USER_EXPERIENCE;
import static utils.ConstantUtils.RequestTypes.DELETE_USER_PROJECT;
import static utils.ConstantUtils.RequestTypes.DELETE_USER_REQUEST;
import static utils.ConstantUtils.RequestTypes.DELETE_USER_SKILL;
import static utils.ConstantUtils.RequestTypes.GENERATE_USER_RESUME;
import static utils.ConstantUtils.RequestTypes.GET_ALL_SECTIONS_REQUEST;
import static utils.ConstantUtils.RequestTypes.GET_ALL_USER_EDUCATION;
import static utils.ConstantUtils.RequestTypes.GET_ALL_USER_EXPERIENCE;
import static utils.ConstantUtils.RequestTypes.GET_ALL_USER_PROJECT;
import static utils.ConstantUtils.RequestTypes.GET_ALL_USER_SKILL;
import static utils.ConstantUtils.RequestTypes.GET_SECTION_REQUEST;
import static utils.ConstantUtils.RequestTypes.GET_USER_PROFILE;
import static utils.ConstantUtils.RequestTypes.LOGIN_USER_REQUEST;
import static utils.ConstantUtils.RequestTypes.PROCESS_CHAT_BOT_PROMPT;
import static utils.ConstantUtils.RequestTypes.SEND_OTP_REQUEST;
import static utils.ConstantUtils.RequestTypes.UPDATE_USER_EDUCATION;
import static utils.ConstantUtils.RequestTypes.UPDATE_USER_EXPERIENCE;
import static utils.ConstantUtils.RequestTypes.UPDATE_USER_PROFILE;
import static utils.ConstantUtils.RequestTypes.UPDATE_USER_PROJECT;
import static utils.ConstantUtils.RequestTypes.UPDATE_USER_SKILL;
import static utils.ConstantUtils.RequestTypes.VERIFY_OTP_REQUEST;


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
    private final GetSectionLambda getSectionLambda;
    private final GetAllSectionsLambda getAllSectionsLambda;
    private final AddUserSkillLambda addUserSkillLambda;
    private final DeleteUserSkillLambda deleteUserSkillLambda;
    private final GetAllUserSkillLambda getAllUserSkillLambda;
    private final UpdateUserSkillLambda updateUserSkillLambda;
    private final OTPVerificationLambda otpVerificationLambda;
    private final SendOTPLambda sendOTPLambda;
    private final AddUserProjectLambda addUserProjectLambda;
    private final DeleteUserProjectLambda deleteUserProjectLambda;
    private final UpdateUserProjectLambda updateUserProjectLambda;
    private final GetAllUserProjectLambda getAllUserProjectLambda;
    private final ChatBotLambda chatBotLambda;
    private final GenerateResumeLambda generateResumeLambda;
    private final GetUserProfileLambda getUserProfileLambda;
    private final UpdateUserProfileLambda updateUserProfileLambda;

    public MainLambda() {
        final AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
        final DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);

        final UserDao userDao = new UserDao(mapper);
        final UserProfileDao userProfileDao = new UserProfileDao(mapper);
        final UserEducationDao userEducationDao = new UserEducationDao(mapper);
        final SectionDao sectionDao = new SectionDao(mapper);
        final UserExperienceDao userExperienceDao = new UserExperienceDao(mapper);
        final UserSkillDao userSkillDao = new UserSkillDao(mapper);
        final OTPCodesDao otpCodesDao = new OTPCodesDao(mapper);
        final UserProjectDao userProjectDao = new UserProjectDao(mapper);

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
        getSectionLambda = new GetSectionLambda(sectionDao);
        getAllSectionsLambda = new GetAllSectionsLambda(sectionDao);
        otpVerificationLambda = new OTPVerificationLambda(otpCodesDao);
        sendOTPLambda = new SendOTPLambda(otpCodesDao, userDao);
        addUserProjectLambda = new AddUserProjectLambda(userProjectDao, userDao);
        deleteUserProjectLambda = new DeleteUserProjectLambda(userProjectDao);
        updateUserProjectLambda = new UpdateUserProjectLambda(userProjectDao);
        getAllUserProjectLambda = new GetAllUserProjectLambda(userProjectDao);
        chatBotLambda = new ChatBotLambda(userDao);
        generateResumeLambda = new GenerateResumeLambda(userDao, userProfileDao, userEducationDao, userProjectDao, userSkillDao, userExperienceDao);
        getUserProfileLambda = new GetUserProfileLambda(userProfileDao);
        updateUserProfileLambda = new UpdateUserProfileLambda(userProfileDao);
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
                return createSectionLambda.createSection(CreateSectionRequest.fromMap(request.getRequestBody()), context);
            case GET_SECTION_REQUEST:
                return getSectionLambda.getSection(GetSectionRequest.fromMap(request.getRequestBody()), context);
            case GET_ALL_SECTIONS_REQUEST:
                return getAllSectionsLambda.getAllSections(context);
            case SEND_OTP_REQUEST:
                return sendOTPLambda.sendOTPCode(SendOTPRequest.fromMap(request.getRequestBody()), context);
            case VERIFY_OTP_REQUEST:
                return otpVerificationLambda.verifyOTPCode(OTPVerificationRequest.fromMap(request.getRequestBody()), context);
            case ADD_USER_PROJECT:
                return addUserProjectLambda.addProject(AddProjectRequest.fromMap(request.getRequestBody()), context);
            case DELETE_USER_PROJECT:
                return deleteUserProjectLambda.deleteProject(DeleteProjectRequest.fromMap(request.getRequestBody()), context);
            case UPDATE_USER_PROJECT:
                return updateUserProjectLambda.updateProject(UpdateProjectRequest.fromMap(request.getRequestBody()), context);
            case GET_ALL_USER_PROJECT:
                return getAllUserProjectLambda.getAllProjects(GetAllUserProjectRequest.fromMap(request.getRequestBody()), context);
            case PROCESS_CHAT_BOT_PROMPT:
                return chatBotLambda.processChatBotRequest(ChatBotRequest.fromMap(request.getRequestBody()), context);
            case GENERATE_USER_RESUME:
                return generateResumeLambda.generateResume(GenerateResumeRequest.fromMap(request.getRequestBody()), context);
            case GET_USER_PROFILE:
                return getUserProfileLambda.getUserProfile(GetUserProfileRequest.fromMap(request.getRequestBody()), context);
            case UPDATE_USER_PROFILE:
                return updateUserProfileLambda.updateUserProfile(UpdateUserProfileRequest.fromMap(request.getRequestBody()), context);
        }
        throw new IllegalArgumentException(String.format("Bad request input request type: %s", request.getRequestType()));
    }
}