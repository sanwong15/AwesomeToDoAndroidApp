package com.example.san.awesometodoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    public static final String PASSED_ITEM_DETAILS = "passed_item_content";
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        readItems(); //Load items during onCreate()
        itemsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        setupListOnItemClickListener();
        setupListViewLongClickListener();
    }

    //Add item to the list
    public void onAddItem(View v){
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        //Clear the EditText view
        etNewItem.setText("");
        writeItems(); //Save items when a new list item is added
    }

    //Remove item from the list with Long click
    private void setupListViewLongClickListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id){
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });


    }

    //Edit item with Listener for short click
    private void setupListOnItemClickListener(){
        lvItems.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView passedItem = (TextView)view.findViewById(android.R.id.text1);
                String itemText = passedItem.getText().toString();

                Intent i = new Intent(MainActivity.this,EditItemActivity.class);
                i.putExtra(PASSED_ITEM_DETAILS,itemText);
                startActivityForResult(i,REQUEST_CODE);

            }
        });

    }

    // Handle the result of EditItemActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String newItemContent = data.getExtras().getString(EditItemActivity.EDITED_ITEM_DETAILS);
            TextView itemToUpdated = (TextView)findViewById(android.R.id.text1);
            itemToUpdated.setText(newItemContent);
            writeItems();
        }
    }

    //Loading and saving items from a file
    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch(IOException e){
            items = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(todoFile,items);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
