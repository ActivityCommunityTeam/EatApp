package com.dijiaapp.eatserviceapp.order;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dijiaapp.eatserviceapp.R;
import com.dijiaapp.eatserviceapp.data.OrderInfo;
import com.dijiaapp.eatserviceapp.kaizhuo.EnterActivityEvent;
import com.dijiaapp.eatserviceapp.kaizhuo.MainActivity;
import com.dijiaapp.eatserviceapp.kaizhuo.SeatFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

import static com.dijiaapp.eatserviceapp.kaizhuo.MainActivity.isOverTableDatas;

/**
 * Created by wjy on 2016/11/7.
 */

public class OrdersItemAdapter extends RecyclerView.Adapter<OrdersItemAdapter.ViewHolder> {
    List<OrderInfo> orderInfos;


    public int getLayout() {
        return R.layout.order_listitem;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false);

        return new ViewHolder(view);
    }
@DebugLog
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrderInfo orderInfo = orderInfos.get(position);
        if (orderInfo.getStatusId().equals("03")) {
            holder.mOrderItemDone.setVisibility(View.GONE);
            holder.mOrderItemDeliver.setVisibility(View.GONE);
            holder.mOrderItemJiacan.setVisibility(View.GONE);
        }

        holder.mOrderItemNumber.setText(orderInfo.getOrderHeaderNo());
        holder.mOrderItemEat.setText(orderInfo.getDinnerNum() + "");
        holder.mOrderItemServer.setText(orderInfo.getWaiterName());
        holder.mOrderItemStatus.setText(orderInfo.getStatusId());
        int _seatid= SeatFragment.getSeatId(orderInfos.get(position).getSeatName());
        Log.i("Daniel","---OrdersItemAdapter---onBindViewHolder--_seatid---"+_seatid+"---"+position);
        //把已经翻过的桌不能加餐
        if (MainActivity.isOverTableDatas!=null) {

            for (int i = 0; i < MainActivity.isOverTableDatas.size(); i++) {
                Log.i("Daniel","---OrdersItemAdapter---onBindViewHolder--isOverTableDatas.get(i)---"+isOverTableDatas.get(i));
                if (isOverTableDatas.get(i)==_seatid) {
                    Log.i("Daniel","---OrdersItemAdapter---i---"+i);
//                    holder.mOrderItemJiacan.setEnabled(false);
                }
            }
        }

//        holder.mOrderItemName.setText(orderInfo.getOrderHeaderNo());

        holder.rootView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EnterActivityEvent(OrderDetailActivity.class, null, orderInfo.getOrderId() + ""));
            }
        });
        holder.mOrderItemDone.setOnClickListener(new View.OnClickListener() {
            @DebugLog
            @Override
            public void onClick(View view) {
                int _seatid= SeatFragment.getSeatId(orderInfos.get(position).getSeatName());
                String _statusId = orderInfos.get(position).getStatusId();
                Log.i("Daniel","OrdersItemAdapter---onClick---_statusId"+_statusId);
               if(_statusId.equals("02")){
                    holder.mOrderItemJiacan.setEnabled(false);
                }

                EventBus.getDefault().post(new OrderOverEvent(_seatid,_statusId));


                //网络操作

            }
        });
        holder.mOrderItemJiacan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new OrderAddFoodEvent(orderInfo));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderInfos != null ? orderInfos.size() : 0;
    }

    public void setOrderInfos(List<OrderInfo> orderInfos) {
        this.orderInfos = orderInfos;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        @BindView(R.id.order_item_name)
        TextView mOrderItemName;
        @BindView(R.id.order_item_number)
        TextView mOrderItemNumber;
        @BindView(R.id.order_item_eat)
        TextView mOrderItemEat;
        @BindView(R.id.order_item_server)
        TextView mOrderItemServer;
        @BindView(R.id.order_item_layout)
        LinearLayout mOrderItemLayout;
        @BindView(R.id.order_item_status)
        TextView mOrderItemStatus;
        @BindView(R.id.order_item_deliver)
        View mOrderItemDeliver;
        @BindView(R.id.order_item_done)
        Button mOrderItemDone;
        @BindView(R.id.order_item_jiacan)
        Button mOrderItemJiacan;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
