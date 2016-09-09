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


}
