package com.nhat.project.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class FinishedItemAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public FinishedItemAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }

    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return mJsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.finished_item, null);
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.food_thumbnail);
            holder.info1View = (TextView) convertView.findViewById(R.id.food_info1);
            holder.info2View = (TextView) convertView.findViewById(R.id.food_info2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        JSONObject jsonObject = (JSONObject) getItem(position);

        String info1 = "";
        String info2 = "";

        if (jsonObject.has("tablenumber")) {
            info1 += "Table:" + jsonObject.optString("tablenumber");
        }
        if (jsonObject.has("foodname")) {
            info1 += " - " + jsonObject.optString("foodname");
        }
        if (jsonObject.has("note")) {
            info1 += " - Note: " + jsonObject.optString("note");
        }
        if (jsonObject.has("date")) {
            info2 += jsonObject.optString("date");
        }
        if (jsonObject.has("foodimage")) {
            String tmpImage = jsonObject.optString("foodimage");
            switch (tmpImage) {
                case "beef1.jpg":
                    holder.thumbnailImageView.setImageResource(R.drawable.beef1);
                    break;
                case "pasta1.jpg":
                    holder.thumbnailImageView.setImageResource(R.drawable.pasta1);
                    break;
                case "soup1.jpg":
                    holder.thumbnailImageView.setImageResource(R.drawable.soup1);
                    break;
                case "chicken1.jpg":
                    holder.thumbnailImageView.setImageResource(R.drawable.chicken1);
                    break;
                case "burger1.jpg":
                    holder.thumbnailImageView.setImageResource(R.drawable.burger1);
                    break;
                case "salad1.jpg":
                    holder.thumbnailImageView.setImageResource(R.drawable.salad1);
                    break;
                default:
                    break;
            }
        }

        holder.info1View.setText(info1);
        holder.info2View.setText(info2);
        return convertView;
    }

    public void updateData(JSONArray jsonArray) {
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public ImageView thumbnailImageView;
        public TextView info1View;
        public TextView info2View;
    }
}