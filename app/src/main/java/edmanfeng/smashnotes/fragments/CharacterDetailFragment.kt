package edmanfeng.smashnotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import edmanfeng.smashnotes.R

class CharacterDetailFragment : Fragment() {


    private lateinit var mCharacter : String
    private lateinit var mTitleTextView : TextView

    companion object {
        private const val ARG_CHARACTER_DETAIL = "character_detail"
        fun newInstance(character : String) : CharacterDetailFragment {
            val args = Bundle()
            args.putSerializable(ARG_CHARACTER_DETAIL, character)

            val frag = CharacterDetailFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCharacter = arguments?.getSerializable(
            ARG_CHARACTER_DETAIL
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

        mTitleTextView = v.findViewById(R.id.title_character_name_text_view)
        mTitleTextView.text = mCharacter

        val recordButton = v.findViewById<Button>(
            R.id.record_button
        )
        recordButton.setOnClickListener{
            val frag = MatchRecordFragment.newInstance(mCharacter)

            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, frag)
                ?.addToBackStack(null)
                ?.commit()
        }

        return v
    }
}