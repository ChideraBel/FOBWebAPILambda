package utils;

import dao.dbModels.DynamoDBSection;
import dao.dbModels.DynamoDBUserProject;
import model.Project;
import model.Section;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConstantUtils {

    final public static String ERROR = "ERROR";
    final public static String SUCCESS = "SUCCESS";
    final public static String RESUME_BUCKET = "fob-resume-bucket";
    final public static String OPEN_AI_CHAT_URL = "https://api.openai.com/v1/chat/completions";
    final public static String OPEN_AI_MODEL = "gpt-3.5-turbo";
    final public static String MONTH_YEAR_FORMAT = "MM/yyyy";

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
    public static String convertISOToMonthYear(String isoDateString){
        try {
            DateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = isoDateFormat.parse(isoDateString);

            DateFormat monthYearFormat = new SimpleDateFormat(MONTH_YEAR_FORMAT);
            return monthYearFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }
}