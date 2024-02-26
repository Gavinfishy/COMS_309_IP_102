package com.example.topg;

import org.hamcrest.Matcher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import androidx.test.platform.app.InstrumentationRegistry;


import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;




import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.core.app.ActivityScenario;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.google.android.material.datepicker.CompositeDateValidator.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.StringEndsWith.endsWith;

import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

// Mock the RequestServerForService class
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
public class TopGAppTests {

    private static final int SIMULATED_DELAY_MS = 500;

    /**
     * Start the server and run this test
     * THIS SIMULATES A COACH LOGGING IN
     */
    @Test
    public void SimulateCoachLogin(){
        ActivityScenario.launch(LoginActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.loginPageEnterUsername))
                .perform(ViewActions.typeText("MasterSnapper")); // Replace "username" with the actual username

        Espresso.onView(ViewMatchers.withId(R.id.loginPageEnterPass))
                .perform(ViewActions.typeText("password")); // Replace "password" with the actual password

        Espresso.closeSoftKeyboard();

        Espresso.onView(ViewMatchers.withId(R.id.LoginPageLoginButton))
                .perform(ViewActions.click());

        ActivityScenario.launch(CoachHomeActivity.class);
        Espresso.onView(ViewMatchers.withId(android.R.id.content))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    /**
     * Start the server and run this test
     * THIS SIMULATES A ATHLETE LOGGING IN
     */
    @Test
    public void SimulateAthleteLogin(){
        ActivityScenario.launch(LoginActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.loginPageEnterUsername))
                .perform(ViewActions.typeText("athlete3"));

        Espresso.onView(ViewMatchers.withId(R.id.loginPageEnterPass))
                .perform(ViewActions.typeText("password"));

        Espresso.closeSoftKeyboard();

        Espresso.onView(ViewMatchers.withId(R.id.LoginPageLoginButton))
                .perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ActivityScenario.launch(AthleteHomeActivity.class);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(android.R.id.content))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    /**
     * Start the server and run this test
     * THIS SIMULATES A GYM RAT LOGGING IN
     */
    @Test
    public void SimulateGymRatLogin(){
        ActivityScenario.launch(LoginActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.loginPageEnterUsername))
                .perform(ViewActions.typeText("BigPookie"));

        Espresso.onView(ViewMatchers.withId(R.id.loginPageEnterPass))
                .perform(ViewActions.typeText("password"));

        Espresso.closeSoftKeyboard();

        Espresso.onView(ViewMatchers.withId(R.id.LoginPageLoginButton))
                .perform(ViewActions.click());

        ActivityScenario.launch(GymRatHomeActivity.class);
        Espresso.onView(ViewMatchers.withId(android.R.id.content))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testViewUserProgressForGymRat() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ViewUserProgressActivity.class);
        intent.putExtra("username", "BigPookie");
        intent.putExtra("accountType", "Gym Rat");
        intent.putExtra("firstName", "Max");
        intent.putExtra("lastName", "Rohrer");

        ActivityScenario<ViewUserProgressActivity> scenario = ActivityScenario.launch(intent);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(withId(R.id.getGraphSpinnerForUser)).perform(click());

        Espresso.onData(anything())
                .atPosition(0)
                .perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(withId(R.id.saveViewUserProgress))
                .perform(ViewActions.click());

        Espresso.onView(withId(R.id.backToHomeViewUserProgress))
                .perform(ViewActions.click());
    }


    @Test
    public void testViewUserProgressForAthlete() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ViewUserProgressActivity.class);
        intent.putExtra("username", "brother3");
        intent.putExtra("accountType", "Athlete");
        intent.putExtra("firstName", "Franck");
        intent.putExtra("lastName", "Franck");

        ActivityScenario<ViewUserProgressActivity> scenario = ActivityScenario.launch(intent);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(withId(R.id.getGraphSpinnerForUser)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onData(anything())
                .atPosition(0)
                .perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(withId(R.id.saveViewUserProgress))
                .perform(ViewActions.click());

        Espresso.onView(withId(R.id.backToHomeViewUserProgress))
                .perform(ViewActions.click());
    }

    @Test
    public void testPostAnnouncement() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), TeamAnnouncementsActivity.class);
        intent.putExtra("username", "MasterSnapper");
        intent.putExtra("accountType", "Coach");
        intent.putExtra("firstName", "Franck");
        intent.putExtra("lastName", "Franck");

        ActivityScenario<TeamAnnouncementsActivity> scenario = ActivityScenario.launch(intent);

        Espresso.onView(ViewMatchers.withId(R.id.enterAnnouncement))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.enterAnnouncement))
                .perform(ViewActions.typeText("Max's computer is fast"));

        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.postAnnouncementButton))
                .perform(ViewActions.click());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(withId(R.id.backToHomeButtonAnnouncements))
                .perform(ViewActions.click());
    }

    @Test
    public void SimulateJoinTeamCoach(){

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), JoinTeamActivity.class);
        intent.putExtra("username", "MasterSnapper");
        intent.putExtra("accountType", "Coach");
        intent.putExtra("firstName", "CoachFranck");
        intent.putExtra("lastName", "Franck");

        ActivityScenario<JoinTeamActivity> scenario = ActivityScenario.launch(intent);

        Espresso.onView(ViewMatchers.withId(R.id.EnterTeamName))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.EnterTeamInviteCode))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.EnterTeamName))
                .perform(ViewActions.typeText("Bucks"));

        Espresso.onView(ViewMatchers.withId(R.id.EnterTeamInviteCode))
                .perform(ViewActions.typeText("4321"));

        Espresso.closeSoftKeyboard();

        Espresso.onView(ViewMatchers.withId(R.id.joinTeamButton))
                .perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ActivityScenario.launch(CoachHomeActivity.class);
        Espresso.onView(ViewMatchers.withId(android.R.id.content))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void SimulateJoinTeamAthlete(){

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), JoinTeamActivity.class);
        intent.putExtra("username", "athlete3");
        intent.putExtra("accountType", "Athlete");
        intent.putExtra("firstName", "CoachFranck");
        intent.putExtra("lastName", "Franck");

        ActivityScenario<JoinTeamActivity> scenario = ActivityScenario.launch(intent);

        Espresso.onView(ViewMatchers.withId(R.id.EnterTeamName))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.EnterTeamInviteCode))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.EnterTeamName))
                .perform(ViewActions.typeText("Bucks"));

        Espresso.onView(ViewMatchers.withId(R.id.EnterTeamInviteCode))
                .perform(ViewActions.typeText("1234"));

        Espresso.closeSoftKeyboard();

        Espresso.onView(ViewMatchers.withId(R.id.joinTeamButton))
                .perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ActivityScenario.launch(AthleteHomeActivity.class);
        Espresso.onView(ViewMatchers.withId(android.R.id.content))
                .check(ViewAssertions.matches(isDisplayed()));
    }
    @Test
    public void testSubmitWorkoutLog() {
        // Setup and launch the activity
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), LogWorkoutActivity.class);
        intent.putExtra("username", "athlete3");
        intent.putExtra("firstName", "CoachFranck");
        intent.putExtra("accountType", "Athlete");

        ActivityScenario<LogWorkoutActivity> scenario = ActivityScenario.launch(intent);


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Espresso.onView(withId(R.id.workoutSpinnerFromAth)).perform(click());


        Espresso.onData(anything()).atPosition(0).perform(click());


        Espresso.onView(withId(R.id.logWeight)).perform(typeText("100"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.logReps)).perform(typeText("10"), closeSoftKeyboard());


        Espresso.onView(withId(R.id.submitWorkoutLogButton)).perform(click());



        Espresso.onView(withId(R.id.backButton)).perform(click());
    }

    @Test
    public void testAssignWorkout() {

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AssignWorkoutsActivity.class);
        intent.putExtra("username", "MasterSnapper");
        intent.putExtra("firstName", "CoachFranck");
        intent.putExtra("accountType", "Coach");
        intent.putExtra("teamName", "Bucks");
        ActivityScenario<AssignWorkoutsActivity> scenario = ActivityScenario.launch(intent);


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Espresso.onView(withId(R.id.athleteUsernameEditText)).perform(typeText("athlete3"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.workoutNameEditText)).perform(typeText("Workout1"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.repsEditText)).perform(typeText("10"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.weightEditText)).perform(typeText("100"), closeSoftKeyboard());


        Espresso.onView(withId(R.id.exerciseSpinner)).perform(click());
        Espresso.onData(anything()).atPosition(0).perform(click());


        Espresso.onView(withId(R.id.assignWorkoutButton)).perform(click());


        Espresso.onView(withId(R.id.backCoachHomeButton)).perform(click());
    }
    @Test
    public void testCreateGymRatAccount() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), CreateAccountActivity.class);
        ActivityScenario<CreateAccountActivity> scenario = ActivityScenario.launch(intent);

        Espresso.onView(withId(R.id.createAccountEnterUsername)).perform(typeText("thsaltough"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.createAccountEnterPass)).perform(typeText("password123"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.createAccountPageEnterFirstName)).perform(typeText("John"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.createAccountPageEnterLastName)).perform(typeText("Doe"), closeSoftKeyboard());

        Espresso.onView(withId(R.id.createAccountSelectUser)).perform(click());
        Espresso.onView(withText("Gym Rat")).perform(click());

         Espresso.onView(withId(R.id.createANewAccountButton)).perform(click());



    }
    @Test
    public void testCreateCoachAccount() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), CreateAccountActivity.class);
        ActivityScenario<CreateAccountActivity> scenario = ActivityScenario.launch(intent);

         Espresso.onView(withId(R.id.createAccountEnterUsername)).perform(typeText("frcbbi"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.createAccountEnterPass)).perform(typeText("password123"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.createAccountPageEnterFirstName)).perform(typeText("John"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.createAccountPageEnterLastName)).perform(typeText("Doe"), closeSoftKeyboard());

        Espresso.onView(withId(R.id.createAccountSelectUser)).perform(click());
        Espresso.onView(withText("Coach")).perform(click());

        Espresso.onView(withId(R.id.createANewAccountButton)).perform(click());



    }
    @Test
    public void testCreateAthleteAccount() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), CreateAccountActivity.class);
        ActivityScenario<CreateAccountActivity> scenario = ActivityScenario.launch(intent);

        Espresso.onView(withId(R.id.createAccountEnterUsername)).perform(typeText("oawaaou"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.createAccountEnterPass)).perform(typeText("password123"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.createAccountPageEnterFirstName)).perform(typeText("John"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.createAccountPageEnterLastName)).perform(typeText("Doe"), closeSoftKeyboard());

        Espresso.onView(withId(R.id.createAccountSelectUser)).perform(click());
        Espresso.onView(withText("Athlete")).perform(click());

        Espresso.onView(withId(R.id.createANewAccountButton)).perform(click());




    }
    @Test
    public void testGroupChatActivity() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GroupChatActivity.class);
        intent.putExtra("username", "athlete3");
        intent.putExtra("teamName", "Bucks");
        ActivityScenario<GroupChatActivity> scenario = ActivityScenario.launch(intent);

        Espresso.onView(withId(R.id.messageInput)).perform(typeText("Hello, team!"), closeSoftKeyboard());

        Espresso.onView(withId(R.id.sendButton)).perform(click());


        Espresso.onView(withId(R.id.backHomeButton)).perform(click());


    }

    @Test
    public void testTeamAdapter() {
         JSONArray mockUserList = createMockUserList();

         Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        TeamAdapter adapter = new TeamAdapter(mockUserList, appContext);

         ViewGroup fakeParent = new FrameLayout(appContext);
        UserItemViewHolder viewHolder = adapter.onCreateViewHolder(fakeParent, 0);
        adapter.onBindViewHolder(viewHolder, 0);
    }



    private JSONArray createMockUserList() {
        JSONArray userList = new JSONArray();

        try {
            JSONObject user1 = new JSONObject();
            user1.put("userName", "athlete1");
            user1.put("accountType", "Athlete");

            JSONObject user2 = new JSONObject();
            user2.put("userName", "MasterSnapper");
            user2.put("accountType", "Coach");

            userList.put(user1);
            userList.put(user2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userList;
    }

    @Test
    public void testCreateTeamActivity() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), CreateTeamActivity.class);
        ActivityScenario<CreateTeamActivity> scenario = ActivityScenario.launch(intent);

        Espresso.onView(withId(R.id.teamNameUnique)).perform(typeText("Team100"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.coachCode)).perform(typeText("123485"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.athCode)).perform(typeText("584321"), closeSoftKeyboard());

        Espresso.onView(withId(R.id.savingButton)).perform(click());


    }

    @Test
    public void testViewTeamActivity() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ViewTeamActivity.class);
        intent.putExtra("username", "MasterSnapper");
        intent.putExtra("accountType", "Coach");
        intent.putExtra("firstName", "Max");
        intent.putExtra("lastName", "Rohrer");
        intent.putExtra("teamName", "Bucks");
        ActivityScenario<ViewTeamActivity> scenario = ActivityScenario.launch(intent);

        Espresso.onView(withId(R.id.teamNameViewTeam)).check(matches(withText("Bucks")));


        Espresso.onView(withId(R.id.backToHomeViewTeam)).perform(click());
    }








}


