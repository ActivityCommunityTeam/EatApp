package com.dijiaapp.eatserviceapp.kaizhuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.utils.KeyboardUtils;
import com.dijiaapp.eatserviceapp.R;
import com.dijiaapp.eatserviceapp.order.OrdersFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Case;

public class MainActivity extends AppCompatActivity {

    private static final String HOME_TAG = "home_flag";
    private static final String ORDERS_TAG = "orders_flag";
    private static final int CONTENT_ORDERS = 2;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    private static final int CONTENT_HOME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        setContent(CONTENT_HOME);

        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        setContent(CONTENT_HOME);
                        break;
                    case R.id.tab_order:
                        setContent(CONTENT_ORDERS);
                        break;
                }
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void enterSeatNumberActivity(EnterActivityEvent enterActivityEvent){
        Intent intent = new Intent(this,enterActivityEvent.getGotoClass());
        intent.putExtra("Seat",enterActivityEvent.getSeat());
        startActivity(intent);
    }
    private void setContent(int contentHome) {
        switch (contentHome) {
            case CONTENT_HOME:
                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HOME_TAG);
                if (homeFragment == null) {
                    homeFragment  = HomeFragment.newInstance("1", "2");
                }
                setFragment(homeFragment,HOME_TAG);
                break;
            case CONTENT_ORDERS:
                OrdersFragment orderFragment = (OrdersFragment) getSupportFragmentManager().findFragmentByTag(ORDERS_TAG);
                if(orderFragment == null){
                    orderFragment = OrdersFragment.newInstance();
                }
                setFragment(orderFragment,ORDERS_TAG);
                break;
        }

    }

    /**
     * 设置fragment
     * @param fragment
     */

    private void setFragment(Fragment fragment,String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, tag);
        fragmentTransaction.commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
