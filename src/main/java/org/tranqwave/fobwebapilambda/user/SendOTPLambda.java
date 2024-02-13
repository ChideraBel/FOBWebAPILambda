package org.tranqwave.fobwebapilambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import dao.OTPCodesDao;
import dao.UserDao;
import dao.dbModels.DynamoDBOTPCode;
import dao.dbModels.DynamoDBUser;
import lombok.NonNull;
import model.ResponseMessage;
import model.SendOTPRequest;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;
import static utils.CredentialsUtils.COMPANY_EMAIL;
import static utils.CredentialsUtils.getSecret;

public class SendOTPLambda {
    private final OTPCodesDao otpCodesDao;
    private final UserDao userDao;

    public SendOTPLambda(OTPCodesDao otpCodesDao, UserDao userDao) {
        this.otpCodesDao = otpCodesDao;
        this.userDao = userDao;
    }

    /*
    Generates random OTP code and sends the code to the user's email
     */
    public ResponseMessage sendOTPCode(SendOTPRequest request, Context context) {
        Instant dateInstant = new Date().toInstant();
        String currentDate = DateTimeFormatter.ISO_INSTANT.format(dateInstant);

        final DynamoDBUser user = userDao.getUser(request.getEmail());

        if (user == null) {
            return new ResponseMessage(ERROR, "Account with email does not exist");
        }

        final String randomizedCode = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));

        final boolean response = sendCode(request.getEmail(), randomizedCode, context);

        if (!response) {
            return new ResponseMessage(ERROR, "Your OTP code could not be sent!");
        }
        final DynamoDBOTPCode dynamoDBOTPCode = DynamoDBOTPCode.builder()
                .user_id(request.getEmail())
                .otp_code(randomizedCode)
                .timestamp(currentDate)
                .build();
        otpCodesDao.save(dynamoDBOTPCode);

        return new ResponseMessage(SUCCESS, "Your OTP code has been sent!");
    }

    private boolean sendCode(@NonNull final String userEmail, @NonNull final String code, Context context) {
        Email from = new Email(COMPANY_EMAIL);
        String subject = "Your FOB Registration OTP code";
        Email to = new Email(userEmail);
        Content content = new Content("text/plain", String.format("Please enter your OTP registration code: %s", code));
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(getSecret("sendgrid_api_key"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            context.getLogger().log(response.getBody());
            if(response.getBody().contains("error"))
                return false;
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
}