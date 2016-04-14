package com.nhat.project.project;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

public class FoodPreparationActivity extends Activity {

    private static final String QUERY_URL = "http://192.168.137.1/Food/WS.php?";
    private OrderAdapter mAdapter;
    private ArrayList<String> mData;
    private LinearLayout titleLayout;
    private LinearLayout choiceLayout;
    private Button btnLoad;
    private ListView mainListView;
    private ArrayAdapter mArrayAdapter;
    private ArrayList mNameList = new ArrayList();
    private FinishedItemAdapter mJSONAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams outerLayout1Params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams choiceParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(300, 100);
        buttonParams.leftMargin = 5;
        buttonParams.rightMargin = 5;

        LinearLayout outerLayout = new LinearLayout(this);
        outerLayout.setOrientation(LinearLayout.VERTICAL);
        outerLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        outerLayout.setLayoutParams(outerLayout1Params);

        titleLayout = new LinearLayout(this);
        titleLayout.setOrientation(LinearLayout.VERTICAL);
        titleLayout.setGravity(Gravity.CENTER);
        titleLayout.setLayoutParams(choiceParams);

        TextView titleTV = new TextView(this);
        titleTV.setGravity(Gravity.CENTER);
        titleTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        titleTV.setTextColor(Color.GREEN);
        titleTV.setText("Finished List");
        titleTV.setPadding(25, 25, 25, 25);

        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setCornerRadius(5.0f);
        gdDefault.setStroke(50, Color.GREEN);

        btnLoad = new Button(this);
        btnLoad.setGravity(Gravity.CENTER);
        btnLoad.setText("Load");
        btnLoad.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        btnLoad.setTextColor(Color.BLACK);
        btnLoad.setBackgroundDrawable(gdDefault);
        btnLoad.setLayoutParams(buttonParams);
        btnLoad.setOnClickListener(clickBtnLoad);

        choiceLayout = new LinearLayout(this);
        choiceLayout.setGravity(Gravity.CENTER);
        choiceLayout.setLayoutParams(choiceParams);

        titleLayout.addView(titleTV);
        choiceLayout.addView(btnLoad);


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        mainListView = new ListView(this);
        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                mNameList);
        mainListView.setAdapter(mArrayAdapter);
        mJSONAdapter = new FinishedItemAdapter(this, getLayoutInflater());
        mainListView.setAdapter(mJSONAdapter);
        layout.addView(mainListView);

        outerLayout.addView(titleLayout);
        outerLayout.addView(choiceLayout);
        outerLayout.addView(layout);

        setContentView(outerLayout);
        doQuery();
    }

    View.OnClickListener clickBtnLoad = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doQuery();
        }
    };

    private void doQuery() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(QUERY_URL + "method=finished", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.has("data")) {
                    mJSONAdapter.updateData(jsonObject.optJSONArray("data"));
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                Log.e("Query Failure", statusCode + " " + throwable.getMessage());
            }
        });
    }

}
