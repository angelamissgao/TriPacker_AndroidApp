package com.example.tripacker.tripacker;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tripacker.tripacker.view.activity.MainActivity;
import com.example.tripacker.tripacker.view.activity.TripActivity;
import com.example.tripacker.tripacker.view.activity.TripViewActivity;

import org.junit.Rule;
import org.junit.Test;

public class TripActivityTest extends ActivityInstrumentationTestCase2<TripActivity> {

    private TripActivity instance;

    private static final String USERNAME_TEXT = "eeewww";
    private static final String NAME_TEXT = "Eileen W";
    private static final String PASSWORD_TEXT = "111111";
    private static final String NEW_INVALID_TEXT = "ei";

    private EditText usernameEtxt;
    private EditText locationEtxt;
    private EditText emailEtxt;
    private EditText birthdayEtxt;
    private EditText phoneEtxt;
    private EditText introductionEtxt;
    private Spinner gender_spinner;

    @Rule
    public ActivityTestRule<TripActivity> mainActivityRule = new ActivityTestRule<>(
            TripActivity.class);


    private Instrumentation mInstrumentation;

    public TripActivityTest() {
        super(TripActivity.class);
    }


    @Override
    protected  void setUp() throws Exception{
        super.setUp();

        setActivityInitialTouchMode(true);

        mInstrumentation = getInstrumentation();

        instance = (TripActivity) getActivity();

    }

    public void testPreconditions() {

    }


    public void testActivityExists() {
        assertNotNull((TripActivity) getActivity());
    }


    @Test
    public void testViewTrip_shouldstartTripViewActivity() {
        Context targetContext = instance.getApplicationContext();
        Intent intent = new Intent(targetContext, TripViewActivity.class);
        intent.putExtra("tripId", 5);
        mainActivityRule.launchActivity(intent);
    }




}
