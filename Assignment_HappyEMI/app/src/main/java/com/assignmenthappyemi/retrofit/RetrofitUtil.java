package com.assignmenthappyemi.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {


    public static final long READ_TIMEOUT = 2;
    public static final long CONNECT_TIMEOUT = 2;

    /**
     * get okHttpclient object after adding headers
     */
    public OkHttpClient getClient(String BASE_URL) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // Add the interceptor to OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(logging);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.MINUTES);
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES);

        return builder.build();
    }

    /**
     * get retrofit object
     */
    public Retrofit getRetrofitBuilder(String BASE_URL) {


        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient(BASE_URL))
                .build();
    }

}
