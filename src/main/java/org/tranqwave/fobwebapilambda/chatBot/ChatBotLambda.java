package org.tranqwave.fobwebapilambda.chatBot;

import com.amazonaws.services.lambda.runtime.Context;
import dao.UserDao;
import model.ChatBotRequest;
import model.ResponseMessage;
import utils.AIUtils;

import static utils.ConstantUtils.ERROR;
import static utils.ConstantUtils.SUCCESS;

public class ChatBotLambda {
    private final UserDao userDao;

    public ChatBotLambda(UserDao userDao) {
        this.userDao = userDao;
    }

    public ResponseMessage processChatBotRequest(ChatBotRequest request, Context context) {

        if(userDao.getUser(request.getEmail()) == null) {
            context.getLogger().log(String.format("Prompt: %s was posed to chat bot by unknown user: %s", request.getUserPrompt(), request.getEmail()));
            return new ResponseMessage(ERROR, String.format("User with email %s does not exist", request.getEmail()));
        }

        final String response = AIUtils.processPrompt(request.getUserPrompt());
        context.getLogger().log(String.format("Prompt: %s was posed to chat bot by user: %s", request.getUserPrompt(), request.getEmail()));

        return new ResponseMessage(SUCCESS, response);
    }
}