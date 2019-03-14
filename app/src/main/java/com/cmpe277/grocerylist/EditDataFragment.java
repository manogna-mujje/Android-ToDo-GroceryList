package com.cmpe277.grocerylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.content.DialogInterface;

public class EditDataFragment extends Fragment {

    private static final String TAG = "EditDataFragment";


    private Button btnSave, btnDelete, btnViewList;
    private EditText editable_itemName, editable_itemQty;
    private TextView pageTitle;

    DatabaseHelper mDatabaseHelper;

    private String selectedName, selectedNameQty;
    private int selectedID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_data_fragment, container, false);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        editable_itemName = (EditText) view.findViewById(R.id.selected_item_name);
        editable_itemQty = (EditText) view.findViewById(R.id.selected_item_qty);
        pageTitle = (TextView) view.findViewById(R.id.pageTitle);
        btnViewList = (Button) view.findViewById(R.id.btnViewList) ;

        pageTitle.setText("Update Item");

        mDatabaseHelper = new DatabaseHelper(getActivity());

        Bundle bundle = this.getArguments();
        System.out.println("NOT NULL");
        selectedID = Integer.parseInt(bundle.getString("id"));
        //now get the name we passed as an extra
        selectedName = bundle.getString("name");
        selectedNameQty = bundle.getString("qty");

        /* set the text to show the current selected name */
        editable_itemName.setText(selectedName);
        editable_itemQty.setText(selectedNameQty);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_itemName.getText().toString();
                String qty = editable_itemQty.getText().toString();
                if (!item.equals("")) {
                    mDatabaseHelper.updateName(item, selectedID, selectedName, Integer.parseInt(qty));
                    toastMessage("Item updated");
                } else {
                    toastMessage("You must enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog("Delete Item?", "You'll lose Item entry");
            }
        });

        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    /**
     * customizable toast
     *
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void customDialog(String title, String message){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builderSingle.setIcon(R.drawable.ic_stat_name);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelMethod();
                    }
                });

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        okMethod();
                    }
                });


        builderSingle.show();
    }

    private void cancelMethod(){

    }

    private void okMethod(){
        mDatabaseHelper.deleteName(selectedID, selectedName);
        editable_itemName.setText("");
        editable_itemQty.setText("");
        toastMessage("Item removed from the list");
    }
}

