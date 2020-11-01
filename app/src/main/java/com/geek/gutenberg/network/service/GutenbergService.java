package com.geek.gutenberg.network.service;

import com.geek.gutenberg.model.ResponseBooks;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GutenbergService {
    @GET("/books")
    Call<ResponseBooks> getBooks(@QueryMap Map<String, String> queryMap);
}
