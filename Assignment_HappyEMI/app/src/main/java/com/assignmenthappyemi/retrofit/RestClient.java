package com.assignmenthappyemi.retrofit;

public class RestClient {

    private static ApiService apiService;

    public static ApiService getApiService(String BASEURL) {

        return  new RetrofitUtil().getRetrofitBuilder(BASEURL).create(ApiService.class);
    }

}