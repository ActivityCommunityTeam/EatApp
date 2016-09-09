package com.dijiaapp.eatserviceapp.kaizhuo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dijiaapp.eatserviceapp.R;
import com.dijiaapp.eatserviceapp.data.Seat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by wjy on 16/8/15.
 *
 */
public class SeatRecyclerviewAdapter extends RecyclerView.Adapter<SeatRecyclerviewAdapter.ViewHolder> {
    private List<Seat> seatList;

    public int getLayout() {
        return R.layout.seat_listitem;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false);
        return new ViewHolder(view);
    }

    @DebugLog
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Seat seat = seatList.get(position);
        holder.mSeatListitemName.setText(seat.getSeatName());
        holder.mSeatListitemNumber.setText(seat.getContainNum() + "哈哈");
        holder.mSeatListitemStatus.setText(seat.getUseStatus());
    }

    @Override
    public int getItemCount() {
        return seatList != null ? seatList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.seat_listitem_name)
        TextView mSeatListitemName;
        @BindView(R.id.seat_listitem_number)
        TextView mSeatListitemNumber;
        @BindView(R.id.seat_listitem_status)
        TextView mSeatListitemStatus;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
