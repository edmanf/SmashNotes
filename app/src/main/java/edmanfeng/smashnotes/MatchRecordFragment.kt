package edmanfeng.smashnotes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MatchRecordFragment : Fragment() {
    companion object {
        fun newInstance() : MatchRecordFragment {
            return MatchRecordFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.match_record_fragment, container, false)

        return v
    }
}