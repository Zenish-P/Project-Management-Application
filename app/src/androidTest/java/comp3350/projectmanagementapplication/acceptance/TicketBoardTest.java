package comp3350.projectmanagementapplication.acceptance;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.fail;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.presentation.TicketBoardActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TicketBoardTest {
    static Intent intent = new Intent(
        ApplicationProvider.getApplicationContext(),
        TicketBoardActivity.class
    );

    static {
        Bundle bundle = new Bundle();

        bundle.putString("teamName", "Team #1");
        bundle.putString("projectName", "Website");

        intent.putExtras(bundle);
    }

    @Rule
    public ActivityScenarioRule<TicketBoardActivity> activityRule = new ActivityScenarioRule<>(intent);

    @Before
    public void before() {
        Utils.resetDatabase();
    }

    @Test
    public void validTicketCreationSucceeds() {
        try {
            Utils.isDisplayed("New Ticket");
            fail();
        } catch (NoMatchingViewException exception) {}

        Utils.clickButton(R.id.addNewProject);
        onView(
            withId(R.id.editTextTextPersonName)
        ).perform(typeText("New Ticket"), closeSoftKeyboard());
        Utils.clickButton(R.id.button3);

        Utils.isDisplayed("New Ticket");
    }

    @Test
    public void invalidTicketCreationFails() {
        Utils.isDisplayed("Add a company info page");

        Utils.clickButton(R.id.addNewProject);
        onView(
            withId(R.id.editTextTextPersonName)
        ).perform(typeText("Add a company info page"), closeSoftKeyboard());
        Utils.clickButton(R.id.button3);

        Utils.isDisplayed(R.id.button3);
    }

    @Test
    public void createTicketButtonResponds() {
        Utils.clickButton(R.id.addNewProject);

        Utils.isDisplayed("Save");
        Utils.isDisplayed("Cancel");
    }

    @Test
    public void ticketTitleDisplayed() {
        Utils.isDisplayed("Add a company info page");
    }

    @Test
    public void ticketDescriptionDisplayed() {
        Utils.isDisplayed("Add a page which lists company information and vision");
    }

    @Test
    public void ticketPriorityDisplayed() {
        Utils.isDisplayed("Medium");
    }

    @Test
    public void ticketCostDisplayed() {
        Utils.isDisplayed("3");
    }
}