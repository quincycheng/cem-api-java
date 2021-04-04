package com.quincycheng.cyberark.cem.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.OutputStream;

public class HTTPClient {

    public static String doGet(String requestUrl) {
        return doGet(requestUrl, null, null);
    }

    public static String doGet(String requestUrl, String token) {
        return doGet(requestUrl, token, null);
    }

    public static String doGet(String requestUrl, String token, String requestBody) {
        String result = "";
        try {

            URL theUrl = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) theUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (token != null) {
                conn.setRequestProperty("Authorization", "Bearer " + token);
            }

            if (requestBody != null) {
                OutputStream os = conn.getOutputStream();
                os.write(requestBody.getBytes());
                os.flush();
            }

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                // System.out.println(output);
                result += output + "\n";
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // POST
    public static String doPost(String requestUrl) {
        return doPost(requestUrl, null);
    }

    public static String doPost(String requestUrl, String token) {
        return doGet(requestUrl, token, null);
    }

    public static String doPost(String requestUrl, String token, String requestBody) {
        String result = "";

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            if (requestBody != null) {
                OutputStream os = conn.getOutputStream();
                os.write(requestBody.getBytes());
                os.flush();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                result += output + "\n";
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
