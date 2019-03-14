package com.cmpe277.grocerylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "AddActivity";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnViewData;
    private EditText groceryName, groceryQty;
    private TextView pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        groceryName = (EditText) findViewById(R.id.groceryName);
        groceryQty = (EditText) findViewById(R.id.groceryQty);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnView);
        pageTitle = (TextView) findViewById(R.id.pageTitle);

        mDatabaseHelper = new DatabaseHelper(this);

        pageTitle.setText("Add New Item");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGroceryItem = groceryName.getText().toString();
                int newGroceryQty = Integer.parseInt(groceryQty.getText().toString());

                if (groceryName.length() != 0) {
                    AddData(newGroceryItem, newGroceryQty);
                    groceryName.setText("");
                    groceryQty.setText("");
                } else {
                    toastMessage("You must put something in the text field!");
                }

            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void AddData(String newEntry, int newGroceryQty) {
        boolean insertData = mDatabaseHelper.addData(newEntry, newGroceryQty);

        if (insertData) {
            toastMessage("Item added to the list!");
        } else {
            toastMessage("Error occured. Item not added to the list");
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
