package algonquin.cst2335.androidfinalproject.DictTest;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import algonquin.cst2335.androidfinalproject.dictionary.DictActivity;
import algonquin.cst2335.androidfinalproject.R;

@RunWith(AndroidJUnit4.class)
public class MenuInteractionTest {

    @Rule
    public ActivityScenarioRule<DictActivity> activityRule =
            new ActivityScenarioRule<>(DictActivity.class);

    @Test
    public void testClickFavoriteItem() {
        // Click on the "Favorite" menu item
        onView(withId(R.id.favoriteItem)).perform(click());

        // Assertion verification - modify according to your application logic
        // For example, check if the Favorites Fragment is displayed
        // onView(withId(R.id.favoriteFragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickBackToMainItem() {
        // Open the overflow menu
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // Click on the "Return to Main Interface" menu item
        onView(withText(R.string.dict_backToMainItem)).perform(click());

        // Assertion verification
        // onView(withId(R.id.mainActivityView)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickHelpItem() {
        // Open the overflow menu
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // Click on the "Help" menu item
        onView(withText(R.string.dict_helpItem)).perform(click());

        // Assertion verification
        // onView(withId(R.id.helpView)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickAboutItem() {
        // Open the overflow menu
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // Click on the "About" menu item
        onView(withText(R.string.dict_aboutItem)).perform(click());

        // Assertion verification
        // onView(withId(R.id.aboutView)).check(matches(isDisplayed()));
    }
}