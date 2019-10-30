package com.cabal.app;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class Client {
    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Configuration.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
