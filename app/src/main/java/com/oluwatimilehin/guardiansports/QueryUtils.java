package com.oluwatimilehin.guardiansports;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by timad on 25/12/2016.
 */

public class QueryUtils {


    public static ArrayList<News> extractNews(String url) {

        URL mUrl = createURl(url);

        ArrayList<News> news = new ArrayList<>();

        try {
            JSONObject parser = new JSONObject(makeNetworkRequest(mUrl));
            JSONObject objParser = parser.getJSONObject("response");
            JSONArray results = objParser.getJSONArray("results");
            for(int i = 0; i < results.length(); i ++){
                JSONObject parse = results.getJSONObject(i);
                String headline = parse.getString("webTitle");
                String objectUrl = parse.getString("webUrl");
                String date = parse.getString("webPublicationDate");

                news.add(new News(headline, date, objectUrl));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("QueryUtils", "Problem parsing the JSOn");
        }
        return  news;
    }

    public static URL createURl(String url) {
        URL mUrl = null;
        try {
            mUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return mUrl;
    }

    public static String makeNetworkRequest(URL mUrl) {

        HttpURLConnection urlConnection;
        String jsonResponse = " ";
        InputStream inputStream;

        try {
            urlConnection = (HttpURLConnection) mUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("QueryUtils", "Problem making HTTP request");
        }
        return jsonResponse;
    }

    public static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
