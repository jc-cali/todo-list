package org.jcapps.todolist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItems extends AppCompatActivity {
    private ListView mListView;
    static ArrayList<ListTitles> mListItems = new ArrayList<>();
    private ArrayAdapter<ListTitles> mArrayAdapter;
    private AdapterView.OnItemClickListener mListener;

    private Intent mAddNewItem;
    private Intent mItemRemove;

    static String msgFromMain;

    ListTitles item = new ListTitles();

    private void addItem(String name) {
        if (mListItems != null) {
            item.setmTitleName(name);
            mListItems.add(item);

        } else {
            mListItems = new ArrayList<ListTitles>();
            item.setmTitleName(name);
            ListTitles item = new ListTitles(name);
            mListItems.add(item);
            mArrayAdapter = new ArrayAdapter<ListTitles>(this, R.layout.todo_list_row, mListItems);
            mArrayAdapter.notifyDataSetChanged();
        }
    }
    private void removeItem(Object obj) {
        if (mListItems != null) {
            mListItems.remove(obj);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        mListView =(ListView) findViewById(R.id.lst_items);
        TextView txtlisttitle = (TextView) findViewById(R.id.txt_items);
        Intent titlefromMain = getIntent();

//        if (titlefromMain != null) {
//            try {
//                msgFromMain = "Items for List - " + titlefromMain.getStringExtra("TITLE");
//                txtlisttitle.setText(msgFromMain);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_additem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AddNewItem", "click fab_additem");
                mAddNewItem = new Intent(getApplicationContext(), AddNewItem.class);

                startActivity(mAddNewItem);
            }
        });
        // Get back new item information from AddNewItem activity.
        mAddNewItem = getIntent();
        addItem(mAddNewItem.getStringExtra("ITEM"));
        mArrayAdapter = new ArrayAdapter<ListTitles>(this, R.layout.todo_list_row, mListItems);

        if(mListView != null && mArrayAdapter != null) {
            mListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListTitles listitems = mListItems.get(position);
                    String item = listitems.getmTitleName();
                    // Removing item from arraylist by object value.
                    removeItem(item);
//                    mItemRemove = new Intent(MainActivity.this, "Activity name to remove item".class);
//                    mItemRemove.putExtra("TITLE", listitems);
//                    startActivity("Actvity name to remove item");
                }
            };

            mListView.setAdapter(mArrayAdapter);
            mListView.setOnItemClickListener(mListener);
            mArrayAdapter.notifyDataSetChanged();
        }

    }
}
