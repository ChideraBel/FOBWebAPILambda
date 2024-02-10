package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserEducationDao;
import dao.dbModels.DynamoDBUserEducation;
import model.DeleteEducationRequest;
import model.ResponseMessage;

import java.util.Optional;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class DeleteUserEducationLambda {
    private final UserEducationDao userEducationDao;

    public DeleteUserEducationLambda(UserEducationDao userEducationDao) {
        this.userEducationDao = userEducationDao;
    }

    /*
    Deletes an education entity for the specified user in the request
     */
    public ResponseMessage deleteEducation(DeleteEducationRequest request, Context context) {
        int educationId = request.getEducationId();

        final Optional<DynamoDBUserEducation> userEducationOptional = userEducationDao.getEducationEntityForUser(request.getEmail(), educationId);

        if (!userEducationOptional.isPresent()) {
            return new ResponseMessage(ERROR, String.format("Education entity for user with email %s and education id %n does not exist",
                    request.getEmail(), educationId));
        }

        userEducationDao.deleteEducationEntityForUser(userEducationOptional.get());

        return new ResponseMessage(SUCCESS, String.format("Education entity for user %s with id: %n has been deleted", request.getEmail(), educationId));
    }
}