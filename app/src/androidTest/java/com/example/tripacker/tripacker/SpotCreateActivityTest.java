package com.example.tripacker.tripacker;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tripacker.tripacker.view.activity.EditProfileActivity;
import com.example.tripacker.tripacker.view.activity.SpotCreateActivity;

public class SpotCreateActivityTest extends ActivityInstrumentationTestCase2<SpotCreateActivity> {

    private SpotCreateActivity instance;

    private static final String USERNAME_TEXT = "eeewww";
    private static final String NAME_TEXT = "Eileen W";
    private static final String PASSWORD_TEXT = "111111";
    private static final String NEW_INVALID_TEXT = "ei";

    private EditText spotName;
    private EditText spotAddress;
    private EditText spotDescription;


    private Instrumentation mInstrumentation;

    public SpotCreateActivityTest() {
        super(SpotCreateActivity.class);
    }


    @Override
    protected  void setUp() throws Exception{
        super.setUp();

        setActivityInitialTouchMode(true);

        mInstrumentation = getInstrumentation();

        instance = (SpotCreateActivity) getActivity();

        spotName =
                (EditText) instance.findViewById(R.id.tripNameInput);
        spotAddress =
                (EditText) instance.findViewById(R.id.startDate);
        spotDescription =
                (EditText) instance.findViewById(R.id.endDate);

    }

    public void testPreconditions() {
        assertNotNull(spotName);
        assertNotNull(spotAddress);
        assertNotNull(spotDescription);
    }


    public void testActivityExists() {
        assertNotNull((SpotCreateActivity) getActivity());
    }







}
