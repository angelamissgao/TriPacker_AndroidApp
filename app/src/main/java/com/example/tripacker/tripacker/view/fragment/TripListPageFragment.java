package com.example.tripacker.tripacker.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.SpotEntity;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.entity.UserEntity;
import com.example.tripacker.tripacker.entity.mapper.UserEntityJsonMapper;
import com.example.tripacker.tripacker.view.SpotListView;
import com.example.tripacker.tripacker.view.activity.SpotViewActivity;
import com.example.tripacker.tripacker.view.activity.TripCreateActivity;
import com.example.tripacker.tripacker.view.activity.TripEditSpotActivity;
import com.example.tripacker.tripacker.view.adapter.TripSpotTimelineAdapter;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.Serializable;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tiger
 * @since March 30, 2016 12:34 PM
 */
public class TripListPageFragment extends Fragment implements SpotListView {
    private static final String TAG = "TripListPageFragment";
    private Context thiscontext;
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final int REQUEST_EDIT = 0;
    private static final int RESULT_OK = 200;
    private static final int RESULT_NOTSAVED = 400;
    private static SharedPreferences pref;

    private ProgressDialog progressDialog;

    private ImageView profile_pic;
    private TextView username_view;
    private ListView trip_spotlistView;
    private ImageView editProfileButton;

    //TripEntity
    private TripEntity tripEntity;
    ArrayList<SpotEntity> spotsOfTrip = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();

        View view = inflater.inflate(R.layout.trip_list_fragment, container, false);

        //Get Trip
        tripEntity = (TripEntity) getArguments().getSerializable("trip_info");
        spotsOfTrip = tripEntity.getSpots();

        //get user ID
        pref = thiscontext.getSharedPreferences("TripackerPref", Context.MODE_PRIVATE);

        // Set Up List View
        setUpViewById(view);

        // Render data to view
        renderSpotList(spotsOfTrip);

        //Set a linearLayout to add buttons
        LinearLayout linearLayout = new LinearLayout(getActivity());

        // Set the layout full width, full height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL); //or VERTICAL

        Button addSpotButton = new Button(thiscontext);
        addSpotButton.setText("+ Add Spot");
        addSpotButton.setTextColor(getResources().getColor(R.color.white));
        addSpotButton.setBackgroundColor(getResources().getColor(R.color.primary));
        addSpotButton.setPadding(10, 10, 10, 10);

        addSpotButton.setLayoutParams(params);

        linearLayout.addView(addSpotButton);
        container.addView(linearLayout);

        Log.e(TAG, "-------> " + pref.getString("name", null));
        Log.e(TAG, "-------> " + pref.getString("cookies", null));
        pref.getString("uid", null);
        Log.e(TAG + " sess", "->" + pref.getString("uid", null));



        // Or even append an entire new collection
        // Fetching some data, data has now returned
        // If data was JSON, convert to ArrayList of User objects.
        //JSONArray jsonArray = ...;
        //ArrayList<User> newUsers = User.fromJson(jsonArray)
        //adapter.addAll(newUsers);


        //Add Spots to Trip by ownerID
        FloatingActionsMenu TripModifiation = (FloatingActionsMenu) view.findViewById(R.id.tripModify);
        FloatingActionButton ButtonAddTrip = (FloatingActionButton) view.findViewById(R.id.AddSpotInTrip);

        // Set Visibility of THe menu;
        if(Integer.parseInt(pref.getString("uid", null)) == tripEntity.getOwnerId()){
            TripModifiation.setVisibility(View.VISIBLE);
        } else {
            TripModifiation.setVisibility(View.INVISIBLE);
        }
        ButtonAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainInten = new Intent(getActivity(), TripEditSpotActivity.class);
                ArrayList<String> spot_info = new ArrayList<String>();
                //Todo: added spot json
                spot_info.add("user_id");
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("user_id", spot_info);
                mainInten.putExtras(bundle);

                startActivity(mainInten);
            }
        });

        return view;
    }

    private void setUpViewById(View view){
        trip_spotlistView = (ListView) view.findViewById(R.id.tripspotlistview);
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
    public void renderSpotList(ArrayList<SpotEntity> Spots) {
        TripSpotTimelineAdapter adapter = new TripSpotTimelineAdapter(thiscontext, Spots);
        trip_spotlistView.setAdapter(adapter);
        trip_spotlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent spotIntent = new Intent(getActivity(), SpotViewActivity.class);

                SpotEntity spotEntity = spotsOfTrip.get(position);
                ArrayList<String> spot_info = new ArrayList<String>();
                spot_info.add(spotEntity.getSpotId());

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("spotId", spot_info);
                spotIntent.putExtras(bundle);

                startActivity(spotIntent);
            }
        });
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
}
