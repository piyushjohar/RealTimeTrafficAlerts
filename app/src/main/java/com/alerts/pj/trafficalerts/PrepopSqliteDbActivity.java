package com.alerts.pj.trafficalerts;

/**
 * Created by pj on 3/24/15.
 */

import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrepopSqliteDbActivity extends ListActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String DB_NAME = "ProjectDB";

    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "Locations";
    private static final String location_id = "_id";
    private static final String location_name = "Name";
    //private static final Bitmap location_image = "Loc_image";
    //byte[] img = cursor.getBlob(cursor.getColumnIndex("logo"));
    String str = "unknown";
    private boolean flag = false;
    private SQLiteDatabase database;
    private ListView listView;

    private ArrayList friends;
    Integer[] imgid = {
            R.drawable.rec1,
            R.drawable.crossing2,
            R.drawable.avenue1,
            R.drawable.rawlings1,
            R.drawable.crossing3,
            R.drawable.avenue1,
            R.drawable.rec4,
            R.drawable.ravineicon
    };


    private int getImageId(String imgName) {
        Field field = null;
        int resId = 0;
        try {
            String str = "church1";
            field = R.drawable.class.getField(imgName);
            try {
                resId = field.getInt(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("resIdresIdresId=" + resId);
        return resId;
    }

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    LocationRequest mLocationRequest;

    protected synchronized void buildGoogleApiClient() {
        System.out.println("buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
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

    public void onConnected(Bundle connectionHint) {


        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        // Toast.makeText(this, "Location is "+mLastLocation.getLatitude()+"::"+mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();

        System.out.println("onConnected mLastLocation=" + mLastLocation);
        if (mLastLocation != null) {
            System.out.println(String.valueOf(mLastLocation.getLatitude()));
            System.out.println(String.valueOf(mLastLocation.getLongitude()));
        }
        PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                new Intent(this, ProximityIntentReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, pendingIntent);

        Cursor cursor = database.rawQuery("SELECT * FROM Locations", null);
        database.execSQL("update Locations set Flag='f'");


    }

    public void checkDistance(Location locationPerson) {
        boolean flag = false;
        Cursor cursor = database.rawQuery("SELECT * FROM Locations", null);
        Cursor cursor2;
        ArrayList<Locations> locationsArrayList = new ArrayList<Locations>();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            Locations locations = new Locations();
            locations.setLatitude(cursor.getDouble(cursor.getColumnIndex("Latitude")));
            locations.setLongitude(cursor.getDouble(cursor.getColumnIndex("Longitude")));
            locations.setName(cursor.getString(cursor.getColumnIndex("Name")));
            locations.setFlag(cursor.getString(cursor.getColumnIndex("Flag")));
            double dist = haversine(locationPerson.getLatitude(), locationPerson.getLongitude(), locations.getLatitude(), locations.getLongitude());
            if (locations.getFlag().equalsIgnoreCase("f")) {
                if (dist < 1) {
                    flag = true;
                    locationsArrayList.add(locations);
                    database.execSQL("update Locations set Flag='t' where Name='" + locations.getName() + "'");
                    //set to true
                } else {
                    //set to false
                    database.execSQL("update Locations set Flag='f' where Name='" + locations.getName() + "'");
                }
            } else {
            }

            cursor.moveToNext();
        }
        if (flag) {
            setNotifictaion(locationsArrayList);
        }
    }

    @Override
    public void onLocationChanged(Location locationPerson) {

        System.out.println("onLocationChanged=" + locationPerson.getLatitude() + ":::" + locationPerson.getLongitude());
        Location myCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        checkDistance(locationPerson);
        GlobalVariables.latitude = locationPerson.getLatitude();
        GlobalVariables.longitude = locationPerson.getLongitude();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the next section.

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(300000);
        mLocationRequest.setFastestInterval(300000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }

    private void setNotifictaion(ArrayList<Locations> locationsArrayList) {

// Creates an explicit intent for an Activity in your app
        String locationNames = "";
        String notMessage = "";
        for (int i = 0; i < locationsArrayList.size(); i++) {
            locationNames += locationsArrayList.get(i).getName() + ";";
            notMessage += locationsArrayList.get(i).getName() + ", ";
        }

        locationNames = locationNames.substring(0, locationNames.length() - 1);
        notMessage = notMessage.substring(0, locationNames.length() - 2);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher1)
                        .setContentTitle("PedRules")
                        .setContentText(notMessage);
        Intent resultIntent = new Intent(this, PrepopSqliteDbActivity.class);
        //resultIntent.putExtra("fromMap","true");
        resultIntent.putExtra("fromNot", "true");
        resultIntent.putExtra("locNames", locationNames);
// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(PrepopSqliteDbActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        buildGoogleApiClient();

        Intent in = getIntent();
        String val = in.getStringExtra("fromMap");
        String not = in.getStringExtra("fromNot");
        if (not != null && not.equalsIgnoreCase("true")) {
            GlobalVariables.fromWhere = "not";
        } else {
            GlobalVariables.fromWhere = "";
        }


        System.out.println("The value of val is =" + val);
        // onClickStartServie();

//        setContentView(R.layout.imageview);
//        setContentView(R.layout.activity_main);

        for (int i = 0; i < imgid.length; i++) {
            System.out.println("DATA IDS:" + i + "--  " + imgid[i]);
        }
        fillFreinds();
    }

    private boolean checkForLocation(String str, String[] list) {
        boolean flag = false;
        for (int i = 0; i < list.length; i++) {
            System.out.println("list i=" + list[i] + ":::" + str);
            if (str.equalsIgnoreCase(list[i])) {
                flag = true;
                break;
            }

        }
        return flag;
    }

    //Extracting elements from the database
    private void fillFreinds() {
        try {
            System.out.println("Hi");
            Long[] friends = new Long[0];
            String[] ruleName = new String[0];
            String[] locName = new String[0];
            int i = 0;
            int j = 0;
            Intent in = getIntent();
            //int index = in.getIntExtra("index",0)+1;

            double lat = in.getDoubleExtra("latitude", 0);
            double lon = in.getDoubleExtra("longitude", 0);
            String fromMap = in.getStringExtra("fromMap");
            //Cursor friendCursor = database.rawQuery("SELECT distinct * FROM Rules",null);
            Cursor friendCursor = database.rawQuery("SELECT * FROM Locations", null);
            if (fromMap != null && fromMap.equalsIgnoreCase("true")) {
                System.out.println("query=" + "SELECT * FROM Rules a,Locations b where b.Latitude = " + lat
                        + " and b.Longitude=" + lon + " and a.Location_id=b._id");
                friendCursor = database.rawQuery("SELECT * FROM Rules a,Locations b where b.Latitude = " + lat
                        + " and b.Longitude=" + lon + " and a.Location_id=b._id", null);
            }
            friendCursor.moveToFirst();

            LatLng latLng;


            if (friendCursor != null) {
                int n = friendCursor.getCount();
                if (n != 0) {
                    friends = new Long[n];
                    ruleName = new String[n];
                    locName = new String[n];
                }
            }
            String not = in.getStringExtra("fromNot");
            System.out.println("not is eqqqual to " + not);
            if (not != null && not.equalsIgnoreCase("true")) {
                String locNames = in.getStringExtra("locNames");
                Double latitude = in.getDoubleExtra("latitude", 0);
                Double longitude = in.getDoubleExtra("longitude", 0);
                System.out.println("loc namesss=" + locNames);
                GlobalVariables.locationNames = locNames;
                GlobalVariables.fromWhere = "not";
                String locs[] = locNames.split(";");
                int k = 0;
                while (friendCursor.isAfterLast() == false) {
                    if (checkForLocation(friendCursor.getString(friendCursor.getColumnIndex("Name")), locs)) {
                        k++;
                    }
                    friendCursor.moveToNext();
                }
                System.out.println("value of k is " + k);
                friends = new Long[k];
                ruleName = new String[k];
                locName = new String[k];
                friendCursor = database.rawQuery("SELECT * FROM Locations", null);
                friendCursor.moveToFirst();

            }
            CustomListAdapter adapter;

            //commented piyush - 01 Apr
            if (fromMap != null && fromMap.equalsIgnoreCase("true")) {
                while (friendCursor.isAfterLast() == false) {
                    Rules rules = new Rules();
                    rules.set_id(friendCursor.getString(friendCursor.getColumnIndex("Rule_path")));
                    rules.setRule_descr(friendCursor.getString(friendCursor.getColumnIndex("Rule_descr")));
                    //index = in.getIntExtra("index",0) + 1;
                    System.out.println("rules from db=" + rules.get_id());
                    if (rules.getRule_descr() != null)
                        System.out.println(rules.getRule_descr());
                    friends[i++] = (Long.parseLong(getImageId(rules.get_id()) + ""));
                    ruleName[j++] = rules.getRule_descr();
                    friendCursor.moveToNext();
                }
                adapter = new CustomListAdapter(this, ruleName, friends);
            } else {
                not = in.getStringExtra("fromNot");
                if (not != null && not.equalsIgnoreCase("true")) {
                    String locNames = in.getStringExtra("locNames");
                    GlobalVariables.locationNames = locNames;
                    GlobalVariables.fromWhere = "not";
                    String locs[] = locNames.split(";");
                    while (friendCursor.isAfterLast() == false) {
                        Locations locations = new Locations();
                        if (checkForLocation(friendCursor.getString(friendCursor.getColumnIndex("Name")), locs)) {
                            locations.setLoc_path(friendCursor.getString(friendCursor.getColumnIndex("Loc_path")));
                            locations.setName(friendCursor.getString(friendCursor.getColumnIndex("Name")));

                            friends[i++] = (Long.parseLong(getImageId(locations.getLoc_path()) + ""));
                            locName[j++] = locations.getName();
                        }

                        friendCursor.moveToNext();

                    }
                } else {
                    while (friendCursor.isAfterLast() == false) {
                        Locations locations = new Locations();
                        locations.setLoc_path(friendCursor.getString(friendCursor.getColumnIndex("Loc_path")));
                        locations.setName(friendCursor.getString(friendCursor.getColumnIndex("Name")));

                        friends[i++] = (Long.parseLong(getImageId(locations.getLoc_path()) + ""));
                        locName[j++] = locations.getName();
                        friendCursor.moveToNext();

                    }
                    GlobalVariables.fromWhere = "";
                }

                adapter = new CustomListAdapter(this, locName, friends);
            }

            setListAdapter(adapter);

            friendCursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }


    public void pullDB()

    {
        Cursor cursor = database.rawQuery("SELECT * FROM Locations", null);
        cursor.moveToFirst();

        List<HashMap<String, Bitmap>> aList = new ArrayList<HashMap<String, Bitmap>>();
        HashMap<String, Bitmap> hm = new HashMap<String, Bitmap>();
        System.out.println("1.3");

        int i = 0;
        if (cursor != null && !cursor.isAfterLast()) {
            do {
                hm = new HashMap<String, Bitmap>();
                Locations location = new Locations();
                location.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                location.setName(cursor.getString(cursor.getColumnIndex("Name")));
                location.setDescr(cursor.getString(cursor.getColumnIndex("Description")));
                byte[] img = cursor.getBlob(cursor.getColumnIndex("Loc_image"));
                if (img != null) {
                    System.out.println("image length=" + img.length + "img=" + img);
                    hm.put("image", BitmapFactory.decodeByteArray(img, 0, img.length));
                }

                location.setLatitude(cursor.getDouble(cursor.getColumnIndex("Latitude")));
                location.setLongitude(cursor.getDouble(cursor.getColumnIndex("Longitude")));

                System.out.println("location-" + location.getName());
                System.out.println("latitude-" + location.getLatitude());

                System.out.println("hm value in loop=" + hm.get("name"));
                aList.add(hm);
                System.out.println("alist in loop=" + aList.get(i++).get("name"));

            } while (cursor.moveToNext());
            // Keys used in Hashmap
            String[] from = {"image"};
            ImageView imgView = (ImageView) findViewById(R.id.icon);

            int[] to = {R.id.image};
            SimpleAdapter adapter = new SimpleAdapter(this, aList,
                    R.layout.imageview, from, to);
            setListAdapter(adapter);
        }
        System.out.println("list is =" + aList);
        if (aList != null) {
            System.out.println("list size =" + aList.size());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is pret.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Respond to the action bar's Up/Home button
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        } else if (id == R.id.action_help) {
            Intent dialer = new Intent(getApplicationContext(), GoogleMaps.class);
            startActivity(dialer);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {

        Intent in = getIntent();
        String fromMap = in.getStringExtra("fromMap");
        if (fromMap != null && fromMap.equalsIgnoreCase("true")) {

        } else {
            Intent i;
            switch (position) {

                default:
                    i = new Intent(this, RulesDisplay.class);
                    i.putExtra("index", position);
                    startActivity(i);
                    break;
            }
        }

    }

    //start the service
    public void onClickStartServie() {
        //start the service from here //MyService is your service class name

        startService(new Intent(this, MyService.class));
    }

    //Stop the started service
    public void onClickStopService() {
        //Stop the running service from here//MyService is your service class name
        //Service will only stop if it is already running.
        stopService(new Intent(this, MyService.class));
    }
}
