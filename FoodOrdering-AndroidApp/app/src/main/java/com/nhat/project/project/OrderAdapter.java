package com.nhat.project.project;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nhat.project.library.util.DynamicHeightTextView;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class OrderAdapter extends ArrayAdapter<String> {

    private Spinner dropdown;
    private EditText noteText;
    private static final String TAG = "OrderAdapter";
    private static final String QUERY_URL = "http://192.168.137.1/Food/WS.php?";
    public static final String[] foodNames = {"Barbecue Beef","Italian Pasta","Curry Soup","Fried Chicken","Hamburger","Salad"};
    public static final String[] foodPrices = {"10","8","12","9","6","7"};

    static class ViewHolder {
        DynamicHeightTextView txtLineOne;
        Button btnGo;
    }

    private final LayoutInflater mLayoutInflater;

    public OrderAdapter(final Context context, final int textViewResourceId, Spinner tmp, EditText note) {
        super(context, textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
        dropdown = tmp;
        noteText = note;
    }

    public void setCurrentDropdown(Spinner tmp) {
        dropdown = tmp;
    }

    public void setCurrentNote(EditText note) {
        noteText = note;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
            vh = new ViewHolder();
            vh.txtLineOne = (DynamicHeightTextView) convertView.findViewById(R.id.txt_info);
            vh.btnGo = (Button) convertView.findViewById(R.id.btn_add);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        switch (position) {
            case 0:
                convertView.setBackgroundResource(R.drawable.beef1);
                break;
            case 1:
                convertView.setBackgroundResource(R.drawable.pasta1);
                break;
            case 2:
                convertView.setBackgroundResource(R.drawable.soup1);
                break;
            case 3:
                convertView.setBackgroundResource(R.drawable.chicken1);
                break;
            case 4:
                convertView.setBackgroundResource(R.drawable.burger1);
                break;
            case 5:
                convertView.setBackgroundResource(R.drawable.salad1);
                break;
            default:
                break;
        }
        vh.txtLineOne.setText(foodNames[position] + "\n$" + foodPrices[position] + ".00");
        vh.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tbNo = dropdown.getSelectedItem().toString();
                String note = noteText.getText().toString();
                Toast.makeText(getContext(), "Added Successfully!", Toast.LENGTH_LONG).show();
                doQuery(tbNo, String.valueOf(position + 1), note);
            }
        });

        return convertView;
    }

    private void doQuery(String tableNo, String foodId, String foodNote) {
        String urlString = "";
        try {
            String tmpTBNo = URLEncoder.encode(tableNo, "UTF-8");
            String tmpFID = URLEncoder.encode(foodId, "UTF-8");
            String tmpFN = URLEncoder.encode(foodNote, "UTF-8");
            urlString = "tableNo=" + tmpTBNo + "&foodId=" + tmpFID + "&foodNote=" + tmpFN;
            Log.d("Adding Food String:", urlString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("Adding Food Result", e.getMessage());
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(QUERY_URL + urlString, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.has("result")) {
                    String res = jsonObject.optString("result");
                    if (res.equalsIgnoreCase("true")) {
                        Log.d("Adding Food Result", "True");
                    } else {
                        Log.d("Adding Food Result", "False");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                Log.e("Query Failure", statusCode + " " + throwable.getMessage());
            }
        });
    }

}