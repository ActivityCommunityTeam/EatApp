package com.dijiaapp.eatserviceapp.network;

import com.dijiaapp.eatserviceapp.network.api.SeatSevice;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wjy on 16/9/1.
 *
 */
public class Network {
    private static SeatSevice seatSevice;
    public static SeatSevice getApiService(){
        if(seatSevice == null){
            Retrofit retrofit = getRetrofit();
            seatSevice = retrofit.create(SeatSevice.class);

        }
        return seatSevice;
    }
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://211.149.235.17:8080/dcb/")
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();

    }

}