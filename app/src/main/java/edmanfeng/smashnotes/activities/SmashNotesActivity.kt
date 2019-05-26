package edmanfeng.smashnotes.activities

import android.os.Bundle
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
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.overviewFragment, R.id.statsFragment),
            drawerLayout
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)

        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        bottom_nav_view.setupWithNavController(navController)
    }
}
