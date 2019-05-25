package edmanfeng.smashnotes.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import edmanfeng.smashnotes.R
import kotlinx.android.synthetic.main.activity_fragment.*

abstract class SingleFragmentNavDrawerActivity : AppCompatActivity() {

    protected abstract fun createFragment(): Fragment

    lateinit var drawerLayout : DrawerLayout

    /**
     * Returns the layout id of the activity that hosts the fragment.
     * @return The layout id of the activity.
     */
    @LayoutRes
    protected fun getLayoutResId(): Int {
        return R.layout.activity_fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())

        drawerLayout = findViewById(R.id.drawer_layout)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                Log.d("SingleFrag", "HOME")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}