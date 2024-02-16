package utils;

import dao.dbModels.DynamoDBSection;
import dao.dbModels.DynamoDBUserProject;
import model.Project;
import model.Section;

public class ConstantUtils {

    final public static String ERROR = "ERROR";
    final public static String SUCCESS = "SUCCESS";

    public static class RequestTypes {
        final public static String LOGIN_USER_REQUEST = "loginUser";
        final public static String CREATE_USER_REQUEST = "createUser";
        final public static String DELETE_USER_REQUEST = "deleteUser";
        final public static String ADD_USER_EDUCATION = "addUserEducation";
        final public static String DELETE_USER_EDUCATION = "deleteUserEducation";
        final public static String UPDATE_USER_EDUCATION = "updateUserEducation";
        final public static String GET_ALL_USER_EDUCATION = "getAllUserEducation";
        final public static String ADD_USER_EXPERIENCE = "addUserExperience";
        final public static String DELETE_USER_EXPERIENCE = "deleteUserExperience";
        final public static String UPDATE_USER_EXPERIENCE = "updateUserExperience";
        final public static String GET_ALL_USER_EXPERIENCE = "getAllUserExperience";
        final public static String ADD_USER_SKILL = "addUserSkill";
        final public static String DELETE_USER_SKILL = "deleteUserSkill";
        final public static String UPDATE_USER_SKILL = "updateUserSkill";
        final public static String GET_ALL_USER_SKILL = "getAllUserSKill";
        final public static String ADD_USER_PROJECT = "addUserProject";
        final public static String DELETE_USER_PROJECT = "deleteUserProject";
        final public static String UPDATE_USER_PROJECT = "updateUserProject";
        final public static String GET_ALL_USER_PROJECT = "getAllUserProject";
        final public static String CREATE_SECTION_REQUEST = "createSection";
        final public static String GET_SECTION_REQUEST = "getSection";
        final public static String GET_ALL_SECTIONS_REQUEST ="getAllSections";
        final public static String SEND_OTP_REQUEST = "sendOTP";
        final public static String VERIFY_OTP_REQUEST = "verifyOTP";
    }

    public static Section toSection(DynamoDBSection dynamoDBSection) {
        return Section.builder()
                .section_id(dynamoDBSection.getSection_id())
                .name(dynamoDBSection.getSection_name())
                .content(dynamoDBSection.getSection_content())
                .description(dynamoDBSection.getSection_description())
                .build();
    }

    public static Project toProject(DynamoDBUserProject dynamoDBUserProject) {
        return Project.builder()
                .projectDescription(dynamoDBUserProject.getDescription())
                .projectName(dynamoDBUserProject.getProject_title())
                .role(dynamoDBUserProject.getRole())
                .startDate(dynamoDBUserProject.getStart_date())
                .techUsed(dynamoDBUserProject.getTechnologies_used())
                .endDate(dynamoDBUserProject.getEnd_date())
                .build();
    }
}