package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserSkillDao;
import dao.dbModels.DynamoDBUserSkill;
import model.ResponseMessage;
import model.UpdateSkillRequest;

import java.util.Optional;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class UpdateUserSkillLambda {
    final UserSkillDao userSkillDao;

    public UpdateUserSkillLambda(UserSkillDao userSkillDao) {
        this.userSkillDao = userSkillDao;
    }

    /*
    Update a skill entity for the specified user in the request
     */
    public ResponseMessage updateSkill(UpdateSkillRequest request, Context context) {
        final int skillId = request.getSkillId();

        final Optional<DynamoDBUserSkill> userSkillOptional = userSkillDao.getSkillEntityForUser(request.getEmail(), skillId);

        if (!userSkillOptional.isPresent()) {
            return new ResponseMessage(ERROR, String.format("Skill entity for user with email %s and experience id %d does not exist",
                    request.getEmail(), skillId));
        }

        final DynamoDBUserSkill dynamoDBUserSkill = userSkillOptional.get();
        dynamoDBUserSkill.setSkill_name(request.getSkillName());

        userSkillDao.save(dynamoDBUserSkill);

        //Log action
        context.getLogger().log(String.format("Skill with id: %d for user: %s was updated in FOBUserSKillsTable", skillId, request.getEmail()));

        return new ResponseMessage(SUCCESS, String.format("Skill entity for user %s with id: %d has been updated", request.getEmail(), skillId));
    }
}