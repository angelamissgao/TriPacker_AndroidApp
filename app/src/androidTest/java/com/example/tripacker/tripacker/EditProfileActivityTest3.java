package com.example.tripacker.tripacker;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tripacker.tripacker.view.activity.EditProfileActivity;

public class EditProfileActivityTest3 extends ActivityInstrumentationTestCase2<EditProfileActivity> {

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

    public EditProfileActivityTest3() {
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
