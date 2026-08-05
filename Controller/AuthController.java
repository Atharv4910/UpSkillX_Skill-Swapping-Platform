package com.upskillx.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.upskillx.keys.Api_key;
import com.upskillx.model.User;

import javafx.scene.control.Alert;

public class AuthController {

     public static User signInWithEmailAndPassword(String email, String password) {
    try {
        String apiKey = new Api_key().getApi_key(); // Replace with your actual API key logic
        URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setDoOutput(true);

        String payload = String.format(
            "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
            email, password
        );

        try (OutputStream os = httpURLConnection.getOutputStream()) {
            os.write(payload.getBytes());
        }

        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 200) {
            // Success
            Scanner scanner = new Scanner(httpURLConnection.getInputStream()).useDelimiter("\\A");
            String response = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            String uid = json.get("localId").getAsString(); // Firebase UID
            String idToken = json.get("idToken").getAsString();
            
            User user = new User(email, uid, idToken);
            return user;

        } else {
            // Failure - Read error stream
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.err.println("Error: " + line);
            }
            return null;
        }

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

    public static String signUp(String email, String password) {

        String errMsg = null;

        try {
            URL url = new URL(
                    "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + new Api_key().getApi_key());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);

            String payload = String.format("{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\" : \"true\"}",
                    email, password);

            OutputStream os = null;
            os = httpURLConnection.getOutputStream();
            os.write(payload.getBytes());

            int responsecode = httpURLConnection.getResponseCode();
            if (responsecode == 200) {
                return errMsg;
            } else {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }

                    try {
                        JSONObject errorJson = new JSONObject(response.toString());
                        String errorMessage = errorJson.getJSONObject("error").getString("message");

                        switch (errorMessage) {
                            case "EMAIL_EXISTS":
                                errMsg = "This email is already registered.";
                                break;
                            case "INVALID_EMAIL":
                                errMsg = "Please enter a valid email address.";
                                break;
                            case "WEAK_PASSWORD":
                            case "WEAK_PASSWORD : Password should be at least 6 characters":
                                errMsg = "Password must be at least 6 characters long.";
                                break;
                            case "OPERATION_NOT_ALLOWED":
                                errMsg = "Password sign-in is disabled.";
                                break;
                            case "TOO_MANY_ATTEMPTS_TRY_LATER":
                                errMsg = "Too many attempts. Please try again later.";
                                break;
                            default:
                                errMsg = "An unknown error occurred: " + errorMessage;
                                break;
                        }

                        return errMsg;

                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Sign-Up Error");
                        alert.setHeaderText(null);
                        alert.setContentText("An unexpected error occurred.");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {

        }
        return errMsg;

    }

}
