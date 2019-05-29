package edmanfeng.smashnotes.activities

import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import edmanfeng.smashnotes.R
import edmanfeng.smashnotes.fragments.OverviewFragment
import kotlinx.android.synthetic.main.activity_fragment.*

class SmashNotesActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return OverviewFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener{controller, dest, args ->
            if (dest.id == R.id.matchRecordFragment) {
                bottom_nav_view.visibility = View.GONE
            } else {
                bottom_nav_view.visibility = View.VISIBLE
            }
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.overviewFragment, R.id.statsFragment),
            findViewById(R.id.drawer_layout)
        )
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        bottom_nav_view.setupWithNavController(navController)
    }
}
