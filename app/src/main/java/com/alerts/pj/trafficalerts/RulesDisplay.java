package com.alerts.pj.trafficalerts;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.lang.reflect.Field;

public class RulesDisplay extends ListActivity {
    private static final String DB_NAME = "ProjectDB";
    private SQLiteDatabase database;
    private Long[] friends=new Long[0];
    String[] ruleName=new String[0];
    int i=0; int j=0;
    String item;
    int index;

    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("PedRules");
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        displayRules();
    }

    public void displayRules() {
        //code commented piyush
        /*Intent in = getIntent();
        item = in.getStringExtra("selectedItem");
        Cursor rulesCursor = database.rawQuery("select * from Rules a, Locations b where b.Name='"+item+"' and b._id=a.Location_id" ,null);
        */
        Intent in = getIntent();
        index = in.getIntExtra("index",0)+1;
        Cursor rulesCursor = database.rawQuery("SELECT * FROM Rules a where a.location_id = " + index ,null);

        rulesCursor.moveToFirst();
        if(rulesCursor!=null){
            int n=rulesCursor.getCount();
            if(n!=0){
                friends=new Long[n];
                ruleName=new String[n];}
        }

        while(rulesCursor.isAfterLast()==false) {
            Rules rules = new Rules();
            rules.setRule_path(rulesCursor.getString(rulesCursor.getColumnIndex("Rule_path")));
            rules.setRule_descr(rulesCursor.getString(rulesCursor.getColumnIndex("Rule_descr")));

            friends[i++]=(Long.parseLong(getImageId(rules.getRule_path()) + ""));
            ruleName[j++]=rules.getRule_descr();
            rulesCursor.moveToNext();
        }

        CustomListAdapter adapter=new CustomListAdapter(this, ruleName, friends);
        setListAdapter(adapter);
        rulesCursor.close();
    }

    private int getImageId(String imgName){
        Field field = null;
        int resId = 0;
        try {

            field = R.drawable.class.getField(imgName);
            try {
                resId = field.getInt(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resId;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {

            Intent i;
            switch (position) {

                default:
                    /*i = new Intent(this, Image.class);
                    System.out.println("rule name="+ l.getItemAtPosition(position)+" item="+item);
                    i.putExtra("ruleName", l.getItemAtPosition(position).toString());
                    i.putExtra("locName",item);
                   // i.putExtra("locationIndex", index);
                    startActivity(i);
                    break;*/
                    i = new Intent(this, Image.class);
                    i.putExtra("ruleIndex", position + 1);
                    i.putExtra("locationIndex", index);
                    startActivity(i);
                    break;
            }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id== R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}