package com.example.aaron.restful_clientexample.utils;

import com.example.aaron.restful_clientexample.pojos.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by User on 10/8/2016.
 */

public interface RetrofitAlbumsInterface {

    @GET("/photos/{id}")
    Call<Album> getAlbumbyId(@Path("id") String id);

    @GET("/photos")
    Call<List<Album>> getAlbums();

    @POST("/photos")
    Call<Album> addAlbum(@Body Album album);

    @PUT("/photos/{albumId}")
    Call<Album> modifyAlbum(@Path("albumId")String id, @Body Album album);

    @PATCH("/photos/{albumId}")
    Call<Album> patchAlbum(@Path("albumId")String id, @Body Album album);

    @DELETE("/photos/{id}")
    Call<Album> deleteAlbum(@Path("id") Integer id);


    /*static class Contributor {

        public Integer albumId;
        public Integer id;
        public String title;
        public String url;
        public String thumbnailUrl;
    }*/

}
