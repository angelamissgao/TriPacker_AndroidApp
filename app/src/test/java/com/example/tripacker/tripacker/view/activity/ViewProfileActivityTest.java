package com.example.tripacker.tripacker.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.view.activity.ViewFollowingActivity;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Spinner;
import android.content.Intent;
import android.test.ServiceTestCase;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.rule.ServiceTestRule;
import android.app.Service;





public class ViewProfileActivityTest extends ActivityInstrumentationTestCase2<ViewProfileActivity>{



    public ViewProfileActivityTest() {
        super(ViewProfileActivity.class);
    }

    public void testActivityExists() {
        ViewProfileActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testViewProfile() {
        ViewProfileActivity activity = getActivity();

        // Type name in text input
        // ----------------------

        final TextView username_view = (TextView) activity.findViewById(R.id.user_name);
        final TextView tripnum_view = (TextView) activity.findViewById(R.id.trip_num);
        final TextView followingnum_view = (TextView) activity.findViewById(R.id.following_num);
        final TextView followernum_view = (TextView) activity.findViewById(R.id.follower_num);


        
    }

    public void testViewFollowing() {
        ViewProfileActivity activity = getActivity();


        // Tap "# of Following" button
        // ----------------------
        LinearLayout viewFollowingBtn = (LinearLayout) activity.findViewById(R.id.view_following_btn);
        TouchUtils.clickView(this, viewFollowingBtn);

        // Verify Following activity intent
        // ----------------------
  /*      Intent serviceIntent =
                new Intent(InstrumentationRegistry.getTargetContext(),
                        ViewFollowingActivity.class);

        // Data can be passed to the service via the Intent.
        serviceIntent.putExtra("profile_id", 2);

        // Bind the service and grab a reference to the binder.
        IBinder binder = mServiceRule.bindService(serviceIntent);

        // Get the reference to the service, or you can call
        // public methods on the binder directly.
        LocalService service =
                ((LocalService.LocalBinder) binder).getService();

        // Verify that the service is working correctly.
        assertThat(service.getProfile_id(), is(any(Integer.class)));
*/

    }

}