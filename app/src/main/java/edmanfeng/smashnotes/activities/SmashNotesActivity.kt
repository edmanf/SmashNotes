package edmanfeng.smashnotes.activities

import android.os.Bundle
import android.util.Log
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import edmanfeng.smashnotes.R
import edmanfeng.smashnotes.fragments.OverviewFragment
import edmanfeng.smashnotes.fragments.StatsFragment
import kotlinx.android.synthetic.main.activity_fragment.*

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

        bottom_nav_view.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.bottom_nav_history -> {
                    Log.d("SmashNotesActivity", "bottom history")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, OverviewFragment.newInstance())
                        .commit()

                    true
                }
                R.id.bottom_nav_ranking -> {
                    Log.d("SmashNotesActivity", "bottom ranking")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, StatsFragment.newInstance())
                        .commit()
                    true
                }
                R.id.bottom_nav_notes -> {
                    Log.d("SmashNotesActivity", "bottom notes")
                    true
                }
                else -> false
            }
        }

    }
}
