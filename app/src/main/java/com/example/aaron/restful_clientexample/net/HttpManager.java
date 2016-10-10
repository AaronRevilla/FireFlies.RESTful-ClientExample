package com.example.aaron.restful_clientexample.net;


import android.util.Log;

import com.example.aaron.restful_clientexample.NativeConnection;
import com.example.aaron.restful_clientexample.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Oscar Aaron Revilla Escalona on 10/4/2016.
 */

public class HttpManager {

    public static BufferedReader reader = null;
    public static OutputStream os = null;
    public final static   int POST = 0;
    public final static  int GET = 1;
    public final static  int PUT = 2;
    public final static  int PATCH = 3;
    public final static  int DELETE = 4;


    public static String getData(String uri, int requestType){

        try {
            URL url;
            HttpURLConnection conn = null;
            StringBuilder sb =  new StringBuilder();


            switch(requestType){
                case POST:
                    url = new URL("https://jsonplaceholder.typicode.com/users");
                    conn = ((HttpURLConnection) url.openConnection());
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    Log.d("POST", uri);
                    os = conn.getOutputStream();
                    os.write(uri.getBytes());
                    os.flush();
                    break;
                case GET:
                    url = new URL(uri);
                    conn = ((HttpURLConnection) url.openConnection());
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("GET");
                    break;
                case PUT:
                    url = new URL("https://jsonplaceholder.typicode.com/users/2");
                    conn = ((HttpURLConnection) url.openConnection());
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("PUT");
                    conn.setDoOutput(true);
                    os = conn.getOutputStream();
                    os.write(uri.getBytes());
                    os.flush();
                    break;
                case PATCH:
                    url = new URL("https://jsonplaceholder.typicode.com/users/2");
                    conn = ((HttpURLConnection) url.openConnection());
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("PATCH");
                    conn.setDoOutput(true);
                    os = conn.getOutputStream();
                    os.write(uri.getBytes());
                    os.flush();
                    break;
                case DELETE:
                    url = new URL("https://jsonplaceholder.typicode.com/users/2");
                    conn = ((HttpURLConnection) url.openConnection());
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("DELETE");
                    os = conn.getOutputStream();
                    os.write(uri.getBytes());
                    os.flush();
                    break;
                default://open get request
                    url = new URL(uri);
                    conn = ((HttpURLConnection) url.openConnection());
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("GET");
                    break;
            }


            reader = new BufferedReader( new InputStreamReader( conn.getInputStream()));


            String line;
            sb.append("URL: " + conn.getURL().toString() + "\n");
            sb.append("RESPONSE CODE: " + conn.getResponseCode() + " " + conn.getResponseMessage() +"\n" );
            sb.append("REQUEST METHOD: " + conn.getRequestMethod() + "\n");
            while((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }

            conn.disconnect();
            reader.close();
            return sb.toString();

        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }

}
