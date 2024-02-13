package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserProjectDao;
import dao.dbModels.DynamoDBUserProject;
import model.DeleteProjectRequest;
import model.ResponseMessage;

import java.util.Optional;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class DeleteUserProjectLambda {
    private final UserProjectDao userProjectDao;

    public DeleteUserProjectLambda(UserProjectDao userProjectDao) {this.userProjectDao = userProjectDao;}

    /*
    Deletes a project entity for the specified user in the request
     */
    public ResponseMessage deleteProject(DeleteProjectRequest request, Context context) {
        int projectId = request.getProjectId();

        final Optional<DynamoDBUserProject> userProjectOptional = userProjectDao.getProjectEntityForUser(request.getEmail(), projectId);

        if(!userProjectOptional.isPresent()) {
            return new ResponseMessage(ERROR, String.format("Project entity for user with email %s and project id %d does not exist",
                    request.getEmail(), projectId));
        }

        userProjectDao.deleteProjectEntityForUser(userProjectOptional.get());

        context.getLogger().log(String.format("Project with id %d for user %s has been deleted from FOBUserProjectsTable"));

        return new ResponseMessage(SUCCESS, String.format("Project entity for user %s with id: %d has been deleted", request.getEmail(), projectId));
    }
}