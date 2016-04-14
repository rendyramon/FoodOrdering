package com.nhat.project.project;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.nhat.project.library.StaggeredGridView;

import java.util.ArrayList;

public class FoodGridViewActivity extends Activity implements AbsListView.OnItemClickListener {

    private static final int FETCH_DATA_TASK_DURATION = 2000;
    private StaggeredGridView mGridView;
    private OrderAdapter mAdapter;
    private ArrayList<String> mData;
    private Spinner dropdown;
    private EditText noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        setTitle("Food List");
        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);

        LayoutInflater layoutInflater = getLayoutInflater();

        View header = layoutInflater.inflate(R.layout.header, null);
        noteText = (EditText) header.findViewById(R.id.txt_note);
        noteText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mAdapter.setCurrentNote(noteText);
            }
        });
        dropdown = (Spinner) header.findViewById(R.id.list_tables);
        String[] items = new String[]{"1", "2", "3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mAdapter.setCurrentDropdown(dropdown);
                String tbNo = dropdown.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                mAdapter.setCurrentDropdown(dropdown);
                String tbNo = dropdown.getSelectedItem().toString();
            }
        });
        mGridView.addHeaderView(header);
        mGridView.setEmptyView(findViewById(android.R.id.empty));
        mAdapter = new OrderAdapter(this, R.id.txt_info, dropdown, noteText);

        if (savedInstanceState != null) {
            fillAdapter();
        }

        if (mData == null) {
            mData = FoodData.generateData();
        }
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);

        fetchData();
    }

    private void fillAdapter() {
        for (String data : mData) {
            mAdapter.add(data);
        }
    }

    private void fetchData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(FETCH_DATA_TASK_DURATION);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                fillAdapter();
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_food_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAdapter.clear();
        fetchData();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //String tbNo = dropdown.getSelectedItem().toString();
        //Toast.makeText(this, "Item Clicked: " + position + " - Table: " + tbNo, Toast.LENGTH_SHORT).show();
    }

}
