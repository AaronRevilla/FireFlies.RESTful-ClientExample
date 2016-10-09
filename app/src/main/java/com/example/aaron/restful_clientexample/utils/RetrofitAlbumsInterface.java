package com.example.aaron.restful_clientexample.utils;

import com.example.aaron.restful_clientexample.pojos.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by User on 10/8/2016.
 */

public interface RetrofitAlbumsInterface {

    /*@GET("/photos/{id}")
    Call<List<Album>> contributors(
            @Path("id") String id
    );*/

    @GET("/photos")
    Call<List<Album>> contributors();

    /*static class Contributor {

        public Integer albumId;
        public Integer id;
        public String title;
        public String url;
        public String thumbnailUrl;
    }*/

}
