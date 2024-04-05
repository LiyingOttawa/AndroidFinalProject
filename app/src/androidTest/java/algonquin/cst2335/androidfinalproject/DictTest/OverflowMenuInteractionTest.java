package algonquin.cst2335.androidfinalproject.DictTest;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import algonquin.cst2335.androidfinalproject.dictionary.DictActivity;
import algonquin.cst2335.androidfinalproject.R;

@RunWith(AndroidJUnit4.class)
public class OverflowMenuInteractionTest {

    @Rule
    public ActivityScenarioRule<DictActivity> activityRule =
            new ActivityScenarioRule<>(DictActivity.class);

    @Test
    public void testClickBackToMainItem() {
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.dict_backToMainItem)).perform(click());
        // 进行断言验证
    }

    @Test
    public void testClickHelpItem() {
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.dict_helpItem)).perform(click());
        // 进行断言验证
    }

    @Test
    public void testClickAboutItem() {
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.dict_aboutItem)).perform(click());
        // 进行断言验证
    }
}
