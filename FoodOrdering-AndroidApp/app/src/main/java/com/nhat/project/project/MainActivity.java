package com.nhat.project.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Arigo Restaurant");
        setContentView(R.layout.activity_main);

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(300, 100);
        buttonParams.leftMargin = 5;
        buttonParams.rightMargin = 5;

        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setCornerRadius(5.0f);
        gdDefault.setStroke(50, Color.GREEN);

        Button orderFood = (Button) findViewById(R.id.btn_order);
        orderFood.setGravity(Gravity.CENTER);
        orderFood.setText("Order Food");
        orderFood.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        orderFood.setTextColor(Color.BLACK);
        orderFood.setBackgroundDrawable(gdDefault);
        orderFood.setLayoutParams(buttonParams);
        orderFood.setOnClickListener(this);

        Button listFood = (Button) findViewById(R.id.btn_foods);
        listFood.setGravity(Gravity.CENTER);
        listFood.setText("List Of Foods");
        listFood.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        listFood.setTextColor(Color.BLACK);
        listFood.setBackgroundDrawable(gdDefault);
        listFood.setLayoutParams(buttonParams);
        listFood.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.btn_order) {
            startActivity(new Intent(this, FoodGridViewActivity.class));
        }
        if (v.getId() == R.id.btn_foods) {
            startActivity(new Intent(this, FoodPreparationActivity.class));
        }
    }
}
