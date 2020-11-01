package com.geek.gutenberg.network.base;

import android.content.Context;

import com.geek.gutenberg.model.Genre;
import com.geek.gutenberg.network.impl.GenreServiceImpl;
import com.geek.gutenberg.network.service.GenreService;
import com.geek.gutenberg.network.service.GutenbergService;
import com.geek.gutenberg.network.utils.NetworkConstants;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.geek.gutenberg.network.utils.NetworkConstants.Retrofit.TIMEOUT;

public class ServiceController {
    private static ServiceController mServiceController;
    private GutenbergService mGutenbergService;
    private GenreService mGenreService;

    private ServiceController(Context context) {
        // Initialize retrofit client to make api calls
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstants.Retrofit.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();

        mGutenbergService = retrofit.create(GutenbergService.class);
        mGenreService = new GenreServiceImpl(context);
    }

    private OkHttpClient getOkHttpClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT,TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    /**
     * Maintian singleton pattern
     * @param context application context (not the activity context)
     * @return instance of service controller
     */
    public static ServiceController getInstance(Context context) {
        if (mServiceController == null) {
            mServiceController = new ServiceController(context);
        }
        return mServiceController;
    }

    /**
     * Return instance of GutenbergService. It has api calls defined related to books retrieval.
     * @return service instance
     */
    public GutenbergService getGutenbergService() {
        return mGutenbergService;
    }

    /**
     * Return instance id GenreService. Is has calls defined related to Genre retrieval.
     * @return
     */
    public GenreService getGenreService() {
        return mGenreService;
    }
}
