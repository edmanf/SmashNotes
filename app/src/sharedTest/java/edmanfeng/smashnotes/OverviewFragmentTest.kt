package edmanfeng.smashnotes

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import edmanfeng.smashnotes.fragments.OverviewFragment
import edmanfeng.smashnotes.fragments.OverviewFragmentDirections
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class OverviewFragmentTest {

    @Test
    fun clickAddGameButton_navigateToMatchRecordFragment() {
        val navController = mock(NavController::class.java)

        val scenario = launchFragmentInContainer<OverviewFragment>(Bundle(), R.style.AppTheme)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        onView(withId(R.id.add_button)).perform(click())
        verify(navController).navigate(
            NavGraphDirections.actionGlobalMatchRecordFragment(GameRecord.NEW_GAME_ID)
        )
    }
}