package com.example.tripacker.tripacker.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import com.example.tripacker.tripacker.R;
import android.widget.EditText;

public class SignupActivityTest extends ActivityInstrumentationTestCase2<SignupActivity>{

    public SignupActivityTest() {
        super(SignupActivity.class);
    }

    public void testActivityExists() {
        SignupActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testSignup() {
        SignupActivity activity = getActivity();

        // Type name in text input
        // ----------------------

        final EditText usernameText =
                (EditText) activity.findViewById(R.id.input_username);
        final EditText nameText =
                (EditText) activity.findViewById(R.id.input_name);
        final EditText passwordText =
                (EditText) activity.findViewById(R.id.input_password);

        // Send string input value
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                usernameText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("eileen");

        // Tap "Sign up" button
        // ----------------------
        Button signupButton = (Button) activity.findViewById(R.id.btn_signup);
        TouchUtils.clickView(this, signupButton);

        // Verify Sign up
        // ----------------------
    /*
        TextView greetMessage = (TextView) activity.findViewById(R.id.message_text_view);
        String actualText = greetMessage.getText().toString();
        assertEquals("Hello, Jake!", actualText);
        */
        
    }

}