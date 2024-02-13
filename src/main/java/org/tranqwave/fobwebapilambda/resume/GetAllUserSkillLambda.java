package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserSkillDao;
import dao.dbModels.DynamoDBUserSkill;
import lombok.NonNull;
import model.GetAllUserSkillRequest;
import model.GetAllUserSkillResponse;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllUserSkillLambda {
    private final UserSkillDao userSkillDao;

    public GetAllUserSkillLambda(UserSkillDao userSkillDao) {
        this.userSkillDao = userSkillDao;
    }

     /*
    Gets all skill entity for the specified user in the request
     */
    public GetAllUserSkillResponse getAllUserSkill(@NonNull final GetAllUserSkillRequest request, Context context) {
        final List<DynamoDBUserSkill> dynamoDBUserSkillList = userSkillDao.getAllUserSkill(request.getEmail());

        if (dynamoDBUserSkillList.isEmpty())
            throw new IllegalArgumentException(String.format("Cannot find skill details for user: %s", request.getEmail()));

        final List<String> skillList = dynamoDBUserSkillList.stream().map(x -> x.getSkill_name()).collect(Collectors.toList());

        return new GetAllUserSkillResponse(skillList);
    }
}