package com.dijiaapp.eatserviceapp.data.source;

import com.dijiaapp.eatserviceapp.data.Seat;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by wjy on 16/8/30.
 */
public class SeatsRemoteDataSource implements SeatsDataSource {
    private static SeatsRemoteDataSource INSTANCE;
    private final static Map<String,Seat> SEATS_SERVICE_DATA = null;

    @Override
    public Observable<List<Seat>> getSeats() {
        return null;
    }

    @Override
    public Observable<Seat> getSeat(String seatId) {
        return null;
    }

    @Override
    public void refreshSeats() {

    }

    @Override
    public void saveSeat(Seat seat) {

    }

    @Override
    public void deleteAllSeats() {

    }

    @Override
    public void deleteSeat() {

    }
}