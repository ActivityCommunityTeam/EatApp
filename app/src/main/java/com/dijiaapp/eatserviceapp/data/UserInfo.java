package com.dijiaapp.eatserviceapp.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wjy on 16/9/8.
 */
public class UserInfo extends RealmObject{
    @PrimaryKey
    private long waiterId;
    private long hotelId;
    private String waiterName;


    public long getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(long waiterId) {
        this.waiterId = waiterId;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "waiterId=" + waiterId +
                ", hotelId=" + hotelId +
                ", waiterName='" + waiterName + '\'' +
                '}';
    }
}
