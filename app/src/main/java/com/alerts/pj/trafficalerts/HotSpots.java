package com.alerts.pj.trafficalerts;

/**
 * Created by pj on 3/18/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HotSpots extends ListFragment {
    String[] itemName ={
            "UF Police Dept",
            "Rawlings Hall",
            "College of Liberal Arts",
            "University Avenue",
            "Augustine Church",
            "Ben Hill Griffin",
            "O'Cnonell Center",
            "Hume Hall",
            "Ravine Conservational Area",
            "UF Book Store",
            "Museum Road",
            "New Engg Building",
            "University Hub",
            "College of Dentistry",
            "Shands Hospital",
            "Housing and Residence",
            "SW Rec Centre",
            "Hilton Hotel"
    };

    int[] images = new int[]{

            R.drawable.ufpolice,
            R.drawable.rawlingsicon,
            R.drawable.artsicon,
            R.drawable.avenueicon,
            R.drawable.churchicon,
            //R.drawable.ben_icon,
            R.drawable.connell2,
            R.drawable.humeicon,
            R.drawable.ravineicon,
            R.drawable.book3,
            R.drawable.crossingicon,
            R.drawable.nebicon,
            R.drawable.hubicon,
            R.drawable.denticon,
            R.drawable.shandsicon,
            R.drawable.housingicon,
            R.drawable.recicon,
            R.drawable.hiltonicon
    };
    String[] itemDesc ={
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<18;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("name", itemName[i]);
            hm.put("desc", itemDesc[i]);
            hm.put("image", Integer.toString(images[i]) );
            aList.add(hm);
        }

        PrepopSqliteDbActivity objDb = new PrepopSqliteDbActivity();
        String[] from = { "image","name","desc" };
        int[] to = { R.id.image,R.id.name,R.id.desc};
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.layoutpiyushlist, from, to);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onItemClick(AdapterView<?> parent, View v, int pos, long id)
    {
        Intent i;
        switch(pos){
            case 0:
                i=new Intent(getActivity().getApplicationContext(),PrepopSqliteDbActivity.class);
                startActivity(i);
                break;
            case 1:
                i=new Intent(getActivity().getApplicationContext(),ImageListView.class);
                startActivity(i);
                break;
            default:break;
        }
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {


        Intent i;
        switch(position){
            default:
                i=new Intent(getActivity().getApplicationContext(),PrepopSqliteDbActivity.class);
                i.putExtra("index", position);
                startActivity(i);
                break;
        }
    }
}
