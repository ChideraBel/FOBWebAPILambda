package org.tranqwave.fobwebapilambda.resume;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserProjectDao;
import dao.dbModels.DynamoDBUserProject;
import model.GetAllUserProjectRequest;
import model.GetAllUserProjectResponse;
import model.Project;

import java.util.List;
import java.util.stream.Collectors;

import static utils.ConstantUtils.toProject;

public class GetAllUserProjectLambda {
    private final UserProjectDao userProjectDao;

    public GetAllUserProjectLambda(UserProjectDao userProjectDao) {
        this.userProjectDao = userProjectDao;
    }

    /*
    Gets all project entities for the specified user in the request
    */
    public GetAllUserProjectResponse getAllProjects(GetAllUserProjectRequest request, Context context) {
        final List<DynamoDBUserProject> dynamoDBUserProjects = userProjectDao.getAllUserProjects(request.getEmail());

        if (dynamoDBUserProjects.isEmpty())
            throw new IllegalArgumentException(String.format("Cannot find project details for user: %s", request.getEmail()));

        final List<Project> projectList = dynamoDBUserProjects.stream().map(x -> toProject(x)).collect(Collectors.toList());

        //Log action
        context.getLogger().log(String.format("Retrieved all %d projects for user %s from FOBUserProjectsTable", projectList.size()));

        return new GetAllUserProjectResponse(projectList);
    }
}