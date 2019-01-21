package edmanfeng.smashnotes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment

class SmashNotesActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return CharacterListFragment.newInstance()
    }
}
