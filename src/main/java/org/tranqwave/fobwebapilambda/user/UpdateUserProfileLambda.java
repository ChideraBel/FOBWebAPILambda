package org.tranqwave.fobwebapilambda.user;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

import com.amazonaws.services.lambda.runtime.Context;

import dao.UserProfileDao;
import dao.dbModels.DynamoDBUserProfile;
import model.ResponseMessage;
import model.UpdateUserProfileRequest;

public class UpdateUserProfileLambda {
    private  final UserProfileDao userProfileDao;

    public  UpdateUserProfileLambda(UserProfileDao userProfileDao){
        this.userProfileDao = userProfileDao;
    }
    /*Update a particular user profile details */

    public ResponseMessage updateUserProfile(UpdateUserProfileRequest request, Context context){
        final String userId = request.getEmailString();

        final DynamoDBUserProfile dynamoDBUserProfile = userProfileDao.getUserProfile(userId);
        if(userProfileDao == null){
            return new ResponseMessage(ERROR, String.format(" User profile with email %s does not exist.", userId));
        }
        dynamoDBUserProfile.setAddress(request.getAddress());
        dynamoDBUserProfile.setDate_of_birth(request.getDob());
        dynamoDBUserProfile.setEmployment(request.getEmploymentStatus());
        dynamoDBUserProfile.setIndustry(request.getIndustry());
        dynamoDBUserProfile.setNationality(request.getNationality());
        dynamoDBUserProfile.setVisa_end_date(request.getVisaExpiration());;
        dynamoDBUserProfile.setProfile_picture(request.getProfilePic());

        userProfileDao.save(dynamoDBUserProfile);

        context.getLogger().log(String.format("User profile with email %s has been updated in FOBUserProfileTable"));

        return new ResponseMessage(SUCCESS, String.format("User profile with email %s has been updated.",userId));
    }
}
