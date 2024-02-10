package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserEducationDao;
import dao.dbModels.DynamoDBUserEducation;
import model.ResponseMessage;
import model.UpdateEducationRequest;

import java.util.Optional;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class UpdateUserEducationLambda {
    private final UserEducationDao userEducationDao;

    public UpdateUserEducationLambda(UserEducationDao userEducationDao) {
        this.userEducationDao = userEducationDao;
    }

    /*
    Update an education entity for the specified user in the request
     */

    public ResponseMessage updateEducation(UpdateEducationRequest request, Context context) {
        int educationId = request.getEducationId();

        final Optional<DynamoDBUserEducation> userEducationOptional = userEducationDao.getEducationEntityForUser(request.getEmail(), educationId);

        if (!userEducationOptional.isPresent()) {
            return new ResponseMessage(ERROR, String.format("Education entity for user with email %s and education id %n does not exist",
                    request.getEmail(), educationId));
        }

        final DynamoDBUserEducation dynamoDBUserEducation = userEducationOptional.get();
        dynamoDBUserEducation.setGpa(request.getGpa());
        dynamoDBUserEducation.setMajor(request.getMajor());
        dynamoDBUserEducation.setDegree_type(request.getDegreeType());
        dynamoDBUserEducation.setEnd_date(request.getEndDate());
        dynamoDBUserEducation.setStart_date(request.getStartDate());
        dynamoDBUserEducation.setInstitution_name(request.getInstitution());

        userEducationDao.save(dynamoDBUserEducation);

        return new ResponseMessage(SUCCESS, String.format("Education entity for user %s with id: %n has been updated", request.getEmail(), educationId));
    }
}