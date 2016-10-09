package com.example.aaron.restful_clientexample.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.aaron.restful_clientexample.R;
import com.example.aaron.restful_clientexample.pojos.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        output.append("POSITION" + position+ "\n");
        // Set item views based on your views and data model
        TextView idAlbum = holder.idAlbum;
        TextView title = holder.title;
        TextView url = holder.url;
        ImageView img = holder.image;

        idAlbum.setText("Album Id: " + String.valueOf(album.getId()));
        title.setText("Title: " + album.getTitle());
        url.setText("URL: " + album.getUrl());

        //AddImage
        Glide.with(context).load(album.getUrl()).placeholder(R.drawable.ic_account_circle_black_48dp).override(150,150).centerCrop().into(img);
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

    public void addAlbum(Album a){
        output.setText("ADD ALBUM = " + a.toString() + "\n");
        Call<Album> call = albumsServices.addAlbum(a);
        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                albums.add(0, response.body());
                notifyItemInserted(0);
                output.append("ALBUM INSERTED " + albums.size() + "\n");
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                output.setText("ERROR DURING THE CALL TO THE SERVICE\n" + t.getStackTrace().toString());
            }
        });
    }

    public void modifyAlbum(final int position){
        Album albumModify = albums.get(position);
        albumModify.setTitle("Queen");
        albumModify.setId(2);
        albumModify.setAlbumId(2);
        albumModify.setUrl("https://i.ytimg.com/vi/_Uu12zY01ts/maxresdefault.jpg");
        albumModify.setThumbnailUrl("https://i.ytimg.com/vi/_Uu12zY01ts/maxresdefault.jpg");
        output.setText("MODIFY ALBUM = " + albumModify.toString() + "\n");
        Call<Album> call = albumsServices.modifyAlbum(String.valueOf(albumModify.getId()), albumModify);
        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                albums.remove(position);
                albums.add(position, response.body());
                output.append("ALBUM MODIFY IN POSITION " + position + " albums=" + albums.size() + "\n");
                //Log.d("DEBUG", "ALBUM MODIFY IN POSITION " + position + " albums=" + albums.size() + " " +response.body());
                notifyItemChanged(position);
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                output.setText("ERROR DURING THE CALL TO THE SERVICE\n" + t.getStackTrace().toString());
            }
        });
    }

    public void patchAlbum(final int position){
        Album albumModify = albums.get(position);
        albumModify.setTitle("Queen Greatest Hits");
        albumModify.setId(2);
        albumModify.setAlbumId(2);
        albumModify.setUrl("https://i.ytimg.com/vi/_Uu12zY01ts/maxresdefault.jpg");
        albumModify.setThumbnailUrl("https://i.ytimg.com/vi/_Uu12zY01ts/maxresdefault.jpg");
        output.setText("PATCH ALBUM = " + albumModify.toString() + "\n");
        Call<Album> call = albumsServices.patchAlbum(String.valueOf(albumModify.getId()), albumModify);
        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                albums.remove(position);
                albums.add(position, response.body());
                notifyItemChanged(position);
                output.append("ALBUM PATCH IN POSITION " + position + " albums=" + albums.size() + "\n");
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                output.setText("ERROR DURING THE CALL TO THE SERVICE\n" + t.getStackTrace().toString());
            }
        });
    }

    public void deleteAlbum(final int position){
        Album albumDelete = albums.get(position);
        output.setText("DELETE ALBUM = " + albumDelete.toString() + "\n");
        Call<Album> call = albumsServices.deleteAlbum(position);
        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                albums.remove(position);
                notifyItemRemoved(position);
                output.append("RESPONSE " + response.toString());
                output.append("ALBUM DELETE IN POSITION " + position + " albums=" + albums.size() + "\n");
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
