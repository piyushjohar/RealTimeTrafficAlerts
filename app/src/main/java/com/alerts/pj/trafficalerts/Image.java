package com.alerts.pj.trafficalerts;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import java.lang.reflect.Field;

public class Image extends Activity{
    private static final String DB_NAME = "ProjectDB";
    private SQLiteDatabase database;
    private ActionBar actionBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagetest);
        ImageView image = (ImageView) findViewById(R.id.test_image1);
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        String imageName = getImageName();
        int id = getImageId(imageName);
        image.setImageResource(id);
        actionBar= getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("PedRules");
    }


    private String getImageName(){
        Intent in = getIntent();
        int ruleIndex = in.getIntExtra("ruleIndex",0);
        int locationIndex = in.getIntExtra("locationIndex",0);
        Rules rules = new Rules();
        Cursor imageCursor = database.rawQuery("SELECT Rule_path FROM Rules a where a.Location_id = " + locationIndex + " and a._id = " + ruleIndex ,null);

        imageCursor.moveToFirst();
        if(imageCursor!=null){
            rules.setRule_path(imageCursor.getString(imageCursor.getColumnIndex("Rule_path")));}
        return rules.getRule_path();
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
