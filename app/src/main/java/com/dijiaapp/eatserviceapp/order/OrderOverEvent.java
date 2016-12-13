package com.dijiaapp.eatserviceapp.order;

import com.dijiaapp.eatserviceapp.data.OrderInfo;

/**
 * Created by wjy on 2016/11/8.
 */
public class OrderOverEvent {
    OrderInfo orderInfo ;
    private long seatId;
    private String statusId;
    public OrderOverEvent(int seatId,String statusId) {
        this.seatId = seatId;
        this.statusId = statusId;
    }

    public String getStatusId() {
        return statusId;
    }

    public long getSeatId() {
        return seatId;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }
}
