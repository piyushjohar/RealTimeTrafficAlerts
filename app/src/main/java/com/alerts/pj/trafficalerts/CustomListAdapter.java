package com.alerts.pj.trafficalerts;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Long[] imgid;
    public CustomListAdapter(Activity context, String[] itemname, Long[] imgid) {
        super(context, R.layout.mylist, itemname);

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;

    }
    public View getView(int position,View view,ViewGroup parent) {
        try {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.mylist, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
            txtTitle.setText(itemname[position]);
            System.out.println("imgid="+imgid[position]+":pos="+position);
            if(imgid[position]!=null) {
                imageView.setImageResource(Integer.parseInt(imgid[position] + ""));
            }
            return rowView;
        } catch (Exception e) {
            e.printStackTrace();

        }
    return null;
    }
}
