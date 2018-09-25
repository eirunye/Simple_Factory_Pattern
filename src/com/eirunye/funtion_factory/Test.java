package com.eirunye.funtion_factory;

import com.eirunye.funtion_factory.definite_factory.JiangNanMealStore;
import com.eirunye.funtion_factory.character.MealStore;
import com.eirunye.funtion_factory.definite_factory.GuangDongMealStore;

/**
 * Author Eirunye
 * Created by on 2018/9/18.
 * Describe
 */
public class Test {
    public static void main(String[] args) {
        MealStore jiangNanMealStore = new JiangNanMealStore();
        jiangNanMealStore.submitOrderMeal("crayfish");

        System.out.println("江南店完成一单");
        System.out.println("==============================\n");

        MealStore shenZhenMealStore = new GuangDongMealStore();
        shenZhenMealStore.submitOrderMeal("roastDuck");

        System.out.println("深圳店完成一单");
    }
}
