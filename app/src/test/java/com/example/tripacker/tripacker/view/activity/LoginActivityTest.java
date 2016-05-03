package com.example.tripacker.tripacker.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import com.example.tripacker.tripacker.R;
import android.widget.EditText;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>{

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    public void testActivityExists() {
        LoginActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testLogin() {
        LoginActivity activity = getActivity();

        // Type name in text input
        // ----------------------

        final EditText usernameText =
                (EditText) activity.findViewById(R.id.input_username);
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

        // Tap "Log in" button
        // ----------------------
        Button loginButton = (Button) activity.findViewById(R.id.btn_login);
        TouchUtils.clickView(this, loginButton);

        // Verify login
        // ----------------------
    /*
        TextView greetMessage = (TextView) activity.findViewById(R.id.message_text_view);
        String actualText = greetMessage.getText().toString();
        assertEquals("Hello, Jake!", actualText);
        */
        
    }

}