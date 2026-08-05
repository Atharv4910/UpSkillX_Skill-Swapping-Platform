package com.upskillx.Controller;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.upskillx.keys.Api_key;

public class QuizGenerator {


    public static String generateQuiz(String category, String skilllevel) {
        try {
            URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + new Api_key().getGemini_apiKey());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Prompt (safe for JSON)
            String prompt = "Generate a JSON array with keys: \"question\", \"options\", and \"answer\". " +
                    "Do not include markdown formatting, code blocks, or explanations. " +
                    "Output only valid raw JSON. No ```json or ``` at all. Give 10 Questions of "+ skilllevel+ "Level " +
                    "Category: " + category;

            String requestBody = "{\n" +
                    "  \"contents\": [{\n" +
                    "    \"parts\": [{\"text\": \"" + prompt.replace("\"", "\\\"") + "\"}]\n" +
                    "  }]\n" +
                    "}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.getBytes());
            }

            Scanner sc = new Scanner(conn.getInputStream());
            StringBuilder response = new StringBuilder();
            while (sc.hasNext()) {
                response.append(sc.nextLine());
            }
            sc.close();

            // Extract the actual text
            String json = new org.json.JSONObject(response.toString())
                    .getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");

            return json.trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "[]"; // fallback
        }
    }
}
