package com.mdcconcepts.androidapp.politicianconnect.fragments.services.shopping.sub_shopping;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.mdcconcepts.androidapp.politicianconnect.R;

public class ShoppingProduct extends ActionBarActivity {

    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_product);
        if (savedInstanceState == null) {

            Intent myIntent = getIntent(); // gets the previously created intent
            int shoppingid = myIntent.getIntExtra("shoppingid", 0); // will return "FirstKeyValue"
            title = myIntent.getStringExtra("shopping_title"); // will return "Second

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, SubShoppingFragment.newInstance(shoppingid, title))
                    .commit();
        }
        restoreActionBar();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Drawable d = getResources().getDrawable(R.drawable.actionbar_bg);
        actionBar.setBackgroundDrawable(d);
//        actionBar.setIcon(getResources().getDrawable(R.drawable.ic_menu));
        actionBar.setTitle(title);
    }


}
