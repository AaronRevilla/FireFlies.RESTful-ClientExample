package com.example.aaron.restful_clientexample.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Oscar Aaron Revilla Escalona on 10/4/2016.
 */

public class HttpManager {

    public static BufferedReader reader = null;

    public static String getData(String uri){

        try {
            URL url = new URL(uri);
            HttpURLConnection conn = ((HttpURLConnection) url.openConnection());

            StringBuilder sb =  new StringBuilder();
            reader = new BufferedReader( new InputStreamReader( conn.getInputStream()));

            String line;
            while((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }

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
