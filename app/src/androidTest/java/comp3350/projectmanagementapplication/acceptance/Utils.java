package comp3350.projectmanagementapplication.acceptance;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

import comp3350.projectmanagementapplication.App;
import comp3350.projectmanagementapplication.persistence.DataAccess;
import comp3350.projectmanagementapplication.persistence.HSQLDatabase;
import comp3350.projectmanagementapplication.persistence.StubDatabase;

public class Utils {
    public static void resetDatabase() {
        App.writeDatabaseScript(ApplicationProvider.getApplicationContext(), true);

        DataAccess database = App.getDatabase();

        if (database instanceof HSQLDatabase) {
            ((HSQLDatabase) database).reset();
        } else if (database instanceof StubDatabase) {
            ((StubDatabase) database).reset();
        }
    }

    public static void isDisplayed(String string) {
        Utils.isDisplayed(withText(string));
    }

    public static void isDisplayed(int id) {
        Utils.isDisplayed(withId(id));
    }

    public static void isDisplayed(Matcher<View> matcher) {
        onView(matcher).check(matches(ViewMatchers.isDisplayed()));
    }

    public static void clickButton(int id) {
        onView(withId(id)).perform(click());
    }
}
