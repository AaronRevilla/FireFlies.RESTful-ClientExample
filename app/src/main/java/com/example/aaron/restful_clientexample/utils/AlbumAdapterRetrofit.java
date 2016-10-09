package com.example.aaron.restful_clientexample.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.example.aaron.restful_clientexample.R;
import com.example.aaron.restful_clientexample.pojos.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 10/8/2016.
 */

public class AlbumAdapterRetrofit extends RecyclerView.Adapter<AlbumAdapterRetrofit.AlbumViewHolder> {
    public List<Album> albums = new ArrayList<Album>();
    Context context;
    TextView output;
    RetrofitAlbumsInterface albumsServices;

    public AlbumAdapterRetrofit(Context context, TextView output){
        this.context = context;
        this.output = output;

        //Init RetrofitService
        albumsServices = RetrofitServiceGenerator.createService(RetrofitAlbumsInterface.class);
        getAlbums();
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

    public void getAlbums(){
        output.setText("GET ALL ALBUMS CALL\n");
        Call<List<Album>> call = albumsServices.getAlbums();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                albums = response.body();
                notifyDataSetChanged();
                output.append("ALL ALBUMS WERE READ, TOTAL=" + albums.size() + "\n");
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                output.setText("ERROR DURING THE CALL TO THE SERVICE\n" + t.getStackTrace().toString());
            }
        });
    }

    public void getUserById(String userId){
        output.setText("GET ALBUM BY ID CALL id=" + userId + "\n");
        Call<Album> call = albumsServices.getAlbumbyId(userId);
        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                albums.add(response.body());
                notifyDataSetChanged();
                output.append("ALBUMS READED " + albums.size() + "\n");
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                output.setText("ERROR DURING THE CALL TO THE SERVICE\n" + t.getStackTrace().toString());
            }
        });
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
