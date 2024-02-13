package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserSkillDao;
import dao.dbModels.DynamoDBUserSkill;
import model.DeleteSkillRequest;
import model.ResponseMessage;

import java.util.Optional;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class DeleteUserSkillLambda {
    private final UserSkillDao userSkillDao;

    public DeleteUserSkillLambda(UserSkillDao userSkillDao) {
        this.userSkillDao = userSkillDao;
    }

    /*
    Deletes a skill entity for the specified user in the request
     */
    public ResponseMessage deleteSkill(DeleteSkillRequest request, Context context) {
        int skillId = request.getSkillId();

        final Optional<DynamoDBUserSkill> userSkillOptional = userSkillDao
                .getSkillEntityForUser(request.getEmail(), skillId);

        if (!userSkillOptional.isPresent()) {
            return new ResponseMessage(ERROR, String.format("Skill entity with id: %d for user with email %s",
                    skillId, request.getEmail()));
        }

        userSkillDao.deleteSkillForUser(userSkillOptional.get());

        //Log action
        context.getLogger().log(String.format("Deleted skill with id: %d for user: %s from FOBUserSKillsTable", skillId, request.getEmail()));

        return new ResponseMessage(SUCCESS, String.format("Skill entity for user %s with id: %d has been deleted", request.getEmail(), skillId));
    }
}