package com.dijiaapp.eatserviceapp.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wjy on 16/9/28.
 * 购物车
 */

public class Cart extends RealmObject {
    private double money;
    private int amount;
    private DishesListBean dishesListBean;
    private int seatId;
    private long time;
    @PrimaryKey
    private long id;


    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public DishesListBean getDishesListBean() {
        return dishesListBean;
    }

    public void setDishesListBean(DishesListBean dishesListBean) {
        this.dishesListBean = dishesListBean;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSeatId() {
        return seatId;
    }
}
