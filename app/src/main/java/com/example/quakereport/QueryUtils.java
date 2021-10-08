package com.example.quakereport;

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


public final class QueryUtils {
    //constructor
    private QueryUtils() {
    }
    ///////////////////////////////////////////////////////
    //creating the url
    public static URL createUrl (String urlString){
        if (urlString!=null) {
            try {
                return new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
    //////////////////////////////////////////////////////
    //making the http request
    public static String makeHttpRequest(URL url){
        String jsonResponse = "";
        if (url==null)
            return "";
        HttpURLConnection urlConnection = null;  //the connection
        InputStream inputStream = null;          //the stream
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e){
            //do nothing
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }
    ///////////////////////////////////////////////////
    //reading from the input stream function
    public static String readFromStream (InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null){
                output.append(line);
                line=bufferedReader.readLine();
            }
        }
        return output.toString();
    }
    ///////////////////////////////////////////////////
    //parsing the json response
    public static ArrayList<Earthquake> extractEarthquakes(String jsonResponse) {
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray features = root.getJSONArray("features");
            for (int i=0; i<features.length();++i){
                JSONObject featuresObject = features.getJSONObject(i);
                JSONObject propsObject = featuresObject.getJSONObject("properties");
                double mag = propsObject.optDouble("mag");
                String place= propsObject.optString("place");
                long timeInMilliSec= propsObject.optLong("time");
                String url =propsObject.getString("url");
                earthquakes.add(new Earthquake(mag,place,timeInMilliSec, url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

}
