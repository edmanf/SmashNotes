package edmanfeng.smashnotes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RecordFragment : Fragment() {


    private lateinit var mCharacter : String
    private lateinit var mTitleTextView : TextView

    companion object {
        private val ARG_RECORD_CHARACTER = "record_character"
        fun newInstance(character : String) : RecordFragment{
            var args = Bundle()
            args.putSerializable(ARG_RECORD_CHARACTER, character)

            var frag = RecordFragment()
            frag.setArguments(args)
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCharacter = arguments?.getSerializable(
            RecordFragment.ARG_RECORD_CHARACTER
        ) as String
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(
            R.layout.record_match_fragment,
            container,
            false
        )

        mTitleTextView = v.findViewById<TextView>(
            R.id.title_character_name_text_view
        )
        mTitleTextView.setText(mCharacter)

        return v
    }
}