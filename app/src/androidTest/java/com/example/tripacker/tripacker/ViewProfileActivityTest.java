package com.example.tripacker.tripacker;
import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.tripacker.tripacker.view.activity.ViewFollowingActivity;
import com.example.tripacker.tripacker.view.activity.ViewProfileActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasType;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class ViewProfileActivityTest {
    private final static String TEST_PROFILE_ID = "1";

    @Rule public IntentsTestRule<ViewProfileActivity> activityRule =
            new IntentsTestRule<>(ViewProfileActivity.class);

    @Before public void stubFollowingIntent() {
        Intent intent = new Intent();
        intent.putExtra("profile_id", TEST_PROFILE_ID);
        ActivityResult result = new ActivityResult(Activity.RESULT_OK, intent);
        intending(hasComponent(ViewFollowingActivity.class.getName()));
    }

    @Test public void followingIntent_viewIsSet() {
        //Check to make sure the Uri field is empty
        onView(withId(R.id.follow_username)).check(matches(withText("")));

        //Start contact picker
        onView(withId(R.id.follow_nickname)).check(matches(withText("")));


    }
}
