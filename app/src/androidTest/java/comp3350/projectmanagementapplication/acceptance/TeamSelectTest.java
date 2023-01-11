package comp3350.projectmanagementapplication.acceptance;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.presentation.TeamSelectActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TeamSelectTest {
    @Rule
    public ActivityScenarioRule<TeamSelectActivity> activityRule = new ActivityScenarioRule<>(
        TeamSelectActivity.class
    );

    @Before
    public void before() {
        Utils.resetDatabase();
    }

    @Test
    public void teamListDisplayed() {
        Utils.isDisplayed("Team #1");
    }

    @Test
    public void validTeamCreationSucceeds() {
        Utils.clickButton(R.id.floatingActionButton);
        onView(
            withId(R.id.nameInput)
        ).perform(typeText("New Team"), closeSoftKeyboard());
        Utils.clickButton(R.id.finishButton);

        Utils.isDisplayed("New Team");
    }

    @Test
    public void invalidTeamCreationFails() {
        Utils.isDisplayed("Team #1");

        Utils.clickButton(R.id.floatingActionButton);
        onView(
                withId(R.id.nameInput)
        ).perform(typeText("Team #1"), closeSoftKeyboard());
        Utils.clickButton(R.id.finishButton);
        Utils.isDisplayed(R.id.finishButton);
    }

    @Test
    public void createTeamButtonResponds() {
        Utils.clickButton(R.id.floatingActionButton);

        Utils.isDisplayed("Team Create");
    }
}