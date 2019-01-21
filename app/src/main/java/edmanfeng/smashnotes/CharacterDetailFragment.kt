package edmanfeng.smashnotes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class CharacterDetailFragment : Fragment() {


    private lateinit var mCharacter : String
    private lateinit var mTitleTextView : TextView

    companion object {
        private val ARG_CHARACTER_DETAIL = "character_detail"
        fun newInstance(character : String) : CharacterDetailFragment{
            var args = Bundle()
            args.putSerializable(ARG_CHARACTER_DETAIL, character)

            var frag = CharacterDetailFragment()
            frag.setArguments(args)
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCharacter = arguments?.getSerializable(
            CharacterDetailFragment.ARG_CHARACTER_DETAIL
        ) as String
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(
            R.layout.character_detail_fragment,
            container,
            false
        )

        mTitleTextView = v.findViewById<TextView>(
            R.id.title_character_name_text_view
        )
        mTitleTextView.setText(mCharacter)

        val recordButton = v.findViewById<Button>(
            R.id.record_button
        )
        recordButton.setOnClickListener({v ->
            var frag = MatchRecordFragment.newInstance()

            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, frag)
                ?.addToBackStack(null)
                ?.commit()
        })

        return v
    }
}