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
import com.example.tripacker.tripacker.view.activity.EditProfileActivity;

import android.widget.EditText;
import android.widget.Spinner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EditProfileActivityTest extends ActivityInstrumentationTestCase2<EditProfileActivity> {

    private EditProfileActivity editprofileInstance;

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


    private Instrumentation mInstrumentation;

    public EditProfileActivityTest() {
        super(EditProfileActivity.class);
    }


    @Override
    protected  void setUp() throws Exception{
        super.setUp();

        setActivityInitialTouchMode(true);

        mInstrumentation = getInstrumentation();

        editprofileInstance = (EditProfileActivity) getActivity();


        usernameEtxt =
                (EditText) editprofileInstance.findViewById(R.id.row1editText);
        locationEtxt =
                (EditText) editprofileInstance.findViewById(R.id.row2editText);
        emailEtxt =
                (EditText) editprofileInstance.findViewById(R.id.row3editText);
        birthdayEtxt =
                (EditText) editprofileInstance.findViewById(R.id.row4editText);
        phoneEtxt =
                (EditText) editprofileInstance.findViewById(R.id.row6editText);
        introductionEtxt =
                (EditText) editprofileInstance.findViewById(R.id.row7editText);
        gender_spinner =
                (Spinner) editprofileInstance.findViewById(R.id.row5spinner);
    }

    public void testPreconditions() {
        assertNotNull(usernameEtxt);
        assertNotNull(locationEtxt);
        assertNotNull(emailEtxt);
        assertNotNull(birthdayEtxt);
        assertNotNull(phoneEtxt);
        assertNotNull(introductionEtxt);
        assertNotNull(gender_spinner);
    }


    public void testActivityExists() {
        assertNotNull((EditProfileActivity) getActivity());
    }







}
