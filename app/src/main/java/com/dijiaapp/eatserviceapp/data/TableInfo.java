package com.dijiaapp.eatserviceapp.data;

/**
 * Created by wjy on 16/9/30.
 */

public class TableInfo {
    private long userId;
    private String waiterName;
    private String orderHeaderNo;
    private long orderId;
    private int dinnerNum;
    private String statusId;

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public String getOrderHeaderNo() {
        return orderHeaderNo;
    }

    public void setOrderHeaderNo(String orderHeaderNo) {
        this.orderHeaderNo = orderHeaderNo;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getDinnerNum() {
        return dinnerNum;
    }

    public void setDinnerNum(int dinnerNum) {
        this.dinnerNum = dinnerNum;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "userId=" + userId +
                ", waiterName='" + waiterName + '\'' +
                ", orderHeaderNo='" + orderHeaderNo + '\'' +
                ", orderId=" + orderId +
                ", dinnerNum=" + dinnerNum +
                ", statusId='" + statusId + '\'' +
                '}';
    }
}
