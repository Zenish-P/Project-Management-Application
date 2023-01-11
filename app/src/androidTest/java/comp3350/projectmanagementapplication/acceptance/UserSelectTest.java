package comp3350.projectmanagementapplication.acceptance;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.presentation.UserSelectActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserSelectTest {
    static Intent intent = new Intent(
        ApplicationProvider.getApplicationContext(),
        UserSelectActivity.class
    );

    static {
        Bundle bundle = new Bundle();

        bundle.putString("teamName", "Team #1");

        intent.putExtras(bundle);
    }

    @Rule
    public ActivityScenarioRule<UserSelectActivity> activityRule = new ActivityScenarioRule<>(intent);

    @Before
    public void before() {
        Utils.resetDatabase();
    }

    @Test
    public void validUserCreationSucceeds() {
        Utils.clickButton(R.id.addNewUser);
        onView(
            withId(R.id.editName)
        ).perform(typeText("New Project"), closeSoftKeyboard());
        Utils.clickButton(R.id.buttonOne);

        Utils.isDisplayed("New Project");
    }

    @Test
    public void invalidUserCreationFails() {
        Utils.isDisplayed("Guido");

        Utils.clickButton(R.id.addNewUser);
        onView(
            withId(R.id.editName)
        ).perform(typeText("Guido"), closeSoftKeyboard());
        Utils.clickButton(R.id.buttonOne);

        Utils.isDisplayed(R.id.buttonOne);
    }

    @Test
    public void createUserButtonResponds() {
        Utils.clickButton(R.id.addNewUser);

        Utils.isDisplayed("Save");
    }

    @Test
    public void userNameDisplayed() {
        Utils.isDisplayed("Guido");
    }

    @Test
    public void userBioDisplayed() {
        Utils.isDisplayed("Likes sushi");
    }
}