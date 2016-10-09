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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
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
                                //notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

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
        /*int max = 250;
        mImageLoader = new ImageRequest(  "http://az616578.vo.msecnd.net/files/2016/04/23/635969800309510397-600135549_Album-1.jpg",
                                    new Response.Listener<Bitmap>() {

                                        @Override
                                        public void onResponse(Bitmap response) {
                                            img.setImageBitmap(response);
                                        }
                                    }, max, max, null, Config.RGB_565,
                                    new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            img.setImageResource(R.drawable.ic_error_outline_black_24dp);
                                        }
                                    });*/
        //img.setImageBitmap(album);
        //VolleyQueueSingleton.getInstance(context).addToRequestQueue(imgReq);
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
