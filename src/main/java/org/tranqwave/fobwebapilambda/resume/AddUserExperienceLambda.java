package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserDao;
import dao.UserExperienceDao;
import dao.dbModels.DynamoDBUser;
import dao.dbModels.DynamoDBUserExperience;
import model.AddExperienceRequest;
import model.ResponseMessage;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class AddUserExperienceLambda {
    private final UserExperienceDao userExperienceDao;
    private final UserDao userDao;

    public AddUserExperienceLambda(UserExperienceDao userExperienceDao, UserDao userDao) {
        this.userDao = userDao;
        this.userExperienceDao = userExperienceDao;
    }

    /*
    Adds a new user experience entity for the specified user in the request
     */

    public ResponseMessage addExperience(AddExperienceRequest request, Context context) {
        final DynamoDBUser user = userDao.getUser(request.getEmail());

        if (user == null)
            return new ResponseMessage(ERROR, String.format("User with email %s does not exist", request.getEmail()));

        final int nextSeq = userExperienceDao.getNextSequence(request.getEmail());

        final DynamoDBUserExperience userExperience = DynamoDBUserExperience.builder()
                .user_id(request.getEmail())
                .company_name(request.getCompany())
                .experience_id(nextSeq)
                .role(request.getRole())
                .start_date(request.getStartDate())
                .end_date(request.getEndDate())
                .description(request.getJobDescription())
                .build();

        userExperienceDao.save(userExperience);

        return new ResponseMessage(SUCCESS, String.format("Experience entered for user %s", request.getEmail()));
    }
}