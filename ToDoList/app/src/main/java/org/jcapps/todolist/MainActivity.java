package org.jcapps.todolist;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    static ArrayList<ListTitles> mListTitles = new ArrayList<>();
    // Set up for ArrayAdapter
    private ArrayAdapter<ListTitles> mArrayAdapter;
    private AdapterView.OnItemClickListener mListener;

    private Intent mAddNewList;
    private Intent mListItems;

    ListTitles title = new ListTitles();

    // todo: model code
    private void addList(String name) {
        if (mListTitles != null) {
            title.setmTitleName(name);
            mListTitles.add(title);
        } else {
            mListTitles = new ArrayList<ListTitles>();
            title.setmTitleName(name);
            ListTitles title = new ListTitles(name);
            mListTitles.add(title);
            mArrayAdapter = new ArrayAdapter<ListTitles>(this, R.layout.todo_list_row, mListTitles);
            mArrayAdapter.notifyDataSetChanged();
        }
    }
    private void removeList(Object obj) {
        if (mListTitles != null) {
            mListTitles.remove(obj);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView =(ListView) findViewById(R.id.lst_todo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Main", "click fab");
                mAddNewList = new Intent(getApplicationContext(), AddNewList.class);

                startActivity(mAddNewList);
            }
        });

        // Get the information from Intent after adding new list title from AddNewList Activity.
        mAddNewList = getIntent();
        // Call addList method after passing list title name in NAME variable
        addList(mAddNewList.getStringExtra("NAME"));
        mArrayAdapter = new ArrayAdapter<ListTitles>(this, R.layout.todo_list_row, mListTitles);

        if(mListView != null && mArrayAdapter != null) {
            mListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListTitles listTitles = mListTitles.get(position);
                    String title = listTitles.getmTitleName();
                    mListItems = new Intent(MainActivity.this, ListItems.class);
                    mListItems.putExtra("TITLE", title);
                    startActivity(mListItems);
                }
            };

            mListView.setAdapter(mArrayAdapter);
            mListView.setOnItemClickListener(mListener);
            mArrayAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
