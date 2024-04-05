package algonquin.cst2335.androidfinalproject.DictTest;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import algonquin.cst2335.androidfinalproject.dictionary.DictActivity;
import algonquin.cst2335.androidfinalproject.R;

@RunWith(AndroidJUnit4.class)
public class FavoriteItemTest {

    @Rule
    public ActivityScenarioRule<DictActivity> activityRule =
            new ActivityScenarioRule<>(DictActivity.class);

    @Test
    public void testClickFavoriteItem() {
        onView(withId(R.id.favoriteItem)).perform(click());
        // 进行断言验证，确保点击后的效果符合预期
    }
}

