package com.dijiaapp.eatserviceapp.diancan;

import com.dijiaapp.eatserviceapp.data.DishesListBean;

/**
 * Created by wjy on 16/9/28.
 */
public class CartEvent {
    private int flag;
    private DishesListBean disesBean;
    public CartEvent(int i, DishesListBean dishesListBean) {
        flag = i;
        disesBean = dishesListBean;
    }

    public int getFlag() {
        return flag;
    }

    public DishesListBean getDisesBean() {
        return disesBean;
    }
}
