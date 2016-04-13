package info.jkjensen.familymap.WebTools;

import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jk on 3/16/2016.
 * A client to help with Http requests
 */
public class HttpClient{

    /**
     * Login the user with a POST request
     * @param url the URL object to use to POST
     * @param username the username entered by the user
     * @param password the password entered by the user
     * @return the result string from the request
     */
    public String userLogin(URL url, String username, String password) {

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.connect();

            JSONObject json = new JSONObject();
            json.put("username", username).put("password", password);
            Log.i("http", json.toString() + "-----------------------------------------");

            OutputStream requestBody = connection.getOutputStream();
            requestBody.write(json.toString().getBytes());
            requestBody.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                Log.i("http", "Response received: " + responseBodyData);
                return responseBodyData;
            }
        }
        catch (Exception e) {
            Log.e("HttpClient", e.getMessage() + "*******************************", e);
            return "error";
        }
        return null;
    }


    /**
     * Make a GET request
     * @param url the URL object to use to POST
     * @param authToken the token to signify an authenticated user
     * @return the result string from the request
     */
    public String get(URL url, String authToken){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            // Set HTTP request headers, if necessary
             connection.addRequestProperty("Authorization", authToken);

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get HTTP response headers, if necessary
                // Map<String, List<String>> headers = connection.getHeaderFields();

                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                Log.i("jk_http", responseBodyData + " was the response.");
                return responseBodyData;
            } else {
                Log.e("jk_http", "Server error " + connection.getResponseCode());// SERVER RETURNED AN HTTP ERROR
            }
        }catch (IOException ioe){
            Log.e("jk_http", ioe.getMessage());
        }
        return null;
    }
}