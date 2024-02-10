package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserEducationDao;
import dao.dbModels.DynamoDBUserEducation;
import model.Education;
import model.GetAllUserEducationRequest;
import model.GetAllUserEducationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllUserEducationLambda {
    private final UserEducationDao userEducationDao;

    public GetAllUserEducationLambda(UserEducationDao userEducationDao){
        this.userEducationDao = userEducationDao;
    }

    /*
    Gets all education entity for the specified user in the request
     */
    public GetAllUserEducationResponse getAllUserEducation(GetAllUserEducationRequest request, Context context){
        final List<DynamoDBUserEducation> dynamoDBUserEducations = userEducationDao.getAllEducationEntitiesForUser(request.getEmail());

        if(dynamoDBUserEducations.isEmpty())
            throw new IllegalArgumentException(String.format("Cannot find education details for user: %s", request.getEmail()));

        final List<Education> educationList = dynamoDBUserEducations.stream().map(x->toEducation(x)).collect(Collectors.toList());

       return new GetAllUserEducationResponse(educationList);
    }

    private Education toEducation(DynamoDBUserEducation dynamoDBUserEducation){
        return Education.builder()
                .gpa(dynamoDBUserEducation.getGpa())
                .major(dynamoDBUserEducation.getMajor())
                .degree_type(dynamoDBUserEducation.getDegree_type())
                .institution_name(dynamoDBUserEducation.getInstitution_name())
                .start_date(dynamoDBUserEducation.getStart_date())
                .end_date(dynamoDBUserEducation.getEnd_date())
                .build();
    }
}