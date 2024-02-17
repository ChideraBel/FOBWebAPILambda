package utils;

import dao.dbModels.DynamoDBSection;
import dao.dbModels.DynamoDBUserProject;
import model.Project;
import model.Section;

public class ConstantUtils {

    final public static String ERROR = "ERROR";
    final public static String SUCCESS = "SUCCESS";
    final public static String RESUME_BUCKET = "fob-resume-bucket";

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
        final public static String PROCESS_CHAT_BOT_PROMPT = "processChatBotPrompt";
        final public static String GENERATE_USER_RESUME = "generateUserResume";
    }

    public static class ResumeGenPrompts {
        final public static String GEN_SUMMARY_PROMPT = "Based on the following details, create a professional summary for my resume,"+
        "\nPlease craft a summary that highlights my professional background, key achievements, and skills, suitable for a senior position in %s industry. Just give me summary straight up";
        final public static String GEN_EXPERIENCE_PROMPT = "Here is my professional experience description: %s write this better for me. Just give me the improved description straight up";
        final public static String GEN_PROJECT_PROMPT = "Here is my project description: %s write this better for me. Just give me the improved description straight up";
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