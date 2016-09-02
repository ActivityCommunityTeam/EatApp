package com.dijiaapp.eatserviceapp.data;

/**
 * Created by wjy on 16/8/15.
 */
public class Seat {
    /**
     * seatType : 01
     * containNum : 6
     * seatId : 1
     * seatName : 座位01
     * useStatus : 01
     */
    private String seatType;
    private int containNum;
    private int seatId;
    private String seatName;
    private String useStatus;

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public int getContainNum() {
        return containNum;
    }

    public void setContainNum(int containNum) {
        this.containNum = containNum;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }
}
