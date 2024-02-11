package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserExperienceDao;
import dao.dbModels.DynamoDBUserExperience;
import lombok.NonNull;
import model.Experience;
import model.GetAllUserExperienceRequest;
import model.GetAllUserExperienceResponse;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllUserExperienceLambda {
    private final UserExperienceDao userExperienceDao;

    public GetAllUserExperienceLambda(UserExperienceDao userExperienceDao) {
        this.userExperienceDao = userExperienceDao;
    }

    /*
    Gets all education entity for the specified user in the request
     */
    public GetAllUserExperienceResponse getAllUserExperience(@NonNull final GetAllUserExperienceRequest request, Context context) {
        final List<DynamoDBUserExperience> dynamoDBUserExperienceList = userExperienceDao.getAllUserExperience(request.getEmail());

        if (dynamoDBUserExperienceList.isEmpty())
            throw new IllegalArgumentException(String.format("Cannot find experience details for user: %s", request.getEmail()));

        final List<Experience> experienceList = dynamoDBUserExperienceList.stream().map(x -> toExperience(x)).collect(Collectors.toList());

        return new GetAllUserExperienceResponse(experienceList);
    }

    private Experience toExperience(DynamoDBUserExperience dynamoDBUserExperience) {
        return Experience.builder()
                .company(dynamoDBUserExperience.getCompany_name())
                .role(dynamoDBUserExperience.getRole())
                .jobDescription(dynamoDBUserExperience.getDescription())
                .startDate(dynamoDBUserExperience.getStart_date())
                .endDate(dynamoDBUserExperience.getEnd_date())
                .build();
    }
}