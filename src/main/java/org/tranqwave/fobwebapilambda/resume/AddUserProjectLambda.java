package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserDao;
import dao.UserProjectDao;
import dao.dbModels.DynamoDBUser;
import dao.dbModels.DynamoDBUserProject;
import model.AddProjectRequest;
import model.ResponseMessage;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class AddUserProjectLambda {
    private final UserProjectDao userProjectDao;
    private final UserDao userDao;

    public AddUserProjectLambda(UserProjectDao userProjectDao, UserDao userDao) {
        this.userProjectDao = userProjectDao;
        this.userDao = userDao;
    }

    /*
    Adds a new project entity for the specified user in the request
     */
    public ResponseMessage addProject(AddProjectRequest request, Context context) {
        final DynamoDBUser user = userDao.getUser(request.getEmail());

        if(user == null)
            return new ResponseMessage(ERROR, String.format("User with email %s does not exist", request.getEmail()));

        final int nextSeq = userProjectDao.getNextSequence(request.getEmail());

        final DynamoDBUserProject userProject = DynamoDBUserProject.builder()
                .user_id(request.getEmail())
                .project_id(nextSeq)
                .project_title(request.getProjectName())
                .description(request.getProjectDescription())
                .start_date(request.getStartDate())
                .end_date(request.getEndDate())
                .role(request.getRole())
                .technologies_used(request.getTechUsed())
                .build();

        userProjectDao.save(userProject);

        context.getLogger().log(String.format("New project added for user %s to FOBUserProjectsTable"));

        return new ResponseMessage(SUCCESS, String.format("Project entered for user %s", request.getEmail()));
    }
}