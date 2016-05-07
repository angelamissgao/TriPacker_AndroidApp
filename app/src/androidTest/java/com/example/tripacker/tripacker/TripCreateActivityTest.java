package com.example.tripacker.tripacker;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tripacker.tripacker.view.activity.TripCreateActivity;

public class TripCreateActivityTest extends ActivityInstrumentationTestCase2<TripCreateActivity> {

    private TripCreateActivity instance;

    private static final String USERNAME_TEXT = "eeewww";
    private static final String NAME_TEXT = "Eileen W";
    private static final String PASSWORD_TEXT = "111111";
    private static final String NEW_INVALID_TEXT = "ei";

    private EditText tripNameInput;
    private EditText beginDateInput;
    private EditText endDateInput;


    private Instrumentation mInstrumentation;

    public TripCreateActivityTest() {
        super(TripCreateActivity.class);
    }


    @Override
    protected  void setUp() throws Exception{
        super.setUp();

        setActivityInitialTouchMode(true);

        mInstrumentation = getInstrumentation();

        instance = (TripCreateActivity) getActivity();

        tripNameInput =
                (EditText) instance.findViewById(R.id.tripNameInput);
        beginDateInput =
                (EditText) instance.findViewById(R.id.startDate);
        endDateInput =
                (EditText) instance.findViewById(R.id.endDate);

    }

    public void testPreconditions() {
        assertNotNull(tripNameInput);
        assertNotNull(beginDateInput);
        assertNotNull(endDateInput);
    }


    public void testActivityExists() {
        assertNotNull((TripCreateActivity) getActivity());
    }







}
