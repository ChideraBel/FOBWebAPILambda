package utils;

import lombok.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static utils.ConstantUtils.OPEN_AI_CHAT_URL;
import static utils.ConstantUtils.OPEN_AI_MODEL;
import java.util.logging.Logger;

public class AIUtils {
    private static final Logger LOGGER = Logger.getLogger(AIUtils.class.getName());

    public static class ResumeGenPrompts {
        final public static String GEN_SUMMARY_PROMPT = "Based on the following details, create an impressive professional summary in only 3 sentences for my resume. " +
                "This summary should highlight why I am suitable for an entry level position in %s industry. " +
                "Speak in first person, and just give me the summary on its own";
        final public static String GEN_EXPERIENCE_PROMPT = "Here is my professional experience description: %s Write this better in one single paragraph (no more than one) without " +
                "personal pronouns for my resume, with action words and specific metric details. " +
                "Speak in first person, don't use any other symbols and just give me the improved description its own";
        final public static String GEN_PROJECT_PROMPT = "Here is my project description: %s Write this better for me with no new lines, for my resume. Be as specific and impressive as you can. " +
                "Speak in first person, and just give me the improved description its own";
    }

    private static String callGPT(String prompt) {
        String url = OPEN_AI_CHAT_URL;
        String apiKey = CredentialsUtils.getSecret("open_ai_api_key");
        String model = OPEN_AI_MODEL;

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The Open AI api request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from gt turbo
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // Logging a debug message
            LOGGER.info("Received response from OpenAI API: " + response.toString());

            // calls the method to extract the message.
            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            // Logging an error message
            LOGGER.severe("Error making request to OpenAI API: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);
    }

    public static String processPrompt(@NonNull final String prompt) {

        return callGPT(prompt);
    }
}