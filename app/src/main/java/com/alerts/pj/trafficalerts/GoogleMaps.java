package com.alerts.pj.trafficalerts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

public class GoogleMaps extends FragmentActivity implements OnMapReadyCallback {


    ArrayList<Circle> circles = new ArrayList<Circle>();
    GoogleMap Mmap;
    Polyline poly;
    ArrayList<LatLng> markerPoints;
    ListView listView;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    TextView txtDistance;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    LatLng origin = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);


    }

    public void onMapReady(GoogleMap map) {
        // Initializing
        markerPoints = new ArrayList<LatLng>();

        CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(29.64495744, -82.34681744));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        System.out.println("GlobalVariables.latitude=" + GlobalVariables.latitude);
        System.out.println("GlobalVariables.longitude" + GlobalVariables.longitude);
        Log.d("url", "1");

        map.moveCamera(center);
        map.animateCamera(zoom);


        //  CircleOptions circleOptions = new CircleOptions().center(new LatLng(29.651911, -82.316737)); // In meters
        //  circleOptions.radius(5000);
        //  Circle circle = map.addCircle(circleOptions);
        Log.d("url", "2");
        MarkerOptions marker = new MarkerOptions().position(new LatLng(29.64495744, -82.34681744)).
                title("University of Florida, Police").snippet("click again to view rules");
        // .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_arow));
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        marker.flat(true);
        map.addMarker(marker);
        MarkerOptions marker2 = new MarkerOptions().position(new LatLng(29.64637895, -82.34339396)).title("Rawlings Hall")
                .snippet("click again to view rules");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker2);
        MarkerOptions marker3 = new MarkerOptions().position(new LatLng(29.65196583, -82.34463198)).title("University Avenue")
                .snippet("click again to view rules");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        map.addMarker(marker3);
        MarkerOptions marker4 = new MarkerOptions().position(new LatLng(29.65220825, -82.34605535))
                .snippet("click again to view rules").title("St. Augustine Catholic Church");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker4);
        MarkerOptions marker5 = new MarkerOptions().position(new LatLng(29.65200892, -82.34953542)).snippet("click again to view rules").title("Ben Hill Griffin Stadium");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker5);
        MarkerOptions marker6 = new MarkerOptions().position(new LatLng(29.64484635, -82.35214296)).snippet("click again to view rules").title("Hume Hall");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker6);
        MarkerOptions marker7 = new MarkerOptions().position(new LatLng(29.64480028, -82.35056945)).snippet("click again to view rules").title("Reitz Ravine Woods Conservation Area");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker7);

        MarkerOptions marker8 = new MarkerOptions().position(new LatLng(29.64495322, -82.34790844)).snippet("click again to view rules").title("UF Book Store and Welcome center");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker8);
        MarkerOptions marker9 = new MarkerOptions().position(new LatLng(29.64965746, -82.34366274)).snippet("click again to view rules").title("College of Liberal Arts");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker9);
        MarkerOptions marker10 = new MarkerOptions().position(new LatLng(29.64867635, -82.35024347)).snippet("click again to view rules").title("O'Connell Center");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker10);
        MarkerOptions marker11 = new MarkerOptions().position(new LatLng(29.64456152, -82.34681744)).snippet("click again to view rules").title("Museum Road - Center Dr crossing");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker11);
        MarkerOptions marker12 = new MarkerOptions().position(new LatLng(29.6431212, -82.34691771)).snippet("click again to view rules").title("New Engineering Building");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker12);
        MarkerOptions marker13 = new MarkerOptions().position(new LatLng(29.64838796, -82.34544997)).snippet("click again to view rules").title("University HUB");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker13);
        MarkerOptions marker14 = new MarkerOptions().position(new LatLng(29.63945836, -82.34619536)).snippet("click again to view rules").title("College Of Dentistry");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker14);
        MarkerOptions marker15 = new MarkerOptions().position(new LatLng(29.63752744, -82.37274356)).snippet("click again to view rules").title("Shands Hospital");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker15);
        MarkerOptions marker16 = new MarkerOptions().position(new LatLng(29.64358621, -82.339525)).snippet("click again to view rules").title("Housing and Residence Education");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker16);
        MarkerOptions marker17 = new MarkerOptions().position(new LatLng(29.637708, -82.368364)).snippet("click again to view rules").title("South West Recreational Centre");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker17);
        MarkerOptions marker18 = new MarkerOptions().position(new LatLng(29.63754, -82.372634)).snippet("click again to view rules").title("Hilton Hotel");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker18);

        if (circles != null && circles.size() > 0) {
            for (int i = 0; i < circles.size(); i++) {
                Circle circ = circles.get(i);
                circ.remove();
            }
        }
        String where = GlobalVariables.fromWhere;
        System.out.println("where in map=" + where);
        if (where != null && where.equalsIgnoreCase("not")) {
            String locNames = GlobalVariables.locationNames;
            String locs[] = locNames.split(";");
            System.out.println(locs.length + "::" + locs);
            SQLiteDatabase database;
            String DB_NAME = "ProjectDB";
            ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
            database = dbOpenHelper.openDataBase();
            Cursor cursor = database.rawQuery("SELECT * FROM Locations", null);
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                Locations locations = new Locations();

                if (checkForLocation(cursor.getString(cursor.getColumnIndex("Name")), locs)) {
                    System.out.println("name=" + cursor.getString(cursor.getColumnIndex("Name")));
                    locations.setLatitude(cursor.getDouble(cursor.getColumnIndex("Latitude")));
                    locations.setLongitude(cursor.getDouble(cursor.getColumnIndex("Longitude")));
                    CircleOptions circleOptions = new CircleOptions()
                            .center(new LatLng(locations.getLatitude(), locations.getLongitude()))   //set center
                            .radius(200)   //set radius in meters
                            .fillColor(0x40ff0000)  //semi-transparent
                            .strokeColor(Color.RED)
                            .strokeWidth(5);
                    Circle circ = map.addCircle(circleOptions);
                    circles.add(circ);
                    center =

                            CameraUpdateFactory.newLatLng(new LatLng(locations.getLatitude(), locations.getLongitude()));
                    CameraUpdateFactory.newLatLng(new LatLng(locations.getLatitude(), locations.getLongitude()));
                    zoom = CameraUpdateFactory.zoomTo(15);
                    System.out.println("GlobalVariables.latitude=" + GlobalVariables.latitude);
                    System.out.println("GlobalVariables.longitude" + GlobalVariables.longitude);
                    Log.d("url", "1");

                    map.moveCamera(center);
                    map.animateCamera(zoom);

                }

                cursor.moveToNext();

            }
        }

        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
        map.setMyLocationEnabled(true);
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {

                if (poly != null) {
                    poly.remove();
                }
                LatLng dest = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                if (origin == null) {
                    origin = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                } else {
                    System.out.println("origin.latitude=" + origin.latitude);
                    System.out.println("origin.longitude=" + origin.longitude);
                    System.out.println("marker.getPosition().latitude=" + marker.getPosition().latitude);
                    System.out.println("marker.getPosition().longitude=" + marker.getPosition().longitude);
                    if (origin.latitude == marker.getPosition().latitude &&
                            origin.longitude == marker.getPosition().longitude) {
                        System.out.println("same clicked");
                        LatLng latLong = marker.getPosition();
                        double dlat = latLong.latitude;
                        double dlon = latLong.longitude;
                        Intent i = new Intent(getApplicationContext(), PrepopSqliteDbActivity.class);
                        i.putExtra("latitude", dlat);
                        i.putExtra("longitude", dlon);
                        i.putExtra("fromMap", "true");
                        startActivity(i);
                    } else {
                        System.out.println("diff clicked");
                        origin = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);

                    }
                }

                Log.d("url", "marker clicked" + marker.getPosition().latitude + "::" + marker.getPosition().longitude);
                return false;
            }
        });
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                LatLng latLong = marker.getPosition();
                double dlat = latLong.latitude;
                double dlon = latLong.longitude;
                Intent i = new Intent(getApplicationContext(), PrepopSqliteDbActivity.class);
                i.putExtra("latitude", dlat);
                i.putExtra("longitude", dlon);
                i.putExtra("fromMap", "true");
                startActivity(i);
            }
        });


    }

    private boolean checkForLocation(String str, String[] list) {
        boolean flag = false;
        for (int i = 0; i < list.length; i++) {
            if (str.equalsIgnoreCase(list[i])) {
                flag = true;
                break;
            }

        }
        return flag;
    }

    private class AsyncTaskEx extends AsyncTask<Void, Void, Void> {

        /**
         * The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute()
         */
        @Override
        protected Void doInBackground(Void... arg0) {
            //call your method here it will run in background
            System.out.println("service running in background");
            return null;
        }

        /**
         * The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground()
         */
        @Override
        protected void onPostExecute(Void result) {
            //Write some code you want to execute on UI after doInBackground() completes
            return;
        }

        @Override
        protected void onPreExecute() {
            //Write some code you want to execute on UI before doInBackground() starts
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}