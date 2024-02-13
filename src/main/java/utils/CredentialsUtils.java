package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class CredentialsUtils {

    final public static String COMPANY_EMAIL = "tranqwave@gmail.com";
    public static String getSecret(String secretName) {
        Region region = Region.of("us-east-2");

        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw e;
        }

        Gson gson = new Gson();

        final JsonObject jsonObject = gson.fromJson(getSecretValueResponse.secretString(), JsonObject.class);
        final String apiKey = jsonObject.get(secretName).getAsString();

        return apiKey;
    }
}