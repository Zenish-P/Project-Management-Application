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
import comp3350.projectmanagementapplication.presentation.ProjectSelectActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProjectSelectTest {
    static Intent intent = new Intent(
        ApplicationProvider.getApplicationContext(),
        ProjectSelectActivity.class
    );

    static {
        Bundle bundle = new Bundle();

        bundle.putString("teamName", "Team #1");

        intent.putExtras(bundle);
    }

    @Rule
    public ActivityScenarioRule<ProjectSelectActivity> activityRule = new ActivityScenarioRule<>(intent);

    @Before
    public void before() {
        Utils.resetDatabase();
    }

    @Test
    public void validProjectCreationSucceeds() {
        Utils.clickButton(R.id.floatingActionButton3);
        onView(
            withId(R.id.projectNameInput)
        ).perform(typeText("New Project"), closeSoftKeyboard());
        Utils.clickButton(R.id.finishButtonInProject);

        Utils.isDisplayed("New Project");
    }

    @Test
    public void invalidProjectCreationFails() {
        Utils.isDisplayed("Website");

        Utils.clickButton(R.id.floatingActionButton3);
        onView(
            withId(R.id.projectNameInput)
        ).perform(typeText("Website"), closeSoftKeyboard());
        Utils.clickButton(R.id.finishButtonInProject);

        Utils.isDisplayed(R.id.finishButtonInProject);
    }

    @Test
    public void createProjectButtonResponds() {
        Utils.clickButton(R.id.floatingActionButton3);

        Utils.isDisplayed("Project Create");
    }

    @Test
    public void projectListDisplayed() {
        Utils.isDisplayed("Website");
    }
}