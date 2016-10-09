package com.example.aaron.restful_clientexample;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.aaron.restful_clientexample.utils.AlbumAdapter;
import com.example.aaron.restful_clientexample.utils.AlbumAdapterRetrofit;

public class RetroFitConnection extends AppCompatActivity {

    public TextView output;
    public RecyclerView rView;
    public RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro_fit_connection);

        output = ((TextView) findViewById(R.id.output));
        output.setMovementMethod(new ScrollingMovementMethod());

        rView = ((RecyclerView) findViewById(R.id.rView));

        if(isOnline()){
            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this);
            rView.setLayoutManager(mLayoutManager);
            // specify an adapter
            AlbumAdapterRetrofit albumAdapter = new AlbumAdapterRetrofit(this, output);
            rView.setAdapter(albumAdapter);
        }
        else{
            output.setText("The device has no connection to Internet");
        }
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
}
