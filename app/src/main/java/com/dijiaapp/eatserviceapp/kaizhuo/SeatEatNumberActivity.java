package com.dijiaapp.eatserviceapp.kaizhuo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dijiaapp.eatserviceapp.R;
import com.dijiaapp.eatserviceapp.data.Seat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeatEatNumberActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.hotel_name)
    TextView mHotelName;
    @BindView(R.id.hotel_type)
    TextView mHotelType;
    @BindView(R.id.hotel_eat_number_et)
    EditText mHotelEatNumberEt;
    @BindView(R.id.hotel_done_bt)
    Button mHotelDoneBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_eat_number);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Seat seat = getIntent().getParcelableExtra("Seat");
        mHotelName.setText(seat.getSeatName());
        mHotelType.setText(seat.getSeatType());


    }

}
