package com.example.tripacker.tripacker;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

import com.example.tripacker.tripacker.view.activity.SignupActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.KeyEvent;

import android.widget.Button;
import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.view.activity.ViewProfileActivity;

import android.widget.EditText;
import android.widget.Spinner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ViewProfileActivityTest extends ActivityInstrumentationTestCase2<ViewProfileActivity> {

    private ViewProfileActivity viewprofileInstance;

    private static final String USERNAME_TEXT = "eeewww";
    private static final String NAME_TEXT = "Eileen W";
    private static final String PASSWORD_TEXT = "111111";
    private static final String NEW_INVALID_TEXT = "ei";




    private Instrumentation mInstrumentation;

    public ViewProfileActivityTest() {
        super(ViewProfileActivity.class);
    }


    @Override
    protected  void setUp() throws Exception{
        super.setUp();

        setActivityInitialTouchMode(true);

        mInstrumentation = getInstrumentation();

        viewprofileInstance = (ViewProfileActivity) getActivity();



    }

    public void testPreconditions() {

    }


    public void testActivityExists() {
        assertNotNull((ViewProfileActivity) getActivity());
    }







}
