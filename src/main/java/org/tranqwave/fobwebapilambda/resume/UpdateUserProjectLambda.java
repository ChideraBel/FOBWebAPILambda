package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserProjectDao;
import dao.dbModels.DynamoDBUserProject;
import model.ResponseMessage;
import model.UpdateProjectRequest;

import java.util.Optional;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class UpdateUserProjectLambda {
    private final UserProjectDao userProjectDao;

    public UpdateUserProjectLambda(UserProjectDao userProjectDao) {
        this.userProjectDao = userProjectDao;
    }

    /*
    Update a project entity for the specified user in the request
     */
    public ResponseMessage updateProject(UpdateProjectRequest request, Context context) {
        final int projectId = request.getProjectId();

        final Optional<DynamoDBUserProject> userProjectOptional = userProjectDao.getProjectEntityForUser(request.getEmail(), projectId);

        if (!userProjectOptional.isPresent()) {
            return new ResponseMessage(ERROR, String.format("Project entity for user with email %s and project id %d does not exist",
                    request.getEmail(), projectId));
        }

        final DynamoDBUserProject dynamoDBUserProject = userProjectOptional.get();
        dynamoDBUserProject.setProject_title(request.getProjectName());
        dynamoDBUserProject.setRole(request.getRole());
        dynamoDBUserProject.setDescription(request.getProjectDescription());
        dynamoDBUserProject.setEnd_date(request.getEndDate());
        dynamoDBUserProject.setStart_date(request.getStartDate());
        dynamoDBUserProject.setTechnologies_used(request.getTechUsed());

        userProjectDao.save(dynamoDBUserProject);

        context.getLogger().log(String.format("Project with id %d for user %s has been updated in FOBUserProjectsTable"));

        return new ResponseMessage(SUCCESS, String.format("Project entity for user %s with id: %d has been updated", request.getEmail(), projectId));
    }
}