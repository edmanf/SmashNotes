package edmanfeng.smashnotes

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import edmanfeng.smashnotes.fragments.OverviewFragment
import org.junit.Test
import org.mockito.Mockito.mock

class OverviewFragmentTest {
    @Test
    fun clickAddGameButton_navigateToMatchRecordFragment() {
        val navController = mock(NavController::class.java)
        val scenario = launchFragmentInContainer<OverviewFragment>()
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.add_button)).perform(click())

    }
}