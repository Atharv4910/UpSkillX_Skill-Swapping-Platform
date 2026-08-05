package com.upskillx.Controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.upskillx.keys.Api_key;

public class ApiController {

   public JSONArray getNews(String category) {
    int page = 1;
    category = (category == null || category.isEmpty()) ? "science" : category;

    try {

        String urlStr = "https://newsapi.org/v2/top-headlines?q="+category+"&page=1&apiKey="+new Api_key().getNews_apikey()+"&pageSize=10";
        // String urlStr = "https://newsapi.org/v2/top-headlines?category=" + category
        //         + "&page=" + page
        //         + "&pageSize=10"
        //         + "&apiKey="+new Api_key().getApi_key();

        System.out.println("Fetching news from: " + urlStr);

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        JSONObject json = new JSONObject(response.toString());
        System.out.println("News API response: " + json);

        return json.getJSONArray("articles");

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

}
