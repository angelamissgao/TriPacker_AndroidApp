package com.example.tripacker.tripacker;

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
import com.example.tripacker.tripacker.view.activity.MainActivity;
import com.example.tripacker.tripacker.view.activity.SignupActivity;

import android.widget.EditText;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class SignupActivityTest extends ActivityInstrumentationTestCase2<SignupActivity>{

    private SignupActivity signupInstance;

    private static final String USERNAME_TEXT = "eeewww";
    private static final String NAME_TEXT = "Eileen W";
    private static final String PASSWORD_TEXT = "111111";
    private static final String NEW_INVALID_TEXT = "ei";

    private EditText usernameText;
    private EditText nameText;
    private EditText passwordText;
    private Button signupButton;
    private Instrumentation mInstrumentation;

    public SignupActivityTest() {
        super(SignupActivity.class);
    }


    @Override
    protected  void setUp() throws Exception{
        super.setUp();

        setActivityInitialTouchMode(true);

        mInstrumentation = getInstrumentation();

        signupInstance = (SignupActivity) getActivity();
        usernameText =
                (EditText) signupInstance.findViewById(R.id.input_username);
        nameText =
                (EditText) signupInstance.findViewById(R.id.input_name);
        passwordText =
                (EditText) signupInstance.findViewById(R.id.input_password);
        signupButton = (Button) signupInstance.findViewById(R.id.btn_signup);
    }

    public void testPreconditions() {
        assertNotNull(usernameText);
        assertNotNull(nameText);
        assertNotNull(passwordText);
        assertNotNull(signupButton);
    }


    public void testActivityExists() {
        assertNotNull(signupInstance);
    }


    @UiThreadTest
    public void testSignup() throws Exception {

        nameText.requestFocus();
        nameText.setText(NAME_TEXT);

        usernameText.requestFocus();
        usernameText.setText(USERNAME_TEXT);

        passwordText.requestFocus();
        passwordText.setText(PASSWORD_TEXT);


        //click the signup button
    //    signupButton.performClick();
        //   signupButton.performClick();
    //    onView(withId(R.id.btn_signup)).perform(click());

        // Close the activity
        //signupInstance.finish();


       // getInstrumentation().waitForIdleSync();
       // assertEquals("Text incorrect", NEW_INVALID_TEXT, usernameText.getText().toString());

    }



}