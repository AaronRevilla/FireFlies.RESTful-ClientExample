package com.example.aaron.restful_clientexample.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.aaron.restful_clientexample.R;
import com.example.aaron.restful_clientexample.pojos.Album;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by User on 10/8/2016.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    public List<Album> albums = new ArrayList<Album>();
    Context context;
    public String URL_BASE = "https://jsonplaceholder.typicode.com/photos";
    TextView output;

    public AlbumAdapter(Context context, TextView output){
        this.context = context;
        this.output = output;
        requestAlbums();
    }

    @Override
    public AlbumAdapter.AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout

        View contactView = inflater.inflate(R.layout.album_layout, parent, false);

        // Return a new holder instance
        AlbumViewHolder albumViewHolder = new AlbumViewHolder(contactView);
        return albumViewHolder;
    }

    public void requestAlbums(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, URL_BASE, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int total = response.length();
                        //Log.d("DEBUG", total + "");
                        for(int idx = 0; idx < total ; idx++){
                            output.setText("Loading Data ... " + (idx+1) + " from " + total);
                            //Log.d("DEBUG", response.toString());
                            Album auxAlbum = new Album();
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = response.getJSONObject(idx);
                                auxAlbum.setAlbumId(Integer.parseInt(jsonObj.getString("id")));
                                auxAlbum.setTitle(jsonObj.getString("title"));
                                auxAlbum.setUrl(jsonObj.getString("url"));
                                auxAlbum.setThumbnailUrl(jsonObj.getString("thumbnailUrl"));
                                albums.add(auxAlbum);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.getStackTrace().toString());
                    }
                });

        // Access the RequestQueue through your singleton class.
        VolleyQueueSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void onBindViewHolder( AlbumAdapter.AlbumViewHolder holder, int position) {
        Album album = albums.get(position);

        // Set item views based on your views and data model
        TextView idAlbum = holder.idAlbum;
        TextView title = holder.title;
        TextView url = holder.url;
        ImageView img = holder.image;

        idAlbum.setText("Album Id: " + String.valueOf(album.getAlbumId()));
        title.setText("Title: " + album.getTitle());
        url.setText("URL: " + album.getUrl());

        Glide.with(context).load(album.getUrl()).placeholder(R.drawable.ic_account_circle_black_48dp).override(150,150).centerCrop().into(img);

        // Retrieves an image specified by the URL, displays it in the UI.
        //ImageLoader mImageLoader;
        //mImageLoader = VolleyQueueSingleton.getInstance(context).getImageLoader();
        //img.setImageUrl("http://az616578.vo.msecnd.net/files/2016/04/23/635969800309510397-600135549_Album-1.jpg", mImageLoader);
       /* mImageLoader.get(   album.getUrl(),
                            ImageLoader.getImageListener(   img,
                                                            R.drawable.ic_account_circle_black_48dp,
                                                            R.drawable.ic_error_outline_black_24dp));*/
        //VolleyQueueSingleton.getInstance(context).addToRequestQueue(imgReq);
    }

    public void addAlbum(final Album a){
        output.setText("ADD ALBUM = " + a.toString() + "\n");

        StringRequest request = new StringRequest(Request.Method.POST, URL_BASE + "/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DEBUG RESPONSE", response);
                Gson gson = new Gson();
                Album newAlbum = gson.fromJson(response, Album.class);
                albums.add(0, newAlbum);
                notifyItemInserted(0);
                output.append("ALBUM INSERTED " + albums.size() + "\n");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                output.setText("ERROR DURING THE CALL TO THE SERVICE\n" + error.getStackTrace());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("albumId",       String.valueOf(a.getAlbumId()));
                params.put("id",            String.valueOf(a.getId()));
                params.put("title",         a.getTitle());
                params.put("url",           a.getUrl());
                params.put("thumbnailUrl",  a.getThumbnailUrl());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        // Access the RequestQueue through your singleton class.
        VolleyQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void modifyAlbum(final int position){
        final Album albumModify = albums.get(position);
        albumModify.setTitle("Queen");
        albumModify.setId(2);
        albumModify.setAlbumId(2);
        albumModify.setUrl("https://i.ytimg.com/vi/_Uu12zY01ts/maxresdefault.jpg");
        albumModify.setThumbnailUrl("https://i.ytimg.com/vi/_Uu12zY01ts/maxresdefault.jpg");
        output.setText("MODIFY ALBUM = " + albumModify.toString() + "\n");


        StringRequest request = new StringRequest(Request.Method.PUT, URL_BASE + "/" + albumModify.getAlbumId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DEBUG MODIFY", response);
                Gson gson = new Gson();
                Album newAlbum = gson.fromJson(response, Album.class);
                albums.remove(position);
                albums.add(position, newAlbum);
                notifyItemChanged(position);
                output.append("ALBUM MODIFY " + albums.size() + "\n");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                output.setText("ERROR DURING THE CALL TO THE SERVICE\n" + error.getStackTrace());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("albumId",       String.valueOf(albumModify.getAlbumId()));
                params.put("id",            String.valueOf(albumModify.getId()));
                params.put("title",         albumModify.getTitle());
                params.put("url",           albumModify.getUrl());
                params.put("thumbnailUrl",  albumModify.getThumbnailUrl());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        VolleyQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void patchAlbum(final int position){
        final Album albumModify = albums.get(position);
        albumModify.setTitle("Queen Greatest Hits");
        albumModify.setId(2);
        albumModify.setAlbumId(2);
        albumModify.setUrl("https://i.ytimg.com/vi/_Uu12zY01ts/maxresdefault.jpg");
        albumModify.setThumbnailUrl("https://i.ytimg.com/vi/_Uu12zY01ts/maxresdefault.jpg");
        output.setText("PATCH ALBUM = " + albumModify.toString() + "\n");
        StringRequest request = new StringRequest(Request.Method.PATCH, URL_BASE + "/" + albumModify.getAlbumId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Album newAlbum = gson.fromJson(response, Album.class);
                albums.remove(position);
                albums.add(position, newAlbum);
                notifyItemChanged(position);
                output.append("ALBUM PATCH " + albums.size() + "\n");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                output.setText("ERROR DURING THE CALL TO THE SERVICE\n" + error.getStackTrace());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("albumId",       String.valueOf(albumModify.getAlbumId()));
                params.put("id",            String.valueOf(albumModify.getId()));
                params.put("title",         albumModify.getTitle());
                params.put("url",           albumModify.getUrl());
                params.put("thumbnailUrl",  albumModify.getThumbnailUrl());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        VolleyQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void deleteAlbum(final int position){
        Log.d("DEBUG", position+ " ");
        Album albumDelete = albums.get(position);
        Log.d("DEBUG", albumDelete.toString());
        output.setText("DELETE ALBUM = " + albumDelete.toString() + "\n");
        StringRequest request = new StringRequest(Request.Method.DELETE, URL_BASE + "/" + albumDelete.getAlbumId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                albums.remove(position);
                notifyItemRemoved(position);
                output.append("ALBUM DELETED " + albums.size() + "\n");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                output.setText("ERROR DURING THE CALL TO THE SERVICE\n" + error.getStackTrace().toString());
                Log.d("Stack", error.getStackTrace().toString());
            }
        });
        VolleyQueueSingleton.getInstance(context).addToRequestQueue(request);
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
