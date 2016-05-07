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
import com.example.tripacker.tripacker.view.activity.LoginActivity;

import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>{

    private LoginActivity loginInstance;

    private static final String USERNAME_TEXT = "eeewww";
    private static final String PASSWORD_TEXT = "111111";
    private static final String NEW_INVALID_TEXT = "ei";

    private EditText usernameText;
    private EditText passwordText;
    private Button loginButton;
    private TextView signupLink;
    private Instrumentation mInstrumentation;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }


    @Before
    protected  void setUp() throws Exception{
        super.setUp();

        setActivityInitialTouchMode(true);

        mInstrumentation = getInstrumentation();

        loginInstance = (LoginActivity) getActivity();
        usernameText =
                (EditText) loginInstance.findViewById(R.id.input_username);

        passwordText =
                (EditText) loginInstance.findViewById(R.id.input_password);
        loginButton = (Button) loginInstance.findViewById(R.id.btn_login);
        signupLink = (TextView) loginInstance.findViewById(R.id.link_signup);
    }
    @Test
    public void testPreconditions() {
        assertNotNull(usernameText);
        assertNotNull(passwordText);
        assertNotNull(loginButton);
        assertNotNull(signupLink);
    }

    @Test
    public void testActivityExists() {
        assertNotNull(loginInstance);
    }
    @Test
    public void testLogin() throws Exception {


        onView(withId(R.id.input_username)).perform(typeText("eileen"));
        onView(withId(R.id.input_password)).perform(typeText("111111"));
        onView(withId(R.id.btn_login)).perform(click());

        //click the login button
        //   loginButton.performClick();

        // Close the activity
        //signupInstance.finish();


        // getInstrumentation().waitForIdleSync();
        // assertEquals("Text incorrect", NEW_INVALID_TEXT, usernameText.getText().toString());

    }



 /*   @UiThreadTest
    public void testGoToSignUp() throws Exception {

        // set text
        loginInstance.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                //click the login button
             //   signupLink.performClick();
            }
        });
        //signupInstance.finish();

    }*/





}