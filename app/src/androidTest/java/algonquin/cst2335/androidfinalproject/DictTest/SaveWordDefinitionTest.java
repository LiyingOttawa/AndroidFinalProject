package algonquin.cst2335.androidfinalproject.DictTest;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.dictionary.DictActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SaveWordDefinitionTest {

    @Rule
    public ActivityScenarioRule<DictActivity> activityRule = new ActivityScenarioRule<>(DictActivity.class);

    @Test
    public void testSaveWordDefinition() {
        // Assume the word "hello" is input into a search field and searched before saving
        onView(withId(R.id.dictTextInput)).perform(typeText("hello"));
        onView(withId(R.id.dictSearchButton)).perform(click());

        // Simulate clicking the save button (you might need to adjust the ID according to your actual layout)
        onView(withId(R.id.favoriteItem)).perform(click());

        // Assuming a Toast appears upon successful save, but since Espresso can't directly assert Toast
        // might consider using a third-party library to handle this or check for other UI changes instead
    }
}

