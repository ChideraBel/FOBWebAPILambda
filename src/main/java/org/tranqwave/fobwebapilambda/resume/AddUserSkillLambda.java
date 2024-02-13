package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserDao;
import dao.UserSkillDao;
import dao.dbModels.DynamoDBUser;
import dao.dbModels.DynamoDBUserSkill;
import model.AddSkillRequest;
import model.ResponseMessage;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class AddUserSkillLambda {
    private final UserSkillDao userSkillDao;
    private final UserDao userDao;

    public AddUserSkillLambda(UserSkillDao userSkillDao, UserDao userDao){
        this.userDao = userDao;
        this.userSkillDao = userSkillDao;
    }

    /*
    Adds a new user skill entity for the specified user in the request
     */
    public ResponseMessage addSkill(AddSkillRequest request, Context context) {
        final DynamoDBUser user = userDao.getUser(request.getEmail());

        if (user == null)
            return new ResponseMessage(ERROR, String.format("User with email %s does not exist", request.getEmail()));

        final int nextSeq = userSkillDao.getNextSequence(request.getEmail());

        final DynamoDBUserSkill userSkill = DynamoDBUserSkill.builder()
                .skill_id(nextSeq)
                .user_id(request.getEmail())
                .skill_name(request.getSkillName())
                .build();

        userSkillDao.save(userSkill);

        //Log action
        context.getLogger().log(String.format("New skill added for user: %s in FOBUserSKillsTable", request.getEmail()));

        return new ResponseMessage(SUCCESS, String.format("Skill entered for user %s", request.getEmail()));
    }
}