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

abstract class SingleFragmentActivity : AppCompatActivity() {

    protected abstract fun createFragment(): Fragment

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
    }

}