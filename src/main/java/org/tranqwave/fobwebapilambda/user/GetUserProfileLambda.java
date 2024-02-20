package org.tranqwave.fobwebapilambda.user;


import com.amazonaws.services.lambda.runtime.Context;
import dao.UserProfileDao;
import dao.dbModels.DynamoDBUserProfile;
import lombok.NonNull;
import model.GetUserProfileResponse;
import model.GetUserProfileRequest;
import model.UserDetails;

public class GetUserProfileLambda {
    private final UserProfileDao userProfileDao;

    public GetUserProfileLambda(UserProfileDao userProfileDao){
        this.userProfileDao = userProfileDao;
    }


    public GetUserProfileResponse getUserProfile(@NonNull final GetUserProfileRequest request, Context context) {
        final DynamoDBUserProfile dynamoDBUserProfile = userProfileDao.getUserProfile(request.getEmail());
        
        if (dynamoDBUserProfile == null) {
            throw new IllegalArgumentException("No user profile found for email: " + request.getEmail());
        }

        final UserDetails userDetails = toDetails(dynamoDBUserProfile);
        
        // Assuming GetUserProfile can accept a DynamoDBUserProfile in its constructor or has a method to set it
        return new GetUserProfileResponse(userDetails);

    }

    private UserDetails toDetails(DynamoDBUserProfile dynamoDBUserProfile){
        return UserDetails.builder()
                .emailString(dynamoDBUserProfile.getUser_id())
                .address(dynamoDBUserProfile.getAddress())
                .dob(dynamoDBUserProfile.getDate_of_birth())
                .employmentStatus(dynamoDBUserProfile.getEmployment())
                .industry(dynamoDBUserProfile.getIndustry())
                .nationality(dynamoDBUserProfile.getNationality())
                .profilePic(dynamoDBUserProfile.getProfile_picture())
                .visaExpiration(dynamoDBUserProfile.getVisa_end_date())
                .build();
    }
}