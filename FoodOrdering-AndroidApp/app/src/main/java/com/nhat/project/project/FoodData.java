package com.nhat.project.project;

import java.util.ArrayList;

public class FoodData {

    public static final int DATA_ITEM_COUNT = 6;
    public static final String[] foodNames = {"Barbecue Beef","Italian Pasta","Curry Soup","Fried Chicken","Hamburger","Salad"};

    public static ArrayList<String> generateData() {

        final ArrayList<String> data = new ArrayList<String>(DATA_ITEM_COUNT);

        for (int i = 0; i < DATA_ITEM_COUNT; i++) {
            data.add(foodNames[i]);
        }

        return data;
    }

}
