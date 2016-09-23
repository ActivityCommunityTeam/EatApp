package com.dijiaapp.eatserviceapp.kaizhuo;

import com.dijiaapp.eatserviceapp.data.Seat;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by wjy on 16/9/21.
 */
public class EnterActivityEvent {
    private Class gotoClass;
    private Seat seat;
    public EnterActivityEvent(Class gotoClass,Seat seat) {
        this.gotoClass = gotoClass;
        this.seat = seat;
    }

    public Class getGotoClass() {
        return gotoClass;
    }

    public Seat getSeat() {
        return seat;
    }
}
