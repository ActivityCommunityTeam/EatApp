package com.dijiaapp.eatserviceapp.diancan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dijiaapp.eatserviceapp.R;
import com.dijiaapp.eatserviceapp.View.PinnedHeaderListView;
import com.dijiaapp.eatserviceapp.data.Cart;
import com.dijiaapp.eatserviceapp.data.DishesListBean;
import com.dijiaapp.eatserviceapp.data.FoodType;
import com.dijiaapp.eatserviceapp.data.Seat;
import com.dijiaapp.eatserviceapp.data.UserInfo;
import com.dijiaapp.eatserviceapp.network.Network;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.dijiaapp.eatserviceapp.R.id.pinnedListView;

public class FoodActivity extends AppCompatActivity {
    int eatNumber;
    long hotelId;
    private boolean[] flagArray;

    private boolean isScroll = true;

    private Seat seat;
    Realm realm;
    Observer<List<FoodType>> observerFoodFromNet = new Observer<List<FoodType>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @DebugLog
        @Override
        public void onNext(List<FoodType> foodTypes) {
            flagArray = new boolean[foodTypes.size()];
            flagArray[0] = true;
            for (int i = 1; i < foodTypes.size(); i++) {
                flagArray[i] = false;
            }

            saveFood(foodTypes);

            setListViews(foodTypes);

        }
    };

    Observer<List<FoodType>> observerFoodFromLocal = new Observer<List<FoodType>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @DebugLog
        @Override
        public void onNext(List<FoodType> foodTypes) {
            flagArray = new boolean[foodTypes.size()];
            flagArray[0] = true;
            for (int i = 1; i < foodTypes.size(); i++) {
                flagArray[i] = false;
            }

            setListViews(foodTypes);

        }
    };

    @DebugLog
    private void saveFood(final List<FoodType> foodTypes) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.copyToRealm(foodTypes);
            }
        }, new Realm.Transaction.OnSuccess() {
            @DebugLog
            @Override
            public void onSuccess() {
                // Transaction was a success.
                System.out.println("chenggong");
            }
        }, new Realm.Transaction.OnError() {
            @DebugLog
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                System.out.println(error);
            }
        });
    }

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_listview)
    ListView mLeftListview;
    @BindView(pinnedListView)
    PinnedHeaderListView mPinnedListView;
    @BindView(R.id.food_cart_bt)
    ImageView mFoodCartBt;
    @BindView(R.id.food_money)
    TextView mFoodMoney;
    @BindView(R.id.food_next)
    Button mFoodNext;
    private LeftListAdapter leftListAdapter;

    private void setListViews(final List<FoodType> foodTypes) {
        final MainSectionedAdapter mainSectionedAdapter = new MainSectionedAdapter(this, foodTypes);
        mPinnedListView.setAdapter(mainSectionedAdapter);
        leftListAdapter = new LeftListAdapter(this, foodTypes, flagArray);
        mLeftListview.setAdapter(leftListAdapter);

        mLeftListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                for (int i = 0; i < foodTypes.size(); i++) {
                    if (i == position) {
                        flagArray[i] = true;
                    } else {
                        flagArray[i] = false;
                    }
                }
                leftListAdapter.notifyDataSetChanged();
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += mainSectionedAdapter.getCountForSection(i) + 1;
                }
                mPinnedListView.setSelection(rightSection);
            }
        });

        mPinnedListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (mPinnedListView.getLastVisiblePosition() == (mPinnedListView.getCount() - 1)) {
                            mLeftListview.setSelection(ListView.FOCUS_DOWN);
                        }

                        // 判断滚动到顶部
                        if (mPinnedListView.getFirstVisiblePosition() == 0) {
                            mLeftListview.setSelection(0);
                        }

                        break;
                }
            }

            int y = 0;
            int x = 0;
            int z = 0;

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < foodTypes.size(); i++) {
                        if (i == mainSectionedAdapter.getSectionForPosition(mPinnedListView.getFirstVisiblePosition())) {
                            flagArray[i] = true;
                            x = i;
                        } else {
                            flagArray[i] = false;
                        }
                    }
                    if (x != y) {
                        leftListAdapter.notifyDataSetChanged();
                        y = x;
                        if (y == mLeftListview.getLastVisiblePosition()) {
//                            z = z + 3;
                            mLeftListview.setSelection(z);
                        }
                        if (x == mLeftListview.getFirstVisiblePosition()) {
//                            z = z - 1;
                            mLeftListview.setSelection(z);
                        }
                        if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                            mLeftListview.setSelection(ListView.FOCUS_DOWN);
                        }
                    }
                } else {
                    isScroll = true;
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cartEvent(CartEvent event) {
        refreshCart(event);
    }

    //刷新购物车
    @DebugLog
    private void refreshCart(CartEvent event) {
        double money = 0;
        List<Cart> carts = realm.where(Cart.class).equalTo("seatId", seat.getSeatId()).findAll();
        DishesListBean disesBean = event.getDisesBean();

        if (event.getFlag() == 0) {
            for (Cart cart : carts) {
                if (cart.getDishesListBean().getId() == disesBean.getId()) {
                    int amount = cart.getAmount();
                    amount--;
                    if (amount == 0)
                        cart.deleteFromRealm();
                    else
                        cart.setAmount(amount);
                    break;
                }
            }
        } else if (event.getFlag() == 1) {
            for (Cart cart : carts) {
                if (cart.getDishesListBean().getId() == disesBean.getId()) {
                    int amount = cart.getAmount();
                    amount++;
                    cart.setAmount(amount);
                    cart.setMoney(disesBean.getDishesPrice() * amount);
                    break;

                }


            }

            realm.beginTransaction();
            Cart cartNew = realm.createObject(Cart.class);
            cartNew.setAmount(1);
            cartNew.setDishesListBean(disesBean);
            cartNew.setMoney(disesBean.getDishesPrice());
            cartNew.setTime(Calendar.getInstance().getTime().getTime());
            cartNew.setSeatId(seat.getSeatId());
            realm.commitTransaction();
        }

        for (Cart cart : carts) {
            money += cart.getMoney();
            System.out.println(money + "::");
        }

        mFoodMoney.setText("￥" + money);
    }

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EventBus.getDefault().register(this);
        realm = Realm.getDefaultInstance();

        hotelId = realm.where(UserInfo.class).findFirst().getHotelId();


        eatNumber = Integer.parseInt(getIntent().getStringExtra("number"));
        seat = getIntent().getParcelableExtra("seat");

        getFood();
    }

    @DebugLog
    private void getFood() {
        Observable<List<FoodType>> observable = getFoodFromLocal();
        if (observable == null) {
            getFoodFromNet();
        } else {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observerFoodFromLocal);
        }

    }

    @DebugLog
    private Observable<List<FoodType>> getFoodFromLocal() {

        List<FoodType> foodTypes = realm.where(FoodType.class).findAll();
        System.out.println(foodTypes + ":::");
        if (foodTypes != null && foodTypes.size() > 0) {
            return Observable.just(foodTypes);
        } else {
            return null;
        }
        /*return realm.where(FoodType.class).findAll().asObservable()
                .filter(new Func1<RealmResults<FoodType>, Boolean>() {
                    @Override
                    public Boolean call(RealmResults<FoodType> foodTypes) {
                        return foodTypes != null && foodTypes.size() > 0;
                    }
                })
                .flatMap(new Func1<RealmResults<FoodType>, Observable<List<FoodType>>>() {
                    @Override
                    public Observable<List<FoodType>> call(RealmResults<FoodType> foodTypes) {
                        return Observable.just(realm.copyFromRealm(foodTypes));
                    }
                });*/


    }

    private void getFoodFromNet() {
        Network.getFoodService().listFoods(hotelId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observerFoodFromNet);

//        return Network.getFoodService().listFoods(hotelId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.food_cart_bt, R.id.food_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.food_cart_bt:
                break;
            case R.id.food_next:
                break;
        }
    }
}
