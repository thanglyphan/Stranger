package com.phan.thang.stranger;

import android.content.res.Resources;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by thang on 16.08.2016.
 */
public class NotificationSender {

    public NotificationSender(){
    }

    public void sendNotificationTo(String a, String b, String c){
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... user) {
                try {
                    String id = user[0];
                    String credentials = "key=" + user[1];
                    String contentType = "application/json";


                    URL url = new URL(user[2]);

                    Map<String,Object> params = new LinkedHashMap<>();
                    params.put("to", id);
                    params.put("data", "Lol man"); //TODO: Change this.

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : params.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                        System.out.println(postData.toString());
                    }

                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", credentials);
                    conn.setRequestProperty("contentType", contentType);
                    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                    for (int c; (c = in.read()) >= 0;)
                        System.out.print((char)c);
                    System.out.println(conn.getResponseCode() + ": " + conn.getResponseMessage());
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(a, b, c); //UserID, FirebaseID, FirebaseAPI.
    }
}
