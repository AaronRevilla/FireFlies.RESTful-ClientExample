package com.example.aaron.restful_clientexample.utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 10/8/2016.
 */

public class RetrofitServiceGenerator {

    public static final String API_BASE_URL = "https://jsonplaceholder.typicode.com";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit builder = new Retrofit.Builder().baseUrl(API_BASE_URL)
                                                                    .addConverterFactory(GsonConverterFactory.create())
                                                                    .build();

   public static <S> S createService(Class<S> serviceClass) {
        //Retrofit retrofit = builder.create(httpClient.build()).build();
        return builder.create(serviceClass);
   }

}
