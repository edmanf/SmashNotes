package edmanfeng.smashnotes.activities

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import edmanfeng.smashnotes.R
import edmanfeng.smashnotes.fragments.OverviewFragment
import edmanfeng.smashnotes.fragments.StatsFragment

class SmashNotesActivity : SingleFragmentNavDrawerActivity() {
    override fun createFragment(): Fragment {
        return OverviewFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            when(it.itemId) {
                R.id.nav_stats -> {
                    val statsFragment = StatsFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, statsFragment)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> true
            }
        }
    }
}
