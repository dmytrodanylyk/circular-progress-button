package com.dd.sample;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] items = getResources().getStringArray(R.array.sample_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        switch (position) {
            case 0:
                Sample1Activity.startThisActivity(this);
                break;
            case 1:
                Sample2Activity.startThisActivity(this);
                break;
            case 2:
                Sample3Activity.startThisActivity(this);
                break;
            case 3:
                Sample4Activity.startThisActivity(this);
                break;
            case 4:
                Sample5Activity.startThisActivity(this);
                break;
        }
    }
}
