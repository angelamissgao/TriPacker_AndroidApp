package com.example.tripacker.tripacker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import android.test.mock.MockContext;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;


import android.test.suitebuilder.annotation.SmallTest;

import com.example.tripacker.tripacker.UserSessionManager;

import java.io.IOException;



/**
 * Unit tests for the {@link SharedPreferencesHelper} that mocks {@link SharedPreferences}.
 */
@SmallTest
@RunWith(MockitoJUnitRunner.class)
public class UserSessionManagerTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    Context context;

    private static final String TEST_NAME = "Test User";

    private static final String TEST_USERNAME = "testuser";

    private static final String TEST_UID = "1";

    private static final String TEST_COOKIES = "JSESSIONID=1qbgt9gxrau7e11hawtq8evatv;Path=/";


    private UserSessionManager mMockUserSessionManager;

    private UserSessionManager mMockBrokenUserSessionManager;

    @Mock
    SharedPreferences mMockSharedPreferences;

    @Mock
    SharedPreferences mMockBrokenSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockEditor;

    @Mock
    SharedPreferences.Editor mMockBrokenEditor;

  /*  @Before
    public void initMocks() {
        context = new MockContext();


        // Create a mocked SharedPreferences.
        //mMockUserSessionManager = new UserSessionManager(context);
        //mMockUserSessionManager = UserSessionManager.getSingleInstance(context);

        // Create a mocked SharedPreferences that fails at saving data.
        //mMockBrokenUserSessionManager = createBrokenUserSessionManager();
    }*/

    @Test
    public void UserSessionManager_CreateUserLoginSession() {
        // Save the personal information to SharedPreferences
        context = new MockContext();
        mMockUserSessionManager = UserSessionManager.getSingleInstance(context);
        mMockUserSessionManager.createUserLoginSession(TEST_USERNAME, TEST_NAME, TEST_UID, TEST_COOKIES);
        boolean success = mMockUserSessionManager.isUserLoggedIn();

        assertThat("Checking that User Log in... returns true",
                success, is(true));


        // Make sure both written and retrieved personal information are equal.
        assertThat("Checking that user name has been persisted and read correctly",
                TEST_NAME,
                is(equalTo(mMockUserSessionManager.getUserDetails().get("nickname"))));
        assertThat("Checking that user username has been persisted and read correctly",
                TEST_NAME,
                is(equalTo(mMockUserSessionManager.getUserDetails().get("username"))));
        assertThat("Checking that user id has been persisted and read correctly",
                TEST_NAME,
                is(equalTo(mMockUserSessionManager.getUserDetails().get("uid"))));
        assertThat("Checking that user cookie has been persisted and read correctly",
                TEST_NAME,
                is(equalTo(mMockUserSessionManager.getUserDetails().get("cookies"))));
    }

    //@Test
   /* public void UserSessionManager_CreateUserLoginSessionFailed_ReturnsFalse() {
        // Read personal information from a broken SharedPreferencesHelper
        mMockUserSessionManager.createUserLoginSession(TEST_USERNAME, TEST_NAME, TEST_UID, TEST_COOKIES);
        boolean success = mMockUserSessionManager.isUserLoggedIn();
        assertThat("Makes sure User Log in returns false", success,
                is(false));
    }*/

    /**
     * Creates a mocked UserSessionManager.
     */
     /* private void createMockUserSessionManager() {
         // UserSessionManager sessionManager = UserSessionManager(context);
        // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written
        // correctly.
        when(UserSessionManager.getSingleInstance(context).pref.getString(eq(UserSessionManager.KEY_NICKNAME), anyString()))
                .thenReturn(TEST_NAME);
          when(UserSessionManager.getSingleInstance(context).pref.getString(eq(UserSessionManager.KEY_USERNAME), anyString()))
                  .thenReturn(TEST_USERNAME);
          when(UserSessionManager.getSingleInstance(context).pref.getString(eq(UserSessionManager.KEY_UID), anyString()))
                  .thenReturn(TEST_UID);
          when(UserSessionManager.getSingleInstance(context).pref.getString(eq(UserSessionManager.KEY_COOKIES), anyString()))
                  .thenReturn(TEST_COOKIES);



        // Mocking a successful commit.
        when(UserSessionManager.getSingleInstance(context).editor.commit()).thenReturn(true);

        // Return the MockEditor when requesting it.
        when(UserSessionManager.getSingleInstance(context).pref.edit()).thenReturn(UserSessionManager.getSingleInstance(context).editor);
        return UserSessionManager.getSingleInstance(context);
    }*/

    /**
     * Creates a mocked UserSessionManager that fails when writing.
     */
/*
    private UserSessionManager createBrokenUserSessionManager() {
        // Mocking a commit that fails.
      when(mMockBrokenEditor.commit()).thenReturn(false);

        // Return the broken MockEditor when requesting it.
        when(mMockBrokenSharedPreferences.edit()).thenReturn(mMockBrokenEditor);
        return new UserSessionManager(context, mMockBrokenSharedPreferences);
    }*/

}