package com.example.tripacker.tripacker.view.activity;
/*
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.view.activity.EditProfileActivity;

import android.widget.EditText;
import android.widget.Spinner;

public class EditProfileActivityTest extends ActivityInstrumentationTestCase2<EditProfileActivity>{

    public EditProfileActivityTest() {
        super(EditProfileActivity.class);
    }

    public void testActivityExists() {
        EditProfileActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testEditProfile() {
        EditProfileActivity activity = getActivity();

        // Type name in text input
        // ----------------------

        final EditText usernameEtxt =
                (EditText) activity.findViewById(R.id.row1editText);
        final EditText locationEtxt =
                (EditText) activity.findViewById(R.id.row2editText);
        final EditText emailEtxt =
                (EditText) activity.findViewById(R.id.row3editText);
        final EditText birthdayEtxt =
                (EditText) activity.findViewById(R.id.row4editText);
        final EditText phoneEtxt =
                (EditText) activity.findViewById(R.id.row6editText);
        final EditText introductionEtxt =
                (EditText) activity.findViewById(R.id.row7editText);
        final Spinner gender_spinner =
                (Spinner) activity.findViewById(R.id.row5spinner);


        // Send string input value
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                usernameEtxt.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Eileen Weiii");

        // Tap "Done" button
        // ----------------------
        TouchUtils.clickView(this, activity.findViewById(R.id.action_done));

        // Verify edit profile
        // ----------------------
    /*
        TextView greetMessage = (TextView) activity.findViewById(R.id.message_text_view);
        String actualText = greetMessage.getText().toString();
        assertEquals("Hello, Jake!", actualText);
        */
        
//    }

//}