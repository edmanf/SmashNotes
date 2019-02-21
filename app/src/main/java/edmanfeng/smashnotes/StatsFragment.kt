package edmanfeng.smashnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class StatsFragment : Fragment() {
    companion object {
        fun newInstance() : StatsFragment {
            return StatsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.stats_fragment, container, false)
        return v
    }
}