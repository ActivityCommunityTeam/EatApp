package com.dijiaapp.eatserviceapp.kaizhuo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dijiaapp.eatserviceapp.R;
import com.dijiaapp.eatserviceapp.data.Seat;
import com.dijiaapp.eatserviceapp.data.UserInfo;
import com.dijiaapp.eatserviceapp.data.source.SeatsRemoteDataSource;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeatFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TYPE = "type";
    @BindView(R.id.seat_recyclerview)
    RecyclerView mSeatRecyclerview;

    private int type;
    private Unbinder unbinder;
    private SeatRecyclerviewAdapter seatRecyclerviewAdapter;
    private Subscription subscription;
    private Realm realm;
    private long hotelId;

    public SeatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SeatFragment.
     */
    public static SeatFragment newInstance(int type) {
        SeatFragment fragment = new SeatFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_TYPE);


        }
    }



    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();

        UserInfo userInfo = realm.where(UserInfo.class).findFirst();
        //获取酒店id
        hotelId = userInfo.getHotelId();
        //设置fragment中显示数据
        setList();
    }
    static List<Seat> Seats;
    private void setList() {
        //首页中fragment数据切换
        SeatsRemoteDataSource seatsRemoteDataSource = new SeatsRemoteDataSource();
        //hotelId设置酒店id，从酒店中读取桌位信息
        subscription = seatsRemoteDataSource.getSeats(hotelId).subscribeOn(Schedulers.io())
                //循环转化为Observable<Seat>
                .flatMap(new Func1<List<Seat>, Observable<Seat>>() {
                    @Override
                    public Observable<Seat> call(List<Seat> seats) {
                        return Observable.from(seats);
                    }
                })
                //根据SeatType分离数据列表为大厅和包间
                .filter(new Func1<Seat, Boolean>() {
                    @Override
                    public Boolean call(Seat seat) {
                        switch (type) {
                            case 1:
                                return seat.getSeatType().equals("01");
                            case 2:
                                return seat.getSeatType().equals("02");
                        }
                        return true;
                    }
                })
                //再次把Observable对象数据转化为list
                .toList()
                //设置在主线程中更新ui
                .observeOn(AndroidSchedulers.mainThread())
                //开始下载桌位信息
                .subscribe(new Observer<List<Seat>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        //读取桌位信息失败
                        System.out.println(e.toString());
                    }

                    @DebugLog
                    @Override
                    public void onNext(List<Seat> seats) {
                        //获得网络桌位数据，更新fragment中显示

                        Seats=seats;
                        seatRecyclerviewAdapter.setSeatList(seats);
                    }
                });
    }
    public static int getSeatId(String seatname){
        for(int i=0;i<Seats.size();i++){
            if(Seats.get(i).getSeatName().equals(seatname)){
                return Seats.get(i).getSeatId();
            }
        }
        return -1;
    }
    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the food_listitem for this fragment
        View view = inflater.inflate(R.layout.fragment_seat, container, false);
        unbinder = ButterKnife.bind(this, view);
        seatRecyclerviewAdapter = new SeatRecyclerviewAdapter();
        //设置为三列的gridlayout
        mSeatRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mSeatRecyclerview.setAdapter(seatRecyclerviewAdapter);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

    }
}
