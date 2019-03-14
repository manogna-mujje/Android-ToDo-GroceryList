package com.cmpe277.grocerylist;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

public class ContainerActivity extends AppCompatActivity {

    private static final String TAG = "ContainerActivity";

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    private String selectedName, selectedNameQty;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "onCreate: Started.");

        //get the intent extra from the MainActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");
        selectedNameQty = receivedIntent.getStringExtra("qty");


        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.containter);
        //setup the pager
        setupViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putString("id",String.valueOf(selectedID));
        bundle.putString("name",selectedName);
        bundle.putString("qty", selectedNameQty);

        EditDataFragment fragment = new EditDataFragment();
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, "Fragment");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }
}