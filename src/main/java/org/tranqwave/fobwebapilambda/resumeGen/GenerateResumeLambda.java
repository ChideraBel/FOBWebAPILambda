package org.tranqwave.fobwebapilambda.resumeGen;

import com.amazonaws.services.lambda.runtime.Context;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import dao.UserDao;
import dao.UserEducationDao;
import dao.UserExperienceDao;
import dao.UserProfileDao;
import dao.UserProjectDao;
import dao.UserSkillDao;
import dao.dbModels.DynamoDBUser;
import dao.dbModels.DynamoDBUserEducation;
import dao.dbModels.DynamoDBUserExperience;
import dao.dbModels.DynamoDBUserProfile;
import dao.dbModels.DynamoDBUserProject;
import dao.dbModels.DynamoDBUserSkill;
import model.GenerateResumeRequest;
import model.ResponseMessage;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import utils.AIUtils;
import utils.ConstantUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.RESUME_BUCKET;
import static utils.ConstantUtils.SUCCESS;

public class GenerateResumeLambda {
    private final UserDao userDao;
    private final UserProfileDao userProfileDao;
    private final UserEducationDao userEducationDao;
    private final UserSkillDao userSkillDao;
    private final UserProjectDao userProjectDao;
    private final UserExperienceDao userExperienceDao;

    public GenerateResumeLambda(UserDao userDao, UserProfileDao userProfileDao, UserEducationDao userEducationDao, UserProjectDao userProjectDao, UserSkillDao userSkillDao, UserExperienceDao userExperienceDao) {
        this.userDao = userDao;
        this.userExperienceDao = userExperienceDao;
        this.userSkillDao = userSkillDao;
        this.userProfileDao = userProfileDao;
        this.userEducationDao = userEducationDao;
        this.userProjectDao = userProjectDao;
    }

    public ResponseMessage generateResume(GenerateResumeRequest request, Context context) {
        final Instant dateInstant = new Date().toInstant();
        final String currentDate = DateTimeFormatter.ISO_INSTANT.format(dateInstant);

        final DynamoDBUser user = userDao.getUser(request.getEmail());

        if (user == null)
            return new ResponseMessage(ERROR, "Account with email does not exist");

        final List<DynamoDBUserEducation> userEducationList = userEducationDao.getAllUserEducation(request.getEmail());
        final List<DynamoDBUserSkill> userSkillList = userSkillDao.getAllUserSkill(request.getEmail());
        final List<DynamoDBUserExperience> userExperienceList = userExperienceDao.getAllUserExperience(request.getEmail());
        final List<DynamoDBUserProject> userProjectList = userProjectDao.getAllUserProjects(request.getEmail());
        final DynamoDBUserProfile userProfile = userProfileDao.getUserProfile(request.getEmail());

        if (userEducationList.isEmpty() && userExperienceList.isEmpty() && userProjectList.isEmpty() && userSkillList.isEmpty()) {
            context.getLogger().log(String.format("Resume information was not found for user with email: %s", request.getEmail()));
            return new ResponseMessage(ERROR, "Oops, no resume information found!");
        }

        String resume_url = "";
        try {
            context.getLogger().log(String.format("Generating resume for user: %s", request.getEmail()));
            final String resumeName = String.format("%s_generated_resume_%s.pdf", request.getEmail(), currentDate);
            resume_url = uploadToS3(RESUME_BUCKET, resumeName, generateResume(userEducationList, userSkillList, userExperienceList, userProjectList, userProfile, user));
        } catch (IOException e) {
            context.getLogger().log(String.format("Unable to generate resume for user: %s with error:", request.getEmail(), e.getMessage()));
            context.getLogger().log(Arrays.toString(e.getStackTrace()));
            return new ResponseMessage(ERROR, e.getMessage());
        }

        context.getLogger().log(String.format("Resume was successfully generated and stored in bucket: % for user: %", resume_url, request.getEmail()));
        return new ResponseMessage(SUCCESS, resume_url);
    }

    private ByteArrayOutputStream generateResume(final List<DynamoDBUserEducation> dynamoDBUserEducations,
                                                 final List<DynamoDBUserSkill> dynamoDBUserSkills,
                                                 final List<DynamoDBUserExperience> dynamoDBUserExperiences,
                                                 final List<DynamoDBUserProject> dynamoDBUserProjects,
                                                 final DynamoDBUserProfile dynamoDBUserProfile,
                                                 final DynamoDBUser dynamoDBUser) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PdfWriter writer = new PdfWriter(baos);
        final PdfDocument pdf = new PdfDocument(writer);
        final Document document = new Document(pdf);

        // Personal Info
        final Paragraph name = new Paragraph(dynamoDBUser.getFullname())
                .setTextAlignment(TextAlignment.CENTER)
                .setBold();
        final Paragraph contact = new Paragraph(String.format("Email: %s | Phone: %s", dynamoDBUser.getUser_id(), dynamoDBUserProfile.getPhone_number()))
                .setTextAlignment(TextAlignment.CENTER);
        document.add(name);
        document.add(contact);

        document.add(new Paragraph("\n"));

        generateSummary(document, dynamoDBUserEducations, dynamoDBUserSkills, dynamoDBUserExperiences, dynamoDBUserProjects, dynamoDBUserProfile.getIndustry());
        if (!dynamoDBUserExperiences.isEmpty()) generateExperience(document, dynamoDBUserExperiences);
        if (!dynamoDBUserProjects.isEmpty()) generateProject(document, dynamoDBUserProjects);
        if (!dynamoDBUserEducations.isEmpty()) generateEducation(document, dynamoDBUserEducations);
        if (!dynamoDBUserSkills.isEmpty()) generateSkills(document, dynamoDBUserSkills);

        document.close();
        return baos;
    }

    //TODO: Investigate proper integration with open API, right now just using the gpt prompts and responses
    private void generateSummary(final Document doc,
                                 final List<DynamoDBUserEducation> dynamoDBUserEducations,
                                 final List<DynamoDBUserSkill> dynamoDBUserSkills,
                                 final List<DynamoDBUserExperience> dynamoDBUserExperiences,
                                 final List<DynamoDBUserProject> dynamoDBUserProjects,
                                 final String industry) {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format(ConstantUtils.ResumeGenPrompts.GEN_SUMMARY_PROMPT, industry));

        sb.append("Education: \n");
        for (DynamoDBUserEducation education : dynamoDBUserEducations) {
            sb.append(education.getDegree_type() + "," + education.getMajor() + "," + education.getInstitution_name() + "," + education.getStart_date() + "," + "-" + "," + education.getEnd_date() + "\n");
        }

        sb.append("Experience: \n");
        for (DynamoDBUserExperience experience : dynamoDBUserExperiences) {
            sb.append(experience.getRole() + "," + experience.getCompany_name() + "," + experience.getStart_date() + "," + "-" + "," + experience.getEnd_date() + experience.getDescription() + "\n");
        }

        sb.append("Projects: \n");
        for (DynamoDBUserProject project : dynamoDBUserProjects) {
            sb.append(project.getProject_title() + "," + project.getDescription() + "\n");
        }

        sb.append("Skills: \n");
        for (DynamoDBUserSkill skill : dynamoDBUserSkills) {
            sb.append(skill.getSkill_name());
        }

        final String summaryText = AIUtils.processPrompt(sb.toString());

        final Paragraph summaryHeading = new Paragraph("Professional Summary")
                .setBold();
        final Paragraph summary = new Paragraph(summaryText);
        doc.add(summaryHeading);
        doc.add(summary);
    }

    private void generateExperience(Document doc, final List<DynamoDBUserExperience> dynamoDBUserExperiences) {
        final Paragraph experienceHeading = new Paragraph("Professional Experience")
                .setBold();
        doc.add(experienceHeading);

        for (DynamoDBUserExperience experience : dynamoDBUserExperiences) {
            final Paragraph experience1 = new Paragraph(String.format("%s at %, %s-%s",
                    experience.getRole(), experience.getCompany_name(), experience.getStart_date(), experience.getEnd_date()))
                    .setItalic()
                    .add("\n" + AIUtils.processPrompt(String.format(ConstantUtils.ResumeGenPrompts.GEN_EXPERIENCE_PROMPT, experience.getDescription())));
            doc.add(experience1);
        }
    }

    private void generateProject(Document doc, final List<DynamoDBUserProject> dynamoDBUserProjects) {
        final Paragraph projectHeading = new Paragraph("Projects")
                .setBold();
        doc.add(projectHeading);
        com.itextpdf.layout.element.List projects = new com.itextpdf.layout.element.List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");
        for (DynamoDBUserProject project : dynamoDBUserProjects) {
            projects.add(project.getProject_title() + ": " + AIUtils.processPrompt(String.format(ConstantUtils.ResumeGenPrompts.GEN_PROJECT_PROMPT, project.getDescription())) +
                    String.format("(%s-%s)", project.getStart_date(), project.getEnd_date()));
        }
        doc.add(projectHeading);
        doc.add(projects);
    }

    private void generateEducation(Document doc, final List<DynamoDBUserEducation> dynamoDBUserEducations) {
        final Paragraph experienceHeading = new Paragraph("Education")
                .setBold();
        doc.add(experienceHeading);

        for (DynamoDBUserEducation education : dynamoDBUserEducations) {
            final Paragraph education1 = new Paragraph(String.format("%s %s-%s, %s", education.getInstitution_name(), education.getStart_date(), education.getEnd_date(), education.getLocation()))
                    .setItalic()
                    .add("\n" + education.getDegree_type() + " in " + education.getMajor());
            doc.add(education1);
        }
    }

    private void generateSkills(Document doc, final List<DynamoDBUserSkill> dynamoDBUserSkills) {
        final Paragraph experienceHeading = new Paragraph("Skills")
                .setBold();
        doc.add(experienceHeading);

        final StringBuilder sb = new StringBuilder();
        for (DynamoDBUserSkill skill : dynamoDBUserSkills) {
            sb.append(skill.getSkill_name() + ", ");
        }
        final int endIdx = sb.lastIndexOf(",");
        final Paragraph skill1 = new Paragraph(sb.toString().substring(0, endIdx));
        doc.add(skill1);
    }

    private String uploadToS3(String bucketName, String fileKey, ByteArrayOutputStream baos) {
        S3Client s3 = S3Client.builder()
                .region(Region.US_EAST_2) // Specify the region
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();

        s3.putObject(objectRequest, RequestBody.fromBytes(baos.toByteArray()));
        return "https://" + bucketName + ".s3." + Region.US_EAST_2.toString() + ".amazonaws.com/" + fileKey;
    }
}