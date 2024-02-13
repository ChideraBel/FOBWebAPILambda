package org.tranqwave.fobwebapilambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import dao.OTPCodesDao;
import dao.dbModels.DynamoDBOTPCode;
import model.OTPVerificationRequest;
import model.ResponseMessage;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class OTPVerificationLambda {
    private final OTPCodesDao otpCodesDao;

    public OTPVerificationLambda(OTPCodesDao otpCodesDao) {
        this.otpCodesDao = otpCodesDao;
    }

    /*
    Verifies that the code entered by the user is the same as the generated OTP code
     */
    public ResponseMessage verifyOTPCode(OTPVerificationRequest request, Context context) {

        final DynamoDBOTPCode dynamoDBOTPCode = otpCodesDao.getLatestOTPCode(request.getEmail());

        if(dynamoDBOTPCode == null)
            return new ResponseMessage(ERROR, String.format("Cannot find OTP Code for user %s", request.getEmail()));

        String code = dynamoDBOTPCode.getOtp_code();

        if(!request.getEnteredOTP().equals(code))
            return new ResponseMessage(ERROR, "Code entered does not match OTP code");

        return new ResponseMessage(SUCCESS, "Your OTP code is verified!");
    }
}