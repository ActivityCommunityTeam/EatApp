package com.dijiaapp.eatserviceapp.kaizhuo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dijiaapp.eatserviceapp.R;
import com.dijiaapp.eatserviceapp.data.Seat;
import com.dijiaapp.eatserviceapp.data.source.SeatsRemoteDataSource;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;


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

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seat, container, false);
        unbinder = ButterKnife.bind(this, view);
        seatRecyclerviewAdapter = new SeatRecyclerviewAdapter();
        mSeatRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mSeatRecyclerview.setAdapter(seatRecyclerviewAdapter);
        SeatsRemoteDataSource seatsRemoteDataSource = new SeatsRemoteDataSource();
        subscription = seatsRemoteDataSource.getSeats(16).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Seat>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.toString());
                    }

                    @DebugLog
                    @Override
                    public void onNext(List<Seat> seats) {
                        System.out.println(seats);
                        seatRecyclerviewAdapter.setSeatList(seats);
                    }
                });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if(subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
