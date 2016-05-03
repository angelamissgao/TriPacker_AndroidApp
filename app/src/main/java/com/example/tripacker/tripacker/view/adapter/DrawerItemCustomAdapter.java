package com.example.tripacker.tripacker.view.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.UserSessionManager;
import com.example.tripacker.tripacker.view.activity.MainActivity;
import com.example.tripacker.tripacker.view.activity.TripActivity;
import com.example.tripacker.tripacker.view.activity.ViewFollowingActivity;
import com.example.tripacker.tripacker.view.fragment.ProfilePageFragment;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class DrawerItemCustomAdapter extends RecyclerView.Adapter<DrawerItemCustomAdapter.ViewHolder> implements AsyncCaller {
    String TAG = "drawer item";
    String[] titles;
    TypedArray icons;
    Context context;

    // The default constructor to receive titles,icons and context from MainActivity.
    public DrawerItemCustomAdapter(String[] titles , TypedArray icons , Context context){

        this.titles = titles;
        this.icons = icons;
        this.context = context;
    }



    /**
     *Its a inner class to RecyclerViewAdapter Class.
     *This ViewHolder class implements View.OnClickListener to handle click events.
     *If the itemType==1 ; it implies that the view is a single row_item with TextView and ImageView.
     *This ViewHolder describes an item view with respect to its place within the RecyclerView.
     *For every item there is a ViewHolder associated with it .
     */

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


        TextView  navTitle;
        ImageView navIcon;
        Context context;

        ImageView profile;
        TextView name;
        TextView username;

        public ViewHolder(View drawerItem , int itemType , Context context){

            super(drawerItem);
            this.context = context;
            drawerItem.setOnClickListener(this);
            if(itemType==1){
                navTitle = (TextView) itemView.findViewById(R.id.rowText);
                navIcon = (ImageView) itemView.findViewById(R.id.rowIcon);
            }else if (itemType==0) {
                name = (TextView) itemView.findViewById(R.id.name);         // Creating Text View object from header.xml for name
                username = (TextView) itemView.findViewById(R.id.username);       // Creating Text View object from header.xml for email
                profile = (ImageView) itemView.findViewById(R.id.circleView);//
            }
        }

        /**
         *This defines onClick for every item with respect to its position.
         */

        @Override
        public void onClick(View v) {

            MainActivity mainActivity = (MainActivity)context;
            mainActivity.mDrawerLayout.closeDrawers();
            FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
            Log.e("Navi ", "Click " + getPosition());
            switch (getPosition()){
                case 1:

                    break;
                case 2:

                    break;
                case 3:
                    Intent tripIntent = new Intent(context, TripActivity.class);
                    context.startActivity(tripIntent);
                    break;
                case 4:

                    break;
                case 5:
                    logout();
                    break;
            }
        }
    }

    /**
     *This is called every time when we need a new ViewHolder and a new ViewHolder is required for every item in RecyclerView.
     *Then this ViewHolder is passed to onBindViewHolder to display items.
     */

    @Override
    public DrawerItemCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType==1){
            View itemLayout =   layoutInflater.inflate(R.layout.drawer_item_row,null);
            return new ViewHolder(itemLayout,viewType,context);
        }
        else if (viewType==0) {
            View itemHeader = layoutInflater.inflate(R.layout.drawer_header,null);
            return new ViewHolder(itemHeader,viewType,context);
        }



        return null;
    }

    /**
     *This method is called by RecyclerView.Adapter to display the data at the specified position.
     *This method should update the contents of the itemView to reflect the item at the given position.
     *So here , if position!=0 it implies its a row_item and we set the title and icon of the view.
     */

    @Override
    public void onBindViewHolder(DrawerItemCustomAdapter.ViewHolder holder, int position) {

        if(position!=0){
            holder.navTitle.setText(titles[position - 1]);
            holder.navIcon.setImageResource(icons.getResourceId(position-1,-1));
        }



    }

    /**
     *It returns the total no. of items . We +1 count to include the header view.
     *So , it the total count is 5 , the method returns 6.
     *This 6 implies that there are 5 row_items and 1 header view with header at position zero.
     */

    @Override
    public int getItemCount() {
        return titles.length+1;
    }


    /**
     *This methods returns 0 if the position of the item is '0'.
     *If the position is zero its a header view and if its anything else
     *its a row_item with a title and icon.
     */

    @Override
    public int getItemViewType(int position) {
        if(position==0)return 0;
        else return 1;
    }

    public void logout(){
        UserSessionManager.getSingleInstance(context).logoutUser();
        //From session
        Log.e(TAG + " sess", "->" + UserSessionManager.getSingleInstance(context).getUserDetails().get("username"));
        Log.e(TAG+" sess", "->" + UserSessionManager.getSingleInstance(context).getUserDetails().get("uid"));
        Log.e(TAG+" sess", "->" + UserSessionManager.getSingleInstance(context).getUserDetails().get("cookies"));

        Log.e("User Logout", "-------> logout");

        // the request
        try{
   //         APIConnection.SetAsyncCaller(this, context);
   //         Log.e("User Logout", "-------> Call API");
   //         APIConnection.signoutUser(UserSessionManager.getSingleInstance(context).getUserDetails().get("uid"), null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) throws JSONException {


        String response = result.toString();

        JSONTokener tokener = new JSONTokener(response);
        try {
            JSONObject finalResult = new JSONObject(tokener);
            Log.i("LOG OUT", "RESPONSE CODE= " + finalResult.getString("success"));


            if(finalResult.getString("success").equals("true")){


                Log.e("Log out ->", " Now log out and start the activity login");
                UserSessionManager.getSingleInstance(context).logoutUser();

                Log.e("Cookie now->", ""+UserSessionManager.getSingleInstance(context).getCookies());

            }else{
                Log.e("Log out FAILED->", " CANNOT LOGOUT USER");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

