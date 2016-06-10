package com.example.san.awesometodoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {

    public static final String EDITED_ITEM_DETAILS = "edited_item_content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //Getting intent data
        Intent i = getIntent();

        String passedItemText = i.getStringExtra(MainActivity.PASSED_ITEM_DETAILS);
        EditText itemDetails = (EditText)findViewById(R.id.UpdatedItem);
        itemDetails.setText(passedItemText,TextView.BufferType.EDITABLE);
    }



    public void onSubmit(View v){
        //Close the activity and return to MainActivity

        EditText updatedItem = (EditText) findViewById(R.id.UpdatedItem);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra(EDITED_ITEM_DETAILS, updatedItem.getText().toString());
        //data.putExtra("code", 200); // ints work too

        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        this.finish(); // closes the activity, pass data to parent
    }
}
