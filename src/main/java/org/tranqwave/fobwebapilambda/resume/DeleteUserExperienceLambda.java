package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserExperienceDao;
import dao.dbModels.DynamoDBUserExperience;
import model.DeleteExperienceRequest;
import model.ResponseMessage;

import java.util.Optional;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class DeleteUserExperienceLambda {
    private final UserExperienceDao userExperienceDao;

    public DeleteUserExperienceLambda(UserExperienceDao userExperienceDao) {
        this.userExperienceDao = userExperienceDao;
    }

    /*
    Deletes an experience entity for the specified user in the request
     */
    public ResponseMessage deleteExperience(DeleteExperienceRequest request, Context contecxt) {
        int experienceId = request.getExperienceId();

        final Optional<DynamoDBUserExperience> userExperienceOptional = userExperienceDao
                .getExperienceEntityForUser(request.getEmail(), experienceId);

        if (!userExperienceOptional.isPresent()) {
            return new ResponseMessage(ERROR, String.format("Experience entity with id: %n for user with email %s",
                    experienceId, request.getEmail()));
        }

        userExperienceDao.deleteEducationEntityForUser(userExperienceOptional.get());

        return new ResponseMessage(SUCCESS, String.format("Experience entity for user %s with id: %n has been deleted", request.getEmail(), experienceId));
    }
}