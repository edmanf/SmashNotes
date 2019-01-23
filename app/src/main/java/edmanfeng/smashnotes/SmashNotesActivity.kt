package edmanfeng.smashnotes

import android.os.Bundle
import androidx.fragment.app.Fragment

class SmashNotesActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return CharacterListFragment.newInstance()
    }
}
