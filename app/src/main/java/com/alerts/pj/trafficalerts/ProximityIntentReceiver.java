package com.alerts.pj.trafficalerts;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.ListView;
import com.google.android.gms.location.FusedLocationProviderApi;

import java.util.ArrayList;

public class ProximityIntentReceiver extends IntentService {

    public ProximityIntentReceiver() {
        super("net.zionsoft.example.MyLocationHandler");
    }


    private static final String DB_NAME = "ProjectDB";
    private static final String TABLE_NAME = "Locations";
    private static final String location_id = "_id";
    private static final String location_name = "Name";
    String str="unknown";
    private boolean flag=false;
    private SQLiteDatabase database;
    private ListView listView;
    @Override
    protected void onHandleIntent(Intent intent) {
        final Location location = intent.getParcelableExtra(FusedLocationProviderApi.KEY_LOCATION_CHANGED);
        System.out.println("location="+location);
        checkDistance(location);
    }
    public static double haversine(
            double lat1, double lng1, double lat2, double lng2) {
        int r = 6371; // average radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r * c;
        return d;
    }
    public void checkDistance(Location locationPerson){
        boolean flag=false;
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        Cursor cursor = database.rawQuery("SELECT * FROM Locations",null);
        Cursor cursor2;
        ArrayList<Locations> locationsArrayList=new ArrayList<Locations>();
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            Locations locations = new Locations();
            locations.setLatitude(cursor.getDouble(cursor.getColumnIndex("Latitude")));
            locations.setLongitude(cursor.getDouble(cursor.getColumnIndex("Longitude")));
            locations.setName(cursor.getString(cursor.getColumnIndex("Name")));
            locations.setFlag(cursor.getString(cursor.getColumnIndex("Flag")));
            double dist=haversine(locationPerson.getLatitude(),locationPerson.getLongitude(),locations.getLatitude(),locations.getLongitude());
            if(locations.getFlag().equalsIgnoreCase("f")){
                if(dist<0.2){
                    flag=true;
                    locationsArrayList.add(locations);
                    database.execSQL("update Locations set Flag='t' where Name='"+locations.getName()+"'");
                    //set to true
                }else{
                    //set to false
                    database.execSQL("update Locations set Flag='f' where Name='"+locations.getName()+"'");
                }
            }else{
                locationsArrayList.add(locations);
            }

            cursor.moveToNext();
        }

        if(flag){
            setNotifictaion(locationsArrayList);
        }
    }

    private void setNotifictaion(ArrayList<Locations> locationsArrayList){

        // Creates an explicit intent for an Activity in your app
        String locationNames="";
        String notMessage="";
        for(int i=0;i<locationsArrayList.size();i++){
            locationNames+=locationsArrayList.get(i).getName()+";";
            notMessage+=locationsArrayList.get(i).getName()+", ";
        }

        locationNames=locationNames.substring(0,locationNames.length()-1);
        notMessage=notMessage.substring(0,locationNames.length()-2);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher1)
                        .setContentTitle("PedRules")
                        .setAutoCancel(true)
                        .setContentText("Pedestrian Rules Information");
        Intent resultIntent = new Intent(this, PrepopSqliteDbActivity.class);
        //resultIntent.putExtra("fromMap","true");
        resultIntent.putExtra("fromNot","true");
        resultIntent.putExtra("locNames",locationNames);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(PrepopSqliteDbActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}