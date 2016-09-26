package com.dijiaapp.eatserviceapp.diancan;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dijiaapp.eatserviceapp.R;
import com.dijiaapp.eatserviceapp.data.FoodType;
import com.dijiaapp.eatserviceapp.data.UserInfo;
import com.dijiaapp.eatserviceapp.network.Network;

import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FoodActivity extends AppCompatActivity {
    int eatNumber;
    long hotelId;

    Realm realm;
    Observer<List<FoodType>> observerFood = new Observer<List<FoodType>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @DebugLog
        @Override
        public void onNext(List<FoodType> foodTypes) {

        }
    };

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();

        hotelId = realm.where(UserInfo.class).findFirst().getHotelId();


        eatNumber = Integer.parseInt(getIntent().getStringExtra("number"));

        getFood();
    }

    private void getFood() {
        Network.getFoodService().listFoods(hotelId).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observerFood);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
