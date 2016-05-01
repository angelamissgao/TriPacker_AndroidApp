package com.example.tripacker.tripacker.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.entity.UserEntity;
import com.example.tripacker.tripacker.entity.mapper.PathJSONParser;
import com.example.tripacker.tripacker.entity.mapper.UserEntityJsonMapper;
import com.example.tripacker.tripacker.view.UserDetailsView;
import com.example.tripacker.tripacker.view.adapter.TripsTimelineAdapter;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;
import com.example.tripacker.tripacker.ws.remote.PathHttpConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Tiger
 * @since March 30, 2016 12:34 PM
 */
public class TripMapPageFragment extends Fragment implements UserDetailsView{
    private static final String TAG = "TripMapPageFragment";
    private Context thiscontext;
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final int REQUEST_EDIT = 0;
    private static final int RESULT_OK = 200;
    private static final int RESULT_NOTSAVED = 400;
    private static SharedPreferences pref;

    private ProgressDialog progressDialog;

    private ImageView profile_pic;
    private TextView username_view;
    private ListView trip_listView;
    private ImageView editProfileButton;
    private TextView durationShow;

    // Google Map
    GoogleMap googleMap;
    private static final LatLng Spot1_GPS = new LatLng(37.4219999,
            -122.0840575);
    private static final LatLng Spot2_GPS = new LatLng(37.41043, -122.059753);
    private static final LatLng Spot3_GPS = new LatLng(37.4852152, -122.059753);

    //TripModel
    TripEntity tripEntity = new TripEntity();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();

        View view = inflater.inflate(R.layout.trip_map_fragment, container, false);

        //Get Trip Bundle
        TripEntity bundle = (TripEntity) getArguments().getSerializable("trip_info");
        Log.e("Get BUNDLE ---->", bundle.getName());


        pref = thiscontext.getSharedPreferences("TripackerPref", Context.MODE_PRIVATE);

        setUpViewById(view);

        Log.e("From Session", "-------> " + pref.getString("name", null));
        Log.e("From Session", "-------> " + pref.getString("cookies", null));

        // Or even append an entire new collection
        // Fetching some data, data has now returned
        // If data was JSON, convert to ArrayList of User objects.
        //JSONArray jsonArray = ...;
        //ArrayList<User> newUsers = User.fromJson(jsonArray)
        //adapter.addAll(newUsers);

        // Google Map Directions API
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map_direction);
        googleMap = mapFragment.getMap();


        MarkerOptions options = new MarkerOptions();
        options.position(Spot1_GPS);
        options.position(Spot2_GPS);
        options.position(Spot3_GPS);
        googleMap.addMarker(options);

        String url = getMapsApiDirectionsUrl();
        ReadTask downloadTask = new ReadTask();
        downloadTask.execute(url);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Spot2_GPS,
                13));
        addMarkers();

        //Locations

        return view;
    }

    private void setUpViewById(View view){


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult ", "requestCode= " + requestCode + "resultCode= " + resultCode);
        if (requestCode == REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(thiscontext, "Profile Updated Successfully", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(thiscontext, "Cancel Update", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void renderUser(UserEntity user) {
        username_view.setText(pref.getString("name", null));
        Picasso
                .with(thiscontext)
                .load("http://weknowyourdreamz.com/images/minions/minions-07.jpg")
                .fit() // will explain later
                .into((ImageView) profile_pic);
    }

    @Override
    public void renderTrip(ArrayList<TripEntity> TripEntities) {
        // Construct the data source
        ArrayList<TripEntity> arrayOfTrips = new ArrayList<TripEntity>();
        // Create the adapter to convert the array to views
        TripsTimelineAdapter adapter = new TripsTimelineAdapter(thiscontext, arrayOfTrips);
        // Attach the adapter to a ListView
        trip_listView.setAdapter(adapter);


    }

    @Override
    public void showLoading() {
        progressDialog = new ProgressDialog(thiscontext,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return null;
    }

    //Google Maps
    private String getMapsApiDirectionsUrl() {
        String waypoints = "waypoints=optimize:true|"
                + Spot1_GPS.latitude + "," + Spot1_GPS.longitude
                + "|" + "|" + Spot2_GPS.latitude + ","
                + Spot2_GPS.longitude + "|" + Spot3_GPS.latitude + ","
                + Spot3_GPS.longitude;

        String output = "json";
        String key = "AIzaSyC25VtN-MdlR24RTttecKVurefMWiKoubU";
        String base_url = "https://maps.googleapis.com/maps/api/directions/";

        String origin_lat = "37.4219999";
        String origin_long = "-122.0840575";
        String origin_url = origin_lat + "%2C" + origin_long;

        String destination_lat = "37.41043";
        String destination_long = "-122.059753";
        String destination_url = destination_lat + "%2C" + destination_long;

        String waypoints_url = "via:37.4852152%2C-122.2363548" + "%7C" + "via:37.4852151%2C-122.2363547";

        String url = base_url + output + "?" +
                "origin="+ origin_url + "&" +
                "destination="+ destination_url +
                "&"+"waypoints=" + waypoints_url +
                "&" + "key=" + key;
        return url;
    }

    private void addMarkers() {
        if (googleMap != null) {
            googleMap.addMarker(new MarkerOptions().position(Spot2_GPS)
                    .title("First Point"));
            googleMap.addMarker(new MarkerOptions().position(Spot1_GPS)
                    .title("Second Point"));
            googleMap.addMarker(new MarkerOptions().position(Spot3_GPS)
                    .title("Third Point"));
        }
    }

    private class ReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                PathHttpConnection http = new PathHttpConnection();
                data = http.readUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }
    }

    private class ParserTask extends
            AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);

                // Get estimated time duration
                JSONArray legs = ((JSONObject) (jObject.getJSONArray("routes").get(0))).getJSONArray("legs");
                String time = ((JSONObject)legs.get(0)).getJSONObject("duration").getString("text");
                Log.e("duration------>", time);
                tripEntity.setEstimateDuration(time);

                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(5);
                polyLineOptions.color(Color.BLUE);
            }

            //Duration
            durationShow = (TextView) getView().findViewById(R.id.tripDuration);
            durationShow.setText(tripEntity.getDuration());

            googleMap.addPolyline(polyLineOptions);
            Log.e("PolylnAddedToMap --->",polyLineOptions.toString());
        }
    }

}
