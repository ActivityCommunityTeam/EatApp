package com.dijiaapp.eatserviceapp.network.api;

import com.dijiaapp.eatserviceapp.data.Seat;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wjy on 16/9/1.
 *
 */
public interface SeatSevice {
    @GET("dining/getDiningTables/{seatId}")
    Observable<List<Seat>> listSeats(@Path("seatId") long id);

    @FormUrlEncoded
    @POST("dining/updateStatus")
    String updateStatus(@Field("seatId") long id, @Field("useStatus") String status);
}
