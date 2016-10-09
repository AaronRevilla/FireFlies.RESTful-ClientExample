package com.example.aaron.restful_clientexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void nativeConnection(View view) {

        Intent nativeConnIntent = new Intent(this, NativeConnection.class);
        startActivity(nativeConnIntent);

    }

    public void volleyConnection(View view) {

        Intent volleyConnection = new Intent(this, VolleyConnection.class);
        startActivity(volleyConnection);

    }


    public void retroFitConnection(View view) {

        Intent retroFitConnection = new Intent(this, RetroFitConnection.class);
        startActivity(retroFitConnection);

    }
}
