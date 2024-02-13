package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserExperienceDao;
import dao.dbModels.DynamoDBUserExperience;
import model.ResponseMessage;
import model.UpdateExperienceRequest;

import java.util.Optional;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class UpdateUserExperienceLambda {
    private final UserExperienceDao userExperienceDao;

    public UpdateUserExperienceLambda(UserExperienceDao userExperienceDao) {
        this.userExperienceDao = userExperienceDao;
    }

    /*
    Update an experience entity for the specified user in the request
     */

    public ResponseMessage updateExperience(UpdateExperienceRequest request, Context context) {
        final int experienceId = request.getExperienceId();

        final Optional<DynamoDBUserExperience> userExperienceOptional = userExperienceDao.getExperienceEntityForUser(request.getEmail(), experienceId);

        if (!userExperienceOptional.isPresent()) {
            return new ResponseMessage(ERROR, String.format("Experience entity for user with email %s and experience id %d does not exist",
                    request.getEmail(), experienceId));
        }

        final DynamoDBUserExperience dynamoDBUserExperience = userExperienceOptional.get();
        dynamoDBUserExperience.setDescription(request.getJobDescription());
        dynamoDBUserExperience.setRole(request.getRole());
        dynamoDBUserExperience.setCompany_name(request.getCompany());
        dynamoDBUserExperience.setEnd_date(request.getEndDate());
        dynamoDBUserExperience.setStart_date(request.getStartDate());

        userExperienceDao.save(dynamoDBUserExperience);

        return new ResponseMessage(SUCCESS, String.format("Experience entity for user %s with id: %d has been updated", request.getEmail(), experienceId));
    }
}