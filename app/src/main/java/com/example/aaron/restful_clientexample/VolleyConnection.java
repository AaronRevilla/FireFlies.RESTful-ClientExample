package com.example.aaron.restful_clientexample;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.aaron.restful_clientexample.pojos.Album;
import com.example.aaron.restful_clientexample.utils.AlbumAdapter;

public class VolleyConnection extends AppCompatActivity {

    public TextView output;
    public RecyclerView rView;
    public LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_connection);

        output = ((TextView) findViewById(R.id.output));
        output.setMovementMethod(new ScrollingMovementMethod());

        rView = ((RecyclerView) findViewById(R.id.rView));

        if(isOnline()){
            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this);
            rView.setLayoutManager(mLayoutManager);
            // specify an adapter
            AlbumAdapter albumAdapter = new AlbumAdapter(this, output);
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

    public void addAlbum(View view) {
        Album album = new Album();
        album.setTitle("The Beatles");
        album.setAlbumId(1000);
        album.setId(1000);
        album.setThumbnailUrl("https://beatlesfacts.files.wordpress.com/2012/05/beatles-british-flag.gif");
        album.setUrl("https://beatlesfacts.files.wordpress.com/2012/05/beatles-british-flag.gif");

        AlbumAdapter auxAlbumAdapater = (AlbumAdapter) rView.getAdapter();
        auxAlbumAdapater.addAlbum(album);

        rView.setAdapter(auxAlbumAdapater);
    }

    public void modifyAlbum(View view) {
        AlbumAdapter auxAlbumAdapater = (AlbumAdapter) rView.getAdapter();
        auxAlbumAdapater.modifyAlbum(0);
        rView.setAdapter(auxAlbumAdapater);
    }

    public void patchAlbum(View view) {
        AlbumAdapter auxAlbumAdapater = (AlbumAdapter) rView.getAdapter();
        auxAlbumAdapater.patchAlbum(0);
        rView.setAdapter(auxAlbumAdapater);
    }

    public void deleteAlbum(View view) {
        AlbumAdapter auxAlbumAdapater = (AlbumAdapter) rView.getAdapter();
        auxAlbumAdapater.deleteAlbum(0);
        rView.setAdapter(auxAlbumAdapater);
    }
}
