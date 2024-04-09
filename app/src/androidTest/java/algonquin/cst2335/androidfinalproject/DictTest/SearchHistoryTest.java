package algonquin.cst2335.androidfinalproject.DictTest;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.core.StringContains.containsString;

import algonquin.cst2335.androidfinalproject.dictionary.DictActivity;
import algonquin.cst2335.androidfinalproject.R;

@RunWith(AndroidJUnit4.class)
public class SearchHistoryTest {
    @Rule
    public ActivityScenarioRule<DictActivity> activityRule = new ActivityScenarioRule<>(DictActivity.class);

    @Test
    public void testSearchHistory() {
        // input and search one word
        onView(withId(R.id.dictTextInput)).perform(typeText("history"));
        onView(withId(R.id.dictSearchButton)).perform(click());


    }
}
