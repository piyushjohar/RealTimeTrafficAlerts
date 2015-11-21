package com.alerts.pj.trafficalerts;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class ImageListView extends ListActivity {

    String[] itemname ={
            "Mayank lets go and find below rules",
            "Now 1",
            "Rule 2",
            "Rule 3",
            "Rule 4",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void onListItemClick(ListView lv ,View view,int position,int imgid) {
        String Slecteditem= (String)getListAdapter().getItem(position);
        Toast.makeText(this, Slecteditem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
     if(id==android.R.id.home) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

