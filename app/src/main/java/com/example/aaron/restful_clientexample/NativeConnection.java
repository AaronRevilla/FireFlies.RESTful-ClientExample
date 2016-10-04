package com.example.aaron.restful_clientexample;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class NativeConnection extends AppCompatActivity {

    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_connection);

        output = ((TextView) findViewById(R.id.output));
        output.setMovementMethod(new ScrollingMovementMethod());

        ConnectionThread asyncThread = new ConnectionThread();
        asyncThread.execute("Param 1", "Param 2", "Param 3");

    }

    public boolean isOnline(){
        ConnectivityManager cm  = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }
        else{
            return false;
        }
    }

    public class ConnectionThread extends AsyncTask<String, String, String>{

        //UI access
        @Override
        protected void onPreExecute() {
            output.append("Starting Connection");
        }

        @Override
        protected String doInBackground(String... params) {
            return "Task complete";
        }

        //UI access
        @Override
        protected void onPostExecute(String s) {
            output.append(s);
        }
    }
}
