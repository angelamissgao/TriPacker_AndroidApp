package com.example.tripacker.tripacker;


import com.example.tripacker.tripacker.view.activity.EditProfileActivity;
import com.example.tripacker.tripacker.view.activity.MainActivity;
import com.example.tripacker.tripacker.view.activity.SpotCreateActivity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TripackerTest.class,
        EditProfileActivityTest.class,
        SpotCreateActivityTest.class,
        TripCreateActivityTest.class
})

public class UnitTestSuite {
}
