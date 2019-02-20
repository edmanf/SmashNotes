package edmanfeng.smashnotes

import android.view.MenuItem
import androidx.fragment.app.Fragment

class SmashNotesActivity : SingleFragmentNavDrawerActivity() {
    override fun createFragment(): Fragment {
        return OverviewFragment.newInstance()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }
}
