package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserEducationDao;
import dao.dbModels.DynamoDBUserEducation;
import lombok.NonNull;
import model.Education;
import model.GetAllUserEducationRequest;
import model.GetAllUserEducationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllUserEducationLambda {
    private final UserEducationDao userEducationDao;

    public GetAllUserEducationLambda(UserEducationDao userEducationDao) {
        this.userEducationDao = userEducationDao;
    }

    /*
    Gets all education entities for the specified user in the request
     */
    public GetAllUserEducationResponse getAllUserEducation(@NonNull final GetAllUserEducationRequest request, Context context) {
        final List<DynamoDBUserEducation> dynamoDBUserEducations = userEducationDao.getAllUserEducation(request.getEmail());

        if (dynamoDBUserEducations.isEmpty())
            throw new IllegalArgumentException(String.format("Cannot find education details for user: %s", request.getEmail()));

        final List<Education> educationList = dynamoDBUserEducations.stream().map(x -> toEducation(x)).collect(Collectors.toList());

        //Log action
        context.getLogger().log(String.format("Retrieved all %d education for user %s from FOBUserEducationTable", educationList.size()));

        return new GetAllUserEducationResponse(educationList);
    }

    private Education toEducation(DynamoDBUserEducation dynamoDBUserEducation) {
        return Education.builder()
                .gpa(dynamoDBUserEducation.getGpa())
                .major(dynamoDBUserEducation.getMajor())
                .degreeType(dynamoDBUserEducation.getDegree_type())
                .institution(dynamoDBUserEducation.getInstitution_name())
                .startDate(dynamoDBUserEducation.getStart_date())
                .endDate(dynamoDBUserEducation.getEnd_date())
                .location(dynamoDBUserEducation.getLocation())
                .build();
    }
}