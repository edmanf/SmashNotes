package edmanfeng.smashnotes

import androidx.fragment.app.Fragment

class SmashNotesActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return OverviewFragment.newInstance()
    }
}
