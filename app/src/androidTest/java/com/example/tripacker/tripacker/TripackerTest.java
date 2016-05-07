package com.example.tripacker.tripacker;


import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.Gravity;
import android.view.KeyEvent;

import android.widget.Button;
import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.view.activity.EditProfileActivity;
import com.example.tripacker.tripacker.view.activity.LoginActivity;
import com.example.tripacker.tripacker.view.activity.MainActivity;
import com.example.tripacker.tripacker.view.activity.SpotEditActivity;
import com.example.tripacker.tripacker.view.activity.TripActivity;
import com.example.tripacker.tripacker.view.activity.TripEditSpotActivity;
import com.example.tripacker.tripacker.view.activity.ViewFollowingActivity;
import com.example.tripacker.tripacker.view.activity.ViewProfileActivity;

import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Predicates.not;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
public class TripackerTest extends ActivityInstrumentationTestCase2<MainActivity>{



    private static final String USERNAME_TEXT = "eeewww";
    private static final String NAME_TEXT = "Eileen W";
    private static final String PASSWORD_TEXT = "111111";
    private static final String NEW_INVALID_TEXT = "ei";

    private EditText usernameText;
    private EditText nameText;
    private EditText passwordText;
    private Button signupButton;
    private Instrumentation mInstrumentation;
    private Activity mInstance;


    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    public TripackerTest() {
        super("com.example.tripacker.tripacker", MainActivity.class);
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);

        mInstrumentation = getInstrumentation();
        mInstance = getActivity();
    }


    @Test
    public void testActivityExists() {
        assertNotNull(mInstance);
    }

    @Test
    public void testLaunchApp_shouldStartMainActivity() {
    //    Intent expectedIntent = new Intent(mainActivityRule.getActivity(), LoginActivity.class);
    //    mainActivityRule.getActivity().startActivity(expectedIntent);
        assertEquals(mInstance.getClass(), MainActivity.class);
    }
    @Test
    public void testLogin_shouldLoginUser_FAILED_USERNAME_PASSWORD_NOT_MATCHED() throws Exception{

        onView(withId(R.id.input_username)).perform(typeText("eileen"));
        onView(withId(R.id.input_password)).perform(typeText("1111"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText("Username or password not match!")).inRoot(withDecorView(not(mInstance.getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testLogin_shouldSignupUser_FAILED_DUPLICATED_USERNAME() throws Exception{
        onView(withId(R.id.link_signup)).perform(click());
        onView(withId(R.id.input_username)).perform(typeText("eileen"));
        onView(withId(R.id.input_name)).perform(typeText("Eileen"));
        onView(withId(R.id.input_password)).perform(typeText("111111"));
        onView(withId(R.id.btn_signup)).perform(click());
        onView(withText("User name duplicated!")).inRoot(withDecorView(not(mInstance.getWindow().getDecorView()))).check(matches(isDisplayed()));

    }

    @Test
    public void testLogin_shouldSignupUser_FAILED_INPUT_MISSING() throws Exception{
        onView(withId(R.id.link_signup)).perform(click());
        onView(withId(R.id.input_username)).perform(typeText("eileen"));
        onView(withId(R.id.input_password)).perform(typeText("111111"));
        onView(withId(R.id.btn_signup)).perform(click());
        onView(withText("Invalid input")).inRoot(withDecorView(not(mInstance.getWindow().getDecorView()))).check(matches(isDisplayed()));

    }

    @Test
    public void testLogin_shouldSignupUser() throws Exception{
        onView(withId(R.id.link_signup)).perform(click());
        onView(withId(R.id.input_username)).perform(typeText("eeewwwww"));
        onView(withId(R.id.input_name)).perform(typeText("Eileen Weiii"));
        onView(withId(R.id.input_password)).perform(typeText("111111"));
        onView(withId(R.id.btn_signup)).perform(click());


    }


    @Test
    public void testLogin_shouldLoginUser() throws Exception{
        onView(withId(R.id.input_username)).perform(typeText("eileen"));
        onView(withId(R.id.input_password)).perform(typeText("111111"));
        onView(withId(R.id.btn_login)).perform(click());
    }

    @Test
    public void testTripFragment_shouldStartTripFragment() throws Exception{
        int tab_id = ((int[])mInstance.getResources().getIntArray(R.array.topNavItems))[2];
        //onView(withId(tab_id)).perform(click());
    }


    @Test
    public void testViewProfile_shouldstartEditProfileActivity() {
        Context targetContext = mInstance.getApplicationContext();
        Intent intent = new Intent(targetContext, EditProfileActivity.class);


        mainActivityRule.launchActivity(intent);
    }

    @Test
    public void testViewProfile_shouldstartViewProfileActivity() {
        Context targetContext = mInstance.getApplicationContext();
        Intent intent = new Intent(targetContext, ViewProfileActivity.class);
        intent.putExtra("profile_id", 15);

        mainActivityRule.launchActivity(intent);
    }

    @Test
    public void testViewProfile_shouldstartTripActivity() {
        Context targetContext = mInstance.getApplicationContext();
        Intent intent = new Intent(targetContext, TripActivity.class);

        mainActivityRule.launchActivity(intent);
    }

    @Test
    public void testViewProfile_shouldstartTripEditActivity() {
        Context targetContext = mInstance.getApplicationContext();
        Intent intent = new Intent(targetContext, TripEditSpotActivity.class);
        ArrayList<Integer> trip_info = new ArrayList<>();
        trip_info.add(5);
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("tripID", trip_info);
        intent.putExtras(bundle);
        mainActivityRule.launchActivity(intent);
    }

    @Test
    public void testViewProfile_shouldstartSpotEditActivity() {
        Context targetContext = mInstance.getApplicationContext();
        Intent intent = new Intent(targetContext, SpotEditActivity.class);
        ArrayList<Integer> trip_info = new ArrayList<>();
        trip_info.add(5);
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("spotId", trip_info);
        intent.putExtras(bundle);
        mainActivityRule.launchActivity(intent);
    }

    @Test
    public void testViewProfile_shouldViewFollowingActivity() {
        Context targetContext = mInstance.getApplicationContext();
        Intent intent = new Intent(targetContext, ViewFollowingActivity.class);
        intent.putExtra("profile_id", 17);
        mainActivityRule.launchActivity(intent);
    }

}
