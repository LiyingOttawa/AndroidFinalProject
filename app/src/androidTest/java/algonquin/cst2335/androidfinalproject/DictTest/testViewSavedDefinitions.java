package algonquin.cst2335.androidfinalproject.DictTest;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.dictionary.DictActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;

/**
 * Test class to verify functionality of viewing saved word definitions.
 */
@RunWith(AndroidJUnit4.class)
public class testViewSavedDefinitions {

    @Rule
    public ActivityScenarioRule<DictActivity> activityRule = new ActivityScenarioRule<>(DictActivity.class);

    @Test
    public void testViewSavedDefinitions() {
        // Assume your menu item to view favorites is identified by R.id.favoriteItem
        onView(withId(R.id.favoriteItem)).perform(click());

        // Check if the RecyclerView displaying saved definitions (dictRecycleView) is visible
        onView(withId(R.id.dictRecycleView))
                .check(matches(isDisplayed()));

        // Assuming the RecyclerView has at least one item
        onView(withId(R.id.dictRecycleView))
                .check(matches(hasMinimumChildCount(1)));
    }
}


