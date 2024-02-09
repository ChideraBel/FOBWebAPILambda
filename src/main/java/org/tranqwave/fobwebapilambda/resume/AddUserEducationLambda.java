package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserDao;
import dao.UserEducationDao;
import dao.dbModels.DynamoDBUser;
import dao.dbModels.DynamoDBUserEducation;
import model.AddEducationRequest;
import model.ResponseMessage;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class AddUserEducationLambda {
    private final UserEducationDao userEducationDao;
    private final UserDao userDao;

    public AddUserEducationLambda(UserEducationDao userEducationDao, UserDao userDao) {
        this.userEducationDao = userEducationDao;
        this.userDao = userDao;
    }

    /*
    Adds a new education entity for the specified user in the request
     */
    public ResponseMessage addEducation(AddEducationRequest request, Context context) {
        final DynamoDBUser user = userDao.getUser(request.getEmail());

        if (user == null)
            return new ResponseMessage(ERROR, String.format("User with email %s does not exist", request.getEmail()));

        final int nextSeq = userEducationDao.getNextSequence(request.getEmail());

        final DynamoDBUserEducation userEducation = DynamoDBUserEducation.builder()
                .user_id(request.getEmail())
                .education_id(nextSeq)
                .institution_name(request.getInstitution())
                .gpa(request.getGpa())
                .major(request.getMajor())
                .degree_type(request.getDegreeType())
                .start_date(request.getStartDate())
                .end_date(request.getEndDate())
                .build();

        userEducationDao.save(userEducation);

        return new ResponseMessage(SUCCESS, String.format("Education entered for user %s", request.getEmail()));
    }
}
