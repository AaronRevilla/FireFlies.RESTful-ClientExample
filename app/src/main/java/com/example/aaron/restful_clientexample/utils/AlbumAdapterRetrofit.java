package com.example.aaron.restful_clientexample.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.aaron.restful_clientexample.R;
import com.example.aaron.restful_clientexample.pojos.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 10/8/2016.
 */

public class AlbumAdapterRetrofit extends RecyclerView.Adapter<AlbumAdapterRetrofit.AlbumViewHolder> {
    public List<Album> albums = new ArrayList<Album>();
    Context context;
    TextView output;
    //RetrofitAlbumsInterface albumsServices;

    public AlbumAdapterRetrofit(Context context, TextView output){
        this.context = context;
        this.output = output;

        RetrofitAlbumsInterface albumsServices = RetrofitServiceGenerator.createService(RetrofitAlbumsInterface.class);
        // Fetch and print a list of the contributors to this library.
        List<Album> contributors = (List<Album>) albumsServices.contributors();

        //requestAlbums();
    }

    @Override
    public AlbumAdapterRetrofit.AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout

        View contactView = inflater.inflate(R.layout.album_layout, parent, false);

        // Return a new holder instance
        AlbumAdapterRetrofit.AlbumViewHolder albumViewHolder = new AlbumAdapterRetrofit.AlbumViewHolder(contactView);
        return albumViewHolder;
    }

    public void requestAlbums(){

    }

    @Override
    public void onBindViewHolder( AlbumAdapterRetrofit.AlbumViewHolder holder, int position) {
        Album album = albums.get(position);

        // Set item views based on your views and data model
        TextView idAlbum = holder.idAlbum;
        TextView title = holder.title;
        TextView url = holder.url;
        final ImageView img = holder.image;

        idAlbum.setText("Album Id: " + String.valueOf(album.getAlbumId()));
        title.setText("Title: " + album.getTitle());
        url.setText("URL: " + album.getUrl());

        // Retrieves an image specified by the URL, displays it in the UI.
        ImageLoader mImageLoader;
        mImageLoader = VolleyQueueSingleton.getInstance(context).getImageLoader();
        //img.setImageUrl("http://az616578.vo.msecnd.net/files/2016/04/23/635969800309510397-600135549_Album-1.jpg", mImageLoader);
        mImageLoader.get(   "http://az616578.vo.msecnd.net/files/2016/04/23/635969800309510397-600135549_Album-1.jpg",
                ImageLoader.getImageListener(   img,
                        R.drawable.ic_account_circle_black_48dp,
                        R.drawable.ic_error_outline_black_24dp));
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder{

        TextView idAlbum;
        TextView title;
        TextView url;
        ImageView image;


        public AlbumViewHolder(View itemView) {
            super(itemView);

            idAlbum = ((TextView) itemView.findViewById(R.id.idAlbum));
            title = ((TextView) itemView.findViewById(R.id.title));
            url = ((TextView) itemView.findViewById(R.id.url));
            image = ((ImageView) itemView.findViewById(R.id.imageId));

        }
    }

}
